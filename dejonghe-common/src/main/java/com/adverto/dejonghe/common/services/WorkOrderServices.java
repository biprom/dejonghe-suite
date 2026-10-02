package com.adverto.dejonghe.common.services;

import com.adverto.dejonghe.common.dbservices.WorkOrderService;
import com.adverto.dejonghe.common.entities.WorkOrder.*;
import com.adverto.dejonghe.common.entities.enums.fleet.Fleet;
import com.adverto.dejonghe.common.entities.enums.fleet.FleetWorkType;
import com.adverto.dejonghe.common.entities.enums.workorder.WorkLocation;
import com.adverto.dejonghe.common.entities.enums.workorder.WorkType;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class WorkOrderServices {
    WorkOrderService workOrderService;
    List<String>errorList;

    public WorkOrderServices(WorkOrderService workOrderService) {
        this.workOrderService = workOrderService;
    }
    public List<String> checkWorkOrderBeforeSendToInvoice(
            WorkOrder workOrder,
            List<String> errorList) {

        this.errorList = errorList;
        errorList.clear();

        Set<String> checkedWorkOrders = new HashSet<>();

        checkWorkOrderAndLinked(workOrder, checkedWorkOrders);

        return errorList;
    }

    private void checkWorkOrderAndLinked(
            WorkOrder workOrder,
            Set<String> checkedWorkOrders) {

        if (workOrder == null) {
            return;
        }

        if (workOrder.getId() != null
                && !checkedWorkOrders.add(workOrder.getId())) {
            return;
        }

        checkSingleWorkOrder(workOrder);

        if (workOrder.getLinkedWorkOrders() != null) {

            for (String linkedWorkOrderId : workOrder.getLinkedWorkOrders()) {

                if (linkedWorkOrderId == null
                        || linkedWorkOrderId.isBlank()) {
                    continue;
                }

                Optional<WorkOrder> linkedWorkOrder =
                        workOrderService.getWorkOrderById(linkedWorkOrderId);

                if (linkedWorkOrder != null) {
                    checkWorkOrderAndLinked(
                            linkedWorkOrder.get(),
                            checkedWorkOrders
                    );
                }
            }
        }
    }

    private void checkSingleWorkOrder(WorkOrder workOrder) {

        String workOrderInfo =
                "Werkbon " + workOrder.getWorkDateTime().format(DateTimeFormatter.ofPattern("dd/MM"));

        checkWorkOrder(workOrder);

        List<WorkOrderHeader> headers =
                workOrder.getWorkOrderHeaderList();

        if (headers == null) {
            return;
        }

        if (workOrder.getMasterEmployeeTeam1() != null
                && headers.size() > 0) {

            checkHeader(
                    workOrderInfo + " - Team 1",
                    workOrder.getWorkLocation(),
                    headers.get(0)
            );
        }

        if (workOrder.getMasterEmployeeTeam2() != null
                && headers.size() > 1) {

            checkHeader(
                    workOrderInfo + " - Team 2",
                    workOrder.getWorkLocation(),
                    headers.get(1)
            );
        }

        if (workOrder.getMasterEmployeeTeam3() != null
                && headers.size() > 2) {

            checkHeader(
                    workOrderInfo + " - Team 3",
                    workOrder.getWorkLocation(),
                    headers.get(2)
            );
        }

        if (workOrder.getMasterEmployeeTeam4() != null
                && headers.size() > 3) {

            checkHeader(
                    workOrderInfo + " - Team 4",
                    workOrder.getWorkLocation(),
                    headers.get(3)
            );
        }
    }

    private void checkWorkOrder(WorkOrder workOrder) {
        if(workOrder.getWorkAddress() == null){
            errorList.add("Klant is niet ingevuld!");
        }
        if(workOrder.getWorkDateTime() == null){
            errorList.add("Datum is niet ingevuld!");
        }
        if(workOrder.getWorkLocation() == null){
            errorList.add("Locatie is niet ingevuld!");
        }
        if(workOrder.getMasterEmployeeTeam1() == null){
            errorList.add("Team1 is niet ingevuld!");
        }
    }

    private void checkHeader(String teamNumber, WorkLocation workLocation, WorkOrderHeader workOrderHeader) {

        if(workOrderHeader.getWorkType() == null){
            errorList.add(teamNumber + " Type Werk is niet ingevuld!");
        }

        if(workOrderHeader.getWorkType().equals(WorkType.CENTRIFUGE)){
            if((workOrderHeader.getBowlEntityList() != null) && (workOrderHeader.getBowlEntityList().size() > 0)){
                for(BowlEntity bowlEntity : workOrderHeader.getBowlEntityList()){
                    if((workLocation.equals(WorkLocation.ON_THE_MOVE)) && ((bowlEntity.getChassisNumber() == null) || (bowlEntity.getChassisNumber().isEmpty()))){
                        errorList.add(teamNumber + " Chassisnummer centrifuge is niet ingevuld!");
                    }
                    if((workLocation.equals(WorkLocation.ON_THE_MOVE)) && (bowlEntity.getWorkhours() == null)){
                        errorList.add(teamNumber + " Draaiuren centrifuge is niet ingevuld!");
                    }
                    if((bowlEntity.getBBowlRemoved())){
                        if((workLocation.equals(WorkLocation.ON_THE_MOVE)) && (bowlEntity.getBowlRemovedNumber() == null) || (bowlEntity.getBowlRemovedNumber().isEmpty())){
                            errorList.add(teamNumber + " Verwijderde Bowlnummer is niet ingevuld!");
                        }
                    }
                    if((bowlEntity.getBBowlReplaced())){
                        if((workLocation.equals(WorkLocation.ON_THE_MOVE)) && (bowlEntity.getBowlReplacedNumber() == null) || (bowlEntity.getBowlReplacedNumber().isEmpty())){
                            errorList.add(teamNumber + " Teruggeplaatste Bowlnummer is niet ingevuld!");
                        }
                    }
                }
            }
        }
        if(workOrderHeader.getDescription() == null){
            errorList.add(teamNumber + " :  Omschrijving is niet ingevuld!");
        }

        if(workLocation.equals(WorkLocation.ON_THE_MOVE)){
            int amountHoursNotCorrect = 0;
            if(workOrderHeader.getWorkOrderTimeList() != null){
                for(WorkOrderTime workOrderTime : workOrderHeader.getWorkOrderTimeList()){
                    if((workOrderTime.getTimeUp() == null)
                            || (workOrderTime.getTimeStart() == null)
                            || (workOrderTime.getTimeStop() == null)
                            || (workOrderTime.getTimeDown() == null)){
                        amountHoursNotCorrect++;
                    }
                }
                if(amountHoursNotCorrect > 0){
                    errorList.add(teamNumber + " : " + amountHoursNotCorrect + " Werkuren zijn niet ingevuld!");
                }
            }
            else{
                errorList.add(teamNumber + " : " +" Werkuren zijn niet ingevuld!");
            }
            if(workOrderHeader.getFleet() == null){
                errorList.add(teamNumber + " : " +" Voertuig is niet ingevuld!");
            }
        }
        else{
            int amountHoursNotCorrect = 0;
            if(workOrderHeader.getWorkOrderTimeList() != null){
                for(WorkOrderTime workOrderTime : workOrderHeader.getWorkOrderTimeList()){
                    if((workOrderTime.getTimeStart() == null)
                            || (workOrderTime.getTimeStop() == null)){
                        amountHoursNotCorrect++;
                    }
                }
                if(amountHoursNotCorrect > 0){
                    errorList.add(teamNumber + " : " + amountHoursNotCorrect + " Werkuren zijn niet ingevuld!");
                }
            }
            else{
                errorList.add(teamNumber + " : " +" Werkuren zijn niet ingevuld!");
            }
        }

        if(workLocation.equals(WorkLocation.ON_THE_MOVE)){
            if(workOrderHeader.getFleet() == null){
                errorList.add(teamNumber + " :  Voertuig is niet ingevuld!");
            }
            if((workOrderHeader.getFleet() != null) && (workOrderHeader.getFleet().equals(Fleet.TRUCK_CRANE))){
                if(workOrderHeader.getFleetWorkType() == null){
                    errorList.add(teamNumber + " :  Type werk kraan is niet ingevuld!");
                }
                if((workOrderHeader.getFleetWorkType() != null) && (workOrderHeader.getFleetWorkType().equals(FleetWorkType.INTENS))){
                    if(workOrderHeader.getFleetHours() == null){
                        errorList.add(teamNumber + " :  Uren kraan is niet ingevuld!");
                    }
                }
            }
        }
    }
}
