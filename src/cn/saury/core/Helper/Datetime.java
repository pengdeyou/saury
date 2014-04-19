package cn.saury.core.Helper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期帮助类
 * 
 */
public class Datetime {

	/** 年月日模式字符串 */
	public static String DATE_FORMAT = "yyyy-MM-dd";

	/** 时分秒模式字符串 */
	public static String TIME_FORMAT = "HH:mm:ss";

	/** 年月日时分秒模式字符串 */
	public static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static String PATTERN_YEAR = "yyyy";
	public static String PATTERN_MONTH = "MM";
	public static String PATTERN_DATE = "dd";
	public static String PATTERN_HOUR = "hh";
	public static String PATTERN_HOUR24 = "HH";
	public static String PATTERN_MINUTE = "mm";
	public static String PATTERN_SECOND = "ss";
	public static String PATTERN_MILLISECOND = "SSS";

	/**
	 * 获取纳秒级时间
	 * 
	 * @return
	 */
	public static long getNanoTime() {
		return System.nanoTime();
	}

	/**
	 * 获取当前时间.
	 * 
	 * @return 当前时间
	 */
	public static Date now() {
		return new Date();
	}

	/**
	 * 当前时间
	 * 
	 * @param pattern
	 * @return 指定格式的当前时间
	 */
	public static String nowString(String pattern) {
		return format(now(), pattern);
	}

	public static Long getUnixTime(){
		Long time=Calendar.getInstance().getTimeInMillis();
		Long getTime= time/ 1000;
		return getTime;
	}
	/**
	 * 以yyyy-MM-dd形式返回当前时间
	 * 
	 * @return
	 */
	public static String nowString() {
		return format(now(), DATETIME_FORMAT);
	}

