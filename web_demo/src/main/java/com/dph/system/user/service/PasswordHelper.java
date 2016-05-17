package com.dph.system.user.service;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dph.system.user.entity.User;

@Component
public class PasswordHelper {

	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

	@Value("${shiro.hashAlgorithmName}")
	private String algorithmName = "md5";

	@Value("${shiro.hashIterations}")
	private int hashIterations = 2;
	
	@Value("${shiro.storedCredentialsHexEncoded}")
	private boolean storedCredentialsHexEncoded = true;

	public void encryptPassword(User user) {
		user.setSalt(randomNumberGenerator.nextBytes().toHex());
		
		SimpleHash simpleHash = new SimpleHash(algorithmName,
				user.getPassword(),
				ByteSource.Util.bytes(user.getCredentialsSalt()),
				hashIterations);
		
		String newPassword = null;
		if (storedCredentialsHexEncoded == true) {
			newPassword = simpleHash.toHex();
		} else {
			newPassword = simpleHash.toBase64();
		}
		
		user.setPassword(newPassword);
	}
}
