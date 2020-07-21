package com.csw.dao;

import com.csw.model.TelephoneNumber;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TelephoneNumberDAO {
    int insertData(@Param("telephoneNumber") TelephoneNumber telephoneNumber,
                    @Param("telephoneNumberId") String telephoneNumberId);

    List<TelephoneNumber> selectTelephoneNumbersBy_Id(@Param("telephoneNumberId") String telephoneNumberId);

    int deleteTelephoneNumberById(@Param("id") String id);
}
