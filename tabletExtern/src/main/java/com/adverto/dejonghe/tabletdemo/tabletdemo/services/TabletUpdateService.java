package com.adverto.dejonghe.tabletdemo.tabletdemo.services;

import org.springframework.stereotype.Service;

@Service
public class TabletUpdateService {

    private boolean versionChecked = false;
    private boolean updateAvailable = true;
    private String latestVersion;

    public boolean isVersionChecked() {
        return versionChecked;
    }

    public boolean isUpdateAvailable() {
        return updateAvailable;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public void setVersionStatus(
            boolean versionChecked,
            boolean updateAvailable,
            String latestVersion
    ) {
        this.versionChecked = versionChecked;
        this.updateAvailable = updateAvailable;
        this.latestVersion = latestVersion;
    }
}
