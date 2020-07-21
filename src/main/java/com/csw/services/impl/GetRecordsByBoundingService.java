package com.csw.services.impl;

import com.csw.getRecordsExtend.Util.CreateGetRecordsExtendResponseDocument;
import com.csw.services.interfaces.IGetRecordsByBoundingService;

public class GetRecordsByBoundingService implements IGetRecordsByBoundingService {
    public String getRecordsByBoundingResponse(String left, String down, String right, String up) {
        return CreateGetRecordsExtendResponseDocument.createGetRecordsByBoundingResponseDocument(left,down,right,up);
    }
}
