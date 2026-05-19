package com.adverto.dejonghe.common.entities.dashboard;

import com.adverto.dejonghe.common.entities.enums.workorder.WorkLocation;
import com.adverto.dejonghe.common.entities.enums.workorder.WorkType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Getter
@Setter
@NoArgsConstructor
public class DashboardWorkEntity {
    @Id
    private String id;
    LocalDateTime start;
    LocalDateTime end;
    Integer pauze = 0;
    WorkType workType;
    WorkLocation workLocation;
    String customerId;
    String employeeId;
    String workOrderId;
}
