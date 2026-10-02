package com.adverto.dejonghe.server.views.dashboard;

import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@UIScope
public class DashboardDateState {
    private LocalDate startDate = LocalDate.now();
    private LocalDate endDate = LocalDate.now();

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setRange(
            LocalDate startDate,
            LocalDate endDate) {

        this.startDate = startDate;
        this.endDate = endDate;
    }
}
