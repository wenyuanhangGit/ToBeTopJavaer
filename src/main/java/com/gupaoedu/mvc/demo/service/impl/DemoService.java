package com.gupaoedu.mvc.demo.service.impl;

import com.gupaoedu.mvc.demo.service.IDemoService;
import com.gupaoedu.mvc.mvcframework.annotation.GPService;

/**
 * 核心业务逻辑
 */
@GPService
public class DemoService implements IDemoService {

	public String get(String name) {
		return "My name is " + name;
	}

}
