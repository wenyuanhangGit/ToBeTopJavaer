package com.gupaoedu.pattern.strategy.promotion;

import java.util.HashMap;
import java.util.Map;

/**
 * 单例模式，容器注册式单例
 * @author wen
 */
public class PromotionStrategyFactory {

    private PromotionStrategyFactory() {}

    private static Map<String, PromotionStrategy> PROMOTION_STRATEGY_MAP = new HashMap<>();

    private static final PromotionStrategy NON_STRATEGY = new EmptyStrategy();

    static {
        PROMOTION_STRATEGY_MAP.put("COUPON", new CouponStrategy());
        PROMOTION_STRATEGY_MAP.put("CASHBACK", new CashBackStrategy());
        PROMOTION_STRATEGY_MAP.put("GROUPBY", new GroupbuyStrategy());
    }

    public static PromotionStrategy getPromotionStrategy(String promotionKey) {
        PromotionStrategy promotionStrategy = PROMOTION_STRATEGY_MAP.get(promotionKey);
        return promotionStrategy == null ? NON_STRATEGY : promotionStrategy;
    }

    private interface PromotionKey {
        String COUPON = "COUPON";
        String CASHBACK = "CASHBACK";
        String GROUPBY = "GROUPBY";
    }

}
