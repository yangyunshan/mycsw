package com.csw.model;

public class ExtrinsicObject extends RegistryObject {

    private VersionInfo contentVersionInfo;
    private Integer isOpaque;
    private String mimeType;

    public VersionInfo getContentVersionInfo() {
        return contentVersionInfo;
    }

    public void setContentVersionInfo(VersionInfo contentVersionInfo) {
        this.contentVersionInfo = contentVersionInfo;
    }

    public Integer isOpaque() {
        return isOpaque;
    }

    public void setOpaque(Integer opaque) {
        isOpaque = opaque;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
