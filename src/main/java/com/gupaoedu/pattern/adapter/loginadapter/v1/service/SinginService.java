package com.gupaoedu.pattern.adapter.loginadapter.v1.service;

import com.gupaoedu.pattern.adapter.loginadapter.ResultMsg;

public class SinginService {

    public ResultMsg registry(String username, String password) {
        return new ResultMsg(200, "", "登录成功!");
    }

    public ResultMsg login(String username, String password) {
        return null;
    }

}
