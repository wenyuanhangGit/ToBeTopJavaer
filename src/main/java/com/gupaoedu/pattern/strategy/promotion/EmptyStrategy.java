package com.gupaoedu.pattern.strategy.promotion;

public class EmptyStrategy implements PromotionStrategy {


    @Override
    public void doPromotion() {
        System.out.println("原价，无优惠！");
    }
}
