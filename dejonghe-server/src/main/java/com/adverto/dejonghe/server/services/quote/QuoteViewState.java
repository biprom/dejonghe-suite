package com.adverto.dejonghe.server.services.quote;

import com.adverto.dejonghe.common.entities.enums.invoice.FINAL_INVOICE_STATUS;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@VaadinSessionScope
@Service
@Getter
@Setter
@NoArgsConstructor
public class QuoteViewState {
    private String customer;
    private String number;
    private FINAL_INVOICE_STATUS status;
}
