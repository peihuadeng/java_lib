package com.dph.common.utils;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private final static String[] timeUnit = new String[]{"毫秒", "秒", "分钟", "小时", "天"};
	/**
	 * 毫秒数换算成“x天x小时x分钟x秒x毫秒”
	 * @param time
	 * @param scale：精确度，0：毫秒，1：秒，2：分钟，3：小时，4：天
	 * @return
	 */
	public static String formatMillis(long time, int i) {
		StringBuilder builder = new StringBuilder();
		long number = 0;
		
		if (time < 1000) {
			if (i <= 0) {
				return builder.insert(0, "毫秒").insert(0, time).toString();				
			} else {
				return builder.insert(0, timeUnit[i]).insert(0, 0).toString();
			}
		} else {
			number = time % 1000;
			time /= 1000;
			if (i <= 0) {
				builder.insert(0, "毫秒").insert(0, number);					
			}			
		}
		
		if (time < 60) {
			if (i <= 1) {
				return builder.insert(0, "秒").insert(0, time).toString();				
			} else {
				return builder.insert(0, timeUnit[i]).insert(0, 0).toString();
			}
		} else {
			number = time % 60;
			time /= 60;
			if (i <= 1) {
				builder.insert(0, "秒").insert(0, number);					
			}			
		}
		
		if (time < 60) {
			if (i <= 2) {
				return builder.insert(0, "分钟").insert(0, time).toString();				
			} else {
				return builder.insert(0, timeUnit[i]).insert(0, 0).toString();
			}
		} else {
			number = time % 60;
			time /= 60;
			if (i <= 2) {
				builder.insert(0, "分钟").insert(0, number);					
			}			
		}
		
		if (time < 24) {
			if (i <= 3) {
				return builder.insert(0, "小时").insert(0, time).toString();				
			} else {
				return builder.insert(0, timeUnit[i]).insert(0, 0).toString();
			}
		} else {
			number = time % 24;
			time /= 24;
			if (i <= 3) {
				builder.insert(0, "小时").insert(0, number);					
			}			
		}
		
		builder.insert(0, "天").insert(0, time);
		
		return builder.toString();
	}

}
