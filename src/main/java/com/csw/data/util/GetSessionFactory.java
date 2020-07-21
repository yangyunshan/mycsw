package com.csw.data.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class GetSessionFactory {
    private static final String RESOURCE = "mybatis-config.xml";
    /**
     * 获取SqlSesssion
     * @return sqlSession
     * */
    public static SqlSessionFactory sqlSessionFactory;
    private static InputStream inputStream;
    static {
        try {
            inputStream = Resources.getResourceAsStream(RESOURCE);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can not get SqlSessionFactory");
        }
    }
}
