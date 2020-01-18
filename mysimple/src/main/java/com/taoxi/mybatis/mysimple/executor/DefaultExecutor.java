package com.taoxi.mybatis.mysimple.executor;

import com.sun.deploy.util.ReflectionUtil;
import com.taoxi.mybatis.mysimple.config.Configuration;
import com.taoxi.mybatis.mysimple.config.MappedStatement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultExecutor implements Executor{

    private final Configuration configuration;

    public DefaultExecutor(Configuration configuration) {
        super();
        this.configuration = configuration;
    }

    public <E> List<E> query(MappedStatement ms, Object parameter) {
        ArrayList<E> ret = new ArrayList<E>();//定义返回结果集
        try {
            Class.forName(configuration.getJdbcDriver());//加载驱动程序
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            //获取连接，从Configuration中获取数据库信息
            connection = DriverManager.getConnection(configuration.getJdbcUrl(), configuration.getJdbcUsername(),
                    configuration.getJdbcPassword());
            //创建prepareStatement，从MappedStatement中获取sql语句
            preparedStatement = connection.prepareStatement(ms.getSql());
            //处理sql语句中的占位符
            parameterize(preparedStatement, parameter);
            //执行查询操作获取resultSet
            resultSet = preparedStatement.executeQuery();
            //将结果集通过反射技术，填充到list中
            handlerResultSet(resultSet, ret, ms.getResultType());

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        System.out.println(ms.getSql());
        return null;
    }


    //对prepareStatement中的占位符进行处理
    private void parameterize(PreparedStatement preparedStatement, Object parameter) throws SQLException {
        if (parameter instanceof Integer) {
            preparedStatement.setInt(1,(int)parameter);
        } else if (parameter instanceof Long) {
            preparedStatement.setLong(1, (long)parameter);
        } else if (parameter instanceof String) {
            preparedStatement.setString(1, (String)parameter);
        }
    }

    //读取resultset中的数据，并转换成目标对象
    private <E> void handlerResultSet(ResultSet resultSet, List<E> ret, String className) {
        Class<E> clazz = null;
        try {
            //通过反射获取类对象
            clazz = (Class<E>)Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            while (resultSet.next()) {
                Object entity = clazz.newInstance();
                //使用反射工具将resultSet中的数据填充到entity中
                ReflectionUtil.setPropToBeanFromResultSet(entity, resultSet);
                //对象加入返回集合中
                ret.add((E)entity);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
