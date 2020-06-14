package com.gupaoedu.pattern.delegate.simple;

public class EmployeeA implements IEmployee {
    @Override
    public void doing(String command) {
        System.out.println("我是A员工，擅长加密，开始干活 ： " + command);
    }
}
