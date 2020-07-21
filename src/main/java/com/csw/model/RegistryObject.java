package com.csw.model;

import java.util.HashSet;
import java.util.Set;

public class RegistryObject extends Identifiable {

    private Set<Classification> classifications = new HashSet<Classification>();
    private InternationalString description;
    private Set<ExternalIdentifier> externalIdentifiers = new HashSet<ExternalIdentifier>();
    private String lid;
    private InternationalString name;
    private ObjectRef objectType;
    private ObjectRef status;
    private VersionInfo versionInfo;

    public Set<Classification> getClassifications() {
        return classifications;
    }

    public void setClassifications(Set<Classification> classifications) {
        this.classifications = classifications;
    }

    public InternationalString getDescription() {
        return description;
    }

    public void setDescription(InternationalString description) {
        this.description = description;
    }

    public Set<ExternalIdentifier> getExternalIdentifiers() {
        return externalIdentifiers;
    }

    public void setExternalIdentifiers(Set<ExternalIdentifier> externalIdentifiers) {
        this.externalIdentifiers = externalIdentifiers;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public InternationalString getName() {
        return name;
    }

    public void setName(InternationalString name) {
        this.name = name;
    }

    public ObjectRef getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectRef objectType) {
        this.objectType = objectType;
    }

    public ObjectRef getStatus() {
        return status;
    }

    public void setStatus(ObjectRef status) {
        this.status = status;
    }

    public VersionInfo getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(VersionInfo versionInfo) {
        this.versionInfo = versionInfo;
    }
}
