package com.csw.servlet.util;

import com.alibaba.fastjson.JSONArray;
import com.csw.model.Data;
import com.csw.data.util.GetDetailInfo;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "PropertiesSingleServlet")
public class PropertiesSingleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("content-type","text/html;charset=UTF-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter printWriter = response.getWriter();

        String id = request.getParameter("id");
        Map<String,String> result = new HashMap<String, String>();

        List<RegistryPackage> registryPackages = QueryDataUtil.selectRegistryPackageById(id);

        String name = GetDetailInfo.getName(id);
        String type = registryPackages.get(0).getType();
        String time = QueryDataUtil.selectTimeByRegistryPackageId(id);
        String owner = registryPackages.get(0).getOwner();
        String keywords = GetDetailInfo.getKeywords(id);
        String description = GetDetailInfo.getDescription(id);
        String[] envelope = GetDetailInfo.getEnvelope(id);
        String[] startAndEndTime = GetDetailInfo.getStartAndEndTime(id);

        result.put("name",name);
        result.put("type",type);
        result.put("time",time);
        result.put("owner",owner);
        result.put("keywords",keywords);
        result.put("description",description);
        result.put("envelope","("+envelope[0]+"),("+envelope[1]+")");
        result.put("startAndEndTime",startAndEndTime[0]+"---"+startAndEndTime[1]);

        List<Data> datas = QueryDataUtil.selectDataInfoById(StringUtil.deletePackage(id));
        String fileNames = "";
        for (Data data : datas) {
            String totalPath = data.getPath();
            fileNames += totalPath+" ";
        }
        result.put("fileNames",fileNames.trim());

        String jsonStr = JSONArray.toJSONString(result);

        printWriter.write(jsonStr);
        printWriter.flush();
        printWriter.close();
    }
}
