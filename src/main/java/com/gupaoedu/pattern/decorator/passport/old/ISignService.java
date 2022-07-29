package com.gupaoedu.pattern.decorator.passport.old;

public interface ISignService {

    ResultMsg registry(String username, String password);


    ResultMsg login(String username, String password);

}
