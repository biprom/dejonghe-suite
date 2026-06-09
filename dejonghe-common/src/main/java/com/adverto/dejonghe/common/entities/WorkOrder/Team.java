package com.adverto.dejonghe.common.entities.WorkOrder;

import com.adverto.dejonghe.common.entities.enums.fleet.Fleet;
import com.adverto.dejonghe.common.entities.enums.fleet.FleetTruckCraneOptions;
import com.adverto.dejonghe.common.entities.enums.fleet.FleetWorkType;
import com.adverto.dejonghe.common.entities.enums.workorder.WorkType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
@NoArgsConstructor
public class Team {

    String technicians;
    String comment;
    String vihicle;
    String roadTunnelTax;
    List<WorkOrderTimePdfDTO> workHours;
}
