package com.csw.servlet.util;

import com.alibaba.fastjson.JSONArray;
import com.csw.data.util.QueryDataUtil;
import com.csw.model.LocalizedString;
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

@WebServlet(name = "GetMoreNewestServlet")
public class GetMoreNewestServlet extends HttpServlet {
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
        List<Map<String,String>> results = new ArrayList<Map<String, String>>();
        List<RegistryPackage> registryPackages = QueryDataUtil.selectNewestRegistryPackageLimited(pageCount,(pageNum-1)*pageCount);
        for (RegistryPackage registryPackage : registryPackages) {
            Map<String,String> result = new HashMap<String, String>();
            String id = registryPackage.getId();
            List<LocalizedString> localizedStrings = QueryDataUtil.selectLocalizedStringsBy_Id(StringUtil.deletePackage(id)+":Name");
            String name = localizedStrings.get(0).getValue();
            result.put("id",StringUtil.deletePackage(id));
            result.put("name",name);
            result.put("type",registryPackage.getType());
            result.put("owner",registryPackage.getOwner());
            result.put("time",QueryDataUtil.selectTimeByRegistryPackageId(id));
            results.add(result);
        }
        String jsonStr = JSONArray.toJSONString(results);
        printWriter.write(jsonStr);
        printWriter.flush();
        printWriter.close();
    }
}
