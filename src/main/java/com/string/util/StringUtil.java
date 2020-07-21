package com.string.util;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author TheBloomOfYouth
 * 该类提供处理字符串的一写方法
 * */
public class StringUtil {

    /**
     * 当字符串以:package结尾时，需要去掉package。如：urn:liesmars:remotesensor:platform:CBERS-2:package
     * 则首先需要处理为：urn:liesmars:remotesensor:platform:CBERS-2
     * @param str: 待处理的字符串
     * @return 返回处理后的字符串
     * */
    public static String deletePackage(String str) {
        String result = null;
        if (str.endsWith(":package")) {
            String ss = str.substring(0,str.length()-8);
            if (ss.endsWith(":")) {
                result = ss.substring(0,ss.length()-1);
            } else {
                result = ss;
            }
        } else {
            result = str;
        }
        return result;
    }

    /**
     * 删除指定的字符串以及以后的内容
     * @param str:
     *           待处理的字符串
     * @param pattern ：
     *                匹配模式，需要删除的字符串
     * */
    public static String deleteStr2(String str,String pattern) {
        String result = "";
        if (str.contains(pattern)) {
            int index = str.indexOf(pattern);
            String ss = str.substring(0,index);
            while (ss.endsWith(":")) {
                ss = ss.substring(0,ss.length()-1);
            }
            result = ss;
        } else {
            result = str;
        }
        return result;
    }

    /**
     * 删除末尾指定的字符串
     * @param str:
     *           待处理的字符串
     * @param pattern ：
     *                匹配模式，需要删除的字符串
     * */
    public static String deleteStr(String str, String pattern) {
        String result = "";
        if (str.endsWith(pattern)) {
            String ss = str.substring(0,str.length()-pattern.length());
            while (ss.endsWith(":")) {
                ss = ss.substring(0,ss.length()-1);
            }
            result = ss;
        } else {
            result = str;
        }
        return result;
    }

    /**
     * 删除末尾指定的字符串
     * @param str:
     *           待处理的字符串
     * @param pattern ：
     *                匹配模式，需要删除的字符串
     * */
    public static String appendStr(String str,String pattern) {
        String result = "";
        if (!str.trim().endsWith(pattern)) {
            result = str.trim() + pattern;
        } else {
            result = str.trim();
        }
        return result;
    }

    /**
     * 给传感器标识符增加:package
     *
     * @param str
     *            传感器标识符
     * @return 返回增加后的传感器标识符
     */
    public static String appendPackage(String str) {
        String result = "";
        if (!str.trim().endsWith(":package")) {
            result = str.trim() + ":package";
        } else {
            result = str.trim();
        }
        return result;
    }

    /**
     * 通过给定字符串截取其中部分，当作home，如：urn:ogc:def:slot:OGC-CSW-ebRIM-Sensor::SensorType
     * 则其home为sensorType
     * @param str:输入字符串
     * @return String:返回截取后的字符串
     * */
    public static String getHome(String str) {
        String result = null;
        String[] strs = str.split(":");
        String temp = strs[strs.length-1];
        String s = str.substring(0,str.length()-temp.length()-1);
        if (s.endsWith(":")) {
            result = s.substring(0,s.length()-1);
        } else {
            result = s;
        }
        return result;
    }
    /**
     * 获取字符串中最后一个':'知乎后面的内容，在本项目中作为id使用。如：
     * 字符串：urn:liesmars:remotesensor:platform:CBERS-2，则处理后结果为：CBERS-2
     * @param str:待处理的字符串
     * @return 返回处理后的字符串
     * */
    public static String getId(String str) {
        int index = str.lastIndexOf(":");
        return str.substring(index+1,str.length());
    }

    /**
     * 检验字符是否为空或不存在，如果是返回null，否则返回 原本的string
     *
     * @param str
     * @return
     */
    public static String checkStringIsNullOrEmpty(String str) {
        if (str==null || str.trim().length()==0) {
            return null;
        } else{
            return str.trim();
        }
    }

    /**
     * 将以逗号分割的字符串转化为List输出
     * */
    public static List<String> strToListByDot(String str) {
        List<String> result = new ArrayList<String>();
        if (str.contains(",")) {
            String[] temp = str.trim().split(",");
            for (String s : temp) {
                result.add(s.trim());
            }
        } else {
            result.add(str.trim());
        }
        return result;
    }

    /**
     * 时间比较
     * 时间格式为：YYYY-MM-DD hh:mm:ss
     *
     * date1在date2之前返回true，否则返回false
     * */
    public static boolean compareDate(String date1, String date2) {
        boolean flag = false;
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
        try {
            Date beginDate = format.parse(date1);
            Date endDate = format.parse(date2);
            flag = beginDate.before(endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Test
    public void test() {
        boolean result = compareDate("2017-08-24 00:00:00","2018-09-10 00:00:00");
        System.out.println(result);
    }
}
