package com.gupaoedu.pattern.decorator.battercake.v2;

public class SausageDecoratro extends BattercakeDecorator {
    public SausageDecoratro(Battercake battercake) {
        super(battercake);
    }

    @Override
    protected String getMsg() {
        return super.getMsg() + "+1个香肠";
    }

    @Override
    protected int getPrice() {
        return super.getPrice() +2;
    }
}
