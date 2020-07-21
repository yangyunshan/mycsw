package com.csw.model;

import java.util.HashSet;
import java.util.Set;

public class RegistryPackage extends RegistryObject {
    private Set<Identifiable> registryObjectList = new HashSet<Identifiable>();
    private String type = "";
    private String owner = "";

    public Set<Identifiable> getRegistryObjectList() {
        return registryObjectList;
    }

    public void setRegistryObjectList(Set<Identifiable> identifiables) {
        this.registryObjectList = identifiables;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
