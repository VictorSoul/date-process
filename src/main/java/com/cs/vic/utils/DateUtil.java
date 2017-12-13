package com.cs.vic.utils;

import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import com.cs.vic.entity.DailyData;
import com.google.gson.Gson;

@SuppressWarnings({ "deprecation", "unchecked" })
public class DateUtil {
	private static HashMap<Date, Boolean> workingDays = new HashMap<Date, Boolean>();
	private static ZoneId ZONEID = ZoneId.systemDefault();
	public static DateFormat hm = new SimpleDateFormat("kk:mm");
	public static DateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	public static DateFormat ym = new SimpleDateFormat("yyyy-MM");
	private static Date noonstart;
	private static Date noonend;
	private static Date five;
	private static Date seven;
	private static Date eleven;
	static {
		try {
			noonstart = hm.parse("12:00");
			noonend = hm.parse("13:00");
			five = hm.parse("17:30");
			seven = hm.parse("19:30");
			eleven = hm.parse("23:30");
			// init();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static boolean isWorkingDay(Date date) {
		Date dt = new Date(date.getYear(), date.getMonth(), date.getDate());
		if (workingDays.get(dt) == null) {
			int currentDay = dt.getDay();
			boolean weekend = (currentDay == 0 || currentDay == 6);
			workingDays.put(dt, !weekend);
		}
		return workingDays.get(dt);
	}

	public static void init(String path) throws ParseException {
		if (path.isEmpty()) {
			return;
		}
		HashMap<String, Boolean> workingday = new HashMap<String, Boolean>();
		workingday = new Gson().fromJson(JsonUtil.readJson(path), workingday.getClass());
		for (Entry<String, Boolean> day : workingday.entrySet()) {
			workingDays.put(ymd.parse(day.getKey()), day.getValue());
		}
	}

	public static double calWorkTime(DailyData dailyData) {
		Date d1 = getDate(dailyData.getStart());
		Date d2 = getDate(dailyData.getEnd());
		long min = getmin(d1, d2);
		dailyData.setWorkstring((min / 60L) + "–° ±" + (min % 60L) + "∑÷÷”");
		return Float.valueOf((float) (min * 1000L / 60L)).floatValue() / 1000.0F;
	}

	public static double calOverTime(DailyData dailyData) {
		if (dailyData.isWorkingday()) {
			Date after = getDate(dailyData.getEnd());
			long min = getmin(five, after);
			return (int) ((min + 15) / 60);
		} else {
			return dailyData.getWorklength();
		}

	}

	public static boolean afterSeven(Date date) {
		Date date2 = getDate(date);
		return date2.after(seven);
	}

	public static boolean afterEleven(Date date) {
		Date date2 = getDate(date);
		return date2.after(eleven);
	}

	private static long getmin(Date d1, Date d2) {

		if (d1.before(noonstart)) {
			if (d2.before(noonstart))
				return ((d2.getTime() - d1.getTime()) / 60000L);
			if ((d2.after(noonstart)) && (d2.before(noonend)))
				return ((noonstart.getTime() - d1.getTime()) / 60000L);

			return ((d2.getTime() - d1.getTime()) / 60000L - 60L);
		}
		if ((d1.after(noonstart)) && (d1.before(noonend)))
			return ((d2.getTime() - noonend.getTime()) / 60000L);

		return ((d2.getTime() - d1.getTime()) / 60000L);
	}

	private static Date getDate(Date date) {
		Date dt = new Date(70, 00, 01);
		dt.setHours(date.getHours());
		dt.setMinutes(date.getMinutes());
		return dt;
	}

	public static Date addOneMou(Date date) {
		LocalDate localdate = LocalDateTime.ofInstant(date.toInstant(), ZONEID).toLocalDate().plusMonths(1);
		return Date.from(localdate.atTime(0, 0, 0, 0).atZone(ZONEID).toInstant());
	}

}
