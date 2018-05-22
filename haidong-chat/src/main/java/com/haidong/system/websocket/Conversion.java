package com.haidong.system.websocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.haidong.business.contrller.base.BaseController;
//import com.haidong.system.mongo.entity.ChatMessage;
import com.huixin.framework.base.JacksonMapper;
import com.huixin.framework.exception.BusinessException;
import com.huixin.framework.redis.RedisUtil;
import com.huixin.framework.sensitiveword.SensitivewordFilterUtil;
import com.huixin.framework.utils.Logger;
import com.huixin.framework.utils.PageData;
import com.huixin.framework.utils.Tools;
import com.huixin.haidong.service.system.business.dictionary.DictionaryService;
import com.huixin.haidong.service.system.business.live.LiveService;
import com.huixin.haidong.service.system.business.money.MoneyService;
import com.huixin.haidong.service.system.business.report.ReportService;
import com.huixin.haidong.service.system.business.wechatinfo.WechatInfoService;
import com.huixin.system.entity.chat.EmceeTip;
import com.huixin.system.entity.chat.GameInfo;
import com.huixin.system.entity.chat.GameReward;
import com.huixin.system.entity.chat.Gift;
import com.huixin.system.entity.chat.LoginInfo;
import com.huixin.system.entity.chat.LotteryGift;
import com.huixin.system.entity.chat.Message;
import com.huixin.system.entity.chat.MessageReturn;
import com.huixin.system.entity.chat.RedPacket;
import com.huixin.system.entity.chat.Telecontroller;
import com.huixin.system.entity.chat.Video;
import com.huixin.system.entity.chat.Vote;
import com.huixin.system.entity.chat.Winning;

/**
 * 
 * @author wuxiang date:2016.8.1 用来封装返回信息，并转换为json字符串
 */

@Component
public class Conversion extends BaseController {

	protected static Logger logger = Logger.getLogger("com.haidong.system.websocket.Conversion");
	public final static String successCode = "200";
	private final static String tokenInvalid = "508";
	private final static String paramsInvalid = "507";
	private final static String LOGIN = "login"; // 对应的是操作类型
	private final static String LOGOUT = "logout";
	private final static String CHAT = "chat";
	private final static String GIFT = "gift";
	private final static String REDPACKET = "redpacket";
	private final static String LOTTERYGIFT = "lotteryGift";
	public final static String TIMER = "timer";
	public final static String GAME = "game";
	public final static String VIDEO = "video";
	public final static String VOTE = "vote"; // 投票
	private final static String EMCEETIP = "emceeTip"; // 司仪打赏(用户打赏广播，通知大屏)
	private final static String TELECONTROLLER = "telecontroller"; // 遥控器
	private final static String WINNING = "winning"; // 抽奖获奖名单
	private final static String GAMEREWARD = "gameReward"; // 游戏获奖名单
	private final static String GAME_EMCEETIP = "gameTip"; // 大屏司仪打赏(通知所有用户)
	private final static String SIGN = "sign"; // 来宾签到

	// key对应是用户的userId,value对应的是用户信息
	// private static Map<String, PageData> usersInfo = new HashMap<String,
	// PageData>();

	@Autowired
	private WechatInfoService wechatinfoService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private MoneyService moneyService;
	@Autowired
	private LiveService liveService;
	@Autowired
	private DictionaryService dictionaryService;

	private static Conversion conversion; // 静态初使化 一个Conversion类
											// 这样是为了在spring初使化之前

