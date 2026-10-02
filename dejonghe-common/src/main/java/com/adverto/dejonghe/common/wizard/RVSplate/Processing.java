package com.adverto.dejonghe.common.wizard.RVSplate;

public enum Processing {

    CUT("Gesneden", null, null),
    LAZERED("gelaserd", "FF-LASER", "LK-KG");

    private final String description;
    private final String forfait;
    private final String kgPrice;

    Processing(String description, String forfait, String kgPrice) {
        this.description = description;
        this.forfait = forfait;
        this.kgPrice = kgPrice;
    }

    public String getDescription() {
        return description;
    }

    public String getForfaitProductCode() {
        return forfait;
    }

    public String getKgPriceProductCode() {
        return kgPrice;
    }

    @Override
    public String toString() {
        return description;
    }
}