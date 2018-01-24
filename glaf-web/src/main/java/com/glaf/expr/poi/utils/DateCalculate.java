package com.glaf.expr.poi.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期计算函数库
 * 
 * @author J
 *
 */
public class DateCalculate {
	/**
	 * 默认为周六和周日为休息日
	 */
	public static final String DEFAULT_WEEK_DAYS = "0000011";
	public static final String[] ONE_LEN_WEEK_DATS = { "0000011", "0000011", "1000001", "110000", "0110000", "0011000", "0001100", "0000110" };
	public static final String[] TWO_LEN_WEEK_DATS = { "0000011", "0000001", "1000000", "010000", "0010000", "0001000", "0000100", "0000010" };

	public static void main(String[] args) {
		try {
			int day = Calendar.SUNDAY;
			int ofsDay = (day + 5) % 7;
			System.out.println(ofsDay);
			System.out.println("星期" + "1234567".charAt(ofsDay));
			DateCalculate ct = new DateCalculate();
			/*
			 * DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); Calendar ca =
			 * Calendar.getInstance(); Date d = df.parse("2014-11-04");
			 * ca.setTime(d);// 设置当前时间
			 * 
			 * //ct.initHolidayList("2014-11-06");// 初始节假日
			 * //ct.initHolidayList("2014-11-07");// 初始节假日
			 * //ct.initHolidayList("2014-11-03");// 初始节假日
			 * 
			 * Calendar c = ct.addDateByWorkDay(ca, 5);
			 * System.out.println(df.format(c.getTime()));
			 */

			System.out.println("" + Math.ceil(12.1));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getClass());
			e.printStackTrace();
		}

	}

	private List<Calendar> holidayList = new ArrayList<Calendar>(); // 节假日列表

	/**
	 * 
	 * 计算相加day天，并且排除节假日和周末后的日期
	 * 
	 * @param calendar
	 *            当前的日期
	 * @param day
	 *            相加天数
	 * @return return Calendar 返回类型 返回相加day天，并且排除节假日和周末后的日期 throws date
	 *         2014-11-24 上午10:32:55
	 */
	public Calendar addDateByWorkDay(Calendar calendar, int day) {

		try {
			for (int i = 0; i < day; i++) {

				calendar.add(Calendar.DAY_OF_MONTH, 1);

				if (checkHoliday(calendar)) {
					i--;
				}
			}
			return calendar;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return calendar;
	}

	public String convertWeekDays(String weekdays) {
		if (weekdays == null) {
			weekdays = DEFAULT_WEEK_DAYS;
		} else if (weekdays.length() == 1) {
			weekdays = ONE_LEN_WEEK_DATS[Integer.parseInt(weekdays)];
		} else if (weekdays.length() == 2) {
			weekdays = TWO_LEN_WEEK_DATS[Integer.parseInt(weekdays) - 10];
		} else if (weekdays.length() != 7) {
			weekdays = DEFAULT_WEEK_DAYS;
		}
		return weekdays;
	}

	/**
	 * 开始结束日期排除节假日和
	 * 
	 * @param start
	 * @param end
	 * @param weekdays
	 * @param holidays
	 * @return
	 * @throws Exception
	 */
	public int calculateWorkDay(Date start, Date end, String weekdays, Date[] holidays) throws Exception {
		Calendar startDay = Calendar.getInstance();
		startDay.setTime(start);

		Calendar endDay = Calendar.getInstance();
		endDay.setTime(end);
		return calculateWorkDay(startDay, endDay, weekdays, holidays);
	}

	/**
	 * 开始结束日期排除节假日和
	 * 
	 * @param start
	 * @param end
	 * @param weekdays
	 * @param holidays
	 * @return
	 * @throws Exception
	 */
	public int calculateWorkDay(long start, long end, String weekdays, Date[] holidays) throws Exception {
		Calendar startDay = Calendar.getInstance();
		startDay.setTime(new Date(start));

		Calendar endDay = Calendar.getInstance();
		endDay.setTime(new Date(end));
		return calculateWorkDay(startDay, endDay, weekdays, holidays);
	}

	/**
	 * 开始结束日期排除节假日和
	 * 
	 * @param start
	 * @param end
	 * @param weekdays
	 * @param holidays
	 * @return
	 * @throws Exception
	 */
	public int calculateWorkDay(Calendar start, Calendar end, String weekdays, Date[] holidays) throws Exception {
		// 开始和结束日期相隔多少天
		// int difDay = (int)(Math.round(((double)(end.getTimeInMillis() -
		// start.getTimeInMillis()))/86400000) + 1);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		long startTime = df.parse(df.format(start.getTime())).getTime();
		long endTime = df.parse(df.format(end.getTime())).getTime();
		int difDay = (int) ((endTime - startTime) / (1000 * 60 * 60 * 24)) + 1;
		if (holidays != null) {
			// 注册假期
			initHolidayLists(holidays);
		}
		weekdays = convertWeekDays(weekdays);
		int difWorkDay = 0;
		if (difDay != 0) {
			Calendar calculateTime = difDay > 0 ? start : end;
			for (int i = 0; i < Math.abs(difDay); i++) {
				if (!checkHoliday(calculateTime, weekdays)) {
					difWorkDay++;
				}
				// 加一天
				calculateTime.add(Calendar.DAY_OF_MONTH, 1);
			}
			if (difDay < 0) {
				return -difWorkDay;
			}
		}
		return difWorkDay;
	}

	/**
	 * 验证是否节假日 是节假日返回true
	 * 
	 * @param calendar
	 * @return
	 * @throws Exception
	 */
	public boolean checkHoliday(Calendar calendar) throws Exception {
		return checkHoliday(calendar, DEFAULT_WEEK_DAYS);
	}

	/**
	 * 验证是否节假日 是节假日返回true
	 * 
	 * @param calendar
	 * @param weekdays
	 *            格式为 0000011
	 * @return
	 * @throws Exception
	 */
	public boolean checkHoliday(Calendar calendar, String weekdays) throws Exception {

		// 判断日期是否是休息日
		if (weekdays.charAt((calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7) == '1') {
			return true;
		}
		// 判断日期是否是节假日
		for (Calendar ca : holidayList) {
			if (ca.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) && ca.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)
					&& ca.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 初始化法定假期
	 * 
	 * @param dates
	 */
	public void initHolidayLists(Date[] dates) {
		for (Date d : dates) {
			initHolidayList(d);
		}

	}

	/**
	 * 初始化法定假期库
	 * 
	 * @param date
	 */
	public void initHolidayList(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		holidayList.add(calendar);
	}
}
