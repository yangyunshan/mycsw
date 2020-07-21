package com.csw.springmvc.controller;

import com.alibaba.fastjson.JSONArray;
import com.csw.data.util.QueryDataUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MoreNewestInfoController {
    @RequestMapping(value = "queryAllRecordsNum")
    public void queryAllRecordsNum(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");

        String fileType = request.getParameter("fileType");

        int num;
        if (fileType!=null&&!fileType.equals("")) {
            num = QueryDataUtil.getCountOfRecordsByFileType(fileType);
        } else {
            num = QueryDataUtil.getCountOfRegistryPackageRecords();
        }

        Map<String,Integer> result = new HashMap<String, Integer>();
        result.put("number",num);
        String jsonObj = JSONArray.toJSONString(result);

        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.write(jsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            printWriter.flush();
            printWriter.close();
        }
    }

    @RequestMapping(value = "getMoreNewestInfo")
    public void getMoreNewestInfo(HttpServletRequest request, HttpServletResponse response) {

    }
}
