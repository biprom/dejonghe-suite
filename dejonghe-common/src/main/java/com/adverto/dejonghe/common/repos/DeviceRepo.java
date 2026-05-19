package com.adverto.dejonghe.common.repos;


import com.adverto.dejonghe.common.entities.installation.Device;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeviceRepo extends MongoRepository<Device, String> {

}
