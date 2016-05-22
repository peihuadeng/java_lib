package com.dph.system.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dph.common.web.BaseController;

@Controller
@RequestMapping("sys")
public class SysController extends BaseController {

	@Value("${shiro.retryLoginLimit}")
	private int retryLoginLimit = 5;

	@RequestMapping("login")
	public String loginView(HttpServletRequest request, HttpServletResponse response) {
		String destPage = "system/sys/login";

		String errorClassName = (String) request.getAttribute("shiroLoginFailure");
		if (UnknownAccountException.class.getName().equals(errorClassName)) {
			request.setAttribute("error", "用户名/密码错误");
		} else if (IncorrectCredentialsException.class.getName().equals(errorClassName)) {
			request.setAttribute("error", "用户名/密码错误");
		} else if (LockedAccountException.class.getName().equals(errorClassName)) {
			request.setAttribute("error", "用户已被锁定，请联系管理员");
		} else if (ExcessiveAttemptsException.class.getName().equals(errorClassName)) {
			request.setAttribute("error", "登录失败已超过" + retryLoginLimit + "次，请稍等十分钟后再试");
		} else if (errorClassName != null) {
			request.setAttribute("error", "未知错误：" + errorClassName);
		}
		return destPage;
	}
	
	@RequestMapping("casLoginFail")
	public String loginFail() {
		String destPage = "system/sys/casLoginFail";
		
		return destPage;
	}

	@RequestMapping("unauthorized")
	public String unauthorized(HttpServletRequest request, HttpServletResponse response) {
		String destPage = "error/unauthorized";
		return destPage;
	}

}
