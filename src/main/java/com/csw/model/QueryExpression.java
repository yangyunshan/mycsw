package com.csw.model;

public class QueryExpression {
    private String id;
    private ObjectRef queryLanguage;
    private String angthings;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ObjectRef getQueryLanguage() {
        return queryLanguage;
    }

    public void setQueryLanguage(ObjectRef queryLanguage) {
        this.queryLanguage = queryLanguage;
    }

    public String getAngthings() {
        return angthings;
    }

    public void setAngthings(String angthings) {
        this.angthings = angthings;
    }
}
