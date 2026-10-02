package com.adverto.dejonghe.server.views.invoice;

import com.adverto.dejonghe.common.dbservices.WorkOrderService;
import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrder;
import com.adverto.dejonghe.common.services.WorkOrderPdfServices;
import com.adverto.dejonghe.server.Controllers.PdfController;
import com.adverto.dejonghe.server.customEvents.AddProductEventListener;
import com.adverto.dejonghe.server.customEvents.AddRemoveProductEvent;
import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.dbservices.InvoiceService;
import com.adverto.dejonghe.common.dbservices.ProductService;
import com.adverto.dejonghe.common.entities.customers.Address;
import com.adverto.dejonghe.common.entities.customers.Customer;
import com.adverto.dejonghe.common.entities.enums.employee.UserFunction;
import com.adverto.dejonghe.common.entities.enums.product.VAT;
import com.adverto.dejonghe.common.entities.invoice.Invoice;
import com.adverto.dejonghe.common.entities.product.product.Product;
import com.adverto.dejonghe.server.services.invoice.InvoiceServices;
import com.adverto.dejonghe.server.views.subViews.SearchCustomerSubView;
import com.adverto.dejonghe.server.views.subViews.SelectProductSubView;
import com.adverto.dejonghe.server.views.subViews.ShowImageSubVieuw;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.*;
import com.vaadin.flow.shared.Registration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Route("eindfacturatie/:id")
@Menu(order = 0, icon = LineAwesomeIconUrl.EURO_SIGN_SOLID)
public class NewInvoiceView extends VerticalLayout implements HasUrlParameter<String>,HasDynamicTitle {

    private final InvoiceService invoiceService;
    private final WorkOrderService workOrderService;
    ProductService productService;
    SelectProductSubView selectProductSubView;
    SearchCustomerSubView searchCustomerSubView;
    CustomerService customerService;
    GridFsTemplate gridFsTemplate;
    ShowImageSubVieuw showImageSubView;
    InvoiceServices invoiceServices;
    AddProductEventListener listener;
    WorkOrderPdfServices workOrderPdfServices;
    PdfController pdfController;

    SplitLayout mainSplitLayout;
    SplitLayout headerSplitLayout;
    FormLayout headerFormLayout;
    Dialog searchCustomerDialog;
    Dialog saveInvoiceDialog;
    Dialog finishInvoiceDialog;

    Customer selectedCustomer;
    Address selectedAddress;

    Invoice selectedInvoice;

    private String pageTitle = "Proforma";

    Optional<List<Address>> allCustomerAddresses;
    ComboBox<Address> addressComboBox = new ComboBox<>();
    ComboBox<Address> projectCustomerAddressComboBox = new ComboBox<>();
    Card customerCard;
    Span badge;
    TextField tfInvoiceNumber;
    TextArea tfPoNumber;
    DatePicker invoiceDatePicker;
    DatePicker expiryDatePicker;
    TextArea invoiceCommentTextArea;

    MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
    Upload dropEnabledUpload = new Upload(buffer);
    Button showImageButton;
    Dialog imageDialog;

    Binder<Invoice>invoiceBinder;

    private boolean sidebarCollapsed;
    Button slideButton;
    Button goBackButton;
    Icon leftArrowIcon;
    Icon rightArrowIcon;

    Button saveInvoiceButton = new Button("Bewaar document");
    String linkParameter;

    Button generateInvoiceButton = new Button("Maak factuur");
    Button showPDFButton = new Button("Bekijk PDF");
    Button openWorkOrdersButton = new Button("Open werkbonnen");

    Checkbox checkbToCheck;
    Checkbox checkbApproved;
    Checkbox checkbRejected;

    Boolean openedFromFinalInvoiceOrNot = false;
    VerticalLayout customerInfoLayout = new VerticalLayout();

    VerticalLayout vLayoutFirstStepHeader;
    private Registration eventRegistration;
    private UI ui;

    private boolean ignoreAddressChange = false;


    private FileSystemResource linkToBulkSpreadsheet = new FileSystemResource("/Users/bramvandenberghe/Desktop/facturatie.xlsx");

