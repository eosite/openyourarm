package com.glaf.expression.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

public class DateFunc {

	public static final String YEAR_RETURN = "Y";

	public static final String MONTH_RETURN = "M";

	public static final String DAY_RETURN = "D";

	public static final String HOUR_RETURN = "H";

	public static final String MINUTE_RETURN = "MI";

	public static final String SECOND_RETURN = "S";

	/**
	 * 获取日期
	 * 
	 * @param d1
	 * @return
	 */
	public static String getDate(Date d1) {
		return DateFormatUtils.format(d1, "yyyy-MM-dd");
	}
	
	/**
	 * 
	 * @return
	 */
	public static Long getTimeStamp(Date d1){
		return d1.getTime();
	}
	
	/**
	 * 
	 * @return
	 */
	public static Long getNowTimeStamp(){
		return System.currentTimeMillis();
	}
	
	/**
	 * 获取时间
	 * 
	 * @param d1
	 * @return
	 */
	public static String getTime(Date d1) {
		return DateFormatUtils.format(d1, "HH:mm:ss");
	}

	/**
	 * 获取日期时间
	 * 
	 * @param d1
	 * @return
	 */
	public static String getDateTime(Date d1) {
		return DateFormatUtils.format(d1, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取年份
	 * 
	 * @param d1
	 * @return
	 */
	public static int getYear(Date d1) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		return c1.get(Calendar.YEAR);
	}

	/**
	 * 获取月份
	 * 
	 * @param d1
	 * @return
	 */
	public static int getMonth(Date d1) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		return c1.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取月份
	 * 
	 * @param d1
	 * @return
	 */
	public static int getMonthStr(String d1) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(com.glaf.core.util.DateUtils.toDate("2016-06-06"));
		return c1.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取几号
	 * 
	 * @param d1
	 * @return
	 */
	public static int getDayOfMonth(Date d1) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		return c1.get(Calendar.DAY_OF_MONTH) + 1;
	}

