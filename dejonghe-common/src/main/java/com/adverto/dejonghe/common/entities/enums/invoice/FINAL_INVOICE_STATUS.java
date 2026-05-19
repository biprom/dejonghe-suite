package com.adverto.dejonghe.common.entities.enums.invoice;

public enum FINAL_INVOICE_STATUS {
    TO_FINALISE ("Te finaliseren"),
    TO_SEND   ("Te versturen"),
    OPEN   ("Openstaand nt vervallen"),
    PARTIAL_PAID   ("Gedeeltelijk Betaald"),
    PAID   ("Betaald"),
    EXPIRED ("Vervallen"),;

    private final String discription;

    FINAL_INVOICE_STATUS(String discription) {
        this.discription = discription;
    }

    public String getDiscription() { return discription; }
}
