package com.wen.proxy.jdkproxy;

/**
 * 实际对象
 */
public class RealSubject implements Subject {

    public String sayHello(String name) {
        return "hello " + name;
    }

    public String sayGoodBye() {
        return "good bye";
    }

}
