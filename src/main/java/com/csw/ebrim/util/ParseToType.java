package com.csw.ebrim.util;

import com.csw.data.util.QueryDataUtil;
import com.csw.model.*;
import com.ebrim.model.rim.AssociationDocument;
import com.ebrim.model.rim.*;
import com.ebrim.model.wrs.AnyValueType;
import com.ebrim.model.wrs.ValueListDocument;
import com.ebrim.model.wrs.ValueListType;
import net.opengis.gml.*;
import org.apache.xmlbeans.XmlAnySimpleType;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

public class ParseToType {

    /**
     * 根据RegistryPackage的id值来获取RegistryPackageType的类型,
     * 并且返回给CreateGetRecordsResponseDocument函数
     *
     * @param registryPackageId
     *            需要查询的registryPackageId值
     * @return 需要返回的是RegistryPackageType，如果没有，返回null
     */
    public static RegistryPackageType getRegistryPackageTypeById(String registryPackageId, String resultType) {
        RegistryPackage registryPackage = QueryDataUtil.queryRegistryPackageById(registryPackageId);
        if (registryPackage!=null) {
            RegistryPackageType registryPackageType = parseRegistryPackageToRegistryPackageType(registryPackage,resultType);
            return registryPackageType;
        } else {
            System.out.println("This registryPackage is not exist!");
            return null;
        }
    }

    /**
     * 根据RegistryPackage的信息生成RegistryPackageType类型
     *
     * @param rp
     * @return 生成RegistrypackageType类型返回
     */
    public static RegistryPackageType parseRegistryPackageToRegistryPackageType(RegistryPackage rp, String resultType) {
        RegistryPackageDocument registryPackageDocument = RegistryPackageDocument.Factory.newInstance();
        RegistryPackageType registryPackageType = registryPackageDocument.addNewRegistryPackage();
        if (rp.getId()!=null) {
            registryPackageType.setId(rp.getId());
        }
        if (rp.getObjectType()!=null) {
            registryPackageType.setObjectType(parseObjectRef(rp.getObjectType()));
        }
        if (resultType.equals("brief")) {
            if (rp.getStatus()!=null) {
                registryPackageType.setStatus(parseObjectRef(rp.getStatus()));
            }
            if (rp.getLid()!=null) {
                registryPackageType.setId(rp.getId());
            }
            if (rp.getVersionInfo()!=null) {
                registryPackageType.setVersionInfo(parseVersionInfoToVersionInfoType(registryPackageType.getVersionInfo(),rp.getVersionInfo()));
            }
        } else if (resultType.equals("summary")) {
            if (rp.getObjectType()!=null) {
                registryPackageType.setObjectType(parseObjectRef(rp.getObjectType()));
            }
            if (rp.getStatus()!=null) {
                registryPackageType.setStatus(parseObjectRef(rp.getStatus()));
            }
            if (rp.getLid()!=null) {
                registryPackageType.setLid(rp.getLid());
            }
            if (rp.getVersionInfo()!=null) {
                registryPackageType.setVersionInfo(parseVersionInfoToVersionInfoType(registryPackageType.getVersionInfo(),rp.getVersionInfo()));
            }
            if (rp.getSlots()!=null&&rp.getSlots().size()>0) {
                Set<SlotType1> slotType1s = new HashSet<SlotType1>();
                for (Slot s : rp.getSlots()) {
                    slotType1s.add(parseSlotToSlotType(registryPackageType.addNewSlot(),s));
                }
                registryPackageType.setSlotArray(slotType1s.toArray(new SlotType1[rp.getSlots().size()]));
            }
            if (rp.getName()!=null) {
                registryPackageType.setName(parseInternationalStringToInternationalStringType(registryPackageType.getName(),rp.getName()));
            }
            if (rp.getDescription()!=null) {
                registryPackageType.setDescription(parseInternationalStringToInternationalStringType(registryPackageType.getDescription(),rp.getDescription()));
            }
        } else if (resultType.equals("full")) {
            if (rp.getHome()!=null) {
                registryPackageType.setHome(rp.getHome());
            }
            if (rp.getName()!=null) {
                registryPackageType.setName(parseInternationalStringToInternationalStringType(registryPackageType.getName(),rp.getName()));
            }
            if (rp.getObjectType()!=null) {
                registryPackageType.setObjectType(parseObjectRef(rp.getObjectType()));
            }
            if (rp.getVersionInfo()!=null) {
                registryPackageType.setVersionInfo(parseVersionInfoToVersionInfoType(registryPackageType.getVersionInfo(),rp.getVersionInfo()));
            }
            if (rp.getClassifications()!=null&&rp.getClassifications().size()>0) {
                Set<ClassificationType> cts = new HashSet<ClassificationType>();
                for (Classification c : rp.getClassifications()) {
                    cts.add(parseClassificationToClassificationType(registryPackageType.addNewClassification(),c));
                }
                registryPackageType.setClassificationArray(cts.toArray(new ClassificationType[rp.getClassifications().size()]));
            }
            if (rp.getDescription()!=null) {
                registryPackageType.setDescription(parseInternationalStringToInternationalStringType(registryPackageType.getDescription(),rp.getDescription()));
            }
            if (rp.getExternalIdentifiers()!=null&&rp.getExternalIdentifiers().size()>0) {
                Set<ExternalIdentifierType> eits = new HashSet<ExternalIdentifierType>();
                for (ExternalIdentifier ei : rp.getExternalIdentifiers()) {
                    eits.add(parseExternalIdentifierToExternalIdentifierType(registryPackageType.addNewExternalIdentifier(),ei));
                }
                registryPackageType.setExternalIdentifierArray(eits.toArray(new ExternalIdentifierType[rp.getExternalIdentifiers().size()]));
            }
            if (rp.getSlots()!=null&&rp.getSlots().size()>0) {
                Set<SlotType1> slotType1s = new HashSet<SlotType1>();
                for (Slot s : rp.getSlots()) {
                    slotType1s.add(parseSlotToSlotType(registryPackageType.addNewSlot(),s));
                }
                registryPackageType.setSlotArray(slotType1s.toArray(new SlotType1[rp.getSlots().size()]));
            }
            if (rp.getRegistryObjectList()!=null&&rp.getRegistryObjectList().size()>0) {
                RegistryObjectListType rolt = registryPackageType.addNewRegistryObjectList();
                Set<IdentifiableType> its = new HashSet<IdentifiableType>();
                for (Identifiable i : rp.getRegistryObjectList()) {
                    if (i.getHome().equals("com.ebrim.model.wrs.impl.ExtrinsicObjectTypeImpl")) {
                        String extrinsicObjectId = i.getId();
                        ExtrinsicObject extrinsicObject = QueryDataUtil.queryExtrinsicObjectById(extrinsicObjectId);
                        ExtrinsicObjectDocument eoDocument = ExtrinsicObjectDocument.Factory.newInstance();
                        ExtrinsicObjectType eot = parseExtrinsicObjectToExtrinsicObjectType(eoDocument.addNewExtrinsicObject(),extrinsicObject);
                        eoDocument.setExtrinsicObject(eot);
                        IdentifiableType it = eot;
                        its.add(it);
                    }
                    if (i.getHome().equals("com.ebrim.model.rim.impl.ServiceTypeImpl")) {
                        String serviceId = i.getId();
                        Service service = QueryDataUtil.queryServiceById(serviceId);
                        ServiceDocument serviceDocument = ServiceDocument.Factory.newInstance();
                        ServiceType st = parseServiceToServiceType(serviceDocument.addNewService(),service);
                        serviceDocument.setService(st);
                        IdentifiableType it = st;
                        its.add(it);
                    }
                    if (i.getHome().equals("com.ebrim.model.rim.impl.ClassificationNodeTypeImpl")) {
                        String classificationNodeId = i.getId();
                        ClassificationNode classificationNode = QueryDataUtil.queryClassificationNodesById(classificationNodeId);
                        ClassificationNodeDocument cfnDocument = ClassificationNodeDocument.Factory.newInstance();
                        ClassificationNodeType cfnt = parseClassificationNodeToClassificationNodeType(cfnDocument.addNewClassificationNode(),classificationNode);
                        cfnDocument.setClassificationNode(cfnt);
                        IdentifiableType identifiableType = cfnt;
                        its.add(identifiableType);
                    }
                    if (i.getHome().equals("com.ebrim.model.rim.impl.AssociationType1Impl")) {
                        String associationId = i.getId();
                        Association association = QueryDataUtil.queryAssociationById(associationId);
                        AssociationDocument associationDocument = AssociationDocument.Factory.newInstance();
                        AssociationType1 at = parseAssociationToAssociationType(associationDocument.addNewAssociation(),association);
                        associationDocument.setAssociation(at);
                        IdentifiableType identifiableType = at;
                        its.add(identifiableType);
                    }
                    if (i.getHome().equals("com.ebrim.model.rim.impl.OrganizationTypeImpl")) {
                        String organizationId = i.getId();
                        Organization organization = QueryDataUtil.queryOrganizationById(organizationId);
                        OrganizationDocument oDocument = OrganizationDocument.Factory.newInstance();
                        OrganizationType ot = parseOrganizationToOrganizationType(oDocument.addNewOrganization(),organization);
                        oDocument.setOrganization(ot);
                        IdentifiableType identifiableType = ot;
                        its.add(ot);
                    }
                    if (i.getHome().equals("com.ebrim.model.rim.impl.PersonTypeImpl")) {
                        String personId = i.getId();
                        Person person = QueryDataUtil.queryPersonById(personId);
                        PersonDocument pDocument = PersonDocument.Factory.newInstance();
                        PersonType pt = parsePersonToPersonType(pDocument.addNewPerson(),person);
                        pDocument.setPerson(pt);
                        IdentifiableType identifiableType = pt;
                        its.add(identifiableType);
                    }
                }
                rolt.setIdentifiableArray(its.toArray(new IdentifiableType[its.size()]));
                registryPackageType.setRegistryObjectList(rolt);
            }
        }
        return registryPackageType;
    }

