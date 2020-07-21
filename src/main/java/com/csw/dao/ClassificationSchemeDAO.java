package com.csw.dao;

import com.csw.model.ClassificationScheme;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassificationSchemeDAO {
    int inserData(@Param("classificationScheme") ClassificationScheme classificationScheme);

    List<ClassificationScheme> selectClassificationSchemeById(@Param("id") String id);
}
