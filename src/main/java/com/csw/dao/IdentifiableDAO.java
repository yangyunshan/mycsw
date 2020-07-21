package com.csw.dao;

import com.csw.model.Identifiable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IdentifiableDAO {
    int insertData(@Param("identifiable") Identifiable identifiable,
                    @Param("identifiableId") String identifiableId);

    List<Identifiable> selectIdentifiablesBy_Id(@Param("identifiableId") String identifiableId);

    List<Identifiable> selectIdentifiableById(@Param("id") String id);

    List<String> selectIdentifiable_IdById(@Param("id")String id);

    int deleteIdentifiableById(@Param("id") String id);
}
