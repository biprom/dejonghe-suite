package com.adverto.dejonghe.server.views.subViews;

import com.adverto.dejonghe.common.dbservices.DeviceService;
import com.adverto.dejonghe.common.entities.customers.Address;
import com.adverto.dejonghe.common.entities.enums.employee.UserFunction;
import com.adverto.dejonghe.common.entities.images.DocumentBundle;

import com.adverto.dejonghe.common.entities.installation.Device;
import com.adverto.dejonghe.common.services.DocumentFileStorageService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.server.streams.DownloadHandler;
import com.vaadin.flow.server.streams.DownloadResponse;
import com.vaadin.flow.server.streams.UploadHandler;
import com.adverto.dejonghe.common.services.DocumentFileStorageService.StoredFile;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

@Component
@Scope("prototype")
public class CurrentDocumentSubView extends VerticalLayout {

    private final DocumentFileStorageService storage;
    private final DeviceService deviceService;

    private final Grid<DocumentBundle> grid =
            new Grid<>(DocumentBundle.class, false);

    private final TextField descriptionFilter = new TextField();
    private final Select<String> typeFilter = new Select<>();

    private final TextField uploadDescription = new TextField();
    private final DatePicker uploadDate = new DatePicker("Datum");

    private final Upload upload;
    private final Button uploadBundleButton =
            new Button("Bundel uploaden", VaadinIcon.UPLOAD.create());

    private boolean uploadInProgress = false;
    private int expectedUploadCount = 0;

    private final ConcurrentLinkedQueue<StoredFile> pendingFiles =
            new ConcurrentLinkedQueue<>();

    private final Map<String, Optional<StoredFile>> fileCache =
            new HashMap<>();

    private final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Address currentAddress;
    private Consumer<Address> saveHandler;
    private MenuItem mergeMenuItem;

    private record ViewerEntry(
            DocumentBundle bundle,
            StoredFile file
    ) {}

    public CurrentDocumentSubView(
            DocumentFileStorageService storage,
            DeviceService deviceService) {

        this.storage = storage;
        this.deviceService = deviceService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        H3 title = new H3("Documenten en foto's");

        Button allImages = new Button(
                "Alle foto's",
                VaadinIcon.PICTURE.create(),
                event -> openViewer(getAllFiles(true))
        );

        Button allDocuments = new Button(
                "Alle documenten",
                VaadinIcon.FILE_TEXT.create(),
                event -> openViewer(getAllFiles(false))
        );

        // Acties op de aangevinkte rijen in de Grid.
        MenuBar actionMenu = new MenuBar();
        actionMenu.addThemeVariants(
                MenuBarVariant.LUMO_DROPDOWN_INDICATORS
        );
        MenuItem actions = actionMenu.addItem("Actie");
        mergeMenuItem = actions.getSubMenu().addItem(
                "Voeg samen",
                event -> confirmMergeSelectedBundles()
        );
        mergeMenuItem.setEnabled(false);

        HorizontalLayout toolbar = new HorizontalLayout(
                title,
                allImages,
                allDocuments,
                actionMenu
        );

        toolbar.setWidthFull();
        toolbar.setAlignItems(FlexComponent.Alignment.CENTER);
        toolbar.expand(title);

        configureGrid();

        uploadDescription.setLabel("Omschrijving nieuwe bundel");
        uploadDescription.setPlaceholder(
                "Bijvoorbeeld: Foto's installatie + keuringsverslag"
        );
        uploadDescription.setWidthFull();

        uploadDate.setValue(LocalDate.now());

        /*
         * Iedere file wordt afzonderlijk door de UploadHandler
         * ontvangen en in GridFS opgeslagen.
         *
         * Hier maken we NOG GEEN DocumentBundle!
         *
         * De callback draait buiten de Vaadin UI-lock.
         * Daarom slaan we hier alleen bestanden op en wijzigen
         * we geen Vaadin-componenten.
         */
        upload = new Upload(UploadHandler.inMemory(
                (metadata, data) -> {

                    StoredFile file = storage.store(
                            metadata.fileName(),
                            data
                    );

                    pendingFiles.add(file);
                }
        ));

        upload.setWidthFull();
        // De bestanden blijven in de wachtrij totdat op 'Bundel uploaden' wordt geklikt.
        upload.setAutoUpload(false);
        upload.setDropAllowed(true);
        upload.addClassName("document-bundle-upload");
        upload.setMaxFiles(50);
        upload.setMaxFileSize(25 * 1024 * 1024);

        upload.setAcceptedFileTypes(
                ".jpg", ".jpeg", ".png", ".gif", ".webp",
                ".pdf", ".doc", ".docx",
                ".xls", ".xlsx",
                ".ppt", ".pptx",
                ".txt", ".csv"
        );

        upload.setUploadButton(
                new Button("Bestanden selecteren")
        );

        upload.setDropLabel(
                new Span("Sleep foto's en documenten hierheen")
        );

        // De originele filelijst kan door de gebruiker nog aangepast worden.
        // Pas na het einde van de volledige reeks wordt de bundel aangemaakt.
        upload.addAllFinishedListener(event -> finishManualUpload());

        upload.addFileRejectedListener(event ->
                showError(event.getErrorMessage())
        );

        upload.setEnabled(false);

        uploadBundleButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        uploadBundleButton.setEnabled(false);
        uploadBundleButton.addClickListener(event -> startBundleUpload());

        // Verberg het kleine individuele startknopje in de filelijst.
        // Zo kan de gebruiker uitsluitend de volledige reeks starten.
        // De verwijderknop per klaargezet bestand blijft zichtbaar.
        upload.getElement().executeJs("""
            const styleId = 'document-bundle-upload-css';
            if (!document.getElementById(styleId)) {
                const style = document.createElement('style');
                style.id = styleId;
                style.textContent =
                    'vaadin-upload.document-bundle-upload ' +
                    'vaadin-upload-file::part(start-button),' +
                    'vaadin-upload.document-bundle-upload ' +
                    'vaadin-upload-file::part(retry-button)' +
                    '{display:none!important;}';
                document.head.appendChild(style);
            }
        """);

        HorizontalLayout uploadFields = new HorizontalLayout(
                uploadDate,
                uploadDescription
        );

        uploadFields.setWidthFull();
        uploadFields.expand(uploadDescription);
        uploadFields.setAlignItems(FlexComponent.Alignment.END);

        add(
                toolbar,
                grid,
                new H3("Nieuwe bundel toevoegen"),
                uploadFields,
                upload,
                uploadBundleButton
        );
    }

