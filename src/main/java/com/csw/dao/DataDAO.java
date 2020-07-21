package com.csw.dao;

import com.csw.model.Data;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataDAO {
    int insertData(@Param("data")Data data);

    List<Data> selectDataById(@Param("id")String id);

    int deleteDataById(@Param("id") String id);
}
