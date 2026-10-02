package com.adverto.dejonghe.server;

import com.adverto.dejonghe.common.entities.updateVersion.ApplicationSettings;
import com.adverto.dejonghe.common.repos.ApplicationSettingsRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationSettingsInitializer {

    private final ApplicationSettingsRepository settingsRepository;

    @Value("${tablet.local.latest-version}")
    private String latestLocalTabletVersion;

    @Value("${tablet.external.latest-version}")
    private String latestExternalTabletVersion;

    public ApplicationSettingsInitializer(
            ApplicationSettingsRepository settingsRepository) {

        this.settingsRepository = settingsRepository;
    }

    @PostConstruct
    public void initialize() {

        ApplicationSettings settings =
                settingsRepository.findById("GENERAL")
                        .orElseGet(() -> {
                            ApplicationSettings newSettings =
                                    new ApplicationSettings();

                            newSettings.setId("GENERAL");
                            return newSettings;
                        });

        settings.setLatestLocalTabletVersion(
                latestLocalTabletVersion
        );

        settings.setLatestExternalTabletVersion(
                latestExternalTabletVersion
        );

        settingsRepository.save(settings);
    }
}
