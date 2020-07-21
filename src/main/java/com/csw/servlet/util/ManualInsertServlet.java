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

@WebServlet(name = "ManualInsertServlet")
public class ManualInsertServlet extends HttpServlet {

    private String filePath;
    private String tempPath;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setHeader("content-type","text/html;charset=UTF-8");
//        response.setContentType("text/html;charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        String basePath = request.getSession().getServletContext().getRealPath("WEB-INF");
        filePath = basePath+"/"+"data";
        tempPath = basePath+"/"+"temp";

        File tempFile = new File(tempPath);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }

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
            if (QueryDataUtil.checkRegistryPackageIsExist(dataId)) {
                System.out.println("数据已存在");
                return;
            }

            String ebrimContent = Info2Ebrim.parseInfo2Ebrim(dataType,dataId,description,keywords,name,format,size,resolution,north,west,south,east,startTime,endTime);
            switch (Integer.valueOf(dataType)) {
                case 1:{
                    result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(ebrimContent,"geologicalHazardData",owner);
                    break;
                }
                case 2:{
                    result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(ebrimContent,"waterResourcesData",owner);
                    break;
                }
                case 3:{
                    result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(ebrimContent,"ecologicalData",owner);
                    break;
                }
                case 4:{
                    result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(ebrimContent,"climateData",owner);
                    break;
                }
                case 5:{
                    result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(ebrimContent,"environmentData",owner);
                    break;
                }
                case 6:{
                    result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(ebrimContent,"keyBandData",owner);
                    break;
                }
                case 7:{
                    result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(ebrimContent,"stratigraphicPaleontologyData",owner);
                    break;
                }
                case 8:{
                    result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(ebrimContent,"mineralResourceData",owner);
                    break;
                }
                case 9:{
                    result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(ebrimContent,"energyData",owner);
                    break;
                }
                case 10:{
                    result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(ebrimContent,"literaturePictureData",owner);
                    break;
                }
            }

            String count = result.substring(result.indexOf("<csw:totalInserted>")+"<csw:totalInserted>".length(),result.indexOf("</csw:totalInserted>"));
            //当元数据插入成功后，执行源数据的上传操作
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
                                wholePath = FileOperationUtil.processUploadFile(fileItem,printWriter,filePath+"/"+"geologicalHazardData");
                                if (wholePath.equals("")) {
                                    return;
                                }
                                data.setType("geologicalHazardData");
                                data.setPath(wholePath);
                                break;
                            }
                            case 2: {
                                wholePath = FileOperationUtil.processUploadFile(fileItem,printWriter,filePath+"/"+"waterResourcesData");
                                if (wholePath.equals("")) {
                                    return;
                                }
                                data.setType("waterResourcesData");
                                data.setPath(wholePath);
                                break;
                            }
                            case 3: {
                                wholePath = FileOperationUtil.processUploadFile(fileItem,printWriter,filePath+"/"+"ecologicalData");
                                if (wholePath.equals("")) {
                                    return;
                                }
                                data.setType("ecologicalData");
                                data.setPath(wholePath);
                                break;
                            }
                            case 4: {
                                wholePath = FileOperationUtil.processUploadFile(fileItem,printWriter,filePath+"/"+"climateData");
                                if (wholePath.equals("")) {
                                    return;
                                }
                                data.setType("climateData");
                                data.setPath(wholePath);
                                break;
                            }
                            case 5: {
                                wholePath = FileOperationUtil.processUploadFile(fileItem,printWriter,filePath+"/"+"environmentData");
                                if (wholePath.equals("")) {
                                    return;
                                }
                                data.setType("environmentData");
                                data.setPath(wholePath);
                                break;
                            }
                            case 6: {
                                wholePath = FileOperationUtil.processUploadFile(fileItem,printWriter,filePath+"/"+"keyBandData");
                                if (wholePath.equals("")) {
                                    return;
                                }
                                data.setType("keyBandData");
                                data.setPath(wholePath);
                                break;
                            }
                            case 7: {
                                wholePath = FileOperationUtil.processUploadFile(fileItem,printWriter,filePath+"/"+"stratigraphicPaleontologyData");
                                if (wholePath.equals("")) {
                                    return;
                                }
                                data.setType("stratigraphicPaleontologyData");
                                data.setPath(wholePath);
                                break;
                            }
                            case 8: {
                                wholePath = FileOperationUtil.processUploadFile(fileItem,printWriter,filePath+"/"+"mineralResourceData");
                                if (wholePath.equals("")) {
                                    return;
                                }
                                data.setType("mineralResourceData");
                                data.setPath(wholePath);
                                break;
                            }
                            case 9: {
                                wholePath = FileOperationUtil.processUploadFile(fileItem,printWriter,filePath+"/"+"energyData");
                                if (wholePath.equals("")) {
                                    return;
                                }
                                data.setType("energyData");
                                data.setPath(wholePath);
                                break;
                            }
                            case 10: {
                                wholePath = FileOperationUtil.processUploadFile(fileItem,printWriter,filePath+"/"+"literaturePictureData");
                                if (wholePath.equals("")) {
                                    return;
                                }
                                data.setType("literaturePictureData");
                                data.setPath(wholePath);
                                break;
                            }
                        }
                        System.out.println("文件上传成功");
                        status = InsertDataUtil.insertDataInfo(data);
                    }
                }
            }

            String jsonStr = "";
            Map<String,Object> temp = new HashMap<String, Object>();
            if (status>0) {
                temp.put("insertCount",count);
            } else {
                DeleteDataUtil.deleteDataInfoById(dataId);
                temp.put("insertCount","0");
            }
            jsonStr = JSONArray.toJSONString(temp);
            System.out.println(jsonStr);
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
