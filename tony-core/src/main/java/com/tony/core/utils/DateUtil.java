package com.tony.core.utils;

import java.util.Date;

import org.joda.time.DateTime;

/**
 * 日期、时间工具对象
 * @author Tony.lai
 * 
 */
public class DateUtil {
	

	/**
	 * 获取当前时间的日期信息(yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	public static String currentStr() {
		return DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
	}

	public static String yyyyMMdd() {
		return DateTime.now().toString("yyyy-MM-dd");
	}
	
	/**
	 * 获取当前时间的指定格式的日期信息
	 * @param pattern yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String currentStr(String pattern) {
		if (null == pattern) {
			return currentStr();
		}
		return DateTime.now().toString(pattern);
	}
	
	/**
	 * 将字符串形式的时间 转换成 JDK的Date对象
	 * @param dateStr	字符串时间
	 * @return java.util.Date
	 */
	public static Date toDate(String dateStr) {
		DateTime dt = new DateTime(dateStr);
		return dt.toDate();
	}
	
	/**
	 * 返回yyyy-MM-dd HH:mm:ss格式的时间信息
	 * @param date JDK的Date对象。
	 * @return
	 */
	public static String dateStr(java.util.Date date) {
		DateTime dt = new DateTime(date);
		return dt.toString("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 返回yyyy-MM-dd格式的日期信息
	 * @param date JDK的Date对象。
	 * @return
	 */
	public static String yyyyMMdd(java.util.Date date) {
		DateTime dt = new DateTime(date);
		return dt.toString("yyyy-MM-dd");
	}
	
	/**
	 * 返回指定格式的时间信息
	 * @param date JDK的Date对象。
	 * @param pattern	时间格式。
	 * @return
	 */
	public static String dateStr(java.util.Date date, String pattern) {
		DateTime dt = new DateTime(date);
		return dt.toString(pattern);
	}

}
