package com.csw.model;

public class ObjectRef extends Identifiable{

    private Boolean createReplica;

    public void setCreateReplica(Boolean createReplica) {
        this.createReplica = createReplica;
    }

    public Boolean getCreateReplica() {
        return createReplica;
    }
}
