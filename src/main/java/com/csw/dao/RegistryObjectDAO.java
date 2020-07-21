package com.csw.dao;

import com.csw.model.RegistryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RegistryObjectDAO {
    int insertData(@Param("registryObject") RegistryObject registryObject);

    List<RegistryObject> selectRegistryObjectById(@Param("id") String id);
}
