package com.adverto.dejonghe.common.entities.updateVersion;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "application_settings")
public class ApplicationSettings {

    @Id
    private String id;

    private String latestLocalTabletVersion;
    private String latestExternalTabletVersion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLatestLocalTabletVersion() {
        return latestLocalTabletVersion;
    }

    public void setLatestLocalTabletVersion(String latestLocalTabletVersion) {
        this.latestLocalTabletVersion = latestLocalTabletVersion;
    }

    public String getLatestExternalTabletVersion() {
        return latestExternalTabletVersion;
    }

    public void setLatestExternalTabletVersion(String latestExternalTabletVersion) {
        this.latestExternalTabletVersion = latestExternalTabletVersion;
    }
}