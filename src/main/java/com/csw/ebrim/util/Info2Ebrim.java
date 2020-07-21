package com.csw.ebrim.util;

import com.csw.file.util.FileOperationUtil;
import com.csw.util.GetSystemInfoUtil;
import com.string.util.StringUtil;
import org.junit.Test;

import java.util.List;

public class Info2Ebrim {
    /**
     * 将各个节点信息转换为完整的ebrim内容
     * */
    public static String parseInfo2Ebrim(String dataType,String dataId,String description,
                                         String keywords,String name,String format,
                                         String size,String resolution,
                                         String north,String west,String south,String east,
                                         String startTime, String endTime) {
        String basePath = FileOperationUtil.getWebPath();
        String content = FileOperationUtil.readFileContent(basePath+"templateFiles/ebrim.xml","utf-8");

        String registryPackageHeader = "";
        String extrinsicObject = "";

        if (dataId!=null&&!dataId.equals("")) {
            String registryPackageHeaderTemp = content.substring(0,(content.indexOf("<rim:RegistryObjectList>")+"<rim:RegistryObjectList>".length()));
            StringBuilder headerStringBuilder = new StringBuilder(registryPackageHeaderTemp);
            headerStringBuilder.insert(headerStringBuilder.indexOf("id=\"")+4, StringUtil.appendPackage(dataId));
            registryPackageHeader = headerStringBuilder.toString();

            String extrinsicObjectHeaderTemp = "<wrs:ExtrinsicObject xmlns:wrs=\"http://www.opengis.net/cat/wrs/1.0\" mimeType=\"application/xml\" objectType=\"\" lid=\"\" id=\"\">";
            StringBuilder stringBuilder = new StringBuilder(extrinsicObjectHeaderTemp);
            if (dataType!=null) {
                stringBuilder.insert(stringBuilder.indexOf("lid=\"")+"lid=\"".length(),StringUtil.deleteStr2(dataId,":package"));
                stringBuilder.insert(stringBuilder.lastIndexOf("id=\"")+"id=\"".length(),StringUtil.deleteStr2(dataId,":package"));
                switch (Integer.valueOf(dataType)) {
                    case 1: {
                        String objectType = "urn:ogc:def:objectType:OGC-CSW-ebRIM-Vector";
                        stringBuilder.insert(stringBuilder.indexOf("objectType=\"") + "objectType=\"".length(), objectType);

                        extrinsicObject = stringBuilder.toString();
                        extrinsicObject += parseCore2Ebrim(objectType,description,keywords,name,format,size,resolution,north,west,south,east,startTime,endTime);

                        break;
                    }
                    case 2: {
                        String objectType = "urn:ogc:def:objectType:OGC-CSW-ebRIM-Raster";
                        stringBuilder.insert(stringBuilder.indexOf("objectType=\"")+"objectType=\"".length(),objectType);

                        extrinsicObject = stringBuilder.toString();
                        extrinsicObject += parseCore2Ebrim(objectType,description,keywords,name,format,size,resolution,north,west,south,east,startTime,endTime);

                        break;
                    }
                    case 3: {
                        String objectType = "urn:ogc:def:objectType:OGC-CSW-ebRIM-Stream";
                        stringBuilder.insert(stringBuilder.indexOf("objectType=\"")+"objectType=\"".length(),objectType);

                        extrinsicObject = stringBuilder.toString();
                        extrinsicObject += parseCore2Ebrim(objectType,description,keywords,name,format,size,resolution,north,west,south,east,startTime,endTime);

                        break;
                    }
                    case 4: {
                        String objectType = "urn:ogc:def:objectType:OGC-CSW-ebRIM-Text";
                        stringBuilder.insert(stringBuilder.indexOf("objectType=\"")+"objectType=\"".length(),objectType);

                        extrinsicObject = stringBuilder.toString();
                        extrinsicObject += parseCore2Ebrim(objectType,description,keywords,name,format,size,resolution,north,west,south,east,startTime,endTime);

                        break;
                    }
                    case 5: {
                        String objectType = "urn:ogc:def:objectType:OGC-CSW-ebRIM-Chart";
                        stringBuilder.insert(stringBuilder.indexOf("objectType=\"")+"objectType=\"".length(),objectType);

                        extrinsicObject = stringBuilder.toString();
                        extrinsicObject += parseCore2Ebrim(objectType,description,keywords,name,format,size,resolution,north,west,south,east,startTime,endTime);

                        break;
                    }
                    case 6: {
                        String objectType = "urn:ogc:def:objectType:OGC-CSW-ebRIM-Sensor";
                        stringBuilder.insert(stringBuilder.indexOf("objectType=\"")+"objectType=\"".length(),objectType);

                        extrinsicObject = stringBuilder.toString();
                        extrinsicObject += parseCore2Ebrim(objectType,description,keywords,name,format,size,resolution,north,west,south,east,startTime,endTime);

                        break;
                    }
                }
            }
            extrinsicObject += "</wrs:ExtrinsicObject>";
        }

        String foot = "</rim:RegistryObjectList></rim:RegistryPackage>";

        return registryPackageHeader + extrinsicObject + foot;
    }

