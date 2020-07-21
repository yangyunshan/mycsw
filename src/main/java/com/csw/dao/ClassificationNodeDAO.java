package com.csw.dao;

import com.csw.model.ClassificationNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassificationNodeDAO {
    int insertData(@Param("classificationNode") ClassificationNode classificationNode);

    List<ClassificationNode> selectClassificationNodesById(@Param("id") String id);

    int deleteClassificationNodeById(@Param("id") String id);
}
