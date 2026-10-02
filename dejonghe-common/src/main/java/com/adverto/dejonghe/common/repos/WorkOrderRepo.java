package com.adverto.dejonghe.common.repos;


import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrder;
import com.adverto.dejonghe.common.entities.enums.workorder.WorkOrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WorkOrderRepo extends MongoRepository<WorkOrder, String> {
    List<WorkOrder>findWorkOrderByWorkOrderStatus(WorkOrderStatus status);
    Optional<WorkOrder> findWorkOrderById(String workOrderId);
    WorkOrder findWorkOrderByLinkedWorkOrdersContains(String id);
    WorkOrder findWorkOrderByWorkDateTime(LocalDateTime workDateTime);
    List<WorkOrder>findByWorkOrderStatusAndStarter(WorkOrderStatus status, Boolean starter);
    @Query("""
    {
        'workAddress.addressName': ?0,
        'starter': ?1,
        'imageList.0': { $exists: true }
    }
    """)
    Optional<List<WorkOrder>> findByWorkAddress_AddressNameAndImageListIsNotEmpty(
            String workAddressName,
            Boolean starter
    );
}
