/**
 * 
 */
package com.haidong.system.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.huixin.framework.constant.ComConstants;
import com.huixin.framework.exception.BusinessException;
import com.huixin.framework.utils.Logger;
import com.huixin.framework.utils.PageData;
import com.huixin.framework.utils.token.Jwt;

import net.minidev.json.JSONObject;

/**
 * @author X.Y.CHEN
 * 
 * 2016年7月5日
 */
public class CheckTokenFilter implements HandlerInterceptor {
	
	protected Logger logger = Logger.getLogger(this.getClass());

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// response.setHeader("Access-Control-Allow-Origin", "*");
		// 其他API接口一律校验token
		// 从请求头中获取token
		System.out.println(request.getServletPath());
		String token = request.getHeader("token");
		Map<String, Object> resultMap = Jwt.validToken(token);
		TokenState state = TokenState.getTokenState((String) resultMap.get("state"));
		switch (state) {
		case VALID:
			// 取出payload中数据,放入到request作用域中
			request.setAttribute("data", resultMap.get("data"));
			// 放行
			return true;
		case EXPIRED:
//			invalToken(response, "您的token不合法");
			throw new BusinessException(ComConstants.INVALID, "登录已过期,请重新登录.", new PageData());
		case INVALID:
//			invalToken(response, "您的token不合法");
			throw new BusinessException(ComConstants.INVALID, "非法用户登录.", new PageData());
		} 
		return false;
	}
	
	private void invalToken(HttpServletResponse response, String msg) throws IOException {
		// token过期或者无效，则输出错误信息返回给ajax
		JSONObject outputMSg = new JSONObject();
		outputMSg.put("code", ComConstants.INVALID);
		outputMSg.put("message", msg);
		logger.info("不合法的token");
		output(outputMSg.toJSONString(), response);
	}

	public void output(String jsonStr, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8;");
		PrintWriter out = response.getWriter();
		// out.println();
		out.write(jsonStr);
		out.flush();
		out.close();

	}

	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("token过滤器初始化了");
	}

	public void destroy() {

	}

	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		System.out.println("afterCompletion");

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		System.out.println("postHandle");
	}

}
