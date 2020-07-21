package com.csw.ebrim.util;

import com.csw.file.util.FileOperationUtil;
import com.csw.model.*;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.*;

/**
 * 暂未使用
 * */
public class ParseEbrimContent {

    public Object registryPackage;
    public Object extrinsicObject;
    public ArrayList<Object> classificationNodes = new ArrayList<Object>();
    public ArrayList<Object> associations = new ArrayList<Object>();
    public ArrayList<Object> persons = new ArrayList<Object>();
    public ArrayList<Object> organizations = new ArrayList<Object>();
    public ArrayList<Object> services = new ArrayList<Object>();

    /**
     * @author TheBloomOfYouth
     * 初步解析ebRIM文档，将文档中的各个节点以对象的形式存储到相应的变量中，待进一步解析
     * @param ebRIM: ebRIM内容字符串
     * @return void
     * */

    /**
     * 解析ebrim内容获取内容id（包含package）
     * */
    public static String getIdFromEbrim(String ebrimContent) {
        String result = "";

        int index = ebrimContent.indexOf("<rim:RegistryObjectList>");
        String registryPackageHeader = ebrimContent.substring(0,index);
        result = registryPackageHeader.substring(registryPackageHeader.indexOf("id=\"")+"id=\"".length(), registryPackageHeader.indexOf("\">"));

        return result;
    }

