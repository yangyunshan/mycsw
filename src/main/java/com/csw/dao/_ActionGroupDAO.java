package com.csw.dao;

import com.csw.privileage.util.ActionGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface _ActionGroupDAO {
    int insertData(@Param("actionGroup") ActionGroup actionGroup);

    List<ActionGroup> selectAllActionGroups();

    List<ActionGroup> selectActionGroupByGroupId(@Param("groupId") int groupId);

    ActionGroup selectActionGroupById(@Param("id") int id);

    int deleteActionGroupById(@Param("id") int id);
}
