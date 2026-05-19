package com.adverto.dejonghe.common.repos;


import com.adverto.dejonghe.common.entities.quote.Quote;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface QuoteRepo extends MongoRepository<Quote, String> {
    List<Quote>findQuoteByQuoteNumber(String quoteNumber);
    Quote findQuoteById(String workOrderId);
    List<Quote>findAll();
    List<Quote>findQuoteByCustomer_Id(String customerId);
    List<Quote>findByCustomer_IdAndExpiryDateBefore(String customerId, LocalDate expiryDate);
    Optional<Quote> findTopByOrderByQuoteNumberDesc();
    List<Quote> findByCustomer_Id(String customerId);
}