    /**
     * 解析person，并存储为personType类型
     *
     * @param p
     */
    public static PersonType parsePersonToPersonType(PersonType personType, Person p) {
        PersonType pt = personType;
        if (p.getId()!=null) {
            pt.setId(p.getId());
        }
        if (p.getLid()!=null) {
            pt.setLid(p.getLid());
        }
        if (p.getHome()!=null) {
            pt.setHome(p.getHome());
        }
        if (p.getDescription()!=null) {
            pt.setDescription(parseInternationalStringToInternationalStringType(pt.addNewDescription(),p.getDescription()));
        }
        if (p.getName()!=null) {
            pt.setName(parseInternationalStringToInternationalStringType(pt.addNewName(),p.getName()));
        }
        if (p.getSlots()!=null&&p.getSlots().size()>0) {
            Set<SlotType1> slotType1s = new HashSet<SlotType1>();
            for (Slot s : p.getSlots()) {
                slotType1s.add(parseSlotToSlotType(pt.addNewSlot(),s));
            }
            pt.setSlotArray(slotType1s.toArray(new SlotType1[p.getSlots().size()]));
        }
        if (p.getObjectType()!=null) {
            pt.setObjectType(parseObjectRef(p.getObjectType()));
        }
        if (p.getVersionInfo()!=null) {
            pt.setVersionInfo(parseVersionInfoToVersionInfoType(pt.addNewVersionInfo(),p.getVersionInfo()));
        }
        if (p.getTelephoneNumbers()!=null&&p.getTelephoneNumbers().size()>0) {
            Set<TelephoneNumberType> tnts = new HashSet<TelephoneNumberType>();
            for (TelephoneNumber tn : p.getTelephoneNumbers()) {
                tnts.add(parseTelephoneNumberToTelephoneNumberType(pt.addNewTelephoneNumber(),tn));
            }
            pt.setTelephoneNumberArray(tnts.toArray(new TelephoneNumberType[p.getTelephoneNumbers().size()]));
        }
        if (p.getStatus()!=null) {
            pt.setStatus(parseObjectRef(p.getStatus()));
        }
        if (p.getPersonName()!=null) {
            pt.setPersonName(parsePersonNameToPersonNameType(pt.addNewPersonName(),p.getPersonName()));
        }
        if (p.getExternalIdentifiers()!=null&&p.getExternalIdentifiers().size()>0) {
            Set<ExternalIdentifierType> eits = new HashSet<ExternalIdentifierType>();
            for (ExternalIdentifier ei : p.getExternalIdentifiers()) {
                eits.add(parseExternalIdentifierToExternalIdentifierType(pt.addNewExternalIdentifier(),ei));
            }
            pt.setExternalIdentifierArray(eits.toArray(new ExternalIdentifierType[p.getExternalIdentifiers().size()]));
        }
        if (p.getEmailAddresses()!=null&&p.getEmailAddresses().size()>0) {
            Set<EmailAddressType> eats = new HashSet<EmailAddressType>();
            for (EmailAddress ea : p.getEmailAddresses()) {
                eats.add(parseEmailAddressToEmailAddressType(pt.addNewEmailAddress(),ea));
            }
            pt.setEmailAddressArray(eats.toArray(new EmailAddressType[p.getEmailAddresses().size()]));
        }
        if (p.getClassifications()!=null&&p.getClassifications().size()>0) {
            Set<ClassificationType> cts = new HashSet<ClassificationType>();
            for (Classification c : p.getClassifications()) {
                cts.add(parseClassificationToClassificationType(pt.addNewClassification(),c));
            }
            pt.setClassificationArray(cts.toArray(new ClassificationType[p.getClassifications().size()]));
        }
        if (p.getAddresses()!=null&&p.getAddresses().size()>0) {
            Set<PostalAddressType> pats = new HashSet<PostalAddressType>();
            for (PostalAddress pa : p.getAddresses()) {
                pats.add(parsePostalAddressToPostalAddressType(pt.addNewAddress(),pa));
            }
            pt.setAddressArray(pats.toArray(new PostalAddressType[p.getAddresses().size()]));
        }
        return pt;
    }

