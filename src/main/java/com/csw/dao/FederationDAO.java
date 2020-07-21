package com.csw.dao;

import com.csw.model.Federation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FederationDAO {
    int insertData(@Param("federation") Federation federation);

    List<Federation> selectFederationById(@Param("id") String id);

    int deleteFederationById(@Param("id") String id);
}
