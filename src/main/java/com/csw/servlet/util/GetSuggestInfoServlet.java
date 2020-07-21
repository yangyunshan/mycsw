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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "GetSuggestInfoServlet")
public class GetSuggestInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter printWriter = response.getWriter();

        String data = request.getParameter("data");
        Map<String,List> infos = new HashMap<String, List>();
        List<String> suggestIds = new ArrayList<String>();
        List<String> ids = QueryDataUtil.selectRegistryPackageByFuzzId(data);
        if (ids.size()>0) {
            for (String id : ids) {
                suggestIds.add(id);
            }
            infos.put("suggestInfos",suggestIds);
        } else {
            return;
        }

        String jsonStr = JSONArray.toJSONString(suggestIds);
        printWriter.write(jsonStr);
        printWriter.flush();
        printWriter.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
