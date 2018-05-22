package com.haidong.system.filter;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.haidong.system.websocket.Conversion;
import com.haidong.system.websocket.MessageHandler;
import com.huixin.system.entity.chat.Message;
import com.huixin.system.entity.chat.MessageReturn;

/**
 * 登录验证过滤
 * 
 * 创建人：FH 创建时间
 * 
 * @version
 */
public class StartFilter implements Filter {
	
	private  MessageHandler  handler;
	
	/**
	 * 初始�?
	 */
	public void init(FilterConfig fc) throws ServletException {
		// FileUtil.createDir("d:/FH/topic/");
		// writeFile();
		timer(fc);
//		 ServletContext context = fc.getServletContext();  
//	     ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);  
//	     handler = (MessageHandler) ctx.getBean("messageHandler");
	}

	// 计时�?
	public void timer(FilterConfig fc) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 9); // 控制�?
		calendar.set(Calendar.MINUTE, 0); // 控制�?
		calendar.set(Calendar.SECOND, 0); // 控制�?

		Date time = calendar.getTime(); // 得出执行任务的时�?
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(fc.getServletContext());  
		final MessageHandler  handler = (MessageHandler) context.getBean("messageHandler");  
		Timer timer = new Timer();
		Gson gson = new Gson();
		MessageReturn messageReturn = new MessageReturn();
		Message message = new Message();
		message.setMessageType("3"); // 3.定时发送，防止断线
		message.setText("timer");
		messageReturn.setData(message);
		messageReturn.setCode(Conversion.successCode);
		messageReturn.setMessage("success");
		messageReturn.setType(Conversion.TIMER);
		final String str =  gson.toJson(messageReturn);
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				try {
					handler.sendTimeMsgToAPPUser(str);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, time, 1000 * 60 * 3);// 这里设定将延时每天固定执�?
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub

	}

	public MessageHandler getHandler() {
		return handler;
	}

	public void setHandler(MessageHandler handler) {
		this.handler = handler;
	}

}
