package com.csw.model;

import java.util.HashSet;
import java.util.Set;

public class Notification extends RegistryObject {
    private ObjectRef subscription;
    private Set<Identifiable> registryObjectList = new HashSet<Identifiable>();

    public ObjectRef getSubscription() {
        return subscription;
    }

    public void setSubscription(ObjectRef subscription) {
        this.subscription = subscription;
    }

    public Set<Identifiable> getRegistryObjectList() {
        return registryObjectList;
    }

    public void setRegistryObjectList(Set<Identifiable> registryObjectList) {
        this.registryObjectList = registryObjectList;
    }
}
