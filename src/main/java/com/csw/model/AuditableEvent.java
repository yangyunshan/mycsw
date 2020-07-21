package com.csw.model;

import java.util.Set;

public class AuditableEvent extends RegistryObject {

    private ObjectRef eventType;
    private Set<ObjectRef> affectedObjects;
    private String requestId;
    private String timeStamp;
    private ObjectRef user;

    public ObjectRef getEventType() {
        return eventType;
    }

    public void setEventType(ObjectRef eventType) {
        this.eventType = eventType;
    }

    public Set<ObjectRef> getAffectedObjects() {
        return affectedObjects;
    }

    public void setAffectedObjects(Set<ObjectRef> affectedObjects) {
        this.affectedObjects = affectedObjects;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public ObjectRef getUser() {
        return user;
    }

    public void setUser(ObjectRef user) {
        this.user = user;
    }
}
