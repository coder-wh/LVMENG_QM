package com.lvmeng.qm.base.commons.util;

/**
 * 将毫秒格式化
 * 
 * @author Peter
 *
 */
public class FormatDuration {
	private FormatDuration() {
		throw new Error("you can't instantiate this class: FormatDuration ");
	}

	/**
	 * 格式化 当前时间小于1分钟，则格式化到秒； 当前时间小于1小时，则格式化到分钟 ；其他情况，则格式化到小时
	 * 
	 * @param t
	 *            毫秒
	 * @return
	 * @author Peter
	 */
	public static String format(int t) {
		if (t < 60000) {
			return (t % 60000) / 1000 + "秒";
		} else if ((t >= 60000) && (t < 3600000)) {
			return getString((t % 3600000) / 60000) + ":" + getString((t % 60000) / 1000);
		} else {
			return getString(t / 3600000) + ":" + getString((t % 3600000) / 60000) + ":"
					+ getString((t % 60000) / 1000);
		}
	}
	/**
	 * 格式化 当前时间小于1分钟，则格式化到秒； 当前时间小于1小时，则格式化到分钟 ；其他情况，则格式化到小时
	 * 
	 * @param t
	 *            秒
	 * @return
	 * @author Peter
	 */
	public static String formatToSec(int time) {
		String timeStr = null;
		int hour = 0;
		int minute = 0;
		int second = 0;
		if (time <= 0)
			return "00:00";
		else {
			minute = time / 60;
			if (minute < 60) {
				second = time % 60;
				timeStr = unitFormat(minute) + ":" + unitFormat(second);
			} else {
				hour = minute / 60;
				if (hour > 99)
					return "99:59:59";
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
			}
		}
		return timeStr;
	}

	private static String unitFormat(int i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Integer.toString(i);
		else
			retStr = "" + i;
		return retStr;
	}
	/**
	 * 按指定类型转换，默认转成小时
	 * 
	 * @param t
	 * @param type
	 *            type为"day"时，格式化到天；为"minute"时，格式化到分钟；
	 * @return
	 * @author Peter
	 */
	public static String formatDuration(int t, String type) {
		String result = null;
		if ("day".equalsIgnoreCase(type)) {
			result = formatToDay(t);
		} else if ("minute".equalsIgnoreCase(type)) {
			result = formatToMinute(t);
		} else {// 默认小时开头
			result = formatToHour(t);
		}
		return result;
	}

	/**
	 * 格式化到天
	 * 
	 * @param t
	 *            毫秒
	 * @return 返回格式："dd:HH:mm:ss"
	 * @author Peter
	 */
	public static String formatToDay(int t) {
		int days = t / 86400000;
		int hours = (t % 86400000) / 3600000;
		int minutes = (t % 3600000) / 60000;
		int seconds = (t % 60000) / 1000;
		if (days > 0) {
			return days + "：" + hours + ":" + minutes + ":" + seconds;
		} else {
			if (hours > 0) {
				return hours + ":" + minutes + ":" + seconds;
			} else {
				if (minutes > 0) {
					return minutes + ":" + seconds;
				} else {
					return String.valueOf(seconds);
				}
			}
		}

	}

	/**
	 * 格式化成小时
	 * 
	 * @param t
	 *            毫秒，long型
	 * @return 返回格式："HH:mm:ss"
	 * @author Peter
	 */
	public static String formatToHour(long t) {
		return getString(t / 3600000L) + ":" + getString((t % 3600000L) / 60000L) + ":"
				+ getString((t % 60000L) / 1000L);
	}

	/**
	 * 格式化成小时
	 * 
	 * @param t
	 *            毫秒，int型
	 * @return 返回格式："HH:mm:ss"
	 * @author Peter
	 */
	public static String formatToHour(int t) {
		return getString(t / 3600000) + ":" + getString((t % 3600000) / 60000) + ":" + getString((t % 60000) / 1000);
	}

	/**
	 * 格式化成分钟
	 * 
	 * @param t
	 *            毫秒，int型
	 * @return 返回格式："mm:ss"
	 * @author Peter
	 */
	public static String formatToMinute(int t) {
		int minutes = t / 60000;
		int seconds = (t % 60000) / 1000;

		if (minutes > 0) {
			return minutes + ":" + seconds;
		} else {
			return String.valueOf(seconds);
		}

	}

	// 处理long型数据
	private static String getString(long t) {
		String m = "";
		if (t > 0) {
			if (t < 10) {
				m = "0" + t;
			} else {
				m = t + "";
			}
		} else {
			m = "00";
		}
		return m;
	}

	// 处理int型数据
	private static String getString(int t) {
		String m = "";
		if (t > 0) {
			if (t < 10) {
				m = "0" + t;
			} else {
				m = t + "";
			}
		} else {
			m = "00";
		}
		return m;
	}

}
