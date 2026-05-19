package com.adverto.dejonghe.server.views.quote;

import com.adverto.dejonghe.server.customEvents.AddProductEventListener;
import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.dbservices.ProductService;
import com.adverto.dejonghe.common.dbservices.QuoteService;
import com.adverto.dejonghe.common.entities.customers.Address;
import com.adverto.dejonghe.common.entities.customers.Customer;
import com.adverto.dejonghe.common.entities.enums.employee.UserFunction;
import com.adverto.dejonghe.common.entities.enums.product.VAT;
import com.adverto.dejonghe.common.entities.product.product.Product;
import com.adverto.dejonghe.common.entities.quote.Quote;
import com.adverto.dejonghe.server.services.quote.QuoteServices;
import com.adverto.dejonghe.server.views.subViews.SearchCustomerSubView;
import com.adverto.dejonghe.server.views.subViews.SelectProductSubView;
import com.adverto.dejonghe.server.views.subViews.ShowImageSubVieuw;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.vaadin.flow.component.Component;
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
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Route("eindQuote/:id")
@Menu(order = 0, icon = LineAwesomeIconUrl.EURO_SIGN_SOLID)
public class NewQuoteView extends VerticalLayout implements HasUrlParameter<String>,HasDynamicTitle {

    QuoteService quoteService;
    ProductService productService;
    SelectProductSubView selectProductSubView;
    SearchCustomerSubView searchCustomerSubView;
    CustomerService customerService;
    GridFsTemplate gridFsTemplate;
    ShowImageSubVieuw showImageSubView;
    QuoteServices quoteServices;
    AddProductEventListener listener;

    SplitLayout mainSplitLayout;
    SplitLayout headerSplitLayout;
    FormLayout headerFormLayout;
    Dialog searchCustomerDialog;
    Dialog saveQuoteDialog;
    Dialog finishInvoiceDialog;

    Customer selectedCustomer;
    Address selectedAddress;

    Quote selectedQuote;

    private String pageTitle = "Offerte";

    Optional<List<Address>> allCustomerAddresses;
    ComboBox<Address> addressComboBox = new ComboBox<>();
    Card customerCard;
    Span badge;
    TextField tfInvoiceNumber;
    DatePicker quoteDatePicker;
    DatePicker expiryDatePicker;
    TextArea invoiceCommentTextArea;

    MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
    Upload dropEnabledUpload = new Upload(buffer);
    Button showImageButton;
    Dialog imageDialog;

    Binder<Quote> quoteBinder;

    private boolean sidebarCollapsed;
    Button slideButton;
    Icon leftArrowIcon;
    Icon rightArrowIcon;

    Button saveInvoiceButton = new Button("Bewaar document");
    String linkParameter;

    Button showPDFButton = new Button("Bekijk PDF");

    Checkbox checkbToCheck;
    Checkbox checkbApproved;
    Checkbox checkbSend;
    Checkbox checkbRejected;

    VerticalLayout customerInfoLayout = new VerticalLayout();


    private FileSystemResource linkToBulkSpreadsheet = new FileSystemResource("/Users/bramvandenberghe/Desktop/facturatie.xlsx");

