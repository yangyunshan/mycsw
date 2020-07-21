package com.csw.dao;

import com.csw.privileage.util.Group;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface _GroupDAO {
    int insertData(@Param("group") Group group);

    List<Group> selectAllGroups();

    Group selectGroupById(@Param("id") int id);

    int deleteGroupById(@Param("id") int id);
}