	public void setWechatinfoService(WechatInfoService wechatinfoService) {
		this.wechatinfoService = wechatinfoService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public void setMoneyService(MoneyService moneyService) {
		this.moneyService = moneyService;
	}

	public void setLiveService(LiveService liveService) {
		this.liveService = liveService;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	@PostConstruct // 通过@PostConstruct 和 @PreDestroy 方法 实现初始化和销毁bean之前进行的操作
	public void init() {
		conversion = this;
		conversion.wechatinfoService = this.wechatinfoService; // 初使化时将已静态化的wechatinfoService实例化
		conversion.reportService = this.reportService;
		conversion.moneyService = this.moneyService;
		conversion.liveService = this.liveService;
		conversion.dictionaryService = this.dictionaryService;
	}

	/**
	 * 封装登陆返回信息
	 * 
	 * @param countNum
	 * @param photoUrl
	 * @param userName
	 * @return
	 */

	public static String loginReturn(String countNum, String userId, String roomId, String type)
			throws BusinessException {
		logger.info("用户userId{" + userId + "} 房间roomId{" + roomId + "} 类型type{" + type + "}");
		MessageReturn messageReturn = new MessageReturn();
		LoginInfo loginInfo = new LoginInfo();
		Boolean tokenValidate = true; // token验证结果
		if (tokenValidate) {
			// userId等于空 时初始化大屏 建立连接
			if (null != userId && !"".equals(userId) && !"undefined".equals(userId)) {
				PageData pd = new PageData();
				pd.put("USER_ID", userId);
				try {
					pd = conversion.wechatinfoService.findHeadUrlByUserId(pd);
					if (pd != null) {
						String userName = pd.getString("nickname");
						String photoUrl = pd.getString("headimgurl");
						String gender = pd.getString("sex");
						loginInfo.setUserId(userId);
						loginInfo.setGender(gender);
						loginInfo.setUserName(userName);
						loginInfo.setPhotoUrl(photoUrl);
						pd.put("roomId", roomId);
						pd.put("type", type);
					}
				} catch (Exception e) {
					logger.error("sys loginReturn error", e);
					e.printStackTrace();
				}
				loginInfo.setType(type); // 1代表App用户 2代表现场用户

				logger.info("用户pd {" + pd + "}");
			} else {
				loginInfo.setType("3"); // 3代表大屏
			}
			loginInfo.setCountNum(countNum);
			messageReturn.setData(loginInfo);
			messageReturn.setCode(successCode);
			messageReturn.setMessage("success");

		}
		messageReturn.setType(LOGIN);
		Gson gson = new Gson();
		return gson.toJson(messageReturn);
	}

//	/*
//	 * 设置现场用户
//	 */
//	private static String setCurrentUser(String roomId, LoginInfo loginInfo, PageData pd, String userName,
//			String photoUrl) throws Exception {
//		logger.info("设置现场用户 setCurrentUser[ " + pd + " ]");
//		PageData pdreport = new PageData();
//		pdreport.put("liveid", roomId);
//		// pdreport.put("unionid", pd.get("unionid"));
//		pdreport.put("userId", pd.get("USER_ID"));
//		pdreport = conversion.reportService.findByUnionId(pdreport);
//		if (pdreport != null) {
//			if (pdreport.getInt("state") == 1) {
//				loginInfo.setState(1);// 已签到，已广播
//			} else {
//				loginInfo.setState(0);// 已签到，未广播
//				pdreport.put("state", 1);
//				pdreport.put("userId", pd.get("USER_ID"));
//				conversion.reportService.editState(pdreport);
//			}
//			// 现场用户取更改之后的昵称和头像链接
//			String renickname = pdreport.getString("renickname");
//			String rephotoUrl = pdreport.getString("reheadimgurl");
//			String wish = pdreport.getString("wish");
//			if (null == renickname || "".equals(renickname)) {
//				loginInfo.setUserName(userName);
//			} else {
//				loginInfo.setUserName(renickname);
//				pd.put("nickname", renickname);
//			}
//			if (null == rephotoUrl || "".equals(rephotoUrl)) {
//				loginInfo.setPhotoUrl(photoUrl);
//			} else {
//				loginInfo.setPhotoUrl(rephotoUrl);
//				pd.put("headimgurl", rephotoUrl);
//			}
//			loginInfo.setWish(wish);
//			return "2";
//		} else {
//			logger.info("现场用户 但未签到成功 roomId[ " + roomId + " ] 用户Id{" + pd.get("USER_ID") + "}");
//			return "1"; // 未签到成功 算场外用户
//		}
//	}

	/**
	 * 封装注销时返回信息(清除缓存信息)
	 * 
	 * @param countNum
	 * @param photoUrl
	 * @param userName
	 * @return
	 */

	public static void logoutReturn(String countNum, String userId, String roomId) throws BusinessException {
		if (null != userId && null != roomId) {
			// RedisUtil.getInstance().del(userId); // 用户离开该房间时，移除redis中的缓存信息
			// RedisUtil.getInstance().set(roomId, countNum); //
			// 将当前房间的观众人数存入redis缓存
		}
	}

	/**
	 * 封装发送消息时返回信息
	 * 
	 * @param text
	 * @param userName
	 * @return
	 */

	public static String chatReturn(String text, String money, String userId) throws BusinessException {

		MessageReturn messageReturn = new MessageReturn();
		Message message = new Message();
		if (null != money && !"".equals(money)) {
			message.setMoney(money);
			message.setMessageType("2"); // 2.祝福消息
		} else {
			message.setMessageType("1"); // 1.普通消息
			// 过滤普通消息
			SensitivewordFilterUtil filter = new SensitivewordFilterUtil();
			text = filter.filterMessage(text);
		}
		PageData pd = new PageData();
		pd.put("USER_ID", userId);
		try {
			pd = conversion.wechatinfoService.findHeadUrlByUserId(pd);
		} catch (Exception e) {
			logger.info("chatReturn-error"); //
			e.printStackTrace();
		}
		// 从redis缓存中取出所需要的用户信息
		message.setPhotoUrl(pd.getString("headimgurl"));
		message.setGender(pd.getString("sex"));
		message.setUserName(pd.getString("nickname"));
		message.setUserId(userId);
		message.setText(text);
		messageReturn.setCode(successCode);
		messageReturn.setMessage("success");
		messageReturn.setData(message);
		messageReturn.setType(CHAT);
		Gson gson = new Gson();
		return gson.toJson(messageReturn);
	}

	/**
	 * 得到需要存入mongoDb的对象
	 * 小程序端的需要放开注释
	 * @param text
	 * @param money
	 * @param userId
	 * @return
	 * @throws BusinessException
	 */
//	public static ChatMessage getObjForMongo(String text, String money, String roomId, String userId)
//			throws BusinessException {
//		ChatMessage message = new ChatMessage();
//		if (null != money && !"".equals(money)) {
//			message.setMoney(money);
//			message.setMessageType("2"); // 2.祝福消息
//		} else {
//			message.setMessageType("1"); // 1.普通消息
//			// 过滤普通消息
//			SensitivewordFilterUtil filter = new SensitivewordFilterUtil();
//			text = filter.filterMessage(text);
//		}
//		PageData pd = new PageData();
//		pd.put("USER_ID", userId);
//		try {
//			pd = conversion.wechatinfoService.findHeadUrlByUserId(pd);
//		} catch (Exception e) {
//			logger.info("chatReturn-error"); //
//			e.printStackTrace();
//		}
//		// 从redis缓存中取出所需要的用户信息
//		message.setPhotoUrl(pd.getString("headimgurl"));
//		message.setGender(pd.getString("sex"));
//		message.setUserName(pd.getString("nickname"));
//		message.setText(text);
//		message.setRoomId(roomId);
//		message.setUserId(userId);
//		return message;
//	}

	/**
	 * 封装发礼物后返回的数据
	 * 
	 * @param giftId
	 * @param userName
	 * @return
	 */

	public static String giftReturn(String giftId, String text, String userId) throws BusinessException {

		MessageReturn messageReturn = new MessageReturn();
		Gift gift1 = new Gift();
		if (null != giftId && !"".equals(giftId)) {
			gift1.setGiftId(giftId);
		}

		PageData pd = new PageData();
		pd.put("USER_ID", userId);
		try {
			pd = conversion.wechatinfoService.findHeadUrlByUserId(pd);
		} catch (Exception e) {
			logger.info("chatReturn-error"); //
			e.printStackTrace();
		}
		// 从redis缓存中取出所需要的用户信息(如果不存在，返回是null，前台解析会报错吗？)
		gift1.setPhotoUrl(pd.getString("headimgurl"));
		gift1.setGender(pd.getString("sex"));
		gift1.setUserName(pd.getString("nickname"));

		if (null != text && !"".equals(text)) {
			gift1.setText(text);
			gift1.setType("2");
		} else {
			gift1.setType("1");
		}
		messageReturn.setCode(successCode);
		messageReturn.setMessage("success");
		messageReturn.setData(gift1);
		messageReturn.setType(GIFT);
		Gson gson = new Gson();
		return gson.toJson(messageReturn);
	}

	/***
	 * 发送红包返回的数据
	 * 
	 * @param sendUserId
	 *            发送红包者Id
	 * @param total
	 *            金额
	 * @return
	 * @throws Exception
	 */
	public static String redPacketReturn(String sendUserId, String total, String redpacketId, String couple)
			throws Exception {
		RedPacket redPacket = new RedPacket();
		MessageReturn messageReturn = new MessageReturn();
		// 从redis缓存中取出所需要的用户信息(如果不存在，返回是null，前台解析会报错吗？)
		PageData pd = new PageData();
		pd.put("USER_ID", sendUserId);
		PageData user = conversion.wechatinfoService.findHeadUrlByUserId(pd);
		redPacket.setUserId(sendUserId);
		redPacket.setUserName(user.getString("nickname"));
		redPacket.setPhotoUrl(user.getString("headimgurl"));
		redPacket.setText("发送了一个大红包");
		redPacket.setRedpacketId(redpacketId);
		redPacket.setTotal(total);
		redPacket.setCouple(couple);
		messageReturn.setMessage("success");
		messageReturn.setData(redPacket);
		messageReturn.setType(REDPACKET);
		messageReturn.setCode(successCode);
		Gson gson = new Gson();
		return gson.toJson(messageReturn);
	}

	/**
	 * 返回参加游戏的人员信息
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static String gamePeopleReturn(String userId, String user_game_role) throws Exception {
		GameInfo gamePeople = new GameInfo();
		MessageReturn messageReturn = new MessageReturn();
		PageData pd = new PageData();
		pd.put("USER_ID", userId);
		PageData user = conversion.wechatinfoService.findHeadUrlByUserId(pd);
		logger.info("gamePeopleReturn: " + user);
		gamePeople.setPhotoUrl(user.getString("headimgurl"));
		gamePeople.setUserName(user.getString("nickname"));
		gamePeople.setUserId(userId);
		gamePeople.setType("people");
		if (Tools.notEmpty(user_game_role)) {
			gamePeople.setUser_game_role(user_game_role); // 用户游戏所属
		}
		messageReturn.setMessage("success");
		messageReturn.setData(gamePeople);
		messageReturn.setType(GAME);
		messageReturn.setCode(successCode);
		Gson gson = new Gson();
		return gson.toJson(messageReturn);
	}

	/**
	 * 返回视频发送消息
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static String sendVideoReturn(String userId) throws Exception {
		Video video = new Video();
		MessageReturn messageReturn = new MessageReturn();
		// PageData pd = new PageData();
		// pd.put("USER_ID", userId);
		// PageData user = conversion.wechatinfoService.findHeadUrlByUserId(pd);
		logger.info("sendVideoReturn: " + userId);
		// video.setPhotoUrl(user.getString("headimgurl"));
		// video.setUserName(user.getString("nickname"));
		// video.setUserId(userId);
		video.setAddress("美国");
		video.setPhotoUrl(
				"http://wx.qlogo.cn/mmopen/Q3auHgzwzM6lvz3fa1nXNFwMcsFaKwBEjria9pBHSYpjicRLALkaDF8xibTuWwEe4DPFFvURRHoSqTJwG64LfCoEicQfxARRMR6yS4ibG5aB4xvQ/0");
		video.setUserName("旧金山的二大爷");
		video.setVideoUrl("http://www.hidongtv.com/jw2.mp4");
		video.setUserId(userId);

		messageReturn.setMessage("success");
		messageReturn.setData(video);
		messageReturn.setType(VIDEO);
		messageReturn.setCode(successCode);
		Gson gson = new Gson();
		return gson.toJson(messageReturn);
	}

	/**
	 * 返回游戏操作行为
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static String gameOperateReturn(String type) throws Exception {
		GameInfo gameOperate = new GameInfo();
		MessageReturn messageReturn = new MessageReturn();
		PageData pd = new PageData();
		gameOperate.setType(type); // start:开始游戏 end:结束游戏
		messageReturn.setMessage("success");
		messageReturn.setData(gameOperate);
		messageReturn.setType(GAME);
		messageReturn.setCode(successCode);
		Gson gson = new Gson();
		return gson.toJson(messageReturn);

	}

	/**
	 * 投票返回信息
	 * 
	 * @param userId
	 * @param voteResult
	 * @return
	 * @throws Exception
	 */
	public static String voteReturn(String userId, String voteResult) throws Exception {
		Vote vote = new Vote();
		MessageReturn messageReturn = new MessageReturn();
		PageData pd = new PageData();
		// pd.put("USER_ID", userId);
		// PageData userInfo =
		// conversion.wechatinfoService.findHeadUrlByUserId(pd);
		// vote.setUserId(userId);
		// vote.setNickname(userInfo.getString("nickname"));
		// vote.setPhotoUrl(userInfo.getString("headimgurl"));
		vote.setVoteResult(voteResult);
		messageReturn.setMessage("success");
		messageReturn.setData(vote);
		messageReturn.setType(VOTE);
		messageReturn.setCode(successCode);
		Gson gson = new Gson();
		return gson.toJson(messageReturn);
	}

	/**
	 * 构造游戏针对用户广播数据
	 * 
	 * @param liveId
	 * @param gameCode
	 * @return
	 */
	public static String returnGameResult(String liveId, String gameCode, String status) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("liviId", liveId);
		data.put("gameCode", gameCode);
		data.put("status", status);
		result.put("data", data);
		result.put("code", successCode);
		result.put("message", "success");
		result.put("type", GAME);
		return JacksonMapper.toJsonString(result);
	}

	/**
	 * 构造游戏针对用户广播数据
	 * 
	 * @param liveId
	 * @param gameCode
	 * @return
	 * @throws Exception
	 */
	public static String returnSendLottery(String sendUserId, String lotteryId, String total) throws Exception {
		LotteryGift lotteryGift = new LotteryGift(lotteryId, total);
		MessageReturn messageReturn = new MessageReturn();
		// 从redis缓存中取出所需要的用户信息(如果不存在，返回是null，前台解析会报错吗？)
		PageData pd = new PageData();
		pd.put("USER_ID", sendUserId);
		PageData user = conversion.wechatinfoService.findHeadUrlByUserId(pd);
		lotteryGift.setUserId(sendUserId);
		lotteryGift.setUserName(user.getString("nickname"));
		lotteryGift.setPhotoUrl(user.getString("headimgurl"));
		lotteryGift.setText("恭喜贵宾" + user.getString("nickname") + "赢得了" + total + "圆红包");
		messageReturn.setMessage("success");
		messageReturn.setData(lotteryGift);
		messageReturn.setType(LOTTERYGIFT);
		messageReturn.setCode(successCode);
		Gson gson = new Gson();
		return gson.toJson(messageReturn);
	}

	public static List<String> randomReturn(String method) throws Exception {
		List<String> result_list = new ArrayList<String>();
		String result = "";
		PageData userInfo = new PageData();
		try {
			userInfo = conversion.wechatinfoService.findUserRandom(new PageData());

		} catch (Exception e) {
			logger.info("randomReturn-error"); //
			e.printStackTrace();
		}
		String userId = userInfo.getString("USER_ID");
		if (Tools.notEmpty(userId)) {
			if ("sendLottery".equals(method)) { // 发送中奖信息
				// text 统一从缓存中取
				result = returnSendLottery(userId, RedisUtil.getInstance(5).srandmember("RANDOM_LOTTERYID"),
						RedisUtil.getInstance(5).srandmember("RANDOM_TOTAL_MONEY"));
				result_list.add(result);
			} else if ("gift".equals(method)) {

				// giftId 缓存中随机取
				// String giftId =
				// RedisUtil.getInstance(5).srandmember("RANDOM_GIFTID");
				// if(giftId.equals("Domineering_LG1") ||
				// giftId.equals("Domineering_LG2") ||
				// giftId.equals("Domineering_LG3")){
				// result = giftReturn(giftId,
				// RedisUtil.getInstance(5).srandmember("RANDOM_TEXT"),
				// userInfo.getString("USER_ID"));
				// }else{
				// result = giftReturn(giftId, "",
				// userInfo.getString("USER_ID"));
				// }

				String result_gift = giftReturn("love", "", userInfo.getString("USER_ID"));
				result_list.add(result_gift);
				// 绑定爱情游艇发送中奖信息
				String result_red2 = returnSendLottery(userId, RedisUtil.getInstance(5).srandmember("RANDOM_LOTTERYID"),
						RedisUtil.getInstance(5).srandmember("RANDOM_TOTAL_MONEY"));
				result_list.add(result_red2);

			} else if ("redPacket".equals(method)) {
				// total 缓存中随机取
				result = redPacketReturn(userId, RedisUtil.getInstance(5).srandmember("RANDOM_TOTAL_MONEY"),
						"redpacketId", "isCouple");
				result_list.add(result);
			}
		}

		return result_list;
	}

	public static String RandomTextReturn() throws Exception {
		String text = "";
		PageData userInfo = new PageData();
		try {
			userInfo = conversion.wechatinfoService.findUserRandom(new PageData());

		} catch (Exception e) {
			logger.info("randomReturn-error"); //
			e.printStackTrace();
		}
		String userId = userInfo.getString("USER_ID");
		if (Tools.notEmpty(userId)) {
			text = giftReturn("Domineering_LG3", RedisUtil.getInstance(5).srandmember("RANDOM_TEXT"),
					userInfo.getString("USER_ID"));
		}

		return text;
	}

	/**
	 * 普通弹幕内置消息
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String RandomNormalTextReturn() throws Exception {
		String text = "";
		PageData userInfo = new PageData();
		try {
			userInfo = conversion.wechatinfoService.findUserRandom(new PageData());

		} catch (Exception e) {
			logger.info("RandomNormalTextReturn-error"); //
			e.printStackTrace();
		}
		String userId = userInfo.getString("USER_ID");
		if (Tools.notEmpty(userId)) {

			text = chatReturn(RedisUtil.getInstance(5).srandmember("RANDOM_TEXT"), null, userId);
		}

		return text;
	}

	public static PageData getLiveByReportNum(String liveId) throws Exception {
		PageData live = new PageData();
		live.put("liveid", liveId);
		PageData new_live = conversion.liveService.getLiveByReportNum(live);
		return new_live;
	}



	/**
	 * 从缓存中用户信息中 获取用户对应的房间号
	 * 
	 * @param userId
	 * @return
	 */
	public static String getRoomId(String userId) throws BusinessException {
		return RedisUtil.getInstance().hget(userId, "roomId");
	}

	/**
	 * 从缓存中用户信息中 获取用户对应的类型 1.代表APP用户 2.代表现场用户
	 * 
	 * @param userId
	 * @return
	 */

	public static String getUserType(String userId) throws BusinessException {
		return RedisUtil.getInstance().hget(userId, "type");
	}

	/**
	 * 切播
	 * 
	 * @param liveId
	 * @param videoUrl
	 * @return
	 * @throws Exception
	 */
	public static String changeVideoReturn(String liveId, String videoUrl) throws Exception {
		Video video = new Video();
		MessageReturn messageReturn = new MessageReturn();
		PageData pd = new PageData();
		pd.put("liveId", liveId);
		pd.put("videoUrl", videoUrl);
		conversion.liveService.changeLivePath(pd);
		video.setVideoUrl(videoUrl);
		messageReturn.setMessage("success");
		messageReturn.setData(video);
		messageReturn.setType("vote");
		messageReturn.setCode("200");
		Gson gson = new Gson();
		return gson.toJson(messageReturn);
	}

	public static String getCurrentLive(String liveId) throws Exception {
		String live_path = "";
		PageData pd = new PageData();
		pd.put("liveId", liveId);
		pd = conversion.liveService.getCurrentLive(pd);
		if (null != pd) {
			live_path = pd.getString("live_path");
		}
		return live_path;
	}

	// 签到信息处理
	public static String signReturn(String userId, String roomId, String type, String wish) throws Exception {
		logger.info("用户userId{" + userId + "} 房间roomId{" + roomId + "} 类型type{" + type + "}签到");
		MessageReturn messageReturn = new MessageReturn();
		LoginInfo signInfo = new LoginInfo();
		// userId等于空 时初始化大屏 建立连接
		if (null != userId && !"".equals(userId) && !"undefined".equals(userId)) {
			PageData pd = new PageData();
			pd.put("USER_ID", userId);
			try {
				pd = conversion.wechatinfoService.findHeadUrlByUserId(pd);
				if (pd != null) {
					String userName = pd.getString("nickname");
					String photoUrl = pd.getString("headimgurl");
					String gender = pd.getString("sex");
					signInfo.setUserId(userId);
					signInfo.setGender(gender);
					signInfo.setUserName(userName);
					signInfo.setPhotoUrl(photoUrl);
					signInfo.setWish(wish);
					pd.put("roomId", roomId);
					pd.put("type", type);
				}
			} catch (Exception e) {
				logger.error("sys sign error", e);
				e.printStackTrace();
			}
			signInfo.setType(type); // 1代表App用户 2代表现场用户
			logger.info("用户pd {" + pd + "}");
		} else {
			signInfo.setType("3"); // 3代表大屏
		}
		messageReturn.setData(signInfo);
		messageReturn.setCode(successCode);
		messageReturn.setMessage("success");

		messageReturn.setType(SIGN);
		Gson gson = new Gson();
		return gson.toJson(messageReturn);
	}

	// 打赏信息处理
	public static String emceeTipReturn(String userId, String roomId) throws Exception {
		logger.info("用户userId{" + userId + "} 房间roomId{" + roomId + "} 打赏司仪");
		MessageReturn messageReturn = new MessageReturn();
		EmceeTip emceeTip = new EmceeTip();
		if (Tools.notEmpty(userId)) {
			PageData pd = new PageData();
			pd.put("USER_ID", userId);
			pd = conversion.wechatinfoService.findHeadUrlByUserId(pd);
			String userName = pd.getString("nickname");
			String photoUrl = pd.getString("headimgurl");
			emceeTip.setUserId(userId);
			emceeTip.setUserName(userName);
			emceeTip.setPhotoUrl(photoUrl);

			messageReturn.setData(emceeTip);
			messageReturn.setCode(successCode);
			messageReturn.setMessage("success");
			messageReturn.setType(EMCEETIP);
		} else {
			messageReturn.setCode(paramsInvalid);
			messageReturn.setMessage("fail");

		}
		Gson gson = new Gson();
		return gson.toJson(messageReturn);
	}

	// 司仪遥控器信息处理
	public static String telecontrollerReturn(String roomId, PageData pd) throws Exception {
		logger.info(" 房间roomId{" + roomId + "}遥控器");
		MessageReturn messageReturn = new MessageReturn();
		Telecontroller telecontroller = new Telecontroller();
		telecontroller.setAction_code(pd.getString("action_code"));
		messageReturn.setData(telecontroller);
		messageReturn.setCode(successCode);
		messageReturn.setMessage("success");
		messageReturn.setType(TELECONTROLLER);
		Gson gson = new Gson();
		return gson.toJson(messageReturn);
	}

	// 抽奖获奖人员信息处理
	public static String winningReturn(String roomId, PageData pd) throws Exception {
		logger.info(" 房间roomId{" + roomId + "} 抽奖获奖人员信息");
		MessageReturn messageReturn = new MessageReturn();
		Winning winning = new Winning();
		String reward_userid = pd.getString("reward_userid");
		List<PageData> reward_user_list = new ArrayList<>();
		if (Tools.notEmpty(reward_userid)) {
			String[] reward_user = reward_userid.split(",");
			for (String userid : reward_user) {
				PageData user = new PageData();
				pd.put("USER_ID", userid);
				user = conversion.wechatinfoService.findHeadUrlByUserId(pd);
				reward_user_list.add(user);
			}
		}
		winning.setReward_name(pd.getString("reward_name"));
		winning.setReward_user_info(reward_user_list);

		messageReturn.setData(winning);
		messageReturn.setCode(successCode);
		messageReturn.setMessage("success");
		messageReturn.setType(WINNING);
		Gson gson = new Gson();
		return gson.toJson(messageReturn);
	}
	

	// 游戏获奖人员信息处理
	public static String gameRewardReturn(String roomId, PageData pd) throws Exception {
		logger.info(" 房间roomId{" + roomId + "} 游戏获奖人员信息");
		MessageReturn messageReturn = new MessageReturn();
		GameReward gameReward = new GameReward();
		String reward_userid = pd.getString("reward_userid");
		List<PageData> reward_user_list = new ArrayList<>();
		if (Tools.notEmpty(reward_userid)) {
			String[] reward_user = reward_userid.split(",");
			for (String userid : reward_user) {
				PageData user = new PageData();
				pd.put("USER_ID", userid);
				user = conversion.wechatinfoService.findHeadUrlByUserId(pd);
				reward_user_list.add(user);
			}
		}
		String reward_user_score = pd.getString("reward_user_score");
		if (Tools.notEmpty(reward_user_score)) {
			String[] user_score = reward_user_score.split(",");
			for(int i=0, len=reward_user_list.size(); i<len ; i++){
				reward_user_list.get(i).put("score", user_score[i]);
			}
		}
		
		gameReward.setGameCode(pd.getString("gameCode"));
		gameReward.setReward_user_info(reward_user_list);

		messageReturn.setData(gameReward);
		messageReturn.setCode(successCode);
		messageReturn.setMessage("success");
		messageReturn.setType(GAMEREWARD);
		Gson gson = new Gson();
		return gson.toJson(messageReturn);
	}

	/**
	 * 构造司仪打赏针对用户广播数据
	 * 
	 * @param liveId
	 * @param gameCode
	 * @return
	 */
	public static String returnEmcee(String liveId, String money_one, String money_two) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("money_one", money_one);
		data.put("money_two", money_two);
		result.put("data", data);
		result.put("code", successCode);
		result.put("message", "success");
		result.put("type", GAME_EMCEETIP);
		return JacksonMapper.toJsonString(result);
	}
	
	
	public static void main(String[] args) {
		PageData pd = RedisUtil.getInstance().hgetAllPd("xxxx");

		System.out.println(pd.size());
	}

}