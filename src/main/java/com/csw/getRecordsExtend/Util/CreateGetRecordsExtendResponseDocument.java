package com.csw.getRecordsExtend.Util;

import com.csw.file.util.FileOperationUtil;
import com.csw.getRecordById.util.CreateGetRecordByIdResponseDocument;
import com.csw.util.GetSystemInfoUtil;
import java.util.List;
import java.util.Set;

public class CreateGetRecordsExtendResponseDocument {

    /**
     * 创建GetRecordsByKeywords响应文档
     *
     * @return 返回GetRecordsByKeywords响应文档
     * */
    public static String createGetRecordsByKeywordsResponseDocument(List<String> keywords) {
        String[] beginAndEnd = createGetRecordsExtendBeginAndEndDocument();
        String core = createGetRecordsByKeywordsResponseCoreDocument(keywords);
        String result = beginAndEnd[0] + core + beginAndEnd[1];
        return result;
    }

    /**
     * 创建GetRecordsByTitle响应文档
     *
     * @return 返回GetRecordsByTitle响应文档
     * */
    public static String createGetRecordsByTitleResponseDocument(String title) {
        String[] beginAndEnd = createGetRecordsExtendBeginAndEndDocument();
        String core = createGetRecordsByTitleResponseCoreDocument(title);
        String result = beginAndEnd[0] + core + beginAndEnd[1];
        return result;
    }

    /**
     * 创建GetRecordsByBounding响应文档
     *
     * @param left :
     *             左下角x值
     * @param down :
     *             左下角y值
     * @param right :
     *              右上角x值
     * @param up :
     *           右上角y值
     *
     * @return 返回GetRecordsByBounding响应文档
     * */
    public static String createGetRecordsByBoundingResponseDocument(String left, String down,
                                                                    String right, String up) {
        String[] beginAndEnd = createGetRecordsExtendBeginAndEndDocument();
        String core = createGetRecordsByBoundingResponseCoreDocument(left,down,right,up);
        String result = beginAndEnd[0] + core + beginAndEnd[1];
        return result;
    }

    /**
     * 创建GetRecordsByFileType响应文档
     *
     * @param type :
     *             文件类型名称
     * */
    public static String createGetRecordsByFileTypeResponseDocument(String type) {
        String[] beginAndEnd = createGetRecordsExtendBeginAndEndDocument();
        String core = createGetRecordsByFileTypeResponseCoreDocument(type);
        String result = beginAndEnd[0] + core + beginAndEnd[1];
        return result;
    }

    /**
     * 创建GetRecordsByOwner响应文档
     *
     * @param owner :
     *              注册者名称
     *
     * */
    public static String createGetRecordsByOwnerResponseDocument(String owner) {
        String[] beginAndEnd = createGetRecordsExtendBeginAndEndDocument();
        String core = createGetRecordsByOwnerResponseCoreDocument(owner);
        String result = beginAndEnd[0] + core + beginAndEnd[1];
        return result;
    }

    /**
     * 创建GetRecordsByPersonName响应文档
     *
     * @param personName :
     *                   联系人名字
     *
     * */
    public static String createGetRecordsByPersonNameResponseDocument(String personName) {
        String[] beginAndEnd = createGetRecordsExtendBeginAndEndDocument();
        String core = createGetRecordsByPersonNameResponseCoreDocument(personName);
        String result = beginAndEnd[0] + core + beginAndEnd[1];
        return result;
    }

    /**
     * 创建GetRecordsByOrganization响应文档
     *
     * @param organization :
     *                     组织名称
     *
     * */
    public static String createGetRecordsByOrganizationResponseDocument(String organization) {
        String[] beginAndEnd = createGetRecordsExtendBeginAndEndDocument();
        String core = createGetRecordsByOrganizationResponseCoreDocument(organization);
        String result = beginAndEnd[0] + core + beginAndEnd[1];
        return result;
    }

    /**
     * 生成GetRecordsByPersonName响应文档的核心内容
     * */
    public static String createGetRecordsByPersonNameResponseCoreDocument(String personName) {
        String coreDocument = "";
        String timestamp = GetSystemInfoUtil.getTimeStamp().toString();
        String searchStatus = "<csw:SearchStatus timestamp=\""+timestamp+"\"/>";
        Set<String> registryPackageIds = GetRecordsExtendUtil.queryRegistryPackageByPerson(personName);
        int resultCount = registryPackageIds.size();
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
        for (String id : registryPackageIds) {
            core += CreateGetRecordByIdResponseDocument.getRegistryPackageContent(id);
        }
        coreDocument = searchStatus + searchResults + core + "</csw:SearchResults>";
        return coreDocument;
    }

    /**
     * 生成GetRecordsByOrganization响应文档的核心内容
     * */
    public static String createGetRecordsByOrganizationResponseCoreDocument(String organization) {
        String coreDocument = "";
        String timestamp = GetSystemInfoUtil.getTimeStamp().toString();
        String searchStatus = "<csw:SearchStatus timestamp=\""+timestamp+"\"/>";
        Set<String> registryPackageIds = GetRecordsExtendUtil.queryRegistryPackageByOrganization(organization);
        int resultCount = registryPackageIds.size();
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
        for (String id : registryPackageIds) {
            core += CreateGetRecordByIdResponseDocument.getRegistryPackageContent(id);
        }
        coreDocument = searchStatus + searchResults + core + "</csw:SearchResults>";
        return coreDocument;
    }