    public NewQuoteView(ProductService productService,
                        SelectProductSubView selectProductSubView,
                        SearchCustomerSubView searchCustomerSubView,
                        CustomerService customerService,
                        QuoteService quoteService,
                        GridFsTemplate gridFsTemplate,
                        ShowImageSubVieuw showImageSubView,
                        QuoteServices quoteServices,
                        AddProductEventListener listener) {
        this.productService = productService;
        this.selectProductSubView = selectProductSubView;
        this.searchCustomerSubView = searchCustomerSubView;
        this.customerService = customerService;
        this.quoteService = quoteService;
        this.gridFsTemplate = gridFsTemplate;
        this.showImageSubView = showImageSubView;
        this.quoteServices = quoteServices;
        this.listener = listener;

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

    private Component getSend() {
        checkbSend = new Checkbox("VERZONDEN");
        checkbSend.addValueChangeListener(value -> {
            if(value.isFromClient()){
                checkbToCheck.setValue(false);
                checkbRejected.setValue(false);
                checkbApproved.setValue(false);
            }
        });
        return checkbSend;
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
            quoteServices.generateInvoicePDF(selectedQuote);
        });
    }

    private void setUpFinishButton() {
        saveInvoiceButton.setWidth("100%");
        saveInvoiceButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_WARNING);
        saveInvoiceButton.addClickListener(e -> {
            saveQuoteDialog.open();
        });
    }

    private void setUpSaveInvoiceDialog() {
        saveQuoteDialog = new Dialog();
        saveQuoteDialog.setHeaderTitle("Ben je zeker dat je dit document wil bewaren?");

        VerticalLayout dialogLayout = createSaveDialogLayout();
        saveQuoteDialog.add(dialogLayout);

        Button saveButton = createSaveButton(saveQuoteDialog);
        Button cancelButton = new Button("Niet Bewaren", e -> saveQuoteDialog.close());
        saveQuoteDialog.getFooter().add(cancelButton);
        saveQuoteDialog.getFooter().add(saveButton);
    }

    private Button createSaveButton(Dialog dialog) {
        Button saveButton = new Button("Bewaren");
        saveButton.addClickListener(click -> {
            dialog.close();
            try {
                selectedQuote.setCustomer(selectedCustomer);
                quoteBinder.writeBean(selectedQuote);
                selectedQuote.setProductList(selectProductSubView.getSelectedProductList());
                quoteService.save(selectedQuote);
                Notification.show("Deze factuur is bewaard");
            } catch (ValidationException e) {
                Notification.show("Deze factuur kon niet worden bewaard.");
            }
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return saveButton;
    }

    private static VerticalLayout createSaveDialogLayout() {

        Span span = new Span("");
        VerticalLayout dialogLayout = new VerticalLayout(span);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return dialogLayout;
    }

    private void setUpInvoiceBinder() {
        quoteBinder = new Binder<>();
        quoteBinder.forField(tfInvoiceNumber)
                .asRequired("Elke offerte moet een factuurnummer hebben!")
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
                    return x.getQuoteNumber();
                }, (x,y) -> {
                    x.setQuoteNumber(y);
                });

        quoteBinder.forField(quoteDatePicker)
                .asRequired("Elke offerte moet een factuurdatum hebben!")
                .bind(Quote::getQuoteDate, Quote::setQuoteDate);
        quoteBinder.forField(expiryDatePicker)
                .asRequired("Elke offerte moet een vevaldatum hebben!")
                .bind(Quote::getExpiryDate, Quote::setExpiryDate);
        quoteBinder.forField(addressComboBox)
                .asRequired("Gelieve een adres te selecteren!")
                .bind(Quote::getWorkAddress, Quote::setWorkAddress);
        quoteBinder.forField(invoiceCommentTextArea)
                .bind(Quote::getDiscription, Quote::setDiscription);
        quoteBinder.forField(checkbToCheck)
                .withNullRepresentation(false)
                .bind(Quote::getToCheck, Quote::setToCheck);
        quoteBinder.forField(checkbApproved)
                .withNullRepresentation(false)
                .bind(Quote::getBApproved, Quote::setBApproved);
        quoteBinder.forField(checkbRejected)
                .withNullRepresentation(false)
                .bind(Quote::getBRejected, Quote::setBRejected);
        quoteBinder.forField(checkbSend)
                .withNullRepresentation(false)
                .bind(Quote::getBSend, Quote::setBSend);
        quoteBinder.addValueChangeListener(workOrder -> {
            try {
                selectedQuote.setCustomer(selectedCustomer);
                quoteBinder.writeBean(selectedQuote);
                selectedQuote.setProductList(selectProductSubView.getSelectedProductList());
                quoteService.save(selectedQuote);
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
        VerticalLayout vLayout = new VerticalLayout();
        vLayout.setSizeFull();
        vLayout.add(slideButton);
        vLayout.add(getAddressComboBox());
        vLayout.add(getCustomerCard());
        vLayout.add(getInvoiceNumber());
        vLayout.add(getQuoteDatePicker());
        vLayout.add(getExpiryDatePicker());
        return vLayout;
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
        checkVLayout.add(getSend());
        checkVLayout.setWidth("30%");
        hLayout.setWidth("100%");
        hLayout.add(checkVLayout);
        hLayout.add(getInvoiceCommentTextArea());
        hLayout.setFlexGrow(1,checkVLayout);
        hLayout.setFlexGrow(3,invoiceCommentTextArea);
        VerticalLayout buttonLayout = new VerticalLayout();
        buttonLayout.setSizeFull();
        buttonLayout.setPadding(false);
        buttonLayout.add(showPDFButton);
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

    private DatePicker getQuoteDatePicker() {
        quoteDatePicker = new DatePicker();
        quoteDatePicker.setSizeFull();
        quoteDatePicker.setValue(LocalDate.now());
        quoteDatePicker.addValueChangeListener(event -> {
            expiryDatePicker.setValue(event.getValue().plusDays(14));
        });
        return quoteDatePicker;
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
        tfInvoiceNumber.setValue(String.valueOf(quoteServices.getNewQuoteNumber()));
        return tfInvoiceNumber;
    }

    private ComboBox getAddressComboBox() {
        allCustomerAddresses = customerService.getAllCustomerAdresses();
        if (allCustomerAddresses.isPresent()) {
            addressComboBox.setItems(allCustomerAddresses.get());
        }
        else{
            Notification.show("Geen Klanten in de database");
        }
        addressComboBox.setPlaceholder("Gelieve een klant/adres te selecteren");
        addressComboBox.setItemLabelGenerator(address -> {
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
        addressComboBox.addValueChangeListener(event -> {
           Optional<List<Customer>>selectedCustomerList  = customerService.getCustomerByWorkAddress(event.getValue());
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
                    Address invoiceAddress = selectedCustomer.getAddresses().stream().filter(address -> (address.getInvoiceAddress() != null) && (address.getInvoiceAddress() == true)).findFirst().get();
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
        });
        addressComboBox.setWidthFull();
        return addressComboBox;
    }

    private void setUpMainSplitLayout() {

        slideButton = new Button(VaadinIcon.ARROW_RIGHT.create());
        leftArrowIcon = VaadinIcon.ARROW_LEFT.create();
        rightArrowIcon = VaadinIcon.ARROW_RIGHT.create();

        sidebarCollapsed = true;

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
        showImageButton.setText("Deze factuur bevat " + selectedQuote.getImageList().size() + " foto(s), klik hier om ze te bekijken.");
        showImageButton.removeThemeVariants(ButtonVariant.LUMO_ERROR);
        showImageButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        showImageButton.addClickListener(e -> {imageDialog.open();});
    }

    private void storeImageIdToThisInvoice(String idString) throws ValidationException {
        if(selectedQuote.getImageList() != null) {
            selectedQuote.getImageList().add(idString);
            selectedQuote.setCustomer(selectedCustomer);
            quoteBinder.writeBean(selectedQuote);
            selectedQuote.setProductList(selectProductSubView.getSelectedProductList());
            quoteService.save(selectedQuote);
        }
        else{
            List<String>imageIdList = new ArrayList<>();
            imageIdList.add(idString);
            selectedQuote.setImageList(imageIdList);
            selectedQuote.setCustomer(selectedCustomer);
            quoteBinder.writeBean(selectedQuote);
            selectedQuote.setProductList(selectProductSubView.getSelectedProductList());
            quoteService.save(selectedQuote);
        }
    }

    private void setUpImageDialog() {
        imageDialog = new Dialog();
        imageDialog.setHeaderTitle("Toegevoegde foto's onder geselecteerde factuur");
        imageDialog.add(showImageSubView);
        Button cancelButton = new Button("Sluiten", e -> {
            try {
                selectedQuote.setCustomer(selectedCustomer);
                quoteBinder.writeBean(selectedQuote);
                selectedQuote.setProductList(selectProductSubView.getSelectedProductList());
                quoteService.save(selectedQuote);
            } catch (ValidationException ex) {
                throw new RuntimeException(ex);
            }
            quoteService.save(selectedQuote);
            imageDialog.close();
        });
        imageDialog.getFooter().add(cancelButton);
    }

    private void setUpShowImageButton() {
        showImageButton = new Button("Er zijn in deze offerte geen foto's toegevoegd!");
        showImageButton.setWidth("100%");

        showImageButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        showImageButton.addClickListener(e -> {
            if((selectedQuote.getImageList() != null) && (selectedQuote.getImageList().size() > 0)) {
                showImageSubView.setUser(UserFunction.ADMIN);
                showImageSubView.setSelectedWorkOrder(selectedQuote.getImageList());
            }
            else{
                Notification notification = Notification.show("Deze offerte bevat nog geen foto's!");
                notification.setPosition(Notification.Position.MIDDLE);
                notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
            }
        });
    }

    private void readNewQuote() {
        Quote newQuote = new Quote();
        newQuote.setQuoteDate(LocalDate.now());
        newQuote.setExpiryDate(LocalDate.now().plusDays(14));
        newQuote.setQuoteNumber(quoteServices.getNewQuoteNumber());
        List<Product>products = new ArrayList<>();
        Product product1 = new Product();
        product1.setBComment(true);
        product1.setSelectedAmount(null);
        product1.setSellPrice(0.0);
        product1.setTotalPrice(0.0);
        product1.setVat(VAT.EENENTWINTIG);
        product1.setTeamNumber(0);
        products.add(product1);
        newQuote.setProductList(products);
        selectedQuote = newQuote;
        quoteBinder.readBean(selectedQuote);
        selectProductSubView.setUserFunctionAndDocumentDate(UserFunction.ADMIN, selectedQuote.getQuoteDate());
        selectProductSubView.setSelectedCustmer(selectedCustomer);
        selectProductSubView.setSelectedProductList(selectedQuote.getProductList());
    }

    @PostConstruct
    private void init() {
        listener.addEventConsumer(event -> {
            // UI-thread safe update
            UI.getCurrent().access(() -> {
                try {
                    selectedQuote.setCustomer(selectedCustomer);
                    quoteBinder.writeBean(selectedQuote);
                    selectedQuote.setProductList(selectProductSubView.getSelectedProductList());
                    quoteService.save(selectedQuote);

                    //selectProductSubView.setSelectedProductList(selectedInvoice.getProductList());
                    //selectProductSubView.getSelectedProductGrid().getDataProvider().refreshAll();

                    if(event.getMessage().matches("Product verwijderd")){
                        Notification notification = Notification.show("Artikel is verwijderd.");
                        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    }
                    else if(event.getMessage().matches("Product toegevoegd")){
                        Notification notification = Notification.show("Artikel is toegevoegd.");
                        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    }
                    else{
                        //do nothing
                    }

                }
                catch (ValidationException e) {
                    Notification notification = Notification.show("Product niet toegevoegd gelieve eerst de hoofding in te vullen aub.");
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    selectProductSubView.getSelectedProductList().remove(selectProductSubView.getSelectedProductList().get(selectProductSubView.getSelectedProductList().size() - 1));
                    selectProductSubView.getSelectedProductGrid().getDataProvider().refreshAll();
                }
            });
        });
    }


    @Override
    public void setParameter(BeforeEvent beforeEvent,@OptionalParameter String parameter) {

        String id = beforeEvent.getRouteParameters()
                .get("id")
                .orElse(null);

        // query parameters
//        Map<String, List<String>> query = beforeEvent.getLocation()
//                .getQueryParameters()
//                .getParameters();
//
//        String proforma = query.getOrDefault("proforma", List.of("false")).get(0);
//        String workAddressStreet = query.getOrDefault("workAddressStreet", null).get(0);


        if ((id != null) && (id.length()>0) && (!(id.matches("none")))) {
            //Open WorkOrder by an other page and search WorkOrder by linkParameter.
            //So for every page with CurrentWorkOrderSubView in it that will open a clicked item in WorkOrderView
            linkParameter = id;
            Optional<Quote> optionalQuoteById = quoteService.getQuoteById(linkParameter);
            if (optionalQuoteById.isPresent()) {
                selectedQuote = optionalQuoteById.get();
                selectProductSubView.setSelectedProductList(selectedQuote.getProductList());
                selectProductSubView.setUserFunctionAndDocumentDate(UserFunction.ADMIN, selectedQuote.getQuoteDate());
                selectedCustomer = selectedQuote.getCustomer();
                selectProductSubView.setSelectedCustmer(selectedCustomer);
                quoteBinder.readBean(selectedQuote);
            } else {
                Notification show = Notification.show("Dit document kon niet worden geopend!");
                show.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        }
        else{
            linkParameter = null;
            readNewQuote();
        }
    }

    @Override
    public String getPageTitle() {
        return pageTitle;
    }

}
