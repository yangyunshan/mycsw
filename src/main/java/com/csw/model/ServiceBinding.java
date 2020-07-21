package com.csw.model;

import java.util.HashSet;
import java.util.Set;

public class ServiceBinding extends RegistryObject {

    private String accessURI;
    private ObjectRef service;
    private Set<SpecificationLink> specificationLinks = new HashSet<SpecificationLink>();
    private ObjectRef targetBinding;

    public String getAccessURI() {
        return accessURI;
    }

    public void setAccessURI(String accessURI) {
        this.accessURI = accessURI;
    }

    public ObjectRef getService() {
        return service;
    }

    public void setService(ObjectRef service) {
        this.service = service;
    }

    public Set<SpecificationLink> getSpecificationLinks() {
        return specificationLinks;
    }

    public void setSpecificationLinks(Set<SpecificationLink> specificationLinks) {
        this.specificationLinks = specificationLinks;
    }

    public ObjectRef getTargetBinding() {
        return targetBinding;
    }

    public void setTargetBinding(ObjectRef targetBinding) {
        this.targetBinding = targetBinding;
    }
}
