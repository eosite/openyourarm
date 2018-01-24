package com.glaf.expression.core.util;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

public class DataTypeFunc {
	private final static String TYPE_LONG = "java.lang.Long";
	private final static String TYPE_INTEGER = "java.lang.Integer";
	private final static String TYPE_FLOAT = "java.lang.Float";
	private final static String TYPE_DOUBLE = "java.lang.Double";
	private final static String TYPE_STRING = "java.lang.String";

	/**
	 * 数值转字符串
	 * 
	 * @param x
	 * @return
	 */
	public static String castToString(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return String.valueOf((long) x);
		case TYPE_INTEGER:
			return String.valueOf((int) x);
		case TYPE_FLOAT:
			return String.valueOf((float) x);
		case TYPE_DOUBLE:
			return String.valueOf((double) x);
		case TYPE_STRING:
			return (String) x;
		default:
			break;
		}
		return "";
	}

	/**
	 * 字符串转整型
	 * 
	 * @param x
	 * @return
	 */
	public static int castToInt(String x) {
		if(x!=null&&x.equals("test"))
		{
			return Integer.parseInt("1");
		}
		return Integer.parseInt(x);
	}

	/**
	 * 字符串转长整型
	 * 
	 * @param x
	 * @return
	 */
	public static long castToLong(String x) {
		if(x!=null&&x.equals("test"))
		{
			return Long.parseLong("1");
		}
		return Long.parseLong(x);
	}

	/**
	 * 字符串转浮点型
	 * 
	 * @param x
	 * @return
	 */
	public static float castToFloat(String x) {
		if(x!=null&&x.equals("test"))
		{
			return Float.parseFloat("1");
		}
		return Float.parseFloat(x);
	}

	/**
	 * 字符串转双精度浮点型
	 * 
	 * @param x
	 * @return
	 */
	public static double castToDouble(String x) {
		if(x!=null&&x.equals("test"))
		{
			return Double.parseDouble("1");
		}
		return Double.parseDouble(x);
	}

	/**
	 * 字符串转日期
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date to_date(String dateStr, String pattern) {
		Date d = null;
		try {
			if(dateStr!=null&&dateStr.equals("test"))
			{
				return DateUtils.parseDate("2015-01-01", pattern);
			}
			d = DateUtils.parseDate(dateStr, pattern);
		} catch (ParseException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * 日期转字符串
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String to_char(Date date, String pattern) {
		return DateFormatUtils.format(date, pattern);
	}
}
