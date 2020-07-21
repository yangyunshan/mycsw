package com.csw.ebrim.util;

import com.csw.data.util.QueryDataUtil;
import com.csw.model.RegistryPackage;

import java.util.HashMap;
import java.util.Map;

public class Ebrim2Info {
    public static Map<String,String> getAllInfoById(String id) {
        Map<String,String> result = new HashMap<String, String>();

        RegistryPackage registryPackage = QueryDataUtil.queryRegistryPackageById(id);

        String dataType = registryPackage.getType();

        return result;
    }
}
