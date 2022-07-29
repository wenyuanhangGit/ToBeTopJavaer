package com.gupaoedu.pattern.decorator.battercake.v1;

public class BattercakeWithEgg extends BatterCake {

    @Override
    protected String getMsg() {
        return super.getMsg() + "+1个鸡蛋";
    }

    @Override
    public int getPrice() {
        return super.getPrice() + 1;
    }
}
