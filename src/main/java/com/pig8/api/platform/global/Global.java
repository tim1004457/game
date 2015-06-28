package com.pig8.api.platform.global;

import com.pig8.api.platform.util.LogUtils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author navy
 *
 */
public class Global {
	 /**
	  *输入文本中直接禁止的关键字
	  */
	public static Map FORBID_KEYS_MAP   = new HashMap();
    
	/**
	 * 获取请求对象
	 * @return
	 */
	public static HttpServletRequest getRequest(){
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return (HttpServletRequest) attr.getRequest();
	}
	
	/**
	 * 获取访问路径
	 * @return
	 */
	public static String getFullPath(){
		try {
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpServletRequest request =  attr.getRequest();
			String rootPath = request.getContextPath();
			String fullPath = request.getScheme() + "://"
						+ request.getServerName() + ":" + request.getServerPort()
						+ rootPath + "/";
			
			return fullPath;
		} catch (Exception e) {
			LogUtils.error(e);
			return "";
		}
	}
}
