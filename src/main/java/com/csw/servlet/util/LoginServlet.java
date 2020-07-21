package com.csw.servlet.util;

import com.csw.getRecordsExtend.Util.CreateGetRecordsExtendResponseDocument;
import com.csw.user.util.VerifyUserActionUtil;
import com.string.util.ParseXMLToHtmlUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        String url = request.getParameter("referrer");
        boolean flag = VerifyUserActionUtil.verifyUser(user,password);
        if (flag) {
            request.getSession().setAttribute("user",user);
            response.sendRedirect("../index.html");
        } else {
            request.getSession().setAttribute("message","error");
            response.sendRedirect("login.html");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