    /**
     * 生成GetRecordsByOwner响应文档中的核心内容
     * */
    public static String createGetRecordsByOwnerResponseCoreDocument(String owner) {
        String coreDocument = "";
        String timestamp = GetSystemInfoUtil.getTimeStamp().toString();
        String searchStatus = "<csw:SearchStatus timestamp=\""+timestamp+"\"/>";
        Set<String> registryPackageIds = GetRecordsExtendUtil.queryRegistryPackageByOwner(owner);
        int resultCount = registryPackageIds.size();
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
        for (String id : registryPackageIds) {
            core += CreateGetRecordByIdResponseDocument.getRegistryPackageContent(id);
        }
        coreDocument = searchStatus + searchResults + core + "</csw:SearchResults>";
        return coreDocument;
    }

    /**
     * 生成GetRecordsByFileType响应文档中的核心内容
     * */
    public static String createGetRecordsByFileTypeResponseCoreDocument(String type) {
        String coreDocument = "";
        String timestamp = GetSystemInfoUtil.getTimeStamp().toString();
        String searchStatus = "<csw:SearchStatus timestamp=\""+timestamp+"\"/>";
        Set<String> registryPackageIds = GetRecordsExtendUtil.queryRegistryPackageByFileType(type);
        int resultCount = registryPackageIds.size();
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
        for (String id : registryPackageIds) {
            core += CreateGetRecordByIdResponseDocument.getRegistryPackageContent(id);
        }
        coreDocument = searchStatus + searchResults + core + "</csw:SearchResults>";
        return coreDocument;
    }

    /**
     * 生成GetRecordsByBounding响应文档的核心内容
     *
     * @return String
     *
     */
    public static String createGetRecordsByBoundingResponseCoreDocument(String left, String down,
                                                                        String right, String up) {
        String coreDocument = "";
        String timestamp = GetSystemInfoUtil.getTimeStamp().toString();
        String searchStatus = "<csw:SearchStatus timestamp=\""+timestamp+"\"/>";
        Set<String> registryPackageIds = GetRecordsExtendUtil.queryRegistryPackageByBounding(left,down,right,up);
        int resultCount = registryPackageIds.size();
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
        for (String id : registryPackageIds) {
            core += CreateGetRecordByIdResponseDocument.getRegistryPackageContent(id);
        }
        coreDocument = searchStatus + searchResults + core + "</csw:SearchResults>";
        return coreDocument;
    }

    /**
     * 生成GetRecordsByTitle响应文档的核心内容
     *
     * @return String
     *
     */
    public static String createGetRecordsByTitleResponseCoreDocument(String title) {
        String coreDocument = "";
        String timestamp = GetSystemInfoUtil.getTimeStamp().toString();
        String searchStatus = "<csw:SearchStatus timestamp=\""+timestamp+"\"/>";
        Set<String> registryPackageIds = GetRecordsExtendUtil.queryRegistryPackageByTitle(title);
        int resultCount = registryPackageIds.size();
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
        for (String id : registryPackageIds) {
            core += CreateGetRecordByIdResponseDocument.getRegistryPackageContent(id);
        }
        coreDocument = searchStatus + searchResults + core + "</csw:SearchResults>";
        return coreDocument;
    }

    /**
     * 生成GetRecordsByKeywords响应文档的核心内容
     *
     * @return String
     *
     */
    public static String createGetRecordsByKeywordsResponseCoreDocument(List<String> keywords) {
        String coreDocument = "";
        String timestamp = GetSystemInfoUtil.getTimeStamp().toString();
        String searchStatus = "<csw:SearchStatus timestamp=\""+timestamp+"\"/>";
        Set<String> registryPackageIds = GetRecordsExtendUtil.queryRegistryPackageByKeywords(keywords);
        int resultCount = registryPackageIds.size();
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
        for (String id : registryPackageIds) {
            core += CreateGetRecordByIdResponseDocument.getRegistryPackageContent(id);
        }
        coreDocument = searchStatus + searchResults + core + "</csw:SearchResults>";
        return coreDocument;
    }

    /**
     * 创建GetRecords响应文档的开头和结尾部分
     * */
    public static String[] createGetRecordsExtendBeginAndEndDocument() {
        String baseWebPath = FileOperationUtil.getWebPath();
        String templateFileContent = FileOperationUtil.readFileContent(baseWebPath+"templateFiles/getRecordsResponse.xml","UTF-8");
        int num1 = templateFileContent.lastIndexOf("<csw:SearchStatus");
        String begin = templateFileContent.substring(0,num1);
        int num2 = templateFileContent.lastIndexOf("</csw:SearchResults>");
        String end = templateFileContent.substring(num2+"</csw:SearchResults>".length());

        String[] beginAndEnd = new String[2];
        beginAndEnd[0] = begin;
        beginAndEnd[1] = end;
        return beginAndEnd;
    }

}
