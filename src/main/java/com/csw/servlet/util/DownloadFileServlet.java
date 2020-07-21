package com.csw.servlet.util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@WebServlet(name = "DownloadFileServlet")
public class DownloadFileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String user = (String) request.getSession().getAttribute("user");

        String filePath = request.getParameter("filePath");
        String[] filePaths = filePath.split(" ");
        for (String path : filePaths) {
            File file = new File(path);
            if (!file.exists()) {
                return;
            }
            String realName = path.substring(path.lastIndexOf("/")+1);
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realName, "UTF-8"));
            InputStream in = new FileInputStream(path);
            OutputStream out = response.getOutputStream();
            //写文件
            byte[] buffer = new byte[1024];
            int len;
            while ((len=in.read(buffer))!=-1) {
                out.write(buffer,0,len);
            }
            in.close();
            out.close();
        }
    }
}
