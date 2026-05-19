package com.adverto.dejonghe.common.entities.invoice;

import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrder;
import com.adverto.dejonghe.common.entities.customers.Address;
import com.adverto.dejonghe.common.entities.customers.Customer;
import com.adverto.dejonghe.common.entities.product.product.Product;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Document
@Getter
@Setter
@NoArgsConstructor
public class Invoice {
    @Id
    private String id;

    Boolean bFinalInvoice = false;
    Integer invoiceNumber;
    Integer finalInvoiceNumber;
    Integer billitNumber;
    Customer customer;
    Address workAddress;
    Address projectWorkAddress;
    LocalDate invoiceDate;
    LocalDate expiryDate;
    LocalDate paymentDate;
    String discription;
    Boolean sendToBillit = false;
    Boolean billitError = false;
    Boolean toCheck = false;
    Boolean bApproved = false;
    Boolean bRejected = false;
    Boolean requestPoNumber = false;
    Boolean expired = false;
    Boolean paid = false;
    Boolean partialPaid = false;
    Boolean unpaid = false;
    Boolean reminder1 = false;
    Boolean reminder2 = false;
    Boolean reminder3 = false;
    Double totalAmountTempPlaceholder;
    Boolean finalizeInvoice = false;
    String poNumber;

    Set<WorkOrder> workOrderList;

    List<Product>productList;

    List<String>imageList;

    List<Payment>paymentList;
    Double totalPayedAmount;

}
