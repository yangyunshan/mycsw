package com.csw.dao;

import com.csw.model.ExtrinsicObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtrinsicObjectDAO {
    int insertData(@Param("extrinsicObject")ExtrinsicObject extrinsicObject);

    List<ExtrinsicObject> selectExtrinsicObjectById(@Param("id") String id);

    List<ExtrinsicObject> selectAllExtrinsicObjects();

    int deleteExtrinsicObjectById(@Param("id") String id);
}
