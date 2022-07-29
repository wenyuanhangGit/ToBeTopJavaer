package com.gupaoedu.pattern.decorator.passport.upgrade;

import com.gupaoedu.pattern.decorator.passport.old.ISignService;
import com.gupaoedu.pattern.decorator.passport.old.ResultMsg;

public class SigninForThridService implements ISignForThirdService {

    private ISignService signService;

    public SigninForThridService(ISignService signService) {
        this.signService = signService;
    }

    @Override
    public ResultMsg loginForQQ(String id) {
        return null;
    }

    @Override
    public ResultMsg loginForWechat(String id) {
        return null;
    }

    @Override
    public ResultMsg loginForToken(String id) {
        return null;
    }

    @Override
    public ResultMsg loginForTelphone(String id) {
        return null;
    }

    @Override
    public ResultMsg registry(String id) {
        return null;
    }

    @Override
    public ResultMsg registry(String username, String password) {
        return null;
    }

    @Override
    public ResultMsg login(String username, String password) {
        return signService.login(username, password);
    }
}
