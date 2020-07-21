package com.csw.getRecords.util;

import com.csw.data.util.QueryDataUtil;
import com.csw.ebrim.util.ParseEbrimToDC;
import com.csw.ebrim.util.ParseToType;
import com.csw.file.util.FileOperationUtil;
import com.csw.model.RegistryPackage;
import com.csw.util.GetSystemInfoUtil;
import com.ebrim.model.rim.*;

import java.util.*;

public class CreateGetRecordsResponseDocument {

    /**
     * 创建GetRecords响应文档
     *
     * @return 返回GetRecords响应文档
     * */
    public static String createGetRecordsResponseDocument() {
        String baseWebPath = FileOperationUtil.getWebPath();
        String templateFileContent = FileOperationUtil.readFileContent(baseWebPath+"templateFiles/getRecordsResponse.xml","UTF-8");
        int num1 = templateFileContent.lastIndexOf("<csw:SearchStatus");
        String begin = templateFileContent.substring(0,num1);
        int num2 = templateFileContent.lastIndexOf("</csw:SearchResults>");
        String end = templateFileContent.substring(num2+"</csw:SearchResults>".length());

        String all = createGetRecordsResponseCoreDocument();
        String result = begin + all + end;
        return result;
    }

    /**
     * 生成GetRecords响应文档的核心内容
     *
     * @return String
     *
     */
    public static String createGetRecordsResponseCoreDocument() {
        String coreDocument = "";
        String timestamp = GetSystemInfoUtil.getTimeStamp().toString();
        String searchStatus = "<csw:SearchStatus timestamp=\""+timestamp+"\"/>";
        int resultCount = QueryDataUtil.queryCountOfRecords();
        int nextCount = 0;

        String searchResults = "";
        if (resultCount>10) {
            nextCount = resultCount - 10;
        }
        if (nextCount>0) {
            searchResults = "<csw:SearchResults numberOfRecordsMatched=\""+resultCount+"\" numberOfRecordsReturned=\"10\" nextRecord=\""+nextCount+"\" recordSchema=\"http://www.opengis.net/cat/csw/2.0.2\" elementSet=\""+"full"+"\">";
        } else {
            searchResults = "<csw:SearchResults numberOfRecordsMatched=\""+resultCount+"\" numberOfRecordsReturned=\"10\" recordSchema=\"http://www.opengis.net/cat/csw/2.0.2\" elementSet=\""+"full"+"\">";
        }

        String core = "";
        List<RegistryPackage> registryPackages = QueryDataUtil.queryAllRegistryPackage();
        List<RegistryPackageType> rpts = new ArrayList<RegistryPackageType>();
        for (RegistryPackage registryPackage : registryPackages) {
            RegistryPackageType rpt = ParseToType.parseRegistryPackageToRegistryPackageType(registryPackage,"full");
            rpts.add(rpt);
        }
        for (RegistryPackageType rpt : rpts) {
            String record = ParseEbrimToDC.getRegistryPackageContent(rpt);
            core += record;
        }

        coreDocument = searchStatus + searchResults + core + "</csw:SearchResults>";
        return coreDocument;
    }

}
