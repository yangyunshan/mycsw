package com.csw.model;

public class Federation extends RegistryObject {
    private String replicationSyncLatency;

    public String getReplicationSyncLatency() {
        return replicationSyncLatency;
    }

    public void setReplicationSyncLatency(String replicationSyncLatency) {
        this.replicationSyncLatency = replicationSyncLatency;
    }
}
