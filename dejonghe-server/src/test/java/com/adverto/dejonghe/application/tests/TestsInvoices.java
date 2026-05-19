package com.adverto.dejonghe.application.tests;

import com.adverto.dejonghe.common.dbservices.InvoiceService;
import com.adverto.dejonghe.common.entities.invoice.Invoice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class TestsInvoices {

    private InvoiceService invoiceService;

    @Autowired
    public TestsInvoices(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Test
    void addAllInvoicesBooleanFinalInvoiceFalse() {
            Optional<List<Invoice>> invoiceList = invoiceService.getAll();
            for (Invoice invoice : invoiceList.get()) {
                invoice.setBFinalInvoice(false);
                invoiceService.save(invoice);
            }
        }
}
