package com.adverto.dejonghe.common.repos;


import com.adverto.dejonghe.common.entities.invoice.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepo extends MongoRepository<Invoice, String> {
    List<Invoice>findInvoiceByInvoiceNumber(String invoiceNumber);
    Invoice findInvoiceById(String workOrderId);
    List<Invoice>findInvoiceBybFinalInvoice(Boolean finalInvoice);
    List<Invoice>findInvoiceBybFinalInvoiceAndCustomer_Id(Boolean finalInvoice, String customerId);
    Optional<Invoice> findTopBybFinalInvoiceFalseOrderByInvoiceNumberDesc();
    Optional<Invoice> findTopBybFinalInvoiceTrueOrderByFinalInvoiceNumberDesc();
    List<Invoice> findByCustomer_IdAndExpiryDateBeforeAndPaidFalse(String Id, LocalDate date);
    List<Invoice> findByCustomer_Id(String customerId);
    List<Invoice> findByCustomer_IdAndPaidFalse(String customerId);
    Optional<Invoice> findByWorkOrderList_Id(String workOrderId);
}
