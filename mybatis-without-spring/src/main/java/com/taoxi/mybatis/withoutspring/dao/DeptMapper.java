package com.taoxi.mybatis.withoutspring.dao;


import com.taoxi.mybatis.withoutspring.beans.Dept;

public interface DeptMapper {
   public Dept deptFindById(Integer deptno);
   public void deptSave(Dept dept);
}
