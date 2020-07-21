package com.csw.springmvc.controller;

import com.alibaba.fastjson.JSONArray;
import com.csw.data.util.QueryDataUtil;
import com.csw.model.RegistryPackage;
import com.string.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @RequestMapping(value = "/getNewestInfo")
    public void getNewestInfo(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        String user = (String) request.getSession().getAttribute("user");

        List<Map<String,String>> allInfos = new ArrayList<Map<String, String>>();
        List<RegistryPackage> registryPackages = QueryDataUtil.selectNewestRegistryPackageLimited(8,0);
        for (RegistryPackage registryPackage : registryPackages) {
            Map<String,String> infos = new HashMap<String, String>();
            String id = registryPackage.getId();
            String name = QueryDataUtil.selectLocalizedStringsBy_Id(StringUtil.deletePackage(id)+":Name").get(0).getValue();
            infos.put("id",id);
            infos.put("name",name);
            infos.put("time",QueryDataUtil.selectTimeByRegistryPackageId(id));
            infos.put("type",registryPackage.getType());
            allInfos.add(infos);
        }
        String jsonStr = JSONArray.toJSONString(allInfos);

        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.write(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            printWriter.flush();
            printWriter.close();
        }
    }

    @RequestMapping(value = "/header")
    public void getHeader(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");

        String name = (String) request.getSession().getAttribute("user");

        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            if (name!=null) {
                pw.write(name);
            } else {
                pw.write("null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pw.flush();
            pw.close();
        }
    }

    @RequestMapping(value = "/getInfoCount")
    public void getInfoCount(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
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

        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.write(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            printWriter.flush();
            printWriter.close();
        }
    }
}
