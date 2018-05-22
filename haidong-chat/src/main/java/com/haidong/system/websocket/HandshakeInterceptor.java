package com.haidong.system.websocket;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.haidong.system.filter.TokenState;
import com.huixin.framework.constant.ComConstants;
import com.huixin.framework.exception.BusinessException;
import com.huixin.framework.utils.PageData;
import com.huixin.framework.utils.token.Jwt;

public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
		System.out.println("After Handshake");
		super.afterHandshake(request, response, wsHandler, ex);
	}

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		System.out.println("Before Handshake");
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			HttpServletRequest httpRequest = servletRequest.getServletRequest();
			String userId = httpRequest.getParameter("userId");
			String roomId = httpRequest.getParameter("roomId");
			String type = httpRequest.getParameter("type");

//			 System.out.println("userId====" + userId);
			// System.out.println("roomId====" + roomId);
			// HttpSession session =
			// servletRequest.getServletRequest().getSession(false);
			if (!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(roomId)) {
				Map<String, Object> resultMap = Jwt.validToken(userId);
					TokenState state = TokenState.getTokenState((String) resultMap.get("state"));
					switch (state) {
					case VALID:
						Map<String, Object> tokenMap = (Map<String, Object>) resultMap.get("data");
						userId = (String) tokenMap.get("uid");
						break;
					case EXPIRED:
//						invalToken(response, "您的token不合法");
						throw new BusinessException(ComConstants.INVALID, "登录已过期,请重新登录.", new PageData());
					case INVALID:
//						invalToken(response, "您的token不合法");
						throw new BusinessException(ComConstants.INVALID, "非法用户登录.", new PageData());
				}
				attributes.put("userId", userId);
				attributes.put("roomId", roomId);
				attributes.put("type", type);
			} else {
				attributes.put("roomId", roomId);
				attributes.put("type", type);
			}
		}
		return super.beforeHandshake(request, response, wsHandler, attributes);

	}
	


}