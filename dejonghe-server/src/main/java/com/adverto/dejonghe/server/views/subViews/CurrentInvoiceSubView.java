package com.adverto.dejonghe.server.views.subViews;

import com.adverto.dejonghe.server.customEvents.GetSelectedInvoiceEvent;
import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.dbservices.DeviceService;
import com.adverto.dejonghe.common.dbservices.InvoiceService;
import com.adverto.dejonghe.common.dbservices.WorkOrderService;
import com.adverto.dejonghe.common.entities.customers.Customer;
import com.adverto.dejonghe.common.entities.enums.invoice.FINAL_INVOICE_STATUS;
import com.adverto.dejonghe.common.entities.installation.Device;
import com.adverto.dejonghe.common.entities.invoice.Invoice;
import com.adverto.dejonghe.server.services.invoice.InvoiceServices;
import com.adverto.dejonghe.server.services.invoice.InvoiceViewState;
import com.adverto.dejonghe.server.views.customers.CustomerView;
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
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import software.xdev.vaadin.daterange_picker.business.DateRangeModel;
import software.xdev.vaadin.daterange_picker.business.SimpleDateRange;
import software.xdev.vaadin.daterange_picker.business.SimpleDateRanges;
import software.xdev.vaadin.daterange_picker.ui.DateRangePicker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;

@Component
@Scope("prototype")
public class CurrentInvoiceSubView extends VerticalLayout {

    protected static final List<SimpleDateRange> DATERANGE_VALUES = Arrays.asList(SimpleDateRanges.allValues());
    private final WorkOrderService workOrderService;

    InvoiceService invoiceService;
    InvoiceServices invoiceServices;
    ApplicationEventPublisher eventPublisher;
    PaymentDialogView paymentDialogView;
    InvoiceViewState invoiceViewState;
    CustomerService customerService;
    DeviceService deviceService;

    Grid<Invoice> proFormaInvoiceGrid;
    HeaderRow headerRow;

    ComboBox<String> proformaStatusFilter;
    ComboBox<FINAL_INVOICE_STATUS> finalStatusFilter;
    TextField filterSubject;
    TextField filterNumber;
    TextField filterName;
    DateRangePicker dateRangePicker;
    Button clearFilterButton;

    Invoice selectedInvoice;
    Invoice selectedProformaToSetBackWorkOrders;
    Set<Invoice>invoiceListToRemove;
    Invoice invoiceBackToWorkOrder;
    Notification deleteInvoiceNotification;
    Notification addZeroPositionProductsToWorkAdressNotification;
    Notification addReminderNotification;
    Notification removeReminderNotification;

    Grid.Column<Invoice> columnProformaStatus;
    Grid.Column<Invoice> columnFinalStatus;
    Grid.Column<Invoice> totalNetColumn;
    Grid.Column<Invoice> totalVatColumn;
    Grid.Column<Invoice> totalAndVatColumn;
    Grid.Column<Invoice> toPayColumn;
    Grid.Column<Invoice> actionProformaColumn;
    Grid.Column<Invoice> actionInvoiceColumn;

    ListDataProvider<Invoice> dataProvider;

    NumberFormat df = NumberFormat.getNumberInstance(new Locale("nl", "BE"));
    Button dateRangeButton;
    Dialog dateRangeDialog;
    Button cancelButton;
    Button searchButton;

    Dialog paymentDialog;

    Double totalOpenAmount;
    Double totalAmount;
    Double totalVatAmount;
    Double totalNetAmount;

    Notification warningReOpenWorkOrderNotification;

