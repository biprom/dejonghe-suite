package com.adverto.dejonghe.common.wizard;

import com.adverto.dejonghe.common.dbservices.ProductService;
import com.adverto.dejonghe.common.entities.product.product.Product;
import com.adverto.dejonghe.common.wizard.RVSplate.ProductWizardProfileDialog;
import com.adverto.dejonghe.common.wizard.RVSplate.ProductWizardRVSPlateDialog;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class ProductWizardService {

    private final ProductService productService;

    public ProductWizardService(ProductService productService) {
        this.productService = productService;
    }

    public void openWizard(
            WIZARDTYPE wizardtype,
            Product selectedProduct,
            Consumer<Product> onProductsAdded) {

        switch (wizardtype) {

            case PLATE -> new ProductWizardRVSPlateDialog(
                    onProductsAdded,
                    selectedProduct,
                    productService
            ).open();

            case PROFILE -> new ProductWizardProfileDialog(
                    onProductsAdded,
                    selectedProduct
            ).open();
        }
    }
}