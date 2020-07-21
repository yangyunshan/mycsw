package com.csw.dao;

import com.csw.model.Service;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ServiceDAO {
    int insertData(@Param("service") Service service);

    List<Service> selectServiceById(@Param("id") String id);

    int deleteServiceById(@Param("id") String id);
}
