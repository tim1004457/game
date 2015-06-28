package com.pig8.api.platform.util;

import java.util.Calendar;
import java.util.UUID;

/**
 * @author navy
 *
 */
public final class UUIDUtils {

	/**
	 * 产生一个全局唯一的序列标
	 * @return 年（2位）＋月（2位）＋日（2痊）＋时（2位）＋分（2位）＋秒（2位）＋毫秒（3位）＋UUID（随机15位）
	 * @since 0.1
	 */
	public static String getUUID()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(calendar.getTime());

		StringBuffer u1 = new StringBuffer(StringUtils.formatNumber(calendar.get(Calendar.YEAR), 2, '0'));
		u1.append(StringUtils.formatNumber(calendar.get(Calendar.MONTH) + 1, 2, '0'));
		u1.append(StringUtils.formatNumber(calendar.get(Calendar.DAY_OF_MONTH), 2, '0'));
		u1.append(StringUtils.formatNumber(calendar.get(Calendar.HOUR_OF_DAY), 2, '0'));
		u1.append(StringUtils.formatNumber(calendar.get(Calendar.MINUTE), 2, '0'));
		u1.append(StringUtils.formatNumber(calendar.get(Calendar.SECOND), 2, '0'));
		u1.append(StringUtils.formatNumber(calendar.get(Calendar.MILLISECOND), 3, '0'));

		String u2 = UUID.randomUUID().toString();
		u2 = u2.replaceAll("-", "");
		return u1.toString() + u2.substring(15);
	}
	
	public static String getGUID()
	{
	    String u2 = UUID.randomUUID().toString();
        u2 = u2.replaceAll("-", "");
        return u2.toLowerCase();
	}
	
	public static void main(String args[]){
		System.out.println(UUIDUtils.getGUID());
	}
}
