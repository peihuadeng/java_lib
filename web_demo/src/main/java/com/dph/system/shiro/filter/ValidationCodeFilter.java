package com.dph.system.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

public class ValidationCodeFilter extends AccessControlFilter {
	
	public final static String VALIDATION_CODE_ENABLED = "validation_code_enabled";
	public final static String VALIDATION_CODE_NAME = "validation_code";//验证码存储到session的属性名
	private String validationCodeParam = "validation_code";//前台提交的验证码参数名
	private String failureKeyAttribute = "shiroLoginFailure";//验证失败后存储到的属性名

	public void setValidationCodeParam(String validationCodeParam) {
		this.validationCodeParam = validationCodeParam;
	}

	public void setFailureKeyAttribute(String failureKeyAttribute) {
		this.failureKeyAttribute = failureKeyAttribute;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		if ( (request instanceof HttpServletRequest) && WebUtils.toHttp(request).getMethod().equalsIgnoreCase(POST_METHOD)) {
			HttpServletRequest httpServletRequest = WebUtils.toHttp(request); 
			//表单提交，则则校验验证码是否正确
			HttpSession session = httpServletRequest.getSession();
			Object validationCodeEnabled =  session.getAttribute(VALIDATION_CODE_ENABLED);
			String validationCodeAttribute = (String) session.getAttribute(VALIDATION_CODE_NAME);
			
			String valCodeParam = httpServletRequest.getParameter(validationCodeParam);
			
			if ((validationCodeEnabled == null) || (validationCodeAttribute != null && validationCodeAttribute.equalsIgnoreCase(valCodeParam))) {
				return true;
			}
			
			return false;
		}
		
		return true;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		request.setAttribute(failureKeyAttribute, "validation_error");
		return true;
	}

}
