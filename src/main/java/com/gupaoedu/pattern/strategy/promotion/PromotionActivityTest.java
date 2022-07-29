package com.gupaoedu.pattern.strategy.promotion;

public class PromotionActivityTest {

//    public static void main(String[] args) {
//        PromotionActivity activity618 = new PromotionActivity(new CouponStrategy());
//        PromotionActivity activity1111 = new PromotionActivity(new CashBackStrategy());
//
//        activity618.execute();
//        activity1111.execute();
//    }

    public static void main(String[] args) {
        String promotionKey = "GROUPBY";
        PromotionActivity promotionActivity = new PromotionActivity(PromotionStrategyFactory.getPromotionStrategy(promotionKey));
        promotionActivity.execute();
    }

}
