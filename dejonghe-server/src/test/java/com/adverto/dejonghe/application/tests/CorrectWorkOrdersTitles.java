package com.adverto.dejonghe.application.tests;

import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.dbservices.WorkOrderService;
import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrder;
import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrderHeader;
import com.adverto.dejonghe.common.entities.customers.Customer;
import com.adverto.dejonghe.server.Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = Application.class)
class CorrectWorkOrdersTitles {

    WorkOrderService workOrderService;


    @Autowired
    public CorrectWorkOrdersTitles(WorkOrderService workOrderService) {
        this.workOrderService = workOrderService;
    }

    @Test
    void correctWorkOrderTitles() {
        Optional<List<WorkOrder>> all = workOrderService.getAll();
        if(!all.isEmpty()){
            for(WorkOrder workOrder : all.get()){
                for(WorkOrderHeader header : workOrder.getWorkOrderHeaderList()){
                    if ((header.getTitle() == null) || (header.getTitle().length() == 0)){
                        header.setTitle(" ");
                    }
                    else{

                    }
                }
                workOrderService.save(workOrder);
            }
        }
    }
}
