package com.taoxi.mybatis.mysimple;


import com.taoxi.mybatis.mysimple.entity.Dept;
import com.taoxi.mybatis.mysimple.executor.DefaultExecutor;
import com.taoxi.mybatis.mysimple.mapper.DeptMapper;
import com.taoxi.mybatis.mysimple.session.SqlSession;
import com.taoxi.mybatis.mysimple.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MysimpleApplication {

    private static Logger logger = LoggerFactory.getLogger(MysimpleApplication.class);

    public static void main(String[] args) {

        //1.实例化SqlSessionFactory，加载数据库配置文件以及mapper.xml文件到configuration对象
        SqlSessionFactory factory = new SqlSessionFactory();

        //2.获取sqlsession对象
        SqlSession session = factory.openSession();

        //3.通过动态代理跨越面向接口编程和ibatis编程模型的鸿沟
        DeptMapper deptMapper = session.getMapper(DeptMapper.class);

        //4.遵循jdbc规范，通过底层的四大对象的合作完成数据查询和数据转化
        Dept dept = deptMapper.deptFindById(10);
        logger.info("dept info(deptno:{},dname:{},loc:{})", dept.getDeptNo(), dept.getDname(), dept.getLoc());

    }

}
