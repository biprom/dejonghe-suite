package com.adverto.dejonghe.server.customEvents;

import com.adverto.dejonghe.common.entities.invoice.Invoice;
import org.springframework.context.ApplicationEvent;

public class GetSelectedInvoiceEvent extends ApplicationEvent {
    private final Invoice selectedInvoice;

    public GetSelectedInvoiceEvent(Object source, Invoice selectedInvoice) {
        super(source);
        this.selectedInvoice = selectedInvoice;
    }

    public Invoice getSelectedInvoice() {
        return selectedInvoice;
    }
}
