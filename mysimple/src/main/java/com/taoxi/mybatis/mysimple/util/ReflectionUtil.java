package com.taoxi.mybatis.mysimple.util;

import com.sun.xml.internal.messaging.saaj.soap.impl.FaultElementImpl;

import javax.swing.text.StyledEditorKit;
import java.awt.image.RescaleOp;
import java.lang.reflect.Field;
import java.sql.ResultSet;

public class ReflectionUtil {

    public static void setPropToBean(Object bean, String propName, Object value) {
        Field field;
        try {
            field = field = bean.getClass().getDeclaredField(propName);
            field.setAccessible(true);
            field.set(bean, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    public static void setPropToBeanFromResult(Object entity, ResultSet resultSet) throws Exception {
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        for (int i = 0;i<declaredFields.length;i++) {
            if (declaredFields[i].getType().getSimpleName().equals("String")){
                setPropToBean(entity, declaredFields[i].getName(), resultSet.getString(declaredFields[i].getName()));
            } else if (declaredFields[i].getType().getSimpleName().equals("Integer")){
                setPropToBean(entity, declaredFields[i].getName(), resultSet.getInt(declaredFields[i].getName()));
            } else if (declaredFields[i].getType().getSimpleName().equals("Long")){
                setPropToBean(entity, declaredFields[i].getName(), resultSet.getLong(declaredFields[i].getName()));
            }
        }
    }


    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class<?> clazz = Class.forName("com.taoxi.mybatis.mysimple.entity.Dept");
        Object dept = clazz.newInstance();
        ReflectionUtil.setPropToBean(dept, "dName", "zhongguo");
        System.out.println(dept);
    }

}
