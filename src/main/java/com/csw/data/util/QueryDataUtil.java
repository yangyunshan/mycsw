package com.csw.data.util;

import com.csw.privileage.util.*;
import com.csw.model.*;
import com.csw.dao.*;
import com.csw.privileage.util.Action;
import com.csw.spring.util.SpringContextUtil;

import java.util.ArrayList;
import java.util.List;

public class QueryDataUtil {

    public static List<RegistryPackage> queryRegistryPackageByType(String type) {
        List<RegistryPackage> registryPackages = selectRegistryPackageByType(type);
        if (registryPackages.size()>0) {
            for (RegistryPackage registryPackage : registryPackages) {
                String id = registryPackage.getId();
                if (registryPackage.getDescription()!=null) {
                    InternationalString description = queryInternationalStringById(registryPackage.getDescription().getId());
                    registryPackage.setDescription(description);
                }
                if (registryPackage.getName()!=null) {
                    InternationalString name = queryInternationalStringById(registryPackage.getName().getId());
                    registryPackage.setName(name);
                }
                if (registryPackage.getObjectType()!=null) {
                    ObjectRef objectType = queryObjectRefById(registryPackage.getObjectType().getId());
                    registryPackage.setObjectType(objectType);
                }
                if (registryPackage.getStatus()!=null) {
                    ObjectRef status = queryObjectRefById(registryPackage.getStatus().getId());
                    registryPackage.setStatus(status);
                }
                if (registryPackage.getVersionInfo()!=null) {
                    VersionInfo versionInfo = queryVersionInfoById(registryPackage.getVersionInfo().getId());
                    registryPackage.setVersionInfo(versionInfo);
                }
                List<Classification> classifications = queryClassificationsBy_Id(id);
                if (classifications!=null&&classifications.size()>0) {
                    for (Classification classification : classifications) {
                        registryPackage.getClassifications().add(classification);
                    }
                }
                List<ExternalIdentifier> externalIdentifiers = queryExternalIdentifiersBy_Id(id);
                if (externalIdentifiers!=null&&externalIdentifiers.size()>0) {
                    for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
                        registryPackage.getExternalIdentifiers().add(externalIdentifier);
                    }
                }
                List<Slot> slots = querySlotsBy_Id(id);
                if (slots!=null&&slots.size()>0) {
                    for (Slot slot : slots) {
                        registryPackage.getSlots().add(slot);
                    }
                }
                List<Identifiable> identifiables = queryIdentifiablesBy_Id(id);
                if (identifiables!=null&&identifiables.size()>0) {
                    for (Identifiable identifiable : identifiables) {
                        registryPackage.getRegistryObjectList().add(identifiable);
                    }
                }
            }
            return registryPackages;
        }
        return null;
    }

    public static List<RegistryPackage> queryRegistryPackageByOwner(String owner) {
        List<RegistryPackage> registryPackages = selectRegistryPackageByOwner(owner);
        if (registryPackages.size()>0) {
            for (RegistryPackage registryPackage : registryPackages) {
                String id = registryPackage.getId();
                if (registryPackage.getDescription()!=null) {
                    InternationalString description = queryInternationalStringById(registryPackage.getDescription().getId());
                    registryPackage.setDescription(description);
                }
                if (registryPackage.getName()!=null) {
                    InternationalString name = queryInternationalStringById(registryPackage.getName().getId());
                    registryPackage.setName(name);
                }
                if (registryPackage.getObjectType()!=null) {
                    ObjectRef objectType = queryObjectRefById(registryPackage.getObjectType().getId());
                    registryPackage.setObjectType(objectType);
                }
                if (registryPackage.getStatus()!=null) {
                    ObjectRef status = queryObjectRefById(registryPackage.getStatus().getId());
                    registryPackage.setStatus(status);
                }
                if (registryPackage.getVersionInfo()!=null) {
                    VersionInfo versionInfo = queryVersionInfoById(registryPackage.getVersionInfo().getId());
                    registryPackage.setVersionInfo(versionInfo);
                }
                List<Classification> classifications = queryClassificationsBy_Id(id);
                if (classifications!=null&&classifications.size()>0) {
                    for (Classification classification : classifications) {
                        registryPackage.getClassifications().add(classification);
                    }
                }
                List<ExternalIdentifier> externalIdentifiers = queryExternalIdentifiersBy_Id(id);
                if (externalIdentifiers!=null&&externalIdentifiers.size()>0) {
                    for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
                        registryPackage.getExternalIdentifiers().add(externalIdentifier);
                    }
                }
                List<Slot> slots = querySlotsBy_Id(id);
                if (slots!=null&&slots.size()>0) {
                    for (Slot slot : slots) {
                        registryPackage.getSlots().add(slot);
                    }
                }
                List<Identifiable> identifiables = queryIdentifiablesBy_Id(id);
                if (identifiables!=null&&identifiables.size()>0) {
                    for (Identifiable identifiable : identifiables) {
                        registryPackage.getRegistryObjectList().add(identifiable);
                    }
                }
            }
            return registryPackages;
        }
        return null;
    }

    public static List<RegistryPackage> queryAllRegistryPackage() {
        List<RegistryPackage> registryPackages = selectAllRegistryPackage();
        if (registryPackages.size()>0) {
            for (RegistryPackage registryPackage : registryPackages) {
                String id = registryPackage.getId();
                if (registryPackage.getDescription()!=null) {
                    InternationalString description = queryInternationalStringById(registryPackage.getDescription().getId());
                    registryPackage.setDescription(description);
                }
                if (registryPackage.getName()!=null) {
                    InternationalString name = queryInternationalStringById(registryPackage.getName().getId());
                    registryPackage.setName(name);
                }
                if (registryPackage.getObjectType()!=null) {
                    ObjectRef objectType = queryObjectRefById(registryPackage.getObjectType().getId());
                    registryPackage.setObjectType(objectType);
                }
                if (registryPackage.getStatus()!=null) {
                    ObjectRef status = queryObjectRefById(registryPackage.getStatus().getId());
                    registryPackage.setStatus(status);
                }
                if (registryPackage.getVersionInfo()!=null) {
                    VersionInfo versionInfo = queryVersionInfoById(registryPackage.getVersionInfo().getId());
                    registryPackage.setVersionInfo(versionInfo);
                }
                List<Classification> classifications = queryClassificationsBy_Id(id);
                if (classifications!=null&&classifications.size()>0) {
                    for (Classification classification : classifications) {
                        registryPackage.getClassifications().add(classification);
                    }
                }
                List<ExternalIdentifier> externalIdentifiers = queryExternalIdentifiersBy_Id(id);
                if (externalIdentifiers!=null&&externalIdentifiers.size()>0) {
                    for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
                        registryPackage.getExternalIdentifiers().add(externalIdentifier);
                    }
                }
                List<Slot> slots = querySlotsBy_Id(id);
                if (slots!=null&&slots.size()>0) {
                    for (Slot slot : slots) {
                        registryPackage.getSlots().add(slot);
                    }
                }
                List<Identifiable> identifiables = queryIdentifiablesBy_Id(id);
                if (identifiables!=null&&identifiables.size()>0) {
                    for (Identifiable identifiable : identifiables) {
                        registryPackage.getRegistryObjectList().add(identifiable);
                    }
                }
            }
            return registryPackages;
        }
        return null;
    }

    public static RegistryPackage queryRegistryPackageById(String id) {
        RegistryPackage registryPackage = null;
        List<RegistryPackage> registryPackages = selectRegistryPackageById(id);
        if (registryPackages!=null&&registryPackages.size()>0) {
            registryPackage = registryPackages.get(0);
            if (registryPackage.getDescription()!=null) {
                InternationalString description = queryInternationalStringById(registryPackage.getDescription().getId());
                registryPackage.setDescription(description);
            }
            if (registryPackage.getName()!=null) {
                InternationalString name = queryInternationalStringById(registryPackage.getName().getId());
                registryPackage.setName(name);
            }
            if (registryPackage.getObjectType()!=null) {
                ObjectRef objectType = queryObjectRefById(registryPackage.getObjectType().getId());
                registryPackage.setObjectType(objectType);
            }
            if (registryPackage.getStatus()!=null) {
                ObjectRef status = queryObjectRefById(registryPackage.getStatus().getId());
                registryPackage.setStatus(status);
            }
            if (registryPackage.getVersionInfo()!=null) {
                VersionInfo versionInfo = queryVersionInfoById(registryPackage.getVersionInfo().getId());
                registryPackage.setVersionInfo(versionInfo);
            }
            List<Classification> classifications = queryClassificationsBy_Id(id);
            if (classifications!=null&&classifications.size()>0) {
                for (Classification classification : classifications) {
                    registryPackage.getClassifications().add(classification);
                }
            }
            List<ExternalIdentifier> externalIdentifiers = queryExternalIdentifiersBy_Id(id);
            if (externalIdentifiers!=null&&externalIdentifiers.size()>0) {
                for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
                    registryPackage.getExternalIdentifiers().add(externalIdentifier);
                }
            }
            List<Slot> slots = querySlotsBy_Id(id);
            if (slots!=null&&slots.size()>0) {
                for (Slot slot : slots) {
                    registryPackage.getSlots().add(slot);
                }
            }
            List<Identifiable> identifiables = queryIdentifiablesBy_Id(id);
            if (identifiables!=null&&identifiables.size()>0) {
                for (Identifiable identifiable : identifiables) {
                    registryPackage.getRegistryObjectList().add(identifiable);
                }
            }
        }
        return registryPackage;
    }

    public static List<Classification> queryClassificationsBy_Id(String classificationId) {
        List<Classification> classifications = selectClassificationsBy_Id(classificationId);
        if (classifications!=null&&classifications.size()>0) {
            for (Classification c : classifications) {
                if (c.getDescription()!=null) {
                    InternationalString ins = queryInternationalStringById(c.getDescription().getId());
                    c.setDescription(ins);
                }
                if (c.getName()!=null) {
                    InternationalString ins = queryInternationalStringById(c.getName().getId());
                    c.setName(ins);
                }
                if (c.getObjectType()!=null) {
                    ObjectRef objectType = queryObjectRefById(c.getObjectType().getId());
                    c.setObjectType(objectType);
                }
                if (c.getClassificationNode()!=null) {
                    ObjectRef classificationNode = queryObjectRefById(c.getClassificationNode().getId());
                    c.setClassificationNode(classificationNode);
                }
                if (c.getClassificationScheme()!=null) {
                    ObjectRef classificationScheme = queryObjectRefById(c.getClassificationScheme().getId());
                    c.setClassificationScheme(classificationScheme);
                }
                if (c.getClassifiedObject()!=null) {
                    ObjectRef classifiedObject = queryObjectRefById(c.getClassifiedObject().getId());
                    c.setClassifiedObject(classifiedObject);
                }
                if (c.getStatus()!=null) {
                    ObjectRef status = queryObjectRefById(c.getStatus().getId());
                    c.setStatus(status);
                }
                if (c.getVersionInfo()!=null) {
                    VersionInfo versionInfo = queryVersionInfoById(c.getVersionInfo().getId());
                    c.setVersionInfo(versionInfo);
                }
                List<Classification> sfs = queryClassificationsBy_Id(c.getId());
                if (sfs!=null&&sfs.size()>0) {
                    for (Classification classification : sfs) {
                        c.getClassifications().add(classification);
                    }
                }
                List<ExternalIdentifier> eis = queryExternalIdentifiersBy_Id(c.getId());
                if (eis!=null&&eis.size()>0) {
                    for (ExternalIdentifier externalIdentifier : eis) {
                        c.getExternalIdentifiers().add(externalIdentifier);
                    }
                }
                List<Slot> slots = querySlotsBy_Id(c.getId());
                if (slots!=null&&slots.size()>0) {
                    for (Slot slot : slots) {
                        c.getSlots().add(slot);
                    }
                }
            }
            return classifications;
        }
        return null;
    }

    public static List<ExternalIdentifier> queryExternalIdentifiersBy_Id(String externalIdentifierId) {
        List<ExternalIdentifier> eis = selectExternalIdentifiersBy_Id(externalIdentifierId);
        if (eis!=null&&eis.size()>0) {
            for (ExternalIdentifier ei : eis) {
                if (ei.getDescription()!=null) {
                    InternationalString ins = queryInternationalStringById(ei.getDescription().getId());
                    ei.setDescription(ins);
                }
                if (ei.getName()!=null) {
                    InternationalString ins = queryInternationalStringById(ei.getName().getId());
                    ei.setName(ins);
                }
                if (ei.getObjectType()!=null) {
                    ObjectRef objectType = queryObjectRefById(ei.getObjectType().getId());
                    ei.setObjectType(objectType);
                }
                if (ei.getStatus()!=null) {
                    ObjectRef status = queryObjectRefById(ei.getStatus().getId());
                    ei.setStatus(status);
                }
                if (ei.getVersionInfo()!=null) {
                    VersionInfo versionInfo = queryVersionInfoById(ei.getVersionInfo().getId());
                    ei.setVersionInfo(versionInfo);
                }
                if (ei.getIdentificationScheme()!=null) {
                    ObjectRef identificationScheme = queryObjectRefById(ei.getIdentificationScheme().getId());
                    ei.setIdentificationScheme(identificationScheme);
                }
                if (ei.getRegistryObject()!=null) {
                    ObjectRef registryObject = queryObjectRefById(ei.getRegistryObject().getId());
                    ei.setRegistryObject(registryObject);
                }
                List<Slot> slots = querySlotsBy_Id(ei.getId());
                if (slots!=null&&slots.size()>0) {
                    for (Slot slot : slots) {
                        ei.getSlots().add(slot);
                    }
                }
                List<Classification> classifications = queryClassificationsBy_Id(ei.getId());
                if (classifications!=null&&classifications.size()>0) {
                    for (Classification classification : classifications) {
                        ei.getClassifications().add(classification);
                    }
                }
                List<ExternalIdentifier> externalIdentifiers = queryExternalIdentifiersBy_Id(ei.getId());
                if (externalIdentifiers!=null&&externalIdentifiers.size()>0) {
                    for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
                        ei.getExternalIdentifiers().add(externalIdentifier);
                    }
                }
            }
            return eis;
        }
        return null;
    }

    public static VersionInfo queryVersionInfoById(String id) {
        List<VersionInfo> versionInfos = selectVersionInfoById(id);
        VersionInfo versionInfo = null;
        if (versionInfos!=null&&versionInfos.size()>0) {
            versionInfo = versionInfos.get(0);
        }
        return versionInfo;
    }

    public static ObjectRef queryObjectRefById(String id) {
        List<ObjectRef> objectRefs = selectObjectRefById(id);
        ObjectRef objectRef = null;
        if (objectRefs!=null&&objectRefs.size()>0) {
            objectRef = objectRefs.get(0);
            List<Slot> slots = querySlotsBy_Id(id);
            if (slots!=null) {
                for (Slot slot : slots) {
                    objectRef.getSlots().add(slot);
                }
            }
        }
        return objectRef;
    }

    public static InternationalString queryInternationalStringById(String id) {
        List<InternationalString> inss = selectInternationalStringById(id);
        InternationalString internationalString = null;
        if (inss!=null&&inss.size()>0) {
            internationalString = inss.get(0);
            List<LocalizedString> localizedStrings = queryLocalizedStringsBy_Id(id);
            for (LocalizedString localizedString : localizedStrings) {
                internationalString.getLocalizedStrings().add(localizedString);
            }
        }
        return internationalString;
    }

    public static List<LocalizedString> queryLocalizedStringsBy_Id(String localizedStringId) {
        List<LocalizedString> localizedStrings = selectLocalizedStringsBy_Id(localizedStringId);
        if (localizedStrings.size()>0) {
            return localizedStrings;
        }
        return null;
    }

    public static Identifiable queryIdentifiableById(String id) {
        List<Identifiable> identifiables = selectIdentifiableById(id);
        if (identifiables!=null&&identifiables.size()>0) {
            for (Identifiable identifiable : identifiables) {
                List<Slot> slots = querySlotsBy_Id(identifiable.getId());
                for (Slot slot : slots) {
                    identifiable.getSlots().add(slot);
                }
            }
            return identifiables.get(0);
        }
        return null;
    }

    public static List<Identifiable> queryIdentifiablesBy_Id(String identifiableId) {
        List<Identifiable> identifiables = selectIdentifiablesBy_Id(identifiableId);
        if (identifiables!=null&&identifiables.size()>0) {
            for (Identifiable identifiable : identifiables) {
                List<Slot> slots = querySlotsBy_Id(identifiable.getId());
                for (Slot slot : slots) {
                    identifiable.getSlots().add(slot);
                }
            }
            return identifiables;
        }
        return null;
    }

    public static List<Slot> querySlotsBy_Id(String slotId) {
        List<Slot> slots = selectSlotsBy_Id(slotId);
        if (slots.size()>0) {
            return slots;
        }
        return null;
    }

    public static List<ExtrinsicObject> queryAllExtrinsicObjects() {
        List<ExtrinsicObject> extrinsicObjects = selectAllExtrinsicObjects();
        if (extrinsicObjects!=null&&extrinsicObjects.size()>0) {
            for (ExtrinsicObject extrinsicObject : extrinsicObjects) {
                if (extrinsicObject.getDescription()!=null) {
                    InternationalString ins = queryInternationalStringById(extrinsicObject.getDescription().getId());
                    extrinsicObject.setDescription(ins);
                }
                if (extrinsicObject.getName()!=null) {
                    InternationalString ins = queryInternationalStringById(extrinsicObject.getName().getId());
                    extrinsicObject.setName(ins);
                }
                if (extrinsicObject.getObjectType()!=null) {
                    ObjectRef objectType = queryObjectRefById(extrinsicObject.getObjectType().getId());
                    extrinsicObject.setObjectType(objectType);
                }
                if (extrinsicObject.getStatus()!=null) {
                    ObjectRef status = queryObjectRefById(extrinsicObject.getStatus().getId());
                    extrinsicObject.setStatus(status);
                }
                if (extrinsicObject.getVersionInfo()!=null) {
                    VersionInfo versionInfo = queryVersionInfoById(extrinsicObject.getVersionInfo().getId());
                    extrinsicObject.setVersionInfo(versionInfo);
                }
                if (extrinsicObject.getContentVersionInfo()!=null) {
                    VersionInfo contentVersionInfo = queryVersionInfoById(extrinsicObject.getContentVersionInfo().getId());
                    extrinsicObject.setContentVersionInfo(contentVersionInfo);
                }
                List<Classification> classifications = queryClassificationsBy_Id(extrinsicObject.getId());
                if (classifications!=null&&classifications.size()>0) {
                    for (Classification classification : classifications) {
                        extrinsicObject.getClassifications().add(classification);
                    }
                }
                List<ExternalIdentifier> externalIdentifiers = queryExternalIdentifiersBy_Id(extrinsicObject.getId());
                if (externalIdentifiers!=null&&externalIdentifiers.size()>0) {
                    for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
                        extrinsicObject.getExternalIdentifiers().add(externalIdentifier);
                    }
                }
                List<Slot> slots = querySlotsBy_Id(extrinsicObject.getId());
                if (slots!=null&&slots.size()>0) {
                    for (Slot slot : slots) {
                        extrinsicObject.getSlots().add(slot);
                    }
                }
            }
            return extrinsicObjects;
        }
        return null;
    }

    public static ExtrinsicObject queryExtrinsicObjectById(String id) {
        List<ExtrinsicObject> extrinsicObjects = selectExtrinsicObjectById(id);
        if (extrinsicObjects!=null&&extrinsicObjects.size()>0) {
            for (ExtrinsicObject extrinsicObject : extrinsicObjects) {
                if (extrinsicObject.getDescription()!=null) {
                    InternationalString ins = queryInternationalStringById(extrinsicObject.getDescription().getId());
                    extrinsicObject.setDescription(ins);
                }
                if (extrinsicObject.getName()!=null) {
                    InternationalString ins = queryInternationalStringById(extrinsicObject.getName().getId());
                    extrinsicObject.setName(ins);
                }
                if (extrinsicObject.getObjectType()!=null) {
                    ObjectRef objectType = queryObjectRefById(extrinsicObject.getObjectType().getId());
                    extrinsicObject.setObjectType(objectType);
                }
                if (extrinsicObject.getStatus()!=null) {
                    ObjectRef status = queryObjectRefById(extrinsicObject.getStatus().getId());
                    extrinsicObject.setStatus(status);
                }
                if (extrinsicObject.getVersionInfo()!=null) {
                    VersionInfo versionInfo = queryVersionInfoById(extrinsicObject.getVersionInfo().getId());
                    extrinsicObject.setVersionInfo(versionInfo);
                }
                if (extrinsicObject.getContentVersionInfo()!=null) {
                    VersionInfo contentVersionInfo = queryVersionInfoById(extrinsicObject.getContentVersionInfo().getId());
                    extrinsicObject.setContentVersionInfo(contentVersionInfo);
                }
                List<Classification> classifications = queryClassificationsBy_Id(extrinsicObject.getId());
                if (classifications!=null&&classifications.size()>0) {
                    for (Classification classification : classifications) {
                        extrinsicObject.getClassifications().add(classification);
                    }
                }
                List<ExternalIdentifier> externalIdentifiers = queryExternalIdentifiersBy_Id(extrinsicObject.getId());
                if (externalIdentifiers!=null&&externalIdentifiers.size()>0) {
                    for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
                        extrinsicObject.getExternalIdentifiers().add(externalIdentifier);
                    }
                }
                List<Slot> slots = querySlotsBy_Id(extrinsicObject.getId());
                if (slots!=null&&slots.size()>0) {
                    for (Slot slot : slots) {
                        extrinsicObject.getSlots().add(slot);
                    }
                }
            }
            return extrinsicObjects.get(0);
        }
        return null;
    }

    public static Service queryServiceById(String id) {
        List<Service> services = selectServiceById(id);
        if (services!=null&&services.size()>0) {
            for (Service service : services) {
                if (service.getDescription()!=null) {
                    InternationalString ins = queryInternationalStringById(service.getDescription().getId());
                    service.setDescription(ins);
                }
                if (service.getName()!=null) {
                    InternationalString ins = queryInternationalStringById(service.getName().getId());
                    service.setName(ins);
                }
                if (service.getObjectType()!=null) {
                    ObjectRef objectType = queryObjectRefById(service.getObjectType().getId());
                    service.setObjectType(objectType);
                }
                if (service.getStatus()!=null) {
                    ObjectRef status = queryObjectRefById(service.getStatus().getId());
                    service.setStatus(status);
                }
                if (service.getVersionInfo()!=null) {
                    VersionInfo versionInfo = queryVersionInfoById(service.getVersionInfo().getId());
                    service.setVersionInfo(versionInfo);
                }
                List<Slot> slots = querySlotsBy_Id(service.getId());
                if (slots!=null&&slots.size()>0) {
                    for (Slot slot : slots) {
                        service.getSlots().add(slot);
                    }
                }
                List<Classification> classifications = queryClassificationsBy_Id(service.getId());
                if (classifications!=null&&classifications.size()>0) {
                    for (Classification classification : classifications) {
                        service.getClassifications().add(classification);
                    }
                }
                List<ExternalIdentifier> externalIdentifiers = queryExternalIdentifiersBy_Id(service.getId());
                if (externalIdentifiers!=null&&externalIdentifiers.size()>0) {
                    for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
                        service.getExternalIdentifiers().add(externalIdentifier);
                    }
                }
                List<ServiceBinding> serviceBindings = queryServiceBindingsBy_Id(service.getId());
                if (serviceBindings!=null&&serviceBindings.size()>0) {
                    for (ServiceBinding serviceBinding : serviceBindings) {
                        service.getServiceBindings().add(serviceBinding);
                    }
                }
            }
            return services.get(0);
        }
        return null;
    }

    public static List<ServiceBinding> queryServiceBindingsBy_Id(String serviceBindingId) {
        List<ServiceBinding> serviceBindings = selectServiceBindingsBy_Id(serviceBindingId);
        if (serviceBindings!=null&&serviceBindings.size()>0) {
            for (ServiceBinding serviceBinding : serviceBindings) {
                if (serviceBinding.getDescription()!=null) {
                    InternationalString ins = queryInternationalStringById(serviceBinding.getDescription().getId());
                    serviceBinding.setDescription(ins);
                }
                if (serviceBinding.getName()!=null) {
                    InternationalString ins = queryInternationalStringById(serviceBinding.getName().getId());
                    serviceBinding.setName(ins);
                }
                if (serviceBinding.getObjectType()!=null) {
                    ObjectRef objectType = queryObjectRefById(serviceBinding.getObjectType().getId());
                    serviceBinding.setObjectType(objectType);
                }
                if (serviceBinding.getStatus()!=null) {
                    ObjectRef status = queryObjectRefById(serviceBinding.getStatus().getId());
                    serviceBinding.setStatus(status);
                }
                if (serviceBinding.getVersionInfo()!=null) {
                    VersionInfo versionInfo = queryVersionInfoById(serviceBinding.getVersionInfo().getId());
                    serviceBinding.setVersionInfo(versionInfo);
                }
                if (serviceBinding.getTargetBinding()!=null) {
                    ObjectRef targetBinding = queryObjectRefById(serviceBinding.getTargetBinding().getId());
                    serviceBinding.setTargetBinding(targetBinding);
                }
                if (serviceBinding.getService()!=null) {
                    ObjectRef service = queryObjectRefById(serviceBinding.getService().getId());
                    serviceBinding.setService(service);
                }
                List<Classification> classifications = queryClassificationsBy_Id(serviceBinding.getId());
                if (classifications!=null&&classifications.size()>0) {
                    for (Classification classification : classifications) {
                        serviceBinding.getClassifications().add(classification);
                    }
                }
                List<ExternalIdentifier> externalIdentifiers = queryExternalIdentifiersBy_Id(serviceBinding.getId());
                if (externalIdentifiers!=null&&externalIdentifiers.size()>0) {
                    for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
                        serviceBinding.getExternalIdentifiers().add(externalIdentifier);
                    }
                }
                List<Slot> slots = querySlotsBy_Id(serviceBinding.getId());
                if (slots!=null&&slots.size()>0) {
                    for (Slot slot : slots) {
                        serviceBinding.getSlots().add(slot);
                    }
                }
                List<SpecificationLink> specificationLinks = querySpecificationLinksBy_Id(serviceBinding.getId());
                if (specificationLinks!=null&&specificationLinks.size()>0) {
                    for (SpecificationLink specificationLink : specificationLinks) {
                        serviceBinding.getSpecificationLinks().add(specificationLink);
                    }
                }
            }
            return serviceBindings;
        }
        return null;
    }

    public static List<SpecificationLink> querySpecificationLinksBy_Id(String specificationLinkId) {
        List<SpecificationLink> specificationLinks = selectSpecificationLinksBy_Id(specificationLinkId);
        if (specificationLinks!=null&&specificationLinks.size()>0) {
            for (SpecificationLink specificationLink : specificationLinks) {
                if (specificationLink.getDescription()!=null) {
                    InternationalString ins = queryInternationalStringById(specificationLink.getDescription().getId());
                    specificationLink.setDescription(ins);
                }
                if (specificationLink.getName()!=null) {
                    InternationalString ins = queryInternationalStringById(specificationLink.getName().getId());
                    specificationLink.setName(ins);
                }
                if (specificationLink.getObjectType()!=null) {
                    ObjectRef objectType = queryObjectRefById(specificationLink.getObjectType().getId());
                    specificationLink.setObjectType(objectType);
                }
                if (specificationLink.getStatus()!=null) {
                    ObjectRef status = queryObjectRefById(specificationLink.getStatus().getId());
                    specificationLink.setStatus(status);
                }
                if (specificationLink.getVersionInfo()!=null) {
                    VersionInfo versionInfo = queryVersionInfoById(specificationLink.getVersionInfo().getId());
                    specificationLink.setVersionInfo(versionInfo);
                }
                if (specificationLink.getUsageDescription()!=null) {
                    InternationalString ins = queryInternationalStringById(specificationLink.getUsageDescription().getId());
                    specificationLink.setUsageDescription(ins);
                }
                if (specificationLink.getServiceBinding()!=null) {
                    ObjectRef serviceBinding = queryObjectRefById(specificationLink.getServiceBinding().getId());
                    specificationLink.setServiceBinding(serviceBinding);
                }
                if (specificationLink.getSpecificationObject()!=null) {
                    ObjectRef specificationObject = queryObjectRefById(specificationLink.getSpecificationObject().getId());
                    specificationLink.setSpecificationObject(specificationObject);
                }
                List<Classification> classifications = queryClassificationsBy_Id(specificationLink.getId());
                if (classifications!=null&&classifications.size()>0) {
                    for (Classification classification : classifications) {
                        specificationLink.getClassifications().add(classification);
                    }
                }
                List<ExternalIdentifier> externalIdentifiers = queryExternalIdentifiersBy_Id(specificationLink.getId());
                if (externalIdentifiers!=null&&externalIdentifiers.size()>0) {
                    for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
                        specificationLink.getExternalIdentifiers().add(externalIdentifier);
                    }
                }
                List<Slot> slots = querySlotsBy_Id(specificationLink.getId());
                if (slots!=null&&slots.size()>0) {
                    for (Slot slot : slots) {
                        specificationLink.getSlots().add(slot);
                    }
                }
            }
            return specificationLinks;
        }
        return null;
    }

    public static ClassificationNode queryClassificationNodesById(String id) {
        List<ClassificationNode> classificationNodes = selectClassificationNodeById(id);
        if (classificationNodes!=null&&classificationNodes.size()>0) {
            for (ClassificationNode classificationNode : classificationNodes) {
                if (classificationNode.getDescription()!=null) {
                    InternationalString ins = queryInternationalStringById(classificationNode.getDescription().getId());
                    classificationNode.setDescription(ins);
                }
                if (classificationNode.getName()!=null) {
                    InternationalString ins = queryInternationalStringById(classificationNode.getName().getId());
                    classificationNode.setName(ins);
                }
                if (classificationNode.getObjectType()!=null) {
                    ObjectRef objectType = queryObjectRefById(classificationNode.getObjectType().getId());
                    classificationNode.setObjectType(objectType);
                }
                if (classificationNode.getStatus()!=null) {
                    ObjectRef status = queryObjectRefById(classificationNode.getStatus().getId());
                    classificationNode.setStatus(status);
                }
                if (classificationNode.getVersionInfo()!=null) {
                    VersionInfo versionInfo = queryVersionInfoById(classificationNode.getVersionInfo().getId());
                    classificationNode.setVersionInfo(versionInfo);
                }
                if (classificationNode.getParent()!=null) {
                    ObjectRef parent = queryObjectRefById(classificationNode.getParent().getId());
                    classificationNode.setParent(parent);
                }
                List<Classification> classifications = queryClassificationsBy_Id(classificationNode.getId());
                if (classifications!=null&&classifications.size()>0) {
                    for (Classification classification : classifications) {
                        classificationNode.getClassifications().add(classification);
                    }
                }
                List<ExternalIdentifier> externalIdentifiers = queryExternalIdentifiersBy_Id(classificationNode.getId());
                if (externalIdentifiers!=null&&externalIdentifiers.size()>0) {
                    for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
                        classificationNode.getExternalIdentifiers().add(externalIdentifier);
                    }
                }
                List<Slot> slots = querySlotsBy_Id(classificationNode.getId());
                if (slots!=null&&slots.size()>0) {
                    for (Slot slot : slots) {
                        classificationNode.getSlots().add(slot);
                    }
                }
            }
            return classificationNodes.get(0);
        }
        return null;
    }

    public static Association queryAssociationById(String id) {
        List<Association> associations = selectAssociationById(id);
        if (associations!=null&&associations.size()>0) {
            for (Association association : associations) {
                if (association.getDescription()!=null) {
                    InternationalString ins = queryInternationalStringById(association.getDescription().getId());
                    association.setDescription(ins);
                }
                if (association.getName()!=null) {
                    InternationalString ins = queryInternationalStringById(association.getName().getId());
                    association.setName(ins);
                }
                if (association.getObjectType()!=null) {
                    ObjectRef objectType = queryObjectRefById(association.getObjectType().getId());
                    association.setObjectType(objectType);
                }
                if (association.getStatus()!=null) {
                    ObjectRef status = queryObjectRefById(association.getStatus().getId());
                    association.setStatus(status);
                }
                if (association.getVersionInfo()!=null) {
                    VersionInfo versionInfo = queryVersionInfoById(association.getVersionInfo().getId());
                    association.setVersionInfo(versionInfo);
                }
                if (association.getSourceObject()!=null) {
                    ObjectRef sourceObject = queryObjectRefById(association.getSourceObject().getId());
                    association.setSourceObject(sourceObject);
                }
                if (association.getTargetObject()!=null) {
                    ObjectRef targetObject = queryObjectRefById(association.getTargetObject().getId());
                    association.setTargetObject(targetObject);
                }
                if (association.getAssociationType()!=null) {
                    ObjectRef associationType = queryObjectRefById(association.getAssociationType().getId());
                    association.setAssociationType(associationType);
                }
                List<Classification> classifications = queryClassificationsBy_Id(association.getId());
                if (classifications!=null&&classifications.size()>0) {
                    for (Classification classification : classifications) {
                        association.getClassifications().add(classification);
                    }
                }
                List<ExternalIdentifier> externalIdentifiers = queryExternalIdentifiersBy_Id(association.getId());
                if (externalIdentifiers!=null&&externalIdentifiers.size()>0) {
                    for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
                        association.getExternalIdentifiers().add(externalIdentifier);
                    }
                }
                List<Slot> slots = querySlotsBy_Id(association.getId());
                if (slots!=null&&slots.size()>0) {
                    for (Slot slot : slots) {
                        association.getSlots().add(slot);
                    }
                }
            }
            return associations.get(0);
        }
        return null;
    }

    public static Organization queryOrganizationById(String id) {
        List<Organization> organizations = selectOrganizationById(id);
        if (organizations!=null&&organizations.size()>0) {
            for (Organization organization : organizations) {
                if (organization.getDescription()!=null) {
                    InternationalString ins = queryInternationalStringById(organization.getDescription().getId());
                    organization.setDescription(ins);
                }
                if (organization.getName()!=null) {
                    InternationalString ins = queryInternationalStringById(organization.getName().getId());
                    organization.setName(ins);
                }
                if (organization.getObjectType()!=null) {
                    ObjectRef objectType = queryObjectRefById(organization.getObjectType().getId());
                    organization.setObjectType(objectType);
                }
                if (organization.getStatus()!=null) {
                    ObjectRef status = queryObjectRefById(organization.getStatus().getId());
                    organization.setStatus(status);
                }
                if (organization.getVersionInfo()!=null) {
                    VersionInfo versionInfo = queryVersionInfoById(organization.getVersionInfo().getId());
                    organization.setVersionInfo(versionInfo);
                }
                if (organization.getPrimaryContact()!=null) {
                    ObjectRef primaryContact = queryObjectRefById(organization.getPrimaryContact().getId());
                    organization.setPrimaryContact(primaryContact);
                }
                if (organization.getParent()!=null) {
                    ObjectRef parent = queryObjectRefById(organization.getParent().getId());
                    organization.setParent(parent);
                }
                List<Classification> classifications = queryClassificationsBy_Id(organization.getId());
                if (classifications!=null&&classifications.size()>0) {
                    for (Classification classification : classifications) {
                        organization.getClassifications().add(classification);
                    }
                }
                List<ExternalIdentifier> externalIdentifiers = queryExternalIdentifiersBy_Id(organization.getId());
                if (externalIdentifiers!=null&&externalIdentifiers.size()>0) {
                    for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
                        organization.getExternalIdentifiers().add(externalIdentifier);
                    }
                }
                List<Slot> slots = querySlotsBy_Id(organization.getId());
                if (slots!=null&&slots.size()>0) {
                    for (Slot slot : slots) {
                        organization.getSlots().add(slot);
                    }
                }
                List<PostalAddress> addresses = queryPostalAddressesBy_Id(organization.getId());
                if (addresses!=null&&addresses.size()>0) {
                    for (PostalAddress address : addresses) {
                        organization.getAddresses().add(address);
                    }
                }
                List<EmailAddress> emailAddresses = queryEmailAddressesBy_Id(organization.getId());
                if (emailAddresses!=null&&emailAddresses.size()>0) {
                    for (EmailAddress emailAddress : emailAddresses) {
                        organization.getEmailAddresses().add(emailAddress);
                    }
                }
                List<TelephoneNumber> telephoneNumbers = queryTelephoneNumbersBy_Id(organization.getId());
                if (telephoneNumbers!=null&&telephoneNumbers.size()>0) {
                    for (TelephoneNumber telephoneNumber : telephoneNumbers) {
                        organization.getTelephoneNumbers().add(telephoneNumber);
                    }
                }
            }
            return organizations.get(0);
        }
        return null;
    }

    public static List<Organization> queryAllOrganizations() {
        List<Organization> organizations = selectAllOrganizations();
        if (organizations!=null&&organizations.size()>0) {
            for (Organization organization : organizations) {
                if (organization.getDescription()!=null) {
                    InternationalString ins = queryInternationalStringById(organization.getDescription().getId());
                    organization.setDescription(ins);
                }
                if (organization.getName()!=null) {
                    InternationalString ins = queryInternationalStringById(organization.getName().getId());
                    organization.setName(ins);
                }
                if (organization.getObjectType()!=null) {
                    ObjectRef objectType = queryObjectRefById(organization.getObjectType().getId());
                    organization.setObjectType(objectType);
                }
                if (organization.getStatus()!=null) {
                    ObjectRef status = queryObjectRefById(organization.getStatus().getId());
                    organization.setStatus(status);
                }
                if (organization.getVersionInfo()!=null) {
                    VersionInfo versionInfo = queryVersionInfoById(organization.getVersionInfo().getId());
                    organization.setVersionInfo(versionInfo);
                }
                if (organization.getPrimaryContact()!=null) {
                    ObjectRef primaryContact = queryObjectRefById(organization.getPrimaryContact().getId());
                    organization.setPrimaryContact(primaryContact);
                }
                if (organization.getParent()!=null) {
                    ObjectRef parent = queryObjectRefById(organization.getParent().getId());
                    organization.setParent(parent);
                }
                List<Classification> classifications = queryClassificationsBy_Id(organization.getId());
                if (classifications!=null&&classifications.size()>0) {
                    for (Classification classification : classifications) {
                        organization.getClassifications().add(classification);
                    }
                }
                List<ExternalIdentifier> externalIdentifiers = queryExternalIdentifiersBy_Id(organization.getId());
                if (externalIdentifiers!=null&&externalIdentifiers.size()>0) {
                    for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
                        organization.getExternalIdentifiers().add(externalIdentifier);
                    }
                }
                List<Slot> slots = querySlotsBy_Id(organization.getId());
                if (slots!=null&&slots.size()>0) {
                    for (Slot slot : slots) {
                        organization.getSlots().add(slot);
                    }
                }
                List<PostalAddress> addresses = queryPostalAddressesBy_Id(organization.getId());
                if (addresses!=null&&addresses.size()>0) {
                    for (PostalAddress address : addresses) {
                        organization.getAddresses().add(address);
                    }
                }
                List<EmailAddress> emailAddresses = queryEmailAddressesBy_Id(organization.getId());
                if (emailAddresses!=null&&emailAddresses.size()>0) {
                    for (EmailAddress emailAddress : emailAddresses) {
                        organization.getEmailAddresses().add(emailAddress);
                    }
                }
                List<TelephoneNumber> telephoneNumbers = queryTelephoneNumbersBy_Id(organization.getId());
                if (telephoneNumbers!=null&&telephoneNumbers.size()>0) {
                    for (TelephoneNumber telephoneNumber : telephoneNumbers) {
                        organization.getTelephoneNumbers().add(telephoneNumber);
                    }
                }
            }
            return organizations;
        }
        return null;
    }

    public static List<TelephoneNumber> queryTelephoneNumbersBy_Id(String telephoneNumberId) {
        List<TelephoneNumber> telephoneNumbers = selectTelephoneNumbersBy_Id(telephoneNumberId);
        if (telephoneNumbers!=null&&telephoneNumbers.size()>0) {
            for (TelephoneNumber telephoneNumber : telephoneNumbers) {
                if (telephoneNumber.getPhoneType()!=null) {
                    ObjectRef phoneType = queryObjectRefById(telephoneNumber.getPhoneType().getId());
                    telephoneNumber.setPhoneType(phoneType);
                }
            }
            return telephoneNumbers;
        }
        return null;
    }

    public static List<EmailAddress> queryEmailAddressesBy_Id(String emailAddressId) {
        List<EmailAddress> emailAddresses = selectEmailAddressesBy_Id(emailAddressId);
        if (emailAddresses!=null&&emailAddresses.size()>0) {
            for (EmailAddress emailAddress : emailAddresses) {
                if (emailAddress.getType()!=null) {
                    ObjectRef type = queryObjectRefById(emailAddress.getType().getId());
                    emailAddress.setType(type);
                }
            }
            return emailAddresses;
        }
        return null;
    }

    public static List<PostalAddress> queryPostalAddressesBy_Id(String postalAddressId) {
        List<PostalAddress> addresses = selectPostalAddressesBy_Id(postalAddressId);
        if (addresses!=null&&addresses.size()>0) {
            for (PostalAddress address : addresses) {
                List<Slot> slots = querySlotsBy_Id(address.getId());
                if (slots!=null&&slots.size()>0) {
                    for (Slot slot : slots) {
                        address.getSlots().add(slot);
                    }
                }
            }
            return addresses;
        }
        return null;
    }

    public static Person queryPersonById(String id) {
        List<Person> persons = selectPersonById(id);
        if (persons!=null&&persons.size()>0) {
            for (Person person : persons) {
                if (person.getDescription()!=null) {
                    InternationalString ins = queryInternationalStringById(person.getDescription().getId());
                    person.setDescription(ins);
                }
                if (person.getName()!=null) {
                    InternationalString ins = queryInternationalStringById(person.getName().getId());
                    person.setName(ins);
                }
                if (person.getObjectType()!=null) {
                    ObjectRef objectType = queryObjectRefById(person.getObjectType().getId());
                    person.setObjectType(objectType);
                }
                if (person.getStatus()!=null) {
                    ObjectRef status = queryObjectRefById(person.getStatus().getId());
                    person.setStatus(status);
                }
                if (person.getVersionInfo()!=null) {
                    VersionInfo versionInfo = queryVersionInfoById(person.getVersionInfo().getId());
                    person.setVersionInfo(versionInfo);
                }
                if (person.getPersonName()!=null) {
                    PersonName personName = queryPersonName(person.getPersonName().getId());
                    person.setPersonName(personName);
                }
                List<Classification> classifications = queryClassificationsBy_Id(person.getId());
                if (classifications!=null&&classifications.size()>0) {
                    for (Classification classification : classifications) {
                        person.getClassifications().add(classification);
                    }
                }
                List<ExternalIdentifier> externalIdentifiers = queryExternalIdentifiersBy_Id(person.getId());
                if (externalIdentifiers!=null&&externalIdentifiers.size()>0) {
                    for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
                        person.getExternalIdentifiers().add(externalIdentifier);
                    }
                }
                List<Slot> slots = querySlotsBy_Id(person.getId());
                if (slots!=null&&slots.size()>0) {
                    for (Slot slot : slots) {
                        person.getSlots().add(slot);
                    }
                }
                List<PostalAddress> addresses = queryPostalAddressesBy_Id(person.getId());
                if (addresses!=null&&addresses.size()>0) {
                    for (PostalAddress address : addresses) {
                        person.getAddresses().add(address);
                    }
                }
                List<EmailAddress> emailAddresses = queryEmailAddressesBy_Id(person.getId());
                if (emailAddresses!=null&&emailAddresses.size()>0) {
                    for (EmailAddress emailAddress : emailAddresses) {
                        person.getEmailAddresses().add(emailAddress);
                    }
                }
                List<TelephoneNumber> telephoneNumbers = queryTelephoneNumbersBy_Id(person.getId());
                if (telephoneNumbers!=null&&telephoneNumbers.size()>0) {
                    for (TelephoneNumber telephoneNumber : telephoneNumbers) {
                        person.getTelephoneNumbers().add(telephoneNumber);
                    }
                }
            }
            return persons.get(0);
        }
        return null;
    }

    public static List<Person> queryAllPersons() {
        List<Person> persons = selectAllPersons();
        if (persons!=null&&persons.size()>0) {
            for (Person person : persons) {
                if (person.getDescription()!=null) {
                    InternationalString ins = queryInternationalStringById(person.getDescription().getId());
                    person.setDescription(ins);
                }
                if (person.getName()!=null) {
                    InternationalString ins = queryInternationalStringById(person.getName().getId());
                    person.setName(ins);
                }
                if (person.getObjectType()!=null) {
                    ObjectRef objectType = queryObjectRefById(person.getObjectType().getId());
                    person.setObjectType(objectType);
                }
                if (person.getStatus()!=null) {
                    ObjectRef status = queryObjectRefById(person.getStatus().getId());
                    person.setStatus(status);
                }
                if (person.getVersionInfo()!=null) {
                    VersionInfo versionInfo = queryVersionInfoById(person.getVersionInfo().getId());
                    person.setVersionInfo(versionInfo);
                }
                if (person.getPersonName()!=null) {
                    PersonName personName = queryPersonName(person.getPersonName().getId());
                    person.setPersonName(personName);
                }
                List<Classification> classifications = queryClassificationsBy_Id(person.getId());
                if (classifications!=null&&classifications.size()>0) {
                    for (Classification classification : classifications) {
                        person.getClassifications().add(classification);
                    }
                }
                List<ExternalIdentifier> externalIdentifiers = queryExternalIdentifiersBy_Id(person.getId());
                if (externalIdentifiers!=null&&externalIdentifiers.size()>0) {
                    for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
                        person.getExternalIdentifiers().add(externalIdentifier);
                    }
                }
                List<Slot> slots = querySlotsBy_Id(person.getId());
                if (slots!=null&&slots.size()>0) {
                    for (Slot slot : slots) {
                        person.getSlots().add(slot);
                    }
                }
                List<PostalAddress> addresses = queryPostalAddressesBy_Id(person.getId());
                if (addresses!=null&&addresses.size()>0) {
                    for (PostalAddress address : addresses) {
                        person.getAddresses().add(address);
                    }
                }
                List<EmailAddress> emailAddresses = queryEmailAddressesBy_Id(person.getId());
                if (emailAddresses!=null&&emailAddresses.size()>0) {
                    for (EmailAddress emailAddress : emailAddresses) {
                        person.getEmailAddresses().add(emailAddress);
                    }
                }
                List<TelephoneNumber> telephoneNumbers = queryTelephoneNumbersBy_Id(person.getId());
                if (telephoneNumbers!=null&&telephoneNumbers.size()>0) {
                    for (TelephoneNumber telephoneNumber : telephoneNumbers) {
                        person.getTelephoneNumbers().add(telephoneNumber);
                    }
                }
            }
            return persons;
        }
        return null;
    }

    public static PersonName queryPersonName(String id) {
        List<PersonName> personNames = selectPersonNameById(id);
        if (personNames.size()>0) {
            return personNames.get(0);
        }
        return null;
    }

    public static ClassificationScheme queryClassificationSchemeById(String id) {
        List<ClassificationScheme> classificationSchemes = selectClassificationSchemeById(id);
        if (classificationSchemes!=null&&classificationSchemes.size()>0) {
            for (ClassificationScheme classificationScheme : classificationSchemes) {
                if (classificationScheme.getDescription()!=null) {
                    InternationalString ins = queryInternationalStringById(classificationScheme.getDescription().getId());
                    classificationScheme.setDescription(ins);
                }
                if (classificationScheme.getName()!=null) {
                    InternationalString ins = queryInternationalStringById(classificationScheme.getName().getId());
                    classificationScheme.setName(ins);
                }
                if (classificationScheme.getObjectType()!=null) {
                    ObjectRef objectType = queryObjectRefById(classificationScheme.getObjectType().getId());
                    classificationScheme.setObjectType(objectType);
                }
                if (classificationScheme.getStatus()!=null) {
                    ObjectRef status = queryObjectRefById(classificationScheme.getStatus().getId());
                    classificationScheme.setStatus(status);
                }
                if (classificationScheme.getVersionInfo()!=null) {
                    VersionInfo versionInfo = queryVersionInfoById(classificationScheme.getVersionInfo().getId());
                    classificationScheme.setVersionInfo(versionInfo);
                }
                if (classificationScheme.getNodeType()!=null) {
                    ObjectRef nodeType = queryObjectRefById(classificationScheme.getNodeType().getId());
                    classificationScheme.setNodeType(nodeType);
                }
                List<Classification> classifications = queryClassificationsBy_Id(classificationScheme.getId());
                if (classifications!=null&&classifications.size()>0) {
                    for (Classification classification : classifications) {
                        classificationScheme.getClassifications().add(classification);
                    }
                }
                List<ExternalIdentifier> externalIdentifiers = queryExternalIdentifiersBy_Id(classificationScheme.getId());
                if (externalIdentifiers!=null&&externalIdentifiers.size()>0) {
                    for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
                        classificationScheme.getExternalIdentifiers().add(externalIdentifier);
                    }
                }
                List<Slot> slots = querySlotsBy_Id(classificationScheme.getId());
                if (slots!=null&&slots.size()>0) {
                    for (Slot slot : slots) {
                        classificationScheme.getSlots().add(slot);
                    }
                }
            }
            return classificationSchemes.get(0);
        }
        return null;
    }

    public static ExternalLink queryExternalLinkById(String id) {
        List<ExternalLink> externalLinks = selectExternalLinkById(id);
        if (externalLinks!=null&&externalLinks.size()>0) {
            for (ExternalLink externalLink : externalLinks) {
                if (externalLink.getDescription()!=null) {
                    InternationalString ins = queryInternationalStringById(externalLink.getDescription().getId());
                    externalLink.setDescription(ins);
                }
                if (externalLink.getName()!=null) {
                    InternationalString ins = queryInternationalStringById(externalLink.getName().getId());
                    externalLink.setName(ins);
                }
                if (externalLink.getObjectType()!=null) {
                    ObjectRef objectType = queryObjectRefById(externalLink.getObjectType().getId());
                    externalLink.setObjectType(objectType);
                }
                if (externalLink.getStatus()!=null) {
                    ObjectRef status = queryObjectRefById(externalLink.getStatus().getId());
                    externalLink.setStatus(status);
                }
                if (externalLink.getVersionInfo()!=null) {
                    VersionInfo versionInfo = queryVersionInfoById(externalLink.getVersionInfo().getId());
                    externalLink.setVersionInfo(versionInfo);
                }
                List<Classification> classifications = queryClassificationsBy_Id(externalLink.getId());
                if (classifications!=null&&classifications.size()>0) {
                    for (Classification classification : classifications) {
                        externalLink.getClassifications().add(classification);
                    }
                }
                List<ExternalIdentifier> externalIdentifiers = queryExternalIdentifiersBy_Id(externalLink.getId());
                if (externalIdentifiers!=null&&externalIdentifiers.size()>0) {
                    for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
                        externalLink.getExternalIdentifiers().add(externalIdentifier);
                    }
                }
                List<Slot> slots = querySlotsBy_Id(externalLink.getId());
                if (slots!=null&&slots.size()>0) {
                    for (Slot slot : slots) {
                        externalLink.getSlots().add(slot);
                    }
                }
            }
            return externalLinks.get(0);
        }
        return null;
    }

    public static Federation queryFederationById(String id) {
        List<Federation> federations = selectFederationById(id);
        if (federations!=null&&federations.size()>0) {
            for (Federation federation : federations) {
                if (federation.getDescription()!=null) {
                    InternationalString ins = queryInternationalStringById(federation.getDescription().getId());
                    federation.setDescription(ins);
                }
                if (federation.getName()!=null) {
                    InternationalString ins = queryInternationalStringById(federation.getName().getId());
                    federation.setName(ins);
                }
                if (federation.getObjectType()!=null) {
                    ObjectRef objectType = queryObjectRefById(federation.getObjectType().getId());
                    federation.setObjectType(objectType);
                }
                if (federation.getStatus()!=null) {
                    ObjectRef status = queryObjectRefById(federation.getStatus().getId());
                    federation.setStatus(status);
                }
                if (federation.getVersionInfo()!=null) {
                    VersionInfo versionInfo = queryVersionInfoById(federation.getVersionInfo().getId());
                    federation.setVersionInfo(versionInfo);
                }
                List<Classification> classifications = queryClassificationsBy_Id(federation.getId());
                if (classifications!=null&&classifications.size()>0) {
                    for (Classification classification : classifications) {
                        federation.getClassifications().add(classification);
                    }
                }
                List<ExternalIdentifier> externalIdentifiers = queryExternalIdentifiersBy_Id(federation.getId());
                if (externalIdentifiers!=null&&externalIdentifiers.size()>0) {
                    for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
                        federation.getExternalIdentifiers().add(externalIdentifier);
                    }
                }
                List<Slot> slots = querySlotsBy_Id(federation.getId());
                if (slots!=null&&slots.size()>0) {
                    for (Slot slot : slots) {
                        federation.getSlots().add(slot);
                    }
                }
            }
            return federations.get(0);
        }
        return null;
    }

    public static RegistryObject queryRegistryObjectById(String id) {
        List<RegistryObject> registryObjects = selectRegistryObjectById(id);
        if (registryObjects!=null&&registryObjects.size()>0) {
            for (RegistryObject registryObject : registryObjects) {
                if (registryObject.getDescription()!=null) {
                    InternationalString ins = queryInternationalStringById(registryObject.getDescription().getId());
                    registryObject.setDescription(ins);
                }
                if (registryObject.getName()!=null) {
                    InternationalString ins = queryInternationalStringById(registryObject.getName().getId());
                    registryObject.setName(ins);
                }
                if (registryObject.getObjectType()!=null) {
                    ObjectRef objectType = queryObjectRefById(registryObject.getObjectType().getId());
                    registryObject.setObjectType(objectType);
                }
                if (registryObject.getStatus()!=null) {
                    ObjectRef status = queryObjectRefById(registryObject.getStatus().getId());
                    registryObject.setStatus(status);
                }
                if (registryObject.getVersionInfo()!=null) {
                    VersionInfo versionInfo = queryVersionInfoById(registryObject.getVersionInfo().getId());
                    registryObject.setVersionInfo(versionInfo);
                }
                List<Classification> classifications = queryClassificationsBy_Id(registryObject.getId());
                if (classifications!=null&&classifications.size()>0) {
                    for (Classification classification : classifications) {
                        registryObject.getClassifications().add(classification);
                    }
                }
                List<ExternalIdentifier> externalIdentifiers = queryExternalIdentifiersBy_Id(registryObject.getId());
                if (externalIdentifiers!=null&&externalIdentifiers.size()>0) {
                    for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
                        registryObject.getExternalIdentifiers().add(externalIdentifier);
                    }
                }
                List<Slot> slots = querySlotsBy_Id(registryObject.getId());
                if (slots!=null&&slots.size()>0) {
                    for (Slot slot : slots) {
                        registryObject.getSlots().add(slot);
                    }
                }
            }
            return registryObjects.get(0);
        }
        return null;
    }

    public static Notification queryNotificationById(String id) {
        List<Notification> notifications = selectNotificationById(id);
        if (notifications!=null&&notifications.size()>0) {
            for (Notification notification : notifications) {
                if (notification.getDescription()!=null) {
                    InternationalString ins = queryInternationalStringById(notification.getDescription().getId());
                    notification.setDescription(ins);
                }
                if (notification.getName()!=null) {
                    InternationalString ins = queryInternationalStringById(notification.getName().getId());
                    notification.setName(ins);
                }
                if (notification.getObjectType()!=null) {
                    ObjectRef objectType = queryObjectRefById(notification.getObjectType().getId());
                    notification.setObjectType(objectType);
                }
                if (notification.getStatus()!=null) {
                    ObjectRef status = queryObjectRefById(notification.getStatus().getId());
                    notification.setStatus(status);
                }
                if (notification.getVersionInfo()!=null) {
                    VersionInfo versionInfo = queryVersionInfoById(notification.getVersionInfo().getId());
                    notification.setVersionInfo(versionInfo);
                }
                if (notification.getSubscription()!=null) {
                    ObjectRef subscription = queryObjectRefById(notification.getSubscription().getId());
                    notification.setSubscription(subscription);
                }
                List<Classification> classifications = queryClassificationsBy_Id(notification.getId());
                if (classifications!=null&&classifications.size()>0) {
                    for (Classification classification : classifications) {
                        notification.getClassifications().add(classification);
                    }
                }
                List<ExternalIdentifier> externalIdentifiers = queryExternalIdentifiersBy_Id(notification.getId());
                if (externalIdentifiers!=null&&externalIdentifiers.size()>0) {
                    for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
                        notification.getExternalIdentifiers().add(externalIdentifier);
                    }
                }
                List<Slot> slots = querySlotsBy_Id(notification.getId());
                if (slots!=null&&slots.size()>0) {
                    for (Slot slot : slots) {
                        notification.getSlots().add(slot);
                    }
                }
                List<Identifiable> identifiables = queryIdentifiablesBy_Id(notification.getId());
                if (identifiables!=null&&identifiables.size()>0) {
                    for (Identifiable identifiable : identifiables) {
                        notification.getRegistryObjectList().add(identifiable);
                    }
                }
            }
            return notifications.get(0);
        }
        return null;
    }

    public static Registry queryRegistryById(String id) {
        List<Registry> registries = selectRegistryById(id);
        if (registries!=null&&registries.size()>0) {
            for (Registry registry : registries) {
                if (registry.getDescription()!=null) {
                    InternationalString ins = queryInternationalStringById(registry.getDescription().getId());
                    registry.setDescription(ins);
                }
                if (registry.getName()!=null) {
                    InternationalString ins = queryInternationalStringById(registry.getName().getId());
                    registry.setName(ins);
                }
                if (registry.getObjectType()!=null) {
                    ObjectRef objectType = queryObjectRefById(registry.getObjectType().getId());
                    registry.setObjectType(objectType);
                }
                if (registry.getStatus()!=null) {
                    ObjectRef status = queryObjectRefById(registry.getStatus().getId());
                    registry.setStatus(status);
                }
                if (registry.getVersionInfo()!=null) {
                    VersionInfo versionInfo = queryVersionInfoById(registry.getVersionInfo().getId());
                    registry.setVersionInfo(versionInfo);
                }
                if (registry.getOperator()!=null) {
                    ObjectRef operator = queryObjectRefById(registry.getOperator().getId());
                    registry.setOperator(operator);
                }
                List<Classification> classifications = queryClassificationsBy_Id(registry.getId());
                if (classifications!=null&&classifications.size()>0) {
                    for (Classification classification : classifications) {
                        registry.getClassifications().add(classification);
                    }
                }
                List<ExternalIdentifier> externalIdentifiers = queryExternalIdentifiersBy_Id(registry.getId());
                if (externalIdentifiers!=null&&externalIdentifiers.size()>0) {
                    for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
                        registry.getExternalIdentifiers().add(externalIdentifier);
                    }
                }
                List<Slot> slots = querySlotsBy_Id(registry.getId());
                if (slots!=null&&slots.size()>0) {
                    for (Slot slot : slots) {
                        registry.getSlots().add(slot);
                    }
                }
            }
            return registries.get(0);
        }
        return null;
    }

    public static User queryUserById(String id) {
        List<User> users = selectUserById(id);
        if (users!=null&&users.size()>0) {
            for (User user : users) {
                if (user.getDescription()!=null) {
                    InternationalString ins = queryInternationalStringById(user.getDescription().getId());
                    user.setDescription(ins);
                }
                if (user.getName()!=null) {
                    InternationalString ins = queryInternationalStringById(user.getName().getId());
                    user.setName(ins);
                }
                if (user.getObjectType()!=null) {
                    ObjectRef objectType = queryObjectRefById(user.getObjectType().getId());
                    user.setObjectType(objectType);
                }
                if (user.getStatus()!=null) {
                    ObjectRef status = queryObjectRefById(user.getStatus().getId());
                    user.setStatus(status);
                }
                if (user.getVersionInfo()!=null) {
                    VersionInfo versionInfo = queryVersionInfoById(user.getVersionInfo().getId());
                    user.setVersionInfo(versionInfo);
                }
                if (user.getPersonName()!=null) {
                    PersonName personName = queryPersonName(user.getPersonName().getId());
                    user.setPersonName(personName);
                }
                List<Classification> classifications = queryClassificationsBy_Id(user.getId());
                if (classifications!=null&&classifications.size()>0) {
                    for (Classification classification : classifications) {
                        user.getClassifications().add(classification);
                    }
                }
                List<ExternalIdentifier> externalIdentifiers = queryExternalIdentifiersBy_Id(user.getId());
                if (externalIdentifiers!=null&&externalIdentifiers.size()>0) {
                    for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
                        user.getExternalIdentifiers().add(externalIdentifier);
                    }
                }
                List<Slot> slots = querySlotsBy_Id(user.getId());
                if (slots!=null&&slots.size()>0) {
                    for (Slot slot : slots) {
                        user.getSlots().add(slot);
                    }
                }
                List<PostalAddress> addresses = queryPostalAddressesBy_Id(user.getId());
                if (addresses!=null&&addresses.size()>0) {
                    for (PostalAddress address : addresses) {
                        user.getAddresses().add(address);
                    }
                }
                List<EmailAddress> emailAddresses = queryEmailAddressesBy_Id(user.getId());
                if (emailAddresses!=null&&emailAddresses.size()>0) {
                    for (EmailAddress emailAddress : emailAddresses) {
                        user.getEmailAddresses().add(emailAddress);
                    }
                }
                List<TelephoneNumber> telephoneNumbers = queryTelephoneNumbersBy_Id(user.getId());
                if (telephoneNumbers!=null&&telephoneNumbers.size()>0) {
                    for (TelephoneNumber telephoneNumber : telephoneNumbers) {
                        user.getTelephoneNumbers().add(telephoneNumber);
                    }
                }
            }
            return users.get(0);
        }
        return null;
    }

    public static boolean registryPackageIsExist(String id) {
        boolean flag = checkRegistryPackageIsExist(id);
        return flag;
    }

    public static int queryCountOfRecords() {
        int count = getCountOfRegistryPackageRecords();
        return count;
    }

    /**************************************************************************************/
    public static List<String> selectIdentifiable_IdById(String id) {
        List<String> result = new ArrayList<String>();
        IdentifiableDAO identifiableDAO = SpringContextUtil.getBean(IdentifiableDAO.class);
        try {
            result = identifiableDAO.selectIdentifiable_IdById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public static List<RegistryPackage> selectRegistryPackageById(String id) {
        List<RegistryPackage> registryPackages = new ArrayList<RegistryPackage>();
        RegistryPackageDAO registryPackageDAO = SpringContextUtil.getBean(RegistryPackageDAO.class);
        try {
            registryPackages = registryPackageDAO.selectRegistryPackageById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return registryPackages;
        }
    }

    public static int getCountOfRecordsByFileType(String fileType) {
        int count = 0;
        RegistryPackageDAO registryPackageDAO = SpringContextUtil.getBean(RegistryPackageDAO.class);
        try {
            count = registryPackageDAO.getCountOfRecordsByFileType(fileType);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return count;
        }
    }

    public static List<RegistryPackage> selectRegistryPackageLimited(int limit,int offset) {
        List<RegistryPackage> registryPackages = new ArrayList<RegistryPackage>();
        RegistryPackageDAO registryPackageDAO = SpringContextUtil.getBean(RegistryPackageDAO.class);
        try {
            registryPackages = registryPackageDAO.selectRegistryPackageLimited(limit,offset);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return registryPackages;
        }
    }

    public static List<RegistryPackage> selectNewestRegistryPackageLimited(int limit,int offset) {
        List<RegistryPackage> registryPackages = new ArrayList<RegistryPackage>();
        RegistryPackageDAO registryPackageDAO = SpringContextUtil.getBean(RegistryPackageDAO.class);
        try {
            registryPackages = registryPackageDAO.selectNewestRegistryPackageLimited(limit,offset);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return registryPackages;
        }
    }

    public static String selectTimeByRegistryPackageId(String id) {
        String time = "";
        RegistryPackageDAO registryPackageDAO = SpringContextUtil.getBean(RegistryPackageDAO.class);
        try {
            time = registryPackageDAO.selectTimeById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return time;
        }
    }

    public static List<RegistryPackage> selectRegistryPackageByFileTypeLimited(String fileType, int limit, int offset) {
        List<RegistryPackage> registryPackages = new ArrayList<RegistryPackage>();
        RegistryPackageDAO registryPackageDAO = SpringContextUtil.getBean(RegistryPackageDAO.class);
        try {
            registryPackages = registryPackageDAO.selectRegistryPackageByFileTypeLimited(fileType,limit,offset);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return registryPackages;
        }
    }

    public static List<RegistryPackage> selectRegistryPackageByOwnerLimited(String owner, int limit, int offset) {
        List<RegistryPackage> registryPackages = new ArrayList<RegistryPackage>();
        RegistryPackageDAO registryPackageDAO = SpringContextUtil.getBean(RegistryPackageDAO.class);
        try {
            registryPackages = registryPackageDAO.selectRegistryPackageByOwnerLimited(owner,limit,offset);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return registryPackages;
        }
    }

    public static List<String> selectRegistryPackageByFuzzId(String fuzzId) {
        List<String> registryPackages = new ArrayList<String>();
        RegistryPackageDAO registryPackageDAO = SpringContextUtil.getBean(RegistryPackageDAO.class);
        try {
            registryPackages = registryPackageDAO.selectRegistryPackageByFuzzId(fuzzId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return registryPackages;
        }
    }

    public static List<RegistryPackage> selectAllRegistryPackage() {
        List<RegistryPackage> registryPackages = new ArrayList<RegistryPackage>();
        RegistryPackageDAO registryPackageDAO = SpringContextUtil.getBean(RegistryPackageDAO.class);
        try {
            registryPackages = registryPackageDAO.selectAllRegistryPackage();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return registryPackages;
        }
    }

    public static List<Classification> selectClassificationsBy_Id(String classificationId) {
        List<Classification> classifications = new ArrayList<Classification>();
        ClassificationDAO classificationDAO = SpringContextUtil.getBean(ClassificationDAO.class);
        try {
            classifications = classificationDAO.selectClassificationsBy_Id(classificationId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return classifications;
        }
    }

    public static List<ExternalIdentifier> selectExternalIdentifiersBy_Id(String externalIdentifierId) {
        List<ExternalIdentifier> externalIdentifiers = new ArrayList<ExternalIdentifier>();
        ExternalIdentifierDAO externalIdentifierDAO = SpringContextUtil.getBean(ExternalIdentifierDAO.class);
        try {
            externalIdentifiers = externalIdentifierDAO.selectExternalIdentifiersBy_Id(externalIdentifierId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return externalIdentifiers;
        }
    }

    public static List<VersionInfo> selectVersionInfoById(String id) {
        List<VersionInfo> versionInfos = new ArrayList<VersionInfo>();
        VersionInfoDAO versionInfoDAO = SpringContextUtil.getBean(VersionInfoDAO.class);
        try {
            versionInfos = versionInfoDAO.selectVersionInfoById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return versionInfos;
        }
    }

    public static List<ObjectRef> selectObjectRefById(String id) {
        List<ObjectRef> objectRefs = new ArrayList<ObjectRef>();
        ObjectRefDAO objectRefDAO = SpringContextUtil.getBean(ObjectRefDAO.class);
        try {
            objectRefs = objectRefDAO.selectObjectRefById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return objectRefs;
        }
    }

    public static List<InternationalString> selectInternationalStringById(String id) {
        List<InternationalString> internationalStrings = new ArrayList<InternationalString>();
        InternationalStringDAO internationalStringDAO = SpringContextUtil.getBean(InternationalStringDAO.class);
        try {
            internationalStrings = internationalStringDAO.selectInternationalStringsById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return internationalStrings;
        }
    }

    public static List<LocalizedString> selectLocalizedStringsBy_Id(String localizedStringId) {
        List<LocalizedString> localizedStrings = new ArrayList<LocalizedString>();
        LocalizedStringDAO localizedStringDAO = SpringContextUtil.getBean(LocalizedStringDAO.class);
        try {
            localizedStrings = localizedStringDAO.selectLocalizedStringsBy_Id(localizedStringId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return localizedStrings;
        }
    }

    public static List<LocalizedString> selectLocalizedStringsByFuzzIdAndValue(String fuzzId, String fuzzValue) {
        List<LocalizedString> localizedStrings = new ArrayList<LocalizedString>();
        LocalizedStringDAO localizedStringDAO = SpringContextUtil.getBean(LocalizedStringDAO.class);
        try {
            localizedStrings = localizedStringDAO.selectLocalizedStringsByFuzzIdAndValue(fuzzId,fuzzValue);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return localizedStrings;
        }
    }

    public static List<Identifiable> selectIdentifiableById(String id) {
        List<Identifiable> identifiables = new ArrayList<Identifiable>();
        IdentifiableDAO identifiableDAO = SpringContextUtil.getBean(IdentifiableDAO.class);
        try {
            identifiables = identifiableDAO.selectIdentifiableById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return identifiables;
        }
    }

    public static List<Identifiable> selectIdentifiablesBy_Id(String identifiableId) {
        List<Identifiable> identifiables = new ArrayList<Identifiable>();
        IdentifiableDAO identifiableDAO = SpringContextUtil.getBean(IdentifiableDAO.class);
        try {
            identifiables = identifiableDAO.selectIdentifiablesBy_Id(identifiableId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return identifiables;
        }
    }

    public static List<Slot> selectSlotsBy_Id(String slotId) {
        List<Slot> slots = new ArrayList<Slot>();
        SlotDAO slotDAO = SpringContextUtil.getBean(SlotDAO.class);
        try {
            slots = slotDAO.selectSlotsBy_Id(slotId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return slots;
        }
    }

    public static List<Slot> selectSlotsByFuzzNameAndValue(String fuzzName, String fuzzValue) {
        List<Slot> slots = new ArrayList<Slot>();
        SlotDAO slotDAO = SpringContextUtil.getBean(SlotDAO.class);
        try {
            slots = slotDAO.selectSlotsByFuzzNameAndValue(fuzzName,fuzzValue);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return slots;
        }
    }

    public static List<Slot> selectSlotsByFuzzNameAndId(String fuzzName, String fuzzSlotId) {
        List<Slot> slots = new ArrayList<Slot>();
        SlotDAO slotDAO = SpringContextUtil.getBean(SlotDAO.class);
        try {
            slots = slotDAO.selectSlotsByFuzzNameAndId(fuzzName,fuzzSlotId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return slots;
        }
    }

    public static List<ExtrinsicObject> selectExtrinsicObjectById(String id) {
        List<ExtrinsicObject> extrinsicObjects = new ArrayList<ExtrinsicObject>();
        ExtrinsicObjectDAO extrinsicObjectDAO = SpringContextUtil.getBean(ExtrinsicObjectDAO.class);
        try {
            extrinsicObjects = extrinsicObjectDAO.selectExtrinsicObjectById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return extrinsicObjects;
        }
    }

    public static List<Service> selectServiceById(String id) {
        List<Service> services = new ArrayList<Service>();
        ServiceDAO serviceDAO = SpringContextUtil.getBean(ServiceDAO.class);
        try {
            services = serviceDAO.selectServiceById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return services;
        }
    }

    public static List<ServiceBinding> selectServiceBindingsBy_Id(String serviceBindingId) {
        List<ServiceBinding> serviceBindings = new ArrayList<ServiceBinding>();
        ServiceBindingDAO serviceBindingDAO = SpringContextUtil.getBean(ServiceBindingDAO.class);
        try {
            serviceBindings = serviceBindingDAO.selectServiceBindingsBy_Id(serviceBindingId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return serviceBindings;
        }
    }

    public static List<SpecificationLink> selectSpecificationLinksBy_Id(String specificationLinkId) {
        List<SpecificationLink> specificationLinks = new ArrayList<SpecificationLink>();
        SpecificationLinkDAO specificationLinkDAO = SpringContextUtil.getBean(SpecificationLinkDAO.class);
        try {
            specificationLinks = specificationLinkDAO.selectSpecificationLinksBy_Id(specificationLinkId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return specificationLinks;
        }
    }

    public static List<ClassificationNode>selectClassificationNodeById(String id) {
        List<ClassificationNode> classificationNodes = new ArrayList<ClassificationNode>();
        ClassificationNodeDAO classificationNodeDAO = SpringContextUtil.getBean(ClassificationNodeDAO.class);
        try {
            classificationNodes = classificationNodeDAO.selectClassificationNodesById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return classificationNodes;
        }
    }

    public static List<Association> selectAssociationById(String id) {
        List<Association> associations = new ArrayList<Association>();
        AssociationDAO associationDAO = SpringContextUtil.getBean(AssociationDAO.class);
        try {
            associations = associationDAO.selectAssociationById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return associations;
        }
    }

    public static List<Organization> selectOrganizationById(String id) {
        List<Organization> organizations = new ArrayList<Organization>();
        OrganizationDAO organizationDAO = SpringContextUtil.getBean(OrganizationDAO.class);
        try {
            organizations = organizationDAO.selectOrganizationById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return organizations;
        }
    }

    public static List<Organization> selectOrganizationByName(String nameId) {
        List<Organization> organizations = new ArrayList<Organization>();
        OrganizationDAO organizationDAO = SpringContextUtil.getBean(OrganizationDAO.class);
        try {
            organizations = organizationDAO.selectOrganizationByName(nameId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return organizations;
        }
    }

    public static List<TelephoneNumber> selectTelephoneNumbersBy_Id(String telephoneNumberId) {
        List<TelephoneNumber> telephoneNumbers = new ArrayList<TelephoneNumber>();
        TelephoneNumberDAO telephoneNumberDAO = SpringContextUtil.getBean(TelephoneNumberDAO.class);
        try {
            telephoneNumbers = telephoneNumberDAO.selectTelephoneNumbersBy_Id(telephoneNumberId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return telephoneNumbers;
        }
    }

    public static List<EmailAddress> selectEmailAddressesBy_Id(String emailAddressId) {
        List<EmailAddress> emailAddresses = new ArrayList<EmailAddress>();
        EmailAddressDAO emailAddressDAO = SpringContextUtil.getBean(EmailAddressDAO.class);
        try {
            emailAddresses = emailAddressDAO.selectEmailAddressesBy_Id(emailAddressId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return emailAddresses;
        }
    }

    public static List<PostalAddress> selectPostalAddressesBy_Id(String postalAddressId) {
        List<PostalAddress> postalAddresses = new ArrayList<PostalAddress>();
        PostalAddressDAO postalAddressDAO = SpringContextUtil.getBean(PostalAddressDAO.class);
        try {
            postalAddresses = postalAddressDAO.selectPostalAddressesBy_Id(postalAddressId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return postalAddresses;
        }
    }

    public static List<Person> selectPersonById(String id) {
        List<Person> people = new ArrayList<Person>();
        PersonDAO personDAO = SpringContextUtil.getBean(PersonDAO.class);
        try {
            people = personDAO.selectPersonById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return people;
        }
    }

    public static List<PersonName> selectPersonNameById(String id) {
        List<PersonName> personNames = new ArrayList<PersonName>();
        PersonNameDAO personNameDAO = SpringContextUtil.getBean(PersonNameDAO.class);
        try {
            personNames = personNameDAO.selectPersonNameById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return personNames;
        }
    }

    public static List<ClassificationScheme> selectClassificationSchemeById(String id) {
        List<ClassificationScheme> classificationSchemes = new ArrayList<ClassificationScheme>();
        ClassificationSchemeDAO classificationSchemeDAO = SpringContextUtil.getBean(ClassificationSchemeDAO.class);
        try {
            classificationSchemes = classificationSchemeDAO.selectClassificationSchemeById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return classificationSchemes;
        }
    }

    public static List<ExternalLink> selectExternalLinkById(String id) {
        List<ExternalLink> externalLinks = new ArrayList<ExternalLink>();
        ExternalLinkDAO externalLinkDAO = SpringContextUtil.getBean(ExternalLinkDAO.class);
        try {
            externalLinks = externalLinkDAO.selectExternalLinkById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return externalLinks;
        }
    }

    public static List<Federation> selectFederationById(String id) {
        List<Federation> federations = new ArrayList<Federation>();
        FederationDAO federationDAO = SpringContextUtil.getBean(FederationDAO.class);
        try {
            federations = federationDAO.selectFederationById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return federations;
        }
    }

    public static List<RegistryObject> selectRegistryObjectById(String id) {
        List<RegistryObject> registryObjects = new ArrayList<RegistryObject>();
        RegistryObjectDAO registryObjectDAO = SpringContextUtil.getBean(RegistryObjectDAO.class);
        try {
            registryObjects = registryObjectDAO.selectRegistryObjectById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return registryObjects;
        }
    }

    public static List<Notification> selectNotificationById(String id) {
        List<Notification> notifications = new ArrayList<Notification>();
        NotificationDAO notificationDAO = SpringContextUtil.getBean(NotificationDAO.class);
        try {
            notifications = notificationDAO.selectNotificationById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return notifications;
        }
    }

    public static List<Registry> selectRegistryById(String id) {
        List<Registry> registries = new ArrayList<Registry>();
        RegistryDAO registryDAO = SpringContextUtil.getBean(RegistryDAO.class);
        try {
            registries = registryDAO.selectRegistryById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return registries;
        }
    }

    public static List<User> selectUserById(String id) {
        List<User> users = new ArrayList<User>();
        UserDAO userDAO = SpringContextUtil.getBean(UserDAO.class);
        try {
            users = userDAO.selectUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return users;
        }
    }

    public static boolean checkRegistryPackageIsExist(String id) {
        RegistryPackageDAO registryPackageDAO = SpringContextUtil.getBean(RegistryPackageDAO.class);
        boolean flag = false;
        try {
            flag = registryPackageDAO.checkIdIsExist(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return flag;
        }
    }

    public static int getCountOfRegistryPackageRecords() {
        int count = 0;
        RegistryPackageDAO registryPackageDAO = SpringContextUtil.getBean(RegistryPackageDAO.class);
        try {
            count = registryPackageDAO.getCountOfRecords();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return count;
        }
    }

    public static List<ExtrinsicObject> selectAllExtrinsicObjects() {
        ExtrinsicObjectDAO extrinsicObjectDAO = SpringContextUtil.getBean(ExtrinsicObjectDAO.class);
        List<ExtrinsicObject> extrinsicObjects = new ArrayList<ExtrinsicObject>();
        try {
            extrinsicObjects = extrinsicObjectDAO.selectAllExtrinsicObjects();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return extrinsicObjects;
        }
    }

    public static List<RegistryPackage> selectRegistryPackageByType(String type) {
        RegistryPackageDAO registryPackageDAO = SpringContextUtil.getBean(RegistryPackageDAO.class);
        List<RegistryPackage> registryPackages = new ArrayList<RegistryPackage>();
        try {
            registryPackages = registryPackageDAO.selectRegistryPackageByType(type);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return registryPackages;
        }
    }

    public static List<RegistryPackage> selectRegistryPackageByOwner(String owner) {
        RegistryPackageDAO registryPackageDAO = SpringContextUtil.getBean(RegistryPackageDAO.class);
        List<RegistryPackage> registryPackages = new ArrayList<RegistryPackage>();
        try {
            registryPackages = registryPackageDAO.selectRegistryPackageByOwner(owner);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return registryPackages;
        }
    }

    public static List<Organization> selectAllOrganizations() {
        OrganizationDAO organizationDAO = SpringContextUtil.getBean(OrganizationDAO.class);
        List<Organization> organizations = new ArrayList<Organization>();
        try {
            organizations = organizationDAO.selectAllOrganizations();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return organizations;
        }
    }

    public static List<Person> selectAllPersons() {
        PersonDAO personDAO = SpringContextUtil.getBean(PersonDAO.class);
        List<Person> people = new ArrayList<Person>();
        try {
            people = personDAO.selectAllPersons();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return people;
        }
    }

    public static List<Action> selectAllActions() {
        _ActionDAO actionDAO = SpringContextUtil.getBean(_ActionDAO.class);
        List<Action> actions = new ArrayList<Action>();
        try {
            actions = actionDAO.selectAllActions();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return actions;
        }
    }

    public static Action selectActionById(int id) {
        _ActionDAO actionDAO = SpringContextUtil.getBean(_ActionDAO.class);
        Action action = null;
        try {
            action = actionDAO.selectActionById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return action;
        }
    }

    public static List<ActionGroup> selectAllActionGroups() {
        _ActionGroupDAO actionGroupDAO = SpringContextUtil.getBean(_ActionGroupDAO.class);
        List<ActionGroup> actionGroups = new ArrayList<ActionGroup>();
        try {
            actionGroups = actionGroupDAO.selectAllActionGroups();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return actionGroups;
        }
    }

    public static ActionGroup selectActionGroupById(int id) {
        _ActionGroupDAO actionGroupDAO = SpringContextUtil.getBean(_ActionGroupDAO.class);
        ActionGroup actionGroup = null;
        try {
            actionGroup = actionGroupDAO.selectActionGroupById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return actionGroup;
        }
    }

    public static List<ActionGroup> selectActionGroupByGroupId(int groupId) {
        _ActionGroupDAO actionGroupDAO = SpringContextUtil.getBean(_ActionGroupDAO.class);
        List<ActionGroup> actionGroups = new ArrayList<ActionGroup>();
        try {
            actionGroups = actionGroupDAO.selectActionGroupByGroupId(groupId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return actionGroups;
        }
    }

    public static List<Group> selectAllGroups() {
        _GroupDAO groupDAO = SpringContextUtil.getBean(_GroupDAO.class);
        List<Group> groups = new ArrayList<Group>();
        try {
            groups = groupDAO.selectAllGroups();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return groups;
        }
    }

    public static Group selectGroupById(int id) {
        _GroupDAO groupDAO = SpringContextUtil.getBean(_GroupDAO.class);
        Group group = null;
        try {
            group = groupDAO.selectGroupById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return group;
        }
    }

    public static List<Master> selectAllMasters() {
        _MasterDAO masterDAO = SpringContextUtil.getBean(_MasterDAO.class);
        List<Master> masters = new ArrayList<Master>();
        try {
            masters = masterDAO.selectAllMasters();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return masters;
        }
    }

    public static Master selectMasterByName(String masterName) {
        _MasterDAO masterDAO = SpringContextUtil.getBean(_MasterDAO.class);
        Master master = null;
        try {
            master = masterDAO.selectMasterByName(masterName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return master;
        }
    }

    public static Master selectMasterById(int id) {
        _MasterDAO masterDAO = SpringContextUtil.getBean(_MasterDAO.class);
        Master master = null;
        try {
            master = masterDAO.selectMasterById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return master;
        }
    }

    public static List<MasterGroup> selectAllMasterGroups() {
        _MasterGroupDAO masterGroupDAO = SpringContextUtil.getBean(_MasterGroupDAO.class);
        List<MasterGroup> masterGroups = new ArrayList<MasterGroup>();
        try {
            masterGroups = masterGroupDAO.selectAllMasterGroups();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return masterGroups;
        }
    }

    public static MasterGroup selectMasterGroupById(int id) {
        _MasterGroupDAO masterGroupDAO = SpringContextUtil.getBean(_MasterGroupDAO.class);
        MasterGroup masterGroup = null;
        try {
            masterGroup = masterGroupDAO.selectMasterGroupById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return masterGroup;
        }
    }

    public static MasterGroup selectMasterGroupByMasterId(int masterId) {
        _MasterGroupDAO masterGroupDAO = SpringContextUtil.getBean(_MasterGroupDAO.class);
        MasterGroup masterGroup = null;
        try {
            masterGroup = masterGroupDAO.selectMasterGroupByMasterId(masterId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return masterGroup;
        }
    }

    public static List<Data> selectDataInfoById(String id) {
        DataDAO dataDAO = SpringContextUtil.getBean(DataDAO.class);
        List<Data> data = null;
        try {
            data = dataDAO.selectDataById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return data;
        }
    }


    /***************************************************************************/
 }
