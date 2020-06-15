package com.wen.reflect.method;

import java.lang.reflect.Method;

/**
 * 获取成员方法并调用：
 * 1.批量的
 *      获取所有"公有方法":包含了父类的方法也包含Object类
 *          public Method[] getMethods();
 *      获取所有的成员方法，包括私有的(不包括集成的)
 *          public Method[] getDeclaredMethods();
 * 2.获取单个的
 *      name : 方法名
 *      parameterTypes : 形参
 *      public Method getMethod(String name, Class<?>... parameterTypes)
 *
 *      public Method getDeclaredMethod(String name, Class<?>... parameterTypes)
 *
 *      调用方法：
 *          obj : 要调用方法的对象
 *          args : 调用方法是所传递的实参
 *          Method --> public Object invoke(Object obj, Object... args);
 */
public class MethodClass {

    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("com.wen.reflect.method.Student");

        System.out.println("**********************获取所有的“公有”方法***********************");
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            System.out.println(method);
        }

        System.out.println("**********************获取所有方法，包括私有的***********************");
        methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method);
        }

        System.out.println("**********************获取公有的show1()方法***********************");
        Method m = clazz.getMethod("show1", String.class);
        System.out.println(m);
        //实例化一个Student对象
        Object obj = clazz.getConstructor().newInstance();
        m.invoke(obj, "刘德华");

        System.out.println("**********************获取私有的show4()方法***********************");
        m = clazz.getDeclaredMethod("show4", int.class);
        System.out.println(m);
        m.setAccessible(true);

        Object result = m.invoke(obj, 20);
        System.out.println("返回值：" + result);

    }

}
