package com.test;

import com.csw.data.util.InsertDataUtil;
import com.csw.data.util.QueryDataUtil;
import com.csw.ebrim.util.Info2Ebrim;
import com.csw.model.Data;
import com.csw.dao.DataDAO;
import com.csw.model.ExtrinsicObject;
import com.csw.model.InternationalString;
import com.csw.spring.util.SpringContextUtil;
import com.csw.transaction.util.CreateTransactionResponseDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class JUnit {
//    @Autowired
//    private DataDAO datadao;

    @Test
    public void test() {
        String owner = "yang";

        String dataType = "1";
        String dataId = "geo_42";
        String keywords = "地震";
        String size = "";
        String resolution = "";
        String format = "";
        String description = "";
        String north = "35";
        String west = "99";
        String south = "32";
        String east = "99";
        String name = "四川省九寨沟县地震区域1：25万县界数据（2012年）";
        String startTime = "";
        String endTime = "";

        String result = "";
        if (QueryDataUtil.checkRegistryPackageIsExist(dataId)) {
            System.out.println("数据已存在");
            return;
        }
        String ebrimContent = Info2Ebrim.parseInfo2Ebrim(dataType,dataId,description,keywords,name,format,size,resolution,north,west,south,east,startTime,endTime);
        switch (Integer.valueOf(dataType)) {
            case 1:{
                result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(ebrimContent,"geologicalHazardData",owner);
                break;
            }
            case 2:{
                result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(ebrimContent,"waterResourcesData",owner);
                break;
            }
            case 3:{
                result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(ebrimContent,"ecologicalData",owner);
                break;
            }
            case 4:{
                result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(ebrimContent,"climateData",owner);
                break;
            }
            case 5:{
                result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(ebrimContent,"environmentData",owner);
                break;
            }
            case 6:{
                result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(ebrimContent,"keyBandData",owner);
                break;
            }
            case 7:{
                result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(ebrimContent,"stratigraphicPaleontologyData",owner);
                break;
            }
            case 8:{
                result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(ebrimContent,"mineralResourceData",owner);
                break;
            }
            case 9:{
                result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(ebrimContent,"energyData",owner);
                break;
            }
            case 10:{
                result = CreateTransactionResponseDocument.createTransactionInsertResponseDocument(ebrimContent,"literaturePictureData",owner);
                break;
            }
        }
        System.out.println("Done!");
    }

}
