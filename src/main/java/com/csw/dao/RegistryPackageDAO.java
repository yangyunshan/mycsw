package com.csw.dao;

import com.csw.model.RegistryPackage;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface RegistryPackageDAO {
    int insertData(@Param("registryPackage")RegistryPackage registryPackage,
                   @Param("createTime")Timestamp createTime);

    boolean checkIdIsExist(@Param("id") String id);

    int getCountOfRecords();

    int getCountOfRecordsByFileType(@Param("fileType")String fileType);

    List<RegistryPackage> selectRegistryPackageById(@Param("id") String id);

    //limit查询，设置每次从数据库中查询的数据条数
    List<RegistryPackage> selectRegistryPackageLimited(@Param("limitCount")int limitCount,@Param("offsetIndex")int offsetIndex);

    List<RegistryPackage> selectRegistryPackageByFileTypeLimited(@Param("fileType")String fileType,
                                                                 @Param("limitCount")int limitCount,
                                                                 @Param("offsetIndex")int offsetIndex);

    List<RegistryPackage> selectRegistryPackageByOwnerLimited(@Param("owner")String owner,
                                                                 @Param("limitCount")int limitCount,
                                                                 @Param("offsetIndex")int offsetIndex);

    List<RegistryPackage> selectAllRegistryPackage();

    List<String> selectRegistryPackageByFuzzId(@Param("fuzzId")String fuzzId);

    List<RegistryPackage> selectRegistryPackageByType(@Param("type") String type);

    List<RegistryPackage> selectRegistryPackageByOwner(@Param("owner") String owner);

    String selectTimeById(@Param("id")String id);

    //按照时间降序查询数据
    List<RegistryPackage> selectNewestRegistryPackageLimited(@Param("limitCount")int limitCount,
                                                             @Param("offsetIndex")int offsetIndex);

    int deleteRegistryPackageById(@Param("id") String id);
}
