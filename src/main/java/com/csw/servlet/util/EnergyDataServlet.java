package com.csw.servlet.util;

import com.alibaba.fastjson.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ChartDataServlet")
public class EnergyDataServlet extends HttpServlet {
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

        List<Map<String,String>> results = GetSpecificData.getData("energyData",pageCount,pageNum);

        String jsonStr = JSONArray.toJSONString(results);
        printWriter.write(jsonStr);
        printWriter.flush();
        printWriter.close();
    }
}
