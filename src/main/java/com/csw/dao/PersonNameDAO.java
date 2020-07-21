package com.csw.dao;

import com.csw.model.PersonName;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PersonNameDAO {
    int insertData(@Param("personName") PersonName personName);

    List<PersonName> selectPersonNameById(@Param("id") String id);

    int deletePersonNameById(@Param("id") String id);
}
