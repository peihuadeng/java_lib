package com.dph.common.utils;

import java.util.UUID;

/**
 * 生成器：生成uuid等
 * 
 * @author root
 *
 */
public class Generator {
	
	public static String genUUID() {
		UUID uuid = UUID.randomUUID();
		
		return uuid.toString().replaceAll("-", "");
	}

}
