    package com.csw.dao;

    import com.csw.model.Organization;
    import org.apache.ibatis.annotations.Param;

    import java.util.List;

    public interface OrganizationDAO {
        int insertData(@Param("organization") Organization organization);

        List<Organization> selectOrganizationById(@Param("id") String id);

        List<Organization> selectAllOrganizations();

        List<Organization> selectOrganizationByName(@Param("nameId")String nameId);

        int deleteOrganizationById(@Param("id") String id);
    }
