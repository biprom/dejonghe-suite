package com.adverto.dejonghe.common.wizard.RVSplate;

import com.adverto.dejonghe.common.entities.product.product.Product;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;

import org.springframework.beans.BeanUtils;

import java.util.Locale;
import java.util.function.Consumer;

public class ProductWizardProfileDialog extends Dialog {

    private final Consumer<Product> onProductsAdded;
    private final Product selectedItem;

    private final NumberField lengthField =
            new NumberField("Gebruikte lengte");

    private final Button saveButton =
            new Button("Toevoegen");

    public ProductWizardProfileDialog(
            Consumer<Product> onProductsAdded,
            Product selectedItem) {

        this.onProductsAdded = onProductsAdded;
        this.selectedItem = selectedItem;

        setWidth("650px");
        setMaxWidth("650px");
        setCloseOnEsc(true);
        setCloseOnOutsideClick(false);

        configureFields();
        buildLayout();
    }

    private void configureFields() {

        lengthField.setSuffixComponent(new Span("m"));
        lengthField.setWidthFull();
        lengthField.setMin(0);
        lengthField.setStep(0.1);
        lengthField.setRequiredIndicatorVisible(true);

        // Neem de reeds geselecteerde hoeveelheid over
        Double selectedAmount = selectedItem.getSelectedAmount();

        if (selectedAmount != null
                && Double.isFinite(selectedAmount)
                && selectedAmount > 0) {

            lengthField.setValue(selectedAmount);
        }

        saveButton.addThemeVariants(
                ButtonVariant.LUMO_PRIMARY
        );

        saveButton.addClickListener(event -> finishWizard());
    }

    private void buildLayout() {

        H2 title = new H2("Profiel configureren");

        Span productName = new Span(
                selectedItem.getInternalName() != null
                        ? selectedItem.getInternalName()
                        : ""
        );

        Button cancelButton = new Button(
                "Annuleren",
                event -> close()
        );

        HorizontalLayout buttons = new HorizontalLayout(
                cancelButton,
                saveButton
        );

        buttons.setWidthFull();
        buttons.setJustifyContentMode(
                com.vaadin.flow.component.orderedlayout
                        .FlexComponent.JustifyContentMode.END
        );

        VerticalLayout layout = new VerticalLayout(
                title,
                productName,
                lengthField,
                buttons
        );

        layout.setPadding(true);
        layout.setSpacing(true);

        add(layout);
    }


    private void finishWizard() {

        Double length = lengthField.getValue();

        if (length == null
                || !Double.isFinite(length)
                || length <= 0) {

            lengthField.setInvalid(true);
            lengthField.setErrorMessage(
                    "Gelieve een geldige lengte in te geven."
            );
            return;
        }

        lengthField.setInvalid(false);

        Product combinedProduct = new Product();

        BeanUtils.copyProperties(
                selectedItem,
                combinedProduct
        );

        combinedProduct.setId(null);

        // De ingegeven lengte is de hoeveelheid op de werkbon
        combinedProduct.setSelectedAmount(length);

        // Eenheidsprijzen blijven prijzen per meter
        combinedProduct.setPurchasePrice(
                selectedItem.getPurchasePrice()
        );

        combinedProduct.setSellPrice(
                selectedItem.getSellPrice()
        );

        combinedProduct.setSellPriceIndustry(
                selectedItem.getSellPriceIndustry()
        );

        // Totale verkoopprijs voor alle gebruikte meters
        double unitPrice = selectedItem.getSellPrice() != null
                ? selectedItem.getSellPrice()
                : 0.0;

        combinedProduct.setTotalPrice(
                unitPrice * length
        );

        combinedProduct.setInternalName(
                createCombinedDescription(length)
        );

        // Voorkomen dat de wizard opnieuw opent
        combinedProduct.setWizardtype(null);

        onProductsAdded.accept(combinedProduct);

        close();
    }


    private String createCombinedDescription(double length) {

        String name = selectedItem.getInternalName() != null
                ? selectedItem.getInternalName().trim()
                : "";

        return name;

//        return name + " - " +
//                String.format(
//                        Locale.forLanguageTag("nl-BE"),
//                        "%.2f m",
//                        length
//                );
    }

    /*
     * Zelfde prijsberekening als ProductWizardRVSPlateDialog.
     */
    private static class ProductPriceTotals {

        private double purchasePrice;
        private double sellPrice;
        private double sellPriceIndustry;

        private void addProduct(
                Product product,
                double amount) {

            if (product == null || amount <= 0) {
                return;
            }

            purchasePrice +=
                    safePrice(product.getPurchasePrice())
                            * amount;

            double regularPrice =
                    safePrice(product.getSellPrice());

            sellPrice += regularPrice * amount;

            double industryUnitPrice =
                    product.getSellPriceIndustry() != null
                            && product.getSellPriceIndustry() != 0.0
                            ? product.getSellPriceIndustry()
                            : regularPrice;

            sellPriceIndustry +=
                    industryUnitPrice * amount;
        }

        private static double safePrice(Double price) {
            return price != null ? price : 0.0;
        }
    }
}
