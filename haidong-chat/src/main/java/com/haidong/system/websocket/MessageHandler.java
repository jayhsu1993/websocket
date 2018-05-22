package com.haidong.system.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.huixin.framework.exception.BusinessException;
import com.huixin.framework.redis.RedisUtil;
import com.huixin.framework.utils.Const;
import com.huixin.framework.utils.IpUtils;
import com.huixin.framework.utils.Logger;
import com.huixin.framework.utils.LoggerUtil;
import com.huixin.framework.utils.MapUtil;
import com.huixin.framework.utils.PageData;
import com.huixin.framework.utils.Tools;
import com.huixin.framework.utils.token.Jwt;

public class MessageHandler extends TextWebSocketHandler {
	protected Logger logger = Logger.getLogger(this.getClass());

	// 用户<roomId, List<WebSocketSession>>
//	private static Map<String, List<WebSocketSession>> userList = new ConcurrentHashMap<String, List<WebSocketSession>>();

	//已建立连接的用户
	private static Map<String, WebSocketSession> users = new ConcurrentHashMap<String, WebSocketSession>();

	// 大屏<sessionId,WebSocketSession>
//	private static Map<String, WebSocketSession> screens = new ConcurrentHashMap<String, WebSocketSession>();

	// private static Conversion conversion = new Conversion();

