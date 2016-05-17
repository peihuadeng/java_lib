package com.dph.filter;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;

/**
 * 页面缓存过滤器：增加ie6，ie7压缩支持
 * 
 * @author root
 *
 */
public class PageEhcacheFilter extends SimplePageCachingFilter {

	@Override
	protected boolean acceptsGzipEncoding(HttpServletRequest request) {
		boolean ie6 = headerContains(request, "User-Agent", "MSIE 6.0");
		boolean ie7 = headerContains(request, "User-Agent", "MSIE 7.0");
		return super.acceptsEncoding(request, "gzip") || ie6 || ie7;
	}

	private boolean headerContains(final HttpServletRequest request, final String header, final String value) {
		logRequestHeaders(request);
		final Enumeration<String> accepted = request.getHeaders(header);

		while (accepted.hasMoreElements()) {
			final String headerValue = accepted.nextElement();
			if (headerValue.indexOf(value) != -1) {
				return true;
			}
		}

		return false;
	}

}