    public static String parseCore2Ebrim(String objectType,String description, String keywords,String name,
                                         String format, String size,String resolution,
                                         String north,String west,String south,String east,
                                         String startTime, String endTime) {
        String result = "";

        if (!(description.equals(""))) {
            result += parseInternationStringInfo("description",description);
        }
        if (!(name.equals(""))) {
            result += parseInternationStringInfo("name",name);
        }
        if (!(keywords.equals(""))) {
            result += parseSlotInfo("keywords",objectType,keywords);
        }
        if (!(format.equals(""))) {
            result += parseSlotInfo("format",objectType,format);
        }
        if (!(size.equals(""))) {
            result += parseSlotInfo("size",objectType,size);
        }
        if (!(resolution.equals(""))) {
            result += parseSlotInfo("resolution",objectType,resolution);
        }
        if (!(north.equals("")&&west.equals("")&&south.equals("")&&east.equals(""))) {
            String value = west + "," + south + "," + east + "," + north;
            result += parseSlotInfo("boundedby",objectType,value);
        }
        if (north.equals("")&&west.equals("")&&south.equals("")&&east.equals("")) {
            String value = "";
            result += parseSlotInfo("boundedby",objectType,value);
        }
        if (startTime!=null&&!(startTime.equals(""))) {
            result += parseSlotInfo("validtimebegin",objectType,startTime);
        }
        if (endTime!=null&&!(endTime.equals(""))) {
            result += parseSlotInfo("validtimeend",objectType,endTime);
        }

        return result;
    }

