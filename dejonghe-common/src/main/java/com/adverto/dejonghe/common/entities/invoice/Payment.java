package com.adverto.dejonghe.common.entities.invoice;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Getter
@Setter
@NoArgsConstructor
public class Payment {
    @Id
    private String id;
    LocalDate paymentDate = LocalDate.now();
    Double paymentAmount = 0.0;
    String comment = "";
}
