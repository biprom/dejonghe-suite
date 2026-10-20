package com.adverto.dejonghe.common.services;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Optional;

@Service
public class DocumentFileStorageService {

    private final GridFsTemplate gridFsTemplate;

    public DocumentFileStorageService(GridFsTemplate gridFsTemplate) {
        this.gridFsTemplate = gridFsTemplate;
    }

    public record StoredFile(
            String id,
            String filename,
            String contentType,
            long length
    ) {
        public boolean isImage() {
            return contentType.startsWith("image/");
        }

        public boolean isPdf() {
            return "application/pdf".equals(contentType);
        }
    }

    public StoredFile store(String filename, byte[] data) {

        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("Het bestand is leeg.");
        }

        String safeName = filename == null
                ? "bestand"
                : filename.replace('\\', '/')
                .replaceAll("[\\r\\n]", "_");

        safeName = safeName.substring(safeName.lastIndexOf('/') + 1);

        String mimeType = detectMimeType(safeName, data);

        Document metadata = new Document("contentType", mimeType);

        ObjectId id = gridFsTemplate.store(
                new ByteArrayInputStream(data),
                safeName,
                mimeType,
                metadata
        );

        return new StoredFile(
                id.toHexString(),
                safeName,
                mimeType,
                data.length
        );
    }

    public Optional<StoredFile> getInfo(String id) {

        GridFSFile file = findFile(id);

        if (file == null) {
            return Optional.empty();
        }

        Document metadata = file.getMetadata();

        String mimeType = null;

        if (metadata != null) {
            mimeType = metadata.getString("contentType");

            if (mimeType == null) {
                mimeType = metadata.getString("_contentType");
            }
        }

        if (mimeType == null || mimeType.isBlank()) {
            mimeType = mimeFromExtension(file.getFilename());
        }

        return Optional.of(new StoredFile(
                id,
                file.getFilename() == null ? "bestand" : file.getFilename(),
                mimeType,
                file.getLength()
        ));
    }

    public InputStream openStream(String id) throws IOException {

        GridFSFile file = findFile(id);

        if (file == null) {
            throw new IOException("Bestand niet gevonden: " + id);
        }

        return gridFsTemplate.getResource(file).getInputStream();
    }

    public void delete(String id) {

        if (id != null && ObjectId.isValid(id)) {
            gridFsTemplate.delete(
                    Query.query(Criteria.where("_id").is(new ObjectId(id)))
            );
        }
    }

    private GridFSFile findFile(String id) {

        if (id == null || !ObjectId.isValid(id)) {
            return null;
        }

        return gridFsTemplate.findOne(
                Query.query(Criteria.where("_id").is(new ObjectId(id)))
        );
    }

    private String detectMimeType(String filename, byte[] data) {

        // Herken foto's en PDF aan hun inhoud.
        if (startsWith(data, 0xFF, 0xD8, 0xFF)) {
            return "image/jpeg";
        }

        if (startsWith(data, 0x89, 0x50, 0x4E, 0x47)) {
            return "image/png";
        }

        if (startsWith(data, 0x47, 0x49, 0x46, 0x38)) {
            return "image/gif";
        }

        if (startsWith(data, 0x52, 0x49, 0x46, 0x46)
                && startsWithAt(data, 8, 0x57, 0x45, 0x42, 0x50)) {
            return "image/webp";
        }

        if (startsWith(data, 0x25, 0x50, 0x44, 0x46, 0x2D)) {
            return "application/pdf";
        }

        String extension = extension(filename);

        // Andere documenten.
        return switch (extension) {

            case "docx", "xlsx", "pptx" -> {
                if (!startsWith(data, 0x50, 0x4B, 0x03, 0x04)) {
                    throw new IllegalArgumentException(
                            "Ongeldig Office-document."
                    );
                }
                yield mimeFromExtension(filename);
            }

            case "doc", "xls", "ppt" -> {
                if (!startsWith(data, 0xD0, 0xCF, 0x11, 0xE0)) {
                    throw new IllegalArgumentException(
                            "Ongeldig Office-document."
                    );
                }
                yield mimeFromExtension(filename);
            }

            case "txt", "csv" -> mimeFromExtension(filename);

            default -> throw new IllegalArgumentException(
                    "Bestandstype niet ondersteund: " + filename
            );
        };
    }

    private String mimeFromExtension(String filename) {

        return switch (extension(filename)) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "webp" -> "image/webp";
            case "pdf" -> "application/pdf";
            case "doc" -> "application/msword";
            case "docx" ->
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "xls" -> "application/vnd.ms-excel";
            case "xlsx" ->
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "ppt" -> "application/vnd.ms-powerpoint";
            case "pptx" ->
                    "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            case "txt" -> "text/plain";
            case "csv" -> "text/csv";
            default -> "application/octet-stream";
        };
    }

    private String extension(String filename) {

        if (filename == null || !filename.contains(".")) {
            return "";
        }

        return filename.substring(filename.lastIndexOf('.') + 1)
                .toLowerCase(Locale.ROOT);
    }

    private boolean startsWith(byte[] data, int... signature) {
        return startsWithAt(data, 0, signature);
    }

    private boolean startsWithAt(byte[] data, int offset, int... signature) {

        if (data.length < offset + signature.length) {
            return false;
        }

        for (int i = 0; i < signature.length; i++) {
            if ((data[offset + i] & 0xFF) != signature[i]) {
                return false;
            }
        }

        return true;
    }
}
