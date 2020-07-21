package com.csw.dao;

import com.csw.model.Person;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PersonDAO {
    int insertData(@Param("person") Person person);

    List<Person> selectPersonById(@Param("id") String id);

    List<Person> selectAllPersons();

    int deletePersonById(@Param("id") String id);
}
