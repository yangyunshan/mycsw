package com.csw.services.impl;

import com.csw.getcapabilities.util.CreateGetCapabilitiesDocumentUtil;
import com.csw.services.interfaces.IGetCapabilitiesService;

public class GetCapabilitiesService implements IGetCapabilitiesService {
    public String getCapabilitiesResponse() {
        CreateGetCapabilitiesDocumentUtil getCapabilitiesUtil = new CreateGetCapabilitiesDocumentUtil();
        String result = getCapabilitiesUtil.createGetCapabilitiesResponseDocument();
        return result;
    }
}
