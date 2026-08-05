package com.adverto.dejonghe.common.entities.enums.product;

public enum ORDER_PRODUCT_STATUS {
    TO_ORDER ("Te bestellen"),
    ORDERED   ("Besteld"),
    DELIVERED ("Geleverd");

    private final String discription;

    ORDER_PRODUCT_STATUS(String discription) {
        this.discription = discription;
    }

    public String getDiscription() { return discription; }
}
