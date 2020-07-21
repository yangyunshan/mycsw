package com.csw.dao;

import com.csw.privileage.util.Master;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface _MasterDAO {
        int insertData(@Param("master") Master master);

        List<Master> selectAllMasters();

        Master selectMasterByName(@Param("masterName") String masterName);

        Master selectMasterById(@Param("id") int id);

        int deleteMasterById(@Param("id") int id);
}
