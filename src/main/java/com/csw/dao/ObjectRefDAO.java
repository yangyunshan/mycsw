package com.csw.dao;

import com.csw.model.ObjectRef;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ObjectRefDAO {
    int insertData(@Param("objectRef") ObjectRef objectRef,
                    @Param("objectRefId") String objectRefId);

    List<ObjectRef> selectObjectRefById(@Param("id") String id);

    int deleteObjectRefById(@Param("id") String id);
}
