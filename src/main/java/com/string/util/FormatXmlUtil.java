package com.string.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.StringWriter;

public class FormatXmlUtil {

    /**
     * 格式化xml文档的内容
     */
    public static String formatXml(String str) {
        try {
            Document document = DocumentHelper.parseText(str);
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            StringWriter writer = new StringWriter();
            XMLWriter xmlWriter = new XMLWriter(writer,format);
            xmlWriter.write(document);
            xmlWriter.close();
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
