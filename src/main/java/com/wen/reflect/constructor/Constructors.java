package com.wen.reflect.constructor;

import java.lang.reflect.Constructor;

/**
 * 通过Class对象可以获取某个类中的：构造方法、成员变量、成员方法；并访问成员
 *
 * 1.获取构造方法：
 *      1).批量的方法：
 *          所有"公有的"构造方法
 *              public Constructor[] getConstructors();
 *          获取所有的构造方法(包括私有、受保护、默认、公有)
 *              public Constructor[] getDeclaredConstructors();
 *      2).获取单个的方法，并调用
 *          获取单个"公有的"构造方法
 *              public Constructor getConstructor(Class... parameterTypes)
 *          获取"某个构造方法"可以是私有的，或受保护、默认、公有；
 *              public Constructor getDeclaredConstructor(Class... parameterTypes)
 *
 *      调用构造方法：
 *          Constructor --> newInstance(Object... initargs)
 */
public class Constructors {

    public static void main(String[] args) throws Exception {
        //1.加载Class对象
        Class clazz = Class.forName("com.wen.reflect.constructor.Student");

        //2.获取所有公有构造方法
        System.out.println("*********************所有公有构造方法**********************");
        Constructor[] conArr = clazz.getConstructors();
        for (Constructor c : conArr) {
            System.out.println(c);
        }

        System.out.println("*********************所有的构造方法(包括：私有、受保护、默认、公有)**********************");
        conArr = clazz.getDeclaredConstructors();
        for (Constructor c : conArr) {
            System.out.println(c);
        }

        System.out.println("*********************获取公有、无参的构造方法**********************");
        //因为是无参的构造方法所以类型是null，不写也可以：这里需要的是一个参数的类型，切记是类型
        //返回的是描述这个无参构造函数的对象
        Constructor con = clazz.getConstructor(null);
        System.out.println("con = " + con);
        //调用构造方法
        Object obj = con.newInstance();
        System.out.println("obj = " + obj);

        System.out.println("*********************获取私有构造方法，并调用**********************");
        con = clazz.getDeclaredConstructor(char.class);
        System.out.println(con);
        //调用构造方法，暴力访问，忽略访问修饰符
        con.setAccessible(true);
        obj = con.newInstance('男');
        System.out.println(obj);

    }

}
