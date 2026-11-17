package com.adverto.dejonghe.common.entities.employee;

public enum DayPart {
    MORNING("Voormiddag"),
    AFTERNOON("Namiddag");

    private final String label;

    DayPart(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
