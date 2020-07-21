package com.csw.model;

public class EmailAddress {

    private String id;
    private String address;
    private ObjectRef type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ObjectRef getType() {
        return type;
    }

    public void setType(ObjectRef type) {
        this.type = type;
    }
}
