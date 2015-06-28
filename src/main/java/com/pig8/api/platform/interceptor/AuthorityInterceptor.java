package com.pig8.api.platform.interceptor;

import com.pig8.api.business.common.entity.response.DwzAjaxRes;
import com.pig8.api.platform.cache.RedisCache;
import com.pig8.api.platform.global.Constants;
import com.pig8.api.platform.util.CookieUtils;
import com.pig8.api.platform.util.JsonUtils;
import com.pig8.api.platform.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截控制器
 * @author navy
 */
public class AuthorityInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisCache redisCache;

    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse, Object o) throws Exception {
        String requestURI = httpServletRequest.getRequestURI();
        if (requestURI.endsWith("/login") || true ) {
            return true;
        } else {
            return checkUserToken(httpServletRequest, httpServletResponse);
        }
    }

    /**
     * 检查用户token是否失效
     * @param request
     * @param response
     * @return
     */
    private boolean checkUserToken(HttpServletRequest request, HttpServletResponse response) {
        String token = CookieUtils.get(request, "token");
        if (StringUtils.isEmpty(token)) {
            JsonUtils.outputJson(DwzAjaxRes.NOT_LOGIN, response);
            return false;
        } else {
            String cacheKey = Constants.CACHE_PREFIX_LOGIN_USER + token;
            Map<String, Object> userMap = redisCache.getHashMap(cacheKey);
            if (userMap != null && userMap.size()>0) {
                return true;
            } else {
                JsonUtils.outputJson(DwzAjaxRes.TIME_OUT, response);
                return false;
            }
        }
    }

    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse, Object o, Exception e)
            throws Exception {

    }
}
