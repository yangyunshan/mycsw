package com.csw.servlet.util;

import com.alibaba.fastjson.JSONArray;
import com.csw.data.util.QueryDataUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "GetAllRecordsNumServlet")
public class GetAllRecordsNumServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter printWriter = response.getWriter();

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
        printWriter.write(jsonObj);
        printWriter.flush();
        printWriter.close();
    }
}