    /**
     * 解析PersonName类型
     *
     * @param pn
     * @return
     */
    public static PersonNameType parsePersonNameToPersonNameType(PersonNameType personNameType, PersonName pn) {
        PersonNameType pnt = personNameType;
        if (pn.getFirstName()!=null) {
            pnt.setFirstName(pn.getFirstName());
        }
        if (pn.getMiddleName()!=null) {
            pnt.setMiddleName(pn.getMiddleName());
        }
        if (pn.getLastName()!=null) {
            pnt.setLastName(pn.getLastName());
        }
        return pnt;
    }

    /**
     * 解析TelPhoneNumber，并生成TelphoneNumbrType类型
     *
     */
    public static TelephoneNumberType parseTelephoneNumberToTelephoneNumberType(TelephoneNumberType telephoneNumberType, TelephoneNumber tn) {
        TelephoneNumberType tnt = telephoneNumberType;
        if (tn.getAreaCode()!=null) {
            tnt.setAreaCode(tn.getAreaCode());
        }
        if (tn.getCountryCode()!=null) {
            tnt.setCountryCode(tn.getCountryCode());
        }
        if (tn.getExtension()!=null) {
            tnt.setExtension(tn.getExtension());
        }
        if (tn.getNumber()!=null) {
            tnt.setNumber(tn.getNumber());
        }
        if (tn.getPhoneType()!=null) {
            tnt.setPhoneType(parseObjectRef(tn.getPhoneType()));
        }
        return tnt;
    }

    /**
     * 解析organization，并存储为organizationType类型
     *
     * @param o
     */
    public static OrganizationType parseOrganizationToOrganizationType(OrganizationType organizationType, Organization o) {
        OrganizationType ot = organizationType;
        if (o.getAddresses()!=null&&o.getAddresses().size()>0) {
            Set<PostalAddressType> pats = new HashSet<PostalAddressType>();
            for (PostalAddress pa : o.getAddresses()) {
                pats.add(parsePostalAddressToPostalAddressType(ot.addNewAddress(),pa));
            }
            ot.setAddressArray(pats.toArray(new PostalAddressType[o.getAddresses().size()]));
        }
        if (o.getClassifications()!=null&&o.getClassifications().size()>0) {
            Set<ClassificationType> cts = new HashSet<ClassificationType>();
            for (Classification c : o.getClassifications()) {
                cts.add(parseClassificationToClassificationType(ot.addNewClassification(),c));
            }
            ot.setClassificationArray(cts.toArray(new ClassificationType[o.getClassifications().size()]));
        }
        if (o.getDescription()!=null) {
            ot.setDescription(parseInternationalStringToInternationalStringType(ot.addNewDescription(),o.getDescription()));
        }
        if (o.getEmailAddresses()!=null&&o.getEmailAddresses().size()>0) {
            Set<EmailAddressType> eats = new HashSet<EmailAddressType>();
            for (EmailAddress ea : o.getEmailAddresses()) {
                eats.add(parseEmailAddressToEmailAddressType(ot.addNewEmailAddress(),ea));
            }
            ot.setEmailAddressArray(eats.toArray(new EmailAddressType[o.getEmailAddresses().size()]));
        }
        if (o.getExternalIdentifiers()!=null&&o.getExternalIdentifiers().size()>0) {
            Set<ExternalIdentifierType> eits = new HashSet<ExternalIdentifierType>();
            for (ExternalIdentifier ei : o.getExternalIdentifiers()) {
                eits.add(parseExternalIdentifierToExternalIdentifierType(ot.addNewExternalIdentifier(),ei));
            }
            ot.setExternalIdentifierArray(eits.toArray(new ExternalIdentifierType[o.getExternalIdentifiers().size()]));
        }
        if (o.getHome()!=null) {
            ot.setHome(o.getHome());
        }
        if (o.getLid()!=null) {
            ot.setLid(o.getLid());
        }
        if (o.getId()!=null) {
            ot.setId(o.getId());
        }
        if (o.getName()!=null) {
            ot.setName(parseInternationalStringToInternationalStringType(ot.addNewName(),o.getName()));
        }
        if (o.getObjectType()!=null) {
            ot.setObjectType(parseObjectRef(o.getObjectType()));
        }
        if (o.getParent()!=null) {
            ot.setParent(parseObjectRef(o.getParent()));
        }
        if (o.getPrimaryContact()!=null) {
            ot.setPrimaryContact(parseObjectRef(o.getPrimaryContact()));
        }
        if (o.getSlots()!=null&&o.getSlots().size()>0) {
            Set<SlotType1> slotType1s = new HashSet<SlotType1>();
            for (Slot s : o.getSlots()) {
                slotType1s.add(parseSlotToSlotType(ot.addNewSlot(),s));
            }
            ot.setSlotArray(slotType1s.toArray(new SlotType1[o.getSlots().size()]));
        }
        if (o.getStatus()!=null) {
            ot.setStatus(parseObjectRef(o.getStatus()));
        }
        if (o.getVersionInfo()!=null) {
            ot.setVersionInfo(parseVersionInfoToVersionInfoType(ot.addNewVersionInfo(),o.getVersionInfo()));
        }
        return ot;
    }

    /**
     * 解析EmailAddress类型
     *
     * @param ea
     * @return
     */
    public static EmailAddressType parseEmailAddressToEmailAddressType(EmailAddressType emailAddressType, EmailAddress ea) {
        EmailAddressType eat = emailAddressType;
        if (ea.getAddress()!=null) {
            eat.setAddress(ea.getAddress().trim());
        }
        if (ea.getType()!=null) {
            eat.setType(parseObjectRef(ea.getType()));
        }
        return eat;
    }

