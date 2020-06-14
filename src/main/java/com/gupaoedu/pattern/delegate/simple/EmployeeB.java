package com.gupaoedu.pattern.delegate.simple;

public class EmployeeB implements IEmployee {
    @Override
    public void doing(String command) {
        System.out.println("我是B员工，擅长架构，开始干活 ： " + command);
    }
}
