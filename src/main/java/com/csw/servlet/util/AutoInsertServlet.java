package com.csw.servlet.util;

import com.alibaba.fastjson.JSONArray;
import com.csw.model.Data;
import com.csw.data.util.DeleteDataUtil;
import com.csw.data.util.InsertDataUtil;
import com.csw.data.util.QueryDataUtil;
import com.csw.ebrim.util.ParseEbrimContent;
import com.csw.file.util.FileOperationUtil;
import com.csw.transaction.util.CreateTransactionResponseDocument;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AutoInsertServlet")
public class AutoInsertServlet extends HttpServlet {
    private String filePath;
    private String tempPath;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        String basePath = request.getSession().getServletContext().getRealPath("WEB-INF");
        filePath = basePath+"/"+"data";
        tempPath = basePath+"/"+"temp";

        try {
            if (ServletFileUpload.isMultipartContent(request)) {
                DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
                ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);

                List<FileItem> fileItems = servletFileUpload.parseRequest(request);
                Map<String,String> metaData = FileOperationUtil.processFormData(fileItems);
                String dataType = metaData.get("dataType");

                String owner = (String)request.getSession().getAttribute("user");

                String fileContent = "";
                String wholePath = "";//文件保存的全路径
                String dataId = "";
                int status = 0;//记录数据插入的状态
                String result = "";//源数据插入后返回的状态

                Iterator itemIterator = fileItems.iterator();
                while (itemIterator.hasNext()) {
                    FileItem fileItem = (FileItem)itemIterator.next();
                    if (!fileItem.isFormField() && fileItem.getFieldName().equals("metaDataFile") && fileItem.getName().length() > 0) {
                        fileContent = FileOperationUtil.getContentFromRequest(fileItem.getInputStream());
                    }
                }

                if (fileContent.equals("")) {
                    return;
                }
                dataId = ParseEbrimContent.getIdFromEbrim(fileContent);
                if (QueryDataUtil.checkRegistryPackageIsExist(dataId)||dataId.equals("")) {
                    System.out.println("数据已存在");
                    return;
                }
                switch (Integer.valueOf(dataType)) {
                    case 1:{
                        result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(fileContent,"Vector",owner);
                        break;
                    }
                    case 2:{
                        result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(fileContent,"Raster",owner);
                        break;
                    }
                    case 3:{
                        result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(fileContent,"Stream",owner);
                        break;
                    }
                    case 4:{
                        result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(fileContent,"Text",owner);
                        break;
                    }
                    case 5:{
                        result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(fileContent,"Chart",owner);
                        break;
                    }
                    case 6:{
                        result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(fileContent,"Sensor",owner);
                        break;
                    }
                }
                String count = result.substring(result.indexOf("<csw:totalInserted>")+"<csw:totalInserted>".length(),result.indexOf("</csw:totalInserted>"));
                if (Integer.valueOf(count)>0) {
                    while (itemIterator.hasNext()) {
                        FileItem fileItem = (FileItem)itemIterator.next();
                        if (!fileItem.isFormField()&&fileItem.getFieldName().equals("sourceFile")&&fileItem.getName().length()>0) {
                            System.out.println("处理上传的文件...");
                            Data data = new Data();
                            data.setId(dataId);
                            switch (Integer.valueOf(dataType)) {
                                case 1: {
                                    wholePath = FileOperationUtil.processUploadFile(fileItem,printWriter,filePath+"/"+"vector");
                                    data.setType("vector");
                                    data.setPath(wholePath);
                                }
                                case 2: {
                                    wholePath = FileOperationUtil.processUploadFile(fileItem,printWriter,filePath+"/"+"raster");
                                    data.setType("raster");
                                    data.setPath(wholePath);
                                }
                                case 3: {
                                    wholePath = FileOperationUtil.processUploadFile(fileItem,printWriter,filePath+"/"+"stream");
                                    data.setType("stream");
                                    data.setPath(wholePath);
                                }
                                case 4: {
                                    wholePath = FileOperationUtil.processUploadFile(fileItem,printWriter,filePath+"/"+"text");
                                    data.setType("text");
                                    data.setPath(wholePath);
                                }
                                case 5: {
                                    wholePath = FileOperationUtil.processUploadFile(fileItem,printWriter,filePath+"/"+"chart");
                                    data.setType("chart");
                                    data.setPath(wholePath);
                                }
                                case 6: {
                                    wholePath = FileOperationUtil.processUploadFile(fileItem,printWriter,filePath+"/"+"sensor");
                                    data.setType("sensor");
                                    data.setPath(wholePath);
                                }
                            }
                            status = InsertDataUtil.insertDataInfo(data);
                        }
                    }
                }
                String jsonStr = "";
                Map<String,String> temp = new HashMap<String, String>();
                if (status>0) {
                    temp.put("insertCount",count);
                } else {
                    DeleteDataUtil.deleteDataInfoById(dataId);
                    temp.put("insertCount","0");
                }
                jsonStr = JSONArray.toJSONString(temp);
                printWriter.write(jsonStr);
                printWriter.flush();
                printWriter.close();
            } else {
                throw new IllegalArgumentException("上传内容不是有效的multipart/form-data类型.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
