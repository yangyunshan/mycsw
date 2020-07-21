package com.csw.services.impl;

import com.csw.getRecordsExtend.Util.CreateGetRecordsExtendResponseDocument;
import com.csw.services.interfaces.IGetRecordsByOrganizationService;

public class GetRecordsByOrganizationService implements IGetRecordsByOrganizationService {
    public String getRecordsByOrganizationResponse(String organization) {
        return CreateGetRecordsExtendResponseDocument.createGetRecordsByOrganizationResponseDocument(organization);
    }
}
