package com.pig8.api.platform.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author navy
 *
 */
public class BeanUtils {
	private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

	/**
	 * bean字段转化为String并用逗号分隔
	 * 
	 * @param bean
	 * @return
	 */
	public static String beanToString(Object bean) {
		StringBuffer buf = new StringBuffer();
		Class<? extends Object> cls = bean.getClass();
		Field[] declaredFields = cls.getDeclaredFields();
		String fieldName = null;
		String getMethodName = null;
		Object value = null;
		Method method = null;
		for (Field field : declaredFields) {
			fieldName = field.getName();
			getMethodName = "get" + Character.toUpperCase(fieldName.charAt(0))
					+ fieldName.substring(1);
			try {
				method = cls.getMethod(getMethodName);
				value = method.invoke(bean);
			} catch (Exception e) {
				logger.error("", e);
			}
			if (value instanceof Date) {
				buf.append(fieldName
						+ "="
						+ DateUtils.formatDateToString((Date) value,
								"yyyy-MM-dd HH:mm:ss") + ",");
			} else {
				buf.append(fieldName + "=" + value + ",");
			}
		}
		buf.deleteCharAt(buf.length() - 1);
		buf.append("\r\n");
		return buf.toString();
	}
}
