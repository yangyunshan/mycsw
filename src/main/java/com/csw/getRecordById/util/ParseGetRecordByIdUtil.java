package com.csw.getRecordById.util;

import com.ebrim.model.csw.GetRecordByIdDocument;
import org.junit.Test;

public class ParseGetRecordByIdUtil {

    /**
     * 解析getRecordByID请求文档，获取需要查询的Record的id值
     *
     * @param getRecordByIdDocument
     *            需要解析的getRecordById的文档内容
     * @return 返回解析出的Record的id值，空则返回<b>null</b>
     */
    public static String parseGetRecordByIdDocument(GetRecordByIdDocument getRecordByIdDocument) {
        String registryPackageId = "";
        if (getRecordByIdDocument.getGetRecordById().getIdArray()!=null) {
            registryPackageId = getRecordByIdDocument.getGetRecordById().getIdArray(0);
        }
        return registryPackageId;
    }
}
