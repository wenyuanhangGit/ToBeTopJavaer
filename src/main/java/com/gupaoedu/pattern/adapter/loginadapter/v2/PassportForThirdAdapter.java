package com.gupaoedu.pattern.adapter.loginadapter.v2;

import com.gupaoedu.pattern.adapter.loginadapter.ResultMsg;
import com.gupaoedu.pattern.adapter.loginadapter.v1.service.SinginService;
import com.gupaoedu.pattern.adapter.loginadapter.v2.adapters.*;

/**
 * 结合策略模式、工厂模式、适配器模式
 */
public class PassportForThirdAdapter extends SinginService implements IPassportForThird {
    @Override
    public ResultMsg loginForQQ(String id) {
        return processLogin(id, LoginForQQAdapter.class);
    }

    @Override
    public ResultMsg loginWechat(String id) {
        return processLogin(id, LoginForWechatAdapter.class);
    }

    @Override
    public ResultMsg loginForToken(String token) {
        return processLogin(token, LoginForTokenAdapter.class);
    }

    @Override
    public ResultMsg loginForTelphone(String telphone, String code) {
        return processLogin(telphone, LoginForTelAdapter.class);
    }

    @Override
    public ResultMsg loginForRegistry(String username, String password) {
        super.registry(username, password);
        return super.login(username, password);
    }

    private ResultMsg processLogin(String key, Class<? extends LoginAdapter> clazz) {
        try {
            LoginAdapter adapter = clazz.newInstance();
            if (adapter.support(adapter)) {
                return adapter.login(key, adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
