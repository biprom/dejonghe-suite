package com.adverto.dejonghe.common.wizard;

public enum WIZARD {

    RVS_PLATE("RVS-Plaat wizard");

    private final String description;

    WIZARD(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
