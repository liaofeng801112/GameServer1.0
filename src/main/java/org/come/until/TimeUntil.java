package org.come.until;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author Administrator
 * 
 */
public class TimeUntil {
	/**获取当前时间精确到时分秒*/
	public static String getPastDate() {
		Date t = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(t);
	}
	/**获取时间参数*/
	public static String getPastDate(long time) {
		Date t = new Date(time);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(t);
	}
}