    /**
     * 解析postalAddress属性
     *
     * @param pa
     * @return
     */
    public static PostalAddressType parsePostalAddressToPostalAddressType(PostalAddressType postalAddressType, PostalAddress pa) {
        PostalAddressType pat = postalAddressType;
        if (pa.getCity()!=null) {
            pat.setCity(pa.getCity());
        }
        if (pa.getCountry()!=null) {
            pat.setCountry(pa.getCountry());
        }
        if (pa.getPostalCode()!=null) {
            pat.setPostalCode(pa.getPostalCode());
        }
        if (pa.getStateOrProvince()!=null) {
            pat.setStateOrProvince(pa.getStateOrProvince());
        }
        if (pa.getStreet()!=null) {
            pat.setStreet(pa.getStreet());
        }
        if (pa.getStreetNumber()!=null) {
            pat.setStreetNumber(pa.getStreetNumber());
        }
        return pat;
    }

    /**
     *解析Association值，并存储为AssociationType类型
     *
     * @param association
     */
    public static AssociationType1 parseAssociationToAssociationType(AssociationType1 at, Association association) {
        AssociationType1 associationType = at;
        if (association.getAssociationType()!=null) {
            associationType.setAssociationType(parseObjectRef(association.getAssociationType()));
        }
        if (association.getClassifications()!=null&&association.getClassifications().size()>0) {
            Set<ClassificationType> cts = new HashSet<ClassificationType>();
            for (Classification c : association.getClassifications()) {
                cts.add(parseClassificationToClassificationType(associationType.addNewClassification(),c));
            }
            associationType.setClassificationArray(cts.toArray(new ClassificationType[association.getClassifications().size()]));
        }
        if (association.getDescription()!=null) {
            associationType.setDescription(parseInternationalStringToInternationalStringType(associationType.addNewDescription(),association.getDescription()));
        }
        if (association.getExternalIdentifiers()!=null&&association.getExternalIdentifiers().size()>0) {
            Set<ExternalIdentifierType> eits = new HashSet<ExternalIdentifierType>();
            for (ExternalIdentifier ei : association.getExternalIdentifiers()) {
                eits.add(parseExternalIdentifierToExternalIdentifierType(associationType.addNewExternalIdentifier(),ei));
            }
            associationType.setExternalIdentifierArray(eits.toArray(new ExternalIdentifierType[association.getExternalIdentifiers().size()]));
        }
        if (association.getHome()!=null) {
            associationType.setHome(association.getHome());
        }
        if (association.getId()!=null) {
            associationType.setId(association.getId());
        }
        if (association.getLid()!=null) {
            associationType.setLid(association.getLid());
        }
        if (association.getName()!=null) {
            associationType.setName(parseInternationalStringToInternationalStringType(associationType.addNewName(),association.getName()));
        }
        if (association.getObjectType()!=null) {
            associationType.setObjectType(parseObjectRef(association.getObjectType()));
        }
        if (association.getSlots()!=null&&association.getSlots().size()>0) {
            Set<SlotType1> slotType1s = new HashSet<SlotType1>();
            for (Slot s : association.getSlots()) {
                slotType1s.add(parseSlotToSlotType(associationType.addNewSlot(),s));
            }
            associationType.setSlotArray(slotType1s.toArray(new SlotType1[association.getSlots().size()]));
        }
        if (association.getSourceObject()!=null) {
            associationType.setSourceObject(parseObjectRef(association.getSourceObject()));
        }
        if (association.getTargetObject()!=null) {
            associationType.setTargetObject(parseObjectRef(association.getTargetObject()));
        }
        if (association.getStatus()!=null) {
            associationType.setStatus(parseObjectRef(association.getStatus()));
        }
        if (association.getVersionInfo()!=null) {
            associationType.setVersionInfo(parseVersionInfoToVersionInfoType(associationType.addNewVersionInfo(),association.getVersionInfo()));
        }
        return associationType;
    }

    /**
     * 解析ClassificatinNode值，并存储为ClassificatinNodeType类型
     *
     * @param cfn
     */
    public static ClassificationNodeType parseClassificationNodeToClassificationNodeType(ClassificationNodeType classificationNodeType, ClassificationNode cfn) {
        ClassificationNodeType cfnt = classificationNodeType;
        if (cfn.getClassifications()!=null&&cfn.getClassifications().size()>0) {
            Set<ClassificationType> cfts = new HashSet<ClassificationType>();
            for (Classification c : cfn.getClassifications()) {
                cfts.add(parseClassificationToClassificationType(cfnt.addNewClassification(),c));
            }
            cfnt.setClassificationArray(cfts.toArray(new ClassificationType[cfn.getClassifications().size()]));
        }
        if (cfn.getCode()!=null) {
            cfnt.setCode(cfn.getCode());
        }
        if (cfn.getDescription()!=null) {
            cfnt.setDescription(parseInternationalStringToInternationalStringType(cfnt.addNewDescription(),cfn.getDescription()));
        }
        if (cfn.getExternalIdentifiers()!=null&&cfn.getExternalIdentifiers().size()>0) {
            Set<ExternalIdentifierType> eits = new HashSet<ExternalIdentifierType>();
            for (ExternalIdentifier ei : cfn.getExternalIdentifiers()) {
                eits.add(parseExternalIdentifierToExternalIdentifierType(cfnt.addNewExternalIdentifier(),ei));
            }
            cfnt.setExternalIdentifierArray(eits.toArray(new ExternalIdentifierType[cfn.getExternalIdentifiers().size()]));
        }
        if (cfn.getHome()!=null) {
            cfnt.setHome(cfn.getHome());
        }
        if (cfn.getId()!=null) {
            cfnt.setId(cfn.getId());
        }
        if (cfn.getLid()!=null) {
            cfnt.setLid(cfn.getLid());
        }
        if (cfn.getName()!=null) {
            cfnt.setName(parseInternationalStringToInternationalStringType(cfnt.addNewName(),cfn.getName()));
        }
        if (cfn.getObjectType()!=null) {
            cfnt.setObjectType(parseObjectRef(cfn.getObjectType()));
        }
        if (cfn.getParent()!=null) {
            cfnt.setParent(parseObjectRef(cfn.getParent()));
        }
        if (cfn.getPath()!=null) {
            cfnt.setPath(cfn.getPath());
        }
        if (cfn.getSlots()!=null&&cfn.getSlots().size()>0) {
            Set<SlotType1> slotType1s = new HashSet<SlotType1>();
            for (Slot s : cfn.getSlots()) {
                slotType1s.add(parseSlotToSlotType(cfnt.addNewSlot(),s));
            }
            cfnt.setSlotArray(slotType1s.toArray(new SlotType1[cfn.getSlots().size()]));
        }
        if (cfn.getStatus()!=null) {
            cfnt.setStatus(parseObjectRef(cfn.getStatus()));
        }
        if (cfn.getVersionInfo()!=null) {
            cfnt.setVersionInfo(parseVersionInfoToVersionInfoType(cfnt.addNewVersionInfo(),cfn.getVersionInfo()));
        }
        return cfnt;
    }

