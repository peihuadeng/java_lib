package com.dph.hessian.server.demo.impl;

import com.dph.hessian.server.demo.HelloWorld;

public class HelloWorldImpl implements HelloWorld {

	@Override
	public String hello(String name) {
		String result = String.format("hello, %s", name);
		System.out.println("invoke hello:" + result);
		
		return result;
	}

}
