
package com.adverto.dejonghe.server.views.subViews;

import com.adverto.dejonghe.common.entities.invoice.Invoice;
import com.adverto.dejonghe.common.entities.invoice.Payment;
import com.adverto.dejonghe.server.services.invoice.InvoiceServices;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;

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

    private final InvoiceServices invoiceServices;

    private Invoice invoice;

    private Grid<Payment> paymentGrid;
    private GridListDataView<Payment> paymentGridListDataView;

    private Grid.Column<Payment> paymentDateColumn;
    private Grid.Column<Payment> paymentCommentColumn;
    private Grid.Column<Payment> paymentAmountColumn;
    private Grid.Column<Payment> paymentActionColumn;

    private Binder<Payment> paymentBinder;
    private Editor<Payment> paymentEditor;

    private DatePicker datePicker;
    private NumberField amountField;

    // Lege invoerregel, geen echte betaling
    private Payment draftPayment;

    private final NumberFormat df =
            NumberFormat.getNumberInstance(
                    Locale.forLanguageTag("nl-BE"));

    public PaymentDialogView(InvoiceServices invoiceServices) {
        this.invoiceServices = invoiceServices;

        setUpNumberFormat();
        setUpPaymentGrid();

        setSizeFull();
        add(paymentGrid);
    }

    private void setUpNumberFormat() {
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        df.setGroupingUsed(true);
    }

    private boolean isDraft(Payment payment) {
        return payment == draftPayment;
    }

    private void setUpPaymentGrid() {

        paymentGrid = new Grid<>();
        paymentGrid.setSizeFull();

        // DATUM
        paymentDateColumn = paymentGrid.addColumn(item -> {

                    if (isDraft(item) || item.getPaymentDate() == null) {
                        return "";
                    }

                    return item.getPaymentDate().format(
                            DateTimeFormatter.ofPattern("dd-MM-yyyy"));

                }).setHeader("Datum")
                .setResizable(true)
                .setFlexGrow(3);

        // COMMENTAAR
        paymentCommentColumn = paymentGrid.addColumn(item -> {

                    if (isDraft(item) || item.getComment() == null) {
                        return "";
                    }

                    return item.getComment();

                }).setHeader("Commentaar")
                .setResizable(true)
                .setFlexGrow(3);

        // BEDRAG
        paymentAmountColumn = paymentGrid.addColumn(item -> {

                    if (isDraft(item) || item.getPaymentAmount() == null) {
                        return "";
                    }

                    return df.format(item.getPaymentAmount()) + " €";

                }).setHeader("Bedrag")
                .setResizable(true)
                .setFlexGrow(3);

        // ACTIES
        paymentActionColumn = paymentGrid.addComponentColumn(item -> {

                    if (isDraft(item)) {

                        Icon addIcon = VaadinIcon.PLUS.create();

                        addIcon.getElement().setAttribute(
                                "title", "Nieuwe betaling invoeren");

                        addIcon.addClickListener(e ->
                                editPayment(item, paymentAmountColumn));

                        return addIcon;
                    }

                    Icon removeIcon = VaadinIcon.TRASH.create();
                    removeIcon.setColor("red");

                    removeIcon.addClickListener(e -> {

                        if (paymentEditor.isOpen()) {
                            paymentEditor.cancel();
                        }

                        // Verwijder uit de echte betalingen
                        if (invoice != null &&
                                invoice.getPaymentList() != null) {

                            invoice.getPaymentList()
                                    .removeIf(p -> p == item);
                        }

                        // Verwijder uit de Grid
                        paymentGridListDataView.removeItem(item);

                        updatePaymentTotals();
                    });

                    return removeIcon;

                }).setWidth("120px")
                .setFlexGrow(0);

        // BINDER EN EDITOR
        paymentBinder = new Binder<>(Payment.class);

        paymentEditor = paymentGrid.getEditor();
        paymentEditor.setBinder(paymentBinder);

        // Pas echt opslaan na bevestiging
        paymentEditor.setBuffered(true);

        // DATUM EDITOR
        datePicker = new DatePicker();
        datePicker.setWidthFull();

        datePicker.setI18n(
                new DatePicker.DatePickerI18n()
                        .setFirstDayOfWeek(1));

        paymentBinder.forField(datePicker)
                .asRequired("Gelieve een betaaldatum in te geven.")
                .bind(Payment::getPaymentDate,
                        Payment::setPaymentDate);

        paymentDateColumn.setEditorComponent(datePicker);

        // COMMENTAAR EDITOR
        TextField tfComment = new TextField();
        tfComment.setWidthFull();

        paymentBinder.forField(tfComment)
                .withNullRepresentation("")
                .bind(Payment::getComment,
                        Payment::setComment);

        paymentCommentColumn.setEditorComponent(tfComment);

        // BEDRAG EDITOR
        amountField = new NumberField();
        amountField.setWidthFull();
        amountField.setStep(0.01);

        paymentBinder.forField(amountField)
                .asRequired("Gelieve een bedrag in te geven.")
                .withValidator(
                        value -> value != null
                                && Double.isFinite(value)
                                && value > 0,
                        "Het bedrag moet groter zijn dan 0."
                )
                .bind(Payment::getPaymentAmount,
                        Payment::setPaymentAmount);

        paymentAmountColumn.setEditorComponent(amountField);

        // OPSLAAN EN ANNULEREN
        Button saveButton = new Button(
                "Opslaan",
                e -> paymentEditor.save()
        );

        Button cancelButton = new Button(
                "Annuleren",
                e -> paymentEditor.cancel()
        );

        HorizontalLayout editorActions =
                new HorizontalLayout(saveButton, cancelButton);

        editorActions.setPadding(false);
        editorActions.setSpacing(true);

        paymentActionColumn.setEditorComponent(editorActions);

        // BIJ OPSLAAN VAN EEN BETALING
        paymentEditor.addSaveListener(event -> {

            Payment savedPayment = event.getItem();

            // Was dit de lege invoerregel?
            if (isDraft(savedPayment)) {

                // Nu pas registreren als echte betaling
                invoice.getPaymentList().add(savedPayment);

                // Maak een nieuwe lege invoerregel
                draftPayment = new Payment();

                paymentGridListDataView.addItem(draftPayment);
            }

            paymentGridListDataView.refreshItem(savedPayment);

            updatePaymentTotals();
        });

        // OPEN EDITOR BIJ KLIKKEN OP EEN REGEL
        paymentGrid.addItemClickListener(event -> {

            if (event.getColumn() == paymentActionColumn) {
                return;
            }

            editPayment(event.getItem(), event.getColumn());
        });

        paymentGrid.appendFooterRow();
    }


    private void editPayment(
            Payment payment,
            Grid.Column<Payment> column) {

        if (invoice == null || payment == null) {
            return;
        }

        if (paymentEditor.isOpen()) {
            paymentEditor.cancel();
        }

        // Alleen een voorstel doen bij een nieuwe betaling
        if (isDraft(payment)) {

            // Zorg dat het actuele factuurtotaal berekend is
            setTotalsInFooter();

            // Bereken het resterende bedrag
            double remainingAmount = getRemainingAmount();

            // Vul het voorstel in VOORDAT de editor opent
            payment.setPaymentDate(LocalDate.now());

            if (remainingAmount > 0) {
                payment.setPaymentAmount(remainingAmount);
            } else {
                payment.setPaymentAmount(null);
            }
        }

        // Vaadin Binder leest nu de ingevulde Payment-waarden
        paymentEditor.editItem(payment);

        // Focus op de aangeklikte kolom
        if (column != null) {

            Component editorComponent =
                    column.getEditorComponent();

            if (editorComponent instanceof Focusable) {
                ((Focusable<?>) editorComponent).focus();
            }
        }
    }


    private double getTotalPaid() {

        if (invoice == null ||
                invoice.getPaymentList() == null) {
            return 0.0;
        }

        return invoice.getPaymentList().stream()
                .map(Payment::getPaymentAmount)
                .filter(value ->
                        value != null &&
                                Double.isFinite(value))
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    private double getRemainingAmount() {

        if (invoice == null) {
            return 0.0;
        }

        Double totalAmount =
                invoice.getTotalAmountTempPlaceholder();

        if (totalAmount == null ||
                !Double.isFinite(totalAmount)) {
            return 0.0;
        }

        double remaining = totalAmount - getTotalPaid();

        // Bedrag afronden op 2 decimalen
        return Math.max(
                0.0,
                Math.round(remaining * 100.0) / 100.0
        );
    }

    private void updatePaymentTotals() {

        if (invoice == null) {
            return;
        }

        invoice.setTotalPayedAmount(getTotalPaid());

        setTotalsInFooter();
    }

    private void setTotalsInFooter() {

        try {

            Optional<Double> amount =
                    invoiceServices.calcTotalNetFromInvoice(invoice);

            Optional<Double> vat =
                    invoiceServices.calcTotalTaxFromInvoice(invoice);

            if (amount.isPresent() && vat.isPresent()) {

                invoice.setTotalAmountTempPlaceholder(
                        amount.get() + vat.get());
            }

            double totalPaid = getTotalPaid();

            Double invoiceAmount =
                    invoice.getTotalAmountTempPlaceholder();

            double totalInvoice =
                    invoiceAmount != null ? invoiceAmount : 0.0;

            paymentAmountColumn.setFooter(
                    df.format(totalPaid) + " € van " +
                            df.format(totalInvoice) + " € betaald"
            );

        } catch (Exception e) {

            paymentAmountColumn.setFooter("N/A");
        }
    }

    public void setInvoice(Invoice item) {

        if (paymentEditor.isOpen()) {
            paymentEditor.cancel();
        }

        this.invoice = item;

        if (invoice == null) {

            draftPayment = null;

            paymentGridListDataView =
                    paymentGrid.setItems(new ArrayList<>());

            paymentAmountColumn.setFooter("");

            return;
        }

        // Zorg dat de betalingslijst bestaat
        if (invoice.getPaymentList() == null) {
            invoice.setPaymentList(new ArrayList<>());
        }

        // Kopie voor de Grid
        // De echte betalingen blijven in invoice
        List<Payment> gridPayments =
                new ArrayList<>(invoice.getPaymentList());

        // Voeg 1 lege invoerregel toe
        draftPayment = new Payment();

        gridPayments.add(draftPayment);

        paymentGridListDataView =
                paymentGrid.setItems(gridPayments);

        // Gebruik objectidentiteit voor de Grid
        paymentGridListDataView.setIdentifierProvider(
                System::identityHashCode);

        updatePaymentTotals();
    }

    public Invoice getInvoiceToSave() {
        return invoice;
    }
}
