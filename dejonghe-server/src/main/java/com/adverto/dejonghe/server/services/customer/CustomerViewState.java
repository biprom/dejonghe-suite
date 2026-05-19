package com.adverto.dejonghe.server.services.customer;

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
public class CustomerViewState {
    private String searchText;
}
