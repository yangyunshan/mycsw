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

@WebServlet(name = "GetInfoCountServlet")
public class GetInfoCountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        String user = (String) request.getSession().getAttribute("user");

        int total = QueryDataUtil.queryCountOfRecords();
        int geologicalHazardDataCount = QueryDataUtil.getCountOfRecordsByFileType("geologicalHazardData");
        int waterResourcesDataCount = QueryDataUtil.getCountOfRecordsByFileType("waterResourcesData");
        int ecologicalDataCount = QueryDataUtil.getCountOfRecordsByFileType("ecologicalData");
        int climateDataCount = QueryDataUtil.getCountOfRecordsByFileType("climateData");
        int environmentDataCount = QueryDataUtil.getCountOfRecordsByFileType("environmentData");
        int stratigraphicPaleontologyDataCount = QueryDataUtil.getCountOfRecordsByFileType("stratigraphicPaleontologyData");
        int mineralResourceDataCount = QueryDataUtil.getCountOfRecordsByFileType("mineralResourceData");
        int energyDataCount = QueryDataUtil.getCountOfRecordsByFileType("energyData");
        int literaturePictureDataCount = QueryDataUtil.getCountOfRecordsByFileType("literaturePictureData");
        int keyBandDataCount = QueryDataUtil.getCountOfRecordsByFileType("keyBandData");

        Map<String,String> infos = new HashMap<String, String>();
        infos.put("total",total+"");
        infos.put("geologicalHazardDataCount",geologicalHazardDataCount+"");
        infos.put("waterResourcesDataCount",waterResourcesDataCount+"");
        infos.put("ecologicalDataCount",ecologicalDataCount+"");
        infos.put("climateDataCount",climateDataCount+"");
        infos.put("environmentDataCount",environmentDataCount+"");
        infos.put("stratigraphicPaleontologyDataCount",stratigraphicPaleontologyDataCount+"");
        infos.put("mineralResourceDataCount",mineralResourceDataCount+"");
        infos.put("energyDataCount",energyDataCount+"");
        infos.put("literaturePictureDataCount",literaturePictureDataCount+"");
        infos.put("keyBandDataCount",keyBandDataCount+"");

        String jsonStr = JSONArray.toJSONString(infos);
        printWriter.write(jsonStr);
        printWriter.flush();
        printWriter.close();
    }
}
