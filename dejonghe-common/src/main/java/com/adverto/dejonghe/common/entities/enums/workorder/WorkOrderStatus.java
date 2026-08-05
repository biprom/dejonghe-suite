package com.adverto.dejonghe.common.entities.enums.workorder;

public enum WorkOrderStatus {
    RUNNING ("Lopend", "(l)"),
    FINISHED   ("Afgewerkt", "(a)"),
    INVOICED   ("Gefactureerd", "(g)");

    private final String discription;
    private final String abbr ;

    WorkOrderStatus(String discription, String abbr) {

        this.discription = discription;
        this.abbr = abbr;
    }

    public String getDiscription() { return discription; }
    public String getAbbr() { return abbr; }
}