	/*
	 * 处理前端发送的文本信息
	 * (non-Javadoc)
	 * @see org.springframework.web.socket.handler.AbstractWebSocketHandler#handleTextMessage(org.springframework.web.socket.WebSocketSession, org.springframework.web.socket.TextMessage)
	 */
	protected void handleTextMessage(WebSocketSession session, TextMessage message) {
		if (!"".equals(message.getPayload())) {
			synchronized (session) {
				if (session.isOpen()) {
					// session.getBasicRemote().sendText(message);
					try {
						session.sendMessage(message);
					} catch (IOException e) {
						logger.info("发送出错EROR == handleTextMessage, size:" + users.size());
						e.printStackTrace();
					}
				} else {
					logger.info("handleTextMessage session has been closed:" + users.size());
				}
			}
		}
	}

	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		logger.info("连接出错EROR == handleTransportError, size:" + users.size());
		logger.info(exception.getMessage());
		try {
			if (session.isOpen()) {
				session.close();
			}
			users.remove(session.getId());
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

	}
    /*
     * 当连接建立时会被调用
     * (non-Javadoc)
     * @see org.springframework.web.socket.handler.AbstractWebSocketHandler#afterConnectionEstablished(org.springframework.web.socket.WebSocketSession)
     */
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		LoggerUtil.getchatConnectLog(logger.getLog4jLogger());
		users.put(session.getId(), session);
		String userId = null;
		String userRoom = null;
		String type = null;
		// Map<String, String> userMap = new HashMap<String, String>();
		if (session.getAttributes().get("userId") != null) {
			userId = (String) session.getAttributes().get("userId");
		}
		logger.info(userId + " 登录:afterConnectionEstablished Connection established, size:" + users.size());
		userRoom = (String) session.getAttributes().get("roomId");
		type = (String) session.getAttributes().get("type");
		// System.out.println("session.getLocalAddress().getHostName() " +
		// session.getRemoteAddress().getHostName());
		try {
			if(Tools.notEmpty(userId) && Tools.notEmpty(userRoom) && Tools.notEmpty(type)){
				String returnMessage = Conversion.loginReturn(String.valueOf(users.size()), userId, userRoom, type);
				if (returnMessage != null) {
					if (users != null) {
						for (WebSocketSession wssession : users.values()) {
							String room = (String) wssession.getAttributes().get("roomId");
							if (userRoom.equals(room)) {
								handleMessage(wssession, new TextMessage(returnMessage));
							}
						}
//						sendGameSign(returnMessage, session);
					}
				}
			}else{
				logger.info("params is miss 用户userId{" + userId + "} 房间roomId{"+ userRoom +"} 类型type{"+ type +"}");
			}
		
		} catch (BusinessException e) {
			logger.error("sys afterConnectionEstablished error" + users.size(), e);
			e.printStackTrace();
		}
	}


	private void sendGameSign(String returnMessage, WebSocketSession session) {
//		System.out.println("returnMessage:" + returnMessage);
		Map<String, Object> msg =  MapUtil.toHashMap(returnMessage);
//		System.out.println("msg:" + msg);
		if(null != msg ) {
//			System.out.println("msg.get(data)  " + msg.get("data"));
			if(null != msg.get("data") ) {
				Map<String, Object> data = (Map<String, Object>) msg.get("data");
				if ("2".equals(String.valueOf(data.get("type")))) {
					if(0 == Integer.valueOf(String.valueOf(data.get("state")))) {
						sendSign(session);
					}
				}
			}
		}
	}

	private void sendSign(WebSocketSession session) {
		String userRoom = (String) session.getAttributes().get("roomId");
		String userId = (String) session.getAttributes().get("userId");
		PageData pd = new PageData();
		pd.put("USER_ID", userId);
		String tokenStr = Jwt.putToken(pd);
		String path = RedisUtil.getInstance(5).get(Const.CHAT_LIST);
		String chatList[] = path.split(",");
		for (int i = 0; i < chatList.length; i++) {
			String url = chatList[i];
			// http://120.132.23.135:8080/,120.132.13.71
			// String WS_URI =
			// "ws://121.40.194.154:8081/haidong-chat/marco?userId=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiIyNGU1ZmI5NGI5OWY0NmRlYWQ5OTBmZWViMjhlMGY2ZiIsImlhdCI6MTQ4OTQ3MzgxOTg3NywiZXh0IjoxNDkwMDc4NjE5ODc3fQ.gEXN5Pumpn9RK7fDtDBF7LmGXImtv0r0uxnrP1b3LOM&roomId=5ac20f85e70c4a0eb19802ea28ae84e0&type=1";
//			logger.info("sendSign--> " +url + " == " + session.getRemoteAddress().getHostName());
//			logger.info("sendSign--> " +url + " == " + session.getRemoteAddress().getHostString());
//			logger.info("sendSign--> " +url + " == " + session.getRemoteAddress().getAddress().getHostAddress());
//			logger.info("sendSign--> " +url + " == " + session.getRemoteAddress().getAddress().getHostName());
			String p = IpUtils.getLinuxLocalIp();
//			logger.info("IpUtils.getLinuxLocalIp()--> " + p );
			if (!url.contains(p)) {
				StandardWebSocketClient client = new StandardWebSocketClient();
				String wsUrl = "ws:" + url.substring(url.indexOf("//"), url.length()) + "marco?roomId=" + userRoom + "&type=2&userId=" + tokenStr;
				WebSocketConnectionManager manager = new WebSocketConnectionManager(client, new MyHandler(), wsUrl);
				manager.start();
				logger.info("sentChatUrl--> " + wsUrl + " 广播");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					logger.info("InterruptedException " );
				}
				manager.stop();
			}
		}
	}

	/**
	 * 连接断开后，服务器消息处理
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String userId = (String) session.getAttributes().get("userId");
		logger.info(userId + "登出:afterConnectionClosed Connection closed. Status: " + status + " user:" + users.size());
		String userRoom = (String) session.getAttributes().get("roomId");
		users.remove(session.getId());
		try {
			Conversion.logoutReturn(String.valueOf(users.size()), userId, userRoom); // 清除redis中用户信息
		} catch (Exception e) {
			logger.error("sys afterConnectionClosed error" + users.size(), e);
			logger.info(e.getMessage());
		}
	}

	/**
	 * 针对所有用户广播(除弹幕过滤控制台不广播)
	 * 
	 * @param roomId
	 * @param returnMessage
	 * @throws Exception
	 */
	public void sendMsgToUsers(String roomId, String returnMessage) throws Exception {
		if (users != null) {
			logger.info("sendMsgToUsers:" + users.size());
			for (WebSocketSession wssession : users.values()) {
				String room = (String) wssession.getAttributes().get("roomId");
				String type = (String) wssession.getAttributes().get("type");
				if (roomId.equals(room) && !"4".equals(type)) {
					handleMessage(wssession, new TextMessage(returnMessage));
				}
			}
		}
	}

	/**
	 * 向APP用户广播消息(包括场外、场内APP用户)
	 * 
	 * @param roomId
	 * @param userType
	 * @param returnMessage
	 * @throws Exception
	 */
	public void sendMsgToAPPUsers(String roomId, String userType, String returnMessage) throws Exception {
		if (users != null) {
			logger.info("sendMsgToAPPUsers:" + users.size());
			for (WebSocketSession wssession : users.values()) {
				String room = (String) wssession.getAttributes().get("roomId");
				String type = (String) wssession.getAttributes().get("type");
				if (roomId.equals(room) && !"3".equals(type)) {
					handleMessage(wssession, new TextMessage(returnMessage));
				}
			}
		}
	}

	/**
	 * 针对大屏广播消息
	 * 
	 * @param roomId
	 * @param returnMessage
	 * @throws Exception
	 */
	public void sendMsgToScreen(String roomId, String returnMessage) throws Exception {
		if (users != null) {
			logger.info("sendMsgToScreen:" + users.size());
			for (WebSocketSession wssession : users.values()) {
				String room = (String) wssession.getAttributes().get("roomId");
				String type = (String) wssession.getAttributes().get("type");
				if (roomId.equals(room) && "3".equals(type)) { // 3:代表大屏
					handleMessage(wssession, new TextMessage(returnMessage));
				}
			}
		}
	}

	/**
	 * 服务器连接大屏心跳
	 * 
	 * @param returnMessage
	 * @throws Exception
	 */
	public void sendTimeMsgToAPPUser(String returnMessage) throws Exception {
		if (users != null) {
			logger.info("sendMsgToAPPUsers:" + users.size());
			for (WebSocketSession wssession : users.values()) {
				String type = (String) wssession.getAttributes().get("type");
				// if ("3".equals(type)) { //3:代表大屏
				handleMessage(wssession, new TextMessage(returnMessage));
				// }
			}
		}
	}
	
	/**
	 * 服务器不定时随机发送消息
	 * 
	 * @param returnMessage
	 * @throws Exception
	 */
	public void sendRandomMsgToScreen(String returnMessage) throws Exception {
		if (users != null) {
			logger.info("sendRandomMsgToScreen:" + users.size());
			for (WebSocketSession wssession : users.values()) {
				String type = (String) wssession.getAttributes().get("type");
				 if ("3".equals(type)) { //3:代表大屏
					 String liveId = (String) wssession.getAttributes().get("roomId");
					 try {
						 PageData live = Conversion.getLiveByReportNum(liveId);	//查询签到人数是否超过10
						 if(null != live){
							 String scene = live.getString("scene_type");
							 if(Tools.isEmpty(scene) || "0".equals(scene)){	//只有婚礼版发，其他场景自动屏蔽
								 handleMessage(wssession, new TextMessage(returnMessage));
							 }
						 }
					} catch (Exception e) {
						logger.error("内置弹幕礼物发送错误");
					}
					 
					
				 }
			}
		}
	}
	
	
	/**
	 * 针对g过滤器控制台广播消息
	 * 
	 * @param roomId
	 * @param returnMessage
	 * @throws Exception
	 */
	public void sendMsgToFilter(String roomId, String returnMessage) throws Exception {
		if (users != null) {
			logger.info("sendMsgToScreen:" + users.size());
			for (WebSocketSession wssession : users.values()) {
				String room = (String) wssession.getAttributes().get("roomId");
				String type = (String) wssession.getAttributes().get("type");
				if (roomId.equals(room) && "4".equals(type)) { // 4:代表弹幕过滤控制台
					handleMessage(wssession, new TextMessage(returnMessage));
				}
			}
		}
	}
	

	private static class MyHandler extends TextWebSocketHandler {
		@Override
		public void afterConnectionEstablished(WebSocketSession session) throws Exception {
			System.out.println("MyHandler connected...........");
			// session.sendMessage(new TextMessage("hello, web socket"));
			// super.afterConnectionEstablished(session);
		}

		@Override
		protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
			 System.out.println("MyHandler receive: " + message.getPayload());
			// super.handleTextMessage(session, message);
		}
		
		@Override
		public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
			session.close();
			System.out.println("MyHandler afterConnectionClosed status:" + status);
		}
		
		public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
			session.close();
			System.out.println("MyHandler handleTransportError exception:" + exception);
		}
		
	}

}