package com.gupaoedu.pattern.strategy.pay;

import com.gupaoedu.pattern.strategy.pay.payport.PayStrategy;

public class PayStrategyTest {

    public static void main(String[] args) {
        Order order = new Order("1", "2020061400000001", 324.45);
        MsgResult result = order.pay(PayStrategy.UNION_PAY);
        System.out.println(result);
    }

}
