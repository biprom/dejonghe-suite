package com.adverto.dejonghe.common.entities.employee;

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
public class Employee {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String abbreviation;
    private String phoneNumber;
    private String comment;
    private Boolean technician = false;
    private Boolean alert = false;
    private String alertMessage;
    private LocalDate birthDate;
    private LocalDate dateOfService;
    private Integer priority = 0;
}
