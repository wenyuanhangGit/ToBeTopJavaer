package com.gupaoedu.pattern.delegate.simple;

public class DelegateTest {

    public static void main(String[] args) {
        new Boss().command("加密", new Leader());
    }

}
