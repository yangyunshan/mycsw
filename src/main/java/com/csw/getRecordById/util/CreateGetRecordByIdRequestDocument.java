package com.csw.getRecordById.util;

import com.ebrim.model.csw.ElementSetNameType;
import com.ebrim.model.csw.GetRecordByIdDocument;
import com.ebrim.model.csw.GetRecordByIdType;
import org.junit.Test;

public class CreateGetRecordByIdRequestDocument {
    /**
     * 根据id来生成GetRecordByIdDocument内容
     *
     * @param id
     *            需要生成GetRecordByIdDocument的id
     * @param elementSet
     *            full,summary,brief
     * @return
     */
    //生成POST request请求(xml格式)
    public static GetRecordByIdDocument createGetRecordByIdRequestDocument(String id, String elementSet) {
        GetRecordByIdDocument getRecordByIdDocument = GetRecordByIdDocument.Factory.newInstance();
        GetRecordByIdType getRecordByIdType = getRecordByIdDocument.addNewGetRecordById();
        getRecordByIdType.addId(id);
        getRecordByIdType.setVersion("2.0.2");
        getRecordByIdType.setService("CSW");
        ElementSetNameType elementSetNameType = getRecordByIdType.addNewElementSetName();
        elementSetNameType.setStringValue(elementSet);
        return getRecordByIdDocument;
    }
}