    /**
     * 解析 service值，并存储为ServiceType类型
     *
     * @param s
     */
    public static ServiceType parseServiceToServiceType(ServiceType serviceType, Service s) {
        ServiceType st = serviceType;
        if (s.getClassifications()!=null&&s.getClassifications().size()>0) {
            Set<ClassificationType> cts = new HashSet<ClassificationType>();
            for (Classification c : s.getClassifications()) {
                cts.add(parseClassificationToClassificationType(st.addNewClassification(),c));
            }
            st.setClassificationArray(cts.toArray(new ClassificationType[s.getClassifications().size()]));
        }
        if (s.getDescription()!=null) {
            st.setDescription(parseInternationalStringToInternationalStringType(st.addNewDescription(),s.getDescription()));
        }
        if (s.getExternalIdentifiers()!=null&&s.getExternalIdentifiers().size()>0) {
            Set<ExternalIdentifierType> eits = new HashSet<ExternalIdentifierType>();
            for (ExternalIdentifier ei : s.getExternalIdentifiers()) {
                eits.add(parseExternalIdentifierToExternalIdentifierType(st.addNewExternalIdentifier(),ei));
            }
            st.setExternalIdentifierArray(eits.toArray(new ExternalIdentifierType[s.getExternalIdentifiers().size()]));
        }
        if (s.getHome()!=null) {
            st.setHome(s.getHome());
        }
        if (s.getId()!=null) {
            st.setId(s.getId());
        }
        if (s.getLid()!=null) {
            st.setLid(s.getLid());
        }
        if (s.getName()!=null) {
            st.setName(parseInternationalStringToInternationalStringType(st.addNewName(),s.getName()));
        }
        if (s.getObjectType()!=null) {
            st.setObjectType(parseObjectRef(s.getObjectType()));
        }
        if (s.getServiceBindings()!=null&&s.getServiceBindings().size()>0) {
            Set<ServiceBindingType> sbts = new HashSet<ServiceBindingType>();
            for (ServiceBinding sb : s.getServiceBindings()) {
                sbts.add(parseServiceBindingToServiceBindingType(st.addNewServiceBinding(),sb));
            }
            st.setServiceBindingArray(sbts.toArray(new ServiceBindingType[s.getServiceBindings().size()]));
        }
        if (s.getSlots()!=null&&s.getSlots().size()>0) {
            Set<SlotType1> slotType1s = new HashSet<SlotType1>();
            for (Slot slot : s.getSlots()) {
                slotType1s.add(parseSlotToSlotType(st.addNewSlot(),slot));
            }
            st.setSlotArray(slotType1s.toArray(new SlotType1[s.getSlots().size()]));
        }
        if (s.getVersionInfo()!=null) {
            st.setVersionInfo(parseVersionInfoToVersionInfoType(st.addNewVersionInfo(),s.getVersionInfo()));
        }
        if (s.getStatus()!=null) {
            st.setStatus(parseObjectRef(s.getStatus()));
        }
        return st;
    }

    /**
     * 解析ServiceBinding值，并存储为serviceBindingType类型
     *
     * @param sb
     * @return
     */
    public static ServiceBindingType parseServiceBindingToServiceBindingType(ServiceBindingType serviceBindingType, ServiceBinding sb) {
        ServiceBindingType sbt = serviceBindingType;
        if (sb.getAccessURI()!=null) {
            sbt.setAccessURI(sb.getAccessURI());
        }
        if (sb.getClassifications()!=null&&sb.getClassifications().size()>0) {
            Set<ClassificationType> cts = new HashSet<ClassificationType>();
            for (Classification c : sb.getClassifications()) {
                cts.add(parseClassificationToClassificationType(sbt.addNewClassification(),c));
            }
            sbt.setClassificationArray(cts.toArray(new ClassificationType[sb.getClassifications().size()]));
        }
        if (sb.getDescription()!=null) {
            sbt.setDescription(parseInternationalStringToInternationalStringType(sbt.addNewDescription(),sb.getDescription()));
        }
        if (sb.getExternalIdentifiers()!=null&&sb.getExternalIdentifiers().size()>0) {
            Set<ExternalIdentifierType> eits = new HashSet<ExternalIdentifierType>();
            for (ExternalIdentifier ei : sb.getExternalIdentifiers()) {
                eits.add(parseExternalIdentifierToExternalIdentifierType(sbt.addNewExternalIdentifier(),ei));
            }
            sbt.setExternalIdentifierArray(eits.toArray(new ExternalIdentifierType[sb.getExternalIdentifiers().size()]));
        }
        if (sb.getHome()!=null) {
            sbt.setHome(sb.getHome());
        }
        if (sb.getId()!=null) {
            sbt.setId(sb.getId());
        }
        if (sb.getLid()!=null) {
            sbt.setLid(sb.getLid());
        }
        if (sb.getName()!=null) {
            sbt.setName(parseInternationalStringToInternationalStringType(sbt.addNewName(),sb.getName()));
        }
        if (sb.getObjectType()!=null) {
            sbt.setObjectType(parseObjectRef(sb.getObjectType()));
        }
        if (sb.getService()!=null) {
            sbt.setService(parseObjectRef(sb.getService()));
        }
        if (sb.getSlots()!=null&&sb.getSlots().size()>0) {
            Set<SlotType1> slotType1s = new HashSet<SlotType1>();
            for (Slot s : sb.getSlots()) {
                slotType1s.add(parseSlotToSlotType(sbt.addNewSlot(),s));
            }
            sbt.setSlotArray(slotType1s.toArray(new SlotType1[sb.getSlots().size()]));
        }
        if (sb.getSpecificationLinks()!=null&&sb.getSpecificationLinks().size()>0) {
            Set<SpecificationLinkType> slts = new HashSet<SpecificationLinkType>();
            for (SpecificationLink sl : sb.getSpecificationLinks()) {
                slts.add(parseSpecificationLinkToSpecificationLinkType(sbt.addNewSpecificationLink(),sl));
            }
            sbt.setSpecificationLinkArray(slts.toArray(new SpecificationLinkType[sb.getSpecificationLinks().size()]));
        }
        if (sb.getStatus()!=null) {
            sbt.setStatus(parseObjectRef(sb.getStatus()));
        }
        if (sb.getTargetBinding()!=null) {
            sbt.setTargetBinding(parseObjectRef(sb.getTargetBinding()));
        }
        if (sb.getVersionInfo()!=null) {
            sbt.setVersionInfo(parseVersionInfoToVersionInfoType(sbt.addNewVersionInfo(),sb.getVersionInfo()));
        }
        return sbt;
    }

