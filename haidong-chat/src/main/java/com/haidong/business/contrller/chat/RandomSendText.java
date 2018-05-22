package com.haidong.business.contrller.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.haidong.system.websocket.Conversion;
import com.haidong.system.websocket.MessageHandler;

@Component("random_text")
@Transactional
public class RandomSendText {
	@Autowired
	private MessageHandler messageHandler;
	
	public void execute() throws IllegalArgumentException, IllegalAccessException{
		try {
			String text = Conversion.RandomTextReturn();
			messageHandler.sendRandomMsgToScreen(text);
			Thread.sleep(3 * 1000L);
			String normal_text = Conversion.RandomNormalTextReturn();
			messageHandler.sendRandomMsgToScreen(normal_text);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
