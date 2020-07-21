package com.csw.dao;

import com.csw.model.ExternalIdentifier;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExternalIdentifierDAO {
    int insertData(@Param("externalIdentifier") ExternalIdentifier externalIdentifier,
                    @Param("externalIdentifierId") String externalIdentifierId);

    List<ExternalIdentifier> selectExternalIdentifiersBy_Id(@Param("externalIdentifierId") String externalIdentifierId);

    int deleteExternalIdentifierById(@Param("id") String id);
}
