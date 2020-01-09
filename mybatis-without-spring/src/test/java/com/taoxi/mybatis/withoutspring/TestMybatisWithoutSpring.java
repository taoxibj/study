package com.taoxi.mybatis.withoutspring;

import com.taoxi.mybatis.withoutspring.beans.Dept;
import com.taoxi.mybatis.withoutspring.dao.DeptMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public class TestMybatisWithoutSpring {

    private static Logger logger = LoggerFactory.getLogger(TestMybatisWithoutSpring.class);

    private SqlSession session;

    @Before
    public void start() throws Exception {
        try {
            InputStream inputStream = Resources.getResourceAsStream("myBatis-config.xml");
            InputStream inputStream2 = Resources.getResourceAsStream("config2.properties");
            Properties properties = new Properties();
            properties.load(inputStream2);
            //SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(inputStream, null, properties);
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream, "development2");
            session = factory.openSession();
        } catch (Exception e) {
            logger.error("start function error:", e);
            throw e;
        }
    }

    @Test
    public void test01(){
        Dept dept = new Dept();
        dept.setDname("风控部2");
        dept.setLoc("北京");

        try{
//        	  session.insert("Dept.saveDept", dept);
            session.insert("com.taoxi.mybatis.withoutspring.dao.DeptMapper.saveDept", dept);
            session.commit();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testDeptFindById() throws Exception{
        try {
            DeptMapper dao = session.getMapper(DeptMapper.class);
            Dept dept = dao.deptFindById(10);
            System.out.println(dept.getDname());
        } catch (Exception e) {
            logger.error("testDeptFindById function error:", e);
            throw e;
        }
    }

    @After
    public void end() {
        if (session != null) {
            session.close();
        }
    }

}