    /**
     *解析SpecificationLink，并且保存为SpecificationLinkType类型
     * @param sl
     * @return
     */
    public static SpecificationLinkType parseSpecificationLinkToSpecificationLinkType(SpecificationLinkType specificationLinkType, SpecificationLink sl) {
        SpecificationLinkType slt = specificationLinkType;
        if (sl.getClassifications()!=null&&sl.getClassifications().size()>0) {
            Set<ClassificationType> cts = new HashSet<ClassificationType>();
            for (Classification c : sl.getClassifications()) {
                cts.add(parseClassificationToClassificationType(slt.addNewClassification(),c));
            }
            slt.setClassificationArray(cts.toArray(new ClassificationType[sl.getClassifications().size()]));
        }
        if (sl.getDescription()!=null) {
            slt.setDescription(parseInternationalStringToInternationalStringType(slt.addNewDescription(),sl.getDescription()));
        }
        if (sl.getExternalIdentifiers()!=null&&sl.getExternalIdentifiers().size()>0) {
            Set<ExternalIdentifierType> eits = new HashSet<ExternalIdentifierType>();
            for (ExternalIdentifier ei : sl.getExternalIdentifiers()) {
                eits.add(parseExternalIdentifierToExternalIdentifierType(slt.addNewExternalIdentifier(),ei));
            }
            slt.setExternalIdentifierArray(eits.toArray(new ExternalIdentifierType[sl.getExternalIdentifiers().size()]));
        }
        if (sl.getHome()!=null) {
            slt.setHome(sl.getHome());
        }
        if (sl.getId()!=null) {
            slt.setId(sl.getId());
        }
        if (sl.getLid()!=null) {
            slt.setLid(sl.getLid());
        }
        if (sl.getName()!=null) {
            slt.setName(parseInternationalStringToInternationalStringType(slt.addNewName(),sl.getName()));
        }
        if (sl.getObjectType()!=null) {
            slt.setObjectType(parseObjectRef(sl.getObjectType()));
        }
        if (sl.getServiceBinding()!=null) {
            slt.setServiceBinding(parseObjectRef(sl.getServiceBinding()));
        }
        if (sl.getSlots()!=null&&sl.getSlots().size()>0) {
            Set<SlotType1> slotType1s = new HashSet<SlotType1>();
            for (Slot s : sl.getSlots()) {
                slotType1s.add(parseSlotToSlotType(slt.addNewSlot(),s));
            }
            slt.setSlotArray(slotType1s.toArray(new SlotType1[sl.getSlots().size()]));
        }
        if (sl.getSpecificationObject()!=null) {
            slt.setSpecificationObject(parseObjectRef(sl.getSpecificationObject()));
        }
        if (sl.getStatus()!=null) {
            slt.setStatus(parseObjectRef(sl.getStatus()));
        }
        if (sl.getUsageDescription()!=null) {
            slt.setUsageDescription(parseInternationalStringToInternationalStringType(slt.addNewUsageDescription(),sl.getUsageDescription()));
        }
        if (sl.getUsageParameters()!=null) {
            String[] values = sl.getUsageParameters().split(",");
            slt.setUsageParameterArray(values);
        }
        if (sl.getVersionInfo()!=null) {
            slt.setVersionInfo(parseVersionInfoToVersionInfoType(slt.addNewVersionInfo(),sl.getVersionInfo()));
        }
        return slt;
    }

    /**
     * 解析ExtrinsicObject，并且保存为ExrinsicObjectType类型
     */
    public static ExtrinsicObjectType parseExtrinsicObjectToExtrinsicObjectType(ExtrinsicObjectType extrinsicObjectType, ExtrinsicObject eo) {
        ExtrinsicObjectType eot = extrinsicObjectType;
        if (eo.isOpaque()>0) {
            eot.setIsOpaque(true);
        } else {
            eot.setIsOpaque(false);
        }
        if (eo.getClassifications()!=null&&eo.getClassifications().size()>0) {
            Set<ClassificationType> cts = new HashSet<ClassificationType>();
            for (Classification c : eo.getClassifications()) {
                cts.add(parseClassificationToClassificationType(eot.addNewClassification(),c));
            }
            eot.setClassificationArray(cts.toArray(new ClassificationType[eo.getClassifications().size()]));
        }
        if (eo.getVersionInfo()!=null) {
            eot.setVersionInfo(parseVersionInfoToVersionInfoType(eot.addNewVersionInfo(),eo.getVersionInfo()));
        }
        if (eo.getContentVersionInfo()!=null) {
            eot.setContentVersionInfo(parseVersionInfoToVersionInfoType(eot.addNewContentVersionInfo(),eo.getContentVersionInfo()));
        }
        if (eo.getDescription()!=null) {
            eot.setDescription(parseInternationalStringToInternationalStringType(eot.addNewDescription(),eo.getDescription()));
        }
        if (eo.getExternalIdentifiers()!=null&&eo.getExternalIdentifiers().size()>0) {
            Set<ExternalIdentifierType> eits = new HashSet<ExternalIdentifierType>();
            for (ExternalIdentifier ei : eo.getExternalIdentifiers()) {
                eits.add(parseExternalIdentifierToExternalIdentifierType(eot.addNewExternalIdentifier(),ei));
            }
            eot.setExternalIdentifierArray(eits.toArray(new ExternalIdentifierType[eo.getExternalIdentifiers().size()]));
        }
        if (eo.getHome()!=null) {
            eot.setHome(eo.getHome());
        }
        if (eo.getId()!=null) {
            eot.setId(eo.getId());
        }
        if (eo.getLid()!=null) {
            eot.setLid(eo.getLid());
        }
        if (eo.getName()!=null) {
            eot.setName(parseInternationalStringToInternationalStringType(eot.addNewName(),eo.getName()));
        }
        if (eo.getMimeType()!=null) {
            eot.setMimeType(eo.getMimeType());
        }
        if (eo.getObjectType()!=null) {
            eot.setObjectType(parseObjectRef(eo.getObjectType()));
        }
        if (eo.getSlots()!=null&&eo.getSlots().size()>0) {
            Set<SlotType1> slotType1s = new HashSet<SlotType1>();
            for (Slot s : eo.getSlots()) {
                slotType1s.add(parseSlotToSlotType(eot.addNewSlot(),s));
            }
            eot.setSlotArray(slotType1s.toArray(new SlotType1[eo.getSlots().size()]));
        }
        if (eo.getStatus()!=null) {
            eot.setStatus(parseObjectRef(eo.getStatus()));
        }
        return eot;
    }

