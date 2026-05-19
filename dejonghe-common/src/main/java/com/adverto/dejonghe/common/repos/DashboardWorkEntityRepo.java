package com.adverto.dejonghe.common.repos;

import com.adverto.dejonghe.common.entities.dashboard.DashboardWorkEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DashboardWorkEntityRepo extends MongoRepository<DashboardWorkEntity, String> {
    public void removeAllByWorkOrderId(String workOrderId);
}