    public NewInvoiceView(ProductService productService,
                          SelectProductSubView selectProductSubView,
                          SearchCustomerSubView searchCustomerSubView,
                          CustomerService customerService,
                          InvoiceService invoiceService,
                          GridFsTemplate gridFsTemplate,
                          ShowImageSubVieuw showImageSubView,
                          InvoiceServices invoiceServices,
                          AddProductEventListener listener,
                          WorkOrderPdfServices workOrderPdfServices,
                          PdfController pdfController, WorkOrderService workOrderService) {
        this.productService = productService;
        this.selectProductSubView = selectProductSubView;
        this.searchCustomerSubView = searchCustomerSubView;
        this.customerService = customerService;
        this.invoiceService = invoiceService;
        this.gridFsTemplate = gridFsTemplate;
        this.showImageSubView = showImageSubView;
        this.invoiceServices = invoiceServices;
        this.listener = listener;
        this.workOrderPdfServices = workOrderPdfServices;
        this.pdfController = pdfController;

        this.setSizeFull();
        setUpShowImageButton();
        setUpSearchCustomerDialog();
        setUpMainSplitLayout();
        setUpHeaderSplitLayout();
        mainSplitLayout.addToPrimary(selectProductSubView.getLayout());
        VerticalLayout vLayout = new VerticalLayout();
        vLayout.add(selectProductSubView.getFilter());
        vLayout.add(selectProductSubView.getSelectedProductGrid());
        vLayout.setSizeFull();
        setUpHeaderFormLayout();
        headerSplitLayout.addToPrimary(headerFormLayout);
        headerSplitLayout.addToSecondary(vLayout);
        mainSplitLayout.addToSecondary(headerSplitLayout);
        setUpInvoiceBinder();
        setUpUpload();
        setUpImageDialog();
        setUpSaveInvoiceDialog();
        setUpFinishInvoice();

        setUpFinishButton();
        setUpToInvoiceButton();

        this.setSizeFull();
        this.getStyle()
                .set("display", "flex")
                .set("flex-direction", "column");
        this.addClassName("view-wrapper");
        this.getStyle().set("background-color", "#e9ebef");
        this.setPadding(false);
        this.setSpacing(false);

        this.add(mainSplitLayout);
        this.workOrderService = workOrderService;
    }

    private void setUpFinishInvoice() {
        finishInvoiceDialog = new Dialog();
        finishInvoiceDialog.setHeaderTitle("Ben je zeker dat je deze proforma wil afwerken?");

        VerticalLayout dialogLayout = createFinishDialogLayout();
        finishInvoiceDialog.add(dialogLayout);

        Button saveButton = createSaveAsInvoice(finishInvoiceDialog);
        Button cancelButton = new Button("Niet Afwerken", e -> finishInvoiceDialog.close());
        finishInvoiceDialog.getFooter().add(cancelButton);
        finishInvoiceDialog.getFooter().add(saveButton);
    }

    private VerticalLayout createFinishDialogLayout() {
        Span span = new Span("Van deze proforma wordt een factuur gemaakt!");
        VerticalLayout dialogLayout = new VerticalLayout(span);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return dialogLayout;
    }

    private Checkbox getToCheck() {
        checkbToCheck = new Checkbox("TE CONTROLEREN");
        checkbToCheck.addValueChangeListener(value -> {
            if(value.isFromClient()){
                checkbApproved.setValue(false);
                checkbRejected.setValue(false);
            }
        });
        return checkbToCheck;
    }

    private Checkbox getApproved() {
        checkbApproved = new Checkbox("GOEDGEKEURD");
        checkbApproved.addValueChangeListener(value -> {
            if(value.isFromClient()){
                checkbToCheck.setValue(false);
                checkbRejected.setValue(false);
            }
        });
        return checkbApproved;
    }

    private Checkbox getRejected() {
        checkbRejected = new Checkbox("AFGEKEURD");
        checkbRejected.addValueChangeListener(value -> {
            if(value.isFromClient()){
                checkbToCheck.setValue(false);
                checkbApproved.setValue(false);
            }
        });
        return checkbRejected;
    }


    private void setUpToInvoiceButton() {
        showPDFButton.setWidth("100%");
        showPDFButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_WARNING);
        showPDFButton.addClickListener(e -> {
            invoiceServices.generateInvoicePDF(selectedInvoice);
        });

