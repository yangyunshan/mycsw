package com.csw.services.impl;

import com.csw.getRecords.util.CreateGetRecordsResponseDocument;
import com.csw.services.interfaces.IGetRecordsService;
import org.junit.Test;

public class GetRecordsService implements IGetRecordsService {
    public String getRecordsResponse() {
        return CreateGetRecordsResponseDocument.createGetRecordsResponseDocument();
    }
}
