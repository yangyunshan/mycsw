package com.csw.dao;

import com.csw.model.Classification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassificationDAO {
    int insertData(@Param("classification") Classification classification,
                    @Param("classificationId") String classificationId);

    List<Classification> selectClassificationsBy_Id(@Param("classificationId") String classificationId);

    int deleteClassificationById(@Param("id") String id);
}
