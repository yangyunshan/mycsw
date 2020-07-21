package com.csw.dao;

import com.csw.model.PostalAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PostalAddressDAO {
    int insertData(@Param("postalAddress") PostalAddress postalAddress,
                    @Param("postalAddressId") String postalAddressId);

    List<PostalAddress> selectPostalAddressesBy_Id(@Param("postalAddressId") String postalAddressId);

    int deletePostalAddressById(@Param("id") String id);
}
