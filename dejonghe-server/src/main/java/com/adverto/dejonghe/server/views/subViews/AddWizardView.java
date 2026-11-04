
package com.adverto.dejonghe.server.views.subViews;

import com.adverto.dejonghe.common.dbservices.ProductService;
import com.adverto.dejonghe.common.entities.product.product.Product;
import com.adverto.dejonghe.common.entities.product.product.Wizard;
import com.adverto.dejonghe.common.wizard.WIZARDTYPE;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;

import org.springframework.context.annotation.Scope;

@org.springframework.stereotype.Component
@Scope("prototype")
public class AddWizardView extends VerticalLayout {

    private final ProductService productService;

    private Product selectedProduct;

    private final H1 title = new H1();

    private final ComboBox<WIZARDTYPE> wizardComboBox =
            new ComboBox<>("WizardType");

    private final VerticalLayout parameterLayout =
            new VerticalLayout();

    private final NumberField rhoField =
            new NumberField("Massadichtheid (kg/m³)");

    private final NumberField mkgField =
            new NumberField();

    private final RadioButtonGroup<String> profileMode =
            new RadioButtonGroup<>();

    private final Button saveButton =
            new Button("Bewaren");

    private static final String KG_PER_METER =
            "Gewicht per meter (kg/m)";

    private static final String METER_PER_KG =
            "Meter per kilogram (m/kg)";

    public AddWizardView(ProductService productService) {

        this.productService = productService;

        setPadding(true);
        setSpacing(true);
        setWidthFull();

        add(title);
        add(setUpWizardCombobox());

        parameterLayout.setPadding(false);
        parameterLayout.setSpacing(true);

        add(parameterLayout);

        configureFields();
        configureSaveButton();

        add(saveButton);

        updateParameterLayout(null);
    }

    private Component setUpWizardCombobox() {

        wizardComboBox.setItems(WIZARDTYPE.values());

        wizardComboBox.setItemLabelGenerator(
                WIZARDTYPE::getDescription
        );

        wizardComboBox.setWidth("300px");
        wizardComboBox.setClearButtonVisible(true);

        wizardComboBox.addValueChangeListener(event -> {
            updateParameterLayout(event.getValue());
        });

        return wizardComboBox;
    }

    private void configureFields() {

        rhoField.setWidth("250px");
        rhoField.setMin(0);
        rhoField.setStep(1);
        rhoField.setStepButtonsVisible(true);

        mkgField.setWidth("250px");
        mkgField.setMin(0);
        mkgField.setStep(0.01);

        profileMode.setLabel("Invoermethode");

        profileMode.setItems(
                KG_PER_METER,
                METER_PER_KG
        );

        profileMode.setValue(KG_PER_METER);

        profileMode.addValueChangeListener(event -> {
            if (event.isFromClient()) {
                Double value = mkgField.getValue();

                if (value != null && value > 0 && Double.isFinite(value)) {
                    mkgField.setValue(1.0 / value);
                }
            }

            updateProfileInput();
        });
    }

    private void updateParameterLayout(WIZARDTYPE type) {

        parameterLayout.removeAll();

        if (type == null) {
            return;
        }

        switch (type) {

            case PLATE -> {

                parameterLayout.add(
                        new H3("Plaatparameters"),
                        rhoField
                );
            }

            case PROFILE -> {

                parameterLayout.add(
                        new H3("Profielparameters"),
                        profileMode,
                        mkgField
                );

                updateProfileInput();
            }
        }
    }

    private void updateProfileInput() {

        if (KG_PER_METER.equals(profileMode.getValue())) {
            mkgField.setLabel("Gewicht per meter (kg/m)");
        } else {
            mkgField.setLabel("Meter per kilogram (m/kg)");
        }
    }

    private void configureSaveButton() {

        saveButton.addThemeVariants(
                ButtonVariant.LUMO_PRIMARY
        );

        saveButton.addClickListener(event -> saveWizard());
    }

    private void saveWizard() {

        if (selectedProduct == null) {
            Notification.show("Geen artikel geselecteerd");
            return;
        }

        WIZARDTYPE type = wizardComboBox.getValue();

        if (type == null) {
            Notification.show("Selecteer eerst een WizardType");
            return;
        }

        Wizard wizard = selectedProduct.getWizardtype();

        if (wizard == null) {
            wizard = new Wizard();
        }

        wizard.setWizardtype(type);

        switch (type) {

            case PLATE -> {

                Double rho = rhoField.getValue();

                if (rho == null || rho <= 0 ||
                        !Double.isFinite(rho)) {
                    Notification.show(
                            "Geef een geldige massadichtheid in"
                    );
                    return;
                }

                wizard.setRho(rho);
            }

            case PROFILE -> {

                Double value = mkgField.getValue();

                if (value == null || value <= 0 ||
                        !Double.isFinite(value)) {
                    Notification.show(
                            "Geef een geldig profielgewicht in"
                    );
                    return;
                }

                double mkg = value;

                if (KG_PER_METER.equals(profileMode.getValue())) {
                    mkg = 1.0 / value;
                }

                if (!Double.isFinite(mkg) || mkg <= 0) {
                    Notification.show("Ongeldige verhouding");
                    return;
                }

                wizard.setMkg(mkg);
            }
        }

        selectedProduct.setWizardtype(wizard);

        productService.save(selectedProduct);

        Notification.show(
                "Wizard is toegevoegd en bewaard"
        );
    }

    public void setSelectedProductForWizard(
            Product selectedProduct) {

        this.selectedProduct = selectedProduct;

        title.setText(
                "Koppel Wizard aan artikel: " +
                        selectedProduct.getInternalName()
        );

        rhoField.clear();
        mkgField.clear();

        // Altijd eerst de invoereenheid herstellen.
        profileMode.setValue(METER_PER_KG);

        Wizard wizard = selectedProduct.getWizardtype();

        if (wizard != null) {

            rhoField.setValue(wizard.getRho());
            mkgField.setValue(wizard.getMkg());

            wizardComboBox.setValue(wizard.getWizardtype());

            updateParameterLayout(wizard.getWizardtype());

        } else {

            wizardComboBox.clear();
            updateParameterLayout(null);
        }
    }
}
