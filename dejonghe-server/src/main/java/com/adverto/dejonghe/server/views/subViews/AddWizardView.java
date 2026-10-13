package com.adverto.dejonghe.server.views.subViews;

import com.adverto.dejonghe.common.dbservices.ProductService;
import com.adverto.dejonghe.common.entities.product.product.Product;
import com.adverto.dejonghe.common.wizard.WIZARD;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.context.annotation.Scope;

@org.springframework.stereotype.Component
@Scope("prototype")
public class AddWizardView extends VerticalLayout {

    private final ProductService productService;
    Product selectedProduct;
    H1 title = new H1();
    ComboBox<WIZARD>wizardComboBox;


    public AddWizardView(ProductService productService) {
        this.add(title);
        this.add(setUpWizardCombobox());
        this.productService = productService;
    }

    private Component setUpWizardCombobox() {
        wizardComboBox = new ComboBox<>();
        wizardComboBox.setItems(WIZARD.values());
        wizardComboBox.setItemLabelGenerator(x -> x.getDescription());
        wizardComboBox.addValueChangeListener(x -> {
            selectedProduct.setWizard(x.getValue());
            productService.save(selectedProduct);
            Notification.show("Wizard is toegevoegd en bewaard");
        });
        return wizardComboBox;
    }


    public void setSelectedProductForWizard(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
        title.setText("Koppel Wizard aan artikel : " + selectedProduct.getInternalName());
        if(selectedProduct.getWizard() != null){
            wizardComboBox.setValue(selectedProduct.getWizard());
        }
    }
}
