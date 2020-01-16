package com.taoxi.java.proxy.st;

import com.taoxi.java.proxy.service.HelloInterface;
import com.taoxi.java.proxy.service.impl.Hello;

public class HelloProxy implements HelloInterface {
    private HelloInterface helloInterface = new Hello();
    @Override
    public void sayHello() {
        System.out.println("Before invoke sayHello" );
        helloInterface.sayHello();
        System.out.println("After invoke sayHello");
    }


    public static void main(String[] args) {
        HelloProxy helloProxy = new HelloProxy();
        helloProxy.sayHello();
    }

}
