package com.adverto.dejonghe.server.views.subViews;

import com.adverto.dejonghe.server.customEvents.GetSelectedQuoteEvent;
import com.adverto.dejonghe.common.dbservices.QuoteService;
import com.adverto.dejonghe.common.entities.invoice.Invoice;
import com.adverto.dejonghe.common.entities.quote.Quote;
import com.adverto.dejonghe.server.services.quote.QuoteServices;
import com.adverto.dejonghe.server.services.quote.QuoteViewState;
import com.adverto.dejonghe.server.views.customers.CustomerView;
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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import software.xdev.vaadin.daterange_picker.business.DateRangeModel;
import software.xdev.vaadin.daterange_picker.business.SimpleDateRange;
import software.xdev.vaadin.daterange_picker.business.SimpleDateRanges;
import software.xdev.vaadin.daterange_picker.ui.DateRangePicker;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;

@Component
@Scope("prototype")
public class CurrentQuoteSubView extends VerticalLayout {

    protected static final List<SimpleDateRange> DATERANGE_VALUES = Arrays.asList(SimpleDateRanges.allValues());

    QuoteService quoteService;
    QuoteServices quoteServices;
    ApplicationEventPublisher eventPublisher;
    QuoteViewState quoteViewState;

    Grid<Quote> quoteGrid;
    HeaderRow headerRow;

    ComboBox<String> quoteStatusFilter;
    TextField filterSubject;
    TextField filterNumber;
    TextField filterName;
    DateRangePicker dateRangePicker;
    Button clearFilterButton;

    Quote selectedQuote;
    Set<Quote> quoteListToRemove;
    Notification deleteQuoteNotification;

    Grid.Column<Quote> columnProformaStatus;
    Grid.Column<Quote> columnFinalStatus;
    Grid.Column<Quote> totalNetColumn;
    //Grid.Column<Quote> totalVatColumn;
    //Grid.Column<Quote> totalAndVatColumn;
    Grid.Column<Quote> actionInvoiceColumn;

    ListDataProvider<Quote> dataProvider;

    NumberFormat df = NumberFormat.getNumberInstance(new Locale("nl", "BE"));
    Button dateRangeButton;
    Dialog dateRangeDialog;
    Button cancelButton;
    Button searchButton;

