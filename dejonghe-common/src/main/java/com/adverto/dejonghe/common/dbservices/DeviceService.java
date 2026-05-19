package com.adverto.dejonghe.common.dbservices;

import com.adverto.dejonghe.common.entities.installation.Device;
import com.adverto.dejonghe.common.repos.DeviceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeviceService {
    @Autowired
    DeviceRepo deviceRepo;

    public String save(Device device) {
        Device storedDevice = deviceRepo.save(device);
        return storedDevice.getId();
    }

    public Optional<Device> getDeviceById(String id) {
        return deviceRepo.findById(id);
    }

    public void deleteById(String id) {
        deviceRepo.deleteById(id);
    }


}
