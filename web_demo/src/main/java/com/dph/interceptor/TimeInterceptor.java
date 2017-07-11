package com.dph.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * calculate how long to process request.
 *
 * @author root
 *
 */
public class TimeInterceptor extends HandlerInterceptorAdapter {

	private final static Logger logger = Logger.getLogger(TimeInterceptor.class);
	private ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("start-time");

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		long startTime = System.currentTimeMillis();
		startTimeThreadLocal.set(startTime);

		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		long endTime = System.currentTimeMillis();
		long startTime = startTimeThreadLocal.get();
		startTimeThreadLocal.remove();//清除
		long consumeTime = endTime - startTime;
		logger.info(String.format("'%s' consume %dms", request.getRequestURI(), consumeTime));

		super.afterCompletion(request, response, handler, ex);
	}

}
