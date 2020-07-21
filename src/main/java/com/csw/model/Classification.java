package com.csw.model;

public class Classification extends RegistryObject{

    private ObjectRef classificationScheme;
    private ObjectRef classificationNode;
    private ObjectRef classifiedObject;
    private String nodeRepresentation;

    public ObjectRef getClassificationScheme() {
        return classificationScheme;
    }

    public void setClassificationScheme(ObjectRef classificationScheme) {
        this.classificationScheme = classificationScheme;
    }

    public ObjectRef getClassificationNode() {
        return classificationNode;
    }

    public void setClassificationNode(ObjectRef classificationNode) {
        this.classificationNode = classificationNode;
    }

    public ObjectRef getClassifiedObject() {
        return classifiedObject;
    }

    public void setClassifiedObject(ObjectRef classifiedObject) {
        this.classifiedObject = classifiedObject;
    }

    public String getNodeRepresentation() {
        return nodeRepresentation;
    }

    public void setNodeRepresentation(String nodeRepresentation) {
        this.nodeRepresentation = nodeRepresentation;
    }
}
