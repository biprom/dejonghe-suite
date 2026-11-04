package com.adverto.dejonghe.common.wizard;

public enum WIZARDTYPE {

    PLATE("Plaat wizard"),
    PROFILE("Profiel wizard");

    private final String description;

    WIZARDTYPE(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