    @Autowired
    public CurrentQuoteSubView(QuoteService quoteService,
                               QuoteServices quoteServices,
                               ApplicationEventPublisher eventPublisher,
                               QuoteViewState quoteViewState) {
        this.quoteService = quoteService;
        this.quoteServices = quoteServices;
        this.eventPublisher = eventPublisher;
        this.quoteViewState = quoteViewState;

        setUpNumberFormat();
        setUpfilters();
        createReportDelete();
        setUpDateRangeButton();
        this.add(setUpGrid());
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
            addItemsToQuoteGridFromFilter();
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

        quoteStatusFilter = new ComboBox<>();

        filterSubject = new TextField();
        filterSubject.setWidth("100%");
        filterSubject.setPlaceholder("Commentaar");

        filterNumber = new TextField();
        filterNumber.setWidth("100%");
        filterNumber.setPlaceholder("Nummer");

        filterName = new TextField();
        filterName.setWidth("100%");
        filterName.setPlaceholder("Naam,BTW-nr,Werfadres,Stad,Straat");

        quoteStatusFilter.setItems("","Geen Status","Te controleren","Goedgekeurd","Afgekeurd","Verzonden");

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
            addItemsToQuoteGridFromFilter();
        });

        quoteStatusFilter.addValueChangeListener(event -> {
            addItemsToQuoteGridFromFilter();
        });

        filterSubject.addValueChangeListener(event -> {
            addItemsToQuoteGridFromFilter();
        });

        filterNumber.addValueChangeListener(event -> {
            quoteViewState.setNumber(event.getValue());
            addItemsToQuoteGridFromFilter();
        });


        filterName.addValueChangeListener(event -> {
            quoteViewState.setCustomer(event.getValue());
            addItemsToQuoteGridFromFilter();
        });

        clearFilterButton.addClickListener(e -> {
            filterName.setValue("");
            filterNumber.setValue("");
            filterSubject.setValue("");
            quoteStatusFilter.setValue(quoteStatusFilter.getEmptyValue());
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

    private Grid<Quote> setUpGrid() {
        quoteGrid = new Grid<>();
        quoteGrid.setPartNameGenerator(quote -> {
            if((quote.getFinalizeQuote() != null ) && (quote.getFinalizeQuote() == true)) {
                return "";
            }
            else if((quote.getFinalizeQuote() == false)){
                return "";
            }
            else{
                return "gray";
            }
        });
        quoteGrid.setPartNameGenerator(item -> item.getFinalizeQuote() ? "grid-row-grey" : "grid-row-grey");
        quoteGrid.addClassName("rounded-tree");
        quoteGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        quoteGrid.addClassName("my-bold-footer");
        quoteGrid.appendFooterRow();
        Grid.Column<Quote> columnCustomer = quoteGrid.addColumn(invoice -> invoice.getCustomer().getName()).setHeader("Naam").setFlexGrow(4);
        Grid.Column<Quote> columnInvoiceNumber = quoteGrid.addColumn(quote -> {
            if(quote.getQuoteNumber() != null){
                String s = quote.getQuoteNumber().toString();
                return s.substring(0, 2) + "/" + s.substring(2);
            }
            else{
                return "/";
            }
        }).setHeader("Nummer").setFlexGrow(1).setSortable(true);
        Grid.Column<Quote> columnInvoiceDate = quoteGrid.addColumn(quote -> quote.getQuoteDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).setHeader("Datum").setFlexGrow(1);
        columnInvoiceDate.setFooter("Totalen : ");
        Grid.Column<Quote> columnComment = quoteGrid.addColumn(quote -> quote.getDiscription()).setHeader("Omschrijving").setFlexGrow(12);
        columnComment.setVisible(false);

        totalNetColumn = quoteGrid.addColumn(quote -> {
            Optional<Double> amount = quoteServices.calcTotalNetFromQuote(quote);
            return "€ " + df.format(amount.get());
        }).setHeader("Netto (excl BTW.)").setFlexGrow(2);

//        totalVatColumn = quoteGrid.addColumn(quote -> {
//            Optional<Double> vat = quoteServices.calcTotalTaxFromQuote(quote);
//            return "€ " + df.format(vat.get());
//        }).setHeader("BTW").setFlexGrow(2);
//
//        totalAndVatColumn = quoteGrid.addColumn(quote -> {
//            Optional<Double> amount = quoteServices.calcTotalNetFromQuote(quote);
//            Optional<Double> vat = quoteServices.calcTotalTaxFromQuote(quote);
//
//            if(amount.isPresent() && vat.isPresent()){
//                quote.setTotalAmountTempPlaceholder(amount.get()+vat.get());
//            }
//            return "€ " + df.format(quote.getTotalAmountTempPlaceholder());
//        }).setHeader("Totaal").setFlexGrow(2);

        columnProformaStatus = quoteGrid.addComponentColumn(item -> {
            if((item.getBApproved() != null) && (item.getBApproved() == true)){

                HorizontalLayout horizontalLayout = new HorizontalLayout();
                horizontalLayout.setSpacing(false);
                horizontalLayout.setPadding(false);
                horizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);

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
                horizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);

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
                horizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);

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
            if((item.getBSend() != null) && (item.getBSend() == true)){

                HorizontalLayout horizontalLayout = new HorizontalLayout();
                horizontalLayout.setSpacing(false);
                horizontalLayout.setPadding(false);
                horizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);

                Span badge = new Span("Verstuurd ");

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

         actionInvoiceColumn = quoteGrid.addComponentColumn(item -> {
             return getActionInvoiceButton(item);
         }).setHeader("Actie").setFlexGrow(2);

        quoteGrid.sort(GridSortOrder.desc(columnInvoiceNumber).build());

        quoteGrid.addItemClickListener(event -> {

            //if selectionColumn is aangeklikt -> don't trigger event!
            if(event.getColumn() == null){
                return;
            }
            //generate event so the invoice can be opened from motherView (only if invoice is not send to Billit.
            selectedQuote = event.getItem();
            eventPublisher.publishEvent(new GetSelectedQuoteEvent(this, selectedQuote));
        });

        headerRow = quoteGrid.appendHeaderRow();
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.add(clearFilterButton,filterName);
        headerRow.getCell(columnCustomer).setComponent(headerLayout);
        headerRow.getCell(columnInvoiceNumber).setComponent(filterNumber);
        headerRow.getCell(columnComment).setComponent(filterSubject);
        headerRow.getCell(columnProformaStatus).setComponent(quoteStatusFilter);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(dateRangeButton);
        headerRow.getCell(columnInvoiceDate).setComponent(horizontalLayout);

        quoteGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        quoteGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        quoteGrid.addThemeVariants(GridVariant.LUMO_COMPACT);

        return quoteGrid;
    }

    private ComponentEventListener<ClickEvent<MenuItem>> goToCustomer(String customerId) {
        return  (event) -> {
            UI.getCurrent().navigate(
                    CustomerView .class, customerId);
        };
    }


    private com.vaadin.flow.component.Component getActionInvoiceButton(Quote item) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(false);
        horizontalLayout.setPadding(false);
        horizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        MenuBar actionBar = new MenuBar();
        actionBar.addClassName("large-menubar");
        actionBar.addThemeVariants(MenuBarVariant.LUMO_DROPDOWN_INDICATORS);
        MenuItem actie = actionBar.addItem("Actie".toUpperCase());
        actie.getElement().getClassList().add("menu-as-button");
        actie.getSubMenu().addItem("Open PDF",openPdf(item));
        actie.getSubMenu().addItem("Ga naar klant", goToCustomer(item.getCustomer().getId()));
        actie.getSubMenu().addItem("Stuur naar klant", sendToCustomer(item));

        horizontalLayout.add(actionBar);
        return horizontalLayout;
    }

    private ComponentEventListener<ClickEvent<MenuItem>> sendToCustomer(Quote item) {
        return  (event) -> {

            //copy pdf in clipboard
            quoteServices.generateInvoicePDF(item);

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
            String subject = "Offerte : 260002";
            String body = """
                            Beste klant.
                            
                            Commentaar voor de klant (nog eens vragen aan Kristof wat hier allemaal moet staan)
                            
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

    private ComponentEventListener<ClickEvent<MenuItem>> openPdf(Quote item) {
        return  (event) -> {
            quoteServices.generateInvoicePDF(item);
        };
    }

    private com.vaadin.flow.component.Component getStatusBadgesforInvoice(Invoice item) {

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(false);
        horizontalLayout.setPadding(false);
        horizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);

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
            horizontalLayout.add(badge);
        }
        else if ((item.getPartialPaid() != null) && (item.getPartialPaid() == true)){
            period = Period.between(LocalDate.now(), item.getExpiryDate());
            if(period.getDays() < 0 ){
                Span badge = new Span(""+ period.plusDays(2).getDays());
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
            period = Period.between(LocalDate.now(), item.getExpiryDate());
            if(period.getDays() < 0 ){
                Span badge = new Span(""+ period.plusDays(2).getDays());
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
        //totalAndVatColumn.setFooter(String.valueOf("€ " + df.format(dataProvider.fetch(new Query<>()).mapToDouble(x -> (quoteServices.calcTotalNetFromQuote(x).get() + quoteServices.calcTotalTaxFromQuote(x).get())).sum())));
        //totalVatColumn.setFooter(String.valueOf("€ " + df.format(dataProvider.fetch(new Query<>()).mapToDouble(x -> quoteServices.calcTotalTaxFromQuote(x).get()).sum())));
        totalNetColumn.setFooter(String.valueOf("€ " + df.format(dataProvider.fetch(new Query<>()).mapToDouble(x -> quoteServices.calcTotalNetFromQuote(x).get()).sum())));
    }

    public void addItemsToProformaGrid(List<Quote>quoteList){
        if((quoteList != null) && (quoteList.size() > 0)){
            quoteGrid.setVisible(true);
            dataProvider = new ListDataProvider<>(quoteList);
            quoteGrid.setDataProvider(dataProvider);

            //totalAndVatColumn.setFooter(String.valueOf("€ " + df.format(dataProvider.getItems().stream().mapToDouble(x -> (quoteServices.calcTotalNetFromQuote(x).get() + quoteServices.calcTotalTaxFromQuote(x).get())).sum())));
            //totalVatColumn.setFooter(String.valueOf("€ " + df.format(dataProvider.getItems().stream().mapToDouble(x -> quoteServices.calcTotalTaxFromQuote(x).get()).sum())));
            totalNetColumn.setFooter(String.valueOf("€ " + df.format(dataProvider.getItems().stream().mapToDouble(x -> quoteServices.calcTotalNetFromQuote(x).get()).sum())));
        }
        else{
            quoteGrid.setVisible(false);
            Notification notification = Notification.show("Geen Proforma Facturen");
            notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
        }
    }

    public void addItemsToQuoteGridFromFilter(){
        dataProvider.clearFilters();
        dataProvider.addFilter(item -> {

            boolean nameOk = true;
            boolean numberOk = true;
            boolean discriptionOk = true;
            boolean statusOk = true;
            boolean internalStatus = true;
            boolean dateSelectOK = true;
            boolean requestPO = true;

            if(!filterNumber.getValue().isEmpty()){
                numberOk = item.getQuoteNumber().toString().toLowerCase().contains(filterNumber.getValue().toLowerCase());
            }

            if (!filterName.getValue().isEmpty()) {
                nameOk =(item.getWorkAddress().getStreet().toLowerCase().contains(filterName.getValue().toLowerCase())) ||
                        (item.getWorkAddress().getCity().toLowerCase().contains(filterName.getValue().toLowerCase())) ||
                        (item.getWorkAddress().getAddressName().toLowerCase().contains(filterName.getValue().toLowerCase())) ||
                        (item.getCustomer().getName().toLowerCase().contains(filterName.getValue().toLowerCase())) ||
                        (item.getCustomer().getVatNumber().toLowerCase().contains(filterName.getValue().toLowerCase()));
            }

            if(!filterSubject.getValue().isEmpty()){
                discriptionOk = item.getDiscription() != null && item.getDiscription().toLowerCase().contains(filterSubject.getValue().toLowerCase());
            }

            if(quoteStatusFilter.getValue() != null){
                internalStatus = (quoteStatusFilter.getValue().matches("Geen Status") && (item.getToCheck().equals(false))&& item.getBApproved().equals(false)&& item.getBRejected().equals(false))||
                        (quoteStatusFilter.getValue().matches("Te controleren") && (item.getToCheck().equals(true)))||
                        (quoteStatusFilter.getValue().matches("Goedgekeurd") && (item.getBApproved().equals(true)))||
                        (quoteStatusFilter.getValue().matches("Afgekeurd") && (item.getBRejected().equals(true)))||
                (quoteStatusFilter.getValue().matches("Verzonden") && (item.getBSend().equals(true)));
            }

            if(dateRangePicker.getValue() != null){
                dateSelectOK =
                        (item.getQuoteDate().isEqual(dateRangePicker.getStart()) || item.getQuoteDate().isAfter(dateRangePicker.getStart())) &&
                                (item.getQuoteDate().isEqual(dateRangePicker.getEnd())   || item.getQuoteDate().isBefore(dateRangePicker.getEnd()));
            }
            return numberOk && nameOk && discriptionOk && statusOk && internalStatus && dateSelectOK;
        });

        refreshTotals();
        dataProvider.refreshAll();

    }


    public Notification createReportDelete() {
        deleteQuoteNotification = new Notification();
        deleteQuoteNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Icon icon = VaadinIcon.WARNING.create();
        Button retryBtn = new Button("Annuleer",
                clickEvent -> deleteQuoteNotification.close());
        retryBtn.getStyle().setMargin("0 0 0 var(--lumo-space-l)");

        var layout = new HorizontalLayout(icon,
                new Text("Ben je zeker dat je deze offerte wil wissen?"), retryBtn,
                createCloseBtn(deleteQuoteNotification));
        layout.setAlignItems(Alignment.CENTER);

        deleteQuoteNotification.add(layout);

        return deleteQuoteNotification;
    }

    public Button createCloseBtn(Notification notification) {
        Button removeBtn = new Button(VaadinIcon.TRASH.create(),
                clickEvent -> {
                    if((quoteListToRemove != null) && (!quoteListToRemove.isEmpty())){
                        try{
                            for(Quote quote: quoteListToRemove){
                                dataProvider.getItems()
                                        .removeIf(i -> i.getId().equals(quote.getId()));
                            }
                            quoteGrid.getDataProvider().refreshAll();
                        }
                        catch(Exception e){
                        }
                        for(Quote quote: quoteListToRemove){
                            quoteService.delete(quote);
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

    public void tryToSetPreviousFilters(){
        refreshTotals();
        dataProvider.refreshAll();

        if((quoteViewState.getCustomer() != null) && (quoteViewState.getCustomer().length() > 0)) {
            filterName.setValue(quoteViewState.getCustomer());
        }
        if((quoteViewState.getNumber() != null) && (quoteViewState.getNumber().length() > 0)) {
            filterNumber.setValue(quoteViewState.getNumber());
        }
    }

    public void showRemoveNotification() {
        if(quoteGrid.getSelectedItems().size() >= 0){
            quoteListToRemove = quoteGrid.getSelectedItems();
            deleteQuoteNotification.open();
        }
        else{
            Notification.show("Gelieve minimum 1 werkbon te selecteren om te verwijderen");
        }
    }
}
