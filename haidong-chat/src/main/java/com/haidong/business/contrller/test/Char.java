package com.haidong.business.contrller.test;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.mchange.v1.cachedstore.CachedStore.Manager;

public class Char {

	// public static void main(String[] args) {
	// try {
	// WebSocketContainer container = ContainerProvider.getWebSocketContainer();
	// //
	// 获取WebSocket连接器，其中具体实现可以参照websocket-api.jar的源码,Class.forName("org.apache.tomcat.websocket.WsWebSocketContainer");
	// String uri =
	// "ws://121.40.194.154:8081/haidong-chat/marco?roomId=5ac20f85e70c4a0eb19802ea28ae84e0&type=1";
	// Session session = container.connectToServer(Client.class, new URI(uri));
	// // 连接会话
	// session.getBasicRemote().sendText("123132132131"); // 发送文本消息
	// session.getBasicRemote().sendText("4564546");
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }

	public static final String WS_URI = "ws://121.40.194.154:8081/haidong-chat/marco?userId=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiIyNGU1ZmI5NGI5OWY0NmRlYWQ5OTBmZWViMjhlMGY2ZiIsImlhdCI6MTQ4OTQ3MzgxOTg3NywiZXh0IjoxNDkwMDc4NjE5ODc3fQ.gEXN5Pumpn9RK7fDtDBF7LmGXImtv0r0uxnrP1b3LOM&roomId=5ac20f85e70c4a0eb19802ea28ae84e0&type=1";
//	public static final String WS_URI = "ws://192.168.1.92:8080/haidong-chat/marco?userId=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiI1ZTQ3YzJhMThmYWE0OWI4YTQ4OWQ4ZmQ5YTVlNWJiYiIsImlhdCI6MTQ4Nzc1NTk4MDgxMiwiZXh0IjoxNDg4MzYwNzgwODEyfQ.snTtHbCGzslJTkbHt5GACJcFFw1fG0QbM5q2fzHZbhg&roomId=5ac20f85e70c4a0eb19802ea28ae84e0&type=1";
//	public static final String WS_URI = "ws://localhost:8090/haidong-chat/marco?userId=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiI1ZTQ3YzJhMThmYWE0OWI4YTQ4OWQ4ZmQ5YTVlNWJiYiIsImlhdCI6MTQ4Nzc1NTk4MDgxMiwiZXh0IjoxNDg4MzYwNzgwODEyfQ.snTtHbCGzslJTkbHt5GACJcFFw1fG0QbM5q2fzHZbhg&roomId=5ac20f85e70c4a0eb19802ea28ae84e0&type=1";
	public static void main(String[] args) throws IOException, InterruptedException {
		StandardWebSocketClient client = new StandardWebSocketClient();
		for (int i = 0; i < 200; i++) {
			WebSocketConnectionManager manager = new WebSocketConnectionManager(client, new MyHandler(), WS_URI);

			manager.start();

			Thread.sleep(500);
		}
		Thread.sleep(1000 * 60 * 1);
	
	}

	private static class MyHandler extends TextWebSocketHandler {
		@Override
		public void afterConnectionEstablished(WebSocketSession session) throws Exception {
			System.out.println("connected...........");
//			session.sendMessage(new TextMessage("hello, web socket"));
//			super.afterConnectionEstablished(session);
		}

		@Override
		protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//			System.out.println("receive: " + message.getPayload());
//			super.handleTextMessage(session, message);
		}
	}

}
