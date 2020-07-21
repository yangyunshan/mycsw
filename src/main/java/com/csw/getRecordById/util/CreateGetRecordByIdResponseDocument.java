package com.csw.getRecordById.util;

import com.csw.ebrim.util.ParseEbrimToDC;
import com.csw.ebrim.util.ParseToType;
import com.csw.file.util.FileOperationUtil;
import com.ebrim.model.rim.*;

public class CreateGetRecordByIdResponseDocument {

    /**
     * 生成 getRecordByID的响应文档
     *
     * @param recordId
     *            需要返回getRecordByID的响应文档的id值
     * @return
     *         返回getRecordById的Response的文档内容，空则返回不包含任何的RegistryPackage的getRecords的文档内容
     */
    public static String createGetRecordByIdResponseDocument(String recordId) {
        /*********生成需要的RegistryPackage的文档的内容，以Dublin Core元数据规范表示**********/
        String all = getRegistryPackageContent(recordId);
        String baseWebPath = FileOperationUtil.getWebPath();
        String templateFileContent = FileOperationUtil.readFileContent(baseWebPath+"templateFiles/getRecordByIdResponse.xml","UTF-8");
        int num1 = templateFileContent.lastIndexOf("<csw:Record>");
        String begin = templateFileContent.substring(0,num1);
        int num2 = templateFileContent.lastIndexOf("</csw:Record>");
        String end = templateFileContent.substring(num2+"</csw:Record>".length());
        String result = begin + all + end;
        return result;
    }

    /**
     * 根据RegistryPackage的id值获取文档的内容
     *
     * @param recordId
     *            : 查询的registrypackage的id值
     *
     * @return 返回的RegistryPacakge的内容
     */
    public static String getRegistryPackageContent(String recordId) {
        RegistryPackageType registryPackageType = ParseToType.getRegistryPackageTypeById(recordId,"full");
        String allContent = "";
        if (registryPackageType!=null) {
            allContent = ParseEbrimToDC.getRegistryPackageContent(registryPackageType);
        }
        return allContent;
    }
}
