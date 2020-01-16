package com.taoxi.java.proxy.dynamic;

import com.taoxi.java.proxy.service.ByeInterface;
import com.taoxi.java.proxy.service.HelloInterface;
import com.taoxi.java.proxy.service.impl.Bye;
import com.taoxi.java.proxy.service.impl.Hello;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyHandler implements InvocationHandler {
    private Object object;
    public ProxyHandler(Object object){
        this.object = object;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before invoke "  + method.getName());
        method.invoke(object, args);
        System.out.println("After invoke " + method.getName());
        return null;
    }

    public static void main(String[] args) {
        System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        HelloInterface hello = new Hello();
        ByeInterface bye = new Bye();
        InvocationHandler handler = new ProxyHandler(hello);
        InvocationHandler handler1 = new ProxyHandler(bye);

        HelloInterface proxyHello = (HelloInterface) Proxy.newProxyInstance(hello.getClass().getClassLoader(), hello.getClass().getInterfaces(), handler);
        ByeInterface proxyHello1 = (ByeInterface) Proxy.newProxyInstance(bye.getClass().getClassLoader(), bye.getClass().getInterfaces(), handler1);

        proxyHello.sayHello();
        proxyHello1.sayBye();
    }

}
