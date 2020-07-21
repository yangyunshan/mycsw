package com.csw.servlet.util;

import com.alibaba.fastjson.JSONArray;
import com.csw.model.Data;
import com.csw.data.util.DeleteDataUtil;
import com.csw.data.util.InsertDataUtil;
import com.csw.data.util.QueryDataUtil;
import com.csw.ebrim.util.Info2Ebrim;
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
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@WebServlet(name = "GetAllInfoByIdServlet")
public class UpdateDataServlet extends HttpServlet {

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
            if (!ServletFileUpload.isMultipartContent(request)) {
                throw new IllegalArgumentException("上传内容不是有效的multipart/form-data类型.");
            }

            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            diskFileItemFactory.setSizeThreshold(4*1024);
            diskFileItemFactory.setRepository(new File(tempPath));

            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
            servletFileUpload.setFileSizeMax(4*1024*1024);


            List<FileItem> fileItems = servletFileUpload.parseRequest(request);
            Map<String,String> metaData = FileOperationUtil.processFormData(fileItems);

            String owner = (String)request.getSession().getAttribute("user");

            String dataType = metaData.get("dataType");
            String dataId = metaData.get("dataId");
            String keywords = metaData.get("keywords");
            String size = metaData.get("size");
            String resolution = metaData.get("resolution");
            String format = metaData.get("format");
            String description = metaData.get("description");
            String north = metaData.get("north");
            String west = metaData.get("west");
            String south = metaData.get("south");
            String east = metaData.get("east");
            String name = metaData.get("name");
            String startTime = metaData.get("beginTime");
            String endTime = metaData.get("endTime");

            String result = "";

            String ebrimContent = Info2Ebrim.parseInfo2Ebrim(dataType,dataId,description,keywords,name,format,size,resolution,north,west,south,east,startTime,endTime);
            switch (Integer.valueOf(dataType)) {
                case 1:{
                    result = CreateTransactionResponseDocument.createTransactionUpdateResponseDocument(dataId,ebrimContent,"Vector",owner);
                    break;
                }
                case 2:{
                    result = CreateTransactionResponseDocument.createTransactionUpdateResponseDocument(dataId,ebrimContent,"Raster",owner);
                    break;
                }
                case 3:{
                    result = CreateTransactionResponseDocument.createTransactionUpdateResponseDocument(dataId,ebrimContent,"Stream",owner);
                    break;
                }
                case 4:{
                    result = CreateTransactionResponseDocument.createTransactionUpdateResponseDocument(dataId,ebrimContent,"Text",owner);
                    break;
                }
                case 5:{
                    result = CreateTransactionResponseDocument.createTransactionUpdateResponseDocument(dataId,ebrimContent,"Chart",owner);
                    break;
                }
                case 6:{
                    result = CreateTransactionResponseDocument.createTransactionUpdateResponseDocument(dataId,ebrimContent,"Sensor",owner);
                    break;
                }
            }

            String count = result.substring(result.indexOf("<csw:totalUpdated>")+"<csw:totalUpdated>".length(),result.indexOf("</csw:totalUpdated>"));
            //当元数据更新成功后，执行源数据的上传操作
            List<Data> datas = QueryDataUtil.selectDataInfoById(dataId);
            for (Data data : datas) {
                FileOperationUtil.deleteFile(data.getPath());
            }
            DeleteDataUtil.deleteDataInfoById(dataId);
            String wholePath = "";
            int status = 0;
            if (Integer.valueOf(count)>0) {
                Iterator iterator = fileItems.iterator();
                while (iterator.hasNext()) {
                    FileItem fileItem = (FileItem)iterator.next();
                    if (!fileItem.isFormField()) {
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
                temp.put("updateCount",count);
            } else {
                DeleteDataUtil.deleteDataInfoById(dataId);
                temp.put("updateCount","0");
            }
            jsonStr = JSONArray.toJSONString(temp);
            printWriter.write(jsonStr);
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
