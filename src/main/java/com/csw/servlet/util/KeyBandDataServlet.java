package com.csw.servlet.util;

import com.alibaba.fastjson.JSONArray;
import com.csw.data.util.QueryDataUtil;
import com.csw.model.RegistryPackage;
import com.string.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class KeyBandDataServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        String user = (String) request.getSession().getAttribute("user");
        String currentPage = request.getParameter("currentPage");
        int pageNum = 1;
        if (currentPage!=null) {
            pageNum = Integer.valueOf(currentPage);
        }
        int pageCount = 12;

        List<Map<String,String>> results = GetSpecificData.getData("keyBandData",pageCount,pageNum);

        String jsonStr = JSONArray.toJSONString(results);
        printWriter.write(jsonStr);
        printWriter.flush();
        printWriter.close();
    }
}
