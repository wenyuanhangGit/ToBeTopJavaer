package com.gupaoedu.pattern.adapter.loginadapter.v2.adapters;

import com.gupaoedu.pattern.adapter.loginadapter.ResultMsg;

public class LoginForWechatAdapter implements LoginAdapter {
    @Override
    public boolean support(Object adapter) {
        return adapter instanceof LoginForWechatAdapter;
    }

    @Override
    public ResultMsg login(String id, Object a) {
        return null;
    }
}
