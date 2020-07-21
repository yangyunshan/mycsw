package com.csw.model;

public class TelephoneNumber {

    private String id;
    private String areaCode;
    private String countryCode;
    private String extension;
    private String number;
    private ObjectRef phoneType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ObjectRef getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(ObjectRef phoneType) {
        this.phoneType = phoneType;
    }
}
