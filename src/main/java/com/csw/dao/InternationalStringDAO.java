package com.csw.dao;

import com.csw.model.InternationalString;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface InternationalStringDAO {
    int insertData(@Param("internationalString") InternationalString internationalString);

    List<InternationalString> selectInternationalStringsById(@Param("id") String id);

    int deleteInternationalStringById(@Param("id") String id);
}
