package com.csw.services.impl;

import com.csw.getRecordsExtend.Util.CreateGetRecordsExtendResponseDocument;
import com.csw.services.interfaces.IGetRecordsByPersonNameService;

public class GetRecordsByPersonNameService implements IGetRecordsByPersonNameService {
    public String getRecordsByPersonNameResponse(String personName) {
        return CreateGetRecordsExtendResponseDocument.createGetRecordsByPersonNameResponseDocument(personName);
    }
}
