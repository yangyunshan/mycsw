package com.csw.data.util;

import com.csw.privileage.util.*;
import com.csw.model.*;
import com.csw.dao.*;
import com.csw.privileage.util.Action;
import com.csw.spring.util.SpringContextUtil;
import com.csw.util.GetSystemInfoUtil;
import com.ebrim.model.rim.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.sql.DataSourceDefinition;
import java.sql.Timestamp;
import java.util.Set;

public class InsertDataUtil {

    private static TransactionStatus transactionStatus;
    private static DataSourceTransactionManager transactionManager;

    /**
     * 该方法为总的方法，用于解析文档，获取根元素RegistryPackage，并且解析RegistryPackage
     *
     * @param content
     *            XML文档内容
     * @param owner
     *            content的拥有者
     * @return 解析后的所获得RegistryPackage
     */
    public static boolean parseAndSaveXMLDocumentByContent(String content, String type, String owner) {
        boolean result = false;
        String ebrimDocument = content.trim();
        try {
            RegistryPackageDocument registryPackageDocument = RegistryPackageDocument.Factory.parse(ebrimDocument);
            /*******获取根元素RegistryPackage对象*******/
            RegistryPackageType registryPackageType = registryPackageDocument.getRegistryPackage();
            if (!QueryDataUtil.checkRegistryPackageIsExist(registryPackageType.getId())) {
                parseRegistryPackageTypeAndSave(registryPackageType,type,owner);
                result = true;
            } else {
                System.out.println("RegistryPackage has been existed!");
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    /**
     * 解析RegistryPackage对象
     *
     * @param rpt
     * @param owner
     * @return
     */
    private static Object savePoint = null;

    public static RegistryPackage parseRegistryPackageTypeAndSave(RegistryPackageType rpt, String type, String owner) {
        RegistryPackage registryPackage = SpringContextUtil.getBean(RegistryPackage.class);

        transactionManager = SpringContextUtil.getBean("transactionManager");
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);
        savePoint = transactionStatus.createSavepoint();
        /*********解析RegistryPackage的id属性*********/
        if (rpt.getId()!=null) {
            registryPackage.setId(rpt.getId());
        }
        /***********解析RegistryPackage的home属性***********/
        if (rpt.getHome()!=null) {
            registryPackage.setHome(rpt.getHome());
        }
        /***********解析RegistryPackage的lid属性***********/
        if (rpt.getLid()!=null) {
            registryPackage.setLid(rpt.getLid());
        }
        /***********解析RegistryPackage的类型***********/
        if (type!=null) {
            registryPackage.setType(type);
        }
        /***********解析RegistryPackage的拥有者***********/
        if (owner!=null) {
            registryPackage.setOwner(owner);
        }
        /***********解析RegistryPackage的name属性***********/
        if (rpt.getName()!=null) {
            registryPackage.setName(parseInternationalStringTypeAndSave(rpt.getName(),rpt.getId()+":Name"));
        }
        /***********解析RegistryPackage的description属性***********/
        if (rpt.getDescription()!=null) {
            registryPackage.setDescription(parseInternationalStringTypeAndSave(rpt.getDescription(),rpt.getId()+":Description"));
        }
        /***********解析RegistryPackage的objectType属性***********/
        if (rpt.getObjectType()!=null) {
            registryPackage.setObjectType(parseObjectRefTypeAndSave(rpt.getId()+":ObjectType",rpt.getObjectType(),null,null,rpt.getId()));
        }
        /***********解析RegistryPackage的ExternalIdentifier属性***********/
        if (rpt.getExternalIdentifierArray()!=null&&rpt.getExternalIdentifierArray().length>0) {
            for (ExternalIdentifierType eit : rpt.getExternalIdentifierArray()) {
                registryPackage.getExternalIdentifiers().add(parseExternalIdentifierTypeAndSave(eit,eit.getId()));
            }
        }
        /***********解析RegistryPackage的VersionInfo属性***********/
        if (rpt.getVersionInfo()!=null) {
            registryPackage.setVersionInfo(parseVersionInfoTypeAndSave(rpt.getVersionInfo(),rpt.getId()));
        }
        /***********解析RegistryPackage的Classification属性***********/
        if (rpt.getClassificationArray()!=null&&rpt.getClassificationArray().length>0) {
            for (ClassificationType ct : rpt.getClassificationArray()) {
                registryPackage.getClassifications().add(parseClassificationTypeAndSave(ct,rpt.getId()));
            }
        }
        /***********解析RegistryPackage的Status属性***********/
        if (rpt.getStatus()!=null) {
            registryPackage.setStatus(parseObjectRefTypeAndSave(rpt.getId()+":Status",rpt.getStatus(),null,null,rpt.getId()));
        }
        /***********解析RegistryPackage的Slots属性***********/
        if (rpt.getSlotArray()!=null&&rpt.getSlotArray().length>0) {
            int i=1;
            for (SlotType1 slotType1 : rpt.getSlotArray()) {
                registryPackage.getSlots().add(parseSlotTypeAndSave(slotType1,rpt.getId()+":RegistryPackage:Slot"+i,rpt.getId()));
                i++;
            }
        }
        /***********解析RegistryPackage的Identifiable属性.五种情况***********/
        if (rpt.getRegistryObjectList()!=null&&rpt.getRegistryObjectList().getIdentifiableArray()!=null&&rpt.getRegistryObjectList().getIdentifiableArray().length>0) {
            for (IdentifiableType ift : rpt.getRegistryObjectList().getIdentifiableArray()) {
                /***********解析ExtrinsicObjectTypeImpl类型***********/
                if (ift.getClass().getName().equals("com.ebrim.model.wrs.impl.ExtrinsicObjectTypeImpl")) {
                    ExtrinsicObjectType eot = (ExtrinsicObjectType)ift;
                    Identifiable identifiable = new Identifiable();
                    identifiable.setHome("com.ebrim.model.wrs.impl.ExtrinsicObjectTypeImpl");
                    identifiable.setId(parseExtrinsicObjectAndSave(eot).getId());
                    InsertDataUtil.insertIdentifiable(identifiable,rpt.getId());
                    registryPackage.getRegistryObjectList().add(identifiable);
                }
                /***********解析ClassificationNodeTypeImpl类型***********/
                if (ift.getClass().getName().equals("com.ebrim.model.rim.impl.ClassificationNodeTypeImpl")) {
                    ClassificationNodeType cfnt = (ClassificationNodeType)ift;
                    Identifiable identifiable = new Identifiable();
                    identifiable.setId(parseClassificationNodeTypeAndSave(cfnt).getId());
                    identifiable.setHome("com.ebrim.model.rim.impl.ClassificationNodeTypeImpl");
                    InsertDataUtil.insertIdentifiable(identifiable,rpt.getId());
                    registryPackage.getRegistryObjectList().add(identifiable);
                }
                /***********解析AssociationTypeImpl类型***********/
                if (ift.getClass().getName().equals("com.ebrim.model.rim.impl.AssociationType1Impl")) {
                    AssociationType1 associationType = (AssociationType1)ift;
                    Identifiable identifiable = new Identifiable();
                    identifiable.setId(parseAssociationTypeAndSave(associationType).getId());
                    identifiable.setHome("com.ebrim.model.rim.impl.AssociationType1Impl");
                    InsertDataUtil.insertIdentifiable(identifiable,rpt.getId());
                    registryPackage.getRegistryObjectList().add(identifiable);
                }
                /***********解析ServiceTypeImpl类型***********/
                if (ift.getClass().getName().equals("com.ebrim.model.rim.impl.ServiceTypeImpl")) {
                    ServiceType serviceType = (ServiceType)ift;
                    Identifiable identifiable = new Identifiable();
                    identifiable.setId(parseServiceTypeAndSave(serviceType).getId());
                    identifiable.setHome("com.ebrim.model.rim.impl.ServiceTypeImpl");
                    InsertDataUtil.insertIdentifiable(identifiable,rpt.getId());
                    registryPackage.getRegistryObjectList().add(identifiable);
                }
                /***********解析OrganizationTypeImpl类型***********/
                if (ift.getClass().getName().equals("com.ebrim.model.rim.impl.OrganizationTypeImpl")) {
                    OrganizationType organizationType = (OrganizationType)ift;
                    Identifiable identifiable = new Identifiable();
                    identifiable.setId(parseOrganizationTypeAndSave(organizationType).getId());
                    identifiable.setHome("com.ebrim.model.rim.impl.OrganizationTypeImpl");
                    InsertDataUtil.insertIdentifiable(identifiable,rpt.getId());
                    registryPackage.getRegistryObjectList().add(identifiable);
                }
                /***********解析PersonTypeImpl类型***********/
                if (ift.getClass().getName().equals("com.ebrim.model.rim.impl.PersonTypeImpl")) {
                    PersonType personType = (PersonType)ift;
                    Identifiable identifiable = new Identifiable();
                    identifiable.setId(parsePersonTypeAndSave(personType).getId());
                    identifiable.setHome("com.ebrim.model.rim.impl.PersonTypeImpl");
                    InsertDataUtil.insertIdentifiable(identifiable,rpt.getId());
                    registryPackage.getRegistryObjectList().add(identifiable);
                }
                /***********解析FederationTypeImpl类型***********/
                if (ift.getClass().getName().equals("com.ebrim.model.rim.impl.FederationTypeImpl")) {
                    FederationType federationType = (FederationType)ift;
                    Identifiable identifiable = new Identifiable();
                    identifiable.setId(parseFederationTypeAndSave(federationType).getId());
                    identifiable.setHome("com.ebrim.model.rim.impl.FederationTypeImpl");
                    InsertDataUtil.insertIdentifiable(identifiable,rpt.getId());
                    registryPackage.getRegistryObjectList().add(identifiable);
                }
                /***********解析UserTypeImpl类型***********/
                if (ift.getClass().getName().equals("com.ebrim.model.rim.impl.UserTypeImpl")) {
                    UserType userType = (UserType)ift;
                    Identifiable identifiable = new Identifiable();
                    identifiable.setId(parseUserTypeAndSave(userType).getId());
                    identifiable.setHome("com.ebrim.model.rim.impl.UserTypeImpl");
                    InsertDataUtil.insertIdentifiable(identifiable,rpt.getId());
                    registryPackage.getRegistryObjectList().add(identifiable);
                }
                /***********解析NotificationTypeImpl类型***********/
                if (ift.getClass().getName().equals("com.ebrim.model.rim.impl.NotificationTypeImpl")) {
                    NotificationType notificationType = (NotificationType)ift;
                    Identifiable identifiable = new Identifiable();
                    identifiable.setId(parseNotificationTypeAndSave(notificationType,rpt.getId()).getId());
                    identifiable.setHome("com.ebrim.model.rim.impl.NotificationTypeImpl");
                    InsertDataUtil.insertIdentifiable(identifiable,rpt.getId());
                    registryPackage.getRegistryObjectList().add(identifiable);
                }
                /***********解析ObjectRefTypeImpl类型***********/
                if (ift.getClass().getName().equals("com.ebrim.model.rim.impl.ObjectRefTypeImpl")) {
                    ObjectRefType objectRefType = (ObjectRefType)ift;
                    Identifiable identifiable = new Identifiable();
                    identifiable.setId(parseObjectrefTypeAndSave(objectRefType,rpt.getId()).getId());
                    identifiable.setHome("com.ebrim.model.rim.impl.ObjectRefTypeImpl");
                    InsertDataUtil.insertIdentifiable(identifiable,rpt.getId());
                    registryPackage.getRegistryObjectList().add(identifiable);
                }
                /***********解析RegistryTypeImpl类型***********/
                if (ift.getClass().getName().equals("com.ebrim.model.rim.impl.RegistryTypeImpl")) {
                    RegistryType registryType = (RegistryType)ift;
                    Identifiable identifiable = new Identifiable();
                    identifiable.setId(parseRegistryTypeAndSave(registryType).getId());
                    identifiable.setHome("com.ebrim.model.rim.impl.RegistryTypeImpl");
                    InsertDataUtil.insertIdentifiable(identifiable,rpt.getId());
                    registryPackage.getRegistryObjectList().add(identifiable);
                }
            }
        }
        insertRegistryPackageInfo(registryPackage);
        transactionManager.commit(transactionStatus);
        return registryPackage;
    }

    /**
     * 解析registry对象
     *
     * @param rt
     * @return
     */
    public static Registry parseRegistryTypeAndSave(RegistryType rt) {
        Registry r = SpringContextUtil.getBean(Registry.class);
        if (rt!=null) {
            if (rt.getClassificationArray()!=null&&rt.getClassificationArray().length>0) {
                for (ClassificationType ct : rt.getClassificationArray()) {
                    r.getClassifications().add(parseClassificationTypeAndSave(ct,rt.getId()));
                }
            }
            if (rt.getDescription()!=null) {
                r.setDescription(parseInternationalStringTypeAndSave(rt.getDescription(),rt.getId()+":Description"));
            }
            if (rt.getExternalIdentifierArray()!=null&&rt.getExternalIdentifierArray().length>0) {
                for (ExternalIdentifierType eit : rt.getExternalIdentifierArray()) {
                    r.getExternalIdentifiers().add(parseExternalIdentifierTypeAndSave(eit,eit.getId()));
                }
            }
            if (rt.getHome()!=null) {
                r.setHome(rt.getHome());
            }
            if (rt.getId()!=null) {
                r.setId(rt.getId());
            }
            if (rt.getLid()!=null) {
                r.setLid(rt.getLid());
            }
            if (rt.getName()!=null) {
                r.setName(parseInternationalStringTypeAndSave(rt.getName(),rt.getId()+":Name"));
            }
            if (rt.getObjectType()!=null) {
                r.setObjectType(parseObjectRefTypeAndSave(rt.getId()+":ObjectType",rt.getObjectType(),null,null,rt.getId()));
            }
            if (rt.getSlotArray()!=null&&rt.getSlotArray().length>0) {
                int i = 1;
                for (SlotType1 slotType1 : rt.getSlotArray()) {
                    r.getSlots().add(parseSlotTypeAndSave(slotType1,rt.getId()+":Registry:Slot"+i,rt.getId()));
                    i++;
                }
            }
            if (rt.getStatus()!=null) {
                r.setStatus(parseObjectRefTypeAndSave(rt.getId()+":Status",rt.getStatus(),null,null,rt.getId()));
            }
            if (rt.getVersionInfo()!=null) {
                r.setVersionInfo(parseVersionInfoTypeAndSave(rt.getVersionInfo(),rt.getId()));
            }
            if (rt.getCatalogingLatency()!=null) {
                r.setCatalogingLatency(rt.getCatalogingLatency().toString());
            }
            if (rt.getConformanceProfile()!=null) {
                r.setConformanceProfile(rt.getConformanceProfile().toString());
            }
            if (rt.getOperator()!=null) {
                r.setOperator(parseObjectRefTypeAndSave(rt.getId()+":Operator",rt.getOperator(),null,null,rt.getId()));
            }
            if (rt.getReplicationSyncLatency()!=null) {
                r.setReplicationSyncLatency(rt.getReplicationSyncLatency().toString());
            }
            if (rt.getSpecificationVersion()!=null) {
                r.setSpecificationVersion(rt.getSpecificationVersion());
            }
        }
        insertRegistryInfo(r);
        return r;
    }

    /**
     * 解析objectRef对象
     *
     * @param ort
     * @return
     */
    public static ObjectRef parseObjectrefTypeAndSave(ObjectRefType ort, String objectrefId) {
        ObjectRef o = SpringContextUtil.getBean(ObjectRef.class);
        if (ort!=null) {
            if (ort.getId()!=null) {
                o.setId(ort.getId());
            }
            if (ort.getHome()!=null) {
                o.setHome(ort.getHome());
            }
            if (ort.getSlotArray()!=null&&ort.getSlotArray().length>0) {
                int i = 1;
                for (SlotType1 slotType1 : ort.getSlotArray()) {
                    o.getSlots().add(parseSlotTypeAndSave(slotType1,ort.getId()+":ObjectRef:Slot"+i,ort.getId()));
                    i++;
                }
            }
            if ((Boolean)ort.getCreateReplica()!=null) {
                o.setCreateReplica(ort.getCreateReplica());
            }
        }
        insertObjectRefInfo(o,objectrefId);
        return o;
    }

    /**
     * 解析NotificationType对象
     *
     * @param nt
     * @return
     */
    public static Notification parseNotificationTypeAndSave(NotificationType nt, String notificationId) {
        Notification n = SpringContextUtil.getBean(Notification.class);
        if (nt!=null) {
            if (nt.getClassificationArray()!=null&&nt.getClassificationArray().length>0) {
                for (ClassificationType ct : nt.getClassificationArray()) {
                    n.getClassifications().add(parseClassificationTypeAndSave(ct,nt.getId()));
                }
            }
            if (nt.getDescription()!=null) {
                n.setDescription(parseInternationalStringTypeAndSave(nt.getDescription(),nt.getId()+":Description"));
            }
            if (nt.getExternalIdentifierArray()!=null&&nt.getExternalIdentifierArray().length>0) {
                for (ExternalIdentifierType eit : nt.getExternalIdentifierArray()) {
                    n.getExternalIdentifiers().add(parseExternalIdentifierTypeAndSave(eit,eit.getId()));
                }
            }
            if (nt.getId()!=null) {
                n.setId(nt.getId());
            }
            if (nt.getHome()!=null) {
                n.setHome(nt.getHome());
            }
            if (nt.getLid()!=null) {
                n.setLid(nt.getLid());
            }
            if (nt.getName()!=null) {
                n.setName(parseInternationalStringTypeAndSave(nt.getName(),nt.getId()+":Name"));
            }
            if (nt.getObjectType()!=null) {
                n.setObjectType(parseObjectRefTypeAndSave(nt.getId()+":ObjectType",nt.getObjectType(),null,null,nt.getId()));
            }
            if (nt.getSlotArray()!=null&&nt.getSlotArray().length>0) {
                int i = 1;
                for (SlotType1 slotType1 : nt.getSlotArray()) {
                    n.getSlots().add(parseSlotTypeAndSave(slotType1,nt.getId()+":Notification:Slot"+i,nt.getId()));
                    i++;
                }
            }
            if (nt.getStatus()!=null) {
                n.setStatus(parseObjectRefTypeAndSave(nt.getId()+":Status",nt.getStatus(),null,null,nt.getId()));
            }
            if (nt.getVersionInfo()!=null) {
                n.setVersionInfo(parseVersionInfoTypeAndSave(nt.getVersionInfo(),nt.getId()));
            }
            if (nt.getRegistryObjectList()!=null&&nt.getRegistryObjectList().getIdentifiableArray()!=null&&nt.getRegistryObjectList().getIdentifiableArray().length>0) {
                for (IdentifiableType it : nt.getRegistryObjectList().getIdentifiableArray()) {
                    n.getRegistryObjectList().add(parseIdentifiableAndSave(it,notificationId));
                }
            }
            if (nt.getSubscription()!=null) {
                n.setSubscription(parseObjectRefTypeAndSave(nt.getId()+":Subscription",nt.getSubscription(),null,null,nt.getId()));
            }
        }
        insertNotificationInfo(n,notificationId);
        return n;
    }

    /**
     * 解析Identifiable对象
     *
     * @param it
     *
     * @return
     */
    public static Identifiable parseIdentifiableAndSave(IdentifiableType it, String identifiableId) {
        Identifiable i = SpringContextUtil.getBean(Identifiable.class);
        if (it.getId()!=null) {
            i.setId(it.getId());
        }
        if (it.getHome()!=null) {
            i.setHome(it.getHome());
        }
        int num = 0;
        if (it.getSlotArray()!=null&&it.getSlotArray().length>0) {
            for (SlotType1 slotType1 : it.getSlotArray()) {
                i.getSlots().add(parseSlotTypeAndSave(slotType1,it.getId()+":Identifiable:Slot"+num,it.getId()));
                num++;
            }
        }
        insertIdentifiable(i,identifiableId);
        return i;
    }

    /**
     * 解析UserType对象(与person类似）
     *
     * @param ut
     *
     * @return
     */
    public static User parseUserTypeAndSave(UserType ut) {
        User user = SpringContextUtil.getBean(User.class);
        if (ut!=null) {
            if (ut.getAddressArray()!=null&&ut.getAddressArray().length>0) {
                for (PostalAddressType pat : ut.getAddressArray()) {
                    user.getAddresses().add(parsePostalAddressTypeAndSave(pat,ut.getId(),ut.getId()));
                }
            }
            if (ut.getId()!=null) {
                user.setId(ut.getId());
            }
            if (ut.getClassificationArray()!=null&&ut.getClassificationArray().length>0) {
                for (ClassificationType ct : ut.getClassificationArray()) {
                    user.getClassifications().add(parseClassificationTypeAndSave(ct,ut.getId()));
                }
            }
            if (ut.getDescription()!=null) {
                user.setDescription(parseInternationalStringTypeAndSave(ut.getDescription(),ut.getId()+":Description"));
            }
            if (ut.getEmailAddressArray()!=null&&ut.getEmailAddressArray().length>0) {
                for (EmailAddressType eat : ut.getEmailAddressArray()) {
                    user.getEmailAddresses().add(parseEmailAddressTypeAndSave(eat,ut.getId(),ut.getId()));
                }
            }
            if (ut.getHome()!=null) {
                user.setHome(ut.getHome());
            }
            if (ut.getId()!=null) {
                user.setId(ut.getId());
            }
            if (ut.getLid()!=null) {
                user.setLid(ut.getLid());
            }
            if (ut.getName()!=null) {
                user.setName(parseInternationalStringTypeAndSave(ut.getName(),ut.getId()+":Name"));
            }
            if (ut.getObjectType()!=null) {
                user.setObjectType(parseObjectRefTypeAndSave(ut.getId()+":ObjectType",ut.getObjectType(),null,null,ut.getId()));
            }
            if (ut.getPersonName()!=null) {
                user.setPersonName(parsePersonNameTypeAndSave(ut.getPersonName(),ut.getId()));
            }
            if (ut.getSlotArray()!=null&&ut.getSlotArray().length>0) {
                int i = 1;
                for (SlotType1 slotType1 : ut.getSlotArray()) {
                    user.getSlots().add(parseSlotTypeAndSave(slotType1,ut.getId()+":User:Slot"+i,ut.getId()));
                    i++;
                }
            }
            if (ut.getStatus()!=null) {
                user.setStatus(parseObjectRefTypeAndSave(ut.getId()+":Status",ut.getStatus(),null,null,ut.getId()));
            }
            if (ut.getTelephoneNumberArray()!=null&&ut.getTelephoneNumberArray().length>0) {
                for (TelephoneNumberType tnt : ut.getTelephoneNumberArray()) {
                    user.getTelephoneNumbers().add(parseTelephoneNumberTypeAndSave(tnt,ut.getId(),ut.getId()));
                }
            }
        }
        insertUserInfo(user);
        return user;
    }

    /**
     * 解析federationType对象
     *
     * @param ft
     * @return
     */
    public static Federation parseFederationTypeAndSave(FederationType ft) {
        Federation f = SpringContextUtil.getBean(Federation.class);
        if (ft!=null) {
            if (ft.getId()!=null) {
                f.setId(ft.getId());
            }
            if (ft.getClassificationArray()!=null&&ft.getClassificationArray().length>0) {
                for (ClassificationType ct : ft.getClassificationArray()) {
                    f.getClassifications().add(parseClassificationTypeAndSave(ct,ft.getId()));
                }
            }
            if (ft.getDescription()!=null) {
                f.setDescription(parseInternationalStringTypeAndSave(ft.getDescription(),ft.getId()+":Description"));
            }
            if (ft.getExternalIdentifierArray()!=null&&ft.getExternalIdentifierArray().length>0) {
                for (ExternalIdentifierType eit : ft.getExternalIdentifierArray()) {
                    f.getExternalIdentifiers().add(parseExternalIdentifierTypeAndSave(eit,eit.getId()));
                }
            }
            if (ft.getHome()!=null) {
                f.setHome(ft.getHome());
            }
            if (ft.getLid()!=null) {
                f.setLid(ft.getLid());
            }
            if (ft.getName()!=null) {
                f.setName(parseInternationalStringTypeAndSave(ft.getName(),ft.getId()+":Name"));
            }
            if (ft.getObjectType()!=null) {
                f.setObjectType(parseObjectRefTypeAndSave(ft.getId()+":ObjectType",ft.getObjectType(),null,null,ft.getId()));
            }
            if (ft.getSlotArray()!=null&&ft.getSlotArray().length>0) {
                int i = 1;
                for (SlotType1 slotType1 : ft.getSlotArray()) {
                    f.getSlots().add(parseSlotTypeAndSave(slotType1,ft.getId()+":Federation:Slot"+i,ft.getId()));
                }
            }
            if (ft.getStatus()!=null) {
                f.setStatus(parseObjectRefTypeAndSave(ft.getId()+":Status",ft.getStatus(),null,null,ft.getId()));
            }
            if (ft.getVersionInfo()!=null) {
                f.setVersionInfo(parseVersionInfoTypeAndSave(ft.getVersionInfo(),ft.getId()));
            }
            if (ft.getReplicationSyncLatency()!=null) {
                f.setReplicationSyncLatency(ft.getReplicationSyncLatency().toString());
            }
        }
        insertFederationInfo(f);
        return f;
    }

    /**
     * 解析PersonType对象
     *
     * @param pt
     *            type:PersonType
     * @return
     */
    public static Person parsePersonTypeAndSave(PersonType pt) {
        Person p = SpringContextUtil.getBean(Person.class);
        if (pt!=null) {
            if (pt.getAddressArray()!=null&&pt.getAddressArray().length>0) {
                for (PostalAddressType pat : pt.getAddressArray()) {
                    p.getAddresses().add(parsePostalAddressTypeAndSave(pat,pt.getId(),pt.getId()));
                }
            }
            if (pt.getClassificationArray()!=null&&pt.getClassificationArray().length>0) {
                for (ClassificationType ct : pt.getClassificationArray()) {
                    p.getClassifications().add(parseClassificationTypeAndSave(ct,pt.getId()));
                }
            }
            if (pt.getDescription()!=null) {
                p.setDescription(parseInternationalStringTypeAndSave(pt.getDescription(),pt.getId()+":Description"));
            }
            if (pt.getEmailAddressArray()!=null&&pt.getEmailAddressArray().length>0) {
                for (EmailAddressType eat : pt.getEmailAddressArray()) {
                    p.getEmailAddresses().add(parseEmailAddressTypeAndSave(eat,pt.getId(),pt.getId()));
                }
            }
            if (pt.getHome()!=null) {
                p.setHome(pt.getHome());
            }
            if (pt.getId()!=null) {
                p.setId(pt.getId());
            }
            if (pt.getLid()!=null) {
                p.setLid(pt.getLid());
            }
            if (pt.getName()!=null) {
                p.setName(parseInternationalStringTypeAndSave(pt.getName(),pt.getId()+":Name"));
            }
            if (pt.getObjectType()!=null) {
                p.setObjectType(parseObjectRefTypeAndSave(pt.getId()+":ObjectType",pt.getObjectType(),null,null,pt.getId()));
            }
            if (pt.getPersonName()!=null) {
                p.setPersonName(parsePersonNameTypeAndSave(pt.getPersonName(),pt.getId()));
            }
            if (pt.getSlotArray()!=null&&pt.getSlotArray().length>0) {
                int i = 1;
                for (SlotType1 slotType1 : pt.getSlotArray()) {
                    p.getSlots().add(parseSlotTypeAndSave(slotType1,pt.getId()+":Person:Slot"+i,pt.getId()));
                    i++;
                }
            }
            if (pt.getStatus()!=null) {
                p.setStatus(parseObjectRefTypeAndSave(pt.getId()+":Status",pt.getStatus(),null,null,pt.getId()));
            }
            if (pt.getTelephoneNumberArray()!=null&&pt.getTelephoneNumberArray().length>0) {
                for (TelephoneNumberType tnt : pt.getTelephoneNumberArray()) {
                    p.getTelephoneNumbers().add(parseTelephoneNumberTypeAndSave(tnt,pt.getId(),pt.getId()));
                }
            }
        }
        insertPersonInfo(p);
        return p;
    }

    /**
     * 解析PersonNameType属性
     *
     * @param pnt
     * @param id
     * @return
     */
    public static PersonName parsePersonNameTypeAndSave(PersonNameType pnt, String id) {
        PersonName pn = SpringContextUtil.getBean(PersonName.class);
        if (pnt!=null) {
            if (id!=null) {
                pn.setId(id);
            }
            if (pnt.getFirstName()!=null) {
                pn.setFirstName(pnt.getFirstName());
            }
            if (pnt.getMiddleName()!=null) {
                pn.setMiddleName(pnt.getMiddleName());
            }
            if (pnt.getLastName()!=null) {
                pn.setLastName(pnt.getLastName());
            }
        }
        insertPersonNameInfo(pn);
        return pn;
    }

    /**
     * 解析organizationType属性
     *
     * @param ot
     * @return
     */
    public static Organization parseOrganizationTypeAndSave(OrganizationType ot) {
        Organization o = SpringContextUtil.getBean(Organization.class);
        if (ot!=null) {
            if (ot.getAddressArray()!=null&&ot.getAddressArray().length>0) {
                for (PostalAddressType pat : ot.getAddressArray()) {
                    o.getAddresses().add(parsePostalAddressTypeAndSave(pat,ot.getId(),ot.getId()));
                }
            }
            if (ot.getEmailAddressArray()!=null&&ot.getEmailAddressArray().length>0) {
                for (EmailAddressType eat : ot.getEmailAddressArray()) {
                    o.getEmailAddresses().add(parseEmailAddressTypeAndSave(eat,ot.getId(),ot.getId()));
                }
            }
            if (ot.getClassificationArray()!=null&&ot.getClassificationArray().length>0) {
                for (ClassificationType ct : ot.getClassificationArray()) {
                    o.getClassifications().add(parseClassificationTypeAndSave(ct,ot.getId()));
                }
            }
            if (ot.getDescription()!=null) {
                o.setDescription(parseInternationalStringTypeAndSave(ot.getDescription(),ot.getId()+":Description"));
            }
            if (ot.getExternalIdentifierArray()!=null&&ot.getExternalIdentifierArray().length>0) {
                for (ExternalIdentifierType eit : ot.getExternalIdentifierArray()) {
                    o.getExternalIdentifiers().add(parseExternalIdentifierTypeAndSave(eit,eit.getId()));
                }
            }
            if (ot.getHome()!=null) {
                o.setHome(ot.getHome());
            }
            if (ot.getId()!=null) {
                o.setId(ot.getId());
            }
            if (ot.getLid()!=null) {
                o.setLid(ot.getLid());
            }
            if (ot.getName()!=null) {
                o.setName(parseInternationalStringTypeAndSave(ot.getName(),ot.getId()+":Name"));
            }
            if (ot.getObjectType()!=null) {
                o.setObjectType(parseObjectRefTypeAndSave(ot.getId()+":ObjectType",ot.getObjectType(),null,null,ot.getId()));
            }
            if (ot.getParent()!=null) {
                o.setParent(parseObjectRefTypeAndSave(ot.getId()+":Parent",ot.getParent(),null,null,ot.getId()));
            }
            if (ot.getPrimaryContact()!=null) {
                o.setPrimaryContact(parseObjectRefTypeAndSave(ot.getId()+":PrimaryContact",ot.getPrimaryContact(),null,null,ot.getId()));
            }
            if (ot.getSlotArray()!=null&&ot.getSlotArray().length>0) {
                int i = 1;
                for (SlotType1 slotType1 : ot.getSlotArray()) {
                    o.getSlots().add(parseSlotTypeAndSave(slotType1,ot.getId()+":Organization:Slot"+i,ot.getId()));
                    i++;
                }
            }
            if (ot.getStatus()!=null) {
                o.setStatus(parseObjectRefTypeAndSave(ot.getId()+":Status",ot.getStatus(),null,null,ot.getId()));
            }
            if (ot.getVersionInfo()!=null) {
                o.setVersionInfo(parseVersionInfoTypeAndSave(ot.getVersionInfo(),ot.getId()));
            }
            if (ot.getTelephoneNumberArray()!=null&&ot.getTelephoneNumberArray().length>0) {
                for (TelephoneNumberType tnt : ot.getTelephoneNumberArray()) {
                    o.getTelephoneNumbers().add(parseTelephoneNumberTypeAndSave(tnt,ot.getId(),ot.getId()));
                }
            }
        }
        insertOrganizationInfo(o);
        return o;
    }

    /**
     * 解析TelephoneNumber
     *
     * @param tnt
     * @param id
     * @return
     */
    public static TelephoneNumber parseTelephoneNumberTypeAndSave(TelephoneNumberType tnt, String id, String telephoneNumberId) {
        TelephoneNumber tn = SpringContextUtil.getBean(TelephoneNumber.class);
        if (tnt!=null) {
            if (id!=null) {
                tn.setId(id);
            }
            if (tnt.getAreaCode()!=null) {
                tn.setAreaCode(tnt.getAreaCode());
            }
            if (tnt.getCountryCode()!=null) {
                tn.setCountryCode(tnt.getCountryCode());
            }
            if (tnt.getNumber()!=null) {
                tn.setNumber(tnt.getNumber());
            }
            if (tnt.getExtension()!=null) {
                tn.setExtension(tnt.getExtension());
            }
            if (tnt.getPhoneType()!=null) {
                tn.setPhoneType(parseObjectRefTypeAndSave(id,tnt.getPhoneType(),null,null,telephoneNumberId));
            }
        }
        insertTelephoneNumberInfo(tn,telephoneNumberId);
        return tn;
    }

    /**
     * 解析EmailAddressType属性
     *
     * @param eat
     * @param id
     * @return
     */
    public static EmailAddress parseEmailAddressTypeAndSave(EmailAddressType eat, String id, String emailAddressId) {
        EmailAddress ea = SpringContextUtil.getBean(EmailAddress.class);
        if (eat!=null) {
            if (id!=null) {
                ea.setId(id);
            }
            if (eat.getAddress()!=null) {
                ea.setAddress(eat.getAddress());
            }
            if (eat.getType()!=null) {
                ea.setType(parseObjectRefTypeAndSave(id,eat.getType(),null,null,emailAddressId));
            }
        }
        insertEmailAddress(ea,emailAddressId);
        return ea;
    }

    /**
     * 解析PostalAddress属性
     *
     * @param pat
     * @param id
     * @return
     */
    public static PostalAddress parsePostalAddressTypeAndSave(PostalAddressType pat, String id, String postalAddressId) {
        PostalAddress pa = SpringContextUtil.getBean(PostalAddress.class);
        if (pat!=null) {
            if (pat.getCity()!=null) {
                pa.setCity(pat.getCity());
            }
            if (pat.getCity()!=null) {
                pa.setCountry(pat.getCountry());
            }
            if (pat.getPostalCode()!=null) {
                pa.setPostalCode(pat.getPostalCode());
            }
            if (id!=null) {
                pa.setId(id);
            }
            if (pat.getStateOrProvince()!=null) {
                pa.setStateOrProvince(pat.getStateOrProvince());
            }
            if (pat.getStreet()!=null) {
                pa.setStreet(pat.getStreet());
            }
            if (pat.getStreetNumber()!=null) {
                pa.setStreetNumber(pat.getStreetNumber());
            }
        }
        insertPostalAddress(pa,postalAddressId);
        return pa;
    }

    /**
     * 解析serviceType属性
     *
     * @param serviceType:需要转换的serviceType
     * @return
     */
    public static Service parseServiceTypeAndSave(ServiceType serviceType) {
        Service service = SpringContextUtil.getBean(Service.class);
        if (serviceType!=null) {
            if (serviceType.getId()!=null) {
                service.setId(serviceType.getId());
            }
            if (serviceType.getClassificationArray()!=null&&serviceType.getClassificationArray().length>0) {
                for (ClassificationType classificationType : serviceType.getClassificationArray()) {
                    service.getClassifications().add(parseClassificationTypeAndSave(classificationType,serviceType.getId()));
                }
            }
            if (serviceType.getDescription()!=null) {
                service.setDescription(parseInternationalStringTypeAndSave(serviceType.getDescription(),serviceType.getId()+":Description"));
            }
            if (serviceType.getExternalIdentifierArray()!=null&&serviceType.getExternalIdentifierArray().length>0) {
                for (ExternalIdentifierType eit : serviceType.getExternalIdentifierArray()) {
                    service.getExternalIdentifiers().add(parseExternalIdentifierTypeAndSave(eit,eit.getId()));
                }
            }
            if (serviceType.getHome()!=null) {
                service.setHome(serviceType.getHome());
            }
            if (serviceType.getLid()!=null) {
                service.setLid(serviceType.getLid());
            }
            if (serviceType.getName()!=null) {
                service.setName(parseInternationalStringTypeAndSave(serviceType.getName(),serviceType.getId()+":Name"));
            }
            if (serviceType.getObjectType()!=null) {
                service.setObjectType(parseObjectRefTypeAndSave(serviceType.getId()+":ObjectType",serviceType.getObjectType(),null,null,serviceType.getId()));
            }
            if (serviceType.getServiceBindingArray()!=null&&serviceType.getServiceBindingArray().length>0) {
                for (ServiceBindingType serviceBindingType : serviceType.getServiceBindingArray()) {
                    service.getServiceBindings().add(parseServiceBindingTypeAndSave(serviceBindingType,serviceType.getId()));
                }
            }
            if (serviceType.getSlotArray()!=null&&serviceType.getSlotArray().length>0) {
                int i = 1;
                for (SlotType1 slotType1 : serviceType.getSlotArray()) {
                    service.getSlots().add(parseSlotTypeAndSave(slotType1,serviceType.getId()+":Service:Slot"+i,serviceType.getId()));
                    i++;
                }
            }
            if (serviceType.getStatus()!=null) {
                service.setStatus(parseObjectRefTypeAndSave(serviceType.getId()+":Status",serviceType.getStatus(),null,null,serviceType.getId()));
            }
            if (serviceType.getVersionInfo()!=null) {
                service.setVersionInfo(parseVersionInfoTypeAndSave(serviceType.getVersionInfo(),serviceType.getId()));
            }
        }
        insertServiceInfo(service);
        return service;
    }

    /**
     * 解析serivceBindingType属性
     *
     * @param serviceBindingType
     * @return
     */
    public static ServiceBinding parseServiceBindingTypeAndSave(ServiceBindingType serviceBindingType, String serviceBindingId) {
        ServiceBinding serviceBinding = SpringContextUtil.getBean(ServiceBinding.class);
        if (serviceBindingType!=null) {
            if (serviceBindingType.getAccessURI()!=null) {
                serviceBinding.setAccessURI(serviceBindingType.getAccessURI());
            }
            if (serviceBindingType.getClassificationArray()!=null&&serviceBindingType.getClassificationArray().length>0) {
                for (ClassificationType classificationType : serviceBindingType.getClassificationArray()) {
                    serviceBinding.getClassifications().add(parseClassificationTypeAndSave(classificationType,serviceBindingType.getId()));
                }
            }
            if (serviceBindingType.getDescription()!=null) {
                serviceBinding.setDescription(parseInternationalStringTypeAndSave(serviceBindingType.getDescription(),serviceBindingType.getId()+":Description"));
            }
            if (serviceBindingType.getExternalIdentifierArray()!=null&&serviceBindingType.getExternalIdentifierArray().length>0) {
                for (ExternalIdentifierType eit : serviceBindingType.getExternalIdentifierArray()) {
                    serviceBinding.getExternalIdentifiers().add(parseExternalIdentifierTypeAndSave(eit,eit.getId()));
                }
            }
            if (serviceBindingType.getHome()!=null) {
                serviceBinding.setHome(serviceBindingType.getHome());
            }
            if (serviceBindingType.getId()!=null) {
                serviceBinding.setId(serviceBindingType.getId());
            }
            if (serviceBindingType.getLid()!=null) {
                serviceBinding.setLid(serviceBindingType.getLid());
            }
            if (serviceBindingType.getName()!=null) {
                serviceBinding.setName(parseInternationalStringTypeAndSave(serviceBindingType.getName(),serviceBindingType.getId()+":Name"));
            }
            if (serviceBindingType.getObjectType()!=null) {
                serviceBinding.setObjectType(parseObjectRefTypeAndSave(serviceBindingType.getId()+":ObjectType",serviceBindingType.getObjectType(),null,null,serviceBindingType.getId()));
            }
            if (serviceBindingType.getService()!=null) {
                serviceBinding.setService(parseObjectRefTypeAndSave(serviceBindingType.getId()+":Service",serviceBindingType.getService(),null,null,serviceBindingType.getId()));
            }
            if (serviceBindingType.getSlotArray()!=null&&serviceBindingType.getSlotArray().length>0) {
                int i = 1;
                for (SlotType1 slotType1 : serviceBindingType.getSlotArray()) {
                    serviceBinding.getSlots().add(parseSlotTypeAndSave(slotType1,serviceBindingType.getId()+":ServiceBinding:Slot"+i,serviceBindingType.getId()));
                    i++;
                }
            }
            if (serviceBindingType.getSpecificationLinkArray()!=null&&serviceBindingType.getSpecificationLinkArray().length>0) {
                for (SpecificationLinkType slt : serviceBindingType.getSpecificationLinkArray()) {
                    serviceBinding.getSpecificationLinks().add(parseSpecificationLinkTypeAndSave(slt,serviceBindingType.getId()));
                }
            }
            if (serviceBindingType.getStatus()!=null) {
                serviceBinding.setStatus(parseObjectRefTypeAndSave(serviceBindingType.getId()+":Status",serviceBindingType.getStatus(),null,null,serviceBindingType.getId()));
            }
            if (serviceBindingType.getTargetBinding()!=null) {
                serviceBinding.setTargetBinding(parseObjectRefTypeAndSave(serviceBindingType.getId()+":TargetBinding",serviceBindingType.getTargetBinding(),null,null,serviceBindingType.getId()));
            }
            if (serviceBindingType.getVersionInfo()!=null) {
                serviceBinding.setVersionInfo(parseVersionInfoTypeAndSave(serviceBindingType.getVersionInfo(),serviceBindingType.getId()));
            }
        }
        insertServiceBindingInfo(serviceBinding,serviceBindingId);
        return serviceBinding;
    }

    /**
     * 解析SpecificationLink属性，并保存起来
     *
     * @param slt
     * @return
     */
    public static SpecificationLink parseSpecificationLinkTypeAndSave(SpecificationLinkType slt, String specificationLinkId) {
        SpecificationLink sl = SpringContextUtil.getBean(SpecificationLink.class);
        if (slt!=null) {
            if (slt.getClassificationArray()!=null&&slt.getClassificationArray().length>0) {
                for (ClassificationType ct : slt.getClassificationArray()) {
                    sl.getClassifications().add(parseClassificationTypeAndSave(ct,slt.getId()));
                }
            }
            if (slt.getDescription()!=null) {
                sl.setDescription(parseInternationalStringTypeAndSave(slt.getDescription(),slt.getId()+":Description"));
            }
            if (slt.getExternalIdentifierArray()!=null&&slt.getExternalIdentifierArray().length>0) {
                for (ExternalIdentifierType eit : slt.getExternalIdentifierArray()) {
                    sl.getExternalIdentifiers().add(parseExternalIdentifierTypeAndSave(eit,eit.getId()));
                }
            }
            if (slt.getHome()!=null) {
                sl.setHome(slt.getHome());
            }
            if (slt.getId()!=null) {
                sl.setId(slt.getId());
            }
            if (slt.getLid()!=null) {
                sl.setLid(slt.getLid());
            }
            if (slt.getName()!=null) {
                sl.setName(parseInternationalStringTypeAndSave(slt.getName(),slt.getId()+":Name"));
            }
            if (slt.getObjectType()!=null) {
                sl.setObjectType(parseObjectRefTypeAndSave(slt.getId()+":ObjectType",slt.getObjectType(),null,null,slt.getId()));
            }
            if (slt.getServiceBinding()!=null) {
                sl.setServiceBinding(parseObjectRefTypeAndSave(slt.getId()+":ServiceBinding",slt.getServiceBinding(),null,null,slt.getId()));
            }
            if (slt.getSlotArray()!=null&&slt.getSlotArray().length>0) {
                int i = 1;
                for (SlotType1 slotType1 : slt.getSlotArray()) {
                    sl.getSlots().add(parseSlotTypeAndSave(slotType1,slt.getId()+":SpecificationLink:Slot"+i,slt.getId()));
                    i++;
                }
            }
            if (slt.getSpecificationObject()!=null) {
                sl.setSpecificationObject(parseObjectRefTypeAndSave(slt.getId()+":SpecificationObject",slt.getSpecificationObject(),null,null,slt.getId()));
            }
            if (slt.getStatus()!=null) {
                sl.setStatus(parseObjectRefTypeAndSave(slt.getId()+":Status",slt.getStatus(),null,null,slt.getId()));
            }
            if (slt.getUsageDescription()!=null) {
                sl.setUsageDescription(parseInternationalStringTypeAndSave(slt.getUsageDescription(),slt.getId()+":UsageDescription"));
            }
            if (slt.getUsageParameterArray()!=null) {
                String values = "";
                for (String usageParameter : slt.getUsageParameterArray()) {
                    values += usageParameter + ",";
                }
                sl.setUsageParameters(values);
            }
            if (slt.getVersionInfo()!=null) {
                sl.setVersionInfo(parseVersionInfoTypeAndSave(slt.getVersionInfo(),slt.getId()));
            }
        }
        insertSpecificationLinkInfo(sl,specificationLinkId);
        return sl;
    }

    /**
     * 解析associationType属性
     *
     * @param associationType
     * @return
     */
    public static Association parseAssociationTypeAndSave(AssociationType1 associationType) {
        Association association = SpringContextUtil.getBean(Association.class);
        if (associationType!=null) {
            if (associationType.getId()!=null) {
                association.setId(associationType.getId());
            }
            if (associationType.getName()!=null) {
                association.setName(parseInternationalStringTypeAndSave(associationType.getName(),associationType.getId()+":Name"));
            }
            if (associationType.getHome()!=null) {
                association.setHome(associationType.getHome());
            }
            if (associationType.getSlotArray()!=null&&associationType.getSlotArray().length>0) {
                int i = 1;
                for (SlotType1 slotType1 : associationType.getSlotArray()) {
                    association.getSlots().add(parseSlotTypeAndSave(slotType1,associationType.getId()+":Association:Slot"+i,associationType.getId()));
                    i++;
                }
            }
            if (associationType.getAssociationType()!=null) {
                association.setAssociationType(parseObjectRefTypeAndSave(associationType.getId()+":AssociationType",associationType.getAssociationType(),null,null,associationType.getId()));
            }
            if (associationType.getClassificationArray()!=null&&associationType.getClassificationArray().length>0) {
                for (ClassificationType ct : associationType.getClassificationArray()) {
                    association.getClassifications().add(parseClassificationTypeAndSave(ct,associationType.getId()));
                }
            }
            if (associationType.getDescription()!=null) {
                association.setDescription(parseInternationalStringTypeAndSave(associationType.getDescription(),associationType.getId()+":Description"));
            }
            if (associationType.getLid()!=null) {
                association.setLid(associationType.getLid());
            }
            if (associationType.getSourceObject()!=null) {
                association.setSourceObject(parseObjectRefTypeAndSave(associationType.getId()+":SourceObject",associationType.getSourceObject(),null,null,associationType.getId()));
            }
            if (associationType.getTargetObject()!=null) {
                association.setTargetObject(parseObjectRefTypeAndSave(associationType.getId()+":TargetObject",associationType.getTargetObject(),null,null,associationType.getId()));
            }
            if (associationType.getVersionInfo()!=null) {
                association.setVersionInfo(parseVersionInfoTypeAndSave(associationType.getVersionInfo(),associationType.getId()));
            }
            if (associationType.getObjectType()!=null) {
                association.setObjectType(parseObjectRefTypeAndSave(associationType.getId()+":ObjectType",associationType.getObjectType(),null,null,associationType.getId()));
            }
            if (associationType.getExternalIdentifierArray()!=null&&associationType.getExternalIdentifierArray().length>0) {
                for (ExternalIdentifierType eit : associationType.getExternalIdentifierArray()) {
                    association.getExternalIdentifiers().add(parseExternalIdentifierTypeAndSave(eit,eit.getId()));
                }
            }
            if (associationType.getStatus()!=null) {
                association.setStatus(parseObjectRefTypeAndSave(associationType.getId()+":Status",associationType.getStatus(),null,null,associationType.getId()));
            }

        }
        insertAssociationInfo(association);
        return association;
    }

    /**
     * 解析ClassificationNodeType 并将属性保存在ClassificationNode中
     *
     * @param cfnt
     * @return
     */
    public static ClassificationNode parseClassificationNodeTypeAndSave(ClassificationNodeType cfnt) {
        ClassificationNode cfn = SpringContextUtil.getBean(ClassificationNode.class);
        if (cfnt!=null) {
            if (cfnt.getId()!=null) {
                cfn.setId(cfnt.getId());
            }
            if (cfnt.getHome()!=null) {
                cfn.setHome(cfnt.getHome());
            }
            if (cfnt.getLid()!=null) {
                cfn.setLid(cfnt.getLid());
            }
            if (cfnt.getDescription()!=null) {
                cfn.setDescription(parseInternationalStringTypeAndSave(cfnt.getDescription(),cfnt.getId()+":Description"));
            }
            if (cfnt.getObjectType()!=null) {
                cfn.setObjectType(parseObjectRefTypeAndSave(cfnt.getId()+":ObjectType",cfnt.getObjectType(),null,null,cfnt.getId()));
            }
            if (cfnt.getParent()!=null) {
                cfn.setParent(parseObjectRefTypeAndSave(cfnt.getId()+":Parent",cfnt.getParent(),null,null,cfnt.getId()));
            }
            if (cfnt.getCode()!=null) {
                cfn.setCode(cfnt.getCode());
            }
            if (cfnt.getPath()!=null) {
                cfn.setPath(cfnt.getPath());
            }
            if (cfnt.getSlotArray()!=null&&cfnt.getSlotArray().length>0) {
                int i = 1;
                for (SlotType1 slotType1 : cfnt.getSlotArray()) {
                    cfn.getSlots().add(parseSlotTypeAndSave(slotType1,cfnt.getId()+":ClassificationNode:Slot"+i,cfnt.getId()));
                    i++;
                }
            }
            if (cfnt.getVersionInfo()!=null) {
                cfn.setVersionInfo(parseVersionInfoTypeAndSave(cfnt.getVersionInfo(),cfnt.getId()));
            }
            if (cfnt.getExternalIdentifierArray()!=null&&cfnt.getExternalIdentifierArray().length>0) {
                for (ExternalIdentifierType eit : cfnt.getExternalIdentifierArray()) {
                    cfn.getExternalIdentifiers().add(parseExternalIdentifierTypeAndSave(eit,eit.getId()));
                }
            }
            if (cfnt.getClassificationArray()!=null&&cfnt.getClassificationArray().length>0) {
                for (ClassificationType cfType : cfnt.getClassificationArray()) {
                    cfn.getClassifications().add(parseClassificationTypeAndSave(cfType,cfnt.getId()));
                }
            }
            if (cfnt.getStatus()!=null) {
                cfn.setStatus(parseObjectRefTypeAndSave(cfnt.getId()+":Status",cfnt.getStatus(),null,null,cfnt.getId()));
            }
            if (cfnt.getName()!=null) {
                cfn.setName(parseInternationalStringTypeAndSave(cfnt.getName(),cfnt.getId()+":Name"));
            }
        }
        insertClassificationNodeInfo(cfn);
        return cfn;
    }

    /**
     * 解析ExtrinsicObjectType并将属性保存在ExtrisicObject中
     *
     * @param eot
     * @return
     */
    public static ExtrinsicObject parseExtrinsicObjectAndSave(ExtrinsicObjectType eot) {
        ExtrinsicObject extrinsicObject = SpringContextUtil.getBean(ExtrinsicObject.class);
        if (eot!=null) {
            if (eot.getId()!=null) {
                extrinsicObject.setId(eot.getId());
            }
            if (eot.getLid()!=null) {
                extrinsicObject.setLid(eot.getLid());
            }
            if (eot.getHome()!=null) {
                extrinsicObject.setHome(eot.getHome());
            }
            if ((Boolean)eot.getIsOpaque()!=null) {
                extrinsicObject.setOpaque(1);
            }
            if (eot.getVersionInfo()!=null) {
                extrinsicObject.setVersionInfo(parseVersionInfoTypeAndSave(eot.getVersionInfo(),eot.getId()));
            }
            if (eot.getObjectType()!=null) {
                extrinsicObject.setObjectType(parseObjectRefTypeAndSave(eot.getId()+":ObjectType",eot.getObjectType(),null,null,eot.getId()));
            }
            if (eot.getName()!=null) {
                extrinsicObject.setName(parseInternationalStringTypeAndSave(eot.getName(),eot.getId()+":Name"));
            }
            if (eot.getMimeType()!=null) {
                extrinsicObject.setMimeType(eot.getMimeType());
            }
            if (eot.getExternalIdentifierArray()!=null&&eot.getExternalIdentifierArray().length>0) {
                for (ExternalIdentifierType eit : eot.getExternalIdentifierArray()) {
                    extrinsicObject.getExternalIdentifiers().add(parseExternalIdentifierTypeAndSave(eit,eit.getId()));
                }
            }
            if (eot.getDescription()!=null) {
                extrinsicObject.setDescription(parseInternationalStringTypeAndSave(eot.getDescription(),eot.getId()+":Description"));
            }
            if (eot.getClassificationArray()!=null&&eot.getClassificationArray().length>0) {
                for (ClassificationType ct : eot.getClassificationArray()) {
                    extrinsicObject.getClassifications().add(parseClassificationTypeAndSave(ct,eot.getId()));
                }
            }
            if (eot.getSlotArray()!=null&&eot.getSlotArray().length>0) {
                int i = 1;
                for (SlotType1 st : eot.getSlotArray()) {
                    extrinsicObject.getSlots().add(parseSlotTypeAndSave(st,eot.getId()+":ExtrinsicObject:Slot"+i,eot.getId()));
                    i++;
                }
            }
            if (eot.getStatus()!=null) {
                extrinsicObject.setStatus(parseObjectRefTypeAndSave(eot.getId()+":Status",eot.getStatus(),null,null,eot.getId()));
            }
            if (eot.getVersionInfo()!=null) {
                extrinsicObject.setVersionInfo(parseVersionInfoTypeAndSave(eot.getVersionInfo(),eot.getId()));
            }
        }
        insertExtrinsicObjectInfo(extrinsicObject);
        return extrinsicObject;
    }

    /**
     * 解析ExternalIdentifierType类型，并且将该类型转换成ExternalIdentifier类型。
     *
     * @param eit
     * @return
     */
    public static ExternalIdentifier parseExternalIdentifierTypeAndSave(ExternalIdentifierType eit, String externalIdentifierId) {
        ExternalIdentifier ei = SpringContextUtil.getBean(ExternalIdentifier.class);
        if (eit!=null) {
            if (eit.getId()!=null) {
                ei.setId(eit.getId());
            }
            if (eit.getLid()!=null) {
                ei.setLid(eit.getLid());
            }
            if (eit.getHome()!=null) {
                ei.setHome(eit.getHome());
            }
            if (eit.getObjectType()!=null) {
                ei.setObjectType(parseObjectRefTypeAndSave(eit.getId()+":ObjectRef",eit.getObjectType(),null,null,eit.getId()));
            }
            if (eit.getRegistryObject()!=null) {
                ei.setRegistryObject(parseObjectRefTypeAndSave(eit.getId()+":RegistryObject",eit.getRegistryObject(),null,null,eit.getId()));
            }
            if (eit.getVersionInfo()!=null) {
                ei.setVersionInfo(parseVersionInfoTypeAndSave(eit.getVersionInfo(),eit.getId()));
            }
            if (eit.getDescription()!=null) {
                ei.setDescription(parseInternationalStringTypeAndSave(eit.getDescription(),eit.getId()+":Description"));
            }
            if (eit.getIdentificationScheme()!=null) {
                ei.setIdentificationScheme(parseObjectRefTypeAndSave(eit.getId()+":IdentificationScheme",eit.getIdentificationScheme(),null,null,eit.getId()));
            }
            if (eit.getValue()!=null) {
                ei.setValue(eit.getValue());
            }
            if (eit.getStatus()!=null) {
                ei.setStatus(parseObjectRefTypeAndSave(eit.getId()+":Status",eit.getStatus(),null,null,eit.getId()));
            }
            if (eit.getClassificationArray()!=null&&eit.getClassificationArray().length>0) {
                for (ClassificationType ct : eit.getClassificationArray()) {
                    ei.getClassifications().add(parseClassificationTypeAndSave(ct,eit.getId()));
                }
            }
            if (eit.getExternalIdentifierArray()!=null&&eit.getExternalIdentifierArray().length>0) {
                for (ExternalIdentifierType externalIdentifierType : eit.getExternalIdentifierArray()) {
                    ei.getExternalIdentifiers().add(parseExternalIdentifierTypeAndSave(externalIdentifierType,eit.getId()));
                }
            }
            if (eit.getSlotArray()!=null&&eit.getSlotArray().length>0) {
                for (SlotType1 slotType1 : eit.getSlotArray()) {
                    ei.getSlots().add(parseSlotTypeAndSave(slotType1, eit.getId(),eit.getId()));
                }
            }
            if (eit.getName()!=null) {
                ei.setName(parseInternationalStringTypeAndSave(eit.getName(),eit.getId()+":Name"));
            }
        }
        insertExternalIdentifierInfo(ei,externalIdentifierId);
        return ei;
    }

    /**
     * 解析各种ClassificationType类型，并将解析的内容存储到classification类中
     *
     * @param ct
     * @return
     */
    public static Classification parseClassificationTypeAndSave(ClassificationType ct, String classificationId) {
        Classification c = SpringContextUtil.getBean(Classification.class);
        if (ct!=null) {
            if (ct.getId()!=null) {
                c.setId(ct.getId());
            }
            if (ct.getLid()!=null) {
                c.setLid(ct.getLid());
            }
            if (ct.getDescription()!=null) {
                c.setDescription(parseInternationalStringTypeAndSave(ct.getDescription(),ct.getId()+":Description"));
            }
            if (ct.getHome()!=null) {
                c.setHome(ct.getHome());
            }
            if (ct.getName()!=null) {
                c.setName( parseInternationalStringTypeAndSave(ct.getName(),ct.getId()+":Name"));
            }
            if (ct.getClassificationNode()!=null) {
                c.setClassificationNode(parseObjectRefTypeAndSave(ct.getId()+":ClassificationNode",ct.getClassificationNode(),null,null,ct.getId()));
            }
            if (ct.getStatus()!=null) {
                c.setStatus(parseObjectRefTypeAndSave(ct.getId()+":Status",ct.getStatus(),null,null,ct.getId()));
            }
            if (ct.getClassifiedObject()!=null) {
                c.setClassifiedObject(parseObjectRefTypeAndSave(ct.getId()+":ClassifiedObject",ct.getClassifiedObject(),null,null,ct.getId()));
            }
            if (ct.getObjectType()!=null) {
                c.setObjectType(parseObjectRefTypeAndSave(ct.getId()+":ObjectType",ct.getObjectType(),null,null,ct.getId()));
            }
            if (ct.getNodeRepresentation()!=null) {
                c.setNodeRepresentation(ct.getNodeRepresentation());
            }
            if (ct.getVersionInfo()!=null) {
                c.setVersionInfo(parseVersionInfoTypeAndSave(ct.getVersionInfo(),ct.getId()));
            }
            if (ct.getClassificationScheme()!=null) {
                c.setClassificationScheme(parseObjectRefTypeAndSave(c.getId()+":ClassificationScheme",ct.getClassificationScheme(),null,null,ct.getId()));
            }
            if (ct.getExternalIdentifierArray()!=null&&ct.getExternalIdentifierArray().length>0) {
                for (ExternalIdentifierType externalIdentifierType : ct.getExternalIdentifierArray()) {
                    c.getExternalIdentifiers().add(parseExternalIdentifierTypeAndSave(externalIdentifierType,ct.getId()));
                }
            }
            if (ct.getSlotArray()!=null&&ct.getSlotArray().length>0) {
                for (SlotType1 slotType1 : ct.getSlotArray()) {
                    c.getSlots().add(parseSlotTypeAndSave(slotType1,ct.getId(),ct.getId()));
                }
            }
        }
        insertClassificationInfo(c,classificationId);
        return c;
    }

    /**
     * 解析versioninfoType，由于在存储的时候需要一个id，id由外部给与
     *
     * @param versionInfoType
     * @param id
     * @return
     */
    public static VersionInfo parseVersionInfoTypeAndSave(VersionInfoType versionInfoType, String id) {
        VersionInfo versionInfo = SpringContextUtil.getBean(VersionInfo.class);
        if (id!=null) {
            versionInfo.setId(id);
        }
        if (versionInfoType!=null) {
            if (versionInfoType.getVersionName()!=null) {
                versionInfo.setVersionName(versionInfoType.getVersionName());
            }
            if (versionInfoType.getComment()!=null) {
                versionInfo.setComment(versionInfoType.getComment());
            }
        }
        insertVersionInfo(versionInfo);
        return versionInfo;
    }

    /**
     * 解析ObjectRef对象，并保存到数据库中
     *
     * @param id
     * @param home
     * @param slots
     * @param createReplica
     * @return
     */
    public static ObjectRef parseObjectRefTypeAndSave(String id, String home, Boolean createReplica,
                                               Set<Slot> slots, String objectRefId) {
        ObjectRef objectRef = SpringContextUtil.getBean(ObjectRef.class);
        if (id != null) {
            objectRef.setId(id);
        }
        if (home != null) {
            objectRef.setHome(home);
        }
        if (createReplica != null) {
            objectRef.setCreateReplica(createReplica);
        }
        if (slots != null) {
            for (Slot slot : slots) {
                InsertDataUtil.insertSlotInfo(slot,objectRefId);
            }
            objectRef.setSlots(slots);
        }
        insertObjectRefInfo(objectRef,objectRefId);
        return objectRef;
    }

    /**
     * 解析Slot对象，并保存到数据库中
     *
     * @param slotType1
     * @param id
     * @return
     */
    public static Slot parseSlotTypeAndSave(SlotType1 slotType1, String id, String slotId) {
        Slot slot = SpringContextUtil.getBean(Slot.class);
        if (slotType1!=null) {
            if (id!=null) {
                slot.setId(id);
            }
            if (slotType1.getName()!=null) {
                slot.setName(slotType1.getName());
            }
            if (slotType1.getSlotType()!=null) {
                slot.setSlotType(slotType1.getSlotType());
            }
            if (slotType1.getValueList()!=null) {
                slot.setValues(slotType1.getValueList().toString());
            }
        }
        insertSlotInfo(slot,slotId);
        return slot;
    }

    /**
     * 解析InternationalStringType对象，并保存到数据库中
     *
     * @param inst
     * @param id
     * @return
     */
    public static InternationalString parseInternationalStringTypeAndSave(InternationalStringType inst, String id) {
        InternationalString ins = SpringContextUtil.getBean(InternationalString.class);
        if (inst!=null) {
            if (inst.getLocalizedStringArray().length>0&&inst.getLocalizedStringArray().length>0) {
                for (LocalizedStringType lst : inst.getLocalizedStringArray()) {
                    ins.getLocalizedStrings().add(parseLocalizedStringTypeAndSave(lst,id,id));
                }
            }
        }
        if (id!=null) {
            ins.setId(id);
        }
        insertInternationalString(ins);
        return ins;
    }

    /**
     * 解析localizedStringType类型，并存入到相应的localizedString中,
     * 由于localizedStringType中不提供ID属性，需要外部加入id属性
     *
     * @return
     */
    public static LocalizedString parseLocalizedStringTypeAndSave(LocalizedStringType lst, String id, String localizedStringId) {
        LocalizedString ls = SpringContextUtil.getBean(LocalizedString.class);
        if (id!=null) {
            ls.setId(id);
        }
        if (lst.getLang()!=null) {
            ls.setLang(lst.getLang());
        }
        if (lst.getValue()!=null) {
            ls.setValue(lst.getValue());
        }
        if (lst.getCharset().toString()!=null) {
            ls.setCharSet(lst.getCharset().getStringValue());
        }
        insertLocalizedString(ls,localizedStringId);
        return ls;
    }

    /***************************************************************************/

    public static int insertRegistryPackageInfo(RegistryPackage registryPackage) {
        RegistryPackageDAO registryPackageDAO = SpringContextUtil.getBean(RegistryPackageDAO.class);
        int status = 0;
        Timestamp timestamp = GetSystemInfoUtil.getTimeStamp();
        try {
            status = registryPackageDAO.insertData(registryPackage,timestamp);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertExtrinsicObjectInfo(ExtrinsicObject extrinsicObject) {
        ExtrinsicObjectDAO extrinsicObjectDAO = SpringContextUtil.getBean(ExtrinsicObjectDAO.class);
        int status = 0;
        try {
            status = extrinsicObjectDAO.insertData(extrinsicObject);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertInternationalString(InternationalString internationalString) {
        InternationalStringDAO internationalStringDAO = SpringContextUtil.getBean(InternationalStringDAO.class);
        int status = 0;
        try {
            status = internationalStringDAO.insertData(internationalString);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertLocalizedString(LocalizedString localizedString,String localizedStringId) {
        LocalizedStringDAO localizedStringDAO = SpringContextUtil.getBean(LocalizedStringDAO.class);
        int status = 0;
        try {
            localizedStringDAO.insertData(localizedString,localizedStringId);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertSlotInfo(Slot slot, String slotId) {
        SlotDAO slotDAO = SpringContextUtil.getBean(SlotDAO.class);
        int status = 0;
        try {
            status = slotDAO.insertData(slot,slotId);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertClassificationInfo(Classification classification, String classificationId) {
        ClassificationDAO classificationDAO = SpringContextUtil.getBean(ClassificationDAO.class);
        int status = 0;
        try {
            status = classificationDAO.insertData(classification,classificationId);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertAssociationInfo(Association association) {
        AssociationDAO associationDAO = SpringContextUtil.getBean(AssociationDAO.class);
        int status = 0;
        try {
            status = associationDAO.insertData(association);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertClassificationNodeInfo(ClassificationNode classificationNode) {
        ClassificationNodeDAO classificationNodeDAO = SpringContextUtil.getBean(ClassificationNodeDAO.class);
        int status = 0;
        try {
            status = classificationNodeDAO.insertData(classificationNode);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertServiceInfo(Service service) {
        ServiceDAO serviceDAO = SpringContextUtil.getBean(ServiceDAO.class);
        int status = 0;
        try {
            status = serviceDAO.insertData(service);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertServiceBindingInfo(ServiceBinding serviceBinding, String serviceBindingId) {
        ServiceBindingDAO serviceBindingDAO = SpringContextUtil.getBean(ServiceBindingDAO.class);
        int status = 0;
        try {
            status = serviceBindingDAO.insertData(serviceBinding,serviceBindingId);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertOrganizationInfo(Organization organization) {
        OrganizationDAO organizationDAO = SpringContextUtil.getBean(OrganizationDAO.class);
        int status = 0;
        try {
            status = organizationDAO.insertData(organization);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertPersonInfo(Person person) {
        PersonDAO personDAO = SpringContextUtil.getBean(PersonDAO.class);
        int status = 0;
        try {
            status = personDAO.insertData(person);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertPersonNameInfo(PersonName personName) {
        PersonNameDAO personNameDAO = SpringContextUtil.getBean(PersonNameDAO.class);
        int status = 0;
        try {
            status = personNameDAO.insertData(personName);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertObjectRefInfo(ObjectRef objectRef,String objectRefId) {
        ObjectRefDAO objectRefDAO = SpringContextUtil.getBean(ObjectRefDAO.class);
        int status = 0;
        try {
            status = objectRefDAO.insertData(objectRef,objectRefId);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertPostalAddress(PostalAddress postalAddress, String postalAddressId) {
        PostalAddressDAO postalAddressDAO = SpringContextUtil.getBean(PostalAddressDAO.class);
        int status = 0;
        try {
            status = postalAddressDAO.insertData(postalAddress,postalAddressId);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertEmailAddress(EmailAddress emailAddress, String emailAddressId) {
        EmailAddressDAO emailAddressDAO = SpringContextUtil.getBean(EmailAddressDAO.class);
        int status = 0;
        try {
            status = emailAddressDAO.insertData(emailAddress,emailAddressId);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertVersionInfo(VersionInfo versionInfo) {
        VersionInfoDAO versionInfoDAO = SpringContextUtil.getBean(VersionInfoDAO.class);
        int status = 0;
        try {
            status = versionInfoDAO.insertData(versionInfo);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertExternalIdentifierInfo(ExternalIdentifier externalIdentifier, String externalIdentifierId) {
        ExternalIdentifierDAO externalIdentifierDAO = SpringContextUtil.getBean(ExternalIdentifierDAO.class);
        int status = 0;
        try {
            status = externalIdentifierDAO.insertData(externalIdentifier,externalIdentifierId);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertIdentifiable(Identifiable identifiable, String identifiable_id) {
        IdentifiableDAO identifiableDAO = SpringContextUtil.getBean(IdentifiableDAO.class);
        int status = 0;
        try {
            status = identifiableDAO.insertData(identifiable,identifiable_id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertSpecificationLinkInfo(SpecificationLink specificationLink, String specificationLinkId) {
        SpecificationLinkDAO specificationLinkDAO = SpringContextUtil.getBean(SpecificationLinkDAO.class);
        int status = 0;
        try {
            status = specificationLinkDAO.insertData(specificationLink,specificationLinkId);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertTelephoneNumberInfo(TelephoneNumber telephoneNumber, String telephoneNumberId) {
        TelephoneNumberDAO telephoneNumberDAO = SpringContextUtil.getBean(TelephoneNumberDAO.class);
        int status = 0;
        try {
            status = telephoneNumberDAO.insertData(telephoneNumber,telephoneNumberId);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertFederationInfo(Federation federation) {
        FederationDAO federationDAO = SpringContextUtil.getBean(FederationDAO.class);
        int status = 0;
        try {
            status = federationDAO.insertData(federation);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertUserInfo(User user) {
        UserDAO userDAO = SpringContextUtil.getBean(UserDAO.class);
        int status = 0;
        try {
            status = userDAO.insertData(user);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertNotificationInfo(Notification notification, String notificationId) {
        NotificationDAO notificationDAO = SpringContextUtil.getBean(NotificationDAO.class);
        int status = 0;
        try {
            status = notificationDAO.insertData(notification,notificationId);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertRegistryInfo(Registry registry) {
        RegistryDAO registryDAO = SpringContextUtil.getBean(RegistryDAO.class);
        int status = 0;
        try {
            status = registryDAO.insertData(registry);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertActionInfo(Action action) {
        _ActionDAO actionDAO = SpringContextUtil.getBean(_ActionDAO.class);
        int status = 0;
        try {
            status = actionDAO.insertData(action);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return status;
        }

    }

    public static int insertActionGroupInfo(ActionGroup actionGroup) {
        _ActionGroupDAO actionGroupDAO = SpringContextUtil.getBean(_ActionGroupDAO.class);
        int status = 0;
        try {
            status = actionGroupDAO.insertData(actionGroup);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertGroupInfo(Group group) {
        _GroupDAO groupDAO = SpringContextUtil.getBean(_GroupDAO.class);
        int status = 0;
        try {
            status = groupDAO.insertData(group);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertMasterInfo(Master master) {
        _MasterDAO masterDAO = SpringContextUtil.getBean(_MasterDAO.class);
        int status = 0;
        try {
            status = masterDAO.insertData(master);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertMasterGroupInfo(MasterGroup masterGroup) {
        _MasterGroupDAO masterGroupDAO = SpringContextUtil.getBean(_MasterGroupDAO.class);
        int status = 0;
        try {
            status = masterGroupDAO.insertData(masterGroup);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int insertDataInfo(Data data) {
        DataDAO dataDAO = SpringContextUtil.getBean(DataDAO.class);
        int status = 0;
        try {
            status = dataDAO.insertData(data);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }
    /***************************************************************************/
}