    /**
     * 将Classification中的数据填充到ClassificationType中
     *
     * @param c
     */
    public static ClassificationType parseClassificationToClassificationType(ClassificationType ct, Classification c) {
        ClassificationType classificationType = ct;
        if (c.getObjectType()!=null) {
            classificationType.setObjectType(parseObjectRef(c.getObjectType()));
        }
        if (c.getClassifications()!=null&&c.getClassifications().size()>0) {
            Set<ClassificationType> cts = new HashSet<ClassificationType>();
            for (Classification classification : c.getClassifications()) {
                cts.add(parseClassificationToClassificationType(classificationType.addNewClassification(),classification));
            }
            classificationType.setClassificationArray(cts.toArray(new ClassificationType[c.getClassifications().size()]));
        }
        if (c.getClassificationNode()!=null) {
            classificationType.setClassificationNode(parseObjectRef(c.getClassificationNode()));
        }
        if (c.getDescription()!=null) {
            classificationType.setDescription(parseInternationalStringToInternationalStringType(classificationType.addNewDescription(),c.getDescription()));
        }
        if (c.getExternalIdentifiers()!=null&&c.getExternalIdentifiers().size()>0) {
            Set<ExternalIdentifierType> eist = new HashSet<ExternalIdentifierType>();
            for (ExternalIdentifier ei : c.getExternalIdentifiers()) {
                eist.add(parseExternalIdentifierToExternalIdentifierType(classificationType.addNewExternalIdentifier(),ei));
            }
            classificationType.setExternalIdentifierArray(eist.toArray(new ExternalIdentifierType[c.getExternalIdentifiers().size()]));
        }
        if (c.getHome()!=null) {
            classificationType.setHome(c.getHome());
        }
        if (c.getId()!=null) {
            classificationType.setId(c.getId());
        }
        if (c.getLid()!=null) {
            classificationType.setLid(c.getLid());
        }
        if (c.getName()!=null) {
            classificationType.setName(parseInternationalStringToInternationalStringType(classificationType.addNewName(),c.getName()));
        }
        if (c.getNodeRepresentation()!=null) {
            classificationType.setNodeRepresentation(c.getNodeRepresentation());
        }
        if (c.getObjectType()!=null) {
            classificationType.setObjectType(parseObjectRef(c.getClassificationNode()));
        }
        if (c.getSlots()!=null&&c.getSlots().size()>0) {
            Set<SlotType1> slotType1s = new HashSet<SlotType1>();
            for (Slot s : c.getSlots()) {
                slotType1s.add(parseSlotToSlotType(classificationType.addNewSlot(),s));
            }
            classificationType.setSlotArray(slotType1s.toArray(new SlotType1[c.getSlots().size()]));
        }
        if (c.getStatus()!=null) {
            classificationType.setStatus(parseObjectRef(c.getStatus()));
        }
        if (c.getVersionInfo()!=null) {
            classificationType.setVersionInfo(parseVersionInfoToVersionInfoType(classificationType.addNewVersionInfo(),c.getVersionInfo()));
        }
        return classificationType;
    }

    /**
     * 解析ExternalIdentifier，存储为ExternalIdentifierType类型
     *
     * @param ei
     */
    public static ExternalIdentifierType parseExternalIdentifierToExternalIdentifierType(ExternalIdentifierType externalIdentifierType, ExternalIdentifier ei) {
        ExternalIdentifierType eit = externalIdentifierType;
        if (ei.getClassifications()!=null&&ei.getClassifications().size()>0) {
            Set<ClassificationType> cts = new HashSet<ClassificationType>();
            for (Classification c : ei.getClassifications()) {
                cts.add(parseClassificationToClassificationType(eit.addNewClassification(),c));
            }
            eit.setClassificationArray(cts.toArray(new ClassificationType[ei.getClassifications().size()]));
        }
        if (ei.getDescription()!=null) {
            eit.setDescription(parseInternationalStringToInternationalStringType(eit.addNewDescription(),ei.getDescription()));
        }
        if (ei.getExternalIdentifiers()!=null&&ei.getExternalIdentifiers().size()>0) {
            Set<ExternalIdentifierType> eits = new HashSet<ExternalIdentifierType>();
            for (ExternalIdentifier externalIdentifier : ei.getExternalIdentifiers()) {
                eits.add(parseExternalIdentifierToExternalIdentifierType(eit.addNewExternalIdentifier(),externalIdentifier));
            }
            eit.setExternalIdentifierArray(eits.toArray(new ExternalIdentifierType[ei.getExternalIdentifiers().size()]));
        }
        if (ei.getHome()!=null) {
            eit.setHome(ei.getHome());
        }
        if (ei.getId()!=null) {
            eit.setId(ei.getId());
        }
        if (ei.getName()!=null) {
            eit.setName(parseInternationalStringToInternationalStringType(eit.addNewName(),ei.getName()));
        }
        if (ei.getObjectType()!=null) {
            eit.setObjectType(parseObjectRef(ei.getObjectType()));
        }
        if (ei.getRegistryObject()!=null) {
            eit.setObjectType(parseObjectRef(ei.getRegistryObject()));
        }
        if (ei.getSlots()!=null&&ei.getSlots().size()>0) {
            Set<SlotType1> slotType1s = new HashSet<SlotType1>();
            for (Slot s : ei.getSlots()) {
                slotType1s.add(parseSlotToSlotType(eit.addNewSlot(),s));
            }
            eit.setSlotArray(slotType1s.toArray(new SlotType1[ei.getSlots().size()]));
        }
        if (ei.getStatus()!=null) {
            eit.setStatus(parseObjectRef(ei.getStatus()));
        }
        if (ei.getValue()!=null) {
            eit.setValue(ei.getValue());
        }
        if (ei.getVersionInfo()!=null) {
            eit.setVersionInfo(parseVersionInfoToVersionInfoType(eit.addNewVersionInfo(),ei.getVersionInfo()));
        }
        return eit;
    }

    /**
     * 返回一个internationalString的InternationalStringType类型的函数
     *
     * @param ins
     * @return
     */
    public static InternationalStringType parseInternationalStringToInternationalStringType(InternationalStringType internationalStringType, InternationalString ins) {
        InternationalStringType inst = internationalStringType;
        if (ins.getLocalizedStrings()!=null&&ins.getLocalizedStrings().size()>0) {
            Set<LocalizedStringType> lsts = new HashSet<LocalizedStringType>();
            for (LocalizedString ls : ins.getLocalizedStrings()) {
                lsts.add(parseLocalizedStringToLocalizedStringType(inst.addNewLocalizedString(),ls));
            }
            inst.setLocalizedStringArray(lsts.toArray(new LocalizedStringType[ins.getLocalizedStrings().size()]));
        }
        return inst;
    }

