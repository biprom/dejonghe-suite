package com.adverto.dejonghe.server.customEvents;

import com.adverto.dejonghe.common.entities.product.product.Product;
import org.springframework.context.ApplicationEvent;

public class GetSelectedProductEvent extends ApplicationEvent {
    private final Product selectedProduct;

    public GetSelectedProductEvent(Object source, Product selectedProduct) {
        super(source);
        this.selectedProduct = selectedProduct;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }
}
