package com.gupaoedu.pattern.adapter.loginadapter.v2.adapters;

import com.gupaoedu.pattern.adapter.loginadapter.ResultMsg;

public interface LoginAdapter {

    boolean support(Object adapter);

    ResultMsg login(String id, Object a);

}
