package com.huixin.haidong.business.interceptor;

import com.huixin.framework.enums.ApiSet;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * FilterHandlerIntercepor.
 *
 * @author Lance
 * @date 2017/03/22
 */
public class FilterHandlerIntercepor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String uri = request.getRequestURI();
        if(uri.indexOf("/", 1) > -1){
            uri = uri.substring(0, uri.indexOf("/", 1));
        }
        if(ApiSet.getAapis().contains(uri)){
            return true;
        }else{
            response.sendRedirect(request.getRequestURL().substring(0, request.getRequestURL().indexOf(request.getRequestURI())));
            return false;
        }
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
