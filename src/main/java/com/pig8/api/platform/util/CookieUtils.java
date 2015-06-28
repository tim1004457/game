/**
 * 
 */
package com.pig8.api.platform.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author navy
 *
 */
public class CookieUtils {
	
	private static Logger logger = LoggerFactory.getLogger(CookieUtils.class);
	
	public static String get(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			logger.info("cookieName=" + cookie.getName() + ", cookieValue=" + cookie.getValue() + ", cookieDomain=" + cookie.getDomain());
			if (cookie.getName().equals(cookieName)) {
				return cookie.getValue();
			}
		}
		
		return null;
	}

}
