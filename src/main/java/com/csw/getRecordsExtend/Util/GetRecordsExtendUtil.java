package com.csw.getRecordsExtend.Util;

import com.csw.data.util.QueryDataUtil;
import com.csw.model.*;
import com.string.util.StringUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

import java.util.*;

public class GetRecordsExtendUtil {
    /**
     * 根据registryPackage子元素对象的id值获取所有的可能的RegistryPackage的Id属性
     *
     * @param id :
     *            registryPackage子元素的id
     *
     * @return 相对应的RegistryPackage的id集合
     */
    public static String getRegistryPackageUtil(String id) {
        String registryPackageId = "";
        List<RegistryPackage> registryPackages = QueryDataUtil.queryAllRegistryPackage();
        for (RegistryPackage registryPackage : registryPackages) {
            Iterator<Identifiable> iterator = registryPackage.getRegistryObjectList().iterator();
            while (iterator.hasNext()) {
                Identifiable identifiable = iterator.next();
                if (identifiable.getId().equals(id)) {
                    registryPackageId = registryPackage.getId();
                }
            }
        }
        return registryPackageId;
    }

    /**
     * 通过关键字查询记录
     *
     * @param keywords :
     *                所要查询的关键字集合
     *
     * @return 符合要求的RegistryPackageId集合
     * */
    public static Set<String> queryRegistryPackageByKeywords(List<String> keywords) {
        List<ExtrinsicObject> extrinsicObjects = QueryDataUtil.queryAllExtrinsicObjects();
        List<String> extrinsicObjectIds = new ArrayList<String>();
        for (ExtrinsicObject extrinsicObject : extrinsicObjects) {
            Set<Slot> slots = extrinsicObject.getSlots();
            for (Slot slot : slots) {
                if (slot.getName().toLowerCase().endsWith("keywords")) {
                    for (String keyword : keywords) {
                        if (slot.getValues().contains(keyword)) {
                            extrinsicObjectIds.add(extrinsicObject.getId());
                            break;
                        }
                    }
                }
            }
        }
        Set<String> registryPackageIds = new HashSet<String>();
        for (String id : extrinsicObjectIds) {
            registryPackageIds.add(getRegistryPackageUtil(id));
        }
        return registryPackageIds;
    }

    /**
     * 通过标题查询记录
     *
     * @param title :
     *              所要查询的标题名称（模糊查询）
     *
     * @return 符合要求的RegistryPackageId集合
     * */
    public static Set<String> queryRegistryPackageByTitle(String title) {
        List<ExtrinsicObject> extrinsicObjects = QueryDataUtil.queryAllExtrinsicObjects();
        List<String> extrinsicObjectIds = new ArrayList<String>();
        for (ExtrinsicObject extrinsicObject : extrinsicObjects) {
            InternationalString name = extrinsicObject.getName();
            if (name.getLocalizedStrings().size()>0) {
                if (name.getLocalizedStrings().iterator().next().getValue().contains(title)) {
                    extrinsicObjectIds.add(extrinsicObject.getId());
                }
            }
        }
        Set<String> registryPackageIds = new HashSet<String>();
        for (String id : extrinsicObjectIds) {
            registryPackageIds.add(getRegistryPackageUtil(id));
        }
        return registryPackageIds;
    }

