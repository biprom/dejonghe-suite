package com.adverto.dejonghe.server.views.subViews;

import com.adverto.dejonghe.common.entities.invoice.Invoice;
import com.adverto.dejonghe.common.entities.invoice.Payment;
import com.adverto.dejonghe.server.services.invoice.InvoiceServices;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.FooterRow;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import org.springframework.context.annotation.Scope;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@org.springframework.stereotype.Component
@Scope("prototype")
public class PaymentDialogView extends Div {

    InvoiceServices invoiceServices;

    Invoice invoice;
    Grid<Payment> paymentGrid;
    GridListDataView<Payment> paymentGridListDataView;

    Grid.Column<Payment> paymentDateColumn;
    Grid.Column<Payment> paymentCommentColumn;
    Grid.Column<Payment> paymentAmountColumn;

    Binder<Payment>paymentBinder;
    Editor<Payment> paymentEditor;

    FooterRow footerRow;

    NumberFormat df = NumberFormat.getNumberInstance(new Locale("nl", "BE"));

    public PaymentDialogView(InvoiceServices invoiceServices) {
        this.invoiceServices = invoiceServices;

        setUpPaymentGrid();
        setUpNumberFormat();
        this.setSizeFull();
        this.add(paymentGrid);
    }

    private void setUpNumberFormat() {
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        df.setGroupingUsed(true);
    }

    private void setUpPaymentGrid() {
        paymentGrid = new Grid<>();
        paymentGrid.setWidth("100%");
        paymentGrid.setHeight("100%");
        paymentDateColumn = paymentGrid.addColumn(item -> {
            if(item.getPaymentDate() != null) {
                return item.getPaymentDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            }
            else{
                return "";
            }
        }).setHeader("Datum").setResizable(true).setFlexGrow(3);

        paymentCommentColumn = paymentGrid.addColumn(item -> item.getComment()).setHeader("Commentaar").setResizable(true).setAutoWidth(true).setFlexGrow(3);

        paymentAmountColumn = paymentGrid.addColumn(item -> df.format(item.getPaymentAmount()) + " €").setHeader("Bedrag").setResizable(true).setAutoWidth(true).setFlexGrow(3);

        paymentGrid.addComponentColumn(item -> {
            Icon addIcon = VaadinIcon.PLUS.create();
            addIcon.addClickListener(x -> {
                Payment payment = new Payment();
                payment.setPaymentDate(LocalDate.now());
                try{
                    payment.setPaymentAmount(invoice.getTotalAmountTempPlaceholder() - invoice.getPaymentList().stream().mapToDouble(pay -> pay.getPaymentAmount()).sum());
                }
                catch (Exception e){
                    Notification.show("Kan geen restbedrag berekenen!");
                }
                paymentGridListDataView.addItem(payment);
            });
            return addIcon;
        });

        paymentGrid.addComponentColumn(item -> {
            Icon removeIcon = VaadinIcon.TRASH.create();
            removeIcon.setColor("red");
            removeIcon.addClickListener(x -> {
                paymentGridListDataView.removeItem(item);
            });
            return removeIcon;
        });

        paymentBinder = new Binder<>(Payment.class);
        paymentEditor = paymentGrid.getEditor();
        paymentEditor.setBinder(paymentBinder);

        DatePicker datePicker = new DatePicker();
        datePicker.setI18n(
                new DatePicker.DatePickerI18n()
                        .setFirstDayOfWeek(1));
        datePicker.setWidthFull();
        addCloseHandler(datePicker, paymentEditor);
        paymentBinder.forField(datePicker)
                .withNullRepresentation(LocalDate.now())
                .asRequired("Gelieve een betaaldatum in te geven aub.")
                .bind(Payment::getPaymentDate, Payment::setPaymentDate);
        paymentDateColumn.setEditorComponent(datePicker);

        TextField tfComment = new TextField();
        tfComment.setWidthFull();
        addCloseHandler(tfComment, paymentEditor);
        paymentBinder.forField(tfComment)
                .withNullRepresentation("")
                .bind(Payment::getComment, Payment::setComment);
        paymentCommentColumn.setEditorComponent(tfComment);

        TextField tfAmount = new TextField();
        tfAmount.setWidthFull();
        addCloseHandler(tfAmount, paymentEditor);
        paymentBinder.forField(tfAmount)
                .withNullRepresentation("0.0")
                .asRequired("Gelieve een aantal in te geven aub.")
                .withConverter(
                        new StringToDoubleConverter("Dit is geen decimaal getal"))
                .bind(Payment::getPaymentAmount, Payment::setPaymentAmount);
        paymentAmountColumn.setEditorComponent(tfAmount);

        paymentBinder.addValueChangeListener(event -> {
            Payment paymentToChange = paymentEditor.getItem();
            invoice.setTotalPayedAmount(invoiceServices.getTotalPayed(invoice).get());
            paymentBinder.readBean(paymentToChange);
            setTotalsInFooter();
        });

        paymentGrid.addItemClickListener(e -> {
            paymentEditor.editItem(e.getItem());
            Component editorComponent = e.getColumn().getEditorComponent();
            if (editorComponent instanceof Focusable) {
                ((Focusable) editorComponent).focus();
            }
        });

        footerRow = paymentGrid.appendFooterRow();

    }

    private void setTotalsInFooter(){
        try{
            paymentDateColumn.setFooter("");

            Optional<Double> amount = invoiceServices.calcTotalNetFromInvoice(invoice);
            Optional<Double> vat = invoiceServices.calcTotalTaxFromInvoice(invoice);

            if(amount.isPresent() && vat.isPresent()){
                invoice.setTotalAmountTempPlaceholder(amount.get()+vat.get());
            }

            paymentAmountColumn.setFooter(String.format("%s €", df.format(invoice.getPaymentList().stream().mapToDouble(item -> item.getPaymentAmount()).sum())) + " van " +String.format("%s €", df.format(invoice.getTotalAmountTempPlaceholder()))+ " betaald");
        }
        catch(Exception e){
            paymentCommentColumn.setFooter("N/A");
        }
    }



    private static void addCloseHandler(Component textField,
                                        Editor<Payment> editor) {
        textField.getElement().addEventListener("keydown", e -> editor.cancel())
                .setFilter("event.code === 'Escape'");
    }

    public void setInvoice(Invoice item) {
        this.invoice = item;
        if((invoice != null) && (invoice.getPaymentList() != null) && (invoice.getPaymentList().size() > 0)) {
            paymentGridListDataView = paymentGrid.setItems(invoice.getPaymentList());
            setTotalsInFooter();
        }
        else{
            List<Payment> paymentList = new ArrayList<>();
            Payment payment = new Payment();
            payment.setPaymentDate(LocalDate.now());
            try{
                if(invoice.getPaymentList() != null){
                    payment.setPaymentAmount(invoice.getTotalAmountTempPlaceholder() - invoice.getPaymentList().stream().mapToDouble(pay -> pay.getPaymentAmount()).sum());
                }
                else{
                    payment.setPaymentAmount(invoice.getTotalAmountTempPlaceholder() - 0.0);
                }
            }
            catch (Exception e){
                Notification.show("Kan geen restbedrag berekenen!");
            }
            paymentList.add(payment);
            invoice.setPaymentList(paymentList);
            paymentGridListDataView = paymentGrid.setItems(invoice.getPaymentList());
            setTotalsInFooter();
        }
    }

    public Invoice getInvoiceToSave() {
        return invoice;
    }
}