    @Autowired
    public CurrentInvoiceSubView(InvoiceService invoiceService,
                                 InvoiceServices invoiceServices,
                                 ApplicationEventPublisher eventPublisher,
                                 PaymentDialogView paymentDialogView,
                                 InvoiceViewState invoiceViewState,
                                 WorkOrderService workOrderService,
                                 CustomerService customerService,
                                 DeviceService deviceService) {
        this.invoiceService = invoiceService;
        this.invoiceServices = invoiceServices;
        this.eventPublisher = eventPublisher;
        this.paymentDialogView = paymentDialogView;
        this.invoiceViewState = invoiceViewState;
        this.customerService = customerService;
        this.deviceService = deviceService;

        setUpNumberFormat();
        setUpfilters();
        createAddZeroPositionProductsToWorkAddress();
        createRemoveReminderNotification();
        createReminderNotification();
        createReportDelete();
        setUpDateRangeButton();
        setUpPaymentDialog();
        createDoubleProductNotification();
        this.add(setUpGrid());
        this.getStyle()
                .set("border-radius", "16px")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.9)");
        this.workOrderService = workOrderService;
    }

    private Notification createDoubleProductNotification() {
        warningReOpenWorkOrderNotification = new Notification();
        warningReOpenWorkOrderNotification.addThemeVariants(NotificationVariant.LUMO_WARNING);
        warningReOpenWorkOrderNotification.setPosition(Notification.Position.MIDDLE);
        Icon icon = VaadinIcon.WARNING.create();
        Button seperateButton = new Button("Zet werkbonnen terug en verwijder proforma",
                clickEvent -> {
                    if((selectedProformaToSetBackWorkOrders != null) && (selectedProformaToSetBackWorkOrders.getWorkOrderList() != null) && (selectedProformaToSetBackWorkOrders.getWorkOrderList().size() > 0)) {
                        selectedProformaToSetBackWorkOrders.getWorkOrderList().forEach(workOrder -> {workOrderService.save(workOrder);});
                        invoiceService.delete(selectedProformaToSetBackWorkOrders);
                        dataProvider.getItems().remove(selectedProformaToSetBackWorkOrders);
                        dataProvider.refreshAll();
                        warningReOpenWorkOrderNotification.close();
                    }
                    Notification.show("Werkbonnen zijn teruggezet en proforma is verwijderd");
                });
        seperateButton.getStyle().setMargin("0 0 0 var(--lumo-space-l)");
        Button cancelButton = new Button("Annuleer",
                clickEvent -> {
                    warningReOpenWorkOrderNotification.close();
                });
        cancelButton.getStyle().setMargin("0 0 0 var(--lumo-space-l)");

        Text text = new Text("Ben je zeker, deze proforma zal worden verwijderd !");
        var layout = new HorizontalLayout(icon,
                text, seperateButton, cancelButton);
        layout.setWidth("100%");
        layout.setHeight("100%");

        warningReOpenWorkOrderNotification.add(layout);

        return warningReOpenWorkOrderNotification;
    }

    private void setUpPaymentDialog() {
        paymentDialog = new Dialog();
        paymentDialog.setWidth("60%");
        paymentDialog.setHeight("60%");
        paymentDialog.add(paymentDialogView);
        Button saveButton = createSavePaymentButton(paymentDialog);
        Button cancelButton = new Button("Cancel", e -> paymentDialog.close());
        paymentDialog.getFooter().add(cancelButton);
        paymentDialog.getFooter().add(saveButton);
    }

    private Button createSavePaymentButton(Dialog paymentDialog) {

        Button saveButton = new Button("Bewaar", e -> {

            Optional<Double> optPayed =
                    invoiceServices.getTotalPayed(paymentDialogView.getInvoiceToSave());

            Optional<Double> optTotalNetAmount =
                    invoiceServices.calcTotalNetFromInvoice(paymentDialogView.getInvoiceToSave());

            Optional<Double> optTotalTaxAmount =
                    invoiceServices.calcTotalTaxFromInvoice(paymentDialogView.getInvoiceToSave());

            if (optPayed.isPresent()
                    && optTotalNetAmount.isPresent()
                    && optTotalTaxAmount.isPresent()) {

                BigDecimal payed = BigDecimal.valueOf(optPayed.get())
                        .setScale(2, RoundingMode.HALF_UP);

                BigDecimal totalNet = BigDecimal.valueOf(optTotalNetAmount.get())
                        .setScale(2, RoundingMode.HALF_UP);

                BigDecimal totalTax = BigDecimal.valueOf(optTotalTaxAmount.get())
                        .setScale(2, RoundingMode.HALF_UP);

                BigDecimal total = totalNet.add(totalTax)
                        .setScale(2, RoundingMode.HALF_UP);

                // full payed
                if (payed.compareTo(total) >= 0) {

                    paymentDialogView.getInvoiceToSave().setUnpaid(false);
                    paymentDialogView.getInvoiceToSave().setPartialPaid(false);
                    paymentDialogView.getInvoiceToSave().setPaid(true);
                    paymentDialogView.getInvoiceToSave().setPaymentDate(LocalDate.now());
                }

                // partial payed
                else if (payed.compareTo(BigDecimal.ZERO) > 0
                        && payed.compareTo(total) < 0) {

                    paymentDialogView.getInvoiceToSave().setUnpaid(false);
                    paymentDialogView.getInvoiceToSave().setPartialPaid(true);
                    paymentDialogView.getInvoiceToSave().setPaid(false);
                }

                // nothing payed
                else if (payed.compareTo(BigDecimal.ZERO) == 0) {

                    paymentDialogView.getInvoiceToSave().setUnpaid(true);
                    paymentDialogView.getInvoiceToSave().setPartialPaid(false);
                    paymentDialogView.getInvoiceToSave().setPaid(false);
                }
            }

            invoiceService.save(paymentDialogView.getInvoiceToSave());

            paymentDialog.close();

            dataProvider.refreshAll();

            refreshTotals();
        });

        return saveButton;
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
            addItemsToPendingWorkOrderGridFromFilter();
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

    private void setUpNumberFormat() {
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        df.setGroupingUsed(true);
    }

    private void setUpfilters() {

        clearFilterButton = new Button(VaadinIcon.CLOSE.create());

        proformaStatusFilter = new ComboBox<>();
        finalStatusFilter = new ComboBox<>();

        filterSubject = new TextField();
        filterSubject.setWidth("100%");
        filterSubject.setPlaceholder("Commentaar");

        filterNumber = new TextField();
        filterNumber.setWidth("100%");
        filterNumber.setPlaceholder("Nummer");

        filterName = new TextField();
        filterName.setWidth("100%");
        filterName.setPlaceholder("Naam,BTW-nr,Werfadres,Stad,Straat,artikelen");

        proformaStatusFilter.setItems("Geen Status","Te controleren","Goedgekeurd","Afgekeurd","PO in aanvraag");
        finalStatusFilter.setItems(FINAL_INVOICE_STATUS.values());
        finalStatusFilter.setItemLabelGenerator(FINAL_INVOICE_STATUS::getDiscription);
        finalStatusFilter.setWidth("100%");

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
            addItemsToPendingWorkOrderGridFromFilter();
        });

        proformaStatusFilter.addValueChangeListener(event -> {
            addItemsToPendingWorkOrderGridFromFilter();
        });

        finalStatusFilter.addValueChangeListener(event -> {
            invoiceViewState.setStatus(event.getValue());
            addItemsToPendingWorkOrderGridFromFilter();
        });

        filterSubject.addValueChangeListener(event -> {
            addItemsToPendingWorkOrderGridFromFilter();
        });

        filterNumber.addValueChangeListener(event -> {
            invoiceViewState.setNumber(event.getValue());
            addItemsToPendingWorkOrderGridFromFilter();
        });


        filterName.addValueChangeListener(event -> {
            invoiceViewState.setCustomer(event.getValue());
            addItemsToPendingWorkOrderGridFromFilter();
        });

        clearFilterButton.addClickListener(e -> {
            filterName.setValue("");
            filterNumber.setValue("");
            filterSubject.setValue("");
            finalStatusFilter.setValue(finalStatusFilter.getEmptyValue());
            proformaStatusFilter.setValue(proformaStatusFilter.getEmptyValue());
            dateRangePicker.setStart(LocalDate.now().withDayOfYear(1));
            dateRangePicker.setEnd(LocalDate.now());
        });
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

    private Grid<Invoice> setUpGrid() {
        proFormaInvoiceGrid = new Grid<>();
        proFormaInvoiceGrid.setPartNameGenerator(product -> {
            if((product.getFinalizeInvoice() != null ) && (product.getFinalizeInvoice() == true)) {
                return "";
            }
            else if((product.getBFinalInvoice() == false)){
                return "";
            }
            else{
                return "gray";
            }
        });
        proFormaInvoiceGrid.setClassNameGenerator(item -> item.getFinalizeInvoice() ? "grid-row-grey" : "grid-row-grey");
        proFormaInvoiceGrid.addClassName("rounded-tree");
        proFormaInvoiceGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        proFormaInvoiceGrid.addClassName("my-bold-footer");
        proFormaInvoiceGrid.appendFooterRow();
        Grid.Column<Invoice> columnCustomer = proFormaInvoiceGrid.addColumn(invoice -> {
            if(invoice.getCustomer() != null){
                if((invoice.getCustomer().getBProjectCustomer()) && (invoice.getWorkAddress() != null)){
                    try{
                        return invoice.getCustomer().getName() + " (" + String.valueOf(invoice.getProjectWorkAddress().getAddressName()) + ")";
                    }
                    catch (Exception e){
                        return invoice.getCustomer().getName();
                    }
                }
                return invoice.getCustomer().getName();
            }
            else{
                return "";
            }
        }).setHeader("Naam").setFlexGrow(4);
        Grid.Column<Invoice> columnInvoiceNumber = proFormaInvoiceGrid.addColumn(invoice -> {
            if((invoice.getBFinalInvoice() == true)){
                if(invoice.getFinalInvoiceNumber() != null){
                    String s = invoice.getFinalInvoiceNumber().toString();
                    return s.substring(0, 2) + "/" + s.substring(2);
                }
                else{
                    return "/";
                }
            }
            else{
                if(invoice.getInvoiceNumber() != null){
                    String s = invoice.getInvoiceNumber().toString();
                    return s.substring(0, 2) + "/" + s.substring(2);
                }
                else{
                    return "/";
                }
            }
        }).setHeader("Nummer").setFlexGrow(1).setSortable(true);
        Grid.Column<Invoice> columnInvoiceDate = proFormaInvoiceGrid.addColumn(invoice -> invoice.getInvoiceDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).setHeader("Datum").setFlexGrow(1);
        columnInvoiceDate.setFooter("Totalen : ");
        Grid.Column<Invoice> columnComment = proFormaInvoiceGrid.addColumn(invoice -> invoice.getDiscription()).setHeader("Omschrijving").setFlexGrow(12);
        columnComment.setVisible(false);

        totalNetColumn = proFormaInvoiceGrid.addColumn(invoice -> {
            Optional<Double> amount = invoiceServices.calcTotalNetFromInvoice(invoice);
            return "€ " + df.format(amount.get());
        }).setHeader("Netto").setFlexGrow(2);

        totalVatColumn = proFormaInvoiceGrid.addColumn(invoice -> {
            Optional<Double> vat = invoiceServices.calcTotalTaxFromInvoice(invoice);
            return "€ " + df.format(vat.get());
        }).setHeader("BTW").setFlexGrow(2);

        totalAndVatColumn = proFormaInvoiceGrid.addColumn(invoice -> {
            Optional<Double> amount = invoiceServices.calcTotalNetFromInvoice(invoice);
            Optional<Double> vat = invoiceServices.calcTotalTaxFromInvoice(invoice);

            if(amount.isPresent() && vat.isPresent()){
                invoice.setTotalAmountTempPlaceholder(amount.get()+vat.get());
            }
            return "€ " + df.format(invoice.getTotalAmountTempPlaceholder());
        }).setHeader("Totaal").setFlexGrow(2);

        toPayColumn = proFormaInvoiceGrid.addColumn(invoice -> {
            Optional<Double> amount = invoiceServices.calcTotalNetFromInvoice(invoice);
            Optional<Double> vat = invoiceServices.calcTotalTaxFromInvoice(invoice);
            Optional<Double>payed = invoiceServices.getTotalPayed(invoice);
            Double toPay = amount.get() + vat.get() - payed.get();
            return "€ " + df.format(toPay);
        }).setHeader("Openstaand").setFlexGrow(2);


//        Grid.Column<Invoice> columnSubject = proFormaInvoiceGrid.addComponentColumn(invoice -> {
//            TextArea textArea = new TextArea();
//            textArea.setWidth("100%");
//            textArea.setHeight("100%");
//            if(invoice.getDiscription() != null){
//                textArea.setValue(invoice.getDiscription());
//            }
//            else{
//                textArea.setValue("");
//            }
//            textArea.setReadOnly(true);
//            return textArea;
//        }).setHeader("Omschrijving").setAutoWidth(true);

        columnProformaStatus = proFormaInvoiceGrid.addComponentColumn(item -> {
            if((item.getRequestPoNumber() != null) && (item.getRequestPoNumber() == true)){
                HorizontalLayout horizontalLayout = new HorizontalLayout();
                horizontalLayout.setSpacing(false);
                horizontalLayout.setPadding(false);
                horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

                Span badge = new Span("PO in aanvraag");

                badge.getStyle().set(
                        "background",
                        "linear-gradient(135deg, rgba(160,160,160,0.9), rgba(100,100,100,0.9))"
                );

                badge.getStyle().set("color", "white");
                badge.getStyle().set("width", "150px");
                badge.getStyle().set("height", "40px");
                badge.getStyle().set("font-size", "16px");
                badge.getStyle().set("font-weight", "bold");
                badge.getStyle().set("border-radius", "35px");

                badge.getStyle().set("display", "flex");
                badge.getStyle().set("align-items", "center");
                badge.getStyle().set("justify-content", "center");

                badge.getStyle().set("box-shadow", "0 8px 24px rgba(120,120,120,0.45)");

                horizontalLayout.add(badge);
                return horizontalLayout;
            }
            if((item.getBApproved() != null) && (item.getBApproved() == true)){

                HorizontalLayout horizontalLayout = new HorizontalLayout();
                horizontalLayout.setSpacing(false);
                horizontalLayout.setPadding(false);
                horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

                Span badge = new Span("Goedgekeurd");
                badge.getElement().getThemeList().add("badge success");

                badge.getStyle().set("color", "white");
                badge.getStyle().set("width", "150px");
                badge.getStyle().set("height", "40px");
                badge.getStyle().set("font-size", "16px");
                badge.getStyle().set("border-radius", "35px");

                badge.getStyle().set("display", "flex");
                badge.getStyle().set("align-items", "center");
                badge.getStyle().set("justify-content", "center");

                badge.getStyle().set(
                        "background",
                        "linear-gradient(135deg, rgba(40,200,120,0.9), rgba(20,150,90,0.85))"
                );

                badge.getStyle().set("box-shadow", "0 8px 24px rgba(40,200,120,0.9)");


                badge.getStyle().set("font-weight", "600");
                horizontalLayout.add(badge);
                return horizontalLayout;
            }
            if((item.getBRejected() != null) && (item.getBRejected() == true)){

                HorizontalLayout horizontalLayout = new HorizontalLayout();
                horizontalLayout.setSpacing(false);
                horizontalLayout.setPadding(false);
                horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

                Span badge = new Span("Afgekeurd");
                badge.getElement().getThemeList().add("badge error");

                badge.getStyle().set("color", "white");
                badge.getStyle().set("width", "150px");
                badge.getStyle().set("height", "40px");
                badge.getStyle().set("font-size", "16px");
                badge.getStyle().set("border-radius", "35px");

                badge.getStyle().set("display", "flex");
                badge.getStyle().set("align-items", "center");
                badge.getStyle().set("justify-content", "center");

                badge.getStyle().set(
                        "background",
                        "linear-gradient(135deg, rgba(255,95,95,0.95), rgba(200,30,30,0.85))"
                );

                badge.getStyle().set("box-shadow", "0 8px 24px rgba(220,40,40,0.45)");

                badge.getStyle().set("font-weight", "600");

                badge.getStyle().set("border", "1px solid rgba(255,255,255,0.25)");
                horizontalLayout.add(badge);
                return horizontalLayout;
            }
            if((item.getToCheck() != null) && (item.getToCheck() == true)){

                HorizontalLayout horizontalLayout = new HorizontalLayout();
                horizontalLayout.setSpacing(false);
                horizontalLayout.setPadding(false);
                horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

                Span badge = new Span("Te controleren ");

                badge.getStyle().set("background",
                        "linear-gradient(135deg, rgba(0,140,255,0.85), rgba(0,80,220,0.85))");
                badge.getStyle().set("color", "white");
                badge.getStyle().set("width", "150px");
                badge.getStyle().set("height", "40px");
                badge.getStyle().set("font-size", "16px");
                badge.getStyle().set("border-radius", "35px");

                badge.getStyle().set("display", "flex");
                badge.getStyle().set("align-items", "center");
                badge.getStyle().set("justify-content", "center");

                badge.getStyle().set("font-weight", "600");
                badge.getStyle().set("box-shadow", "0 8px 24px rgba(0,80,220,0.85)");

                horizontalLayout.add(badge);

                return horizontalLayout;
            }
            else{
                Span badge = new Span(" ");
                badge.getStyle().set("background-color", "transparent");
                badge.getStyle().set("width", "150px");
                badge.getStyle().set("height", "40px");
                badge.getStyle().set("font-size", "18px");
                badge.getStyle().set("border-radius", "35px");
                return badge;
            }
        }).setHeader("Status");

         columnFinalStatus = proFormaInvoiceGrid.addComponentColumn(item -> {
             //first alwasy check if invoice is expired
             if(item.getPaid() == false){
                 if(LocalDate.now().isAfter(item.getExpiryDate())){
                     item.setExpired(true);
                 }
                 else{
                     item.setExpired(false);
                 }
             }
             else{
                 item.setExpired(false);
             }
             return getStatusBadgesforInvoice(item);
        }).setHeader("Status").setFlexGrow(2);

        actionProformaColumn = proFormaInvoiceGrid.addComponentColumn(item -> {
            return getActionProformaButton(item);
        }).setHeader("Actie");


         actionInvoiceColumn = proFormaInvoiceGrid.addComponentColumn(item -> {
             return getActionInvoiceButton(item);
         }).setHeader("Actie").setFlexGrow(2);

        proFormaInvoiceGrid.sort(GridSortOrder.desc(columnInvoiceNumber).build());

        proFormaInvoiceGrid.addItemClickListener(event -> {

            //if selectionColumn is aangeklikt -> don't trigger event!
            if(event.getColumn() == null){
                return;
            }
            //generate event so the invoice can be opened from motherView (only if invoice is not send to Billit.
            selectedInvoice = event.getItem();
            if((selectedInvoice.getBFinalInvoice() == true) & (selectedInvoice.getSendToBillit() != null) && (selectedInvoice.getSendToBillit() == true)){
                Notification.show("Deze factuur is al verstuurd via PEPPOL en kan niet meer worden gewijzigd");
            } else if ((selectedInvoice.getBFinalInvoice() == false) && (selectedInvoice.getRequestPoNumber() != null) && (selectedInvoice.getRequestPoNumber() == true)) {
                Notification.show("PO nummer voor deze proforma is in aanvraag");
            } else{
                eventPublisher.publishEvent(new GetSelectedInvoiceEvent(this, selectedInvoice));
            }
        });

        headerRow = proFormaInvoiceGrid.appendHeaderRow();
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.add(clearFilterButton,filterName);
        headerRow.getCell(columnCustomer).setComponent(headerLayout);
        headerRow.getCell(columnInvoiceNumber).setComponent(filterNumber);
        headerRow.getCell(columnComment).setComponent(filterSubject);
        headerRow.getCell(columnProformaStatus).setComponent(proformaStatusFilter);
        headerRow.getCell(columnFinalStatus).setComponent(finalStatusFilter);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(dateRangeButton);
        headerRow.getCell(columnInvoiceDate).setComponent(horizontalLayout);

        proFormaInvoiceGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        proFormaInvoiceGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        proFormaInvoiceGrid.addThemeVariants(GridVariant.LUMO_COMPACT);

        return proFormaInvoiceGrid;
    }

    private com.vaadin.flow.component.Component getActionProformaButton(Invoice item) {

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(false);
        horizontalLayout.setPadding(false);
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        MenuBar actionBar = new MenuBar();
        actionBar.addClassName("large-menubar");
        actionBar.addThemeVariants(MenuBarVariant.LUMO_DROPDOWN_INDICATORS);
        MenuItem actie = actionBar.addItem("Actie".toUpperCase());
        actie.getElement().getClassList().add("menu-as-button");
        actie.getSubMenu().addItem("Open PDF",openPdfForProforma(item));
        actie.getSubMenu().addItem("Stuur nr klant", sendProformaToCustomer(item));
        if((item.getRequestPoNumber() != null) && (item.getRequestPoNumber() == true)) {
            actie.getSubMenu().addItem("Ga nr klant", goToCustomer(item.getCustomer().getId()));
        }
        if((item.getRequestPoNumber() != null) && (item.getRequestPoNumber() == true)) {
            actie.getSubMenu().addItem("Activeer proforma", poReceivedForProforma(item));
        }
        if(((item.getPaid() == null) || (item.getPaid() == false)) && (item.getWorkOrderList() != null) && (item.getWorkOrderList().size() > 0)) {
            actie.getSubMenu().addItem("Open werkbonnen", openWorkOrdersForProforma(item));
        }
        if(((item.getSendToBillit() == null) || (item.getSendToBillit() == false)) && (item.getWorkOrderList() != null) && (item.getWorkOrderList().size() > 0)) {
            actie.getSubMenu().addItem("Zet werkbonnen terug", reOpenWorkOrdersInFinishedWorkOrders(item));
        }
        actie.getSubMenu().addItem("Maak factuur", makeInvoiceForProforma(item));

        horizontalLayout.add(actionBar);
        return horizontalLayout;
    }

    private ComponentEventListener<ClickEvent<MenuItem>> reOpenWorkOrdersInFinishedWorkOrders(Invoice item) {
        return  (event) -> {
            selectedProformaToSetBackWorkOrders = item;
            warningReOpenWorkOrderNotification.open();
        };
    }

    private ComponentEventListener<ClickEvent<MenuItem>> openWorkOrdersForProforma(Invoice item) {
        return  (event) -> {
            Map<String, String> params = Map.of(
                    "invoiceId", item.getId()
            );
            UI.getCurrent().navigate(
                    FinishedWorkorderView.class,
                    QueryParameters.simple(params)
            );
        };
    }

    private ComponentEventListener<ClickEvent<MenuItem>> poReceivedForProforma(Invoice item) {
        return  (event) -> {
            item.setRequestPoNumber(false);
            invoiceService.save(item);
            dataProvider.refreshAll();
        };
    }

    private ComponentEventListener<ClickEvent<MenuItem>> goToCustomer(String customerId) {
        return  (event) -> {
            UI.getCurrent().navigate(
                    CustomerView .class, customerId);
        };
    }




    private ComponentEventListener<ClickEvent<MenuItem>> makeInvoiceForProforma(Invoice item) {
        return  (event) -> {
            item.setBFinalInvoice(true);
            item.setFinalizeInvoice(true);
            item.setInvoiceDate(LocalDate.now());
            item.setExpiryDate(LocalDate.now().plusDays(14));
            item.setUnpaid(true);
            item.setFinalInvoiceNumber(invoiceServices.getNewFinalInvoiceNumber());
            invoiceService.save(item);
            dataProvider.getItems().remove(item);
            dataProvider.refreshAll();
        };
    }

    private ComponentEventListener<ClickEvent<MenuItem>> sendProformaToCustomer(Invoice item) {
        return  (event) -> {

            invoiceServices.generateInvoicePDF(item);

            String pdfUrl = "http://localhost:8080/pdf";

            getUI().ifPresent(ui ->
                    ui.getPage().executeJs("""
            const url = $0;

            fetch(url)
                .then(response => response.blob())
                .then(blob => {
                    const item = new ClipboardItem({
                        'application/pdf': blob
                    });
                    return navigator.clipboard.write([item]);
                })
                .then(() => {
                    console.log("PDF in klembord geplaatst");
                })
                .catch(err => {
                    console.error("Fout bij kopiëren:", err);
                });
        """, pdfUrl)
            );

            String ontvanger = "klant@email.be";
            String subject = "Proforma : " + item.getInvoiceNumber() + " aanvraag PO nummer";
            String body = """
                            Beste klant.
                            
       
                            
                            Alvast bedankt.
                            """;
            String mailtoLink = "mailto:" + ontvanger +
                    "?subject=" + encode(subject) +
                    "&body=" + encode(body);
            getUI().ifPresent(ui -> ui.getPage().open(mailtoLink));
            item.setRequestPoNumber(true);
            invoiceService.save(item);
            refreshTotals();
            dataProvider.refreshAll();
        };
    }

    private ComponentEventListener<ClickEvent<MenuItem>> openPdfForProforma(Invoice item) {
        return  (event) -> {
            invoiceServices.generateInvoicePDF(item);
        };
    }


    private com.vaadin.flow.component.Component getActionInvoiceButton(Invoice item) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(false);
        horizontalLayout.setPadding(false);
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        MenuBar actionBar = new MenuBar();
        actionBar.addClassName("large-menubar");
        actionBar.addThemeVariants(MenuBarVariant.LUMO_DROPDOWN_INDICATORS);
        MenuItem actie = actionBar.addItem("Actie".toUpperCase());
        actie.getElement().getClassList().add("menu-as-button");
        actie.getSubMenu().addItem("Open PDF",openPdf(item));
        actie.getSubMenu().addItem("Voeg betaling toe",addPayment(item));
        if(item.getCustomer() != null){
            actie.getSubMenu().addItem("Ga naar klant", goToCustomer(item.getCustomer().getId()));
        }
        if(((item.getPaid() == null) || (item.getPaid() == false)) && (item.getWorkOrderList() != null) && (item.getWorkOrderList().size() > 0)) {
            actie.getSubMenu().addItem("Open werkbonnen", openWorkOrdersForInvoice(item));
        }
        actie.getSubMenu().addItem("Stuur herinnering",sendFirstReminder(item));
        actie.getSubMenu().addItem("Terug nr proforma",returnInvoiceToProforma(item));
        actie.getSubMenu().addItem("Kopieer 0- positie artkelen",copy0Products(item));
        actie.getSubMenu().addItem("Stuur naar Billit",sendToBillit(item));


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

    private ComponentEventListener<ClickEvent<MenuItem>> returnInvoiceToProforma(Invoice item) {
        return  (event) -> {
            item.setFinalizeInvoice(false);
            item.setBFinalInvoice(false);
            invoiceService.save(item);
            dataProvider.getItems().remove(item);
            dataProvider.refreshAll();
            refreshTotals();
        };
    }

    private ComponentEventListener<ClickEvent<MenuItem>> sendToBillit(Invoice item) {
        return  (event) -> {
            Integer retInt;
            String retStr;

            String retVal = invoiceServices.generateInvoicePDFAndSendToBillit(item);
            //if there are 0 positions -> save item as a Device
            if(item.getProductList() != null && item.getProductList().size() > 0){
                invoiceServices.checkZeroPositionsAndSaveThemToWorkAddress(item);
            }

            try{
                retInt = Integer.valueOf(retVal);
            }
            catch(Exception e){
                retStr = retVal;
                Notification.show(retStr);
            }
            dataProvider.refreshItem(item);
            refreshTotals();
        };
    }

    private ComponentEventListener<ClickEvent<MenuItem>> copy0Products(Invoice item) {
        return  (event) -> {
            selectedInvoice = item;
            addZeroPositionProductsToWorkAdressNotification.open();
        };
    }

    private ComponentEventListener<ClickEvent<MenuItem>> sendFirstReminder(Invoice item) {
        return  (event) -> {
            selectedInvoice = item;
            addReminderNotification.open();

            invoiceServices.generateInvoicePDF(item);

            String pdfUrl = "http://localhost:8080/pdf";

            getUI().ifPresent(ui ->
                    ui.getPage().executeJs("""
            const url = $0;

            fetch(url)
                .then(response => response.blob())
                .then(blob => {
                    const item = new ClipboardItem({
                        'application/pdf': blob
                    });
                    return navigator.clipboard.write([item]);
                })
                .then(() => {
                    console.log("PDF in klembord geplaatst");
                })
                .catch(err => {
                    console.error("Fout bij kopiëren:", err);
                });
                """, pdfUrl)
            );

            String ontvanger = "klant@email.be";
            String subject = "Herinnering factuur : " + item.getInvoiceNumber();
            String body = """
                            Beste klant.
                            
                            Volgens onze boekhouding zou de factuur in bijlage nog niet betaald zijn.
                            
                            Mogen wij u vriendelijk verzoeken om dit te controleren en indien nodig in orde brengen.
                            
                            Alvast bedankt.
                            """;
            String mailtoLink = "mailto:" + ontvanger +
                    "?subject=" + encode(subject) +
                    "&body=" + encode(body);
            getUI().ifPresent(ui -> ui.getPage().open(mailtoLink));
            refreshTotals();
            dataProvider.refreshAll();
        };

    }

    private String encode(String value) {
        return URLEncoder
                .encode(value, StandardCharsets.UTF_8)
                .replace("+", "%20");
    }

    private ComponentEventListener<ClickEvent<MenuItem>> addPayment(Invoice item) {
        return  (event) -> {
            paymentDialogView.setInvoice(item);
            paymentDialog.open();
        };
    }

    private ComponentEventListener<ClickEvent<MenuItem>> openPdf(Invoice item) {
        return  (event) -> {
            invoiceServices.generateInvoicePDF(item);
        };
    }

    private com.vaadin.flow.component.Component getStatusBadgesforInvoice(Invoice item) {

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(false);
        horizontalLayout.setPadding(false);
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        //add amount of days next to it
        Period period;

        if((item.getFinalizeInvoice() == null) || (item.getFinalizeInvoice() == false)){
            Span badge = new Span("TE FINALISEREN");

            badge.getStyle().set(
                    "background",
                    "linear-gradient(135deg, rgba(160,160,160,0.9), rgba(100,100,100,0.9))"
            );

            badge.getStyle().set("color", "white");
            badge.getStyle().set("width", "150px");
            badge.getStyle().set("height", "40px");
            badge.getStyle().set("font-size", "16px");
            badge.getStyle().set("font-weight", "bold");
            badge.getStyle().set("border-radius", "35px");

            badge.getStyle().set("display", "flex");
            badge.getStyle().set("align-items", "center");
            badge.getStyle().set("justify-content", "center");

            badge.getStyle().set("box-shadow", "0 8px 24px rgba(120,120,120,0.45)");

            horizontalLayout.add(badge);
        }
        else if((item.getSendToBillit() != null) && (item.getSendToBillit() == false)){

            Span badge = new Span("TE VERSTUREN");

            badge.getStyle().set("background",
                    "linear-gradient(135deg, rgba(0,140,255,0.85), rgba(0,80,220,0.85))");
            badge.getStyle().set("color", "white");
            badge.getStyle().set("width", "150px");
            badge.getStyle().set("height", "40px");
            badge.getStyle().set("font-size", "16px");
            badge.getStyle().set("border-radius", "35px");

            badge.getStyle().set("display", "flex");
            badge.getStyle().set("align-items", "center");
            badge.getStyle().set("justify-content", "center");

            badge.getStyle().set("font-weight", "600");
            badge.getStyle().set("box-shadow", "0 8px 24px rgba(0,80,220,0.85)");

            horizontalLayout.add(badge);
        }
        else if(item.getBillitError() != null && item.getBillitError() == true){
            Span badge = new Span("Billit Error");

            badge.getStyle().set(
                    "background",
                    "linear-gradient(135deg, #1e1e1e, #000000)"
            );

            badge.getStyle().set("color", "white");
            badge.getStyle().set("width", "150px");
            badge.getStyle().set("height", "40px");
            badge.getStyle().set("font-size", "16px");
            badge.getStyle().set("font-weight", "bold");
            badge.getStyle().set("border-radius", "35px");

            badge.getStyle().set("display", "flex");
            badge.getStyle().set("align-items", "center");
            badge.getStyle().set("justify-content", "center");

            badge.getStyle().set("box-shadow", "0 8px 24px rgba(120,120,120,0.45)");

            horizontalLayout.add(badge);

        }
        else if((item.getPaid() != null) && (item.getPaid() == true)){
            Span badge = new Span("BETAALD");
            badge.getElement().getThemeList().add("badge success");

            badge.getStyle().set("color", "white");
            badge.getStyle().set("width", "150px");
            badge.getStyle().set("height", "40px");
            badge.getStyle().set("font-size", "16px");
            badge.getStyle().set("border-radius", "35px");

            badge.getStyle().set("display", "flex");
            badge.getStyle().set("align-items", "center");
            badge.getStyle().set("justify-content", "center");

            badge.getStyle().set(
                    "background",
                    "linear-gradient(135deg, rgba(40,200,120,0.9), rgba(20,150,90,0.85))"
            );

            badge.getStyle().set("box-shadow", "0 8px 24px rgba(40,200,120,0.9)");


            badge.getStyle().set("font-weight", "600");

            // now add Reminders
            if(item.getReminderLevel() != 0){
                Span reminderBadge = new Span(""+ item.getReminderLevel());
                reminderBadge.getElement().getThemeList().add("badge warning");

                reminderBadge.getStyle().set("color", "white");
                reminderBadge.getStyle().set("width", "75px");
                reminderBadge.getStyle().set("height", "40px");
                reminderBadge.getStyle().set("font-size", "16px");
                reminderBadge.getStyle().set("border-radius", "35px");

                reminderBadge.getStyle().set("display", "flex");
                reminderBadge.getStyle().set("align-items", "center");
                reminderBadge.getStyle().set("justify-content", "center");

                reminderBadge.getStyle().set(
                        "background",
                        "linear-gradient(135deg, rgba(230,150,30,0.95), rgba(200,110,0,0.90))"
                );

                reminderBadge.getStyle().set("box-shadow", "0 8px 24px rgba(220,130,20,0.45)");

                reminderBadge.getStyle().set("font-weight", "600");

                reminderBadge.getStyle().set("border", "1px solid rgba(255,255,255,0.25)");

                reminderBadge.addClickListener(event -> {
                    createRemoveReminderNotification().open();
                });

                horizontalLayout.add(badge,reminderBadge);
            }
            else{
                horizontalLayout.add(badge);
            }
        }
        else if ((item.getPartialPaid() != null) && (item.getPartialPaid() == true)){
            long totalDays =
                    ChronoUnit.DAYS.between(
                            LocalDate.now(),
                            item.getExpiryDate()
                    );
            if(totalDays < 0 ){
                Span badge = new Span("DEELS BET. "+ Math.abs(totalDays));
                badge.getElement().getThemeList().add("badge warning");

                badge.getStyle().set("color", "white");
                badge.getStyle().set("width", "150px");
                badge.getStyle().set("height", "40px");
                badge.getStyle().set("font-size", "16px");
                badge.getStyle().set("border-radius", "35px");

                badge.getStyle().set("display", "flex");
                badge.getStyle().set("align-items", "center");
                badge.getStyle().set("justify-content", "center");

                badge.getStyle().set(
                        "background",
                        "linear-gradient(135deg, rgba(230,150,30,0.95), rgba(200,110,0,0.90))"
                );

                badge.getStyle().set("box-shadow", "0 8px 24px rgba(220,130,20,0.45)");

                badge.getStyle().set("font-weight", "600");

                badge.getStyle().set("border", "1px solid rgba(255,255,255,0.25)");

                // now add Reminders
                if(item.getReminderLevel() != 0){
                    Span reminderBadge = new Span(""+ item.getReminderLevel());
                    reminderBadge.getElement().getThemeList().add("badge warning");

                    reminderBadge.getStyle().set("color", "white");
                    reminderBadge.getStyle().set("width", "75px");
                    reminderBadge.getStyle().set("height", "40px");
                    reminderBadge.getStyle().set("font-size", "16px");
                    reminderBadge.getStyle().set("border-radius", "35px");

                    reminderBadge.getStyle().set("display", "flex");
                    reminderBadge.getStyle().set("align-items", "center");
                    reminderBadge.getStyle().set("justify-content", "center");

                    reminderBadge.getStyle().set(
                            "background",
                            "linear-gradient(135deg, rgba(230,150,30,0.95), rgba(200,110,0,0.90))"
                    );

                    reminderBadge.getStyle().set("box-shadow", "0 8px 24px rgba(220,130,20,0.45)");

                    reminderBadge.getStyle().set("font-weight", "600");

                    reminderBadge.getStyle().set("border", "1px solid rgba(255,255,255,0.25)");

                    reminderBadge.addClickListener(event -> {
                        createRemoveReminderNotification().open();
                    });

                    horizontalLayout.add(badge,reminderBadge);
                }
                else{
                    horizontalLayout.add(badge);
                }
            }
            else{
                Span badge = new Span("DEELS BET.");
                badge.getElement().getThemeList().add("badge warning");

                badge.getStyle().set("color", "white");
                badge.getStyle().set("width", "150px");
                badge.getStyle().set("height", "40px");
                badge.getStyle().set("font-size", "16px");
                badge.getStyle().set("border-radius", "35px");

                badge.getStyle().set("display", "flex");
                badge.getStyle().set("align-items", "center");
                badge.getStyle().set("justify-content", "center");

                badge.getStyle().set(
                        "background",
                        "linear-gradient(135deg, rgba(230,150,30,0.95), rgba(200,110,0,0.90))"
                );

                badge.getStyle().set("box-shadow", "0 8px 24px rgba(220,130,20,0.45)");

                badge.getStyle().set("font-weight", "600");

                badge.getStyle().set("border", "1px solid rgba(255,255,255,0.25)");
                horizontalLayout.add(badge);
            }

        }
        else if ((item.getUnpaid() != null) && (item.getUnpaid() == true)){
            long totalDays =
                    ChronoUnit.DAYS.between(
                            LocalDate.now(),
                            item.getExpiryDate()
                    );
            if(totalDays < 0 ){
                Span badge = new Span(""+ Math.abs(totalDays));;
                badge.getElement().getThemeList().add("badge error");

                badge.getStyle().set("color", "white");
                badge.getStyle().set("width", "150px");
                badge.getStyle().set("height", "40px");
                badge.getStyle().set("font-size", "16px");
                badge.getStyle().set("border-radius", "35px");

                badge.getStyle().set("display", "flex");
                badge.getStyle().set("align-items", "center");
                badge.getStyle().set("justify-content", "center");

                badge.getStyle().set(
                        "background",
                        "linear-gradient(135deg, rgba(255,95,95,0.95), rgba(200,30,30,0.85))"
                );

                badge.getStyle().set("box-shadow", "0 8px 24px rgba(220,40,40,0.45)");

                badge.getStyle().set("font-weight", "600");

                badge.getStyle().set("border", "1px solid rgba(255,255,255,0.25)");

                // now add Reminders
                if(item.getReminderLevel() != 0){
                    Span reminderBadge = new Span(""+ item.getReminderLevel());
                    reminderBadge.getElement().getThemeList().add("badge warning");

                    reminderBadge.getStyle().set("color", "white");
                    reminderBadge.getStyle().set("width", "75px");
                    reminderBadge.getStyle().set("height", "40px");
                    reminderBadge.getStyle().set("font-size", "16px");
                    reminderBadge.getStyle().set("border-radius", "35px");

                    reminderBadge.getStyle().set("display", "flex");
                    reminderBadge.getStyle().set("align-items", "center");
                    reminderBadge.getStyle().set("justify-content", "center");

                    reminderBadge.getStyle().set(
                            "background",
                            "linear-gradient(135deg, rgba(230,150,30,0.95), rgba(200,110,0,0.90))"
                    );

                    reminderBadge.getStyle().set("box-shadow", "0 8px 24px rgba(220,130,20,0.45)");

                    reminderBadge.getStyle().set("font-weight", "600");

                    reminderBadge.getStyle().set("border", "1px solid rgba(255,255,255,0.25)");

                    reminderBadge.addClickListener(event -> {
                        createRemoveReminderNotification().open();
                    });

                    horizontalLayout.add(badge,reminderBadge);
                }
                else{
                    horizontalLayout.add(badge);
                }
            }
            else{
                Span badge = new Span("ONBETAALD");
                badge.getElement().getThemeList().add("badge error");

                badge.getStyle().set("color", "white");
                badge.getStyle().set("width", "150px");
                badge.getStyle().set("height", "40px");
                badge.getStyle().set("font-size", "16px");
                badge.getStyle().set("border-radius", "35px");

                badge.getStyle().set("display", "flex");
                badge.getStyle().set("align-items", "center");
                badge.getStyle().set("justify-content", "center");

                badge.getStyle().set(
                        "background",
                        "linear-gradient(135deg, rgba(255,95,95,0.85), rgba(200,30,30,0.85))"
                );

                badge.getStyle().set("box-shadow", "0 8px 24px rgba(220,40,40,0.45)");

                badge.getStyle().set("font-weight", "600");

                badge.getStyle().set("border", "1px solid rgba(255,255,255,0.25)");
                horizontalLayout.add(badge);
            }
        }
        return horizontalLayout;
    }

    public void refreshTotals(){
//        List<Invoice> invoiceList = dataProvider.getItems().stream().toList();
//        for(Invoice invoice : invoiceList){
//            Optional<Invoice> foundInvoice = invoiceService.getInvoiceById(invoice.getId());
//                invoice.setFinalizeInvoice(foundInvoice.get().getFinalizeInvoice());
//                invoice.setBillitNumber(foundInvoice.get().getBillitNumber());
//                invoice.setSendToBillit(foundInvoice.get().getSendToBillit());
//                invoice.setPaid(foundInvoice.get().getPaid());
//                invoice.setUnpaid(foundInvoice.get().getUnpaid());
//                invoice.setBFinalInvoice(foundInvoice.get().getBFinalInvoice());
//                invoice.setPartialPaid(foundInvoice.get().getPartialPaid());
//                proFormaInvoiceGrid.getDataProvider().refreshItem(invoice);
//        }

        toPayColumn.setFooter(String.valueOf("€ " + df.format(invoiceServices.getTotalToBePayedFromInvoiceList(dataProvider.fetch(new Query<>()).toList()).get())));
        totalAndVatColumn.setFooter(String.valueOf("€ " + df.format(dataProvider.fetch(new Query<>()).mapToDouble(x -> (invoiceServices.calcTotalNetFromInvoice(x).get() + invoiceServices.calcTotalTaxFromInvoice(x).get())).sum())));
        totalVatColumn.setFooter(String.valueOf("€ " + df.format(dataProvider.fetch(new Query<>()).mapToDouble(x -> invoiceServices.calcTotalTaxFromInvoice(x).get()).sum())));
        totalNetColumn.setFooter(String.valueOf("€ " + df.format(dataProvider.fetch(new Query<>()).mapToDouble(x -> invoiceServices.calcTotalNetFromInvoice(x).get()).sum())));
    }

    public void addItemsToProformaGrid(List<Invoice>invoiceList){
        if((invoiceList != null) && (invoiceList.size() > 0)){
            proFormaInvoiceGrid.setVisible(true);
            dataProvider = new ListDataProvider<>(invoiceList);
            proFormaInvoiceGrid.setDataProvider(dataProvider);

            toPayColumn.setFooter(String.valueOf("€ " + df.format(invoiceServices.getTotalToBePayedFromInvoiceList(dataProvider.getItems().stream().toList()).get())));
            totalAndVatColumn.setFooter(String.valueOf("€ " + df.format(dataProvider.getItems().stream().mapToDouble(x -> (invoiceServices.calcTotalNetFromInvoice(x).get() + invoiceServices.calcTotalTaxFromInvoice(x).get())).sum())));
            totalVatColumn.setFooter(String.valueOf("€ " + df.format(dataProvider.getItems().stream().mapToDouble(x -> invoiceServices.calcTotalTaxFromInvoice(x).get()).sum())));
            totalNetColumn.setFooter(String.valueOf("€ " + df.format(dataProvider.getItems().stream().mapToDouble(x -> invoiceServices.calcTotalNetFromInvoice(x).get()).sum())));
        }
        else{
            proFormaInvoiceGrid.setVisible(false);
            Notification notification = Notification.show("Geen Proforma Facturen");
            notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
        }
    }

    public void addItemsToPendingWorkOrderGridFromFilter(){
        dataProvider.clearFilters();
        dataProvider.addFilter(item -> {

            boolean nameOk = true;
            boolean numberOk = true;
            boolean discriptionOk = true;
            boolean statusOk = true;
            boolean internalStatus = true;
            boolean dateSelectOK = true;
            boolean requestPO = true;

            if((!filterNumber.getValue().isEmpty()) && (item.getInvoiceNumber() != null)) {
                numberOk = item.getInvoiceNumber().toString().toLowerCase().contains(filterNumber.getValue().toLowerCase());
            }

            if (!filterName.getValue().isEmpty()) {
                nameOk =
                        (item.getProductList().stream().filter(x -> x.getInternalName() != null).anyMatch(x -> x.getInternalName().toLowerCase().contains(filterName.getValue().toLowerCase()))) ||
                        (item.getWorkAddress().getStreet().toLowerCase().contains(filterName.getValue().toLowerCase())) ||
                        (item.getWorkAddress().getCity().toLowerCase().contains(filterName.getValue().toLowerCase())) ||
                        (item.getWorkAddress().getAddressName().toLowerCase().contains(filterName.getValue().toLowerCase())) ||
                        (item.getCustomer().getName().toLowerCase().contains(filterName.getValue().toLowerCase())) ||
                        (item.getCustomer().getVatNumber().toLowerCase().contains(filterName.getValue().toLowerCase()));
            }

            if(!filterSubject.getValue().isEmpty()){
                discriptionOk = item.getDiscription() != null && item.getDiscription().toLowerCase().contains(filterSubject.getValue().toLowerCase());
            }

            if (finalStatusFilter.getValue() != null) {
                statusOk =  ((item.getFinalizeInvoice() == false) && finalStatusFilter.getValue().equals(FINAL_INVOICE_STATUS.TO_FINALISE))||
                            ((item.getFinalizeInvoice() == true) && (item.getSendToBillit() == false) && finalStatusFilter.getValue().equals(FINAL_INVOICE_STATUS.TO_SEND))||
                            ((item.getUnpaid() == true) && (item.getExpired() == false) && (item.getFinalizeInvoice() == true) && (item.getSendToBillit() == true) && finalStatusFilter.getValue().equals(FINAL_INVOICE_STATUS.OPEN))||
                            ((item.getUnpaid() == true) && (item.getExpired() == true) && (item.getFinalizeInvoice() == true) && (item.getSendToBillit() == true) && finalStatusFilter.getValue().equals(FINAL_INVOICE_STATUS.EXPIRED))||
                            ((item.getPartialPaid() == true) && (item.getFinalizeInvoice() == true) && (item.getSendToBillit() == true) && finalStatusFilter.getValue().equals(FINAL_INVOICE_STATUS.PARTIAL_PAID))||
                            ((item.getPaid() == true) && (item.getFinalizeInvoice() == true) && (item.getSendToBillit() == true) && finalStatusFilter.getValue().equals(FINAL_INVOICE_STATUS.PAID));
            }

            if(proformaStatusFilter.getValue() != null){
                internalStatus = (proformaStatusFilter.getValue().matches("Geen Status") && (item.getToCheck().equals(false))&& item.getBApproved().equals(false)&& item.getBRejected().equals(false)&& item.getRequestPoNumber().equals(false))||
                        (proformaStatusFilter.getValue().matches("Te controleren") && (item.getToCheck().equals(true)))||
                        (proformaStatusFilter.getValue().matches("Goedgekeurd") && (item.getBApproved().equals(true)))||
                        (proformaStatusFilter.getValue().matches("Afgekeurd") && (item.getBRejected().equals(true)))||
                        (proformaStatusFilter.getValue().matches("PO in aanvraag") && (item.getRequestPoNumber().equals(true)));
            }

            if(dateRangePicker.getValue() != null){
                dateSelectOK =
                        (item.getInvoiceDate().isEqual(dateRangePicker.getStart()) || item.getInvoiceDate().isAfter(dateRangePicker.getStart())) &&
                                (item.getInvoiceDate().isEqual(dateRangePicker.getEnd())   || item.getInvoiceDate().isBefore(dateRangePicker.getEnd()));
            }
            return numberOk && nameOk && discriptionOk && statusOk && internalStatus && dateSelectOK;
        });

        refreshTotals();
        dataProvider.refreshAll();

    }

    public Notification createReminderNotification(){
        addReminderNotification = new Notification();
        addReminderNotification.setPosition(Notification.Position.MIDDLE);
        addReminderNotification.addThemeVariants(NotificationVariant.LUMO_WARNING);

        Icon icon = VaadinIcon.WARNING.create();
        Button retryBtn = new Button("Annuleer",
                clickEvent -> addReminderNotification.close());
        retryBtn.getStyle().setMargin("0 0 0 var(--lumo-space-l)");

        var layout = new HorizontalLayout(icon,
                new Text("Ben je zeker dat je een herinnering wilt sturen"), retryBtn,
                createReminder(addReminderNotification));
        layout.setAlignItems(Alignment.CENTER);

        addReminderNotification.add(layout);

        return addReminderNotification;
    }

    private com.vaadin.flow.component.Component createReminder(Notification addReminderNotification) {
        Button removeBtn = new Button("Stuur herinnering",
                clickEvent -> {
                    invoiceServices.addReminder(selectedInvoice);
                    invoiceService.save(selectedInvoice);
                    dataProvider.refreshAll();
                    refreshTotals();
                    addReminderNotification.close();
                });
        removeBtn.addThemeVariants(LUMO_TERTIARY_INLINE);
        return removeBtn;
    }

    public Notification createRemoveReminderNotification(){
        removeReminderNotification = new Notification();
        removeReminderNotification.setPosition(Notification.Position.MIDDLE);
        removeReminderNotification.addThemeVariants(NotificationVariant.LUMO_WARNING);

        Icon icon = VaadinIcon.WARNING.create();
        Button retryBtn = new Button("Annuleer",
                clickEvent -> removeReminderNotification.close());
        retryBtn.getStyle().setMargin("0 0 0 var(--lumo-space-l)");

        var layout = new HorizontalLayout(icon,
                new Text("Ben je zeker dat je een herinnering wilt verwijderen"), retryBtn,
                createRemoveReminder(removeReminderNotification));
        layout.setAlignItems(Alignment.CENTER);

        removeReminderNotification.add(layout);

        return removeReminderNotification;
    }

    private com.vaadin.flow.component.Component createRemoveReminder(Notification removeReminderNotification) {
        Button removeBtn = new Button("Verwijder herinnering",
                clickEvent -> {
                    invoiceServices.removeReminder(selectedInvoice);
                    invoiceService.save(selectedInvoice);
                    dataProvider.refreshAll();
                    refreshTotals();
                    removeReminderNotification.close();
                });
        removeBtn.addThemeVariants(LUMO_TERTIARY_INLINE);
        return removeBtn;
    }

    public Notification createAddZeroPositionProductsToWorkAddress(){
        addZeroPositionProductsToWorkAdressNotification = new Notification();
        addZeroPositionProductsToWorkAdressNotification.setPosition(Notification.Position.MIDDLE);
        addZeroPositionProductsToWorkAdressNotification.addThemeVariants(NotificationVariant.LUMO_WARNING);

        Icon icon = VaadinIcon.WARNING.create();
        Button retryBtn = new Button("Annuleer",
                clickEvent -> addZeroPositionProductsToWorkAdressNotification.close());
        retryBtn.getStyle().setMargin("0 0 0 var(--lumo-space-l)");

        var layout = new HorizontalLayout(icon,
                new Text("Ben je zeker dat je 0- postitie artikelen onder de klant (werfadres) wilt bewaren"), retryBtn,
                createAddZeroProducts(addZeroPositionProductsToWorkAdressNotification));
        layout.setAlignItems(Alignment.CENTER);

        addZeroPositionProductsToWorkAdressNotification.add(layout);

        return addZeroPositionProductsToWorkAdressNotification;
    }

    private com.vaadin.flow.component.Component createAddZeroProducts(Notification addZeroPositionProductsToWorkAdressNotification) {
        Button removeBtn = new Button("Kopieer de 0- postitie artikelen",
                clickEvent -> {
                    invoiceServices.checkZeroPositionsAndSaveThemToWorkAddress(selectedInvoice);
                    addZeroPositionProductsToWorkAdressNotification.close();
                });
        removeBtn.addThemeVariants(LUMO_TERTIARY_INLINE);
        return removeBtn;
    }


    public Notification createReportDelete() {
        deleteInvoiceNotification = new Notification();
        deleteInvoiceNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Icon icon = VaadinIcon.WARNING.create();
        Button retryBtn = new Button("Annuleer",
                clickEvent -> deleteInvoiceNotification.close());
        retryBtn.getStyle().setMargin("0 0 0 var(--lumo-space-l)");

        var layout = new HorizontalLayout(icon,
                new Text("Ben je zeker dat je deze werkbon wil wissen?"), retryBtn,
                createCloseBtn(deleteInvoiceNotification));
        layout.setAlignItems(Alignment.CENTER);

        deleteInvoiceNotification.add(layout);

        return deleteInvoiceNotification;
    }



    public Button createCloseBtn(Notification notification) {
        Button removeBtn = new Button(VaadinIcon.TRASH.create(),
                clickEvent -> {
                    if((invoiceListToRemove != null) && (!invoiceListToRemove.isEmpty())){
                        try{
                            for(Invoice invoice: invoiceListToRemove){
                                dataProvider.getItems()
                                        .removeIf(i -> i.getId().equals(invoice.getId()));
                            }
                            proFormaInvoiceGrid.getDataProvider().refreshAll();
                        }
                        catch(Exception e){
                        }
                        for(Invoice invoice: invoiceListToRemove){
                            invoiceService.delete(invoice);
                        }
                        Notification.show("Geselecteerde proforma's zijn verwijderd");
                    }
                    else{
                        Notification.show("Geen Proforma's te verwijderen");
                    }
                    notification.close();
                });
        removeBtn.addThemeVariants(LUMO_TERTIARY_INLINE);
        return removeBtn;
    }

    public void viewAsProformaInvoices(){
        columnProformaStatus.setVisible(true);
        columnFinalStatus.setVisible(false);
        totalAndVatColumn.setVisible(true);
        actionInvoiceColumn.setVisible(false);
        actionProformaColumn.setVisible(true);
        totalVatColumn.setVisible(true);
        totalNetColumn.setVisible(true);
        toPayColumn.setVisible(false);
        //proFormaInvoiceGrid.addClassName("my-grid-no-footer");
    }

    public void viewAsFinalWorkOrders(){
        columnProformaStatus.setVisible(false);
        columnFinalStatus.setVisible(true);
        totalAndVatColumn.setVisible(true);
        actionInvoiceColumn.setVisible(true);
        actionProformaColumn.setVisible(false);
        totalVatColumn.setVisible(true);
        totalNetColumn.setVisible(true);
        toPayColumn.setVisible(true);
        proFormaInvoiceGrid.removeClassName("my-grid-no-footer");
    }

    public void tryToSetPreviousFilters(){
        refreshTotals();
        dataProvider.refreshAll();

        if((invoiceViewState.getCustomer() != null) && (invoiceViewState.getCustomer().length() > 0)) {
            filterName.setValue(invoiceViewState.getCustomer());
        }
        if((invoiceViewState.getNumber() != null) && (invoiceViewState.getNumber().length() > 0)) {
            filterNumber.setValue(invoiceViewState.getNumber());
        }
        if((invoiceViewState.getStatus() != null)) {
            finalStatusFilter.setValue(invoiceViewState.getStatus());
        }
    }

    public void showBackToWorkOrderNotification() {
        if(proFormaInvoiceGrid.getSelectedItems().size() == 1){
            invoiceBackToWorkOrder = proFormaInvoiceGrid.getSelectedItems().stream().findFirst().get();

        }
        else{
            Notification.show("Gelieve exact 1 factuur te selecteren om terug naar Werkbon te zetten");
        }
    }

    public void showRemoveNotification() {
        if(proFormaInvoiceGrid.getSelectedItems().size() >= 0){
            invoiceListToRemove = proFormaInvoiceGrid.getSelectedItems();
            deleteInvoiceNotification.open();
        }
        else{
            Notification.show("Gelieve minimum 1 factuur te selecteren om te verwijderen");
        }
    }
}
