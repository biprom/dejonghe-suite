package com.adverto.dejonghe.server.Controllers;

import com.adverto.dejonghe.common.dbservices.WorkOrderService;
import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrder;
import com.adverto.dejonghe.common.services.WorkOrderPdfServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
public class PdfController {

    @Value( "${rootFolder}" )
    private String rootFolder;

    @Autowired
    WorkOrderService workOrderService;

    @Autowired
    private WorkOrderPdfServices workOrderPdfServices;

    private String pdfNaam;

    //for workorders
    @GetMapping("/pdf/workorder/{workOrderId}")
    public ResponseEntity<Resource> getWorkOrderPdf(
            @PathVariable String workOrderId) {

        Optional<WorkOrder> optionalWorkOrder =
                workOrderService.getWorkOrderById(workOrderId);

        if (optionalWorkOrder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            String pdfNaam =
                    workOrderPdfServices.generateWorkOrderPDF(optionalWorkOrder.get());

            Path rootPath = Paths.get(rootFolder).toAbsolutePath().normalize();
            Path pdfPath = rootPath.resolve(pdfNaam).normalize();

            if (!pdfPath.startsWith(rootPath) || !Files.exists(pdfPath)) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new InputStreamResource(Files.newInputStream(pdfPath));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + pdfPath.getFileName() + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(Files.size(pdfPath))
                    .body(resource);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //for invoices
    @GetMapping("/pdf/invoice/{id}")
    public ResponseEntity<Resource> getPdf(@PathVariable String id) {
        try {
            Path rootPath = Paths.get(rootFolder).toAbsolutePath().normalize();
            Path pdfPath = rootPath.resolve(pdfNaam).normalize();

            // Voorkomt toegang tot bestanden buiten rootFolder
            if (!pdfPath.startsWith(rootPath)) {
                return ResponseEntity.badRequest().build();
            }

            File file = pdfPath.toFile();

            if (!file.exists() || !file.isFile()) {
                return ResponseEntity.notFound().build();
            }

            InputStreamResource resource =
                    new InputStreamResource(new FileInputStream(file));

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + file.getName() + "\""
                    )
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(file.length())
                    .body(resource);

        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    //for attachements for invoices
    @GetMapping("/attachement")
    public ResponseEntity<Resource> getAttachement() {
        try {
            File file = new File(rootFolder + "all_attachments.pdf");

            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }

            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=attachement.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(file.length())
                    .body(resource);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    public void setPdfNaam(String pdfNaam) {
        this.pdfNaam = pdfNaam;
    }

}
