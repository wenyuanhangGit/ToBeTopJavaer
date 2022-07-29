package com.wen.reflect.generic;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * 通过反射越过泛型检查
 *
 * 例如：有一个String泛型的集合，怎样能向这个集合中添加一个Integer类型的值？
 */
public class Demo {

    public static void main(String[] args) throws Exception {
        ArrayList<String> strList = new ArrayList<>();
        strList.add("aaa");
        strList.add("bbb");

        Class<? extends ArrayList> clazz = strList.getClass();
        Method method = clazz.getMethod("add", Object.class);
        method.invoke(strList, 100);

        for (Object obj : strList) {
            System.out.println(obj);
        }
    }

}