	/**
	 * 获取星期几
	 * 
	 * @param d1
	 * @return
	 */
	public static String getDayOfWeek(Date d1) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		int DAY_OF_WEEK = c1.get(Calendar.DAY_OF_WEEK);
		String DAY_OF_WEEK_NAME = "";
		switch (DAY_OF_WEEK) {
		case 1:
			DAY_OF_WEEK_NAME = "星期天";
			break;
		case 2:
			DAY_OF_WEEK_NAME = "星期一";
			break;
		case 3:
			DAY_OF_WEEK_NAME = "星期二";
			break;
		case 4:
			DAY_OF_WEEK_NAME = "星期三";
			break;
		case 5:
			DAY_OF_WEEK_NAME = "星期四";
			break;
		case 6:
			DAY_OF_WEEK_NAME = "星期五";
			break;
		case 7:
			DAY_OF_WEEK_NAME = "星期六";
			break;
		default:
			break;
		}
		return DAY_OF_WEEK_NAME;
	}

	/**
	 * 获取几点
	 * 
	 * @param d1
	 * @return
	 */
	public static int getHour(Date d1) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		return c1.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取几分
	 * 
	 * @param d1
	 * @return
	 */
	public static int getMinute(Date d1) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		return c1.get(Calendar.MINUTE);
	}

	/**
	 * 获取几秒
	 * 
	 * @param d1
	 * @return
	 */
	public static int getSecond(Date d1) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		return c1.get(Calendar.SECOND);
	}

	/**
	 * 获取d1时间毫秒数
	 * 
	 * @param d1
	 * @return
	 */
	public static long getMilliSecond(Date d1) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		return c1.getTimeInMillis();
	}

	/**
	 * 获取时间差 年、月、日、时、分、秒
	 * 
	 * @param d1
	 * @param d2
	 * @param diffType
	 * @return
	 */
	public static long getDiffDatetime(Date d1, Date d2, String diffType) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);
		switch (diffType) {
		case YEAR_RETURN:
			return getByField(c1, c2, Calendar.YEAR);
		case MONTH_RETURN:
			return getByField(c1, c2, Calendar.YEAR) * 12 + getByField(c1, c2, Calendar.MONTH);
		case DAY_RETURN:
			return getTime(d1, d2) / (24 * 60 * 60 * 1000);
		case HOUR_RETURN:
			return getTime(d1, d2) / (60 * 60 * 1000);
		case MINUTE_RETURN:
			return getTime(d1, d2) / (60 * 1000);
		case SECOND_RETURN:
			return getTime(d1, d2) / 1000;
		default:
			return 0;
		}
	}

	/**
	 * 获取时间差 年、月、日、时、分、秒
	 * 
	 * @param beginTime
	 * @param endTime
	 * @param formatPattern
	 * @param returnPattern
	 * @return
	 * @throws ParseException
	 */
	public static long getBetween(String beginTime, String endTime, String formatPattern, String returnPattern)
			throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern);
		Date beginDate = simpleDateFormat.parse(beginTime);
		Date endDate = simpleDateFormat.parse(endTime);
		Calendar beginCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		beginCalendar.setTime(beginDate);
		endCalendar.setTime(endDate);
		switch (returnPattern) {
		case YEAR_RETURN:
			return getByField(beginCalendar, endCalendar, Calendar.YEAR);
		case MONTH_RETURN:
			return getByField(beginCalendar, endCalendar, Calendar.YEAR) * 12
					+ getByField(beginCalendar, endCalendar, Calendar.MONTH);
		case DAY_RETURN:
			return getTime(beginDate, endDate) / (24 * 60 * 60 * 1000);
		case HOUR_RETURN:
			return getTime(beginDate, endDate) / (60 * 60 * 1000);
		case MINUTE_RETURN:
			return getTime(beginDate, endDate) / (60 * 1000);
		case SECOND_RETURN:
			return getTime(beginDate, endDate) / 1000;
		default:
			return 0;
		}
	}

	private static long getByField(Calendar beginCalendar, Calendar endCalendar, int calendarField) {
		return endCalendar.get(calendarField) - beginCalendar.get(calendarField);
	}

	private static long getTime(Date beginDate, Date endDate) {
		return endDate.getTime() - beginDate.getTime();
	}

	/**
	 * 格式化日期
	 * 
	 * @param d1
	 * @param pattern
	 * @return
	 */
	public static String formatDatetime(Object d1, String pattern) {
		String date = null;
		if (d1 != null) {
			if (d1 instanceof java.util.Date) {
				date = DateFormatUtils.format((Date) d1, pattern);
			} else {
				try {
					date = DateFormatUtils.format(//
							com.glaf.core.util.DateUtils.toDate(d1.toString()), pattern);
				} finally {
					
				}
			}
		}
		return date;
	}

	/**
	 * 获取指定格式化日期
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getFormatDatetime(String pattern) {
		return formatDatetime(new Date(), pattern);
	}

	/**
	 * 判断d1与d2日期是否相等
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean equalDate(Date d1, Date d2) {
		if (DateFormatUtils.format(d1, "yyyy-MM-dd").equals(DateFormatUtils.format(d2, "yyyy-MM-dd"))) {
			return true;
		}
		return false;
	}

	/**
	 * 判断d1与d2日期时间是否相等
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean equalDateTime(Date d1, Date d2) {
		if (DateFormatUtils.format(d1, "yyyy-MM-dd HH:mm:ss")
				.equals(DateFormatUtils.format(d2, "yyyy-MM-dd HH:mm:ss"))) {
			return true;
		}
		return false;
	}

	/**
	 * 获取系统时间
	 * 
	 * @return
	 */
	public static Date getSysDate() {
		return new Date();
	}

	/**
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addDays(Date date, int amount) {
		return DateUtils.addDays(date, amount);
	}
}
