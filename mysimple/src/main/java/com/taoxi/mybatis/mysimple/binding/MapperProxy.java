package com.taoxi.mybatis.mysimple.binding;

import com.taoxi.mybatis.mysimple.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;

public class MapperProxy implements InvocationHandler {

    private SqlSession session;

    public MapperProxy(SqlSession session) {
        super();
        this.session = session;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Collection.class.isAssignableFrom(method.getReturnType())){
            return session.selectList(method.getDeclaringClass().getName()+"."+method.getName(),
                    args==null?null:args[0]);
        }else {
            return session.selectOne(method.getDeclaringClass().getName()+"."+method.getName(),
                    args==null?null:args[0]);
        }
    }
}
