package com.gupaoedu.pattern.adapter.loginadapter.v2;

import com.gupaoedu.pattern.adapter.loginadapter.ResultMsg;

public interface IPassportForThird {

    ResultMsg loginForQQ(String id);

    ResultMsg loginWechat(String id);

    ResultMsg loginForToken(String token);

    ResultMsg loginForTelphone(String telphone, String code);

    ResultMsg loginForRegistry(String username, String password);

}
