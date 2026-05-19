package com.adverto.dejonghe.common.repos;


import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrder;
import com.adverto.dejonghe.common.entities.enums.workorder.WorkOrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WorkOrderRepo extends MongoRepository<WorkOrder, String> {
    List<WorkOrder>findWorkOrderByWorkOrderStatus(WorkOrderStatus status);
    Optional<WorkOrder> findWorkOrderById(String workOrderId);
    WorkOrder findWorkOrderByLinkedWorkOrdersContains(String id);
    WorkOrder findWorkOrderByWorkDateTime(LocalDateTime workDateTime);
    List<WorkOrder>findByWorkOrderStatusAndStarter(WorkOrderStatus status, Boolean starter);
}
