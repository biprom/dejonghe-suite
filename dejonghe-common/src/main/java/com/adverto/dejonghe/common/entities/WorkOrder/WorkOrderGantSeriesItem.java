package com.adverto.dejonghe.common.entities.WorkOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
@Getter
@Setter
@NoArgsConstructor
public class WorkOrderGantSeriesItem {
    private String abbreviationName;
    private String workAddressName;
    private String workOrderId;
    private String status;
    private Instant start;
    private Instant end;
    private Integer yPosition;
    private int lane;
    private Integer prio;
}
