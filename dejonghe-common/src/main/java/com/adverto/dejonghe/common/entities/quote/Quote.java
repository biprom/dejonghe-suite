package com.adverto.dejonghe.common.entities.quote;

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

@Document
@Getter
@Setter
@NoArgsConstructor
public class Quote {
    @Id
    private String id;

    Boolean bFinalDocument = false;
    Integer quoteNumber;
    Customer customer;
    Address workAddress;
    LocalDate quoteDate;
    LocalDate expiryDate;
    String discription;
    Boolean toCheck = false;
    Boolean bApproved = false;
    Boolean bRejected = false;
    Boolean bSend = false;
    Boolean expired = false;
    Boolean reminder1 = false;
    Boolean reminder2 = false;
    Boolean reminder3 = false;
    Double totalAmountTempPlaceholder;
    Boolean finalizeQuote = false;

    List<Product>productList;

    List<String>imageList;

}
