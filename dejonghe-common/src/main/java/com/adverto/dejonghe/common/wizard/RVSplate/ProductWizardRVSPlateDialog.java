package com.adverto.dejonghe.common.wizard.RVSplate;

import com.adverto.dejonghe.common.dbservices.ProductService;
import com.adverto.dejonghe.common.entities.product.product.Product;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class ProductWizardRVSPlateDialog extends Dialog {

    private final Consumer<Product> onProductsAdded;
    private ProductService productService;
    private Product selectedItem;

    private int currentStep = 1;

    private final VerticalLayout stepContent =
            new VerticalLayout();

    private final Button previousButton =
            new Button("Terug", VaadinIcon.ARROW_LEFT.create());

    private final Button nextButton =
            new Button("Verder", VaadinIcon.ARROW_RIGHT.create());

    /*
     * STAP 1
     */
    private final NumberField lengthField =
            new NumberField("Lengte");

    private final NumberField widthField =
            new NumberField("Breedte");

    private final NumberField thicknessField =
            new NumberField("Dikte");

    private final Span volumeLabel =
            new Span();

    /*
     * STAP 2
     */
    private final RadioButtonGroup<Processing> processingGroup =
            new RadioButtonGroup<>();

    /*
     * STAP 3
     */
    private final RadioButtonGroup<Folded> foldedGroup =
            new RadioButtonGroup<>();

    public ProductWizardRVSPlateDialog(
            Consumer<Product> onProductsAdded,
            Product selectedItem,
            ProductService productService
    ) {

        this.onProductsAdded = onProductsAdded;
        this.selectedItem = selectedItem;
        this.productService = productService;

        setWidth("650px");
        setHeight("650px");

        setCloseOnEsc(true);
        setCloseOnOutsideClick(false);

        configureFields();
        configureButtons();

        H2 title = new H2("Product configureren");
        title.getStyle().set("margin", "0");

        Button closeButton = new Button(
                VaadinIcon.CLOSE.create(),
                event -> close()
        );

        closeButton.addThemeVariants(
                ButtonVariant.LUMO_TERTIARY,
                ButtonVariant.LUMO_ICON
        );

        HorizontalLayout header =
                new HorizontalLayout(
                        title,
                        closeButton
                );

        header.setWidthFull();
        header.setAlignItems(
                FlexComponent.Alignment.CENTER
        );

        header.expand(title);

        stepContent.setSizeFull();
        stepContent.setPadding(false);

        HorizontalLayout footer =
                new HorizontalLayout(
                        previousButton,
                        nextButton
                );

        footer.setWidthFull();

        footer.setJustifyContentMode(
                FlexComponent.JustifyContentMode.END
        );

        VerticalLayout mainLayout =
                new VerticalLayout(
                        header,
                        stepContent,
                        footer
                );

        mainLayout.setSizeFull();
        mainLayout.expand(stepContent);

        add(mainLayout);

        showStep(1);
    }

    private void configureFields() {

        lengthField.setSuffixComponent(
                new Span("mm")
        );

        widthField.setSuffixComponent(
                new Span("mm")
        );

        thicknessField.setSuffixComponent(
                new Span("mm")
        );

        lengthField.setMin(0);
        widthField.setMin(0);
        thicknessField.setMin(0);

        lengthField.setStep(0.1);
        widthField.setStep(0.1);
        thicknessField.setStep(0.1);

        lengthField.setWidthFull();
        widthField.setWidthFull();
        thicknessField.setWidthFull();

        lengthField.addValueChangeListener(
                e -> calculateVolume()
        );

        widthField.addValueChangeListener(
                e -> calculateVolume()
        );

        thicknessField.addValueChangeListener(
                e -> calculateVolume()
        );

        /*
         * Processing
         */
        processingGroup.setLabel("Bewerking");

        processingGroup.setItems(
                Processing.values()
        );

        processingGroup.setItemLabelGenerator(
                Processing::getDescription
        );

        /*
         * Folded
         */
        foldedGroup.setLabel(
                "Moet het product geplooid worden?"
        );

        foldedGroup.setItems(
                Folded.values()
        );

        foldedGroup.setItemLabelGenerator(
                Folded::getDescription
        );
    }

    private void configureButtons() {

        previousButton.addClickListener(event -> {

            if (currentStep > 1) {
                showStep(currentStep - 1);
            }
        });

        nextButton.addThemeVariants(
                ButtonVariant.LUMO_PRIMARY
        );

        nextButton.setIconAfterText(true);

        nextButton.addClickListener(event -> {

            if (!validateCurrentStep()) {
                return;
            }

            if (currentStep < 4) {

                showStep(currentStep + 1);

            } else {

                finishWizard();
            }
        });
    }

    private void showStep(int step) {

        currentStep = step;

        stepContent.removeAll();

        switch (step) {

            case 1 -> stepContent.add(
                    createStep1()
            );

            case 2 -> stepContent.add(
                    createStep2()
            );

            case 3 -> stepContent.add(
                    createStep3()
            );

            case 4 -> stepContent.add(
                    createSummary()
            );
        }

        previousButton.setVisible(
                step > 1
        );

        if (step == 4) {

            nextButton.setText("Opslaan");

            nextButton.setIcon(
                    new Icon(VaadinIcon.CHECK)
            );

        } else {

            nextButton.setText("Verder");

            nextButton.setIcon(
                    new Icon(VaadinIcon.ARROW_RIGHT)
            );
        }
    }

    private Component createStep1() {

        H2 title =
                new H2("Afmetingen");

        Span description =
                new Span(
                        "Geef de afmetingen van het materiaal in."
                );

        VerticalLayout fields =
                new VerticalLayout(
                        lengthField,
                        widthField,
                        thicknessField
                );

        fields.setWidthFull();

        VerticalLayout layout =
                new VerticalLayout(
                        title,
                        description,
                        fields,
                        volumeLabel
                );

        layout.setPadding(false);

        return layout;
    }

    private void calculateVolume() {

        Double length =
                lengthField.getValue();

        Double width =
                widthField.getValue();

        Double thickness =
                thicknessField.getValue();

        if (length == null
                || width == null
                || thickness == null) {

            volumeLabel.setText("");
            return;
        }

        double volumeMm3 =
                length
                        * width
                        * thickness;

        double volumeDm3 =
                volumeMm3 / 1_000_000.0;

        volumeLabel.setText(
                String.format(
                        "Volume: %.2f dm³",
                        volumeDm3
                )
        );
    }

    private Component createStep2() {

        H2 title =
                new H2("Bewerking");

        Span description =
                new Span(
                        "Selecteer hoe het materiaal moet worden verwerkt."
                );

        VerticalLayout layout =
                new VerticalLayout(
                        title,
                        description,
                        processingGroup
                );

        layout.setPadding(false);

        return layout;
    }

    private Component createStep3() {

        H2 title =
                new H2("Plooien");

        Span description =
                new Span(
                        "Geef aan of het materiaal geplooid moet worden."
                );

        VerticalLayout layout =
                new VerticalLayout(
                        title,
                        description,
                        foldedGroup
                );

        layout.setPadding(false);

        return layout;
    }

    private Component createSummary() {

        H2 title =
                new H2("Overzicht");

        Span description =
                new Span(
                        "Controleer onderstaande gegevens voordat je opslaat."
                );

        double volumeMm3 =
                lengthField.getValue()
                        * widthField.getValue()
                        * thicknessField.getValue();

        double volumeDm3 =
                volumeMm3 / 1_000_000.0;

        H3 dimensionsTitle =
                new H3("Afmetingen");

        HorizontalLayout dimensions =
                new HorizontalLayout(
                        createSummaryItem(
                                "Lengte",
                                formatNumber(
                                        lengthField.getValue()
                                ) + " mm"
                        ),
                        createSummaryItem(
                                "Breedte",
                                formatNumber(
                                        widthField.getValue()
                                ) + " mm"
                        ),
                        createSummaryItem(
                                "Dikte",
                                formatNumber(
                                        thicknessField.getValue()
                                ) + " mm"
                        )
                );

        dimensions.setWidthFull();

        H3 volumeTitle =
                new H3("Volume");

        Span volume =
                new Span(
                        String.format(
                                "%.2f dm³",
                                volumeDm3
                        )
                );

        volume.getStyle()
                .set("font-size", "1.2rem")
                .set("font-weight", "600");

        H3 processingTitle =
                new H3("Bewerking");

        Processing selectedProcessing =
                processingGroup.getValue();

        Span processing =
                new Span(
                        selectedProcessing != null
                                ? selectedProcessing.getDescription()
                                : "-"
                );

        processing.getStyle()
                .set("font-size", "1.1rem")
                .set("font-weight", "600");

        H3 foldedTitle =
                new H3("Geplooid");

        Folded selectedFolded =
                foldedGroup.getValue();

        Span folded =
                new Span(
                        selectedFolded != null
                                ? selectedFolded.getDescription()
                                : "-"
                );

        folded.getStyle()
                .set("font-size", "1.1rem")
                .set("font-weight", "600");

        VerticalLayout layout =
                new VerticalLayout(
                        title,
                        description,
                        dimensionsTitle,
                        dimensions,
                        volumeTitle,
                        volume,
                        processingTitle,
                        processing,
                        foldedTitle,
                        folded
                );

        layout.setPadding(false);

        return layout;
    }

    private Component createSummaryItem(
            String label,
            String value
    ) {

        Span labelSpan =
                new Span(label);

        labelSpan.getStyle()
                .set("font-size", "0.85rem")
                .set(
                        "color",
                        "var(--lumo-secondary-text-color)"
                );

        Span valueSpan =
                new Span(value);

        valueSpan.getStyle()
                .set("font-size", "1.1rem")
                .set("font-weight", "600");

        VerticalLayout layout =
                new VerticalLayout(
                        labelSpan,
                        valueSpan
                );

        layout.setPadding(false);
        layout.setSpacing(false);

        return layout;
    }

    private boolean validateCurrentStep() {

        if (currentStep == 1) {

            boolean valid = true;

            if (lengthField.getValue() == null
                    || lengthField.getValue() <= 0) {

                lengthField.setInvalid(true);

                lengthField.setErrorMessage(
                        "Gelieve een lengte in te geven."
                );

                valid = false;

            } else {

                lengthField.setInvalid(false);
            }

            if (widthField.getValue() == null
                    || widthField.getValue() <= 0) {

                widthField.setInvalid(true);

                widthField.setErrorMessage(
                        "Gelieve een breedte in te geven."
                );

                valid = false;

            } else {

                widthField.setInvalid(false);
            }

            if (thicknessField.getValue() == null
                    || thicknessField.getValue() <= 0) {

                thicknessField.setInvalid(true);

                thicknessField.setErrorMessage(
                        "Gelieve een dikte in te geven."
                );

                valid = false;

            } else {

                thicknessField.setInvalid(false);
            }

            return valid;
        }

        if (currentStep == 2) {

            if (processingGroup.getValue() == null) {

                processingGroup.setInvalid(true);

                processingGroup.setErrorMessage(
                        "Gelieve een bewerking te kiezen."
                );

                return false;
            }

            processingGroup.setInvalid(false);
        }

        if (currentStep == 3) {

            if (foldedGroup.getValue() == null) {

                foldedGroup.setInvalid(true);

                foldedGroup.setErrorMessage(
                        "Gelieve een keuze te maken."
                );

                return false;
            }

            foldedGroup.setInvalid(false);
        }

        return true;
    }

    private void finishWizard() {

        ProductConfiguration configuration =
                new ProductConfiguration();

        configuration.setLength(
                lengthField.getValue()
        );

        configuration.setWidth(
                widthField.getValue()
        );

        configuration.setThickness(
                thicknessField.getValue()
        );

        double volumeMm3 =
                lengthField.getValue()
                        * widthField.getValue()
                        * thicknessField.getValue();

        configuration.setVolume(
                volumeMm3
        );

        /*
         * Nu rechtstreeks enum gebruiken
         */
        configuration.setProcessing(
                processingGroup.getValue()
        );

        configuration.setFolded(
                foldedGroup.getValue()
        );

        createProducts(configuration);

        close();
    }

    private void createProducts(
            ProductConfiguration configuration) {

        /*
         * Volume staat in mm³.
         * 1 dm³ = 1.000.000 mm³.
         * RVS weegt ongeveer 8 kg/dm³.
         */
        double weight =
                configuration.getVolume()
                        / 1_000_000.0
                        * 8.0;

        ProductPriceTotals totals =
                new ProductPriceTotals();

        /*
         * Prijs van de basisplaat per kg.
         */
        totals.addProduct(
                selectedItem,
                weight
        );

        /*
         * Forfait en kg-prijs van de gekozen bewerking.
         */
        Processing processing =
                configuration.getProcessing();

        if (processing != null) {

//            findProductByCode(
//                    processing.getForfaitProductCode()
//            ).ifPresent(product ->
//                    totals.addProduct(product, 1.0)
//            );

            findProductByCode(
                    processing.getKgPriceProductCode()
            ).ifPresent(product ->
                    totals.addProduct(product, weight)
            );
        }

        /*
         * Forfait en kg-prijs voor plooien.
         */
        if (configuration.getFolded() == Folded.YES) {

//            findProductByCode(
//                    configuration.getFolded()
//                            .getForfaitProductCode()
//            ).ifPresent(product ->
//                    totals.addProduct(product, 1.0)
//            );

            findProductByCode(
                    configuration.getFolded()
                            .getKgPriceProductCode()
            ).ifPresent(product ->
                    totals.addProduct(product, weight)
            );
        }

        /*
         * Eén productregel maken.
         *
         * We gebruiken voorlopig selectedItem als basis,
         * zodat eigenschappen zoals BTW, artikelcode en eenheid
         * behouden blijven.
         */
        Product combinedProduct = selectedItem;

        combinedProduct.setInternalName(
                createCombinedDescription(
                        configuration,
                        weight
                )
        );

        /*
         * De samengestelde lijn heeft aantal 1.
         * De eenheidsprijs is de volledige totaalprijs.
         */
        combinedProduct.setSelectedAmount(selectedItem.getSelectedAmount());

        combinedProduct.setPurchasePrice(
                totals.purchasePrice
        );

        combinedProduct.setSellPrice(
                totals.sellPrice
        );

        combinedProduct.setSellPriceIndustry(
                totals.sellPriceIndustry
        );

        combinedProduct.setTotalPrice(
                totals.sellPrice
        );

        //remove wizard field so the wizard does not show op when added to selectedProductList.
        //this would happen after filling in the wizard (loop)
        combinedProduct.setWizard(null);

        onProductsAdded.accept(combinedProduct);
    }

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

            sellPrice +=
                    regularPrice * amount;

            /*
             * Wanneer geen industrieprijs bestaat,
             * gebruiken we de gewone verkoopprijs.
             */
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

    private Optional<Product> findProductByCode(
            String productCode) {

        if (productCode == null ||
                productCode.isBlank()) {

            return Optional.empty();
        }

        return productService
                .findByProductCodeEqualCaseInsensitive(
                        productCode
                )
                .filter(products -> !products.isEmpty())
                .map(List::getFirst);
    }

    private String createCombinedDescription(
            ProductConfiguration configuration,
            double weight) {

        StringBuilder description =
                new StringBuilder();

        /*
         * Bijvoorbeeld: RVS 304 koudgewalst
         */
        if (selectedItem.getInternalName() != null) {
            description.append(
                    selectedItem.getInternalName().trim()
            );
        }

        /*
         * Bijvoorbeeld: gelaserd
         */
        if (configuration.getProcessing() != null) {

            String processingDescription =
                    configuration.getProcessing()
                            .getDescription();

            if (processingDescription != null &&
                    !processingDescription.isBlank()) {

                description
                        .append(" ")
                        .append(
                                processingDescription
                                        .trim()
                                        .toLowerCase()
                        );
            }
        }

        if (configuration.getFolded() == Folded.YES) {
            description.append(" geplooid");
        }

        /*
         * Afmetingen in de vorm L x B x D.
         */
        description
                .append(" ")
                .append(formatNumber(
                        configuration.getLength()
                ))
                .append(" x ")
                .append(formatNumber(
                        configuration.getWidth()
                ))
                .append(" x ")
                .append(formatNumber(
                        configuration.getThickness()
                ))
                .append(" mm");

        /*
         * Gewicht kan handig zijn op de werkbon/factuur.
         */
        description
                .append(" (")
                .append(String.format("%.2f", weight))
                .append(" kg)");

        return description.toString();
    }

    private String formatNumber(
            Double value
    ) {

        if (value == null) {
            return "-";
        }

        if (value % 1 == 0) {
            return String.format(
                    "%.0f",
                    value
            );
        }

        return String.format(
                "%.2f",
                value
        );
    }
}
