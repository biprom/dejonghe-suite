package com.adverto.dejonghe.common.entities.WorkOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
@NoArgsConstructor
public class TeamSummary {

    String comment;
    String date;
    String location;
    String technicians;
    String vihicle;
    String roadTunnelTax;
    List<WorkOrderTimePdfDTO> workHours;
}
