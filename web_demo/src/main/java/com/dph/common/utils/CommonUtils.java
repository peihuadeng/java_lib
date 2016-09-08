package com.dph.common.utils;

import javax.servlet.http.HttpServletRequest;

public class CommonUtils {
	
	public static String getRemoteIP(HttpServletRequest request) {
		String realIp = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(realIp) && !"unknown".equalsIgnoreCase(realIp)) {
			return realIp;
		}

		// 多次反向代理后会有多个IP值，第一个不为unknown是真实IP。
		String forwardedForIp = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(forwardedForIp)) {
			String[] forwardedIpList = forwardedForIp.split(",");
			for (String forwardedIp : forwardedIpList) {
				if (!"unknown".equalsIgnoreCase(forwardedIp)) {
					return forwardedIp;
				}
			}
		}

		return request.getRemoteAddr();
	}

}
