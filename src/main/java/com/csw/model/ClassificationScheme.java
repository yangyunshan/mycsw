package com.csw.model;

public class ClassificationScheme extends RegistryObject {

    private int isInternal;
    private ObjectRef nodeType;

    public int getInternal() {
        return isInternal;
    }

    public void setInternal(int internal) {
        isInternal = internal;
    }

    public ObjectRef getNodeType() {
        return nodeType;
    }

    public void setNodeType(ObjectRef nodeType) {
        this.nodeType = nodeType;
    }
}
