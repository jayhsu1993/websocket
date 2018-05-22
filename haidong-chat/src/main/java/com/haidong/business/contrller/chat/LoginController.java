package com.haidong.business.contrller.chat;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.haidong.system.websocket.MessageHandler;
import com.huixin.system.entity.chat.LoginInfo;

import net.sf.json.JSONObject;


@Controller
public class LoginController {
	
	@Autowired
	private MessageHandler handler;
	
	@RequestMapping(value="/login")
	@ResponseBody
	public ModelAndView dologin(String username, String room, HttpSession session) throws Exception{
		Map<String, String> userMap = new HashMap<String, String>();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("data", username);
		jsonObject.put("room", room);
		userMap.put("userName", username);
		userMap.put("roomId", room);
		session.setAttribute("user", userMap);
		
//		return jsonObject;
		return new ModelAndView("redirect:/chat.jsp");
		
	}
	
}
