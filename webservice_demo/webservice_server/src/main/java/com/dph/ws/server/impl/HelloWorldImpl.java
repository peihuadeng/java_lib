package com.dph.ws.server.impl;

import com.dph.ws.server.HelloWorld;

public class HelloWorldImpl implements HelloWorld {

	@Override
	public String hello(String name) {
		String result = String.format("hello, %s", name);
		System.out.println("--------invoke hello():" + result);
		
		return result;
	}

}
