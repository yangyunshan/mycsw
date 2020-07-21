package com.csw.transaction.util;

import com.csw.file.util.FileOperationUtil;
import org.junit.Test;

public class CreateTransactionResponseDocument {
    /**
     * 生成TransactionInsert响应文档
     *
     * @param data:
     *            需注册的元数据内容
     * @param type:
     *            元数据类型
     * @param owner:
     *             所有者
     *
     * @return TransactionInsert响应文档
     * */
    public static String createTransactionInsertResponseDocument(String data, String type, String owner) {
        String[] beginAndEnd = createTransactionBeginAndEndResponseDocument();
        String core = createTransactionInsertCoreDocument(data,type,owner);
        return beginAndEnd[0] + core + beginAndEnd[1];
    }

    /**
     * 生成TransactionUpdate响应文档
     *
     * @param id:
     *          需删除目标的id标识
     * @param information:
     *                   新的元数据内容（ebrim形式）
     * @param type:
     *            元数据类型
     * @param owner:
     *             所有者
     *
     * @return TransactionUpdate响应文档
     *
     * */
    public static String createTransactionUpdateResponseDocument(String id, String information, String type, String owner) {
        String[] beginAndEnd = createTransactionBeginAndEndResponseDocument();
        String core = createTransactionUpdateCoreDocument(id,information,type,owner);
        return beginAndEnd[0] + core + beginAndEnd[1];
    }

    /**
     * 生成TransactionDelete响应文档
     *
     * @param id:
     *          需删除目标的id标识
     *
     * @return TransactionDelete响应文档
     *
     * */
    public static String createTransactionDeleteResponseDocument(String id) {
        String[] beginAndEnd = createTransactionBeginAndEndResponseDocument();
        String core = createTransactionDeleteCoreDocument(id);
        return beginAndEnd[0] + core + beginAndEnd[1];
    }

    /**
     * 生成Transaction响应文档的头部和尾部部分
     * */
    public static String[] createTransactionBeginAndEndResponseDocument() {
        String[] content = new String[2];
        String baseWebPath = FileOperationUtil.getWebPath();
        String templateFileContent = FileOperationUtil.readFileContent(baseWebPath+"templateFiles/transactionResponse.xml","UTF-8");
        int num1 = templateFileContent.lastIndexOf("<csw:TransactionSummary>");
        String begin = templateFileContent.substring(0,num1);
        int num2 = templateFileContent.lastIndexOf("</csw:TransactionSummary>");
        String end = templateFileContent.substring(num2+"</csw:TransactionSummary>".length());
        content[0] = begin;
        content[1] = end;
        return content;
    }

    /**
     * 生成TransactionInsert响应文档的核心内容
     *
     * @param data:
     *            需注册的元数据内容
     * @param type:
     *            元数据类型
     * @param owner:
     *             所有者
     *
     * @return 响应文档的核心内容
     *
     * */
    public static String createTransactionInsertCoreDocument(String data, String type, String owner) {
        String update = "<csw:totalUpdated>0</csw:totalUpdated>";
        String delete = "<csw:totalDeleted>0</csw:totalDeleted>";
        TransactionUtil transaction = new TransactionUtil();
        int count = 0;//记录注册成功的数目
        if (transaction.insertData(data,type,owner)) {
            count++;
        }
        String insert = "<csw:totalInserted>"+count+"</csw:totalInserted>";
        return insert + update + delete;
    }

    /**
     * 生成TransactionUpdate响应文档的核心内容
     *
     * @param id:
     *          需删除目标的id标识
     * @param information:
     *                   新的元数据内容（ebrim形式）
     * @param type:
     *            元数据类型
     * @param owner:
     *             所有者
     *
     * @return TransactionUpdate响应文档的核心内容
     *
     * */
    public static String createTransactionUpdateCoreDocument(String id, String information, String type, String owner) {
        String delete = "<csw:totalDelete>0</csw:totalDelete>";
        String insert = "<csw:totalInserted>0</csw:totalInserted>";
        int count = 0;
        TransactionUtil transactionUtil = new TransactionUtil();
        if (transactionUtil.updateData(id,information,type,owner)) {
            count++;
        }
        String update = "<csw:totalUpdated>"+count+"</csw:totalUpdated>";
        return insert + update + delete;
    }

    /**
     * 生成TransactionDelete响应文档的核心内容
     *
     * @param id:
     *          需删除目标的id标识
     *
     * @return 响应文档的核心内容
     *
     * */
    public static String createTransactionDeleteCoreDocument(String id) {
        String update = "<csw:totalUpdated>0</csw:totalUpdated>";
        String insert = "<csw:totalInserted>0</csw:totalInserted>";
        TransactionUtil transaction = new TransactionUtil();
        int count = 0;//记录注册成功的数目
        if (transaction.deleteData(id)) {
            count++;
        }
        String delete = "<csw:totalDeleted>"+count+"</csw:totalDeleted>";
        return insert + update + delete;
    }
}
