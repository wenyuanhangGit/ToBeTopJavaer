package com.gupaoedu.pattern.adapter.loginadapter.v2.adapters;

import com.gupaoedu.pattern.adapter.loginadapter.ResultMsg;

public class LoginForQQAdapter implements LoginAdapter {
    @Override
    public boolean support(Object adapter) {
        return adapter instanceof LoginForQQAdapter;
    }

    @Override
    public ResultMsg login(String id, Object a) {
        return null;
    }
}
