package com.csw.model;

public class ExternalIdentifier extends RegistryObject{

    private ObjectRef identificationScheme;
    private ObjectRef registryObject;
    private String value;

    public ObjectRef getIdentificationScheme() {
        return identificationScheme;
    }

    public void setIdentificationScheme(ObjectRef identificationScheme) {
        this.identificationScheme = identificationScheme;
    }

    public ObjectRef getRegistryObject() {
        return registryObject;
    }

    public void setRegistryObject(ObjectRef registryObject) {
        this.registryObject = registryObject;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
