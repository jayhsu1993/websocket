package com.haidong.business.contrller.chat;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.haidong.system.websocket.Conversion;
import com.haidong.system.websocket.MessageHandler;
import com.huixin.framework.redis.RedisUtil;
import com.huixin.framework.utils.PageData;
import com.huixin.framework.wx.lottery.LotteryGift;
import com.huixin.framework.wx.lottery.LotteryUtil;
import com.huixin.haidong.service.system.business.wechatinfo.WechatInfoService;

@Component("random_stat")
@Transactional
public class RandomSendMessage {
	
	@Autowired
	private MessageHandler messageHandler;
	
	public void execute() throws IllegalArgumentException, IllegalAccessException{
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.HOUR_OF_DAY, 9); // 控制�?
//		calendar.set(Calendar.MINUTE, 0); // 控制�?
//		calendar.set(Calendar.SECOND, 0); // 控制�?
//
//		Date time = calendar.getTime(); // 得出执行任务的时�?
		
//		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(fc.getServletContext());  
//		final MessageHandler  handler = (MessageHandler) context.getBean("messageHandler");  
//		Timer timer = new Timer();	
//		TimerTask task = new TimerTask() {
//
//			public void run() {
				//TODO 发几次 随机
//				int times = 1 + new Random().nextInt(2);
//				
//				for(int i=0; i<times; i++){
					try {
						LotteryGift g = LotteryUtil.getLottery();
						String method = g.getLotteryId();
						List<String> returnMessage = Conversion.randomReturn(method);
						Thread.sleep(20 * 1000L);
						if(returnMessage.size() > 0){
							for(String message : returnMessage){
								messageHandler.sendRandomMsgToScreen(message);
								Thread.sleep(15 * 1000L);
							}
						}
						Thread.sleep(8*1000L);
						
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					
//				}
				System.out.println("RunTime:" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
//			}
//		};
//		
//		timer.schedule(task, time, 1000 * 6);// 每5分钟执行一次
//		try {
//			while (true) {
//				Thread.sleep(1000 * 15);	// 每5分钟改变一次周期
//				// 主线程5分钟后，更改任务周期
//				Field[] fields = task.getClass().getSuperclass().getDeclaredFields();
//				for (Field field : fields) {
//					if (field.getName().endsWith("period")) {
//						if (!field.isAccessible()) {
//							field.setAccessible(true);
//						}
//						//新周期 1-5分钟随机
//						field.set(task, (1 + new Random().nextInt(5)) * 1000l * 60);
//					}
//				}
//			}
//
//		} catch (InterruptedException e) {
//		}
//
//		 
	 }
}
