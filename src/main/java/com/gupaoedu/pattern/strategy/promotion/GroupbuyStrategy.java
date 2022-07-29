package com.gupaoedu.pattern.strategy.promotion;

public class GroupbuyStrategy implements PromotionStrategy {
    @Override
    public void doPromotion() {
        System.out.println("团购，优惠！");
    }
}
