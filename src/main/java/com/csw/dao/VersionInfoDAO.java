package com.csw.dao;

import com.csw.model.VersionInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VersionInfoDAO {
    int insertData(@Param("versionInfo") VersionInfo versionInfo);

    List<VersionInfo> selectVersionInfoById(@Param("id") String id);

    int deleteVersionInfoById(@Param("id") String id);
}
