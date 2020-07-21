package com.csw.ebrim.util;

import org.json.JSONObject;
import org.json.XML;

public class ParseDCUtil {

    public static String xml2JsonUtil(String xmlStr) {
        JSONObject xmlJSON = XML.toJSONObject(xmlStr);
        return xmlJSON.toString();
    }
}