    /**
     * 通过边界范围查询记录（两矩形有重叠即判断符合要求）
     *
     * @param left :
     *             左下角x值
     * @param down :
     *             左下角y值
     * @param right :
     *              右上角x值
     * @param up :
     *           右上角y值
     * @return 符合要求的RegistryPackageId集合
     * */
    public static Set<String> queryRegistryPackageByBounding(String left, String down,
                                                              String right, String up) {
//        List<ExtrinsicObject> extrinsicObjects = QueryDataUtil.queryAllExtrinsicObjects();
//        List<String> extrinsicObjectIds = new ArrayList<String>();
//        for (ExtrinsicObject extrinsicObject : extrinsicObjects) {
//            Set<Slot> slots = extrinsicObject.getSlots();
//            for (Slot slot : slots) {
//                if (slot.getName().toLowerCase().endsWith("boundedby")) {
//                    String values = slot.getValues();
//                    String[] corners = new String[2];
//                    int index = 0;
//                    try {
//                        Document document = DocumentHelper.parseText(values);
//                        Element root = document.getRootElement();
//                        for (Iterator i=root.elementIterator();i.hasNext();) {
//                            Element envelope = (Element)i.next();
//                            for (Iterator j=envelope.elementIterator();j.hasNext();) {
//                                Element corner = (Element)j.next();
//                                corners[index] = corner.getText();
//                                index++;
//                            }
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    double x1 = Double.valueOf(corners[0].split(" ")[0]);
//                    double y1 = Double.valueOf(corners[0].split(" ")[1]);
//                    double x2 = Double.valueOf(corners[1].split(" ")[0]);
//                    double y2 = Double.valueOf(corners[1].split(" ")[1]);
//                    double _left = Double.valueOf(left);
//                    double _down = Double.valueOf(down);
//                    double _right = Double.valueOf(right);
//                    double _up = Double.valueOf(up);
//                    if (!(x1>_right||y2<_down||x2<_left||y1>_up)) {
//                        extrinsicObjectIds.add(extrinsicObject.getId());
//                    }
//                }
//            }
//        }
//        Set<String> registryPackageIds = new HashSet<String>();
//        for (String id : extrinsicObjectIds) {
//            registryPackageIds.add(getRegistryPackageUtil(id));
//        }
//        return registryPackageIds;

        Set<String> ids = new HashSet<String>();
        List<Slot> slots = QueryDataUtil.selectSlotsByFuzzNameAndValue("%BoundedBy","%");
        if (slots.size()>0) {
            for (Slot slot : slots) {
                String values = slot.getValues();
                String[] corners = new String[2];
                int index = 0;
                try {
                    Document document = DocumentHelper.parseText(values);
                    Element root = document.getRootElement();
                    for (Iterator i = root.elementIterator(); i.hasNext();) {
                        Element envelope = (Element)i.next();
                        for (Iterator j=envelope.elementIterator();j.hasNext();) {
                            Element corner = (Element)j.next();
                            corners[index] = corner.getText();
                            index++;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                double x1 = Double.valueOf(corners[0].split(" ")[0]);
                double y1 = Double.valueOf(corners[0].split(" ")[1]);
                double x2 = Double.valueOf(corners[1].split(" ")[0]);
                double y2 = Double.valueOf(corners[1].split(" ")[1]);
                double _left = Double.valueOf(left);
                double _down = Double.valueOf(down);
                double _right = Double.valueOf(right);
                double _up = Double.valueOf(up);
                if (!(x1>_right||y2<_down||x2<_left||y1>_up)) {
                    String strTemp = StringUtil.deleteStr2(slot.getId(),":ExtrinsicObject:Slot");
                    ids.add(strTemp);
                }
            }
        }
        return ids;
    }

    /**
     * 查询某一时间段之间的所有数据
     * */
    public static List<String> queryRegistryPackageByStartAndEndDate(String startDate, String endDate) {
        List<String> ids = new ArrayList<String>();
        List<Slot> slots = QueryDataUtil.selectSlotsByFuzzNameAndValue("%ValidTimeEnd","%");
        if (slots.size()>0) {
            for (Slot slot : slots) {
                String value = slot.getValues();
                try {
                    Document document = DocumentHelper.parseText(value);
                    Element root = document.getRootElement();
                    String time = root.getText();
                    if (StringUtil.compareDate(time,endDate)&&!StringUtil.compareDate(time,startDate)) {
                        String strTemp = StringUtil.deleteStr2(slot.getId(),":ExtrinsicObject:Slot");
                        ids.add(strTemp);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return  ids;
    }

    /**
     * 通过文件类型查询记录
     * */
    public static Set<String> queryRegistryPackageByFileType(String type) {
        List<RegistryPackage> registryPackages = QueryDataUtil.queryRegistryPackageByType(type);
        Set<String> registryPackageId = new HashSet<String>();
        for (RegistryPackage registryPackage : registryPackages) {
            registryPackageId.add(registryPackage.getId());
        }
        return registryPackageId;
    }

    /**
     * 通过文件所有者查询
     * */
    public static Set<String> queryRegistryPackageByOwner(String owner) {
        List<RegistryPackage> registryPackages = QueryDataUtil.queryRegistryPackageByOwner(owner);
        Set<String> registryPackageId = new HashSet<String>();
        for (RegistryPackage registryPackage : registryPackages) {
            registryPackageId.add(registryPackage.getId());
        }
        return registryPackageId;
    }

    /**
     * 通过组织名称查询（Organization）
     * */
    public static Set<String> queryRegistryPackageByOrganization(String organizationName) {
        List<Organization> organizations = QueryDataUtil.queryAllOrganizations();
        List<String> organizationIds = new ArrayList<String>();
        for (Organization organization : organizations) {
            InternationalString name = organization.getName();
            if (name.getLocalizedStrings().iterator().next().getValue().contains(organizationName)) {
                organizationIds.add(organization.getId());
            }
        }
        Set<String> registryPackageIds = new HashSet<String>();
        for (String id : organizationIds) {
            registryPackageIds.add(getRegistryPackageUtil(id));
        }
        return registryPackageIds;
    }

    /**
     * 通过联系人员查询
     * */
    public static Set<String> queryRegistryPackageByPerson(String personName) {
        List<Person> people = QueryDataUtil.queryAllPersons();
        List<String> peopleIds = new ArrayList<String>();
        for (Person person : people) {
            PersonName pName = person.getPersonName();
            String firstName = "";
            String middleName = "";
            String lastName = "";
            String longName = "";
            if (pName.getFirstName()!=null&&!pName.getFirstName().equals("null")) {
                firstName = pName.getFirstName();
            }
            if (pName.getMiddleName()!=null&&!pName.getMiddleName().equals("null")) {
                middleName = pName.getMiddleName();
            }
            if (pName.getLastName()!=null&&!pName.getLastName().equals("null")) {
                lastName = pName.getLastName();
            }
            longName = firstName + middleName + lastName;
            if (longName.contains(personName)) {
                peopleIds.add(person.getId());
            }
        }
        Set<String> registryPackageIds = new HashSet<String>();
        for (String id : peopleIds) {
            registryPackageIds.add(getRegistryPackageUtil(id));
        }
        return registryPackageIds;
    }

    @Test
    public void test() {
//        List<String> result = queryRegistryPackageByEndDate("2019-09-20 08:24:59");
//        System.out.println(result);
    }
}