    /**
     * 解析LocalizedString，存储为LocalizedStringType类型
     *
     * @param ls
     * */
    public static LocalizedStringType parseLocalizedStringToLocalizedStringType(LocalizedStringType localizedStringType, LocalizedString ls) {
        LocalizedStringType lst = localizedStringType;
        if (ls.getLang()!=null) {
            lst.setValue(ls.getLang());
        }
        if (ls.getCharSet()!=null) {
            XmlAnySimpleType sat = XmlAnySimpleType.Factory.newInstance();
            sat.setStringValue(ls.getCharSet());
            lst.setCharset(sat);
        }
        if (ls.getValue()!=null) {
            lst.setValue(ls.getValue());
        }
        return lst;
    }

    /**
     * 解析Slot，存储为SlotType1类型
     *
     * @param s
     */
    public static SlotType1 parseSlotToSlotType(SlotType1 st, Slot s) {
        SlotType1 slotType1 = st;
        if (s.getName()!=null) {
            slotType1.setName(s.getName());
        }
        if (s.getSlotType()!=null) {
            slotType1.setSlotType(s.getSlotType());
        }
        if (s.getValues()!=null&&s.getSlotType()!=null) {
            if (s.getSlotType().equals("urn:ogc:def:dataType:ISO-19107:2003:GM_Envelope")) {
                String initialStr = s.getValues();
                EnvelopeDocument envelopeDocument = EnvelopeDocument.Factory.newInstance();
                EnvelopeType envelopeType = envelopeDocument.addNewEnvelope();
                DirectPositionType dptLower = envelopeType.addNewLowerCorner();
                DirectPositionType dptUpper = envelopeType.addNewUpperCorner();
                try {
                    Document document = DocumentHelper.parseText(initialStr);
                    Element root = document.getRootElement();
                    for (Iterator i = root.elementIterator();i.hasNext();) {
                        Element envelope = (Element)i.next();
                        String srsName = envelope.attributeValue("srsName");
                        envelopeType.setSrsName(srsName);
                        String[] corners = new String[2];
                        int num = 0;
                        for (Iterator j = envelope.elementIterator();j.hasNext();) {
                            Element element = (Element)j.next();
                            String corner = element.getText();
                            corners[num] = corner;
                            num++;
                        }
                        dptLower.setStringValue(corners[0]);
                        dptUpper.setStringValue(corners[1]);
                        envelopeType.setLowerCorner(dptLower);
                        envelopeType.setUpperCorner(dptUpper);
                        envelopeDocument.setEnvelope(envelopeType);
                        slotType1.addNewValueList();
                        ValueListDocument valueListDocument = ValueListDocument.Factory.newInstance();
                        ValueListType valueListType = valueListDocument.addNewValueList2();
                        slotType1.setValueList(valueListType);
                        AnyValueType anyValueType = valueListType.addNewAnyValue();
                        anyValueType.set(envelopeType);
                        slotType1.setValueList(valueListType);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (s.getSlotType().equals("urn:oasis:names:tc:ebxml-regrep:DataType:String")) {
                com.ebrim.model.rim.ValueListType vlt = slotType1.addNewValueList();
                String initialStr = s.getValues();
                String value = "";
                try {
                    Document document = DocumentHelper.parseText(initialStr);
                    Element root = (Element)document.getRootElement();
                    if (root.elementIterator().hasNext()) {
                        for (Iterator i = root.elementIterator();i.hasNext();) {
                            Element element = (Element)i.next();
                            value = element.getText();
                            vlt.addNewValue().setStringValue(value);
                        }
                    } else {
                        value = root.getText();
                        vlt.addNewValue().setStringValue(value);
                    }
                    slotType1.setValueList(vlt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (s.getSlotType().equals("urn:oasis:names:tc:ebxml-regrep:DataType:DateTime")) {
                com.ebrim.model.rim.ValueListType vlt = slotType1.addNewValueList();
                String initialStr = s.getValues();
                String value = "";
                try {
                    Document document = DocumentHelper.parseText(initialStr);
                    Element root = document.getRootElement();
                    value = root.getText();
                    vlt.addNewValue().setStringValue(value);
                    slotType1.setValueList(vlt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (s.getSlotType().equals("urn:ogc:def:dataType:ISO-19107:2003:GM_Point")) {
                String initialStr = s.getValues();
                PointDocument pointDocument = PointDocument.Factory.newInstance();
                PointType pointType = pointDocument.addNewPoint();
                DirectPositionType dpt = pointType.addNewPos();
                try {
                    Document document = DocumentHelper.parseText(initialStr);
                    Element root = document.getRootElement();
                    for (Iterator i = root.elementIterator();i.hasNext();) {
                        Element point = (Element) i.next();
                        String pointId = point.attributeValue("id");
                        String srsName = point.attributeValue("srsName");
                        pointType.setId(pointId);
                        pointType.setSrsName(srsName);
                        for (Iterator j = point.elementIterator();j.hasNext();) {
                            Element pos = (Element)j.next();
                            String value = pos.getText();
                            dpt.setStringValue(value);
                        }
                        pointType.setPos(dpt);
                        pointDocument.setPoint(pointType);
                        slotType1.addNewValueList();
                        ValueListDocument valueListDocument = ValueListDocument.Factory.newInstance();
                        ValueListType valueListType = valueListDocument.addNewValueList2();
                        AnyValueType anyValueType = valueListType.addNewAnyValue();
                        anyValueType.set(pointType);
                        valueListType.set(anyValueType);
                        slotType1.setValueList(valueListType);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                com.ebrim.model.rim.ValueListType vlt = slotType1.addNewValueList();
                String initialStr = s.getValues();
                String[] slotStrs = initialStr.substring(0,initialStr.length()-1).split(",");
                for (String str : slotStrs) {
                    vlt.addNewValue().setStringValue(str.trim());
                }
                slotType1.setValueList(vlt);
            }
        }
        return slotType1;
    }

    /**
     * 解析versionInfo，并生成VersionInfoType类型
     *
     * @param vi
     * @return
     */
    public static VersionInfoType parseVersionInfoToVersionInfoType(VersionInfoType vit, VersionInfo vi) {
        VersionInfoType versionInfoType = vit;
        if (vi.getComment()!=null) {
            versionInfoType.setComment(vi.getComment());
        }
        if (vi.getVersionName()!=null) {
            versionInfoType.setVersionName(vi.getVersionName());
        }
        return versionInfoType;
    }

    /**
     * 读取出object中的有用信息
     *
     * @return
     */
    public static String parseObjectRef(ObjectRef o) {
        return o.getHome();
    }
}
