package com.haidong.business.contrller.chat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;
import com.haidong.business.contrller.base.BaseController;
import com.haidong.system.mongo.dao.MessageDao;
import com.haidong.system.mongo.entity.ChatMessage;
import com.haidong.system.websocket.Conversion;
import com.haidong.system.websocket.MessageHandler;
import com.huixin.framework.base.ResponseResult;
import com.huixin.framework.base.RestResultGenerator;
import com.huixin.framework.exception.BusinessException;
import com.huixin.framework.redis.RedisUtil;
import com.huixin.framework.utils.Logger;
import com.huixin.framework.utils.PageData;
import com.huixin.framework.utils.Tools;

import net.sf.json.JSONObject;

@Controller
public class MessageController extends BaseController{

	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private MessageHandler handler;
	
//	@Autowired
//	private MessageDao messageDao;

	@RequestMapping(value = "/chat", method = RequestMethod.GET)
	@ResponseBody
	public String send(HttpServletRequest request, String msg, String money) throws Exception {
		Map<String, Object> tokenMap = (Map<String, Object>) request.getAttribute("data");
		Map<String, String> userInfo = getUserInfo(tokenMap);
		logger.info(" userId:" + userInfo.get("userId") + " roomId:" + userInfo.get("roomId") + " /chat " + msg);
		try {
			String returnMessage = Conversion.chatReturn(msg, money, userInfo.get("userId"));
			this.handler.sendMsgToUsers(userInfo.get("roomId"), returnMessage);
//			if("1".equals(RedisUtil.getInstance(5).get("CHAT_RECORD_SWITCH"))){
//				//聊天信息存入MongoDB
//				ChatMessage chat = Conversion.getObjForMongo(msg, money, userInfo.get("roomId"), userInfo.get("userId"));
//				messageDao.save(chat);
//			}
		} catch (BusinessException e) {
			logger.error("sys chat error", e);
			e.printStackTrace();
		}
		return "success";
	}

	private Map<String, String> getUserInfo(Map<String, Object> tokenMap) {
		Map<String, String> result = new HashMap<String, String>();
		String str[] = ((String) tokenMap.get("uid")).split(",");
		result.put("userId", str[0]);
		if (str.length >= 2) {
			result.put("roomId", str[1]);
		}
		return result;
	}

	@RequestMapping(value = "/gift", method = RequestMethod.GET)
	@ResponseBody
	public String gift(HttpServletRequest request, String giftId, String text) throws Exception {
		Map<String, Object> tokenMap = (Map<String, Object>) request.getAttribute("data");
		Map<String, String> userInfo = getUserInfo(tokenMap);
		logger.info(" userId:" + userInfo.get("userId") + " roomId:" + userInfo.get("roomId") + " /gift " + giftId);
		try {
			String returnMessage = Conversion.giftReturn(giftId, text, userInfo.get("userId"));
			this.handler.sendMsgToUsers(userInfo.get("roomId"), returnMessage);
		} catch (BusinessException e) {
			logger.error("sys gift error", e);
			e.printStackTrace();
		}
		return "success";
	}