    public static String parseSlotInfo(String name, String objectType, String value) {
        String slotTemplate = "<rim:Slot name=\"\" slotType=\"\"><rim:ValueList></rim:ValueList></rim:Slot>";
        String result = "";

        if (name.toLowerCase().equals("keywords")) {
            StringBuilder stringBuilder = new StringBuilder(slotTemplate);
            stringBuilder.insert(stringBuilder.indexOf("name=\"")+"name=\"".length(),objectType+"::Keywords");
            stringBuilder.insert(stringBuilder.indexOf("slotType=\"")+"slotType=\"".length(),"urn:oasis:names:tc:ebxml-regrep:DataType:String");

            String values = "";
            List<String> keyword = StringUtil.strToListByDot(value);
            for (String word : keyword) {
                values += "<value>"+word+"</value>";
            }
            stringBuilder.insert(stringBuilder.indexOf("<rim:ValueList>")+"<rim:ValueList>".length(),values);
            result = stringBuilder.toString();
        }

        if (name.toLowerCase().equals("format")) {
            StringBuilder stringBuilder = new StringBuilder(slotTemplate);
            stringBuilder.insert(stringBuilder.indexOf("name=\"")+"name=\"".length(),objectType+"::Format");
            stringBuilder.insert(stringBuilder.indexOf("slotType=\"")+"slotType=\"".length(),"urn:oasis:names:tc:ebxml-regrep:DataType:String");

            String values = "<value>"+value.trim()+"</value>";
            stringBuilder.insert(stringBuilder.indexOf("<rim:ValueList>")+"<rim:ValueList>".length(),values);
            result = stringBuilder.toString();
        }

        if (name.toLowerCase().equals("size")) {
            StringBuilder stringBuilder = new StringBuilder(slotTemplate);
            stringBuilder.insert(stringBuilder.indexOf("name=\"")+"name=\"".length(),objectType+"::Size");
            stringBuilder.insert(stringBuilder.indexOf("slotType=\"")+"slotType=\"".length(),"urn:oasis:names:tc:ebxml-regrep:DataType:String");

            String values = "<value>"+value.trim()+"</value>";
            stringBuilder.insert(stringBuilder.indexOf("<rim:ValueList>")+"<rim:ValueList>".length(),values);
            result = stringBuilder.toString();
        }
        if (name.toLowerCase().equals("resolution")) {
            StringBuilder stringBuilder = new StringBuilder(slotTemplate);
            stringBuilder.insert(stringBuilder.indexOf("name=\"")+"name=\"".length(),objectType+"::Resolution");
            stringBuilder.insert(stringBuilder.indexOf("slotType=\"")+"slotType=\"".length(),"urn:oasis:names:tc:ebxml-regrep:DataType:String");

            String values = "<value>"+value.trim()+"</value>";
            stringBuilder.insert(stringBuilder.indexOf("<rim:ValueList>")+"<rim:ValueList>".length(),values);
            result = stringBuilder.toString();
        }

        if (name.toLowerCase().equals("validtimebegin")) {
            StringBuilder stringBuilder = new StringBuilder(slotTemplate);
            stringBuilder.insert(stringBuilder.indexOf("name=\"")+"name=\"".length(),objectType+"::ValidTimeBegin");
            stringBuilder.insert(stringBuilder.indexOf("slotType=\"")+"slotType=\"".length(),"urn:oasis:names:tc:ebxml-regrep:DataType:DataTime");

            String values = "<value>"+value.trim()+"</value>";
            stringBuilder.insert(stringBuilder.indexOf("<rim:ValueList>")+"<rim:ValueList>".length(),values);
            result = stringBuilder.toString();
        }

        if (name.toLowerCase().equals("validtimeend")) {
            StringBuilder stringBuilder = new StringBuilder(slotTemplate);
            stringBuilder.insert(stringBuilder.indexOf("name=\"")+"name=\"".length(),objectType+"::ValidTimeEnd");
            stringBuilder.insert(stringBuilder.indexOf("slotType=\"")+"slotType=\"".length(),"urn:oasis:names:tc:ebxml-regrep:DataType:DateTime");

            String values = "<value>"+value.trim()+"</value>";
            stringBuilder.insert(stringBuilder.indexOf("<rim:ValueList>")+"<rim:ValueList>".length(),values);
            result = stringBuilder.toString();
        }

        if (name.toLowerCase().equals("boundedby")) {
            StringBuilder stringBuilder = new StringBuilder(slotTemplate);
            stringBuilder.insert(stringBuilder.indexOf("name=\"")+"name=\"".length(),objectType+"::BoundedBy");
            stringBuilder.insert(stringBuilder.indexOf("slotType=\"")+"slotType=\"".length(),"urn:ogc:def:dataType:ISO-19107:2003:GM_Envelope");

            String bounded = "<wrs:AnyValue><gml:Envelope xmlns:gml=\"http://www.opengis.net/gml\" srsName=\"urn:ogc:def:crs:EPSG:4326\"><gml:lowerCorner></gml:lowerCorner><gml:upperCorner></gml:upperCorner></gml:Envelope></wrs:AnyValue>";

            String values = "";
            StringBuilder boundedByTemp = new StringBuilder(bounded);
            if (value!=null&&value!="") {
                List<String> boundedBy = StringUtil.strToListByDot(value);
                boundedByTemp.insert(boundedByTemp.indexOf("<gml:lowerCorner>")+"<gml:lowerCorner>".length(),boundedBy.get(0)+" "+boundedBy.get(1));
                boundedByTemp.insert(boundedByTemp.indexOf("<gml:upperCorner>")+"<gml:lowerCorner>".length(),boundedBy.get(2)+" "+boundedBy.get(3));
                values = "<value>"+boundedByTemp.toString()+"</value>";
            } else {
                boundedByTemp.insert(boundedByTemp.indexOf("<gml:lowerCorner>")+"<gml:lowerCorner>".length(),"none"+" "+"none");
                boundedByTemp.insert(boundedByTemp.indexOf("<gml:upperCorner>")+"<gml:lowerCorner>".length(),"none"+" "+"none");
                values = "<value>"+boundedByTemp.toString()+"</value>";
            }

            stringBuilder.insert(stringBuilder.indexOf("<rim:ValueList>")+"<rim:ValueList>".length(),values);
            result = stringBuilder.toString();
        }

        return result;
    }

    public static String parseInternationStringInfo(String name, String value) {
        String result = "";

        if (name.toLowerCase().equals("name")) {
            result = "<rim:Name><rim:LocalizedString xml:lang=\"en-US\" value=\""+value+"\""+"></rim:LocalizedString></rim:Name>";
        }

        if (name.toLowerCase().equals("description")) {
            result = "<rim:Description><rim:LocalizedString xml:lang=\"en-US\" value=\""+value+"\""+"></rim:LocalizedString></rim:Description>";
        }
        return result;
    }
}
