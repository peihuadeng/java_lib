package com.dph.system.shiro.credentials;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
	
	private Cache<String, AtomicInteger> passwordRetryCache;
	private int retryLoginLimit = 5;
	
	public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
		passwordRetryCache = cacheManager.getCache("passwordRetryCache");
	}

	public void setRetryLoginLimit(int retryLoginLimit) {
		this.retryLoginLimit = retryLoginLimit;
	}

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		String username = (String)token.getPrincipal();
		//retry count+1
		AtomicInteger retryCount = passwordRetryCache.get(username);
		if (retryCount == null) {
			retryCount = new AtomicInteger(0);
			passwordRetryCache.put(username, retryCount);
		}
		
		if (retryCount.incrementAndGet() > retryLoginLimit) {
			//if retry count > retryLimitCount throw
			throw new ExcessiveAttemptsException();
		}
		
		boolean matches = super.doCredentialsMatch(token, info);
		if (matches) {
			//clear retry count
			passwordRetryCache.remove(username);
		}
		
		return matches;
	}
	
	
}
