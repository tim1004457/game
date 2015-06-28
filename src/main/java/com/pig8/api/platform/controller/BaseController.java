package com.pig8.api.platform.controller;

import com.pig8.api.platform.cache.RedisCache;
import com.pig8.api.platform.global.Constants;
import com.pig8.api.platform.util.CookieUtils;
import com.pig8.api.platform.util.StringUtils;
import com.pig8.api.platform.util.UUIDUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author navy
 */
public class BaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    Map<String, Object> userMap = new HashMap<String, Object>();

    @Autowired
    private RedisCache redisCache;

    //request，response，session 对象
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    /**
     * 每个请求相应操作都会执行这个方法
     * ModelAttribute的作用：表示请求该类的每个Controller前都会首先执行它，
     * 也可以将一些准备数据的操作放置在该方法里面。
     * @param request
     * @param response
     */
    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    protected ModelAndView errorPage(String errorMsg) {
        return errorPage(errorMsg, null);
    }

    protected ModelAndView errorPage(String errorMsg, Exception e) {
        ModelAndView view = new ModelAndView("/showMessage");
        view.addObject("data", errorMsg);
        return view;
    }

    /**
     * 获取请求上下文中的真实IP地址
     */
    public String getRemoteAddr() {
        String ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }

        ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP
            int index = ip.indexOf(',');
            if (index>-1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }

        ip = request.getHeader("Proxy-Client-IP");
        if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }

        ip = request.getHeader("WL-Proxy-Client-IP");
        if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }

        ip = request.getHeader("HTTP_CLIENT_IP");
        if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }

        ip = request.getHeader("X-Cluster-Client-IP");
        if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * 检查userid和token是否为空
     * @param userId
     * @param token
     * @return
     */
    public boolean validateParams(Integer userId, String token) {
        if (userId == null || userId == 0 || StringUtils.isEmpty(token)) {
            return false;
        }
        return true;
    }

    /**
     * @return -1:token 不存在，即未登录，0：token已失效，1：token有效
     */
    public int checkToken() {
        String token = CookieUtils.get(request, "token");
        logger.info("token=" + token);
        if (StringUtils.isEmpty(token)) {
            return -1;
        } else {
            String cacheKey = Constants.CACHE_PREFIX_LOGIN_USER + token;
            Map<String, Object> userMap = redisCache.getHashMap(cacheKey);
            if (userMap != null && userMap.size()>0) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * @return -1:token 不存在，即未登录， 其他为用户ID
     */
    public int getUserIdFromCache() {
        String token = CookieUtils.get(request, "token");
        logger.info("token=" + token);
        if (StringUtils.isEmpty(token)) {
            return -1;
        } else {
            String cacheKey = Constants.CACHE_PREFIX_LOGIN_USER + token;
            Map<String, Object> userMap = redisCache.getHashMap(cacheKey);
            if (userMap != null && userMap.size()>0) {
                return ((Integer) userMap.get("id")).intValue();
            } else {
                return -1;
            }
        }
    }

    public void addCookie(){
        String token = UUIDUtils.getGUID();
        Cookie cookie = new Cookie("token", token);
        if (!request.getServerName().contains("localhost"))
            cookie.setDomain(request.getServerName());
        //            cookie.setDomain("http://localhost:8080");
        cookie.setMaxAge(60 * 30);
        //返回Cookie适用的路径。如果不指定路径g，Cookie将返回给当前页面所在目录及其子目录下 的所有页面
        cookie.setPath("/");
        response.addCookie(cookie);
        //保存到缓存
        String cacheKey = Constants.CACHE_PREFIX_LOGIN_USER + token;
        userMap.put("token", token);
        redisCache.putHashMap(cacheKey, userMap, Constants.SESSION_TIMEOUT,
                              Constants.DEFAULT_CACHE_TIME_UNIT);
    }
}
