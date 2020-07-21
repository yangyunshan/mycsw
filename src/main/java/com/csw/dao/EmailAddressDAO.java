package com.csw.dao;

import com.csw.model.EmailAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmailAddressDAO {
    int insertData(@Param("emailAddress") EmailAddress emailAddress,
                    @Param("emailaddressId") String emailAddressId);

    List<EmailAddress> selectEmailAddressesBy_Id(@Param("emailAddressId") String emailAddressId);

    int deleteEmailAddressById(@Param("id") String id);
}
