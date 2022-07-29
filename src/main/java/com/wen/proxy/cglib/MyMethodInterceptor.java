package com.wen.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 自定义MethodInterceptor
 */
public class MyMethodInterceptor implements MethodInterceptor {

    /**
     * @param o cglib生成的代理对象
     * @param method 被代理对象方法
     * @param objects 方法入参
     * @param methodProxy 代理方法
     * @return 被代理方法返回值
     * @throws Throwable Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("=====插入前置通知=====");
        Object object = methodProxy.invokeSuper(o, objects);
        System.out.println("=====插入后置通知=====");
        return object;
    }

}
