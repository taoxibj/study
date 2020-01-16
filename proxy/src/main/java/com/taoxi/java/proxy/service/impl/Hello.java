package com.taoxi.java.proxy.service.impl;

import com.taoxi.java.proxy.service.HelloInterface;

public class Hello implements HelloInterface {
    @Override
    public void sayHello() {
        System.out.println("Hello tx!");
    }
}
