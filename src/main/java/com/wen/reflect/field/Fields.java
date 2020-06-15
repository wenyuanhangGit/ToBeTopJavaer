package com.wen.reflect.field;

import java.lang.reflect.Field;

/**
 * 获取成员变量并调用：
 * 1.批量的
 *      获取所有的"公有字段"
 *          Field[] getFields();
 *      获取所有字段，包括：私有、受保护、默认、公有；
 *          Field[] getDeclaredFields();
 * 2.获取单个的：
 *      获取某个"公有的"字段
 *          public Field getField(String fieldName);
 *      获取某个字段，可以是私有的
 *          public Field getDeclaredFiled(String fieldName);
 *
 *      设置字段的值：
 *          obj : 要设置的字段所属的对象
 *          value : 要为字段设置的值
 *          Field --> public void set(Object obj, Object value);
 */
public class Fields {

    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("com.wen.reflect.field.Student");

        System.out.println("*********************获取所有公有的字段**********************");
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            System.out.println(field);
        }

        System.out.println("*********************获取所有的字段(包括私有、受保护、默认的)**********************");
        fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field);
        }

        System.out.println("*********************获取公有字段并调用**********************");
        Field f = clazz.getField("name");
        System.out.println(f);
        //获取一个对象
        Object obj = clazz.getConstructor().newInstance();
        //为字段设置值
        f.set(obj, "刘德华");
        //验证
        Student stu = (Student) obj;
        System.out.println("验证姓名 ： " + stu.name);

        System.out.println("*********************获取私有字段并调用**********************");
        f = clazz.getDeclaredField("phoneNum");
        System.out.println(f);
        f.setAccessible(true);
        f.set(obj, "18888888888");
        System.out.println("验证电话：" + stu);


    }

}
