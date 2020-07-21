package com.csw.model;

import java.util.HashSet;
import java.util.Set;

public class InternationalString {
    private String id;
    private Set<LocalizedString> localizedStrings = new HashSet<LocalizedString>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<LocalizedString> getLocalizedStrings() {
        return localizedStrings;
    }

    public void setLocalizedStrings(Set<LocalizedString> localizedStrings) {
        this.localizedStrings = localizedStrings;
    }
}
