package com.csw.servlet.util;

import com.csw.data.util.QueryDataUtil;
import com.csw.model.RegistryPackage;
import com.string.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetSpecificData {

    public static List<Map<String,String>> getData(String type, int pageCount, int pageNum) {
        List<Map<String,String>> results = new ArrayList<Map<String, String>>();
        List<RegistryPackage> registryPackages = QueryDataUtil.selectRegistryPackageByFileTypeLimited(type,pageCount,(pageNum-1)*pageCount);
        for (RegistryPackage registryPackage : registryPackages) {
            Map<String,String> result = new HashMap<String, String>();
            String id = registryPackage.getId();
            String name = QueryDataUtil.selectLocalizedStringsBy_Id(StringUtil.deletePackage(id)+":Name").get(0).getValue();
            result.put("id",id);
            result.put("name",name);
            result.put("time",QueryDataUtil.selectTimeByRegistryPackageId(id));
            result.put("type",registryPackage.getType());
            results.add(result);
        }
        return results;
    }
}
