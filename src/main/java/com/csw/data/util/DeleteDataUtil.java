package com.csw.data.util;

import com.csw.dao.*;
import com.csw.model.*;
import com.csw.spring.util.SpringContextUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class DeleteDataUtil {

    private static TransactionStatus transactionStatus;
    private static DataSourceTransactionManager transactionManager;

    static {
        transactionManager = SpringContextUtil.getBean("transactionManager");
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);
    }
    private static Object savePoint = null;

    public static int deleteRegistryPackageContent(String id) {
        RegistryPackage registryPackage = QueryDataUtil.queryRegistryPackageById(id);
        savePoint = transactionStatus.createSavepoint();

        if (registryPackage!=null) {
            if (registryPackage.getDescription()!=null) {
                deleteInternationalStringContent(registryPackage.getDescription());
            }
            if (registryPackage.getName()!=null) {
                deleteInternationalStringContent(registryPackage.getName());
            }
            if (registryPackage.getObjectType()!=null) {
                deleteObjectRefContent(registryPackage.getObjectType());
            }
            if (registryPackage.getStatus()!=null) {
                deleteObjectRefContent(registryPackage.getStatus());
            }
            if (registryPackage.getVersionInfo()!=null) {
                deleteVersionInfoContent(registryPackage.getVersionInfo());
            }
            if (registryPackage.getSlots()!=null&&registryPackage.getSlots().size()>0) {
                for (Slot slot : registryPackage.getSlots()) {
                    deleteSlotContent(slot);
                }
            }
            if (registryPackage.getClassifications()!=null&&registryPackage.getClassifications().size()>0) {
                for (Classification classification : registryPackage.getClassifications()) {
                    deleteClassificationContent(classification);
                }
            }
            if (registryPackage.getExternalIdentifiers()!=null&&registryPackage.getExternalIdentifiers().size()>0) {
                for (ExternalIdentifier externalIdentifier : registryPackage.getExternalIdentifiers()) {
                    deleteExternalIdentifierContent(externalIdentifier);
                }
            }
            if (registryPackage.getRegistryObjectList()!=null&&registryPackage.getRegistryObjectList().size()>0) {
                for (Identifiable identifiable : registryPackage.getRegistryObjectList()) {
                    deleteIdentifiableContent(identifiable);
                }
            }
        }
        int status = deleteRegistryPackageById(id);
        transactionManager.commit(transactionStatus);
        return status;
    }

    public static int deleteIdentifiableContent(Identifiable identifiable) {
        if (identifiable.getHome().equals("com.ebrim.model.wrs.impl.ExtrinsicObjectTypeImpl")) {
            ExtrinsicObject extrinsicObject = QueryDataUtil.queryExtrinsicObjectById(identifiable.getId());
            if (extrinsicObject!=null) {
                deleteExtrinsicObjectContent(extrinsicObject);
            }
        }
        if (identifiable.getHome().equals("com.ebrim.model.rim.impl.ClassificationNodeTypeImpl")) {
            ClassificationNode classificationNode = QueryDataUtil.queryClassificationNodesById(identifiable.getId());
            if (classificationNode!=null) {
                deleteClassificationNodeContent(classificationNode);
            }
        }
        if (identifiable.getHome().equals("com.ebrim.model.rim.impl.AssociationType1Impl")) {
            Association association = QueryDataUtil.queryAssociationById(identifiable.getId());
            if (association!=null) {
                deleteAssociationContent(association);
            }
        }
        if (identifiable.getHome().equals("com.ebrim.model.rim.impl.ServiceTypeImpl")) {
            Service service = QueryDataUtil.queryServiceById(identifiable.getId());
            if (service!=null) {
                deleteServiceContent(service);
            }
        }
        if (identifiable.getHome().equals("com.ebrim.model.rim.impl.OrganizationTypeImpl")) {
            Organization organization = QueryDataUtil.queryOrganizationById(identifiable.getId());
            if (organization!=null) {
                deleteOrganizationContent(organization);
            }
        }
        if (identifiable.getHome().equals("com.ebrim.model.rim.impl.PersonTypeImpl")) {
            Person person = QueryDataUtil.queryPersonById(identifiable.getId());
            if (person!=null) {
                deletePersonContent(person);
            }
        }
        if (identifiable.getHome().equals("com.ebrim.model.rim.impl.FederationTypeImpl")) {
            Federation federation = QueryDataUtil.queryFederationById(identifiable.getId());
            if (federation!=null) {
                deleteFederationContent(federation);
            }
        }
        if (identifiable.getHome().equals("com.ebrim.model.rim.impl.UserTypeImpl")) {
            User user = QueryDataUtil.queryUserById(identifiable.getId());
            if (user!=null) {
                deleteUserContent(user);
            }
        }
        if (identifiable.getHome().equals("com.ebrim.model.rim.impl.NotificationTypeImpl")) {
            Notification notification = QueryDataUtil.queryNotificationById(identifiable.getId());
            if (notification!=null) {
                deleteNotifiationContent(notification);
            }
        }
        if (identifiable.getHome().equals("com.ebrim.model.rim.impl.ObjectRefTypeImpl")) {
            ObjectRef objectRef = QueryDataUtil.queryObjectRefById(identifiable.getId());
            if (objectRef!=null) {
                deleteObjectRefContent(objectRef);
            }
        }
        if (identifiable.getHome().equals("com.ebrim.model.rim.impl.RegistryTypeImpl")) {
            Registry registry = QueryDataUtil.queryRegistryById(identifiable.getId());
            if (registry!=null) {
                deleteRegistryContent(registry);
            }
        }
        int status = deleteIdentifiableById(identifiable.getId());
        return status;
    }

    public static int deleteRegistryContent(Registry registry) {
        if (registry.getDescription()!=null) {
            deleteInternationalStringContent(registry.getDescription());
        }
        if (registry.getName()!=null) {
            deleteInternationalStringContent(registry.getName());
        }
        if (registry.getObjectType()!=null) {
            deleteObjectRefContent(registry.getObjectType());
        }
        if (registry.getStatus()!=null) {
            deleteObjectRefContent(registry.getStatus());
        }
        if (registry.getOperator()!=null) {
            deleteObjectRefContent(registry.getOperator());
        }
        if (registry.getVersionInfo()!=null) {
            deleteVersionInfoContent(registry.getVersionInfo());
        }
        if (registry.getSlots()!=null&&registry.getSlots().size()>0) {
            for (Slot slot : registry.getSlots()) {
                deleteSlotContent(slot);
            }
        }
        if (registry.getClassifications()!=null&&registry.getClassifications().size()>0) {
            for (Classification classification : registry.getClassifications()) {
                deleteClassificationContent(classification);
            }
        }
        if (registry.getExternalIdentifiers()!=null&&registry.getExternalIdentifiers().size()>0) {
            for (ExternalIdentifier externalIdentifier : registry.getExternalIdentifiers()) {
                deleteExternalIdentifierContent(externalIdentifier);
            }
        }
        int status = deleteRegistryById(registry.getId());
        return status;
    }

    public static int deleteNotifiationContent(Notification notification) {
        if (notification.getDescription()!=null) {
            deleteInternationalStringContent(notification.getDescription());
        }
        if (notification.getName()!=null) {
            deleteInternationalStringContent(notification.getName());
        }
        if (notification.getObjectType()!=null) {
            deleteObjectRefContent(notification.getObjectType());
        }
        if (notification.getStatus()!=null) {
            deleteObjectRefContent(notification.getStatus());
        }
        if (notification.getSubscription()!=null) {
            deleteObjectRefContent(notification.getSubscription());
        }
        if (notification.getVersionInfo()!=null) {
            deleteVersionInfoContent(notification.getVersionInfo());
        }
        if (notification.getSlots()!=null&&notification.getSlots().size()>0) {
            for (Slot slot : notification.getSlots()) {
                deleteSlotContent(slot);
            }
        }
        if (notification.getClassifications()!=null&&notification.getClassifications().size()>0) {
            for (Classification classification : notification.getClassifications()) {
                deleteClassificationContent(classification);
            }
        }
        if (notification.getExternalIdentifiers()!=null&&notification.getExternalIdentifiers().size()>0) {
            for (ExternalIdentifier externalIdentifier : notification.getExternalIdentifiers()) {
                deleteExternalIdentifierContent(externalIdentifier);
            }
        }
        if (notification.getRegistryObjectList()!=null&&notification.getRegistryObjectList().size()>0) {
            for (Identifiable identifiable : notification.getRegistryObjectList()) {
                deleteIdentifiableContent(identifiable);
            }
        }
        int status = deleteNotificationById(notification.getId());
        return status;
    }

    public static int deleteUserContent(User user) {
        if (user.getDescription()!=null) {
            deleteInternationalStringContent(user.getDescription());
        }
        if (user.getName()!=null) {
            deleteInternationalStringContent(user.getName());
        }
        if (user.getObjectType()!=null) {
            deleteObjectRefContent(user.getObjectType());
        }
        if (user.getStatus()!=null) {
            deleteObjectRefContent(user.getStatus());
        }
        if (user.getPersonName()!=null) {
            deletePersonNameContent(user.getPersonName());
        }
        if (user.getVersionInfo()!=null) {
            deleteVersionInfoContent(user.getVersionInfo());
        }
        if (user.getSlots()!=null&&user.getSlots().size()>0) {
            for (Slot slot : user.getSlots()) {
                deleteSlotContent(slot);
            }
        }
        if (user.getClassifications()!=null&&user.getClassifications().size()>0) {
            for (Classification classification : user.getClassifications()) {
                deleteClassificationContent(classification);
            }
        }
        if (user.getExternalIdentifiers()!=null&&user.getExternalIdentifiers().size()>0) {
            for (ExternalIdentifier externalIdentifier : user.getExternalIdentifiers()) {
                deleteExternalIdentifierContent(externalIdentifier);
            }
        }
        if (user.getAddresses()!=null&&user.getAddresses().size()>0) {
            for (PostalAddress postalAddress : user.getAddresses()) {
                deletePostalAddressContent(postalAddress);
            }
        }
        if (user.getEmailAddresses()!=null&&user.getEmailAddresses().size()>0) {
            for (EmailAddress emailAddress : user.getEmailAddresses()) {
                deleteEmailAddressContent(emailAddress);
            }
        }
        if (user.getTelephoneNumbers()!=null&&user.getTelephoneNumbers().size()>0) {
            for (TelephoneNumber telephoneNumber : user.getTelephoneNumbers()) {
                deleteTelephoneNumberContent(telephoneNumber);
            }
        }
        int status = deleteUserById(user.getId());
        return status;
    }

    public static int deleteFederationContent(Federation federation) {
        if (federation.getDescription()!=null) {
            deleteInternationalStringContent(federation.getDescription());
        }
        if (federation.getName()!=null) {
            deleteInternationalStringContent(federation.getName());
        }
        if (federation.getObjectType()!=null) {
            deleteObjectRefContent(federation.getObjectType());
        }
        if (federation.getStatus()!=null) {
            deleteObjectRefContent(federation.getStatus());
        }
        if (federation.getVersionInfo()!=null) {
            deleteVersionInfoContent(federation.getVersionInfo());
        }
        if (federation.getSlots()!=null&&federation.getSlots().size()>0) {
            for (Slot slot : federation.getSlots()) {
                deleteSlotContent(slot);
            }
        }
        if (federation.getClassifications()!=null&&federation.getClassifications().size()>0) {
            for (Classification classification : federation.getClassifications()) {
                deleteClassificationContent(classification);
            }
        }
        if (federation.getExternalIdentifiers()!=null&&federation.getExternalIdentifiers().size()>0) {
            for (ExternalIdentifier externalIdentifier : federation.getExternalIdentifiers()) {
                deleteExternalIdentifierContent(externalIdentifier);
            }
        }
        int status = deleteFederationById(federation.getId());
        return status;
    }

    public static int deletePersonContent(Person person) {
        if (person.getDescription()!=null) {
            deleteInternationalStringContent(person.getDescription());
        }
        if (person.getName()!=null) {
            deleteInternationalStringContent(person.getName());
        }
        if (person.getObjectType()!=null) {
            deleteObjectRefContent(person.getObjectType());
        }
        if (person.getStatus()!=null) {
            deleteObjectRefContent(person.getStatus());
        }
        if (person.getVersionInfo()!=null) {
            deleteVersionInfoContent(person.getVersionInfo());
        }
        if (person.getPersonName()!=null) {
            deletePersonNameContent(person.getPersonName());
        }
        if (person.getSlots()!=null&&person.getSlots().size()>0) {
            for (Slot slot : person.getSlots()) {
                deleteSlotContent(slot);
            }
        }
        if (person.getClassifications()!=null&&person.getClassifications().size()>0) {
            for (Classification classification : person.getClassifications()) {
                deleteClassificationContent(classification);
            }
        }
        if (person.getExternalIdentifiers()!=null&&person.getExternalIdentifiers().size()>0) {
            for (ExternalIdentifier externalIdentifier : person.getExternalIdentifiers()) {
                deleteExternalIdentifierContent(externalIdentifier);
            }
        }
        if (person.getAddresses()!=null&&person.getAddresses().size()>0) {
            for (PostalAddress postalAddress : person.getAddresses()) {
                deletePostalAddressContent(postalAddress);
            }
        }
        if (person.getEmailAddresses()!=null&&person.getEmailAddresses().size()>0) {
            for (EmailAddress emailAddress : person.getEmailAddresses()) {
                deleteEmailAddressContent(emailAddress);
            }
        }
        if (person.getTelephoneNumbers()!=null&&person.getTelephoneNumbers().size()>0) {
            for (TelephoneNumber telephoneNumber : person.getTelephoneNumbers()) {
                deleteTelephoneNumberContent(telephoneNumber);
            }
        }
        int status = deletePersonById(person.getId());
        return status;
    }

    public static int deletePersonNameContent(PersonName personName) {
        int status = deletePersonNameById(personName.getId());
        return status;
    }

    public static int deleteOrganizationContent(Organization organization) {
        if (organization.getDescription()!=null) {
            deleteInternationalStringContent(organization.getDescription());
        }
        if (organization.getName()!=null) {
            deleteInternationalStringContent(organization.getName());
        }
        if (organization.getObjectType()!=null) {
            deleteObjectRefContent(organization.getObjectType());
        }
        if (organization.getStatus()!=null) {
            deleteObjectRefContent(organization.getStatus());
        }
        if (organization.getParent()!=null) {
            deleteObjectRefContent(organization.getParent());
        }
        if (organization.getPrimaryContact()!=null) {
            deleteObjectRefContent(organization.getPrimaryContact());
        }
        if (organization.getVersionInfo()!=null) {
            deleteVersionInfoContent(organization.getVersionInfo());
        }
        if (organization.getSlots()!=null&&organization.getSlots().size()>0) {
            for (Slot slot : organization.getSlots()) {
                deleteSlotContent(slot);
            }
        }
        if (organization.getClassifications()!=null&&organization.getClassifications().size()>0) {
            for (Classification classification : organization.getClassifications()) {
                deleteClassificationContent(classification);
            }
        }
        if (organization.getExternalIdentifiers()!=null&&organization.getExternalIdentifiers().size()>0) {
            for (ExternalIdentifier externalIdentifier : organization.getExternalIdentifiers()) {
                deleteExternalIdentifierContent(externalIdentifier);
            }
        }
        if (organization.getAddresses()!=null&&organization.getAddresses().size()>0) {
            for (PostalAddress postalAddress : organization.getAddresses()) {
                deletePostalAddressContent(postalAddress);
            }
        }
        if (organization.getEmailAddresses()!=null&&organization.getEmailAddresses().size()>0) {
            for (EmailAddress emailAddress : organization.getEmailAddresses()) {
                deleteEmailAddressContent(emailAddress);
            }
        }
        if (organization.getTelephoneNumbers()!=null&&organization.getTelephoneNumbers().size()>0) {
            for (TelephoneNumber telephoneNumber : organization.getTelephoneNumbers()) {
                deleteTelephoneNumberContent(telephoneNumber);
            }
        }
        int status = deleteOrganizationById(organization.getId());
        return status;
    }

    public static int deleteTelephoneNumberContent(TelephoneNumber telephoneNumber) {
        if (telephoneNumber.getPhoneType()!=null) {
            deleteObjectRefContent(telephoneNumber.getPhoneType());
        }
        int status = deleteTelephoneNumberById(telephoneNumber.getId());
        return status;
    }

    public static int deleteEmailAddressContent(EmailAddress emailAddress) {
        if (emailAddress.getType()!=null) {
            deleteObjectRefContent(emailAddress.getType());
        }
        int status = deleteEmailAddressById(emailAddress.getId());
        return status;
    }

    public static int deletePostalAddressContent(PostalAddress postalAddress) {
        if (postalAddress.getSlots()!=null&&postalAddress.getSlots().size()>0) {
            for (Slot slot : postalAddress.getSlots()) {
                deleteSlotContent(slot);
            }
        }
        int status = deletePostalAddressById(postalAddress.getId());
        return status;
    }

    public static int deleteServiceContent(Service service) {
        if (service.getDescription()!=null) {
            deleteInternationalStringContent(service.getDescription());
        }
        if (service.getName()!=null) {
            deleteInternationalStringContent(service.getName());
        }
        if (service.getObjectType()!=null) {
            deleteObjectRefContent(service.getObjectType());
        }
        if (service.getStatus()!=null) {
            deleteObjectRefContent(service.getStatus());
        }
        if (service.getVersionInfo()!=null) {
            deleteVersionInfoContent(service.getVersionInfo());
        }
        if (service.getSlots()!=null&&service.getSlots().size()>0) {
            for (Slot slot : service.getSlots()) {
                deleteSlotContent(slot);
            }
        }
        if (service.getClassifications()!=null&&service.getClassifications().size()>0) {
            for (Classification classification : service.getClassifications()) {
                deleteClassificationContent(classification);
            }
        }
        if (service.getExternalIdentifiers()!=null&&service.getExternalIdentifiers().size()>0) {
            for (ExternalIdentifier externalIdentifier : service.getExternalIdentifiers()) {
                deleteExternalIdentifierContent(externalIdentifier);
            }
        }
        if (service.getServiceBindings()!=null&&service.getServiceBindings().size()>0) {
            for (ServiceBinding serviceBinding : service.getServiceBindings()) {
                deleteServiceBindingContent(serviceBinding);
            }
        }
        int status = deleteServiceById(service.getId());
        return status;
    }

    public static int deleteServiceBindingContent(ServiceBinding serviceBinding) {
        if (serviceBinding.getDescription()!=null) {
            deleteInternationalStringContent(serviceBinding.getDescription());
        }
        if (serviceBinding.getName()!=null) {
            deleteInternationalStringContent(serviceBinding.getName());
        }
        if (serviceBinding.getObjectType()!=null) {
            deleteObjectRefContent(serviceBinding.getObjectType());
        }
        if (serviceBinding.getStatus()!=null) {
            deleteObjectRefContent(serviceBinding.getStatus());
        }
        if (serviceBinding.getService()!=null) {
            deleteObjectRefContent(serviceBinding.getService());
        }
        if (serviceBinding.getTargetBinding()!=null) {
            deleteObjectRefContent(serviceBinding.getTargetBinding());
        }
        if (serviceBinding.getVersionInfo()!=null) {
            deleteVersionInfoContent(serviceBinding.getVersionInfo());
        }
        if (serviceBinding.getClassifications()!=null&&serviceBinding.getClassifications().size()>0) {
            for (Classification classification : serviceBinding.getClassifications()) {
                deleteClassificationContent(classification);
            }
        }
        if (serviceBinding.getSlots()!=null&&serviceBinding.getSlots().size()>0) {
            for (Slot slot : serviceBinding.getSlots()) {
                deleteSlotContent(slot);
            }
        }
        if (serviceBinding.getExternalIdentifiers()!=null&&serviceBinding.getExternalIdentifiers().size()>0) {
            for (ExternalIdentifier externalIdentifier : serviceBinding.getExternalIdentifiers()) {
                deleteExternalIdentifierContent(externalIdentifier);
            }
        }
        if (serviceBinding.getSpecificationLinks()!=null&&serviceBinding.getSpecificationLinks().size()>0) {
            for (SpecificationLink specificationLink : serviceBinding.getSpecificationLinks()) {
                deleteSpecificationLinkContent(specificationLink);
            }
        }
        int status = deleteServiceBindingById(serviceBinding.getId());
        return status;
    }

    public static int deleteSpecificationLinkContent(SpecificationLink specificationLink) {
        if (specificationLink.getDescription()!=null) {
            deleteInternationalStringContent(specificationLink.getDescription());
        }
        if (specificationLink.getName()!=null) {
            deleteInternationalStringContent(specificationLink.getName());
        }
        if (specificationLink.getUsageDescription()!=null) {
            deleteInternationalStringContent(specificationLink.getUsageDescription());
        }
        if (specificationLink.getObjectType()!=null) {
            deleteObjectRefContent(specificationLink.getObjectType());
        }
        if (specificationLink.getStatus()!=null) {
            deleteObjectRefContent(specificationLink.getStatus());
        }
        if (specificationLink.getServiceBinding()!=null) {
            deleteObjectRefContent(specificationLink.getServiceBinding());
        }
        if (specificationLink.getSpecificationObject()!=null) {
            deleteObjectRefContent(specificationLink.getSpecificationObject());
        }
        if (specificationLink.getVersionInfo()!=null) {
            deleteVersionInfoContent(specificationLink.getVersionInfo());
        }
        if (specificationLink.getSlots()!=null&&specificationLink.getSlots().size()>0) {
            for (Slot slot : specificationLink.getSlots()) {
                deleteSlotContent(slot);
            }
        }
        if (specificationLink.getClassifications()!=null&&specificationLink.getClassifications().size()>0) {
            for (Classification classification : specificationLink.getClassifications()) {
                deleteClassificationContent(classification);
            }
        }
        if (specificationLink.getExternalIdentifiers()!=null&&specificationLink.getExternalIdentifiers().size()>0) {
            for (ExternalIdentifier externalIdentifier : specificationLink.getExternalIdentifiers()) {
                deleteExternalIdentifierContent(externalIdentifier);
            }
        }
        int status = deleteSpecificationLinkById(specificationLink.getId());
        return status;
    }

    public static int deleteAssociationContent(Association association) {
        if (association.getDescription()!=null) {
            deleteInternationalStringContent(association.getDescription());
        }
        if (association.getName()!=null) {
            deleteInternationalStringContent(association.getName());
        }
        if (association.getObjectType()!=null) {
            deleteObjectRefContent(association.getObjectType());
        }
        if (association.getStatus()!=null) {
            deleteObjectRefContent(association.getStatus());
        }
        if (association.getAssociationType()!=null) {
            deleteObjectRefContent(association.getAssociationType());
        }
        if (association.getSourceObject()!=null) {
            deleteObjectRefContent(association.getSourceObject());
        }
        if (association.getTargetObject()!=null) {
            deleteObjectRefContent(association.getTargetObject());
        }
        if (association.getVersionInfo()!=null) {
            deleteVersionInfoContent(association.getVersionInfo());
        }
        if (association.getSlots()!=null&&association.getSlots().size()>0) {
            for (Slot slot : association.getSlots()) {
                deleteSlotContent(slot);
            }
        }
        if (association.getClassifications()!=null&&association.getClassifications().size()>0) {
            for (Classification classification : association.getClassifications()) {
                deleteClassificationContent(classification);
            }
        }
        if (association.getExternalIdentifiers()!=null&&association.getExternalIdentifiers().size()>0) {
            for (ExternalIdentifier externalIdentifier : association.getExternalIdentifiers()) {
                deleteExternalIdentifierContent(externalIdentifier);
            }
        }
        int status = deleteAssociationById(association.getId());
        return status;
    }

    public static int deleteClassificationNodeContent(ClassificationNode classificationNode) {
        if (classificationNode.getDescription()!=null) {
            deleteInternationalStringContent(classificationNode.getDescription());
        }
        if (classificationNode.getName()!=null) {
            deleteInternationalStringContent(classificationNode.getName());
        }
        if (classificationNode.getObjectType()!=null) {
            deleteObjectRefContent(classificationNode.getObjectType());
        }
        if (classificationNode.getStatus()!=null) {
            deleteObjectRefContent(classificationNode.getStatus());
        }
        if (classificationNode.getParent()!=null) {
            deleteObjectRefContent(classificationNode.getParent());
        }
        if (classificationNode.getVersionInfo()!=null) {
            deleteVersionInfoContent(classificationNode.getVersionInfo());
        }
        if (classificationNode.getSlots()!=null&&classificationNode.getSlots().size()>0) {
            for (Slot slot : classificationNode.getSlots()) {
                deleteSlotContent(slot);
            }
        }
        if (classificationNode.getClassifications()!=null&&classificationNode.getClassifications().size()>0) {
            for (Classification classification : classificationNode.getClassifications()) {
                deleteClassificationContent(classification);
            }
        }
        if (classificationNode.getExternalIdentifiers()!=null&&classificationNode.getExternalIdentifiers().size()>0) {
            for (ExternalIdentifier externalIdentifier : classificationNode.getExternalIdentifiers()) {
                deleteExternalIdentifierContent(externalIdentifier);
            }
        }
        int status = deleteClassificationNodeById(classificationNode.getId());
        return status;
    }

    public static int deleteExtrinsicObjectContent(ExtrinsicObject extrinsicObject) {
        if (extrinsicObject.getDescription()!=null) {
            deleteInternationalStringContent(extrinsicObject.getDescription());
        }
        if (extrinsicObject.getName()!=null) {
            deleteInternationalStringContent(extrinsicObject.getName());
        }
        if (extrinsicObject.getContentVersionInfo()!=null) {
            deleteVersionInfoContent(extrinsicObject.getContentVersionInfo());
        }
        if (extrinsicObject.getObjectType()!=null) {
            deleteObjectRefContent(extrinsicObject.getObjectType());
        }
        if (extrinsicObject.getStatus()!=null) {
            deleteObjectRefContent(extrinsicObject.getStatus());
        }
        if (extrinsicObject.getVersionInfo()!=null) {
            deleteVersionInfoContent(extrinsicObject.getVersionInfo());
        }
        if (extrinsicObject.getSlots()!=null&&extrinsicObject.getSlots().size()>0) {
            for (Slot slot : extrinsicObject.getSlots()) {
                deleteSlotContent(slot);
            }
        }
        if (extrinsicObject.getClassifications()!=null) {
            for (Classification classification : extrinsicObject.getClassifications()) {
                deleteClassificationContent(classification);
            }
        }
        if (extrinsicObject.getExternalIdentifiers()!=null) {
            for (ExternalIdentifier externalIdentifier : extrinsicObject.getExternalIdentifiers()) {
                deleteExternalIdentifierContent(externalIdentifier);
            }
        }
        int status = deleteExtrinsicObjectById(extrinsicObject.getId());
        return status;
    }

    public static int deleteClassificationContent(Classification classification) {
        if (classification.getDescription()!=null) {
            deleteInternationalStringContent(classification.getDescription());
        }
        if (classification.getName()!=null) {
            deleteInternationalStringContent(classification.getName());
        }
        if (classification.getObjectType()!=null) {
            deleteObjectRefContent(classification.getObjectType());
        }
        if (classification.getStatus()!=null) {
            deleteObjectRefContent(classification.getStatus());
        }
        if (classification.getClassificationNode()!=null) {
            deleteObjectRefContent(classification.getClassificationNode());
        }
        if (classification.getClassificationScheme()!=null) {
            deleteObjectRefContent(classification.getClassificationScheme());
        }
        if (classification.getClassifiedObject()!=null) {
            deleteObjectRefContent(classification.getClassifiedObject());
        }
        if (classification.getVersionInfo()!=null) {
            deleteVersionInfoContent(classification.getVersionInfo());
        }
        if (classification.getSlots()!=null&&classification.getSlots().size()>0) {
            for (Slot slot : classification.getSlots()) {
                deleteSlotContent(slot);
            }
        }
        if (classification.getClassifications()!=null&&classification.getClassifications().size()>0) {
            for (Classification cf : classification.getClassifications()) {
                deleteClassificationContent(cf);
            }
        }
        if (classification.getExternalIdentifiers()!=null&&classification.getExternalIdentifiers().size()>0) {
            for (ExternalIdentifier externalIdentifier : classification.getExternalIdentifiers()) {
                deleteExternalIdentifierContent(externalIdentifier);
            }
        }
        int status = deleteClassificationById(classification.getId());
        return status;
    }

    public static int deleteExternalIdentifierContent(ExternalIdentifier externalIdentifier) {
        if (externalIdentifier.getDescription()!=null) {
            deleteInternationalStringContent(externalIdentifier.getDescription());
        }
        if (externalIdentifier.getName()!=null) {
            deleteInternationalStringContent(externalIdentifier.getName());
        }
        if (externalIdentifier.getObjectType()!=null) {
            deleteObjectRefContent(externalIdentifier.getObjectType());
        }
        if (externalIdentifier.getStatus()!=null) {
            deleteObjectRefContent(externalIdentifier.getStatus());
        }
        if (externalIdentifier.getRegistryObject()!=null) {
            deleteObjectRefContent(externalIdentifier.getRegistryObject());
        }
        if (externalIdentifier.getIdentificationScheme()!=null) {
            deleteObjectRefContent(externalIdentifier.getIdentificationScheme());
        }
        if (externalIdentifier.getVersionInfo()!=null) {
            deleteVersionInfoContent(externalIdentifier.getVersionInfo());
        }
        if (externalIdentifier.getSlots()!=null&&externalIdentifier.getSlots().size()>0) {
            for (Slot slot : externalIdentifier.getSlots()) {
                deleteSlotContent(slot);
            }
        }
        if (externalIdentifier.getClassifications()!=null&&externalIdentifier.getClassifications().size()>0) {
            for (Classification classification : externalIdentifier.getClassifications()) {
                deleteClassificationContent(classification);
            }
        }
        if (externalIdentifier.getExternalIdentifiers()!=null&&externalIdentifier.getExternalIdentifiers().size()>0) {
            for (ExternalIdentifier ei : externalIdentifier.getExternalIdentifiers()) {
                deleteExternalIdentifierContent(ei);
            }
        }
        int status = deleteExternalIdentifierById(externalIdentifier.getId());
        return status;
    }

    public static int deleteSlotContent(Slot slot) {
        int status = deleteSlotById(slot.getId());
        return status;
    }

    public static int deleteVersionInfoContent(VersionInfo versionInfo) {
        int status = deleteVersionInfoById(versionInfo.getId());
        return status;
    }

    public static int deleteObjectRefContent(ObjectRef objectRef) {
        int status = deleteObjectRefById(objectRef.getId());
        return status;
    }

    public static int deleteInternationalStringContent(InternationalString ins) {
        if (ins.getLocalizedStrings()!=null&&ins.getLocalizedStrings().size()>0) {
            for (LocalizedString localizedString : ins.getLocalizedStrings()) {
                deleteLocalizedStringContent(localizedString);
            }
        }
        int status = deleteInternationalStringById(ins.getId());
        return status;
    }

    public static int deleteLocalizedStringContent(LocalizedString localizedString) {
        int status = deleteLocalizedStringById(localizedString.getId());
        return status;
    }

    /******************************************************************************/

    public static int deleteRegistryPackageById(String id) {
       RegistryPackageDAO registryPackageDAO = SpringContextUtil.getBean(RegistryPackageDAO.class);
       int status = 0;
       try {
           status = registryPackageDAO.deleteRegistryPackageById(id);
       } catch (Exception e) {
           transactionStatus.rollbackToSavepoint(savePoint);
           e.printStackTrace();
       } finally {
           return status;
       }
    }

    public static int deleteIdentifiableById(String id) {
        IdentifiableDAO identifiableDAO = SpringContextUtil.getBean(IdentifiableDAO.class);
        int status = 0;
        try {
            status = identifiableDAO.deleteIdentifiableById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteRegistryById(String id) {
        RegistryDAO registryDAO = SpringContextUtil.getBean(RegistryDAO.class);
        int status = 0;
        try {
            status = registryDAO.deleteRegistryById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteNotificationById(String id) {
        NotificationDAO notificationDAO = SpringContextUtil.getBean(NotificationDAO.class);
        int status = 0;
        try {
            status = notificationDAO.deleteNotificationById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteUserById(String id) {
        UserDAO userDAO = SpringContextUtil.getBean(UserDAO.class);
        int status = 0;
        try {
            status = userDAO.deleteUserById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteFederationById(String id) {
        FederationDAO federationDAO = SpringContextUtil.getBean(FederationDAO.class);
        int status = 0;
        try {
            status = federationDAO.deleteFederationById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deletePersonById(String id) {
        PersonDAO personDAO = SpringContextUtil.getBean(PersonDAO.class);
        int status = 0;
        try {
            status = personDAO.deletePersonById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deletePersonNameById(String id) {
        PersonNameDAO personNameDAO = SpringContextUtil.getBean(PersonNameDAO.class);
        int status = 0;
        try {
            status = personNameDAO.deletePersonNameById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteOrganizationById(String id) {
        OrganizationDAO organizationDAO = SpringContextUtil.getBean(OrganizationDAO.class);
        int status = 0;
        try {
            status = organizationDAO.deleteOrganizationById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteTelephoneNumberById(String id) {
        TelephoneNumberDAO telephoneNumberDAO = SpringContextUtil.getBean(TelephoneNumberDAO.class);
        int status = 0;
        try {
            status = telephoneNumberDAO.deleteTelephoneNumberById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteEmailAddressById(String id) {
        EmailAddressDAO emailAddressDAO = SpringContextUtil.getBean(EmailAddressDAO.class);
        int status = 0;
        try {
            status = emailAddressDAO.deleteEmailAddressById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deletePostalAddressById(String id) {
        PostalAddressDAO postalAddressDAO = SpringContextUtil.getBean(PostalAddressDAO.class);
        int status = 0;
        try {
            status = postalAddressDAO.deletePostalAddressById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteServiceById(String id) {
        ServiceDAO serviceDAO = SpringContextUtil.getBean(ServiceDAO.class);
        int status = 0;
        try {
            status = serviceDAO.deleteServiceById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteServiceBindingById(String id) {
        ServiceBindingDAO serviceBindingDAO = SpringContextUtil.getBean(ServiceBindingDAO.class);
        int status = 0;
        try {
            status = serviceBindingDAO.deleteServiceBindingById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteSpecificationLinkById(String id) {
        SpecificationLinkDAO specificationLinkDAO = SpringContextUtil.getBean(SpecificationLinkDAO.class);
        int status = 0;
        try {
            status = specificationLinkDAO.deleteSpecificationLinkById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteAssociationById(String id) {
        AssociationDAO associationDAO = SpringContextUtil.getBean(AssociationDAO.class);
        int status = 0;
        try {
            status = associationDAO.deleteAssociationById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteClassificationNodeById(String id) {
        ClassificationNodeDAO classificationNodeDAO = SpringContextUtil.getBean(ClassificationNodeDAO.class);
        int status = 0;
        try {
            status = classificationNodeDAO.deleteClassificationNodeById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteExtrinsicObjectById(String id) {
        ExtrinsicObjectDAO extrinsicObjectDAO = SpringContextUtil.getBean(ExtrinsicObjectDAO.class);
        int status = 0;
        try {
            status = extrinsicObjectDAO.deleteExtrinsicObjectById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteClassificationById(String id) {
        ClassificationDAO classificationDAO = SpringContextUtil.getBean(ClassificationDAO.class);
        int status = 0;
        try {
            status = classificationDAO.deleteClassificationById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteExternalIdentifierById(String id) {
        ExternalIdentifierDAO externalIdentifierDAO = SpringContextUtil.getBean(ExternalIdentifierDAO.class);
        int status = 0;
        try {
            status = externalIdentifierDAO.deleteExternalIdentifierById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteSlotById(String id) {
        SlotDAO slotDAO = SpringContextUtil.getBean(SlotDAO.class);
        int status = 0;
        try {
            status = slotDAO.deleteSlotById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteVersionInfoById(String id) {
        VersionInfoDAO versionInfoDAO = SpringContextUtil.getBean(VersionInfoDAO.class);
        int status = 0;
        try {
            status = versionInfoDAO.deleteVersionInfoById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteObjectRefById(String id) {
        ObjectRefDAO objectRefDAO = SpringContextUtil.getBean(ObjectRefDAO.class);
        int status = 0;
        try {
            status = objectRefDAO.deleteObjectRefById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteInternationalStringById(String id) {
        InternationalStringDAO internationalStringDAO = SpringContextUtil.getBean(InternationalStringDAO.class);
        int status = 0;
        try {
            status = internationalStringDAO.deleteInternationalStringById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteLocalizedStringById(String id) {
        LocalizedStringDAO localizedStringDAO = SpringContextUtil.getBean(LocalizedStringDAO.class);
        int status = 0;
        try {
            status = localizedStringDAO.deleteLocalizedStringById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteActionById(int id) {
        _ActionDAO _actionDAO = SpringContextUtil.getBean(_ActionDAO.class);
        int status = 0;
        try {
            status = _actionDAO.deleteActionById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteActionGroupById(int id) {
        _ActionGroupDAO _actionGroupDAO = SpringContextUtil.getBean(_ActionGroupDAO.class);
        int status = 0;
        try {
            status = _actionGroupDAO.deleteActionGroupById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteGroupById(int id) {
        _GroupDAO _groupDAO = SpringContextUtil.getBean(_GroupDAO.class);
        int status = 0;
        try {
            status = _groupDAO.deleteGroupById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteMasterById(int id) {
        _MasterDAO _masterDAO = SpringContextUtil.getBean(_MasterDAO.class);
        int status = 0;
        try {
            status = _masterDAO.deleteMasterById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteMasterGroupById(int id) {
        _MasterGroupDAO _masterGroupDAO = SpringContextUtil.getBean(_MasterGroupDAO.class);
        int status = 0;
        try {
            status = _masterGroupDAO.deleteMasterGroupById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    public static int deleteDataInfoById(String id) {
        DataDAO dataDAO = SpringContextUtil.getBean(DataDAO.class);
        int status = 0;
        try {
            status = dataDAO.deleteDataById(id);
        } catch (Exception e) {
            transactionStatus.rollbackToSavepoint(savePoint);
            e.printStackTrace();
        } finally {
            return status;
        }
    }

    /******************************************************************************/
}
