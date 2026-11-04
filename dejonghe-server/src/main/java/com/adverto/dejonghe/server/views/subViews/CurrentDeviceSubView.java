
package com.adverto.dejonghe.server.views.subViews;

import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.dbservices.DeviceService;
import com.adverto.dejonghe.common.entities.customers.Address;
import com.adverto.dejonghe.common.entities.customers.Customer;
import com.adverto.dejonghe.common.entities.installation.Device;
import com.adverto.dejonghe.common.entities.images.DocumentBundle;
import com.adverto.dejonghe.common.services.DocumentFileStorageService;
import com.adverto.dejonghe.common.services.DocumentFileStorageService.StoredFile;
import com.adverto.dejonghe.common.entities.invoice.Invoice;
import com.adverto.dejonghe.common.services.ProductServices;
import com.adverto.dejonghe.server.views.workorder.FinishedWorkorderView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.server.streams.DownloadHandler;
import com.vaadin.flow.server.streams.DownloadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import software.xdev.vaadin.daterange_picker.business.DateRangeModel;
import software.xdev.vaadin.daterange_picker.business.SimpleDateRanges;
import software.xdev.vaadin.daterange_picker.ui.DateRangePicker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.adverto.dejonghe.server.views.subViews.CurrentInvoiceSubView.DATERANGE_VALUES;
import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;

@Component
@Scope("prototype")
public class CurrentDeviceSubView extends VerticalLayout {

    private final ProductServices productServices;
    private final CustomerService customerService;
    DeviceService deviceService;
    private final DocumentFileStorageService documentStorage;
    private final Map<String, Optional<StoredFile>> documentInfoCache = new HashMap<>();

    private record DeviceFileEntry(DocumentBundle bundle, StoredFile file) {}

    private Grid.Column<Device> cameraColumn;
    private Grid.Column<Device> documentsColumn;

    Grid<Device> deviceGrid;
    HeaderRow headerRow;

    Grid.Column<Device> columnType;
    Grid.Column<Device> columnCode;
    Grid.Column<Device> columnName;
    Grid.Column<Device> columnSerialNumber;
    Grid.Column<Device> columntComment;
    Grid.Column<Device> columnDate;
    Grid.Column<Device> columnInvoiceNumber;

    ComboBox<String> typeFilter;
    TextField codeFilter;
    TextField nameFilter;
    TextField serialNumberFilter;
    TextField commentFilter;
    DateRangePicker dateRangePicker;
    TextField invoiceNumberFilter;
    Button clearFilterButton;

    ComboBox<String> cbType;
    TextField tfProductCode;
    TextField tfProductName;
    TextField tfProductSerialNumber;
    TextField tfComment;
    DatePicker datePicker;
    TextField tfInvoiceNumber;

    Customer selectedCustomer;
    Address selectedWorkAddress;
    Device selectedDevice;


    Notification deleteDeviceNotification;

    ListDataProvider<Device> dataProvider;
    Set<Device>deviceListToRemove;

    private Binder<Device> deviceBinder;
    Editor<Device> editor;

    Button dateRangeButton;
    Dialog dateRangeDialog;
    Button cancelButton;
    Button searchButton;


