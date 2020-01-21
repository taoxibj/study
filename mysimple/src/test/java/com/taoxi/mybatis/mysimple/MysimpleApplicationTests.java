package com.taoxi.mybatis.mysimple;

import org.junit.Test;
import java.util.TreeMap;

public class MysimpleApplicationTests {

    @Test
    public void testMapCaseInsensitive() {
        TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
        treeMap.put("KEY1", 11);
        treeMap.put("key2", 22);
        Integer key2 = treeMap.get("Key2");
        System.out.println("key2's value is " + key2.toString());

    }

}
