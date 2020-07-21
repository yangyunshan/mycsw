package com.csw.model;

public class NotifyAction extends Action {
    private String endPoint;
    private ObjectRef notificationOption;

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public ObjectRef getNotificationOption() {
        return notificationOption;
    }

    public void setNotificationOption(ObjectRef notificationOption) {
        this.notificationOption = notificationOption;
    }
}
