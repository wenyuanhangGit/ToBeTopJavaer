package com.wen.reflect.properties;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 我们利用反射和配置文件，可以时：应用程序更新时，对源码无序进行任何修改
 * 我们只需要将新类发送给客户端，并修改配置文件即可
 */
public class Demo {

    public static void main(String[] args) throws Exception {
        //通过反射获取Class对象
        Class clazz = Class.forName(getValue("className"));
        //获取show()方法
        Method m = clazz.getMethod(getValue("methodName"));
        //调用show()方法
        m.invoke(clazz.getConstructor().newInstance());
    }

    /**
     * 根据key获取配置文件中的value
     * @param key key
     * @return value
     * @throws Exception Exception
     */
    public static String getValue(String key) throws Exception {
        Properties properties = new Properties();
        FileReader in = new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\com\\wen\\reflect\\properties\\pro.txt");
        properties.load(in);
        in.close();
        return properties.getProperty(key);
    }

}
