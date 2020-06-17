package com.gupaoedu.pattern.adapter.poweradapter;

public class PowerAdaptor implements DC5 {

    private AC220 ac220;

    public PowerAdaptor(AC220 ac220) {
        this.ac220 = ac220;
    }

    @Override
    public int outputDC5V() {
        int adapterInput = ac220.outputAC220v();
        int adapterOutput = adapterInput / 44;
        System.out.println("使用PowerAdapter输入AC：" + adapterInput + "V,输出DC：" + adapterOutput + "V");
        return adapterOutput;
    }
}
