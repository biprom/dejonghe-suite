package com.adverto.dejonghe.common.entities.WorkOrder;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Document
@Getter
@Setter
@NoArgsConstructor
public class WorkOrderTime {
    @Id
    private String id;

    LocalTime timeUp;
    LocalTime timeDown;
    LocalTime timeStart;
    LocalTime timeStop;
    Integer pauze = 0;
    Boolean bOvernight;

}
