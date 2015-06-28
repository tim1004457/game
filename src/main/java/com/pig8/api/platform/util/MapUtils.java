package com.pig8.api.platform.util;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @author navy
 *
 */
public class MapUtils {
	/**
	 * Map字段转化为String并用逗号分隔
	 * 
	 * @param bean
	 * @return
	 */
	public static String mapToString(Map<String, Object> map) {
		if (null != map) {
			StringBuffer buf = new StringBuffer();
			Set<String> keySet = map.keySet();
			for (String key : keySet) {
				Object value = map.get(key);
				if (value instanceof Date) {
					buf.append(key
							+ "="
							+ DateUtils.formatDateToString((Date) value,
									"yyyy-MM-dd HH:mm:ss") + ",");
				} else {
					buf.append(key + "=" + value + ",");
				}
			}
			buf.deleteCharAt(buf.length() - 1);
			buf.append("\r\n");
			return buf.toString();
		}
		return null;
	}
	
}
