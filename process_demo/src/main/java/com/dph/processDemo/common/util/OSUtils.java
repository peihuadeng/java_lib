/**
 * @author peihuadeng
 *
 */
package com.dph.processDemo.common.util;

import com.dph.processDemo.common.cache.constEnum.OSType;

/**
 * 
 * 操作系统工具
 * 
 * @author peihuadeng
 *
 */
public class OSUtils {

	public static byte getOSType() {
		// 获取操作系统类型
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("windows")) {
			return OSType.WINDOWS;
		} else if (osName.contains("linux")) {
			return OSType.LINUX;
		} else {
			return -1;
		}
	}

	public static void main(String[] args) {
		byte osType = OSUtils.getOSType();
		System.out.println(osType);
	}

}
