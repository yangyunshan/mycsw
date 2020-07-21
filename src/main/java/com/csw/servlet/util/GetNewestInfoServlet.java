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

@WebServlet(name = "GetNewestInfoServlet")
public class GetNewestInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter printWriter = response.getWriter();
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

        printWriter.write(jsonStr);
        printWriter.flush();
        printWriter.close();
    }
}
