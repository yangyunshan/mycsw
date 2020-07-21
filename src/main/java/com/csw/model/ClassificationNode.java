package com.csw.model;

public class ClassificationNode extends RegistryObject {

    private ObjectRef parent;
    private String code;
    private String path;

    public ObjectRef getParent() {
        return parent;
    }

    public void setParent(ObjectRef parent) {
        this.parent = parent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
