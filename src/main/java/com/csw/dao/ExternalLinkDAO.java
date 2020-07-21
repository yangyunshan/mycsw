package com.csw.dao;

import com.csw.model.ExternalLink;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExternalLinkDAO {
    int insertData(@Param("externalLink") ExternalLink externalLink);

    List<ExternalLink> selectExternalLinkById(@Param("id") String id);
}
