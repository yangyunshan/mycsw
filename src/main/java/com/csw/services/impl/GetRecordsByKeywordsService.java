package com.csw.services.impl;

import com.csw.getRecordsExtend.Util.CreateGetRecordsExtendResponseDocument;
import com.csw.services.interfaces.IGetRecordsByKeywordsService;

import java.util.List;

public class GetRecordsByKeywordsService implements IGetRecordsByKeywordsService {
    public String getRecordsByKeywordsResponse(List<String> keywords) {
        return CreateGetRecordsExtendResponseDocument.createGetRecordsByKeywordsResponseDocument(keywords);
    }
}
