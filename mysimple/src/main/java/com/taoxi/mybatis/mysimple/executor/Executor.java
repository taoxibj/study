package com.taoxi.mybatis.mysimple.executor;

import com.taoxi.mybatis.mysimple.config.MappedStatement;

import java.util.List;

public interface Executor {
    public <E> List<E> query(MappedStatement ms, Object parameter) throws Exception;
}
