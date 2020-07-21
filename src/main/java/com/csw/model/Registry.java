package com.csw.model;

public class Registry extends RegistryObject {
    private String catalogingLatency;
    private String conformanceProfile;
    private ObjectRef operator;
    private String replicationSyncLatency;
    private String specificationVersion;

    public String getCatalogingLatency() {
        return catalogingLatency;
    }

    public void setCatalogingLatency(String catalogingLatency) {
        this.catalogingLatency = catalogingLatency;
    }

    public String getConformanceProfile() {
        return conformanceProfile;
    }

    public void setConformanceProfile(String conformanceProfile) {
        this.conformanceProfile = conformanceProfile;
    }

    public ObjectRef getOperator() {
        return operator;
    }

    public void setOperator(ObjectRef operator) {
        this.operator = operator;
    }

    public String getReplicationSyncLatency() {
        return replicationSyncLatency;
    }

    public void setReplicationSyncLatency(String replicationSyncLatency) {
        this.replicationSyncLatency = replicationSyncLatency;
    }

    public String getSpecificationVersion() {
        return specificationVersion;
    }

    public void setSpecificationVersion(String specificationVersion) {
        this.specificationVersion = specificationVersion;
    }
}
