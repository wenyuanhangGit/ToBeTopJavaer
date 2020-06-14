package com.gupaoedu.pattern.strategy.promotion;

public class CashBackStrategy implements PromotionStrategy {
    @Override
    public void doPromotion() {
        System.out.println("返现，优惠！");
    }
}
