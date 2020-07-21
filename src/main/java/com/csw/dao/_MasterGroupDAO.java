package com.csw.dao;

import com.csw.privileage.util.MasterGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface _MasterGroupDAO {
    int insertData(@Param("masterGroup") MasterGroup masterGroup);

    List<MasterGroup> selectAllMasterGroups();

    MasterGroup selectMasterGroupByMasterId(@Param("masterId") int masterId);

    MasterGroup selectMasterGroupById(@Param("id") int id);

    int deleteMasterGroupById(@Param("id") int id);
}
