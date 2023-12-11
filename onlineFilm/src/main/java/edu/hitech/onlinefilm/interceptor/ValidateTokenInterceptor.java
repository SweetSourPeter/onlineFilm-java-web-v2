package edu.hitech.onlinefilm.interceptor;


import cn.hutool.core.util.StrUtil;
import edu.hitech.onlinefilm.common.RespCode;
import edu.hitech.onlinefilm.domain.Customer;
import edu.hitech.onlinefilm.exception.OnlineFilmException;
import edu.hitech.onlinefilm.utils.CacheUtils;
import edu.hitech.onlinefilm.utils.DateUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidateTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equalsIgnoreCase("options")){
            return true;
        }

        String path = request.getServletPath();

        if (!isEscapePath(path) ) {
            checkToken(request);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }


    /**
     * 是否为不需要认证的访问
     * @param path
     * @return
     */
    private boolean isEscapePath(String path){
        return path.contains("/account/login") ||  path.contains("/schedule/list")
                 || path.contains("/account/register")
                 || path.endsWith(".ico") || path.endsWith("/error")
                 || path.endsWith(".html");
    }


    private void checkToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StrUtil.isEmpty(token)) {
            throw new OnlineFilmException(RespCode.USER_NO_LOGIN);
        }
        Customer user = CacheUtils.getUser(token);
        if (user == null){
            throw new OnlineFilmException(RespCode.USER_NO_LOGIN);
        }
        CacheUtils.storeCurrentToken(token);
    }
}
