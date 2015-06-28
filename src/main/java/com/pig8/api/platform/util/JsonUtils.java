package com.pig8.api.platform.util;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * @author navy
 * 
 */

public class JsonUtils {
	
	private final static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	
	public static DecimalFormat DF_UNIT = new DecimalFormat("0.##");
	public static DecimalFormat DF_POINT = new DecimalFormat("0.00");
	
	public static void main(String[] args) {
		System.out.println(json2Object("{'ss':153345.000}", Map.class).get("ss").getClass());
		System.out.println((Long)json2Object("{'ss':1215212521212}", Map.class).get("ss"));
		System.out.println(json2Object("{'ss':2222}", Map.class).get("ss").getClass());
		System.out.println(json2Object("{'ss':2222}", Map.class).get("ss").getClass());
		System.out.println(json2Object("{'ss':345.55}", Map.class).get("ss").getClass());
		System.out.println(Integer.MAX_VALUE);
	}

	/**
	 * json字符串转java对象(jackson方式)
	 * @param <T>
	 * @param jsonStr
	 * @param c
	 * @return
	 */
	public static <T> T json2Object(String jsonStr, Class<T> c){
	    ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);//解析单引号支持
		try {
			return mapper.readValue(jsonStr, c);
		} catch (Exception e) {
			logger.error(jsonStr, e);
		}
		return null;
	}
	
	/**
	 * json字符串转java对象(jackson方式)
	 * @param <T>
	 * @param jsonStr
	 * @param type
	 * @return
	 */
	public static <T> T json2Object(String jsonStr, TypeReference<T> type){
	    ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);//解析单引号支持
		try {
			return mapper.readValue(jsonStr, type);
		} catch (Exception e) {
			logger.error(jsonStr, e);
		}
		return null;
	}
	
	/**
	 * java对象转json字符串(jackson方式)
	 * @param o
	 * @return
	 */
	public static String object2Json(Object o){
		try {
			return new ObjectMapper().writeValueAsString(o);
		} catch (Exception e) {
			logger.error(o.toString(), e);
		}
		return null;
	}
	
	/**
	 * Map转java对象(jackson方式)
	 * @param <T>
	 * @param o
	 * @param c
	 * @return
	 */
	public static <T> T map2Object(Map<?, ?> o, Class<T> c){
		try {
			return json2Object(object2Json(o), c);
		} catch (Exception e) {
			logger.error(o.toString(), e);
		}
		return null;
	}
	public static void outputJson(Object object, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		String json = object2Json(object);
		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.write(json);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}