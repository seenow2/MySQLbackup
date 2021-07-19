package com.seenow.dbbackup.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	/**
	 * 时间格式 : yyyy-MM-dd
	 */
	public final static String SHORT_DATE_STYLE = "yyyy-MM-dd";
	/**
	 * 时间格式 : yyyy-MM-dd HH:mm:ss
	 */
	public final static String LONG_DATE_STYLE = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 将字符串转换为时间
	 * @param s
	 * @return Date
	 */
	public static Date parseDate(String s) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date = null;
		try {
			date = df.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/*
	 * 将字符串转换为时间2
	 * @param s
	 * @return Date
	 */
	public static Date parseDate2(String s) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
		Date date = null;
		try {
			date = df.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 将时间转换为 yyyy-MM-ddTHH:mm:ss 格式
	 * @param dt
	 * @return String
	 */
	public static String parseStr(Date dt) {
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		String a1 = df1.format(dt);
		SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
		String a2 = df2.format(dt);
		return a1 + "T" + a2;
	}

	/**
	 * 将时间转换为 yyyy-MM-dd HH:mm:ss 格式
	 * @param dt
	 * @return String
	 */
	public static String parseStr2(Date dt) {
		SimpleDateFormat df3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String a1 = df3.format(dt);
		return a1;
	}

	/**
	 * 将时间转换为 yyyy-MM-dd格式
	 * @param dt
	 * @return String
	 * @throws ParseException
	 */
	public static String format(String datetime, String srcFormat, String destFormat) throws ParseException {
		SimpleDateFormat df3 = new SimpleDateFormat(srcFormat);
		Date date = df3.parse(datetime);
		return new SimpleDateFormat(destFormat).format(date);
	}

	/**
	 * 将时间转换为 yyyy-MM-dd格式
	 * @param dt
	 * @return String
	 * @throws ParseException
	 */
	public static String formatUtc(String datetime, String srcFormat, String destFormat) throws ParseException {
		SimpleDateFormat df3 = new SimpleDateFormat(srcFormat);
		Date date = df3.parse(datetime);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) - 8);
		return new SimpleDateFormat(destFormat).format(c.getTime());
	}

	/**
	 * 根据指定的格式获取当前时间字符串
	 * @param dateFormat
	 * @return String
	 */
	public static String parseCurrentDate(String dateFormat) {
		Date currentDate  = new Date();
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);
		String currentDateStr = df.format(currentDate);
		return currentDateStr;
	}

	/**
	 * 转换成UTC时间表，格式: yyyy-MM-dd'T'HH:mm:ss
	 * @param dateTime 北京时,格式yyyy-MM-dd'T'HH:mm:ss
	 * @return
	 */
	public static String getUtcDateTime(String dateTime) {
		if ("0001-01-01T00:00:00".equals(dateTime))
		{
			return dateTime;
		}
		Date date = parseDate(dateTime);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) - 8);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return df.format(c.getTime());
	}

	/**
	 * 转换成UTC时间表，格式: yyyy-MM-dd'T'HH:mm:ss
	 * @param dateTime 北京时,格式yyyy-MM-dd'T'HH:mm:ss
	 * @return
	 */
	public static String getSystemDate(String format) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(c.getTime());
	}

	/**
	 * 生成规范的日志时间，格式: yyyy-MM-dd HH:mm:ss
	 * @param
	 * @return String
	 */
	public static String getLogmDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		return df.format(new Date());
	}

	public static Date parseDate(String datetime, String format)
	{
		if (StringUtils.isEmpty(datetime)){
			return null;
		}

		datetime = datetime.replace("T", " ");
		SimpleDateFormat df = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = df.parse(datetime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 转换成UTC时间表，格式: yyyy-MM-dd'T'HH:mm:ss
	 * @param dateTime 北京时,格式yyyy-MM-dd'T'HH:mm:ss
	 * @return
	 */
	public static String getAddedSystemDate(String format, int addDays) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + addDays);
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(c.getTime());
	}

	public static Date getAddedHours(int hours)
	{
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) + hours);
		return c.getTime();
	}

	public static Timestamp getCurrent()
	{
		return new Timestamp(System.currentTimeMillis());
	}
}
