package com.adverto.dejonghe.common.entities.installation;

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
public class Device {
    @Id
    private String id;
    private String type;
    private String code;
    private String deviceName;
    private String serialNumber;
    private String comment;
    private List<String> coupledImages;
    private List<String> coupledDocuments;
    private LocalDate date;
    private String invoiceNumber;
}
