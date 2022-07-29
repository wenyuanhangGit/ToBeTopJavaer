package com.gupaoedu.pattern.adapter.poweradapter;

public class PowerAdapatorTest {

    public static void main(String[] args) {
        DC5 dc5 = new PowerAdaptor(new AC220());
        dc5.outputDC5V();
    }

}
