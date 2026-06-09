package com.adverto.dejonghe.common.entities.WorkOrder;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;

@Document
@Getter
@Setter
@NoArgsConstructor
public class WorkOrderTimePdfDTO {
    String start;
    String pause;
    String End;
}
