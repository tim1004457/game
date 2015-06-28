/**
 * 
 */
package com.pig8.api.platform.global;

import java.util.concurrent.TimeUnit;

/**
 * @author navy
 *
 */
public class Constants {
	
	public static final String CACHE_PREFIX_LOGIN_USER = "login:user:";
	
	public static final long DEFAULT_CACHE_TIMEOUT = 30;//默认缓存时间
	
	public static final TimeUnit DEFAULT_CACHE_TIME_UNIT = TimeUnit.MINUTES;//默认缓存时间单位（分钟）
	
	public static final long SESSION_TIMEOUT = 30;
	
	public static final int DEFAULT_PAGE_SIZE = 15;
	
	//以下是微信相关的常量
	public static final String REDIRECT_URI="http://api.8pig.com/8pig-api/wechat/callback";
	
	public static final String WECHAT_WEB_APPID = "wx8de51bd0d62d42da";
	public static final String WECHAT_WEB_APP_SECRET = "2164eb86b482e6796fa119cfa10d4de5";
	
	public static final String WECHAT_IOS_APPID = "wx7dae8480855816b7";
	public static final String WECHAT_IOS_APP_SECRET = "3dde8341336358ccfd5ab256435528ce";
	
	public static final String WECHAT_CONNECT_URL = "https://open.weixin.qq.com/connect/qrconnect";
	public static final String WECHAT_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";
	public static final String WECHAT_AUTH_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
	
	//图片服务器域名
	public static final String IMG_DOMAIN = "http://test.8pig.com/";
	
	
	
}
