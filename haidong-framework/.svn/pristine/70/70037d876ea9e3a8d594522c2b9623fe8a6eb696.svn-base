package com.huixin.framework.utils.business;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.huixin.framework.utils.FileUtil;
import com.huixin.framework.utils.Logger;


public class DateUtils {
	
	protected static Logger logger = Logger.getLogger("com.huixin.framework.utils.business.DateUtils");

	public static String getDate(String d) {
		String result = "";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date = sdf.parse(d);
			String week = getWeekOfDate(date);
			result = formatter.format(date);
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			date = sdf.parse(d);
			formatter = new SimpleDateFormat("HH:mm分");
			result = result + "("+week+") " + formatter.format(date);
		} catch (ParseException e) {
			logger.error("日期报错" + d, e);
		}
		return result;
	}
	
	public static String getStartDate(String d) {
		String result = "";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(d);
			String week = getWeekOfDate(date);
			result = formatter.format(date);
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.parse(d);
			//formatter = new SimpleDateFormat("HH:mm分");
			result = result + "("+week+") ";
		} catch (ParseException e) {
			logger.error("日期报错" + d, e);
		}
		return result;
	}

	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	public static void main(String[] args) {
		String f = "E:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp4/wtpwebapps/haidong-api/WEB-INF/classes/../../wxt/20160715/jacky";
//		File filedir = new File(f);
//		if (!filedir.exists()) {
//			filedir.mkdirs();
//		} else {
//			System.out.println(filedir.delete());
//			System.out.println(filedir.mkdirs());
//		}
		System.out.println(FileUtil.DeleteFolder(f));
//		System.out.println(getDate("2016-07-16 11:56"));
	}
}
