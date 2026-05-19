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
public class WorkOrderHeader {

    String description;

    WorkType workType;
    List<WorkOrderTime>workOrderTimeList;
    List<BowlEntity>bowlEntityList;
    Fleet fleet;
    FleetTruckCraneOptions fleetOptions;
    Double fleetHours;
    Double roadTax;
    Double tunnelTax;
    FleetWorkType fleetWorkType;
}
