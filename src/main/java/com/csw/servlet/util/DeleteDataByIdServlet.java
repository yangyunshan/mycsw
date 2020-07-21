package com.csw.servlet.util;

import com.alibaba.fastjson.JSONArray;
import com.csw.model.Data;
import com.csw.data.util.DeleteDataUtil;
import com.csw.data.util.QueryDataUtil;
import com.csw.file.util.FileOperationUtil;
import com.csw.transaction.util.CreateTransactionResponseDocument;

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

@WebServlet(name = "DeleteDataByIdServlet")
public class DeleteDataByIdServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
//        response.setContentType("text/html;charset=utf-8");
        PrintWriter printWriter = response.getWriter();

        String dataId = request.getParameter("id");
        String result = "";
        result = CreateTransactionResponseDocument.createTransactionDeleteResponseDocument(dataId);
        String count = result.substring(result.indexOf("<csw:totalDeleted>")+"<csw:totalDeleted>".length(),result.indexOf("</csw:totalDeleted>"));

        List<Data> datas = QueryDataUtil.selectDataInfoById(dataId);
        for (Data data : datas) {
            FileOperationUtil.deleteFile(data.getPath());
        }
        DeleteDataUtil.deleteDataInfoById(dataId);

        String jsonStr = "";
        Map<String,String> temp = new HashMap<String, String>();
        if (Integer.valueOf(count)>0) {
            temp.put("deleteCount",count);
        } else {
            DeleteDataUtil.deleteDataInfoById(dataId);
            temp.put("deleteCount","0");
        }
        jsonStr = JSONArray.toJSONString(temp);
        printWriter.write(jsonStr);
        printWriter.flush();
        printWriter.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
