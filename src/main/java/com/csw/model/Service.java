package com.csw.model;

import java.util.HashSet;
import java.util.Set;

public class Service extends RegistryObject {

    private Set<ServiceBinding> serviceBindings = new HashSet<ServiceBinding>();

    public Set<ServiceBinding> getServiceBindings() {
        return serviceBindings;
    }

    public void setServiceBindings(Set<ServiceBinding> serviceBindings) {
        this.serviceBindings = serviceBindings;
    }
}
