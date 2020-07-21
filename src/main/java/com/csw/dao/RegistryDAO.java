package com.csw.dao;

import com.csw.model.Registry;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RegistryDAO {
    int insertData(@Param("registry") Registry registry);

    List<Registry> selectRegistryById(@Param("id") String id);

    int deleteRegistryById(@Param("id") String id);
}