    @Autowired
    public CurrentDeviceSubView(DeviceService deviceService,
                                ProductServices productServices,
                                CustomerService customerService,
                                DocumentFileStorageService documentStorage)
    {
        this.deviceService = deviceService;
        this.productServices = productServices;
        this.customerService = customerService;
        this.documentStorage = documentStorage;

        setUpfilters();
        createReportDelete();
        setUpDateRangeButton();
        this.add(setUpGrid());
        setUpBinder();
        this.getStyle()
                .set("border-radius", "16px")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.9)");
    }


    private void setUpDateRangeButton() {
        cancelButton = new Button("Cancel");
        cancelButton.setWidth("100%");
        cancelButton.addClickListener(e -> {
            dateRangeDialog.close();
        });
        searchButton = new Button("Zoek");
        searchButton.setWidth("100%");
        searchButton.addClickListener(e -> {
            dateRangeDialog.close();
        });
        dateRangeDialog = new Dialog();
        dateRangeDialog.add(dateRangePicker);
        dateRangeDialog.add(searchButton);
        dateRangeDialog.add(cancelButton);
        dateRangeDialog.setWidth("30%");
        dateRangeDialog.setWidth("30%");
        dateRangeButton = new Button(VaadinIcon.CALENDAR.create());
        dateRangeButton.addClickListener(e -> {
            dateRangeDialog.open();
        });

    }


    private void setUpfilters() {

        clearFilterButton = new Button(VaadinIcon.CLOSE.create());

        typeFilter = new ComboBox<>();
        typeFilter.setWidth("100%");
        typeFilter.setItems(productServices.getDeviceTypesBasedOnFolders());

        codeFilter = new TextField();
        codeFilter.setWidth("100%");
        codeFilter.setPlaceholder("Code");

        nameFilter = new TextField();
        nameFilter.setWidth("100%");
        nameFilter.setPlaceholder("Naam");

        serialNumberFilter = new TextField();
        serialNumberFilter.setWidth("100%");
        serialNumberFilter.setPlaceholder("Serienummer");

        commentFilter = new TextField();
        commentFilter.setWidth("100%");
        commentFilter.setPlaceholder("Commentaar");

        invoiceNumberFilter = new TextField();
        invoiceNumberFilter.setWidth("100%");
        invoiceNumberFilter.setPlaceholder("Factuurnummer");

        dateRangePicker = new DateRangePicker<>(
                () -> new DateRangeModel<>(LocalDate.now().withDayOfYear(1), LocalDate.now(), SimpleDateRanges.YEAR),
                DATERANGE_VALUES)
                .withDatePickerI18n(getDatePickerI18n())
                .withDateRangeLocalizerFunction(dr -> {
                    if(dr == SimpleDateRanges.TODAY)
                    {
                        return "Vandaag";
                    }
                    else if(dr == SimpleDateRanges.DAY)
                    {
                        return "Dag";
                    }
                    else if(dr == SimpleDateRanges.WEEK)
                    {
                        return "Week";
                    }
                    else if(dr == SimpleDateRanges.MONTH)
                    {
                        return "Maand";
                    }
                    else if(dr == SimpleDateRanges.QUARTER)
                    {
                        return "Kwartaal";
                    }
                    else if(dr == SimpleDateRanges.HALF_YEAR)
                    {
                        return "Half jaar";
                    }
                    else if(dr == SimpleDateRanges.YEAR)
                    {
                        return "Jaar";
                    }
                    else if(dr == SimpleDateRanges.FREE)
                    {
                        return "Vrij";
                    }

                    return "?";
                })
                .withStartLabel("Start")
                .withEndLabel("Einde")
                .withDateRangeOptionsLabel("Periode");

        dateRangePicker.addValueChangeListener(event -> {
            addItemsToGridFromFilter();
        });

        invoiceNumberFilter.addValueChangeListener(event -> {
            addItemsToGridFromFilter();
        });

        commentFilter.addValueChangeListener(event -> {
            addItemsToGridFromFilter();
        });

        serialNumberFilter.addValueChangeListener(event -> {
            addItemsToGridFromFilter();
        });

        nameFilter.addValueChangeListener(event -> {
            addItemsToGridFromFilter();
        });


        codeFilter.addValueChangeListener(event -> {
            addItemsToGridFromFilter();
        });

        typeFilter.addValueChangeListener(event -> {
            addItemsToGridFromFilter();
        });

        clearFilterButton.addClickListener(e -> {
            typeFilter.setValue(typeFilter.getEmptyValue());
            codeFilter.setValue("");
            nameFilter.setValue("");
            serialNumberFilter.setValue("");
            commentFilter.setValue("");
            invoiceNumberFilter.setValue("");
            dateRangePicker.setStart(LocalDate.now().withDayOfYear(1));
            dateRangePicker.setEnd(LocalDate.now());
        });
    }

    private void addItemsToGridFromFilter() {
        dataProvider.clearFilters();
        dataProvider.addFilter(item -> {

            boolean typeOk = true;
            boolean codeOk = true;
            boolean nameOk = true;
            boolean serialNumberOk = true;
            boolean commentOk = true;
            boolean dateOk = true;
            boolean invoiceNumberOk = true;

            if((typeFilter.getValue() != null) && (!typeFilter.getValue().isEmpty())){
                typeOk = item.getType().toString().toLowerCase().contains(typeFilter.getValue().toLowerCase());
            }

            if(!codeFilter.getValue().isEmpty()){
                codeOk = item.getCode().toString().toLowerCase().contains(codeFilter.getValue().toLowerCase());
            }

            if(!nameFilter.getValue().isEmpty()){
                nameOk = item.getDeviceName().toString().toLowerCase().contains(nameFilter.getValue().toLowerCase());
            }

            if(!serialNumberFilter.getValue().isEmpty()){
                serialNumberOk = item.getSerialNumber().toString().toLowerCase().contains(serialNumberFilter.getValue().toLowerCase());
            }

            if(!commentFilter.getValue().isEmpty()){
                commentOk = item.getComment().toString().toLowerCase().contains(commentFilter.getValue().toLowerCase());
            }

            if(dateRangePicker.getValue() != null){
                dateOk =
                        (item.getDate().isEqual(dateRangePicker.getStart()) || item.getDate().isAfter(dateRangePicker.getStart())) &&
                                (item.getDate().isEqual(dateRangePicker.getEnd())   || item.getDate().isBefore(dateRangePicker.getEnd()));
            }

            if(!invoiceNumberFilter.getValue().isEmpty()){
                invoiceNumberOk = item.getInvoiceNumber().toString().toLowerCase().contains(invoiceNumberFilter.getValue().toLowerCase());
            }

            return typeOk && codeOk && nameOk && serialNumberOk && commentOk && dateOk && invoiceNumberOk;
        });

        dataProvider.refreshAll();
    }

    public static DatePicker.DatePickerI18n getDatePickerI18n()
    {
        final DatePicker.DatePickerI18n datepicker = new DatePicker.DatePickerI18n();
        datepicker.setFirstDayOfWeek(1);

        datepicker.setCancel("Abbrechen");
        datepicker.setToday("Heute");

//        datepicker.setMonthNames(MONTHS);
//        datepicker.setWeekdays(WEEKDAYS);
//        datepicker.setWeekdaysShort(WEEKDAYS_SHORT);

        return datepicker;
    }

    private Grid<Device> setUpGrid() {
        deviceGrid = new Grid<>();
        deviceGrid.addClassName("rounded-tree");
        deviceGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        deviceGrid.addClassName("my-bold-footer");
        deviceGrid.appendFooterRow();
        cameraColumn = deviceGrid.addComponentColumn(device ->
                        createFilesButton(device, true))
                .setFlexGrow(0)
                .setHeader(new Icon(VaadinIcon.CAMERA))
                .setTextAlign(ColumnTextAlign.CENTER);
        cameraColumn.addClassName("center-header");
        cameraColumn.setWidth("70px");

        documentsColumn = deviceGrid.addComponentColumn(device ->
                        createFilesButton(device, false))
                .setFlexGrow(0)
                .setHeader(new Icon(VaadinIcon.FILE_FONT))
                .setTextAlign(ColumnTextAlign.CENTER);
        documentsColumn.addClassName("center-header");
        documentsColumn.getElement().getThemeList().add("no-min-width");
        documentsColumn.setWidth("70px");

        columnType = deviceGrid.addColumn(device -> {
            if(device.getType() != null){
                return device.getType();
            }
            else{
                return "";
            }
        }).setHeader("Type").setFlexGrow(1);

        columnCode = deviceGrid.addColumn(device -> {
            if((device.getCode() != null)){
                return device.getCode();
            }
            else{
                return "";
            }
        }).setHeader("Code").setFlexGrow(1).setSortable(true);

        columnName = deviceGrid.addColumn(device -> {
            if((device.getDeviceName() != null)){
                return device.getDeviceName();
            }
            else{
                return "";
            }
        }).setHeader("Naam").setFlexGrow(2).setSortable(true);

        columnSerialNumber = deviceGrid.addColumn(device -> {
            if((device.getSerialNumber() != null)){
                return device.getSerialNumber();
            }
            else{
                return "";
            }
        }).setHeader("Serienummer").setFlexGrow(1).setSortable(true);

        columntComment = deviceGrid.addColumn(device -> {
            if((device.getComment() != null)){
                return device.getComment();
            }
            else{
                return "";
            }
        }).setHeader("Commentaar").setFlexGrow(2).setSortable(true);

        columnDate = deviceGrid.addColumn(device -> {
            if((device.getDate() != null)){
                return device.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            }
            else{
                return "";
            }
        }).setHeader("Datum").setFlexGrow(1).setSortable(true);

        columnInvoiceNumber = deviceGrid.addColumn(device -> {
            if((device.getInvoiceNumber() != null)){
                return device.getInvoiceNumber();
            }
            else{
                return "";
            }
        }).setHeader("Factuurnummer").setFlexGrow(1).setSortable(true);


        deviceGrid.sort(GridSortOrder.desc(columnType).build());

        deviceGrid.addItemClickListener(event -> {

            if (event.getColumn() == cameraColumn ||
                    event.getColumn() == documentsColumn) {
                return;
            }

            selectedDevice = event.getItem();

            editor.cancel();
            editor.editItem(event.getItem());
        });

        headerRow = deviceGrid.appendHeaderRow();
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.add(clearFilterButton,typeFilter);
        headerRow.getCell(columnType).setComponent(headerLayout);
        headerRow.getCell(columnCode).setComponent(codeFilter);
        headerRow.getCell(columnName).setComponent(nameFilter);
        headerRow.getCell(columnSerialNumber).setComponent(serialNumberFilter);
        headerRow.getCell(columntComment).setComponent(commentFilter);
        //headerRow.getCell(columnDate).setComponent(dateRangePicker);
        headerRow.getCell(columnInvoiceNumber).setComponent(invoiceNumberFilter);

        deviceGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        deviceGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        deviceGrid.addThemeVariants(GridVariant.LUMO_COMPACT);

        return deviceGrid;
    }

    // =====================================================
    // DOCUMENTBUNDELS VAN HET GESELECTEERDE ADRES
    // De Device zelf bevat geen gekoppelde foto-/document-ID's meer.
    // =====================================================

    private List<DeviceFileEntry> filesForDevice(Device device, boolean images) {
        if (device == null || device.getId() == null
                || selectedWorkAddress == null
                || selectedWorkAddress.getCoupledDocumentBundle() == null) {
            return List.of();
        }

        return selectedWorkAddress.getCoupledDocumentBundle().stream()
                .filter(Objects::nonNull)
                .filter(bundle -> bundle.getIdMachine() != null
                        && bundle.getIdMachine().contains(device.getId()))
                .filter(bundle -> bundle.getIdDocumentList() != null)
                .flatMap(bundle -> bundle.getIdDocumentList().stream()
                        .filter(Objects::nonNull)
                        .map(fileId -> documentInfoCache.computeIfAbsent(
                                fileId, documentStorage::getInfo))
                        .flatMap(Optional::stream)
                        .filter(file -> file.isImage() == images)
                        .map(file -> new DeviceFileEntry(bundle, file)))
                .toList();
    }

    private com.vaadin.flow.component.Component createFilesButton(
            Device device, boolean images) {
        List<DeviceFileEntry> entries = filesForDevice(device, images);
        if (entries.isEmpty()) {
            return new Span("");
        }

        Button button = new Button(images
                ? VaadinIcon.CAMERA.create()
                : VaadinIcon.FILE_FONT.create());
        button.addThemeVariants(LUMO_TERTIARY_INLINE);
        button.getElement().setAttribute("title",
                images ? "Foto's bekijken" : "Documenten bekijken");
        button.getElement().setAttribute("aria-label",
                images ? "Foto's bekijken" : "Documenten bekijken");
        button.addClickListener(event -> openDeviceFiles(device, images));
        return button;
    }

    private void openDeviceFiles(Device device, boolean images) {
        // Opnieuw ophalen: bundels kunnen sinds het tekenen van de Grid veranderd zijn.
        List<DeviceFileEntry> entries = new ArrayList<>(filesForDevice(device, images));
        if (entries.isEmpty()) {
            Notification.show("Geen bestanden gevonden.");
            return;
        }

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle(images ? "Foto's van toestel" : "Documenten van toestel");
        dialog.setWidth("90vw");
        dialog.setHeight("90vh");

        Span filename = new Span();
        Span counter = new Span();
        VerticalLayout preview = new VerticalLayout();
        preview.setSizeFull();
        preview.setPadding(false);
        preview.setAlignItems(Alignment.CENTER);
        preview.getStyle().set("overflow", "auto");

        Button previous = new Button(VaadinIcon.ARROW_LEFT.create());
        Button next = new Button(VaadinIcon.ARROW_RIGHT.create());
        Button close = new Button("Sluiten", event -> dialog.close());
        int[] position = {0};

        Runnable updatePreview = () -> {
            DeviceFileEntry entry = entries.get(position[0]);
            StoredFile file = entry.file();
            filename.setText(file.filename());
            counter.setText((position[0] + 1) + " / " + entries.size());
            previous.setEnabled(position[0] > 0);
            next.setEnabled(position[0] < entries.size() - 1);
            preview.removeAll();

            if (file.isImage()) {
                Image image = new Image(downloadHandler(file), file.filename());
                image.setMaxWidth("100%");
                image.setMaxHeight("70vh");
                preview.add(image);
            } else if (file.isPdf()) {
                IFrame pdf = new IFrame(downloadHandler(file));
                pdf.setWidthFull();
                pdf.setHeight("70vh");
                preview.add(pdf);
            } else {
                preview.add(new Span(
                        "Dit bestandstype kan niet rechtstreeks worden weergegeven."));
            }

            preview.add(new Anchor(downloadHandler(file), "Bestand downloaden"));
        };

        previous.addClickListener(event -> {
            position[0]--;
            updatePreview.run();
        });
        next.addClickListener(event -> {
            position[0]++;
            updatePreview.run();
        });

        HorizontalLayout controls = new HorizontalLayout(
                previous, counter, next, close);
        controls.setAlignItems(Alignment.CENTER);
        HorizontalLayout heading = new HorizontalLayout(filename, controls);
        heading.setWidthFull();
        heading.setAlignItems(Alignment.CENTER);
        heading.expand(filename);
        VerticalLayout layout = new VerticalLayout(heading, preview);
        layout.setSizeFull();
        layout.setPadding(false);
        dialog.add(layout);
        updatePreview.run();
        dialog.open();
    }

    private DownloadHandler downloadHandler(StoredFile file) {
        return DownloadHandler.fromInputStream(event -> {
            try {
                return new DownloadResponse(
                        documentStorage.openStream(file.id()),
                        file.filename(), file.contentType(), file.length());
            } catch (Exception exception) {
                return DownloadResponse.error(404);
            }
        });
    }

    private void setUpBinder() {
        deviceBinder = new Binder<>(Device.class);
        editor = deviceGrid.getEditor();
        editor.setBuffered(true);
        editor.setBinder(deviceBinder);

        cbType = new ComboBox();
        cbType.setItems(productServices.getDeviceTypesBasedOnFolders());
        cbType.setWidthFull();
        tfProductCode = new TextField();
        tfProductCode.setWidthFull();
        tfProductName = new TextField();
        tfProductName.setWidthFull();
        tfProductSerialNumber = new TextField();
        tfProductSerialNumber.setWidthFull();
        tfComment = new TextField();
        tfComment.setWidthFull();
        datePicker = new DatePicker();
        datePicker.setWidthFull();
        tfInvoiceNumber = new TextField();
        tfInvoiceNumber.setWidthFull();

        deviceBinder.forField(cbType)
                .withNullRepresentation("")
                .bind(Device::getType, Device::setType);
        columnType.setEditorComponent(cbType);

        deviceBinder.forField(tfProductCode)
                .withNullRepresentation("")
                .bind(Device::getCode, Device::setCode);
        columnCode.setEditorComponent(tfProductCode);

        deviceBinder.forField(tfProductName)
                .withNullRepresentation("")
                .bind(Device::getDeviceName, Device::setDeviceName);
        columnName.setEditorComponent(tfProductName);

        deviceBinder.forField(tfProductSerialNumber)
                .withNullRepresentation("")
                .bind(Device::getSerialNumber, Device::setSerialNumber);
        columnSerialNumber.setEditorComponent(tfProductSerialNumber);

        deviceBinder.forField(tfComment)
                .withNullRepresentation("")
                .bind(Device::getComment, Device::setComment);
        columntComment.setEditorComponent(tfComment);

        deviceBinder.forField(datePicker)
                .bind(Device::getDate, Device::setDate);
        columnDate.setEditorComponent(datePicker);

        deviceBinder.forField(tfInvoiceNumber)
                .withNullRepresentation("")
                .bind(Device::getInvoiceNumber, Device::setInvoiceNumber);
        columnInvoiceNumber.setEditorComponent(tfInvoiceNumber);

        deviceBinder.addValueChangeListener(valueChangeEvent -> {
                    try {
                        deviceBinder.writeBean(selectedDevice);
                    } catch (ValidationException e) {
                        throw new RuntimeException(e);
                    }
                    deviceService.save(selectedDevice);
                    Notification.show("Toestel is gewijzigd");
                }
        );
    }


    private com.vaadin.flow.component.Component getActionInvoiceButton(Invoice item) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(false);
        horizontalLayout.setPadding(false);
        horizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        MenuBar actionBar = new MenuBar();
        actionBar.addClassName("large-menubar");
        actionBar.addThemeVariants(MenuBarVariant.LUMO_DROPDOWN_INDICATORS);
        MenuItem actie = actionBar.addItem("Actie".toUpperCase());
        actie.getElement().getClassList().add("menu-as-button");


        horizontalLayout.add(actionBar);
        return horizontalLayout;
    }

    private ComponentEventListener<ClickEvent<MenuItem>> openWorkOrdersForInvoice(Invoice item) {
        return  (event) -> {
            Notification.show("Open werkbonnen");
            Map<String, String> params = Map.of(
                    "invoiceId", item.getId()
            );
            UI.getCurrent().navigate(
                    FinishedWorkorderView.class,
                    QueryParameters.simple(params)
            );
        };
    }


    public void addItemsToDeviceGrid(List<Device>deviceList){
        dataProvider = new ListDataProvider<>(deviceList);
        deviceGrid.setDataProvider(dataProvider);
    }


    public Notification createReportDelete() {
        deleteDeviceNotification = new Notification();
        deleteDeviceNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Icon icon = VaadinIcon.WARNING.create();
        Button retryBtn = new Button("Annuleer",
                clickEvent -> deleteDeviceNotification.close());
        retryBtn.getStyle().setMargin("0 0 0 var(--lumo-space-l)");

        var layout = new HorizontalLayout(icon,
                new Text("Ben je zeker dat je geselecteede toestel(len) wilt wissen?"), retryBtn,
                createCloseBtn(deleteDeviceNotification));
        layout.setAlignItems(Alignment.CENTER);

        deleteDeviceNotification.add(layout);

        return deleteDeviceNotification;
    }



    public Button createCloseBtn(Notification notification) {
        Button removeBtn = new Button(VaadinIcon.TRASH.create(),
                clickEvent -> {
                    if((deviceListToRemove != null) && (!deviceListToRemove.isEmpty())){
                        try{
                            for(Device device: deviceListToRemove){
                                dataProvider.getItems()
                                        .removeIf(i -> i.getId().equals(device.getId()));
                            }
                            deviceGrid.getDataProvider().refreshAll();
                        }
                        catch(Exception e){
                        }
                        for(Device device: deviceListToRemove){
                            deviceService.deleteById(device.getId());
                            //remove id in WorkAddres
                            selectedWorkAddress.getCoupledDeviceList().remove(device.getId());
                            customerService.save(selectedCustomer);
                        }
                        Notification.show("Geselecteerde toestel(len) zijn verwijderd");
                    }
                    else{
                        Notification.show("Geen toestellen te verwijderen");
                    }
                    notification.close();
                });
        removeBtn.addThemeVariants(LUMO_TERTIARY_INLINE);
        return removeBtn;
    }

    public void showRemoveNotification() {
        if(deviceGrid.getSelectedItems().size() >= 0){
            deviceListToRemove = deviceGrid.getSelectedItems();
            deleteDeviceNotification.open();
        }
        else{
            Notification.show("Gelieve minimum 1 werkbon te selecteren om te verwijderen");
        }
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public void setselectedWorkAddress(Address selectedWorkAddress) {
        this.selectedWorkAddress = selectedWorkAddress;
        refreshDeviceDocumentIcons();
    }

    public void refreshDeviceDocumentIcons() {
        documentInfoCache.clear();
        if (dataProvider != null) {
            dataProvider.refreshAll();
        }
    }
}