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

@WebServlet(name = "QueryResultByPageServlet")
public class QueryResultByPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String ids = request.getParameter("ids");
        String currentPage = request.getParameter("currentPage");
        String[] allIds = ids.split(",");
        int pageNum = 1;
        if (currentPage!=null) {
            pageNum = Integer.valueOf(currentPage);
        }

        int pageCount = 10;
        List<RegistryPackage> registryPackages = new ArrayList<RegistryPackage>();

        for (int i=(pageNum-1)*pageCount;i<allIds.length;i++) {
            List<RegistryPackage> rTemp = QueryDataUtil.selectRegistryPackageById(StringUtil.appendPackage(allIds[i]));
            if (rTemp.size()>0) {
                registryPackages.add(rTemp.get(0));
            }
        }

        List<Map<String,Object>> results = new ArrayList<Map<String, Object>>();
        for (RegistryPackage registryPackage : registryPackages) {
            Map<String,Object> result = new HashMap<String, Object>();
            String id = registryPackage.getId();
            String name = QueryDataUtil.selectLocalizedStringsBy_Id(StringUtil.deletePackage(id)+":Name").get(0).getValue();
            result.put("id",id);
            result.put("name",name);
            result.put("time",QueryDataUtil.selectTimeByRegistryPackageId(id));
            result.put("type",registryPackage.getType());
            results.add(result);
        }

        String jsonStr = JSONArray.toJSONString(results);

        System.out.println(jsonStr);
        PrintWriter pw = response.getWriter();
        pw.write(jsonStr);
        pw.flush();
        pw.close();
    }
}
