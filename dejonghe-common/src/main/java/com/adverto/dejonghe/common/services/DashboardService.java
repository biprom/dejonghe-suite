package com.adverto.dejonghe.common.services;

import com.adverto.dejonghe.common.entities.dashboard.DashboardWorkEntity;
import com.adverto.dejonghe.common.repos.DashboardWorkEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    DashboardWorkEntityRepo dashboardWorkEntityRepo;

    public void saveDashboardWorkItem(DashboardWorkEntity dashboardWorkEntity) {
        dashboardWorkEntityRepo.save(dashboardWorkEntity);
    }

    public void removeItemsByWorkOrderId(String id) {
        dashboardWorkEntityRepo.removeAllByWorkOrderId(id);
    }
}
