package com.taoxi.java.proxy.service.impl;

import com.taoxi.java.proxy.service.ByeInterface;

public class Bye implements ByeInterface {
    @Override
    public void sayBye() {
        System.out.println("Bye zhanghao!");
    }
}
