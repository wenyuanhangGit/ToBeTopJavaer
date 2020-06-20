package com.gupaoedu.pattern.decorator.passport.upgrade;

import com.gupaoedu.pattern.decorator.passport.old.ISignService;
import com.gupaoedu.pattern.decorator.passport.old.ResultMsg;

public interface ISignForThirdService extends ISignService {

    ResultMsg loginForQQ(String id);

    ResultMsg loginForWechat(String id);

    ResultMsg loginForToken(String id);

    ResultMsg loginForTelphone(String id);

    ResultMsg registry(String id);

}