        openWorkOrdersButton.setWidth("100%");
        openWorkOrdersButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_WARNING);
        openWorkOrdersButton.addClickListener(e -> {
            if(selectedInvoice.getWorkOrderList() != null && selectedInvoice.getWorkOrderList().size() > 0){
                int delay = 0;

                for (WorkOrder workOrder : selectedInvoice.getWorkOrderList()) {

                    String url = "/pdf/workorder/" + workOrder.getId();

                    UI.getCurrent().getPage().executeJs(
                            "setTimeout(() => window.open($0, '_blank'), $1)",
                            url,
                            delay
                    );

                    delay += 1000;
                }
            }
            else{
                Notification notification = Notification.show("Er zijn geen werkbonnen gekoppeld in dit document");
                notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
            }

        });
    }

    private void setUpFinishButton() {
        saveInvoiceButton.setWidth("100%");
        saveInvoiceButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_WARNING);
        saveInvoiceButton.addClickListener(e -> {
            saveInvoiceDialog.open();
        });

        generateInvoiceButton.setWidth("100%");
        generateInvoiceButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_WARNING);
        generateInvoiceButton.addClickListener(e -> {
            if(openedFromFinalInvoiceOrNot == false){
                finishInvoiceDialog.open();
            }
            else{
                selectedInvoice.setFinalizeInvoice(true);
                Notification.show("Deze factuur is gefinaliseerd");
                invoiceService.save(selectedInvoice);
            }
        });
    }

    private void setUpSaveInvoiceDialog() {
        saveInvoiceDialog = new Dialog();
        saveInvoiceDialog.setHeaderTitle("Ben je zeker dat je dit document wil bewaren?");

        VerticalLayout dialogLayout = createSaveDialogLayout();
        saveInvoiceDialog.add(dialogLayout);

        Button saveButton = createSaveButton(saveInvoiceDialog);
        Button cancelButton = new Button("Niet Bewaren", e -> saveInvoiceDialog.close());
        saveInvoiceDialog.getFooter().add(cancelButton);
        saveInvoiceDialog.getFooter().add(saveButton);
    }

    private Button createSaveButton(Dialog dialog) {
        Button saveButton = new Button("Bewaren");
        saveButton.addClickListener(click -> {
            dialog.close();
            try {
                selectedInvoice.setCustomer(selectedCustomer);
                invoiceBinder.writeBean(selectedInvoice);
                selectedInvoice.setProductList(selectProductSubView.getSelectedProductList());
                invoiceService.save(selectedInvoice);
                Notification.show("Deze factuur is bewaard");
            } catch (ValidationException e) {
                Notification.show("Deze factuur kon niet worden bewaard.");
            }
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return saveButton;
    }

    private Button createSaveAsInvoice(Dialog dialog) {
        Button saveButton = new Button("Afwerken");
        saveButton.addClickListener(click -> {
            dialog.close();
            try {
                selectedInvoice.setCustomer(selectedCustomer);
                invoiceBinder.writeBean(selectedInvoice);
                selectedInvoice.setBFinalInvoice(true);
                selectedInvoice.setFinalizeInvoice(true);
                selectedInvoice.setInvoiceDate(LocalDate.now());
                selectedInvoice.setExpiryDate(LocalDate.now().plusDays(14));
                selectedInvoice.setUnpaid(true);
                selectedInvoice.setFinalInvoiceNumber(invoiceServices.getNewFinalInvoiceNumber());
                selectedInvoice.setProductList(selectProductSubView.getSelectedProductList());
                invoiceService.save(selectedInvoice);
                Notification.show("De factuur is aangemaakt");
            } catch (ValidationException e) {
                Notification.show("Kan de factuur niet genereren");
            }
            getUI().ifPresent(ui -> ui.navigate(ProformaInvoiceView.class));
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return saveButton;
    }

    private static VerticalLayout createSaveDialogLayout() {

        Span span = new Span("");
        VerticalLayout dialogLayout = new VerticalLayout(span);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return dialogLayout;
    }

    private void setUpInvoiceBinder() {
        invoiceBinder = new Binder<>();
        invoiceBinder.forField(tfInvoiceNumber)
                .asRequired("Elke factuur moet een factuurnummer hebben!")
                .withConverter(
                        (String value) -> {   // UI → MODEL
                            if (value == null || value.isBlank()) {
                                return null;
                            }
                            try {
                                return Integer.valueOf(value.replace("/", ""));
                            } catch (NumberFormatException e) {
                                throw new RuntimeException("Ongeldig nummer");
                            }
                        },
                        (Integer value) -> {  // MODEL → UI
                            if (value == null) {
                                return "";
                            }
                            String s = String.valueOf(value);
                            return s.length() > 2
                                    ? s.substring(0, 2) + "/" + s.substring(2)
                                    : s;
                        }
                )
                .bind(x -> {
                    if(x.getBFinalInvoice()){
                        return x.getFinalInvoiceNumber();
                    }
                    else{
                        return x.getInvoiceNumber();
                    }
                }, (x,y) -> {
                    if(x.getBFinalInvoice()){
                        x.setFinalInvoiceNumber(y);
                    }
                    else{
                        x.setInvoiceNumber(y);
                    }
                });
        invoiceBinder.forField(tfPoNumber)
                .bind(Invoice::getPoNumber, Invoice::setPoNumber);
        invoiceBinder.forField(invoiceDatePicker)
                .asRequired("Elke factuur moet een factuurdatum hebben!")
                .bind(Invoice::getInvoiceDate, Invoice::setInvoiceDate);
        invoiceBinder.forField(expiryDatePicker)
                .asRequired("Elke factuur moet een vevaldatum hebben!")
                .bind(Invoice::getExpiryDate, Invoice::setExpiryDate);
        invoiceBinder.forField(addressComboBox)
                .asRequired("Gelieve een adres te selecteren!")
                .bind(Invoice::getWorkAddress, Invoice::setWorkAddress);
        invoiceBinder.forField(projectCustomerAddressComboBox)
                .bind(Invoice::getProjectWorkAddress, Invoice::setProjectWorkAddress);
        invoiceBinder.forField(invoiceCommentTextArea)
                .bind(Invoice::getDiscription, Invoice::setDiscription);
        invoiceBinder.forField(checkbToCheck)
                .withNullRepresentation(false)
                .bind(Invoice::getToCheck, Invoice::setToCheck);
        invoiceBinder.forField(checkbApproved)
                .withNullRepresentation(false)
                .bind(Invoice::getBApproved, Invoice::setBApproved);
        invoiceBinder.forField(checkbRejected)
                .withNullRepresentation(false)
                .bind(Invoice::getBRejected, Invoice::setBRejected);
        invoiceBinder.addValueChangeListener(workOrder -> {
            try {
                selectedInvoice.setCustomer(selectedCustomer);
                invoiceBinder.writeBean(selectedInvoice);
                selectedInvoice.setProductList(selectProductSubView.getSelectedProductList());
                invoiceService.save(selectedInvoice);
            } catch (ValidationException e) {
                Notification.show("Kon dit document nog niet bewaren");
            }
        });
    }

    private void setUpHeaderFormLayout() {
        headerFormLayout = new FormLayout();
        headerFormLayout.setSizeFull();
        headerFormLayout.add(getFirstStepHeader());
        headerFormLayout.add(getSecondStepHeader());
        //headerFormLayout.add(getThirdStepHeader(),2);
    }

    private void setUpSearchCustomerDialog() {
        searchCustomerDialog = new Dialog();
        searchCustomerDialog.add(searchCustomerSubView);
        searchCustomerSubView.setDialog(searchCustomerDialog);
        searchCustomerDialog.setCloseOnEsc(true);
        searchCustomerDialog.setHeight("50%");
        searchCustomerDialog.setWidth("50%");
        searchCustomerDialog.addDialogCloseActionListener(event -> {
            selectedCustomer = searchCustomerSubView.getSelectedCustomer();
            selectProductSubView.setSelectedCustmer(selectedCustomer);
            selectedAddress = searchCustomerSubView.getSelectedAddress();
            searchCustomerDialog.close();
            try{
                Address invoiceAddress = selectedCustomer.getAddresses().stream().filter(filter -> filter.getInvoiceAddress()).findFirst().get();
            }
            catch (Exception e){
                Notification notification = Notification.show("Kon geen volledig facturatie- adres vinden : " + e.getMessage());
                notification.setDuration(10000);
                notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
            }

        });
    }

    private VerticalLayout getFirstStepHeader() {
        vLayoutFirstStepHeader = new VerticalLayout();
        vLayoutFirstStepHeader.setSizeFull();
        vLayoutFirstStepHeader.add(new HorizontalLayout(goBackButton,slideButton));
        vLayoutFirstStepHeader.add(getAddressComboBox());
        vLayoutFirstStepHeader.add(getProjectCustomerComboBox());
        vLayoutFirstStepHeader.add(getCustomerCard());
        vLayoutFirstStepHeader.add(getInvoiceNumber());
        vLayoutFirstStepHeader.add(getInvoiceDatePicker());
        vLayoutFirstStepHeader.add(getExpiryDatePicker());
        vLayoutFirstStepHeader.add(getPoNumber());
        return vLayoutFirstStepHeader;
    }

    private VerticalLayout getSecondStepHeader() {
        VerticalLayout mainvLayout = new VerticalLayout();
        mainvLayout.setSizeFull();
        HorizontalLayout hLayout = new HorizontalLayout();
        VerticalLayout checkVLayout = new VerticalLayout();
        checkVLayout.setSpacing(true);
        checkVLayout.setPadding(false);
        checkVLayout.add(getToCheck());
        checkVLayout.add(getApproved());
        checkVLayout.add(getRejected());
        checkVLayout.setWidth("30%");
        hLayout.setWidth("100%");
        hLayout.add(checkVLayout);
        hLayout.add(getInvoiceCommentTextArea());
        hLayout.setFlexGrow(1,checkVLayout);
        hLayout.setFlexGrow(3,invoiceCommentTextArea);
        VerticalLayout buttonLayout = new VerticalLayout();
        buttonLayout.setSizeFull();
        buttonLayout.setPadding(false);
        buttonLayout.add(openWorkOrdersButton,showPDFButton, generateInvoiceButton);
        mainvLayout.add(new Span(""),hLayout,buttonLayout,dropEnabledUpload,showImageButton);
        return mainvLayout;
    }

    private TextArea getInvoiceCommentTextArea() {
        invoiceCommentTextArea = new TextArea("");
        invoiceCommentTextArea.setPlaceholder("Commentaar");
        invoiceCommentTextArea.setWidth("100%");
        invoiceCommentTextArea.setHeight("190px");
        return invoiceCommentTextArea;
    }

    private DatePicker getInvoiceDatePicker() {
        invoiceDatePicker = new DatePicker();
        invoiceDatePicker.setSizeFull();
        invoiceDatePicker.setValue(LocalDate.now());
        invoiceDatePicker.addValueChangeListener(event -> {
            expiryDatePicker.setValue(event.getValue().plusDays(14));
        });
        return invoiceDatePicker;
    }

    private DatePicker getExpiryDatePicker() {
        expiryDatePicker = new DatePicker();
        expiryDatePicker.setSizeFull();
        expiryDatePicker.setLocale(Locale.FRENCH);
        expiryDatePicker.setValue(LocalDate.now().plusDays(14));
        return expiryDatePicker;
    }

    private Component getCustomerCard() {
        customerCard = new Card();
        badge = new Span("Geen status");
        badge.getElement().getThemeList().add("badge success");
        badge.addClickListener(click -> {
            searchCustomerDialog.open();
        });
        customerCard.addThemeVariants(CardVariant.LUMO_ELEVATED);
        customerCard.setTitle(new Div("Naam klant"));
        customerCard.setSubtitle(new Div("BTW- nummer klant"));
        customerCard.setHeaderSuffix(badge);
        customerCard.setWidthFull();
        customerCard.add("commentaar bij klant -> meestal gekoppeld aan Alert- status");
        return customerCard;
    }

    private TextField getInvoiceNumber() {
        tfInvoiceNumber = new TextField();
        tfInvoiceNumber.setWidthFull();
        tfInvoiceNumber.setValue(String.valueOf(invoiceServices.getNewProFormaInvoiceNumber()));
        return tfInvoiceNumber;
    }

    private TextArea getPoNumber(){
        tfPoNumber = new TextArea();
        tfPoNumber.setPlaceholder("Gelieve hier het PO- nummer in te geven");
        tfPoNumber.setWidthFull();
        return tfPoNumber;
    }

    private ComboBox getProjectCustomerComboBox() {
        if (allCustomerAddresses.isPresent()) {
            projectCustomerAddressComboBox.setItems(allCustomerAddresses.get());
        }
        else{
            Notification.show("Geen Klanten in de database");
        }
        projectCustomerAddressComboBox.setPlaceholder("Gelieve een klant/adres te selecteren");
        projectCustomerAddressComboBox.setItemLabelGenerator(address -> {
            if((address.getAddressName() != null) && (address.getAddressName().length() > 0)) {
                if((address.getCustomerName() != null) && (!address.getCustomerName().matches(address.getAddressName()))) {
                    return address.getCustomerName() + " / " + address.getAddressName();
                }
                else{
                    return address.getAddressName();
                }

            }
            else{
                return address.getCustomerName();
            }
        });
        projectCustomerAddressComboBox.setVisible(false);
        projectCustomerAddressComboBox.setWidthFull();
        return projectCustomerAddressComboBox;
    }

    private ComboBox getAddressComboBox() {
        addressComboBox.setWidthFull();
        allCustomerAddresses = customerService.getAllCustomerAdresses();
        if (allCustomerAddresses.isPresent()) {
            allCustomerAddresses.get().sort(Comparator.comparing(Address::getCustomerName));
            addressComboBox.setItems(allCustomerAddresses.get());
        } else {
            Notification.show("Geen Klanten in de database");
        }
        addressComboBox.setPlaceholder("Gelieve een klant/adres te selecteren");
        addressComboBox.setItemLabelGenerator(address -> {
            if ((address.getAddressName() != null) && (address.getAddressName().length() > 0)) {
                if ((address.getCustomerName() != null) && (!address.getCustomerName().matches(address.getAddressName()))) {
                    return address.getCustomerName() + " / " + address.getAddressName();
                } else {
                    return address.getAddressName();
                }

            } else {
                return address.getCustomerName();
            }
        });
        addressComboBox.addValueChangeListener(event -> {

            if (ignoreAddressChange || !event.isFromClient()) {
                return;
            }

            Address oldAddress = event.getOldValue();
            Address newAddress = event.getValue();

            // Alleen tonen indien nodig
            if (selectedInvoice.getProductList() != null
                    && selectedInvoice.getProductList().stream()
                    .filter(item -> !item.getBComment())
                    .findAny()
                    .isPresent()) {

                Dialog dialog = new Dialog();
                dialog.setHeaderTitle("Klant wijzigen");

                dialog.add("De geselecteerde klant wijzigen kan invloed hebben op de producten en prijzen. Wil je doorgaan?");

                Button cancel = new Button("Annuleren", e -> {
                    ignoreAddressChange = true;
                    addressComboBox.setValue(oldAddress);
                    ignoreAddressChange = false;
                    dialog.close();
                });

                Button ok = new Button("Doorgaan", e -> {
                    dialog.close();

                    updateCustomer(newAddress);
                    selectProductSubView.recalcSelectedItemsWithNewCustomer();
                    try {
                        selectedInvoice.setCustomer(selectedCustomer);
                        invoiceBinder.writeBean(selectedInvoice);
                        selectedInvoice.setProductList(selectProductSubView.getSelectedProductList());
                        invoiceService.save(selectedInvoice);
                    } catch (ValidationException val) {
                        Notification.show("Kon dit document nog niet bewaren");
                    }
                    UI.getCurrent().getPage().reload();
                });

                HorizontalLayout footer = new HorizontalLayout();

                Div spacer = new Div();
                footer.setWidthFull();
                footer.expand(spacer);

                footer.add(cancel, spacer, ok);

                dialog.getFooter().add(footer);
                dialog.open();

                return;
            }

            updateCustomer(newAddress);
        });

        return addressComboBox;
    }

    private void updateCustomer(Address address) {

        Optional<List<Customer>>selectedCustomerList  = customerService.getCustomerByWorkAddress(address);
        if(!selectedCustomerList.isEmpty() && selectedCustomerList.get().size() == 1){
            selectProductSubView.setSelectedCustmer(selectedCustomerList.get().get(0));
            selectedCustomer = selectedCustomerList.get().get(0);
        }
        else{
            Notification notification = new Notification();
            notification.setText("Dit werkadres bevat meerdere klanten!");
        }
        if(selectedCustomer != null){
            customerCard.removeAll();
            customerCard.setTitle(new Div(selectedCustomer.getName()));
            customerInfoLayout.setSpacing(false);
            customerInfoLayout.removeAll();
            customerInfoLayout.add(new Div(selectedCustomer.getVatNumber()));
            try{
                Address invoiceAddress = selectedCustomer.getAddresses().stream().filter(x -> (x.getInvoiceAddress() != null) && (x.getInvoiceAddress() == true)).findFirst().get();
                customerInfoLayout.add(new Div(invoiceAddress.getStreet()));
                customerInfoLayout.add(new Div(invoiceAddress.getCity()));
            }
            catch (Exception e){

            }
            customerCard.setSubtitle(customerInfoLayout);
            customerCard.add(selectedCustomer.getComment());
            if((selectedCustomer.getAlertMessage() != null) && (selectedCustomer.getAlertMessage().length() > 0)){
                badge.setText("Alarm");
                badge.getElement().getThemeList().clear();
                badge.getElement().getThemeList().add("badge error");
            }
            else{
                badge.setText("Geen Alarm");
                badge.getElement().getThemeList().clear();
                badge.getElement().getThemeList().add("badge success");
            }
            customerCard.setHeaderSuffix(badge);

            if((selectedCustomer.getBProjectCustomer() == null) || (selectedCustomer.getBProjectCustomer() == false)){
                projectCustomerAddressComboBox.setVisible(false);
            }
            else{
                projectCustomerAddressComboBox.setVisible(true);
            }
        }
        else{
            customerCard.removeAll();
            customerCard.setTitle(new Div("N/A"));
            customerCard.setSubtitle(new Div("N/A"));
            customerCard.add("N/A");
            badge.setText("N/A");
            badge.getElement().getThemeList().clear();
            badge.getElement().getThemeList().add("badge success");
            customerCard.setHeaderSuffix(badge);
        }

        try {
            selectedInvoice.setCustomer(selectedCustomer);
            invoiceBinder.writeBean(selectedInvoice);
            selectedInvoice.setProductList(selectProductSubView.getSelectedProductList());
            invoiceService.save(selectedInvoice);
        } catch (ValidationException e) {
            Notification.show("Kon dit document nog niet bewaren");
        }
    }

    private void setUpMainSplitLayout() {

        slideButton = new Button(VaadinIcon.ARROW_RIGHT.create());
        leftArrowIcon = VaadinIcon.ARROW_LEFT.create();
        rightArrowIcon = VaadinIcon.ARROW_RIGHT.create();

        sidebarCollapsed = true;

        goBackButton = new Button(VaadinIcon.BACKWARDS.create());
        goBackButton.addClickListener(e -> {
            UI.getCurrent().getPage().getHistory().back();
        });

        slideButton.addClickListener(event -> {
            sidebarCollapsed = !sidebarCollapsed;
            updateSidebar();
        });
        slideButton.setAriaLabel("Expand/collapse sidebar");
        slideButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        slideButton.getStyle().set("float", "right");

        mainSplitLayout = new SplitLayout();
        mainSplitLayout.setSizeFull();
        mainSplitLayout.setOrientation(SplitLayout.Orientation.HORIZONTAL);
        mainSplitLayout.setSplitterPosition(0);
    }

    private void updateSidebar() {
        slideButton.setIcon(sidebarCollapsed ? rightArrowIcon : leftArrowIcon);
        mainSplitLayout.setSplitterPosition(sidebarCollapsed ? 0 : 90);
    }

    private void setUpHeaderSplitLayout() {
        headerSplitLayout = new SplitLayout();
        headerSplitLayout.setSizeFull();
        headerSplitLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
    }

    private void setUpUpload() {
        dropEnabledUpload.setWidthFull();
        dropEnabledUpload.setAcceptedFileTypes("image/tiff", ".jpeg");
        dropEnabledUpload.addFileRejectedListener(event -> {
            String errorMessage = event.getErrorMessage();

            Notification notification = Notification.show(errorMessage, 5000,
                    Notification.Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });
        dropEnabledUpload.addFailedListener(event -> {
            Notification.show("Deze foto kon niet worden verstruurd naar de server : " + event.getReason());
        });
        dropEnabledUpload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream inputStream = buffer.getInputStream(fileName);
            DBObject metaData = new BasicDBObject();
            metaData.put("timeOfUpload", LocalDateTime.now().toString());
            try {
                storeImageIdToThisInvoice(gridFsTemplate.store(inputStream, fileName, "image/png", metaData).toString());
                updateGetImageButton();
            } catch (ValidationException e) {
                Notification notification = Notification.show("De toegevoegde foto kon niet worden bewaard!");
                notification.setPosition(Notification.Position.MIDDLE);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                Notification.show("Inputstream van deze foto kon niet worden afgesloten!");
            }
        });
    }

    private void updateGetImageButton() {
        showImageButton.setWidthFull();
        showImageButton.setText("Deze factuur bevat " + selectedInvoice.getImageList().size() + " foto(s), klik hier om ze te bekijken.");
        showImageButton.removeThemeVariants(ButtonVariant.LUMO_ERROR);
        showImageButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        showImageButton.addClickListener(e -> {imageDialog.open();});
    }

    private void storeImageIdToThisInvoice(String idString) throws ValidationException {
        if(selectedInvoice.getImageList() != null) {
            selectedInvoice.getImageList().add(idString);
            selectedInvoice.setCustomer(selectedCustomer);
            invoiceBinder.writeBean(selectedInvoice);
            selectedInvoice.setProductList(selectProductSubView.getSelectedProductList());
            invoiceService.save(selectedInvoice);
        }
        else{
            List<String>imageIdList = new ArrayList<>();
            imageIdList.add(idString);
            selectedInvoice.setImageList(imageIdList);
            selectedInvoice.setCustomer(selectedCustomer);
            invoiceBinder.writeBean(selectedInvoice);
            selectedInvoice.setProductList(selectProductSubView.getSelectedProductList());
            invoiceService.save(selectedInvoice);
        }
    }

    private void setUpImageDialog() {
        imageDialog = new Dialog();
        imageDialog.setHeaderTitle("Toegevoegde foto's onder geselecteerde factuur");
        imageDialog.add(showImageSubView);
        Button cancelButton = new Button("Sluiten", e -> {
            try {
                selectedInvoice.setCustomer(selectedCustomer);
                invoiceBinder.writeBean(selectedInvoice);
                selectedInvoice.setProductList(selectProductSubView.getSelectedProductList());
                invoiceService.save(selectedInvoice);
            } catch (ValidationException ex) {
                throw new RuntimeException(ex);
            }
            invoiceService.save(selectedInvoice);
            imageDialog.close();
        });
        imageDialog.getFooter().add(cancelButton);
    }

    private void setUpShowImageButton() {
        showImageButton = new Button("Er zijn in deze werkbon geen foto's toegevoegd, gelieve altijd een foto te koppelen van de werken!");
        showImageButton.setWidth("100%");

        showImageButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        showImageButton.addClickListener(e -> {
            if((selectedInvoice.getImageList() != null) && (selectedInvoice.getImageList().size() > 0)) {
                showImageSubView.setUser(UserFunction.ADMIN);
                showImageSubView.setSelectedWorkOrder(selectedInvoice.getImageList());
            }
            else{
                Notification notification = Notification.show("Deze werkbon bevat nog geen foto's!");
                notification.setPosition(Notification.Position.MIDDLE);
                notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
            }
        });
    }

    private void readNewInvoice(Boolean proformaOrNot) {
        Invoice newInvoice = new Invoice();
        newInvoice.setInvoiceDate(LocalDate.now());
        newInvoice.setExpiryDate(LocalDate.now().plusDays(14));
        if(proformaOrNot) {
            newInvoice.setBFinalInvoice(false);
            newInvoice.setInvoiceNumber(invoiceServices.getNewProFormaInvoiceNumber());
        }
        else{
            newInvoice.setBFinalInvoice(true);
            newInvoice.setFinalInvoiceNumber(invoiceServices.getNewFinalInvoiceNumber());
        }
        List<Product>products = new ArrayList<>();
        Product product1 = new Product();
        product1.setBComment(true);
        product1.setSelectedAmount(null);
        product1.setSellPrice(0.0);
        product1.setTotalPrice(0.0);
        product1.setVat(VAT.EENENTWINTIG);
        product1.setTeamNumber(0);
        products.add(product1);
        newInvoice.setProductList(products);
        selectedInvoice = newInvoice;
        invoiceBinder.readBean(selectedInvoice);
        selectProductSubView.setUserFunctionAndDocumentDate(UserFunction.ADMIN, selectedInvoice.getInvoiceDate());
        selectProductSubView.setSelectedCustmer(selectedCustomer);
        selectProductSubView.setSelectedProductList(selectedInvoice.getProductList());
    }

    private void handleAddRemoveProductEvent(AddRemoveProductEvent event) {
        try {
            selectedInvoice.setCustomer(selectedCustomer);
            invoiceBinder.writeBean(selectedInvoice);
            selectedInvoice.setProductList(selectProductSubView.getSelectedProductList());
            invoiceService.save(selectedInvoice);

            if ("Product verwijderd".equals(event.getMessage())) {
                Notification notification = Notification.show("Lijn is verwijderd.");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } else if ("Product toegevoegd".equals(event.getMessage())) {
                Notification notification = Notification.show("Lijn is toegevoegd.");
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            }

        } catch (ValidationException e) {
            Notification notification = Notification.show(
                    "Lijn niet toegevoegd gelieve eerst de hoofding in te vullen aub.");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

            List<Product> selectedProducts = selectProductSubView.getSelectedProductList();
            if (!selectedProducts.isEmpty()) {
                selectedProducts.remove(selectedProducts.size() - 1);
                selectProductSubView.getSelectedProductGrid().getDataProvider().refreshAll();
            }
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        this.ui = attachEvent.getUI();

        if (eventRegistration == null) {
            eventRegistration = listener.addEventConsumer(event -> {
                if (ui != null) {
                    ui.access(() -> handleAddRemoveProductEvent(event));
                }
            });
        }
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);

        if (eventRegistration != null) {
            eventRegistration.remove();
            eventRegistration = null;
        }
    }


    @Override
    public void setParameter(BeforeEvent beforeEvent,@OptionalParameter String parameter) {

        String id = beforeEvent.getRouteParameters()
                .get("id")
                .orElse(null);

        // query parameters
        Map<String, List<String>> query = beforeEvent.getLocation()
                .getQueryParameters()
                .getParameters();

        String proforma = query.getOrDefault("proforma", List.of("false")).get(0);
        String workAddressStreet = query.getOrDefault("workAddressStreet", null).get(0);


        if ((id != null) && (id.length()>0) && (!(id.matches("none")))) {
            //Open Invoice by an other page and search Invoice by linkParameter.
            //So for every page with CurrentInvoiceSubView in it that will open a clicked item in WorkOrderView
            linkParameter = id;
            Optional<Invoice> optionalInvoiceById = invoiceService.getInvoiceById(linkParameter);
            if (optionalInvoiceById.isPresent()) {
                selectedInvoice = optionalInvoiceById.get();
                selectProductSubView.setUserFunctionAndDocumentDate(UserFunction.ADMIN, selectedInvoice.getInvoiceDate());
                selectedCustomer = selectedInvoice.getCustomer();
                selectProductSubView.setSelectedCustmer(selectedCustomer);
                invoiceBinder.readBean(selectedInvoice);
                if(selectedInvoice.getBFinalInvoice() != null && selectedInvoice.getBFinalInvoice() == true){
                    //products doesn't have to be checked if it has the same price as the DB
                    selectProductSubView.setSelectedProductList(selectedInvoice.getProductList());
                    invoiceCommentTextArea.setVisible(false);
                    checkbApproved.setVisible(false);
                    checkbRejected.setVisible(false);
                    checkbToCheck.setVisible(false);
                    pageTitle = "Factuur";
                    UI.getCurrent().getPage().setTitle(pageTitle);
                    openedFromFinalInvoiceOrNot = true;
                }
                else{
                    //products have to be checked if it has the same price as the DB
                    selectProductSubView.setSelectedProductList(checkProductPriceWithDB(selectedInvoice.getProductList()));
                    invoiceCommentTextArea.setVisible(true);
                    checkbApproved.setVisible(true);
                    checkbRejected.setVisible(true);
                    checkbToCheck.setVisible(true);
                    pageTitle = "Proforma";
                    UI.getCurrent().getPage().setTitle(pageTitle);
                    openedFromFinalInvoiceOrNot = false;
                }
            } else {
                Notification show = Notification.show("Dit document kon niet worden geopend!");
                show.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        }

        if ((id.matches("none")) && (proforma != null) && (proforma.matches("true"))) {
            linkParameter = null;
            invoiceCommentTextArea.setVisible(true);
            checkbApproved.setVisible(true);
            checkbRejected.setVisible(true);
            checkbToCheck.setVisible(true);
            pageTitle = "Proforma";
            UI.getCurrent().getPage().setTitle(pageTitle);
            readNewInvoice(true);
            openedFromFinalInvoiceOrNot = false;
            if(workAddressStreet != null){
                try{
                    Address workAddressToSelect = allCustomerAddresses.get().stream().filter(x -> (x.getStreet() != null) && (x.getStreet().matches(workAddressStreet))).findFirst().get();
                    addressComboBox.setValue(workAddressToSelect);
                }
                catch (Exception e){

                }
            }
        }
        else if((id.matches("none")) && (proforma != null) && (proforma.matches("false"))){
            linkParameter = null;
            invoiceCommentTextArea.setVisible(false);
            checkbApproved.setVisible(false);
            checkbRejected.setVisible(false);
            checkbToCheck.setVisible(false);
            pageTitle = "Factuur";
            UI.getCurrent().getPage().setTitle(pageTitle);
            readNewInvoice(false);
            openedFromFinalInvoiceOrNot = true;
            if(workAddressStreet != null){
                try{
                    Address workAddressToSelect = allCustomerAddresses.get().stream().filter(x -> (x.getStreet() != null) && (x.getStreet().matches(workAddressStreet))).findFirst().get();
                    addressComboBox.setValue(workAddressToSelect);
                }
                catch (Exception e){

                }
            }
        }
    }

    private List<Product> checkProductPriceWithDB(List<Product> productList) {
        if((productList != null) && (productList.size() > 0)){
            for(Product product : productList.stream().filter(x -> ((x.isNoRecentPriceApproved() == false))).collect(Collectors.toList())){
                if(product.getId() != null){
                    Optional<Product> dbProduct = productService.findById(product.getId());
                    if(dbProduct.isPresent()){
                        if((dbProduct.get().getPurchasePrice() > product.getPurchasePrice())){
                            product.setNoRecentPrice(true);
                            product.setRecentPurchasePrice(dbProduct.get().getPurchasePrice());
                            product.setRecentAgroPrice(dbProduct.get().getSellPrice());
                            product.setRecentIndustryPrice(dbProduct.get().getSellPriceIndustry());
                        }
                        else{
                            product.setNoRecentPrice(false);
                        }
                    }
                }
            }
        }
        return productList;
    }

    @Override
    public String getPageTitle() {
        return pageTitle;
    }

}
