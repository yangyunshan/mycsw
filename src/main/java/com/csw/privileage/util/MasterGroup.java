package com.csw.privileage.util;

import java.sql.Timestamp;

public class MasterGroup {
    private int id;
    private int masterId;
    private String masterName;
    private int groupId;
    private int masterId2;
    private String masterName2;
    private Timestamp createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMasterId() {
        return masterId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getMasterId2() {
        return masterId2;
    }

    public void setMasterId2(int masterId2) {
        this.masterId2 = masterId2;
    }

    public String getMasterName2() {
        return masterName2;
    }

    public void setMasterName2(String masterName2) {
        this.masterName2 = masterName2;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
}
