package com.wen.reflect.getClass;

public class Fanshe {

    public static void main(String[] args) {
        //第一种方式获取Class对象
        Student stu1 = new Student();//new产生了Student对象，Class对象
        Class<? extends Student> stuClass = stu1.getClass();//获取Class对象
        System.out.println(stuClass.getName());

        //第二种方式获取Class对象
        Class stuClass2 = Student.class;
        System.out.println(stuClass == stuClass2);//判断第一种方式获取的Class对象和第二种方式获取的是否是同一个类

        //第三种方式获取Class对象
        try {
            Class<?> stuClass3 = Class.forName("com.wen.reflect.getClass.Student");//注意次字符串为全限定名
            System.out.println(stuClass3 == stuClass2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
