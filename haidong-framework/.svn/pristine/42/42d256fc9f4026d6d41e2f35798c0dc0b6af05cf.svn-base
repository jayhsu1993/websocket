
package com.huixin.framework.exception;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.huixin.framework.log.LogUtil;


/**
 * 异常处理
 *
 * @author fshitd04
 *         Jun 4, 2013
 */
public class ExceptionResolver implements HandlerExceptionResolver {
    private static Logger logger = LogUtil.getLog(ExceptionResolver.class);

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.error("操作执行异常", ex);
        /**
         * handleType 在util.js定义 0生成创建当前页面窗口 1打开一个新窗口 2:刷新当前页
         */
        ModelAndView mv = null;
        String contentType = request.getContentType();
        String accept = request.getHeader("accept");
        try {
            String path = getPath(request);
            String servletPath = request.getServletPath();
            String msg = "";
            if (!(ex instanceof AppException)) {
                msg += ex.getClass().getSimpleName() + "：";
            }
            String headRequestWidth = request.getHeader("X-Requested-With");
            msg += ex.getMessage() == null ? "null" : ex.getMessage().replaceAll("\"", "'").replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n");
            if ((accept != null && accept.indexOf("application/json") != -1) || (contentType != null && contentType.indexOf("application/json") != -1)) {
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                String resultMsg = "{\"result\":\"fail\",\"message\":\"" + msg + "\"}";
                writer.write(resultMsg);
                writer.close();
                return new ModelAndView();
            } else if ("XMLHttpRequest".equalsIgnoreCase(headRequestWidth)) {//ajax请求返回小页面
                request.setAttribute("javax.servlet.error.message", msg);
                request.setAttribute("url", path);
                mv = new ModelAndView("common/error");
                return mv;
            } else {
                request.setAttribute("javax.servlet.error.message", msg);
                request.setAttribute("url", path);
                mv = new ModelAndView("common/error_full");
                return mv;

            }
        } catch (Exception e) {
            logger.error("操作处理失败", e);
        }
        return null;
    }

    private String getPath(HttpServletRequest request) {
        String url = "";
        if (request.isSecure()) {
            url += "https://";
        } else {
            url += "http://";
        }

        url += request.getServerName();

        int portal = request.getServerPort();
        if (portal != 80) {
            url += ":" + portal;
        }

        url += request.getContextPath() + request.getServletPath();

        String qs = request.getQueryString();
        if (qs != null) {
            url += "?" + qs;
        }

        return url;
    }

}
