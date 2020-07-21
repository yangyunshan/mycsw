package com.csw.dao;

import com.csw.model.SpecificationLink;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SpecificationLinkDAO {
    int insertData(@Param("specificationLink") SpecificationLink specificationLink,
                    @Param("specificationLinkId") String specificationLinkId);

    List<SpecificationLink> selectSpecificationLinksBy_Id(@Param("specificationLinkId") String specificationLinkId);

    int deleteSpecificationLinkById(@Param("id") String id);
}
