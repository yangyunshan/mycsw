package com.csw.services.impl;

import com.csw.getRecordsExtend.Util.CreateGetRecordsExtendResponseDocument;
import com.csw.services.interfaces.IGetRecordsByTitleService;

public class GetRecordsByTitleService implements IGetRecordsByTitleService {

    public String getRecordsByTitleResponse(String title) {
        return CreateGetRecordsExtendResponseDocument.createGetRecordsByTitleResponseDocument(title);
    }
}
