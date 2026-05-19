package com.adverto.dejonghe.common.dbservices;

import com.adverto.dejonghe.common.entities.quote.Quote;
import com.adverto.dejonghe.common.repos.QuoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class QuoteService {
    @Autowired
    QuoteRepo quoteRepo;

    public Optional<Quote> getQuoteById(String id) {
        Optional<Quote> optionalQuote = Optional.of(quoteRepo.findQuoteById(id));
        return optionalQuote;
    }

    public Optional<List<Quote>> getAll() {
        List<Quote> quotes = quoteRepo.findAll();
        if (!quotes.isEmpty()) {
            return Optional.of(quotes);
        }
        else{
            return Optional.empty();
        }
    }

    public Optional<Quote> getLastQuote() {
        return quoteRepo.findTopByOrderByQuoteNumberDesc();
    }

    public Optional<List<Quote>> getAllQuoteByFinalQuote() {
        List<Quote> quotes = quoteRepo.findAll();
        if (!quotes.isEmpty()) {
            return Optional.of(quotes);
        }
        else{
            return Optional.empty();
        }
    }

    public Optional<List<Quote>> getAllQuotesCustomerId(String customerId) {
        List<Quote> quotes = quoteRepo.findQuoteByCustomer_Id(customerId);
        if (!quotes.isEmpty()) {
            return Optional.of(quotes);
        }
        else{
            return Optional.empty();
        }
    }

    public void delete(Quote quote) {
        quoteRepo.delete(quote);
    }

    public void save(Quote quote) {
        quoteRepo.save(quote);
        }


    public List<Quote> getExpiredQuotes(String customerId, LocalDate date) {
        return quoteRepo.findByCustomer_IdAndExpiryDateBefore(customerId,date);
    }

    public List<Quote> getQuotesForCustomer(String customerId) {
        return quoteRepo.findByCustomer_Id(customerId);
    }
}
