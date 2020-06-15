package com.wen.proxy.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

/**
 * 原文：https://blog.csdn.net/yhl_jxy/article/details/80633194
 */
public class Client {

    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + "\\src\\main\\java\\com\\wen\\proxy\\cglib\\code";
        //代理类class文件存入本地磁盘方便我们反编译查看源码
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, path);
        //通过cglib动态代理获取代理对象的过程
        Enhancer enhancer = new Enhancer();
        //设置enhance对象的父类
        enhancer.setSuperclass(HelloService.class);
        //设置enhancer的回调对象
        enhancer.setCallback(new MyMethodInterceptor());
        //创建代理对象
        HelloService proxy = (HelloService) enhancer.create();
        //通过代理对象调用目标方法
        proxy.sayHello();
    }

}
