package com.csw.services.impl;

import com.csw.getRecordById.util.CreateGetRecordByIdResponseDocument;
import com.csw.services.interfaces.IGetRecordByIdService;
import org.junit.Test;

public class GetRecordByIdService implements IGetRecordByIdService {
    public String getRecordByIdResponse(String id) {
        String result = CreateGetRecordByIdResponseDocument.createGetRecordByIdResponseDocument(id);
        return result;
    }
}
