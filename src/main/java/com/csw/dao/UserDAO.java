package com.csw.dao;

import com.csw.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDAO {
    int insertData(@Param("user") User user);

    List<User> selectUserById(@Param("id") String id);

    int deleteUserById(@Param("id") String id);
}
