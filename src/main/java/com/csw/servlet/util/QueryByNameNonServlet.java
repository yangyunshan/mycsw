package com.csw.servlet.util;

import com.alibaba.fastjson.JSONArray;
import com.csw.data.util.GetDetailInfo;
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
import java.util.*;

@WebServlet(name = "registry")
public class QueryByNameNonServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String name = request.getParameter("name");

        List<LocalizedString> localizedStrings = QueryDataUtil.selectLocalizedStringsByFuzzIdAndValue("%:Name","%"+name+"%");
        Set<String> ids = new HashSet<String>();
        if (localizedStrings.size()>0) {
            for (LocalizedString localizedString : localizedStrings) {
                String id = StringUtil.deleteStr(localizedString.getId(),":Name");
                List<RegistryPackage> registryPackages = QueryDataUtil.selectRegistryPackageById(StringUtil.appendPackage(id));
                if (registryPackages.size()>0) {
                    ids.add(id);
                }
            }
        }

        Set<String> _ids = new HashSet<String>();
        for (String id : ids) {
            String[] envelope = GetDetailInfo.getEnvelope(id);
            String coordinate = envelope[0]+" "+envelope[1];
            if (coordinate.equals("none none none none")) {
                _ids.add(id);
            }
        }

        Map<String,Object> result = new HashMap<String, Object>();
        result.put("allIds",_ids);

        String jsonStr = JSONArray.toJSONString(result);
        PrintWriter pw = response.getWriter();
        pw.write(jsonStr);
        pw.flush();
        pw.close();
    }
}
