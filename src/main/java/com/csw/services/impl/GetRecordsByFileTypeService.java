package com.csw.services.impl;

import com.csw.getRecordsExtend.Util.CreateGetRecordsExtendResponseDocument;
import com.csw.services.interfaces.IGetRecordsByFileTypeService;

public class GetRecordsByFileTypeService implements IGetRecordsByFileTypeService {
    public String getRecordsByFileTypeResponse(String fileType) {
        return CreateGetRecordsExtendResponseDocument.createGetRecordsByFileTypeResponseDocument(fileType);
    }
}
