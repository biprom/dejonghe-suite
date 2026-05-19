package com.adverto.dejonghe.common.dbservices;

import com.adverto.dejonghe.common.entities.invoice.Invoice;
import com.adverto.dejonghe.common.repos.InvoiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    @Autowired
    InvoiceRepo invoiceRepo;

    public Optional<Invoice> getInvoiceById(String id) {
        Optional<Invoice> optionalInvoice = Optional.of(invoiceRepo.findInvoiceById(id));
        return optionalInvoice;
    }

    public Optional<List<Invoice>> getAll() {
        List<Invoice> invoices = invoiceRepo.findAll();
        if (!invoices.isEmpty()) {
            return Optional.of(invoices);
        }
        else{
            return Optional.empty();
        }
    }

    public Optional<Invoice> getLastProFormaInvoice() {
        return invoiceRepo.findTopBybFinalInvoiceFalseOrderByInvoiceNumberDesc();
    }

    public Optional<Invoice>  getLastFinalInvoice() {
        return invoiceRepo.findTopBybFinalInvoiceTrueOrderByFinalInvoiceNumberDesc();
    }

    public Optional<List<Invoice>> getAllInvoicesByFinalInvoice(Boolean finalInvoice) {
        List<Invoice> invoices = invoiceRepo.findInvoiceBybFinalInvoice(finalInvoice);
        if (!invoices.isEmpty()) {
            return Optional.of(invoices);
        }
        else{
            return Optional.empty();
        }
    }

    public Optional<List<Invoice>> getAllInvoicesByFinalInvoiceAndCustomerId(Boolean finalInvoice, String customerId) {
        List<Invoice> invoices = invoiceRepo.findInvoiceBybFinalInvoiceAndCustomer_Id(finalInvoice, customerId);
        if (!invoices.isEmpty()) {
            return Optional.of(invoices);
        }
        else{
            return Optional.empty();
        }
    }

    public void delete(Invoice invoice) {
        invoiceRepo.delete(invoice);
    }

    public void save(Invoice invoice) {
        invoiceRepo.save(invoice);
        }

    public List<Invoice> getUnpayedOpenInvoices(String customerId) {
        return invoiceRepo.findByCustomer_IdAndPaidFalse(customerId);
    }

    public List<Invoice> getUnpayedExpiredInvoices(String customerId, LocalDate date) {
        return invoiceRepo.findByCustomer_IdAndExpiryDateBeforeAndPaidFalse(customerId,date);
    }

    public List<Invoice> getInvoicesForCustomer(String customerId) {
        return invoiceRepo.findByCustomer_Id(customerId);
    }

    public Optional<Invoice> getInvoiceWithWorkOrderId(String workOrderId) {
        return invoiceRepo.findByWorkOrderList_Id(workOrderId);
    }
}
