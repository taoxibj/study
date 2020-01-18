package com.taoxi.mybatis.mysimple.session;

import com.taoxi.mybatis.mysimple.binding.MapperProxy;
import com.taoxi.mybatis.mysimple.config.Configuration;
import com.taoxi.mybatis.mysimple.config.MappedStatement;
import com.taoxi.mybatis.mysimple.executor.DefaultExecutor;
import com.taoxi.mybatis.mysimple.executor.Executor;

import java.lang.reflect.Proxy;
import java.util.List;

public class DefaultSqlSession implements SqlSession{

    private final Configuration configuration;

    private Executor executor;

    public DefaultSqlSession(Configuration configuration) {
        super();
        this.configuration = configuration;
        executor = new DefaultExecutor(configuration);
    }

    public <T> T selectOne(String statement, Object parameter) {
        //执行Sql语句获取查询结果
        List<Object> selectList = this.selectList(statement, parameter);
        //查询结果为空返回null
        if (selectList == null || selectList.size()==0) {
            return null;
        }
        if (selectList.size()==1) {
            return (T)selectList.get(0);
        }else{
            throw new RuntimeException("Too Many Results!");
        }

    }

    public <E> List<E> selectList(String statement, Object parameter) {

        MappedStatement ms = configuration.getMappedStatments().get(statement);

        return executor.query(ms, parameter);
    }

    public <T> T getMapper(Class<T> type) {
        MapperProxy mapperProxy = new MapperProxy(this);

        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, mapperProxy);
    }
}
