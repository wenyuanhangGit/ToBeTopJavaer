package com.wen.proxy.jdk;

//import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理演示
 *
 * 原文：https://blog.csdn.net/jiankunking/article/details/52143504
 */
public class DynamicProxyDemonstration {

    public static void main(String[] args) {
        //代理的真实对象
        Subject realSubject = new RealSubject();

        /*
          InvocationHandlerImpl 实现了InvocationHandler接口，并能实现方法调用从代理类到委托类的分派转发
          其内部通常包含指向委托类实例的引用，用于真正执行分派转发过来的方法调用
          即：要代理哪个真实对象，就将改对象传进去，最后是通过该真实对象来调用器方法
         */
        InvocationHandler handler = new InvocationHandlerImpl(realSubject);

        ClassLoader loader = realSubject.getClass().getClassLoader();
        Class<?>[] interfaces = realSubject.getClass().getInterfaces();

        /*
          该方法用于为指定类装载器，一组接口及调用处理器生成动态代理类实例
         */
        Subject subject = (Subject) Proxy.newProxyInstance(loader, interfaces, handler);

        System.out.println("动态代理对象的类型： " + subject.getClass().getName());
        System.out.println("--------------------------------------");

        String hello = subject.sayHello("wen");

        System.out.println("--------------------------------------");
        System.out.println(hello);

        //将生成的字节码保存到本地
        createProxyClassFile();
    }

    private static void createProxyClassFile() {
        FileOutputStream out = null;
        try {

            String name = "ProxySubject";
//            byte[] data = ProxyGenerator.generateProxyClass(name, new Class[]{Subject.class});


            Class cl = Class.forName("java.lang.reflect.ProxyGenerator");
            Method m = cl.getDeclaredMethod("generateProxyClass", String.class, Class[].class);
            m.setAccessible(true);
            byte[] data = (byte[]) m.invoke(null, name, new Class[]{Subject.class});

            String path = System.getProperty("user.dir") + "\\src\\main\\java\\com\\wen\\proxy\\jdk\\";
            out = new FileOutputStream(path + name + ".class");
            out.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