	/***
	 * 发送红包通知
	 * 
	 * @param request
	 *            发送红包者Id
	 * @param total
	 *            金额
	 * @param redpacketId
	 *            红包id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/redpacket", method = RequestMethod.GET)
	@ResponseBody
	public String redpacket(HttpServletRequest request, String total, String redpacketId, String couple)
			throws Exception {
		Map<String, Object> tokenMap = (Map<String, Object>) request.getAttribute("data");
		Map<String, String> userInfo = getUserInfo(tokenMap);
		logger.info(" userId:" + userInfo.get("userId") + " roomId:" + userInfo.get("roomId") + " /redpacket " + total);
		try {
			String returnMessage = Conversion.redPacketReturn(userInfo.get("userId"), total, redpacketId, couple);
			this.handler.sendMsgToUsers(userInfo.get("roomId"), returnMessage);
		} catch (BusinessException e) {
			logger.error("sys gift error", e);
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 用户进入游戏等待界面，广播通知大屏
	 * 
	 * @param userId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/gamePeople/{id}/{roomId}", method = RequestMethod.GET)
	@ResponseBody
	public String getGamePeople(@PathVariable("id") String userId, @PathVariable("roomId") String roomId,
			HttpServletRequest request) throws Exception {
		logger.info(userId + " /gamePeople ");
		try {
			String user_game_role = request.getParameter("user_game_role");
			String returnMessage = Conversion.gamePeopleReturn(userId, user_game_role);
			String rd = Conversion.getRoomId(userId);
			logger.info("rd:" + rd + "===  getGamePeople: " + returnMessage + "roomId:" + roomId);
			this.handler.sendMsgToScreen(roomId, returnMessage); // 针对大屏广播游戏等待人员
		} catch (BusinessException e) {
			logger.error("sys game error", e);
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 发送视频
	 * 
	 * @param userId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendVideo/{userId}/{roomId}", method = RequestMethod.GET)
	@ResponseBody
	public String sendVideo(@PathVariable("userId") String userId, @PathVariable("roomId") String roomId,
			HttpServletRequest request) throws Exception {
		logger.info(userId + "[ /sendVideo ]" + roomId);
		try {
			String returnMessage = Conversion.sendVideoReturn(userId);
			this.handler.sendMsgToScreen(roomId, returnMessage); // 针对大屏广播游戏等待人员
		} catch (BusinessException e) {
			logger.error("sys game error", e);
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 返回对应房间的游戏操作(该接口未使用)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/gameOperate", method = RequestMethod.GET)
	@ResponseBody
	public String returnGameOperate(HttpServletRequest request) throws Exception {
		String roomId = request.getParameter("roomId");
		String operateType = request.getParameter("type");
		logger.info(roomId + " /gameOperate/ " + operateType);
		try {
			String returnMessage = Conversion.gameOperateReturn(operateType);
			this.handler.sendMsgToUsers(roomId, returnMessage);
		} catch (BusinessException e) {
			logger.error("sys game error", e);
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 返回对应房间的投票结果(针对大屏发送)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/vote", method = RequestMethod.GET)
	@ResponseBody
	public String returnVoteResult(HttpServletRequest request) throws Exception {
		String roomId = request.getParameter("roomId");
		Map<String, Object> tokenMap = (Map<String, Object>) request.getAttribute("data");
		Map<String, String> userInfo = getUserInfo(tokenMap);
		String voteResult = request.getParameter("voteResult");
		logger.info(" userId:" + userInfo.get("userId") + " roomId:" + userInfo.get("roomId") + " /voteResult "
				+ voteResult);
		try {
			String returnMessage = Conversion.voteReturn(userInfo.get("userId"), voteResult);
			this.handler.sendMsgToScreen(roomId, returnMessage);
		} catch (BusinessException e) {
			logger.error("sys vote error", e);
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 广播游戏初始化完成(针对用户发送)
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = { "/returnGameResult" }, method = {org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public String returnGameResult(String gameCode, String liveId, String status) {
		this.logger.info(liveId + " /returnGameResult/ " + gameCode);
		try {
			String returnMessage = Conversion.returnGameResult(liveId, gameCode, status);
			this.handler.sendMsgToUsers(liveId, returnMessage);
		} catch (Exception e) {
			this.logger.error("sys game error", e);
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 发送获奖通知
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = { "/sendLottery" }, method = {org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public String sendLottery(HttpServletRequest request, String lotteryId, String total) {
		try {
			Map<String, Object> tokenMap = (Map<String, Object>) request.getAttribute("data");
			Map<String, String> userInfo = getUserInfo(tokenMap);
			logger.info("sendLottery userId:" + userInfo.get("userId") + " roomId:" + userInfo.get("roomId") + "  total"
					+ total + " lotteryId:" + lotteryId);
			String returnMessage = Conversion.returnSendLottery(userInfo.get("userId"), lotteryId, total);
			this.handler.sendMsgToScreen(userInfo.get("roomId"), returnMessage);
		} catch (Exception e) {
			this.logger.error("sys game error", e);
			e.printStackTrace();
		}
		return "success";
	}
	
	
	
	/**
	 * 发送切播通知
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = { "/changeVideo" }, method = {org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public String changeVideo(HttpServletRequest request, String videoUrl, String roomId) {
		try {
			String returnMessage = Conversion.changeVideoReturn(roomId, videoUrl);
			this.handler.sendMsgToUsers(roomId, returnMessage);
		} catch (Exception e) {
			this.logger.error("sys changeVideo error", e);
			e.printStackTrace();
		}
		return "success";
	}
	
	
	/**
	 * 获取当前直播地址
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getCurrentLive" }, method = {org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public String getCurrentLive(HttpServletRequest request, String roomId) {
		String live_path = "";
		try {
			live_path = Conversion.getCurrentLive(roomId);
		} catch (Exception e) {
			this.logger.error("sys getCurrentLive error", e);
			e.printStackTrace();
		}
		return live_path;
	}
	
	/**
	 * 用户所有消息发送到弹幕过滤器
	 * @param request
	 * @param msg
	 * @param giftId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/messageFilter", method = RequestMethod.GET)
	@ResponseBody
	public String messageFilter(HttpServletRequest request, String msg, String giftId) throws Exception {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> tokenMap = (Map<String, Object>) request.getAttribute("data");
		Map<String, String> userInfo = getUserInfo(tokenMap);
		String roomId = userInfo.get("roomId");
		String userId = userInfo.get("userId");
		
		logger.info(" userId:" + userId + " roomId:" + roomId + " /chat " + msg);
		String returnMessage = "";
		try {
			if(Tools.notEmpty(msg)){
				if(Tools.notEmpty(giftId)){	//giftId存在
					returnMessage = Conversion.giftReturn(giftId, msg, userId);
				}else{
					returnMessage = Conversion.chatReturn(msg, "", userId);
				}
			}
			this.handler.sendMsgToFilter(roomId, returnMessage);
		} catch (BusinessException e) {
			logger.error("sys messageFilter error", e);
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * 发送过滤后的消息
	 * @param request
	 * @param content
	 * @param roomId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendMessage", method = RequestMethod.GET)
	@ResponseBody
	public String sendMessage(HttpServletRequest request, String content, String roomId) throws Exception {
		logger.info("roomId:" + roomId + " /sendMessage ");
		try {
			this.handler.sendMsgToUsers(roomId, content);
		} catch (BusinessException e) {
			logger.error("sys sendMessage error", e);
			e.printStackTrace();
		}
		return "success";
	}
	
	
	/**
	 * 发送签到消息
	 * @param request
	 * @param content
	 * @param roomId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendSign", method = RequestMethod.GET)
	@ResponseBody
	public String sendMessage(HttpServletRequest request, String wish) throws Exception {
		Map<String, Object> tokenMap = (Map<String, Object>) request.getAttribute("data");
		Map<String, String> userInfo = getUserInfo(tokenMap);
		logger.info(" userId:" + userInfo.get("userId") + " roomId:" + userInfo.get("roomId") + " /sign ");
		try {
			String returnMessage = Conversion.signReturn(userInfo.get("userId"), userInfo.get("roomId"), "2", wish);	//2:现场用户
			this.handler.sendMsgToScreen(userInfo.get("roomId"), returnMessage);
		} catch (BusinessException e) {
			logger.error("sys sign error", e);
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * 发送来宾给司仪打赏消息
	 * @param request
	 * @param content
	 * @param roomId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/emceeTip", method = RequestMethod.GET)
	@ResponseBody
	public String sendEmceeTip(HttpServletRequest request) throws Exception {
		Map<String, Object> tokenMap = (Map<String, Object>) request.getAttribute("data");
		Map<String, String> userInfo = getUserInfo(tokenMap);
		logger.info(" userId:" + userInfo.get("userId") + " roomId:" + userInfo.get("roomId") + " /emceeTip ");
		try {
			String returnMessage = Conversion.emceeTipReturn(userInfo.get("userId"), userInfo.get("roomId"));
			this.handler.sendMsgToScreen(userInfo.get("roomId"), returnMessage);
		} catch (BusinessException e) {
			logger.error("sys sign error", e);
			e.printStackTrace();
		}
		return "success";
	}
	
	
//	@RequestMapping(value = "/chatRecord", method = RequestMethod.GET)
//	@ResponseBody
//	public ResponseResult<Map> chatList(HttpServletRequest request, String liveId, String pageNum, String pageSize) throws Exception {
//		logger.info(" roomId:" + liveId + " /chatRecord ");
//		//聊天信息存入MongoDB
//		try {
//			if("1".equals(RedisUtil.getInstance(5).get("CHAT_RECORD_SWITCH"))){
//				Page<ChatMessage> chat_record_list = messageDao.paginationQuery(liveId, Integer.parseInt(pageNum), Integer.parseInt(pageSize));
//				List<ChatMessage> chat_list = chat_record_list.getContent();
//				Map result = new HashMap();
//				result.put("chat_list", chat_list);
//				result.put("pageNum", pageNum);
//				return RestResultGenerator.genResult(result, "拉取聊天记录成功");
//			}else{
//				return RestResultGenerator.genResult("拉取聊天记录关闭");
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return RestResultGenerator.genErrorResult("拉取聊天记录失败");
//		}
//		
//	}
	
	/**
	 * 遥控器操作广播
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/telecontroller", method = RequestMethod.GET)
	@ResponseBody
	public String telecontroller(HttpServletRequest request) throws Exception {
		Map<String, Object> tokenMap = (Map<String, Object>) request.getAttribute("data");
		Map<String, String> userInfo = getUserInfo(tokenMap);
		PageData pd = this.getPageData();
		logger.info( "roomId:" + userInfo.get("roomId") + " action_code:" + pd.getString("action_code") + " /telecontroller ");
		try {
			String returnMessage = Conversion.telecontrollerReturn(userInfo.get("roomId"), pd);
			this.handler.sendMsgToScreen(userInfo.get("roomId"), returnMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "success";
	}
	
	/**
	 * 抽奖获奖人员信息广播
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/winningList", method = RequestMethod.GET)
	@ResponseBody
	public String winningList(HttpServletRequest request) throws Exception {
		Map<String, Object> tokenMap = (Map<String, Object>) request.getAttribute("data");
		Map<String, String> userInfo = getUserInfo(tokenMap);
		PageData pd = this.getPageData();
		logger.info( "roomId:" + userInfo.get("roomId") + " /winningList");
		try {
			String returnMessage = Conversion.winningReturn(userInfo.get("roomId"), pd);
			this.handler.sendMsgToUsers(userInfo.get("roomId"), returnMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "success";
	}
	
	/**
	 * 游戏获奖人员信息广播
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/gameReward", method = RequestMethod.GET)
	@ResponseBody
	public String gameReward(HttpServletRequest request) throws Exception {
		Map<String, Object> tokenMap = (Map<String, Object>) request.getAttribute("data");
		Map<String, String> userInfo = getUserInfo(tokenMap);
		PageData pd = this.getPageData();
		logger.info( "roomId:" + userInfo.get("roomId") + " /gameReward ");
		
		try {
			String returnMessage = Conversion.gameRewardReturn(userInfo.get("roomId"), pd);
			this.handler.sendMsgToUsers(userInfo.get("roomId"), returnMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "success";
	}
	
	/**
	 * 广播司仪打赏初始化完成(针对用户发送)
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = { "/returnEmceeTip" }, method = {org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public String returnEmceeTipResult(HttpServletRequest request, String money_one, String money_two) {
		Map<String, Object> tokenMap = (Map<String, Object>) request.getAttribute("data");
		Map<String, String> userInfo = getUserInfo(tokenMap);
		String liveId = userInfo.get("roomId");
		this.logger.info(liveId + " /returnEmceeTip/ ");
		try {
			String returnMessage = Conversion.returnEmcee(liveId, money_one, money_two);
			this.handler.sendMsgToUsers(liveId, returnMessage);
		} catch (Exception e) {
			this.logger.error("sys EmceeTip error", e);
			e.printStackTrace();
		}
		return "success";
	}
	

}
