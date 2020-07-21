package com.csw.ebrim.util;

import com.ebrim.model.rim.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Iterator;

public class ParseEbrimToDC {

    /**
     * 根据RegistryPackage的id值获取文档的内容
     *
     * @param rpt
     *
     * @return 返回的RegistryPacakge的主要内容---以Dublin Core元数据表示
     */
    public static String getRegistryPackageContent(RegistryPackageType rpt) {
        String allContent = ParseEbrimToDC.registryPackageCoreContentMethod(rpt);
        return "<csw:Record>" + allContent + "</csw:Record>";
    }

    public static String registryPackageCoreContentMethod(RegistryPackageType rpt) {
        String allContent = "";
        String extrinsicObjectContent = "";
        String organizationContent = "";
        String serviceContent = "";
        String classificationNodeContent = "";
        String associationContent = "";
        for (IdentifiableType it : rpt.getRegistryObjectList().getIdentifiableArray()) {
            if (it.schemaType().getFullJavaName().equals("com.ebrim.model.rim.ExtrinsicObjectType")) {
                ExtrinsicObjectType eot = (ExtrinsicObjectType)it;
                String identifier = eot.getId();
                extrinsicObjectContent += "<dc:identifier>"+identifier+"</dc:identifier>";

                SlotType1[] slots = eot.getSlotArray();
                /***********时间***********/
                String beginTime = null;
                String endTime = null;
                String timePeriod = "";
                for (SlotType1 st : slots) {
                    /**********关键字*********/
                    if (st.getName().toLowerCase().endsWith("keywords")) {
                        String[] keywords = st.getValueList().getValueArray();
                        for (String keyword : keywords) {
                            extrinsicObjectContent += "<dc:subject>"+keyword+"</dc:subject>";
                        }
                    }
                    /**********开始时间***********/
                    if (st.getName().toLowerCase().endsWith("validtimebegin")) {
                        beginTime = st.getValueList().getValueArray()[0];
                    }
                    /**********结束时间***********/
                    if (st.getName().toLowerCase().endsWith("validtimeend")) {
                        endTime = st.getValueList().getValueArray()[0];
                    }

                    if (st.getName().toLowerCase().endsWith("resolution")) {
                        String resolution = st.getValueList().getValueArray()[0];
                        extrinsicObjectContent += "<dc:format>"+resolution+"</dc:format>";
                    }
                    /************边界范围************/
                    if (st.getName().toLowerCase().endsWith("boundedby")&&
                            st.getSlotType().equals("urn:ogc:def:dataType:ISO-19107:2003:GM_Envelope")) {
                        ValueListType slt = st.getValueList();
                        String str = slt.toString();
                        String srsName = "";
                        String[] corners = new String[2];
                        try {
                            Document document = DocumentHelper.parseText(str);
                            Element root = document.getRootElement();
                            for (Iterator i = root.elementIterator(); i.hasNext();) {
                                Element envolope = (Element)i.next();
                                srsName = envolope.attributeValue("srsName");
                                int num = 0;
                                for (Iterator j=envolope.elementIterator();j.hasNext();) {
                                    Element corner = (Element) j.next();
                                    corners[num] = corner.getText();
                                    num++;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        extrinsicObjectContent += "<ows:BoundingBox crs=\""+srsName+"\">"+
                                "<ows:LowerCorner>"+corners[0]+"</ows:LowerCorner>"+
                                "<ows:UpperCorner>"+corners[1]+"</ows:UpperCorner>"+"</ows:BoundingBox>";
                    }
                    if (st.getName().toLowerCase().endsWith("size")) {
                        String size = st.getValueList().getValueArray()[0];
                        extrinsicObjectContent += "<dc:format>"+size+"</dc:format>";
                    }
                    if (st.getName().toLowerCase().endsWith("format")) {
                        String format = st.getValueList().getValueArray()[0];
                        extrinsicObjectContent += "<dc:type>"+format+"</dc:type>";
                    }
//                    if (st.getName().toLowerCase().endsWith("associatedsensoruniqueid")) {
//                        String associatedSensorUniqueId = st.getValueList().getValueArray()[0];
//                        extrinsicObjectContent += "<dc:relation>"+associatedSensorUniqueId+"</dc:relation>";
//                    }
                }
                if (beginTime!=null&&endTime!=null) {
                    timePeriod = beginTime + "-" +endTime;
                    extrinsicObjectContent += "<dc:coverage>"+timePeriod+"</dc:coverage>";
                }

                InternationalStringType inst = eot.getDescription();
                if (inst!=null) {
                    String description = inst.getLocalizedStringArray()[0].getValue();
                    extrinsicObjectContent += "<dc:description>"+description+"</dc:description>";
                }

                InternationalStringType name = eot.getName();
                if (name!=null) {
                    String eName = name.getLocalizedStringArray()[0].getValue();
                    extrinsicObjectContent += "<dc:title>"+eName+"</dc:title>";
                }
            }
            if (it.schemaType().getFullJavaName().equals("com.ebrim.model.rim.ClassificationNodeType")) {
                ClassificationNodeType cnt = (ClassificationNodeType) it;
                InternationalStringType inst = cnt.getName();
                String name = inst.getLocalizedStringArray()[0].getValue();
                classificationNodeContent += "<dc:type>"+name+"</dc:type>";
            }
            if (it.schemaType().getFullJavaName().equals("com.ebrim.model.rim.AssociationType1")) {
                AssociationType1 at = (AssociationType1)it;
                String sourceObject = at.getSourceObject();
                String targetObject = at.getTargetObject();
                associationContent += "<dc:relation>"+sourceObject+"</dc:relation>"+"<dc:relation>"+targetObject+"</dc:relation>";
            }
            if (it.schemaType().getFullJavaName().equals("com.ebrim.model.rim.ServiceType")) {
                //ServiceType st = (ServiceType)it;
            }
            if (it.schemaType().getFullJavaName().equals("com.ebrim.model.rim.OrganizationType")) {
                OrganizationType ot = (OrganizationType)it;
                InternationalStringType inst = ot.getName();
                String name = inst.getLocalizedStringArray()[0].getValue();
                organizationContent += "<dc:creator>"+name+"</dc:creator>";
            }
        }
        allContent = extrinsicObjectContent + serviceContent + associationContent + classificationNodeContent + organizationContent;
        return allContent;
    }
}
