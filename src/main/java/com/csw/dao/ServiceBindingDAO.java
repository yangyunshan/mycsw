package com.csw.dao;

import com.csw.model.ServiceBinding;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ServiceBindingDAO {
    int insertData(@Param("serviceBinding") ServiceBinding serviceBinding,
                    @Param("serviceBindingId") String serviceBindingId);

    List<ServiceBinding> selectServiceBindingsBy_Id(@Param("serviceBindingId") String serviceBindingId);

    int deleteServiceBindingById(@Param("id") String id);
}
