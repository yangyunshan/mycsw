package com.csw.model;

public class Association extends RegistryObject {

    private ObjectRef associationType;
    private ObjectRef sourceObject;
    private ObjectRef targetObject;

    public ObjectRef getAssociationType() {
        return associationType;
    }

    public void setAssociationType(ObjectRef association) {
        this.associationType = association;
    }

    public ObjectRef getSourceObject() {
        return sourceObject;
    }

    public void setSourceObject(ObjectRef sourceObject) {
        this.sourceObject = sourceObject;
    }

    public ObjectRef getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(ObjectRef targetObject) {
        this.targetObject = targetObject;
    }
}
