package com.csw.model;

import java.util.HashSet;
import java.util.Set;

public class Identifiable {

    private String id;          //主键
    private String home;
    private Set<Slot> slots = new HashSet<Slot>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public Set<Slot> getSlots() {
        return slots;
    }

    public void setSlots(Set<Slot> slots) {
        this.slots = slots;
    }
}
