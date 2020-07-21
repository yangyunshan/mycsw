package com.string.util;

import com.csw.getRecordById.util.CreateGetRecordByIdResponseDocument;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

import java.util.Iterator;

public class ParseXMLToHtmlUtil {

    /**
     * 解析xml字符串，将其信息以表格的形式展示到网页中
     * */
    public static String parseXMLToHtml(String strXML) {
        String begin = "<div>";
        String end = "</div>";
        String tableBegin = "<table border='1'><tr><th>序号</th><th>预览</th><th>信息</th></tr>";
        String htmlHeader = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>QueryResult</title>\n" +
                "</head>\n" +
                "<body>";
        String htmlEnd = "</body>\n" +
                "</html>";
        String result = "";
        try {
            Document document =DocumentHelper.parseText(strXML);
            Element root = document.getRootElement();
            for (Iterator i = root.elementIterator();i.hasNext();) {
                Element element = (Element) i.next();
                if (element.getName().equals("Record")) {
                    String queryResult1 = parseRecordNodeToHtml(element);
                    result += "<tr><td>1</td><td><img src='' alt='resource'></td><td>"+queryResult1+"</td></tr><table>";
                }
                if (element.getName().equals("SearchResults")) {
                    int count = 0;
                    for (Iterator j = element.elementIterator();j.hasNext();) {
                        Element element1 = (Element) j.next();
                        String queryResult = parseRecordNodeToHtml(element1);
                        count++;
                        result += "<tr><td>"+count+"</td><td><img src='' alt='resource'></td><td>"+queryResult+"</td></tr><table>";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return htmlHeader + begin + tableBegin + result + end + htmlEnd;
    }

    public static String parseRecordNodeToHtml(Element element) {
        String result = "";
        String identifier = "<b style=\"color:red\">ID:</b><b>";
        String subject = "<b style=\"color:red\">Keywords:</b><b>";
        String description = "<b style=\"color:red\">Description:</b><b>";
        String boundingBox = "<b style=\"color:red\">BoundingBox:</b><b>";
        String coverage = "<b style=\"color:red\">Coverage:</b><b>";
        String relation = "<b style=\"color:red\">Relation:</b><b>";
        String type = "<b style=\"color:red\">Type:</b><b>";
        String creator = "<b style=\"color:red\">Creator:</b><b>";
        for (Iterator i = element.elementIterator();i.hasNext();) {
            Element node = (Element) i.next();
            if (node.getName().equals("identifier")) {
                identifier += node.getText() + "、";
            }
            if (node.getName().equals("subject")) {
                subject += node.getText() + "、";
            }
            if (node.getName().equals("description")) {
                description += node.getText() + "、";
            }
            if (node.getName().equals("type")) {
                type += node.getText() + "、";
            }
            if (node.getName().equals("creator")) {
                creator += node.getText() + "、";
            }
            if (node.getName().equals("coverage")) {
                coverage += node.getText() + "、";
            }
            if (node.getName().equals("BoundingBox")) {
                String[] corner = new String[2];
                int num = 0;
                for (Iterator j = node.elementIterator();j.hasNext();) {
                    Element envelope = (Element)j.next();
                    corner[num] = envelope.getText();
                    num++;
                }
                String x1 = corner[0].split(" ")[0];
                String y1 = corner[0].split(" ")[1];
                String x2 = corner[1].split(" ")[0];
                String y2 = corner[1].split(" ")[1];
                boundingBox += x1+" "+y1+" "+x2+" "+y2 + "、";
            }
            if (node.getName().equals("relation")) {
                relation += node.getText() + "、";
            }
        }
        result = deleteDotAndAddbr(identifier)+deleteDotAndAddbr(subject)+deleteDotAndAddbr(description)+deleteDotAndAddbr(coverage)+
                deleteDotAndAddbr(type)+deleteDotAndAddbr(creator)+deleteDotAndAddbr(boundingBox)+deleteDotAndAddbr(relation);
        return result;
    }

    public static String deleteDotAndAddbr(String str) {
        String result = "";
        if (str.trim().endsWith("、")) {
            result = str.trim().substring(0,str.trim().length()-1);
        } else {
            result = str.trim();
        }
        return result+"</b><br>";
    }

}
