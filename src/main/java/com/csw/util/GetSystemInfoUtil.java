package com.csw.util;

import java.sql.Timestamp;
import java.util.Date;

public class GetSystemInfoUtil {

    /**
     * 获取系统当前时间戳，表示形式如2019-06-19T08:36:07.686Z
     * */
    public static Timestamp getTimeStamp() {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        return timestamp;
    }
}
