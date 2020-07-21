package com.csw.services.impl;

import com.csw.getRecordsExtend.Util.CreateGetRecordsExtendResponseDocument;
import com.csw.services.interfaces.IGetRecordsByOwnerService;

public class GetRecordsByOwnerService implements IGetRecordsByOwnerService {
    public String getRecordsByOwnerResponse(String owner) {
        return CreateGetRecordsExtendResponseDocument.createGetRecordsByOwnerResponseDocument(owner);
    }
}
