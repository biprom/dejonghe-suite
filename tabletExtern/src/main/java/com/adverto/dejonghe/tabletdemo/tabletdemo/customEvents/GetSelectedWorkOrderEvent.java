package com.adverto.dejonghe.tabletdemo.tabletdemo.customEvents;

import com.adverto.dejonghe.common.entities.WorkOrder.WorkOrder;
import org.springframework.context.ApplicationEvent;

public class GetSelectedWorkOrderEvent extends ApplicationEvent {
    private final WorkOrder selectedWorkOrder;

    public GetSelectedWorkOrderEvent(Object source, WorkOrder selectedWorkOrder) {
        super(source);
        this.selectedWorkOrder = selectedWorkOrder;
    }

    public WorkOrder getSelectedWorkOrder() {
        return selectedWorkOrder;
    }
}
