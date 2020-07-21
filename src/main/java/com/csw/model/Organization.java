package com.csw.model;

import java.util.HashSet;
import java.util.Set;

public class Organization extends RegistryObject {

    private Set<PostalAddress> addresses = new HashSet<PostalAddress>();
    private Set<EmailAddress> emailAddresses = new HashSet<EmailAddress>();
    private ObjectRef parent;
    private ObjectRef primaryContact;
    private Set<TelephoneNumber> telephoneNumbers = new HashSet<TelephoneNumber>();

    public Set<PostalAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<PostalAddress> addresses) {
        this.addresses = addresses;
    }

    public Set<EmailAddress> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(Set<EmailAddress> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    public ObjectRef getParent() {
        return parent;
    }

    public void setParent(ObjectRef parent) {
        this.parent = parent;
    }

    public ObjectRef getPrimaryContact() {
        return primaryContact;
    }

    public void setPrimaryContact(ObjectRef primaryContact) {
        this.primaryContact = primaryContact;
    }

    public Set<TelephoneNumber> getTelephoneNumbers() {
        return telephoneNumbers;
    }

    public void setTelephoneNumbers(Set<TelephoneNumber> telephoneNumbers) {
        this.telephoneNumbers = telephoneNumbers;
    }
}
