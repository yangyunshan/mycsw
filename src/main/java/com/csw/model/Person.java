package com.csw.model;

import java.util.HashSet;
import java.util.Set;

public class Person extends RegistryObject {

    private Set<PostalAddress> addresses = new HashSet<PostalAddress>();
    private Set<EmailAddress> emailAddresses = new HashSet<EmailAddress>();
    private PersonName personName;
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

    public PersonName getPersonName() {
        return personName;
    }

    public void setPersonName(PersonName personName) {
        this.personName = personName;
    }

    public Set<TelephoneNumber> getTelephoneNumbers() {
        return telephoneNumbers;
    }

    public void setTelephoneNumbers(Set<TelephoneNumber> telephoneNumbers) {
        this.telephoneNumbers = telephoneNumbers;
    }
}
