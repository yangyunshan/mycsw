package com.csw.data.util;

import com.csw.model.LocalizedString;
import com.csw.model.Slot;
import com.string.util.StringUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

public class GetDetailInfo {

    public static String getKeywords(String registryPackageId) {
        String result = "";

        String id = StringUtil.deletePackage(registryPackageId);
        List<Slot> slots = QueryDataUtil.selectSlotsByFuzzNameAndId("%Keywords",id);
        for (Slot slot : slots) {
            String value = slot.getValues();
            try {
                Document document = DocumentHelper.parseText(value);
                Element root = document.getRootElement();
                for (Iterator i = root.elementIterator();i.hasNext();) {
                    Element node = (Element)i.next();
                    String str = node.getText();
                    result += str + " ";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String getDescription(String registryPackageId) {
        String result = "";

        String id = StringUtil.deletePackage(registryPackageId);
        List<LocalizedString> localizedStrings = QueryDataUtil.selectLocalizedStringsBy_Id(id+":Description");
        for (LocalizedString localizedString : localizedStrings) {
            result = localizedString.getValue();
        }

        return result;
    }

    public static String getName(String registryPackageId) {
        String result = "";

        String id = StringUtil.deletePackage(registryPackageId);
        List<LocalizedString> localizedStrings = QueryDataUtil.selectLocalizedStringsBy_Id(id+":Name");
        for (LocalizedString localizedString : localizedStrings) {
            result = localizedString.getValue();
        }

        return result;
    }

    public static String[] getEnvelope(String registryPackageId) {
        String[] corners = new String[2];

        String id = StringUtil.deletePackage(registryPackageId);
        List<Slot> slots = QueryDataUtil.selectSlotsByFuzzNameAndId("%BoundedBy",id);
        for (Slot slot : slots) {
            String value = slot.getValues();
            try {
                Document document = DocumentHelper.parseText(value);
                Element root = document.getRootElement();
                for (Iterator i = root.elementIterator(); i.hasNext();) {
                    Element envolope = (Element)i.next();
                    int num = 0;
                    for (Iterator j=envolope.elementIterator();j.hasNext();) {
                        Element coordinates = (Element) j.next();

                        for (Iterator k = coordinates.elementIterator();k.hasNext();) {
                            Element corner = (Element)k.next();
                            corners[num] = corner.getText();
                            num++;
                        }

//                        corners[num] = corner.getText();
//                        num++;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return corners;
    }

    public static String[] getStartAndEndTime(String registryPackageId) {
        String[] result = new String[2];

        String id = StringUtil.deletePackage(registryPackageId);
        List<Slot> slots1 = QueryDataUtil.selectSlotsByFuzzNameAndId("%ValidTimeBegin",id);
        for (Slot slot : slots1) {
            String value = slot.getValues();
            try {
                Document document = DocumentHelper.parseText(value);
                Element root = document.getRootElement();
                result[0] = root.getText();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<Slot> slots2 = QueryDataUtil.selectSlotsByFuzzNameAndId("%ValidTimeEnd",id);
        for (Slot slot : slots2) {
            String value = slot.getValues();
            try {
                Document document = DocumentHelper.parseText(value);
                Element root = document.getRootElement();
                result[1] = root.getText();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}