    // =====================================================
    // GRID
    // =====================================================

    private void configureGrid() {

        grid.setWidthFull();
        grid.setAllRowsVisible(true);
        // Checkboxen om meerdere bundels tegelijk te selecteren.
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addSelectionListener(event -> updateMergeActionState());

        grid.addColumn(bundle ->
                        bundle.getDate() == null
                                ? ""
                                : bundle.getDate().format(dateFormatter)
                ).setHeader("Datum")
                .setAutoWidth(true)
                .setFlexGrow(0);

        descriptionFilter.setPlaceholder("Zoeken...");
        descriptionFilter.setClearButtonVisible(true);
        descriptionFilter.setValueChangeMode(ValueChangeMode.EAGER);
        descriptionFilter.setWidthFull();

        descriptionFilter.addValueChangeListener(
                event -> refreshGrid()
        );

        VerticalLayout descriptionHeader = new VerticalLayout(
                new Span("Omschrijving"),
                descriptionFilter
        );

        descriptionHeader.setPadding(false);
        descriptionHeader.setSpacing(false);

        // Regeleindes in de omschrijving behouden, ook na bundels samenvoegen.
        grid.addComponentColumn(bundle -> {
                    Span description = new Span(
                            bundle.getComment() == null
                                    ? ""
                                    : bundle.getComment());
                    description.getStyle()
                            .set("white-space", "pre-wrap")
                            .set("overflow-wrap", "anywhere")
                            .set("display", "block")
                            .set("width", "100%");
                    return description;
                }).setHeader(descriptionHeader)
                .setFlexGrow(2);

        typeFilter.setItems(
                "Alle types",
                "Documenten",
                "Foto's"
        );

        typeFilter.setValue("Alle types");
        typeFilter.setWidthFull();

        typeFilter.addValueChangeListener(
                event -> refreshGrid()
        );

        VerticalLayout typeHeader = new VerticalLayout(
                new Span("Type"),
                typeFilter
        );

        typeHeader.setPadding(false);
        typeHeader.setSpacing(false);

        grid.addColumn(this::getTypeDescription)
                .setHeader(typeHeader)
                .setAutoWidth(true);

        grid.addColumn(bundle ->
                        bundle.getIdDocumentList() == null
                                ? 0
                                : bundle.getIdDocumentList().size()
                ).setHeader("Aantal")
                .setAutoWidth(true)
                .setFlexGrow(0);

        grid.addComponentColumn(bundle ->
                        createViewerButton(bundle, false)
                ).setHeader("PDF / Docs")
                .setAutoWidth(true)
                .setFlexGrow(0);

        grid.addComponentColumn(bundle ->
                        createViewerButton(bundle, true)
                ).setHeader("Foto's")
                .setAutoWidth(true)
                .setFlexGrow(0);

        // Bundel koppelen aan meerdere toestellen van dit werfadres.
        grid.addComponentColumn(this::createDeviceLinkButton)
                .setHeader("Toestellen")
                .setAutoWidth(true)
                .setFlexGrow(0);

        grid.addComponentColumn(this::createEditButton)
                .setHeader("Wijzigen")
                .setAutoWidth(true)
                .setFlexGrow(0);

        grid.addComponentColumn(this::createDeleteBundleButton)
                .setHeader("Verwijderen")
                .setAutoWidth(true)
                .setFlexGrow(0);
    }

    private String getTypeDescription(DocumentBundle bundle) {

        boolean hasImage = files(bundle).stream()
                .anyMatch(StoredFile::isImage);

        boolean hasDocument = files(bundle).stream()
                .anyMatch(file -> !file.isImage());

        if (hasImage && hasDocument) {
            return "Foto + Document";
        }

        if (hasImage) {
            return "Foto";
        }

        if (hasDocument) {
            return "Document";
        }

        return "";
    }

    private Button createViewerButton(
            DocumentBundle bundle,
            boolean imageType
    ) {

        List<ViewerEntry> entries = files(bundle)
                .stream()
                .filter(file -> file.isImage() == imageType)
                .map(file -> new ViewerEntry(bundle, file))
                .toList();

        Button button = new Button(
                imageType
                        ? VaadinIcon.PICTURE.create()
                        : VaadinIcon.FILE_TEXT.create()
        );

        button.setEnabled(!entries.isEmpty());

        button.getElement().setAttribute(
                "title",
                imageType ? "Foto's bekijken" : "Documenten bekijken"
        );

        button.getElement().setAttribute(
                "aria-label",
                imageType ? "Foto's bekijken" : "Documenten bekijken"
        );

        button.addClickListener(event -> openViewer(entries));

        return button;
    }

    private Button createDeviceLinkButton(DocumentBundle bundle) {
        int count = bundle.getIdMachine() == null ? 0
                : (int) bundle.getIdMachine().stream()
                .filter(Objects::nonNull).distinct().count();

        Button button = new Button(
                "Toestellen (" + count + ")",
                VaadinIcon.LINK.create()
        );
        button.getElement().setAttribute(
                "title", "Bundel koppelen aan toestellen");
        button.addClickListener(event -> openDeviceLinkDialog(bundle));
        button.setEnabled(currentAddress != null && saveHandler != null
                && !uploadInProgress);
        return button;
    }

