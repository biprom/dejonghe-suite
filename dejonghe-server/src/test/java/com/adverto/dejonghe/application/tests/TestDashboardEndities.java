package com.adverto.dejonghe.application.tests;

import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.dbservices.EmployeeService;
import com.adverto.dejonghe.common.dbservices.WorkOrderService;
import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrder;
import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrderTime;
import com.adverto.dejonghe.common.entities.dashboard.DashboardWorkEntity;
import com.adverto.dejonghe.common.entities.employee.Employee;
import com.adverto.dejonghe.common.entities.enums.workorder.WorkOrderStatus;
import com.adverto.dejonghe.common.services.DashboardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class TestDashboardEndities {

    CustomerService customerService;
    EmployeeService employeeService;
    WorkOrderService workOrderService;
    DashboardService dashboardService;

    List<WorkOrder>runningWorkOrders;

    @Autowired
    public TestDashboardEndities(CustomerService customerService,
                                 EmployeeService employeeService,
                                 WorkOrderService workOrderService,
                                 DashboardService dashboardService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.workOrderService = workOrderService;
        this.dashboardService = dashboardService;
    }

    @Test
    void nightScript(){
        //find running workOrders
        Optional<List<WorkOrder>> allWorkOrdersByStatus = workOrderService.getAllWorkOrdersByStatus(WorkOrderStatus.RUNNING);
        if(allWorkOrdersByStatus.isPresent()){
            runningWorkOrders = allWorkOrdersByStatus.get();
            for(WorkOrder workOrder : runningWorkOrders){
                //first remove all DashboardEntities with this workOrder_id
                dashboardService.removeItemsByWorkOrderId(workOrder.getId());
                //for Team 1
                if(workOrder.getMasterEmployeeTeam1() != null){
                    getDashboardEntitiesForEmployee(workOrder,workOrder.getMasterEmployeeTeam1(), 0);
                    //check if there are buddies
                    if((workOrder.getExtraEmployeesTeam1() != null) && (workOrder.getExtraEmployeesTeam1().size() > 0)){
                        for(Employee employee : workOrder.getExtraEmployeesTeam1()){
                            getDashboardEntitiesForEmployee(workOrder,employee,0);
                        }
                    }
                }
                //for Team 2
                if(workOrder.getMasterEmployeeTeam2() != null){
                    getDashboardEntitiesForEmployee(workOrder,workOrder.getMasterEmployeeTeam2(), 1);
                    //check if there are buddies
                    if((workOrder.getExtraEmployeesTeam2() != null) && (workOrder.getExtraEmployeesTeam2().size() > 0)){
                        for(Employee employee : workOrder.getExtraEmployeesTeam2()){
                            getDashboardEntitiesForEmployee(workOrder,employee,1);
                        }
                    }
                }
                //for Team 3
                if(workOrder.getMasterEmployeeTeam3() != null){
                    getDashboardEntitiesForEmployee(workOrder,workOrder.getMasterEmployeeTeam3(), 2);
                    //check if there are buddies
                    if((workOrder.getExtraEmployeesTeam3() != null) && (workOrder.getExtraEmployeesTeam3().size() > 0)){
                        for(Employee employee : workOrder.getExtraEmployeesTeam3()){
                            getDashboardEntitiesForEmployee(workOrder,employee,2);
                        }
                    }
                }
                //for Team 4
                if(workOrder.getMasterEmployeeTeam4() != null){
                    getDashboardEntitiesForEmployee(workOrder,workOrder.getMasterEmployeeTeam4(), 3);
                    //check if there are buddies
                    if((workOrder.getExtraEmployeesTeam4() != null) && (workOrder.getExtraEmployeesTeam4().size() > 0)){
                        for(Employee employee : workOrder.getExtraEmployeesTeam4()){
                            getDashboardEntitiesForEmployee(workOrder,employee,3);
                        }
                    }
                }
            }
        }
    }

    private void getDashboardEntitiesForEmployee(WorkOrder workOrder, Employee employee, Integer team) {
        List<WorkOrderTime> workOrderTimeList = workOrder.getWorkOrderHeaderList().get(team).getWorkOrderTimeList();
        for(WorkOrderTime workOrderTime : workOrderTimeList){
            DashboardWorkEntity dashboardWorkEntity = new DashboardWorkEntity();
            dashboardWorkEntity.setEmployeeId(employee.getId());
            dashboardWorkEntity.setCustomerId(customerService.getCustomerByWorkAddress(workOrder.getWorkAddress()).get().getFirst().getId());
            dashboardWorkEntity.setStart(workOrder.getWorkDateTime().withHour(workOrderTime.getTimeUp().getHour()).withMinute(workOrderTime.getTimeStop().getMinute()));
            dashboardWorkEntity.setEnd(workOrder.getWorkDateTime().withHour(workOrderTime.getTimeDown().getHour()).withMinute(workOrderTime.getTimeDown().getMinute()));
            dashboardWorkEntity.setPauze(workOrderTime.getPauze());
            dashboardWorkEntity.setWorkOrderId(workOrder.getId());
            dashboardWorkEntity.setWorkType(workOrder.getWorkOrderHeaderList().get(team).getWorkType());
            dashboardService.saveDashboardWorkItem(dashboardWorkEntity);
        }
    }
}