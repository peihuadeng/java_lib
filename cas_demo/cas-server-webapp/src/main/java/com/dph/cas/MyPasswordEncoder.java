package com.dph.cas;

import org.jasig.cas.authentication.handler.PasswordEncoder;

public class MyPasswordEncoder implements PasswordEncoder {

	/**
	 * 参数password来自用户输入
	 */
	@Override
	public String encode(String password) {
		//TODO:使用散列算法求密码的hash值并返回
		return password;
	}

}