    private void openDeviceLinkDialog(DocumentBundle bundle) {
        if (uploadInProgress || currentAddress == null || saveHandler == null) {
            showError("Koppelen is momenteel niet mogelijk.");
            return;
        }
        if (bundle.getId() == null || bundle.getId().isBlank()) {
            showError("Deze bundel heeft geen ID en kan niet gekoppeld worden.");
            return;
        }
        List<String> addressDeviceIds = currentAddress.getCoupledDeviceList();
        if (addressDeviceIds == null || addressDeviceIds.isEmpty()) {
            Notification.show("Aan dit werfadres zijn nog geen toestellen gekoppeld.");
            return;
        }

        List<Device> devices = addressDeviceIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .map(deviceService::getDeviceById)
                .flatMap(Optional::stream)
                .toList();
        if (devices.isEmpty()) {
            Notification.show("Geen gekoppelde toestellen gevonden in MongoDB.");
            return;
        }

        CheckboxGroup<Device> options = new CheckboxGroup<>();

        options.setLabel("Selecteer één of meerdere toestellen");
        options.setItems(devices);
        options.setItemLabelGenerator(this::deviceLabel);
        options.setWidthFull();

        // Checkboxes onder elkaar tonen
        options.getStyle().set("--vaadin-checkbox-group-display", "flex");
        options.getStyle().set("flex-direction", "column");
        options.addThemeName("vertical");

        // Voorkom dat lange toestelnamen de popup verbreden
        options.getStyle().set("max-width", "100%");
        options.setValue(devices.stream()
                .filter(d -> bundle.getIdMachine() != null
                        && bundle.getIdMachine().contains(d.getId()))
                .collect(Collectors.toCollection(LinkedHashSet::new)));

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Documentbundel aan toestellen koppelen");
        dialog.setWidth("550px");

        Button cancel = new Button("Annuleren", event -> dialog.close());
        Button save = new Button("Koppelingen opslaan", event -> {
            Set<String> selectedDeviceIds = options.getValue().stream()
                    .map(Device::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            if (saveDeviceLinks(bundle, selectedDeviceIds)) {
                dialog.close();
            }
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttons = new HorizontalLayout(cancel, save);
        dialog.add(new VerticalLayout(
                new Span("Alleen toestellen van het geselecteerde werfadres zijn beschikbaar."),
                options, buttons));
        dialog.open();
    }

    private String deviceLabel(Device device) {
        List<String> parts = new ArrayList<>();
        if (device.getDeviceName() != null && !device.getDeviceName().isBlank()) {
            parts.add(device.getDeviceName());
        }
        if (device.getCode() != null && !device.getCode().isBlank()) {
            parts.add(device.getCode());
        }
        if (device.getSerialNumber() != null && !device.getSerialNumber().isBlank()) {
            parts.add("SN: " + device.getSerialNumber());
        }
        return parts.isEmpty() ? device.getId() : String.join(" - ", parts);
    }

    private boolean saveDeviceLinks(DocumentBundle bundle,
                                    Set<String> selectedDeviceIds) {
        if (currentAddress == null || saveHandler == null
                || currentAddress.getCoupledDocumentBundle() == null
                || !currentAddress.getCoupledDocumentBundle().contains(bundle)) {
            showError("Deze documentbundel behoort niet meer tot het geselecteerde adres.");
            return false;
        }
        if (!deviceIds(currentAddress.getCoupledDeviceList())
                .containsAll(selectedDeviceIds)) {
            showError("Een of meer toestellen zijn niet aan dit werfadres gekoppeld.");
            return false;
        }
        List<String> original = bundle.getIdMachine() == null
                ? null : new ArrayList<>(bundle.getIdMachine());
        Set<String> affectedDevices = deviceIds(original);
        affectedDevices.addAll(selectedDeviceIds);

        bundle.setIdMachine(new ArrayList<>(selectedDeviceIds));
        try {
            persistAndSync(
                    Set.of(bundle.getId()), affectedDevices,
                    () -> bundle.setIdMachine(original));
            refreshGrid();
            Notification.show("Toestelkoppelingen opgeslagen.");
            return true;
        } catch (Exception ex) {
            showError("Koppelen mislukt: " + ex.getMessage());
            return false;
        }
    }

    private Button createEditButton(DocumentBundle bundle) {

        Button button = new Button(
                VaadinIcon.EDIT.create()
        );

        button.getElement().setAttribute(
                "aria-label", "Bundel wijzigen"
        );

        button.addClickListener(event -> editBundle(bundle));

        return button;
    }

    private Button createDeleteBundleButton(DocumentBundle bundle) {

        Button button = new Button(VaadinIcon.TRASH.create());
        button.addThemeVariants(
                ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_TERTIARY
        );
        button.getElement().setAttribute(
                "title", "Volledige bundel verwijderen"
        );
        button.getElement().setAttribute(
                "aria-label", "Volledige bundel verwijderen"
        );
        button.addClickListener(event -> confirmDeleteBundle(bundle));
        return button;
    }

    // =====================================================
    // DATA LADEN EN FILTEREN
    // =====================================================

    public void addItemsToGrid(Optional<Address> workAddress) {

        if (uploadInProgress) {
            showError("Wacht tot de huidige bundel is verwerkt voordat je van adres wisselt.");
            return;
        }

        Address nextAddress = workAddress.orElse(null);
        if (currentAddress != nextAddress) {
            // Nog niet geuploade bestanden mogen niet naar een ander adres gaan.
            upload.clearFileList();
            uploadDescription.clear();
            uploadDate.setValue(LocalDate.now());
        }

        currentAddress = nextAddress;
        fileCache.clear();
        grid.deselectAll();
        refreshGrid();
        updateUploadState();
    }

    private void refreshGrid() {

        if (currentAddress == null ||
                currentAddress.getCoupledDocumentBundle() == null) {

            grid.setItems(List.of());
            return;
        }

        String search = descriptionFilter.getValue()
                .toLowerCase(Locale.ROOT)
                .trim();

        String selectedType = typeFilter.getValue();

        List<DocumentBundle> filtered =
                currentAddress.getCoupledDocumentBundle()
                        .stream()
                        .filter(Objects::nonNull)
                        .filter(bundle -> {

                            String description =
                                    bundle.getComment() == null
                                            ? ""
                                            : bundle.getComment()
                                            .toLowerCase(Locale.ROOT);

                            return description.contains(search);
                        })
                        .filter(bundle -> {

                            if ("Foto's".equals(selectedType)) {
                                return files(bundle).stream()
                                        .anyMatch(StoredFile::isImage);
                            }

                            if ("Documenten".equals(selectedType)) {
                                return files(bundle).stream()
                                        .anyMatch(file -> !file.isImage());
                            }

                            return true;
                        })
                        .sorted(Comparator.comparing(
                                DocumentBundle::getDate,
                                Comparator.nullsLast(
                                        Comparator.reverseOrder()
                                )
                        ))
                        .toList();

        grid.setItems(filtered);
    }

    private List<StoredFile> files(DocumentBundle bundle) {

        if (bundle.getIdDocumentList() == null) {
            return List.of();
        }

        return bundle.getIdDocumentList()
                .stream()
                .filter(Objects::nonNull)
                .map(id -> fileCache.computeIfAbsent(
                        id,
                        storage::getInfo
                ))
                .flatMap(Optional::stream)
                .toList();
    }

    private List<ViewerEntry> getAllFiles(boolean images) {

        if (currentAddress == null ||
                currentAddress.getCoupledDocumentBundle() == null) {
            return List.of();
        }

        return currentAddress.getCoupledDocumentBundle()
                .stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(
                        DocumentBundle::getDate,
                        Comparator.nullsLast(
                                Comparator.reverseOrder()
                        )
                ))
                .flatMap(bundle -> files(bundle)
                        .stream()
                        .filter(file -> file.isImage() == images)
                        .map(file -> new ViewerEntry(bundle, file))
                )
                .toList();
    }

    // =====================================================
    // DE TWEEZIJDIGE KOPPELING MET DEVICE
    // Device.coupledImages / coupledDocuments bevatten BUNDEL-ID'S,
    // nooit de GridFS-bestands-ID's.
    // =====================================================

    private Set<String> deviceIds(List<String> ids) {
        if (ids == null) return new LinkedHashSet<>();
        return ids.stream().filter(Objects::nonNull)
                .filter(id -> !id.isBlank())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<String> bundleIds(List<DocumentBundle> bundles) {
        if (bundles == null) return new LinkedHashSet<>();
        return bundles.stream().filter(Objects::nonNull)
                .map(DocumentBundle::getId)
                .filter(Objects::nonNull)
                .filter(id -> !id.isBlank())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private record OriginalLinks(List<String> images, List<String> documents) {}

    /**
     * Slaat eerst het Address op en synchroniseert daarna de betroffen Devices.
     * Bij een fout proberen we zowel de Address als de reeds aangepaste
     * Device-documenten weer terug te zetten (best effort, geen DB-transactie).
     */
    private void persistAndSync(Set<String> affectedBundleIds,
                                Set<String> affectedDeviceIds,
                                Runnable restoreAddressState) {
        Map<String, OriginalLinks> previousDeviceLinks = new LinkedHashMap<>();
        try {
            saveHandler.accept(currentAddress);
            synchronizeDevices(affectedBundleIds, affectedDeviceIds,
                    previousDeviceLinks);
        } catch (Exception originalError) {
            restoreAddressState.run();
            try {
                saveHandler.accept(currentAddress);
            } catch (Exception rollbackError) {
                originalError.addSuppressed(rollbackError);
            }
            // Alleen Devices terugzetten die daadwerkelijk zijn aangeraakt.
            for (Map.Entry<String, OriginalLinks> entry
                    : previousDeviceLinks.entrySet()) {
                deviceService.getDeviceById(entry.getKey()).ifPresent(device -> {
                    try {
                        device.setCoupledImages(entry.getValue().images());
                        device.setCoupledDocuments(entry.getValue().documents());
                        deviceService.save(device);
                    } catch (Exception rollbackError) {
                        originalError.addSuppressed(rollbackError);
                    }
                });
            }
            throw new IllegalStateException(
                    "Bewaren van adres/toestelkoppelingen mislukt. "
                            + "Controleer eventueel gedeeltelijk opgeslagen gegevens.",
                    originalError);
        }
    }

    private void synchronizeDevices(Set<String> affectedBundleIds,
                                    Set<String> affectedDeviceIds,
                                    Map<String, OriginalLinks> before) {
        if (affectedBundleIds.isEmpty() || affectedDeviceIds.isEmpty()) return;
        List<DocumentBundle> bundles = currentAddress.getCoupledDocumentBundle();
        if (bundles == null) bundles = List.of();

        for (String deviceId : affectedDeviceIds) {
            Device device = deviceService.getDeviceById(deviceId).orElse(null);
            if (device == null) {
                throw new IllegalStateException("Toestel niet gevonden: " + deviceId);
            }

            List<String> oldImages = device.getCoupledImages() == null
                    ? null : new ArrayList<>(device.getCoupledImages());
            List<String> oldDocuments = device.getCoupledDocuments() == null
                    ? null : new ArrayList<>(device.getCoupledDocuments());

            LinkedHashSet<String> images = new LinkedHashSet<>(
                    oldImages == null ? List.of() : oldImages);
            LinkedHashSet<String> documents = new LinkedHashSet<>(
                    oldDocuments == null ? List.of() : oldDocuments);

            images.removeAll(affectedBundleIds);
            documents.removeAll(affectedBundleIds);

            for (DocumentBundle bundle : bundles) {
                if (bundle == null || bundle.getId() == null
                        || !affectedBundleIds.contains(bundle.getId())
                        || !deviceIds(bundle.getIdMachine()).contains(deviceId)) {
                    continue;
                }
                // De booleans worden bijgewerkt bij het verwijderen van een file.
                if (Boolean.TRUE.equals(bundle.getImage())) {
                    images.add(bundle.getId());
                }
                if (Boolean.TRUE.equals(bundle.getDocument())) {
                    documents.add(bundle.getId());
                }
            }

            if (!images.equals(new LinkedHashSet<>(
                    oldImages == null ? List.of() : oldImages))
                    || !documents.equals(new LinkedHashSet<>(
                    oldDocuments == null ? List.of() : oldDocuments))) {
                before.put(deviceId, new OriginalLinks(oldImages, oldDocuments));
                device.setCoupledImages(new ArrayList<>(images));
                device.setCoupledDocuments(new ArrayList<>(documents));
                deviceService.save(device);
            }
        }
    }

    // =====================================================
    // ACTIE -> VOEG SAMEN (DE OUDSTE BUNDEL BLIJFT BESTAAN)
    // =====================================================

    private void updateMergeActionState() {
        if (mergeMenuItem != null) {
            mergeMenuItem.setEnabled(
                    currentAddress != null
                            && saveHandler != null
                            && !uploadInProgress
                            && grid.getSelectedItems().size() >= 2
            );
        }
    }

    private void confirmMergeSelectedBundles() {

        if (uploadInProgress) {
            showError("Tijdens een upload kun je geen bundels samenvoegen.");
            return;
        }

        if (currentAddress == null || saveHandler == null) {
            showError("Geen werfadres of opslagfunctie beschikbaar.");
            return;
        }

        List<DocumentBundle> allBundles = currentAddress.getCoupledDocumentBundle();
        if (allBundles == null) {
            showError("Er zijn geen bundels beschikbaar.");
            return;
        }

        List<DocumentBundle> selected = new ArrayList<>(grid.getSelectedItems());
        if (selected.size() < 2) {
            showError("Selecteer minstens twee bundels.");
            return;
        }

        // Controleer of de selectie nog bij het huidige adres hoort.
        if (!allBundles.containsAll(selected)) {
            showError("De selectie is niet meer actueel. Selecteer opnieuw.");
            grid.deselectAll();
            return;
        }

        // De oudste = de vroegste datum. Bij gelijke datum blijft de
        // eerste bundel in de oorspronkelijke adressenlijst bestaan.
        selected.sort(
                Comparator.comparing(
                        DocumentBundle::getDate,
                        Comparator.nullsLast(Comparator.naturalOrder())
                ).thenComparingInt(allBundles::indexOf)
        );

        DocumentBundle oldest = selected.getFirst();
        String date = oldest.getDate() == null
                ? "geen datum"
                : oldest.getDate().format(dateFormatter);
        String description = oldest.getComment() == null
                || oldest.getComment().isBlank()
                ? "Zonder omschrijving"
                : oldest.getComment();

        ConfirmDialog confirmation = new ConfirmDialog();
        confirmation.setHeader("Geselecteerde bundels samenvoegen?");
        confirmation.setText(
                "Aantal geselecteerde bundels: " + selected.size()
                        + "\n\nDeze (oudste) bundel blijft bestaan: "
                        + description + " (" + date + ")."
                        + "\nDe foto's en documenten uit de overige "
                        + (selected.size() - 1) + " bundel(s) worden "
                        + "toegevoegd aan deze bundel."
                        + "\nDe overige bundels verdwijnen uit de tabel."
                        + "\nDe oorspronkelijke bestanden in GridFS blijven bestaan."
        );
        confirmation.setCancelable(true);
        confirmation.setCancelText("Annuleren");
        confirmation.setConfirmText("Samenvoegen");
        confirmation.addConfirmListener(event -> mergeSelectedBundles(selected));
        confirmation.open();
    }

    private void mergeSelectedBundles(List<DocumentBundle> selected) {

        if (uploadInProgress || currentAddress == null || saveHandler == null) {
            showError("Samenvoegen is momenteel niet mogelijk.");
            return;
        }

        List<DocumentBundle> originalBundles = currentAddress.getCoupledDocumentBundle();
        if (originalBundles == null || selected.size() < 2
                || !originalBundles.containsAll(selected)) {
            showError("De geselecteerde bundels zijn niet meer beschikbaar.");
            return;
        }

        // De eerste is de oudste (gesorteerd vóór de bevestiging).
        DocumentBundle oldest = selected.getFirst();

        // Snapshot voor rollback als opslaan mislukt.
        List<String> originalIds = oldest.getIdDocumentList() == null
                ? null : new ArrayList<>(oldest.getIdDocumentList());
        List<String> originalMachines = oldest.getIdMachine() == null
                ? null : new ArrayList<>(oldest.getIdMachine());
        Boolean originalImage = oldest.getImage();
        Boolean originalDocument = oldest.getDocument();
        String originalComment = oldest.getComment();

        // Door LinkedHashSet blijven de volgorde en alle unieke ID's behouden.
        // Bestandsbytes hoeven NIET opnieuw naar GridFS gekopieerd te worden.
        Set<String> mergedIds = new LinkedHashSet<>();
        Set<String> mergedMachines = new LinkedHashSet<>();
        // Omschrijvingen van alle geselecteerde bundels, oudste eerst.
        String mergedDescription = selected.stream()
                .map(DocumentBundle::getComment)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(c -> !c.isBlank())
                .distinct()
                .collect(Collectors.joining("\n"));

        for (DocumentBundle bundle : selected) {
            if (bundle.getIdDocumentList() != null) {
                bundle.getIdDocumentList().stream()
                        .filter(Objects::nonNull)
                        .forEach(mergedIds::add);
            }
            // Bewaar ook machinekoppelingen van de verwijderde bundels.
            if (bundle.getIdMachine() != null) {
                bundle.getIdMachine().stream()
                        .filter(Objects::nonNull)
                        .forEach(mergedMachines::add);
            }
        }

        // Bepaal het type op basis van de samengevoegde bestanden.
        // Bestaande booleans tellen ook mee als er historische metadata ontbreekt.
        boolean hasImages = selected.stream()
                .anyMatch(bundle -> Boolean.TRUE.equals(bundle.getImage()));
        boolean hasDocuments = selected.stream()
                .anyMatch(bundle -> Boolean.TRUE.equals(bundle.getDocument()));

        for (String fileId : mergedIds) {
            Optional<StoredFile> info = fileCache.computeIfAbsent(
                    fileId, storage::getInfo
            );
            if (info.isPresent()) {
                if (info.get().isImage()) {
                    hasImages = true;
                } else {
                    hasDocuments = true;
                }
            }
        }

        // Alleen de oudste bundel blijft in Address.coupledDocumentBundle.
        List<DocumentBundle> updatedBundles = new ArrayList<>(originalBundles);
        updatedBundles.removeIf(bundle -> bundle != oldest && selected.contains(bundle));

        oldest.setIdDocumentList(new ArrayList<>(mergedIds));
        oldest.setIdMachine(new ArrayList<>(mergedMachines));
        oldest.setImage(hasImages);
        oldest.setDocument(hasDocuments);
        oldest.setComment(mergedDescription);
        currentAddress.setCoupledDocumentBundle(updatedBundles);

        try {
            persistAndSync(
                    bundleIds(selected), mergedMachines,
                    () -> {
                        oldest.setIdDocumentList(originalIds);
                        oldest.setIdMachine(originalMachines);
                        oldest.setImage(originalImage);
                        oldest.setDocument(originalDocument);
                        oldest.setComment(originalComment);
                        currentAddress.setCoupledDocumentBundle(originalBundles);
                    });

            grid.deselectAll();
            fileCache.clear();
            refreshGrid();
            updateMergeActionState();

            Notification.show(
                    selected.size() + " bundels samengevoegd tot één bundel ("
                            + mergedIds.size() + " unieke bestanden).",
                    4000,
                    Notification.Position.BOTTOM_START
            );

        } catch (Exception exception) {
            // De lokale wijziging terugdraaien wanneer de save mislukt.
            oldest.setIdDocumentList(originalIds);
            oldest.setIdMachine(originalMachines);
            oldest.setImage(originalImage);
            oldest.setDocument(originalDocument);
            oldest.setComment(originalComment);
            currentAddress.setCoupledDocumentBundle(originalBundles);

            showError("Samenvoegen mislukt: " + exception.getMessage());
        }
    }

    // =====================================================
    // HANDMATIGE UPLOAD: EEN KLIK = EEN DOCUMENTBUNDLE
    // =====================================================

    private void startBundleUpload() {

        if (uploadInProgress) {
            return;
        }

        if (currentAddress == null || saveHandler == null) {
            showError("Geen adres of opslagfunctie geselecteerd.");
            return;
        }

        // Vraag het aantal klaargezette bestanden op uit de client.
        // De echte bestandbytes worden pas na de klik naar de server gestuurd.
        upload.getElement().executeJs("""
                return (this.files || []).filter(file =>
                    !file.complete && !file.error && !file.uploading
                ).length;
                """).then(Integer.class, count -> {

            if (uploadInProgress) {
                return;
            }

            if (count == null || count == 0) {
                Notification.show("Selecteer eerst één of meer bestanden.");
                return;
            }

            if (!pendingFiles.isEmpty()) {
                showError("Er staan nog onafgewerkte bestanden in de wachtrij.");
                return;
            }

            expectedUploadCount = count;
            uploadInProgress = true;
            updateMergeActionState();
            uploadBundleButton.setEnabled(false);
            uploadDescription.setReadOnly(true);
            uploadDate.setReadOnly(true);

            // Vaadin 25 webcomponent: upload nu alle geselecteerde bestanden.
            upload.getElement().callJsFunction("uploadFiles");
        });
    }

    private void finishManualUpload() {

        // Geen upload gestart met de centrale knop: maak geen bundel.
        if (!uploadInProgress) {
            return;
        }

        try {
            if (pendingFiles.size() != expectedUploadCount) {

                List<StoredFile> incomplete = drainPendingFiles();
                cleanupNewFiles(incomplete);
                upload.clearFileList();

                showError("Niet alle bestanden zijn goed ontvangen. " +
                        "Er is geen bundel aangemaakt. Selecteer ze opnieuw.");
                return;
            }

            finishUploadBatch();

        } finally {
            uploadInProgress = false;
            updateMergeActionState();
            expectedUploadCount = 0;
            uploadDescription.setReadOnly(false);
            uploadDate.setReadOnly(false);
            updateUploadState();
        }
    }

    private List<StoredFile> drainPendingFiles() {

        List<StoredFile> result = new ArrayList<>();
        StoredFile file;
        while ((file = pendingFiles.poll()) != null) {
            result.add(file);
        }
        return result;
    }

    private void finishUploadBatch() {

        List<StoredFile> uploaded = drainPendingFiles();
        upload.clearFileList();

        // Geen succesvolle uploads in deze reeks.
        if (uploaded.isEmpty()) {
            return;
        }

        if (currentAddress == null || saveHandler == null) {
            cleanupNewFiles(uploaded);
            showError("Geen adres of opslagfunctie geselecteerd.");
            return;
        }

        String description = uploadDescription.getValue();

        if (description == null || description.isBlank()) {
            description = uploaded.getFirst().filename();
        }

        LocalDate date = uploadDate.getValue();

        if (date == null) {
            date = LocalDate.now();
        }

        boolean hasImages = uploaded.stream()
                .anyMatch(StoredFile::isImage);

        boolean hasDocuments = uploaded.stream()
                .anyMatch(f -> !f.isImage());

        List<String> fileIds = uploaded.stream()
                .map(StoredFile::id)
                .toList();

        // Slechts EEN bundel voor alle geuploade bestanden.
        DocumentBundle newBundle = DocumentBundle.builder()
                .id(UUID.randomUUID().toString())
                .date(date)
                .comment(description.trim())
                .document(hasDocuments)
                .image(hasImages)
                .idDocumentList(new ArrayList<>(fileIds))
                .idMachine(new ArrayList<>())
                .build();

        List<DocumentBundle> originalList =
                currentAddress.getCoupledDocumentBundle();

        List<DocumentBundle> updatedList =
                originalList == null
                        ? new ArrayList<>()
                        : new ArrayList<>(originalList);

        updatedList.add(newBundle);

        currentAddress.setCoupledDocumentBundle(updatedList);

        try {
            // De gewijzigde Address opslaan via CustomerService.
            saveHandler.accept(currentAddress);

            fileCache.clear();
            refreshGrid();

            uploadDescription.clear();
            uploadDate.setValue(LocalDate.now());

            Notification.show(
                    uploaded.size() +
                            " bestand(en) opgeslagen in één bundel.",
                    3500,
                    Notification.Position.BOTTOM_START
            );

        } catch (Exception exception) {

            currentAddress.setCoupledDocumentBundle(originalList);

            cleanupNewFiles(uploaded);

            showError(
                    "Bundel opslaan mislukt: " + exception.getMessage()
            );
        }
    }

    private void cleanupNewFiles(List<StoredFile> uploaded) {

        for (StoredFile file : uploaded) {
            try {
                storage.delete(file.id());
            } catch (Exception exception) {
                // Log een eventueel mislukte cleanup in productie.
            }
        }
    }

    // =====================================================
    // BUNDEL WIJZIGEN: DATUM + OMSCHRIJVING
    // =====================================================

    private void editBundle(DocumentBundle bundle) {

        Dialog dialog = new Dialog();

        dialog.setHeaderTitle("Bundel wijzigen");
        dialog.setWidth("500px");

        DatePicker datePicker = new DatePicker("Datum");

        datePicker.setValue(
                bundle.getDate() == null
                        ? LocalDate.now()
                        : bundle.getDate()
        );

        datePicker.setWidthFull();

        TextArea description = new TextArea("Omschrijving");
        description.setMinRows(4);
        description.setMaxRows(12);

        description.setValue(
                bundle.getComment() == null
                        ? ""
                        : bundle.getComment()
        );

        description.setWidthFull();
        description.setRequired(true);

        Button cancel = new Button(
                "Annuleren",
                event -> dialog.close()
        );

        Button save = new Button("Opslaan");

        save.addClickListener(event -> {

            if (datePicker.getValue() == null ||
                    description.getValue().isBlank()) {

                showError("Vul een datum en omschrijving in.");
                return;
            }

            LocalDate oldDate = bundle.getDate();
            String oldComment = bundle.getComment();

            bundle.setDate(datePicker.getValue());
            bundle.setComment(description.getValue().trim());

            try {
                saveHandler.accept(currentAddress);

                refreshGrid();
                dialog.close();

                Notification.show("Bundel bijgewerkt.");

            } catch (Exception exception) {

                bundle.setDate(oldDate);
                bundle.setComment(oldComment);

                showError(
                        "Opslaan mislukt: " + exception.getMessage()
                );
            }
        });

        HorizontalLayout buttons = new HorizontalLayout(
                cancel,
                save
        );

        VerticalLayout layout = new VerticalLayout(
                datePicker,
                description,
                buttons
        );

        layout.setPadding(false);
        layout.setWidthFull();

        dialog.add(layout);
        dialog.open();
    }

    // =====================================================
    // POPUPVIEWER MET VORIGE / VOLGENDE / VERWIJDEREN
    // =====================================================

    private void openViewer(List<ViewerEntry> selectedFiles) {

        if (selectedFiles.isEmpty()) {
            Notification.show("Geen bestanden gevonden.");
            return;
        }

        List<ViewerEntry> viewerFiles =
                new ArrayList<>(selectedFiles);

        Dialog dialog = new Dialog();
        dialog.setWidth("90vw");
        dialog.setHeight("90vh");

        Span filename = new Span();
        Span counter = new Span();

        VerticalLayout content = new VerticalLayout();

        content.setSizeFull();
        content.setPadding(false);
        content.setAlignItems(FlexComponent.Alignment.CENTER);
        content.getStyle().set("overflow", "auto");

        Button previousButton = new Button(
                VaadinIcon.ARROW_LEFT.create()
        );

        Button nextButton = new Button(
                VaadinIcon.ARROW_RIGHT.create()
        );

        Button deleteButton = new Button(
                VaadinIcon.TRASH.create()
        );

        deleteButton.getElement().setAttribute(
                "title", "Dit bestand verwijderen"
        );

        Button closeButton = new Button(
                VaadinIcon.CLOSE.create(),
                event -> dialog.close()
        );

        int[] currentIndex = {0};

        Runnable[] updateViewer = new Runnable[1];

        updateViewer[0] = () -> {

            if (viewerFiles.isEmpty()) {
                dialog.close();
                return;
            }

            ViewerEntry entry =
                    viewerFiles.get(currentIndex[0]);

            StoredFile currentFile = entry.file();

            filename.setText(currentFile.filename());

            counter.setText(
                    (currentIndex[0] + 1)
                            + " / "
                            + viewerFiles.size()
            );

            previousButton.setEnabled(currentIndex[0] > 0);

            nextButton.setEnabled(
                    currentIndex[0] < viewerFiles.size() - 1
            );

            content.removeAll();

            if (currentFile.isImage()) {

                Image image = new Image(
                        downloadHandler(currentFile),
                        currentFile.filename()
                );

                image.setMaxWidth("100%");
                image.setMaxHeight("70vh");

                content.add(image);

            } else if (currentFile.isPdf()) {

                IFrame pdfViewer = new IFrame(
                        downloadHandler(currentFile)
                );

                pdfViewer.setWidthFull();
                pdfViewer.setHeight("70vh");

                content.add(pdfViewer);

            } else {

                content.add(
                        new Span(
                                "Dit bestandstype kan niet rechtstreeks "
                                        + "worden weergegeven."
                        )
                );
            }

            Anchor download = new Anchor(
                    downloadHandler(currentFile),
                    "Bestand downloaden"
            );

            content.add(download);
        };

        previousButton.addClickListener(event -> {

            currentIndex[0]--;

            updateViewer[0].run();
        });

        nextButton.addClickListener(event -> {

            currentIndex[0]++;

            updateViewer[0].run();
        });

        deleteButton.addClickListener(event -> {

            ViewerEntry entry =
                    viewerFiles.get(currentIndex[0]);

            confirmDelete(entry, () -> {

                viewerFiles.remove(currentIndex[0]);

                if (currentIndex[0] >= viewerFiles.size()) {
                    currentIndex[0] =
                            Math.max(0, viewerFiles.size() - 1);
                }

                updateViewer[0].run();
            });
        });

        HorizontalLayout navigation = new HorizontalLayout(
                previousButton,
                counter,
                nextButton,
                deleteButton
        );

        navigation.setAlignItems(FlexComponent.Alignment.CENTER);

        HorizontalLayout header = new HorizontalLayout(
                filename,
                navigation,
                closeButton
        );

        header.setWidthFull();
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.expand(filename);

        VerticalLayout layout = new VerticalLayout(
                header,
                content
        );

        layout.setSizeFull();
        layout.setPadding(false);

        dialog.add(layout);

        updateViewer[0].run();

        dialog.open();
    }

    // =====================================================
    // EEN VOLLEDIGE BUNDEL VERWIJDEREN (NA BEVESTIGING)
    // =====================================================

    private void confirmDeleteBundle(DocumentBundle bundle) {

        if (uploadInProgress) {
            showError("Tijdens een upload kun je geen bundel verwijderen.");
            return;
        }

        int numberOfFiles = bundle.getIdDocumentList() == null
                ? 0 : bundle.getIdDocumentList().size();

        String description = bundle.getComment() == null
                ? "Zonder omschrijving" : bundle.getComment();

        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Volledige documentbundel verwijderen?");
        dialog.setText(
                "Bundel: " + description + "\n" +
                        "Aantal bestanden: " + numberOfFiles + "\n\n" +
                        "Deze actie kan niet ongedaan worden gemaakt."
        );
        dialog.setCancelable(true);
        dialog.setCancelText("Annuleren");
        dialog.setConfirmText("Verwijderen");
        dialog.setConfirmButtonTheme("error primary");
        dialog.addConfirmListener(event -> deleteBundle(bundle));
        dialog.open();
    }

    private void deleteBundle(DocumentBundle bundle) {

        if (currentAddress == null || saveHandler == null) {
            showError("Geen adres of opslagfunctie beschikbaar.");
            return;
        }

        List<DocumentBundle> original = currentAddress.getCoupledDocumentBundle();
        if (original == null || !original.contains(bundle)) {
            showError("De bundel is niet meer aanwezig.");
            return;
        }

        List<DocumentBundle> updated = new ArrayList<>(original);
        if (!updated.remove(bundle)) {
            return;
        }

        List<String> fileIds = bundle.getIdDocumentList() == null
                ? List.of()
                : new ArrayList<>(bundle.getIdDocumentList());

        // Bewaar eerst de Customer/Address zonder deze bundel.
        currentAddress.setCoupledDocumentBundle(updated);

        try {
            Set<String> affectedIds = bundleIds(List.of(bundle));
            Set<String> affectedDevices = deviceIds(bundle.getIdMachine());
            persistAndSync(affectedIds, affectedDevices,
                    () -> currentAddress.setCoupledDocumentBundle(original));
        } catch (Exception exception) {
            showError("Bundel verwijderen mislukt: " + exception.getMessage());
            return;
        }

        fileCache.clear();
        refreshGrid();

        // Ruim alleen op als geen andere bundel op dit adres dezelfde ID gebruikt.
        // Worden GridFS-bestanden ook tussen verschillende adressen gedeeld,
        // controleer dan alle adressen alvorens het binaire bestand te verwijderen.
        boolean cleanupFailed = false;
        for (String id : fileIds.stream().filter(Objects::nonNull).distinct().toList()) {
            boolean stillUsed = updated.stream()
                    .filter(Objects::nonNull)
                    .anyMatch(other -> other.getIdDocumentList() != null
                            && other.getIdDocumentList().contains(id));

            if (!stillUsed) {
                try {
                    storage.delete(id);
                } catch (Exception exception) {
                    cleanupFailed = true;
                }
            }
        }

        if (cleanupFailed) {
            showError("Bundel verwijderd, maar GridFS-opruiming is " +
                    "niet volledig gelukt.");
        } else {
            Notification.show("Documentbundel verwijderd.");
        }
    }

    // =====================================================
    // BESTAND VERWIJDEREN UIT EEN BUNDEL
    // =====================================================

    private void confirmDelete(
            ViewerEntry entry,
            Runnable afterDelete
    ) {

        Dialog confirmDialog = new Dialog();

        confirmDialog.setHeaderTitle("Bestand verwijderen");

        Span message = new Span(
                "Ben je zeker dat je '" +
                        entry.file().filename() +
                        "' wilt verwijderen?"
        );

        Button cancel = new Button(
                "Annuleren",
                event -> confirmDialog.close()
        );

        Button delete = new Button(
                "Verwijderen",
                event -> {

                    if (deleteFileFromBundle(entry)) {
                        confirmDialog.close();
                        afterDelete.run();
                    }
                }
        );

        confirmDialog.add(
                new VerticalLayout(
                        message,
                        new HorizontalLayout(cancel, delete)
                )
        );

        confirmDialog.open();
    }

    private boolean deleteFileFromBundle(ViewerEntry entry) {

        if (currentAddress == null || saveHandler == null) {
            showError("Geen opslagfunctie beschikbaar.");
            return false;
        }

        DocumentBundle bundle = entry.bundle();
        String fileId = entry.file().id();

        List<String> oldIds =
                bundle.getIdDocumentList() == null
                        ? List.of()
                        : new ArrayList<>(bundle.getIdDocumentList());

        List<String> newIds = new ArrayList<>(oldIds);

        if (!newIds.remove(fileId)) {
            showError("Bestand zit niet meer in deze bundel.");
            return false;
        }

        Boolean oldImage = bundle.getImage();
        Boolean oldDocument = bundle.getDocument();

        List<DocumentBundle> oldBundles =
                currentAddress.getCoupledDocumentBundle();

        List<DocumentBundle> newBundles =
                new ArrayList<>(oldBundles);

        bundle.setIdDocumentList(newIds);

        if (newIds.isEmpty()) {

            // Geen bestanden meer: verwijder de lege bundel.
            newBundles.remove(bundle);

        } else {

            // Bereken document/image opnieuw op basis van
            // de resterende bestanden.
            boolean hasImage = files(bundle).stream()
                    .anyMatch(StoredFile::isImage);

            boolean hasDocument = files(bundle).stream()
                    .anyMatch(file -> !file.isImage());

            bundle.setImage(hasImage);
            bundle.setDocument(hasDocument);
        }

        currentAddress.setCoupledDocumentBundle(newBundles);

        try {
            Set<String> affectedIds = bundleIds(List.of(bundle));
            Set<String> affectedDevices = deviceIds(bundle.getIdMachine());
            persistAndSync(affectedIds, affectedDevices, () -> {
                bundle.setIdDocumentList(new ArrayList<>(oldIds));
                bundle.setImage(oldImage);
                bundle.setDocument(oldDocument);
                currentAddress.setCoupledDocumentBundle(oldBundles);
            });
        } catch (Exception exception) {
            showError("Verwijderen mislukt: " + exception.getMessage());
            return false;
        }

        fileCache.remove(fileId);

        refreshGrid();

        /*
         * Verwijder het GridFS-bestand alleen als geen enkele
         * andere bundel van dit adres er nog naar verwijst.
         */
        boolean stillReferenced =
                newBundles.stream()
                        .filter(Objects::nonNull)
                        .anyMatch(other ->
                                other.getIdDocumentList() != null &&
                                        other.getIdDocumentList()
                                                .contains(fileId)
                        );

        if (!stillReferenced) {

            try {
                storage.delete(fileId);
            } catch (Exception exception) {

                // De koppeling is wel verwijderd, maar GridFS
                // bevat mogelijk nog een verweesd bestand.
                showError(
                        "Koppeling verwijderd, maar GridFS-opruiming "
                                + "mislukt: " + exception.getMessage()
                );

                return true;
            }
        }

        Notification.show("Bestand verwijderd.");

        return true;
    }

    // =====================================================
    // DOWNLOAD
    // =====================================================

    private DownloadHandler downloadHandler(StoredFile file) {

        return DownloadHandler.fromInputStream(event -> {

            try {

                return new DownloadResponse(
                        storage.openStream(file.id()),
                        file.filename(),
                        file.contentType(),
                        file.length()
                );

            } catch (Exception exception) {

                return DownloadResponse.error(404);
            }
        });
    }

    // =====================================================
    // INSTELLINGEN
    // =====================================================

    public void setSaveHandler(Consumer<Address> saveHandler) {

        this.saveHandler = saveHandler;

        updateUploadState();
    }

    private void updateUploadState() {

        boolean ready = currentAddress != null && saveHandler != null;
        upload.setEnabled(ready);
        uploadBundleButton.setEnabled(ready && !uploadInProgress);
        updateMergeActionState();
    }

    public void setAuthorisation(UserFunction userFunction) {
        // Koppel hier je daadwerkelijke gebruikersrechten.
    }

    private void showError(String message) {

        Notification notification = Notification.show(
                message,
                5000,
                Notification.Position.MIDDLE
        );

        notification.addThemeVariants(
                NotificationVariant.ERROR
        );
    }
}
