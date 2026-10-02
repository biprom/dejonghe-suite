package com.adverto.dejonghe.common.repos;

import com.adverto.dejonghe.common.entities.updateVersion.ApplicationSettings;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicationSettingsRepository
        extends MongoRepository<ApplicationSettings, String> {
}
