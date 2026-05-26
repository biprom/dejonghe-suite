package com.adverto.dejonghe.common.entities.sms;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Getter
@Setter
@NoArgsConstructor
public class Sms {
    @Id
    private String id;

    private String number;
    private String message;
}
