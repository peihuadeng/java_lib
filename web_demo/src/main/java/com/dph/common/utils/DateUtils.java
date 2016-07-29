package com.dph.common.utils;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = {
			"yyyy",
			"yyyy-MM", "yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.S", 
			"yyyy/MM", "yyyy/MM/dd", "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss.S", 
			"yyyy.MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm:ss.S"};
	
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
	
	/**
	 * 日期型字符串转化为日期 格式
	 */
	public static Date parseDate(String str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str, parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd HH:mm:ss.S） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"等
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		
		String formatDate = null;
		if (StringUtils.isNotBlank(pattern)) {
			formatDate = DateFormatUtils.format(date, pattern);
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss.S");
		}

		return formatDate;
	}
	
	/**
	 * 得到日期年份字符串，转换格式("yyyy")
	 * @param date
	 * @return
	 */
	public static String formatYear(Date date) {
		return format(date, "yyyy");
	}
	
	/**
	 * 得到日期年月字符串，转换格式("yyyy-MM")
	 * @param date
	 * @return
	 */
	public static String formatMonth(Date date) {
		return format(date, "yyyy-MM");
	}
	
	/**
	 * 得到日期年月日字符串，转换格式("yyyy-MM-dd")
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return format(date, "yyyy-MM-dd");
	}

	/**
	 * 得到日期年月日时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到日期年月日时间字符串，转换格式（yyyy-MM-dd HH:mm:ss.S）
	 */
	public static String formatDateMilliTime(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss.S");
	}
	
	public static void main(String[] args) {
		Date date = new Date();
		System.out.println(DateUtils.formatYear(date));
		System.out.println(DateUtils.formatMonth(date));
		System.out.println(DateUtils.formatDate(date));
		System.out.println(DateUtils.formatDateTime(date));
		System.out.println(DateUtils.formatDateMilliTime(date));
	}
}
