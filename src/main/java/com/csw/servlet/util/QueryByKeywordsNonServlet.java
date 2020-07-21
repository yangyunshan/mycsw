package com.csw.servlet.util;

import com.alibaba.fastjson.JSONArray;
import com.csw.data.util.GetDetailInfo;
import com.csw.data.util.QueryDataUtil;
import com.csw.model.Slot;
import com.string.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(name = "QueryByKeywordsServlet")
public class QueryByKeywordsNonServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String keywords = request.getParameter("keywords");
        List<String> lists = StringUtil.strToListByDot(keywords);
        Set<String> ids = new HashSet<String>();

        for (String keyword : lists) {
            List<Slot> slots = QueryDataUtil.selectSlotsByFuzzNameAndValue("%Keywords","%"+keyword+"%");
            for (Slot slot : slots) {
                String strTemp = StringUtil.deleteStr2(slot.getId(),":ExtrinsicObject:Slot");
                ids.add(strTemp);
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
