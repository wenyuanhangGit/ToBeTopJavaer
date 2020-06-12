package com.wen.proxy.jdkproxy;

/**
 * 需要动态代理的接口
 */
public interface Subject {

    /**
     * 你好
     * @param name name
     * @return String
     */
    String sayHello(String name);

    /**
     * 再见
     * @return String
     */
    String sayGoodBye();

}
