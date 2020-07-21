package com.csw.dao;

import com.csw.privileage.util.Action;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface _ActionDAO {
    int insertData(@Param("action") Action action);

    List<Action> selectAllActions();

    Action selectActionById(@Param("id") int id);

    int deleteActionById(@Param("id") int id);
}
