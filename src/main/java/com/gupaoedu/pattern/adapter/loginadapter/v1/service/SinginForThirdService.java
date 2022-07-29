package com.gupaoedu.pattern.adapter.loginadapter.v1.service;

import com.gupaoedu.pattern.adapter.loginadapter.ResultMsg;

public class SinginForThirdService extends SinginService {

    public ResultMsg loginForQQ(String openId) {
        //1.openId是全局唯一，我们可以把它当做是一个用户名（加长）
        //2.密码默认为QQ_EMPTY
        //3.注册（在原有系统里面创建一个用户）
        //4.调用原来的登录方法
        return loginForRegistry(openId, null);
    }

    public ResultMsg loginForWechat(String openId) {
        return null;
    }

    public ResultMsg loginForToken(String token) {
        return null;
    }

    public ResultMsg loginForTelphone(String telphone, String coe) {
        return null;
    }

    public ResultMsg loginForRegistry(String username, String password) {
        super.registry(username, null);
        return super.login(username, null);
    }

}
