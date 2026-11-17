package com.adverto.dejonghe.common.entities.employee;

public enum AbsenceType {
    VACATION("Verlof"),
    SICK("Ziekte"),
    OTHER("Andere afwezigheid");

    private final String label;

    AbsenceType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