	/**
	 * 获取给定日期对象的年
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 获取给定日期对象的月
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取给定日期对象的天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DATE);
	}

	/**
	 * 获取给定日期对象的小时
	 * 
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR);
	}

	/**
	 * 获取给定日期对象的分
	 * 
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE);
	}

	/**
	 * 获取给定日期对象的秒
	 * 
	 * @param date
	 * @return
	 */
	public static int getSecond(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.SECOND);
	}

	/**
	 * 将某个日期增加指定年数
	 * 
	 * @param date
	 * @param ammount
	 * @return
	 */
	public static Date addYear(Date date, int ammount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, ammount);
		return c.getTime();
	}

	/**
	 * 将某个日期增加指定月数
	 * 
	 * @param date
	 * @param ammount
	 * @return
	 */
	public static Date addMonth(Date date, int ammount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, ammount);
		return c.getTime();
	}

	/**
	 * 将某个日期增加指定天数
	 * 
	 * @param date
	 * @param ammount
	 * @return
	 */
	public static Date addDay(Date date, int ammount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, ammount);
		return c.getTime();
	}

	/**
	 * 将某个日期增加指定小时
	 * 
	 * @param date
	 * @param ammount
	 * @return
	 */
	public static Date addHour(Date date, int ammount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, ammount);
		return c.getTime();
	}

	/**
	 * 将某个日期增加指定分钟
	 * 
	 * @param date
	 * @param ammount
	 * @return
	 */
	public static Date addMinute(Date date, int ammount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, ammount);
		return c.getTime();
	}

	/**
	 * 返回给定的beforeDate比afterDate早的年数.
	 * 
	 * @param beforeDate
	 * @param afterDate
	 * @return
	 */
	public static int beforeYears(Date beforeDate, Date afterDate) {
		int before = getYear(beforeDate);
		int after = getYear(afterDate);
		return after - before;
	}

	/**
	 * 获取beforeDate和afterDate之间相差的年数,精确到天.
	 * 
	 * @param beforeDate
	 * @param afterDate
	 * @return
	 */
	public static int beforeYearsByDay(Date beforeDate, Date afterDate) {
		Date bDate = beforeDate;
		Date aDate = afterDate;
		boolean positive = true;
		if (beforeDate.after(afterDate)) {
			positive = false;
			bDate = afterDate;
			aDate = beforeDate;
		}
		int beforeYears = beforeYears(bDate, aDate);

		int bMonth = getMonth(bDate);
		int aMonth = getMonth(aDate);
		if (aMonth < bMonth) {
			beforeYears--;
		} else if (aMonth == bMonth) {
			int bDay = getDay(bDate);
			int aDay = getDay(aDate);
			if (aDay < bDay) {
				beforeYears--;
			}
		}

		if (positive) {
			return beforeYears;
		} else {
			return new BigDecimal(beforeYears).negate().intValue();
		}
	}

	/**
	 * 获取beforeDate和afterDate之间相差的年数,精确到月.
	 * 
	 * @param beforeDate
	 * @param afterDate
	 * @return
	 */
	public static int beforeYearsByMonth(Date beforeDate, Date afterDate) {
		Date bDate = beforeDate;
		Date aDate = afterDate;
		boolean positive = true;
		if (beforeDate.after(afterDate)) {
			positive = false;
			bDate = afterDate;
			aDate = beforeDate;
		}
		int beforeYears = beforeYears(bDate, aDate);

		int bMonth = getMonth(bDate);
		int aMonth = getMonth(aDate);
		if (aMonth < bMonth) {
			beforeYears--;
		}

		if (positive) {
			return beforeYears;
		} else {
			return new BigDecimal(beforeYears).negate().intValue();
		}
	}

	/**
	 * 返回给定的beforeDate比afterDate早的月数.
	 * 
	 * @param beforeDate
	 * @param afterDate
	 * @return
	 */
	public static int beforeMonths(Date beforeDate, Date afterDate) {
		Calendar beforeCalendar = Calendar.getInstance();
		beforeCalendar.setTime(beforeDate);
		beforeCalendar.set(Calendar.DATE, 1);
		beforeCalendar.set(Calendar.HOUR, 0);
		beforeCalendar.set(Calendar.SECOND, 0);
		beforeCalendar.set(Calendar.MINUTE, 0);
		Calendar afterCalendar = Calendar.getInstance();
		afterCalendar.setTime(afterDate);
		afterCalendar.set(Calendar.DATE, 1);
		afterCalendar.set(Calendar.HOUR, 0);
		afterCalendar.set(Calendar.SECOND, 0);
		afterCalendar.set(Calendar.MINUTE, 0);

		boolean positive = true;
		if (beforeDate.after(afterDate))
			positive = false;
		int beforeMonths = 0;
		while (true) {
			boolean yearEqual = beforeCalendar.get(Calendar.YEAR) == afterCalendar.get(Calendar.YEAR);
			boolean monthEqual = beforeCalendar.get(Calendar.MONTH) == afterCalendar.get(Calendar.MONTH);
			if (yearEqual && monthEqual) {
				break;
			} else {
				if (positive) {
					beforeMonths++;
					beforeCalendar.add(Calendar.MONTH, 1);
				} else {
					beforeMonths--;
					beforeCalendar.add(Calendar.MONTH, -1);
				}
			}
		}
		return beforeMonths;
	}

	/**
	 * 获取beforeDate和afterDate之间相差的完整月数,精确到天.
	 * 
	 * @param beforeDate
	 * @param afterDate
	 * @return
	 */
	public static int beforeRoundMonths(Date beforeDate, Date afterDate) {
		Date bDate = beforeDate;
		Date aDate = afterDate;
		boolean positive = true;
		if (beforeDate.after(afterDate)) {
			positive = false;
			bDate = afterDate;
			aDate = beforeDate;
		}
		int beforeMonths = beforeMonths(bDate, aDate);

		int bDay = getDay(bDate);
		int aDay = getDay(aDate);
		if (aDay < bDay) {
			beforeMonths--;
		}

		if (positive) {
			return beforeMonths;
		} else {
			return new BigDecimal(beforeMonths).negate().intValue();
		}
	}

	/**
	 * 返回给定的beforeDate比afterDate早的天数.
	 * 
	 * @param beforeDate
	 * @param afterDate
	 * @return
	 */
	public static int beforeDays(Date beforeDate, Date afterDate) {
		Calendar beforeCalendar = Calendar.getInstance();
		beforeCalendar.setTime(beforeDate);
		beforeCalendar.set(Calendar.HOUR, 0);
		beforeCalendar.set(Calendar.SECOND, 0);
		beforeCalendar.set(Calendar.MINUTE, 0);
		Calendar afterCalendar = Calendar.getInstance();
		afterCalendar.setTime(afterDate);
		afterCalendar.set(Calendar.HOUR, 0);
		afterCalendar.set(Calendar.SECOND, 0);
		afterCalendar.set(Calendar.MINUTE, 0);
		boolean positive = true;
		if (beforeDate.after(afterDate))
			positive = false;
		int beforeDays = 0;
		while (true) {
			boolean yearEqual = beforeCalendar.get(Calendar.YEAR) == afterCalendar.get(Calendar.YEAR);
			boolean monthEqual = beforeCalendar.get(Calendar.MONTH) == afterCalendar.get(Calendar.MONTH);
			boolean dayEqual = beforeCalendar.get(Calendar.DATE) == afterCalendar.get(Calendar.DATE);
			if (yearEqual && monthEqual && dayEqual) {
				break;
			} else {
				if (positive) {
					beforeDays++;
					beforeCalendar.add(Calendar.DATE, 1);
				} else {
					beforeDays--;
					beforeCalendar.add(Calendar.DATE, -1);
				}
			}
		}
		return beforeDays;
	}

	/**
	 * 根据传入的年、月、日构造日期对象
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public static Date getDate(int year, int month, int date) {
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, date);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 按yyyy-MM-dd格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return format(date, DATE_FORMAT);
	}

	/**
	 * 按yyyy-MM-dd HH:mm:ss格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date) {
		return format(date, DATETIME_FORMAT);
	}

	/**
	 * 按HH:mm:ss格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String formatTime(Date date) {
		return format(date, TIME_FORMAT);
	}

	/**
	 * 通过毫秒数获得日期对象
	 * 
	 * @param millis
	 * @return
	 */
	public static Date parse(long millis) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(millis);
		return c.getTime();
	}

	/**
	 * 将传入的字符串转换成日期对象
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date parse(String dateStr, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 按yyyy-MM-dd HH:mm:ss将传入的字符串转换成日期对象
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parseDateTime(String dateStr) {
		return parse(dateStr, DATETIME_FORMAT);
	}

	/**
	 * 按yyyy-MM-dd将传入的字符串转换成日期对象
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parseDate(String dateStr) {
		return parse(dateStr, DATETIME_FORMAT);
	}

	/**
	 * 按HH:mm:ss将传入的字符串转换成日期对象
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parseTime(String dateStr) {
		return parse(dateStr, DATETIME_FORMAT);
	}

	/**
	 * 将对象转换为日期对象
	 * 
	 * @param obj
	 *            支持字符串(yyyy-MM-dd HH:mm:ss,yyyy-MM-dd)、long
	 * @return
	 */
	/*
	public static Date parse(Object obj) throws IllegalArgumentException {

		if (obj == null) {
			return null;
		}

		Date date = null;
		if (obj instanceof Date) {
			date = (Date) obj;

		} else if (obj instanceof String) {
			date = parseDateTime((String) obj);
			if (date == null) {
				date = parseDate((String) obj);
			}
			if (date == null) {
				Long millis = NumberUtils.convert(obj, Long.class);
				date = parse(millis);
			}

		} else if (obj instanceof Number) {
			Long millis = NumberUtils.convert(obj, Long.class);
			date = parse(millis);

		} else if (obj instanceof java.sql.Date) {
			date = new Date(((java.sql.Date) obj).getTime());

		}
		if (date == null) {
			throw new IllegalArgumentException("Can not convert " + String.valueOf(obj) + " to Date");
		}
		return date;
	}
*/
	/**
	 * 转换为UTC时间
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String toUTC(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return sdf.format(date);
	}

	/**
	 * 按yyyy-MM-dd HH:mm:ss转换为UTC时间
	 * 
	 * @param date
	 * @return
	 */
	public static String toUTC(Date date) {
		return toUTC(date, DATETIME_FORMAT);
	}

}