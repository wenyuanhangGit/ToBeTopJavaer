package com.wen.reflect.main;

import java.lang.reflect.Method;

/**
 * 获取Student类的main方法，不要与当前的main方法搞混了
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("com.wen.reflect.main.Student");

        Method method = clazz.getMethod("main", String[].class);

        method.invoke(null, (Object) new String[]{"a", "b", "c"});

    }

}
