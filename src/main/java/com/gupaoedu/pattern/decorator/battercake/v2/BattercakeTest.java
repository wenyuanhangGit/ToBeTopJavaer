package com.gupaoedu.pattern.decorator.battercake.v2;

public class BattercakeTest {

    public static void main(String[] args) {
        Battercake battercake;

        battercake = new BaseBattercake();

        battercake = new EggDecorator(battercake);

        battercake = new EggDecorator(battercake);

        battercake = new EggDecorator(battercake);

        battercake = new SausageDecoratro(battercake);

        System.out.println(battercake.getMsg() + ",总价：" + battercake.getPrice());

    }

}
