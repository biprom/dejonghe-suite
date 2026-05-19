package com.adverto.dejonghe.server.customEvents;

import com.adverto.dejonghe.common.entities.quote.Quote;
import org.springframework.context.ApplicationEvent;

public class GetSelectedQuoteEvent extends ApplicationEvent {
    private final Quote selectedQuote;

    public GetSelectedQuoteEvent(Object source, Quote selectedQuote) {
        super(source);
        this.selectedQuote = selectedQuote;
    }

    public Quote getSelectedInvoice() {
        return selectedQuote;
    }
}