    public void parseContent(String ebRIM) {
        SAXReader saxReader = new SAXReader();
        try {
            //Document document = saxReader.read(file);
            Document document = DocumentHelper.parseText(ebRIM);
            Element root = document.getRootElement();
            registryPackage = root;
            for (Iterator i = root.elementIterator(); i.hasNext();) {
                Element elements = (Element)i.next();
                for (Iterator j = elements.elementIterator(); j.hasNext();) {
                    Element element = (Element)j.next();
                    if(element.getName().equals("ExtrinsicObject")) {
                        extrinsicObject = element;
                    }
                    else if(element.getName().equals("ClassificationNode")) {
                        classificationNodes.add(element);
                    }
                    else if (element.getName().equals("Association")) {
                        associations.add(element);
                    }
                    else if (element.getName().equals("Service")) {
                        services.add(element);
                    }
                    else if (element.getName().equals("Person")) {
                        persons.add(element);
                    }
                    else if (element.getName().equals("Organization")) {
                        organizations.add(element);
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("done");

    }

    /**
     * 解析registryPackage对象，将其信息写入到RegistryPackage操作类中
     * @param registryPackage:通过解析ebrim说得到的registryPackage对象
     * @return 返回RegistryPackage对象
     * *******未完成*******
     * */
    public RegistryPackage getRegistryPackageInfo(Object registryPackage) {
        Element element = (Element)registryPackage;
        List<Attribute> attributes = element.attributes();
        RegistryPackage rp = new RegistryPackage();
        for (int i = 0; i<attributes.size(); i++) {
            System.out.println(attributes.get(i).getName());
            if (attributes.get(i).getName().equals("id")) {
                rp.setId(attributes.get(i).getValue());
            }
            if (attributes.get(i).getName().equals("lid")) {
                rp.setLid(attributes.get(i).getValue());
            }
            if (attributes.get(i).getName().equals("home")) {
                rp.setHome(attributes.get(i).getValue());
            }
        }
        return rp;
    }

    public InternationalString handleInternationalString(InternationalString ins,String id, Object type) {
        Set<LocalizedString> localizedStrings = new HashSet<LocalizedString>();
        LocalizedString localizedString = new LocalizedString();
        Element element = (Element)type;
        if (element.elementIterator().hasNext()) {
            for (Iterator i=element.elementIterator();i.hasNext();) {
                Element ls = (Element)i.next();
                localizedString.setId(id);
                localizedString.setValue(ls.attributeValue("value"));
                localizedString.setLang(ls.attributeValue("lang"));
                localizedStrings.add(localizedString);
            }
        }
        ins.setLocalizedStrings(localizedStrings);
        ins.setId(id);
        return ins;
    }

    /**
     * 该方法用来解析ExtrinsicObject内容，ExtrinsicObject是RepositoryItem的主要元数据类
     * @param extrinsicObject:解析ebrim内容生成的ExtrinsicObject对象
     * @return ExtrinsicObject操作类
     * */
    public ExtrinsicObject handleExtrinsicObject(Object extrinsicObject) {
        ExtrinsicObject extrinsicObjectTemp = new ExtrinsicObject();
        String extrinsicObjectId = null;
        Set<Classification> classifications = new HashSet<Classification>();
        Set<Slot> slots = new HashSet<Slot>();
        Element node = (Element)extrinsicObject;
        extrinsicObjectId = node.attributeValue("id");
        extrinsicObjectTemp.setId(extrinsicObjectId);
        extrinsicObjectTemp.setMimeType(node.attributeValue("mimeType"));
        ObjectRef objectType = new ObjectRef();
        objectType.setId(node.attributeValue("objectType"));
        extrinsicObjectTemp.setObjectType(objectType);
        extrinsicObjectTemp.setLid(node.attributeValue("lid"));
        for (Iterator j = node.elementIterator();j.hasNext();) {
            Element element1 = (Element)j.next();
            if (element1.getName().equals("Slot")) {
                Slot slot = handleSlot(element1,extrinsicObjectId);
                slots.add(slot);
            }
            if (element1.getName().equals("Classification")) {
                Classification classification = handleClassification(element1);
                classifications.add(classification);
            }
            if (element1.getName().equals("Name")) {
                if (element1.elementIterator().hasNext()) {
                    Object type = element1.elementIterator().next();
                    InternationalString internationalString = new InternationalString();
                    handleInternationalString(internationalString,extrinsicObjectId,type);
                    extrinsicObjectTemp.setName(internationalString);
                }
            }
            if (element1.getName().equals("Description")) {
                if (element1.elementIterator().hasNext()) {
                    InternationalString internationalString = new InternationalString();
                    handleInternationalString(internationalString,extrinsicObjectId,element1);
                    extrinsicObjectTemp.setDescription(internationalString);
                }
            }
        }
        extrinsicObjectTemp.setClassifications(classifications);
        extrinsicObjectTemp.setSlots(slots);
        return extrinsicObjectTemp;
    }

    public ExtrinsicObject getExtrinsicInfo(Object extrinsicObject) {
        ExtrinsicObject extrinsicObjectTemp = handleExtrinsicObject(extrinsicObject);
        return extrinsicObjectTemp;
    }

    /**
     * 该方法用来解析slot节点，获取slot节点内的相关数据
     * @param slot:slot节点对象
     * @return 返回包含slot信息的对象
     * */
    public Slot handleSlot(Object slot, String id) {
        Slot slotTemp = new Slot();
        Element node = (Element)slot;
        slotTemp.setId(id);
        slotTemp.setName(node.attributeValue("name"));
        slotTemp.setSlotType(node.attributeValue("slotType"));
        if (node.elementIterator().hasNext()) {
            if (node.elementIterator().next().getName().equals("ValueList")) {
                Element valueList = node.elementIterator().next();
                ArrayList<String> values = new ArrayList<String>();
                for (Iterator i=valueList.elementIterator();i.hasNext();) {
                    Element value = (Element)i.next();
                    values.add(value.getText());
                }
                slotTemp.setValues(values.toString());
            }
        }
        return slotTemp;
    }

    /**
     * 该方法用来解析Classification节点，获取classification节点内的相关数据
     * @param classification:classification节点对象
     * @return 包含Classification节点信息的操作类集合
     * */
    public Classification handleClassification(Object classification) {
        Classification classificationTemp = new Classification();
        Element node = (Element)classification;
        classificationTemp.setId(node.attributeValue("id"));
        ObjectRef classifiedObject  = new ObjectRef();
        classifiedObject.setId(node.attributeValue("classifiedObject"));
        ObjectRef classificationNode = new ObjectRef();
        classificationNode.setId(node.attributeValue("classificationNode"));
        classificationTemp.setClassifiedObject(classifiedObject);
        classificationTemp.setClassificationNode(classificationNode);
        return classificationTemp;
    }

    /**
     * 解析ClassificationNode节点信息
     * @param classificationNode:解析ebrim后获取的ClassificationNode节点
     * @return 返回ClassificationNode操作类
     * */
    public ClassificationNode handleClassificationNode(Object classificationNode) {
        ClassificationNode classificationNodeTemp = new ClassificationNode();
        String classificationNodeId = null;
        Element node = (Element)classificationNode;
        classificationNodeId = node.attributeValue("id");
        classificationNodeTemp.setId(classificationNodeId);
        ObjectRef parent = new ObjectRef();
        parent.setId(node.attributeValue("parent"));
        classificationNodeTemp.setParent(parent);
        if (node.elementIterator().hasNext()) {
            Element name = node.elementIterator().next();
            InternationalString internationalString = new InternationalString();
            handleInternationalString(internationalString,classificationNodeId,name);
            classificationNodeTemp.setName(internationalString);
        }
        return classificationNodeTemp;
    }

    public ArrayList<ClassificationNode> getClassificationNodeInfo(ArrayList<Object> classificationNodes) {
        ArrayList<ClassificationNode> cns = new ArrayList<ClassificationNode>();
        ClassificationNode classificationNode = new ClassificationNode();
        for (int i=0;i<classificationNodes.size();i++) {
            classificationNode = handleClassificationNode(classificationNodes.get(i));
            cns.add(classificationNode);
        }
        return cns;
    }

    /**
     * 该方法用来解析Association对象，获取其中的信息
     * @param association:由解析ebrim文档之后获取的Object对象
     * @return 包含Association操作类的
     * */
    public Association handleAssociation(Object association) {
        Association associationTemp = new Association();
        String associationId = null;
        Set<Slot> slots = new HashSet<Slot>();
        Element node = (Element)association;
        associationId = node.attributeValue("id");
        associationTemp.setId(associationId);
        ObjectRef associationType = new ObjectRef();
        associationType.setId(node.attributeValue("associationType"));
        associationTemp.setAssociationType(associationType);
        ObjectRef sourceObject = new ObjectRef();
        sourceObject.setId(node.attributeValue("sourceObject"));
        associationTemp.setSourceObject(sourceObject);
        ObjectRef targetObject = new ObjectRef();
        targetObject.setId(node.attributeValue("targetObject"));
        associationTemp.setTargetObject(targetObject);
        if (node.elementIterator().hasNext()) {
            if (node.elementIterator().next().getName().equals("Slot")) {
                Slot slot = handleSlot(node.elementIterator().next(),associationId);
                slots.add(slot);
            }
        }
        if (slots!=null && slots.size()>0) {
            associationTemp.setSlots(slots);
        }
        return associationTemp;
    }

    public ArrayList<Association> getAssociationInfo(ArrayList<Object> associations) {
        ArrayList<Association> results = new ArrayList<Association>();
        for (int i=0;i<associations.size();i++) {
            Association associationTemp = new Association();
            associationTemp = handleAssociation(associations.get(i));
            results.add(associationTemp);
        }
        return results;
    }

    /**
     * 该方法用来解析Service对象信息
     * @param service:由解析ebrim内容之后生成的包含Service信息的集合对象
     * @return 返回包含Service信息的Service操作类集合
     * */
    public Service handleService(Object service) {
        Service serviceTemp = new Service();
        String serviceId = null;
        Element node = (Element)service;
        serviceId = node.attributeValue("id");
        serviceTemp.setId(serviceId);
        Set<Classification> classifications = new HashSet<Classification>();
        Set<ServiceBinding> serviceBindings = new HashSet<ServiceBinding>();
        for (Iterator i=node.elementIterator();i.hasNext();) {
            Element next = (Element)i.next();
            if (next.getName().equals("Classification")) {
                Classification classification = handleClassification(next);
                classifications.add(classification);
            }
            if (next.getName().equals("ServiceBinding")) {
                ServiceBinding serviceBinding = new ServiceBinding();
                String serciceBindingId = null;
                serciceBindingId = next.attributeValue("id");
                serviceBinding.setId(serciceBindingId);
                ObjectRef services = new ObjectRef();
                services.setId(next.attributeValue("service"));
                serviceBinding.setService(services);
                serviceBinding.setAccessURI(next.attributeValue("accessURI"));
                for (Iterator j=next.elementIterator();j.hasNext();){
                    Element element = (Element)j.next();
                    if (element.getName().equals("Name")) {
                        InternationalString name = new InternationalString();
                        handleInternationalString(name,serciceBindingId,element);
                        serviceBinding.setName(name);
                    }
                    if (element.getName().equals("Description")) {
                        InternationalString description = new InternationalString();
                        handleInternationalString(description,serciceBindingId,element);
                        serviceBinding.setDescription(description);
                    }
                }
                serviceBindings.add(serviceBinding);
            }
        }
        if (serviceBindings!=null && serviceBindings.size()>0) {
            serviceTemp.setServiceBindings(serviceBindings);
        }
        if (classifications!=null && classifications.size()>0) {
            serviceTemp.setClassifications(classifications);
        }
        return serviceTemp;
    }

    public ArrayList<Service> getServiceInfo(ArrayList<Object> services) {
        ArrayList<Service> results = new ArrayList<Service>();
        String serviceId = null;
        for (int i=0;i<services.size();i++) {
            Service serviceTemp = new Service();
            serviceTemp = handleService(services.get(i));
            results.add(serviceTemp);
        }
        return results;
    }

    /**
     * 该方法用来解析Organization信息
     * @param organization:由解析ebrim内容后生成的organization对象
     * @return 返回解析后的Organization对象
     * */
    public Organization handleOrganization(Object organization) {
        Organization organizationTemp = new Organization();
        String organizationId = null;
        Set<PostalAddress> postalAddresses = new HashSet<PostalAddress>();
        Set<EmailAddress> emailAddresses = new HashSet<EmailAddress>();
        Element node = (Element)organization;
        organizationId = node.attributeValue("id");
        organizationTemp.setId(organizationId);
        ObjectRef primaryContact = new ObjectRef();
        primaryContact.setId(node.attributeValue("primaryContact"));
        organizationTemp.setPrimaryContact(primaryContact);
        for (Iterator i=node.elementIterator();i.hasNext();) {
            Element next = (Element)i.next();
            if (next.getName().equals("Name")) {
                InternationalString name = new InternationalString();
                handleInternationalString(name,organizationId,next);
                organizationTemp.setName(name);
            }
            if (next.getName().equals("Address")) {
                PostalAddress postalAddress = new PostalAddress();
                postalAddress.setId(organizationId);
                postalAddress.setStreet(next.attributeValue("street"));
                postalAddress.setCountry(next.attributeValue("country"));
                postalAddress.setCity(next.attributeValue("city"));
                postalAddress.setPostalCode(next.attributeValue("postalCode"));
                postalAddresses.add(postalAddress);
            }
            if (next.getName().equals("EmailAddress")) {
                EmailAddress emailAddress = new EmailAddress();
                emailAddress.setId(organizationId);
                emailAddress.setAddress(next.attributeValue("address"));
                emailAddresses.add(emailAddress);
            }
        }
        if (postalAddresses!=null && postalAddresses.size()>0) {
            organizationTemp.setAddresses(postalAddresses);
        }
        if (emailAddresses!=null && emailAddresses.size()>0) {
            organizationTemp.setEmailAddresses(emailAddresses);
        }
        return organizationTemp;
    }

    public ArrayList<Organization> getOrganizationInfo(ArrayList<Object> organizations) {
        ArrayList<Organization> results = new ArrayList<Organization>();
        for (int i=0;i<organizations.size();i++) {
            Organization organization = handleOrganization(organizations.get(i));
            results.add(organization);
        }
        return results;
    }

    /**
     * 该方法用来解析Person信息
     * @param person:由解析ebrim后获取的包含Person信息的对象
     * @return 返回包含Person信息的Person对象
     * */
    public Person handlePerson(Object person) {
        Person personTemp = new Person();
        String personId = null;
        Element node = (Element)person;
        personId = node.attributeValue("id");
        personTemp.setId(personId);
        for (Iterator i=node.elementIterator();i.hasNext();) {
            Element personName = (Element)i.next();
            if (personName.getName().equals("PersonName")) {
                PersonName pName = new PersonName();
                pName.setId(personId);
                pName.setFirstName(personName.attributeValue("firstName"));
                pName.setMiddleName(personName.attributeValue("middleName"));
                pName.setLastName(personName.attributeValue("lastName"));
                personTemp.setPersonName(pName);
            }
        }
        return personTemp;
    }

    public ArrayList<Person> getPersonInfo(ArrayList<Object> persons) {
        ArrayList<Person> results = new ArrayList<Person>();
        for (int i=0;i<persons.size();i++) {
            Person person = handlePerson(persons.get(i));
            results.add(person);
        }
        return results;
    }

    public static void main(String[] args) {
        String ebrim = FileOperationUtil.readFileContent("D://Others/ebrim.xml","UTF-8");
        //getServiceInfo(services);
        //getClassificationNodeInfo(classificationNodes);
        //getAssociationInfo(associations);
        //getServiceInfo(services);
        //getOrganizationInfo(organizations);
        //getPersonInfo(persons);
        //getExtrinsicInfo(extrinsicObject);
        //getClassificationInfo(classifications);
    }

}
