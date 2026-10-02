package com.adverto.dejonghe.tabletdemo.tabletdemo.services;

import com.adverto.dejonghe.common.entities.updateVersion.ApplicationSettings;
import com.adverto.dejonghe.common.repos.ApplicationSettingsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TabletVersionChecker {

    @Value("${tablet.version}")
    private String currentVersion;

    private final ApplicationSettingsRepository settingsRepository;
    private final TabletUpdateService tabletUpdateService;

    public TabletVersionChecker(
            ApplicationSettingsRepository settingsRepository,
            TabletUpdateService tabletUpdateService) {

        this.settingsRepository = settingsRepository;
        this.tabletUpdateService = tabletUpdateService;
    }

    public void checkVersion() {

        try {

            ApplicationSettings settings =
                    settingsRepository.findById("GENERAL")
                            .orElse(null);

            /*
             * Geen settings gevonden of geen versie beschikbaar:
             * status is onzeker -> ROOD
             */
            if (settings == null
                    || settings.getLatestLocalTabletVersion() == null
                    || settings.getLatestLocalTabletVersion().isBlank()) {

                tabletUpdateService.setVersionStatus(
                        false,
                        true,
                        null
                );

                return;
            }

            String latestVersion =
                    settings.getLatestLocalTabletVersion();

            /*
             * Alleen groen wanneer de versies exact overeenkomen.
             */
            boolean updateAvailable =
                    !currentVersion.equals(latestVersion);

            tabletUpdateService.setVersionStatus(
                    true,
                    updateAvailable,
                    latestVersion
            );

        } catch (Exception e) {

            /*
             * Database niet bereikbaar of andere fout:
             * status is onzeker -> ROOD
             */
            tabletUpdateService.setVersionStatus(
                    false,
                    true,
                    null
            );
        }
    }
}
