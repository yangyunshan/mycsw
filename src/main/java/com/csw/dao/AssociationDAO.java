package com.csw.dao;

import com.csw.model.Association;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AssociationDAO {
    int insertData(@Param("association") Association association);

    List<Association> selectAssociationById(@Param("id") String id);

    int deleteAssociationById(@Param("id") String id);
}
