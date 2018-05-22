package com.huixin.haidong.business.controller.game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.huixin.framework.utils.HttpClientUtil;
import com.huixin.framework.utils.LoggerUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huixin.framework.exception.BusinessException;
import com.huixin.framework.redis.RedisUtil;
import com.huixin.framework.utils.Const;
import com.huixin.framework.utils.PageData;
import com.huixin.framework.utils.PathUtil;
import com.huixin.framework.utils.Tools;
import com.huixin.framework.utils.qiniu.FileUploadQiNiu;
import com.huixin.framework.utils.twobarcode.LogoConfig;
import com.huixin.framework.utils.twobarcode.ZXingCodeUtil;
import com.huixin.framework.utils.twobarcode.ZXingConfig;
import com.huixin.haidong.business.base.BaseController;
import com.huixin.haidong.service.system.business.allwater.AllwaterService;
import com.huixin.haidong.service.system.business.dictionary.DictionaryService;
import com.huixin.haidong.service.system.business.game.LiveGameScoreService;
import com.huixin.haidong.service.system.business.game.LiveGameService;
import com.huixin.haidong.service.system.business.report.ReportService;
import com.huixin.haidong.service.system.business.vote.VoteService;
import com.huixin.haidong.service.system.business.wechatinfo.WechatInfoService;

import net.sf.json.JSONObject;
import redis.clients.jedis.Tuple;

/**
 * 类名称：GameController 创建人：system 创建时间：2016-08-30 针对大屏游戏请求，无拦截
 */
@Controller
@RequestMapping(value = "/game")
public class GameAPI extends BaseController {


	
	@Resource(name = "gameService")
	private LiveGameService gameService;

	@Resource(name = "livegamescoreService")
	private LiveGameScoreService livegamescoreService;

	@Resource(name = "allwaterService")
	public AllwaterService allwaterService;

	@Resource(name = "wechatinfoService")
	public WechatInfoService wechatInfoService;

	@Resource(name = "reportService")
	private ReportService reportService;

	@Resource(name = "dictionaryService")
	private DictionaryService dictionaryService;
	
	
	@Resource(name = "voteService")
	private VoteService voteService;

	/**
	 * 初始化游戏
	 * 
	 * @throws BusinessException
	 */
	@RequestMapping(value = "initGame", method = RequestMethod.GET)
	public @ResponseBody Object initGame() throws BusinessException {
		PageData pd = this.getPageData();
		LoggerUtil.getLogByLiveId(logger.getLog4jLogger(), pd.getString("liveId"));
		JSONObject jsonObject = new JSONObject();

		String liveId = pd.getString("liveId");
		String gameCode = pd.getString("gameCode");
		if (null == liveId || "".equals(liveId)) {
			throw new BusinessException("300", "初始化游戏失败,liveId不能为空!", pd);
		} else if (null == gameCode || "".equals(gameCode)) {
			throw new BusinessException("301", "初始化游戏失败,gameCode不能为空!", pd);
		} else {
			RedisUtil.getInstance(1).hset(liveId, "gameCode", pd.getString("gameCode"));
			RedisUtil.getInstance(1).hset(liveId, "status", "0"); // 0:表示游戏未开始
			jsonObject.put("message", "初始化游戏成功");
			jsonObject.put("code", 200);
			// 发广播通知用户
			boolean result = sendGameChat(liveId, gameCode, "1", putToken(pd.getString("USER_ID")));
			// 微信发客服消息通知用户
			SendMessage sendMessage = new SendMessage(liveId, gameCode);
			new Thread(sendMessage).start();

			if (result) {
				jsonObject.put("gameChat", true);
			} else {
				jsonObject.put("gameChat", false);
			}

		}
		logMessage(logger, "主线程执行完");
		return returnJson(pd, jsonObject);
	}

	/**
	 * 开始游戏
	 * 
	 * @throws BusinessException
	 */
	@RequestMapping(value = "beginGame", method = RequestMethod.GET)
	public @ResponseBody Object beginGame() throws BusinessException {
		PageData pd = this.getPageData();
		LoggerUtil.getLogByLiveId(logger.getLog4jLogger(), pd.getString("liveId"));
		logBefore(logger, "开始游戏--beginGame:" + pd);
		JSONObject jsonObject = new JSONObject();
		try {
			gameService.delete(pd);
			pd.put("status", 1);
			gameService.save(pd);
			livegamescoreService.delete(pd);
			RedisUtil.getInstance(2).del(pd.getString("liveId")); // 开始游戏时，先清空游戏分数数据
			RedisUtil.getInstance(1).hset(pd.getString("liveId"), "status", "1"); // 1:更改游戏状态
			jsonObject.put("message", "开始成功");
			jsonObject.put("code", 200);
			// 发通知给用户 2 表示游戏开始
			logBefore(logger, "游戏开始发通知--start:");
			String gameCode = pd.getString("gameCode");
			if(!("hdGame11".equals(gameCode) || "hdGame12".equals(gameCode))){
				sendGameChat(pd.getString("liveId"), pd.getString("gameCode"), "2", putToken(pd.getString("USER_ID")));
			}
			logBefore(logger, "游戏发通知结束--beginGame--end:");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			jsonObject.put("code", 500);
			jsonObject.put("message", "开始游戏出错");
		}
		return returnJson(pd, jsonObject);
	}

	private boolean sendGameChat(String liveId, String gameCode, String status, String token) {
		LoggerUtil.getLogByLiveId(logger.getLog4jLogger(), liveId);
		logger.info("发送游戏用户通知-->liveId:" + liveId + "-->gameCode" + gameCode);
		String hostList = RedisUtil.getInstance(5).get(Const.CHAT_LIST_GONG);
		String[] hosts = hostList.split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		map.put("liveId", liveId);
		map.put("gameCode", gameCode);
		map.put("status", status);
		try {
			for (int i = 0; i < hosts.length; i++) {
				String url = hosts[i] + "returnGameResult";
				result = HttpClientUtil.getHttp(url, token, map);
			}

			if ("success".equals(result)) {
				logger.info("发送游戏用户通知成功");
				return true;
			} else {
				logger.info("发送游戏用户通知失败");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发送游戏用户通知出错{}", e);
			return false;
		}
	}

	private void sendGameMessage(String liveId, String gameCode) {
		LoggerUtil.getLogByLiveId(logger.getLog4jLogger(), liveId);
		logger.info("发送游戏用户微信通知-->liveId:" + liveId + "-->gameCode" + gameCode);
		String host = RedisUtil.getInstance(5).get(Const.SIYI_GONG);
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		map.put("live_id", liveId);
		map.put("gameCode", gameCode);
		try {
			String url = host + "gameMessage";
			HttpClientUtil.getHttp(url, map);
			logger.info("发送游戏用户微信通知成功");

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发送游戏用户微信通知失败{}", e);
		}
	}

	/**
	 * 获取游戏分数
	 * 
	 * @throws BusinessException
	 */
	@RequestMapping(value = "scoreList", method = RequestMethod.GET)
	public @ResponseBody Object scoreList() {
		PageData pd = this.getPageData();
		LoggerUtil.getLogByLiveId(logger.getLog4jLogger(), pd.getString("liveId"));
		logBefore(logger, "获取游戏分数--scoreList:" + pd);
		JSONObject jsonObject = new JSONObject();
		String liveId = pd.getString("liveId");

		try {
			Set<Tuple> shakeList = RedisUtil.getInstance(2).zrevrangeWithScores(liveId, 0, 9);
			List returnList = setScoreList(shakeList);
			jsonObject.put("data", returnList);
			jsonObject.put("message", "获取游戏分数成功");
			jsonObject.put("code", 200);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			jsonObject.put("code", 500);
			jsonObject.put("message", "获取游戏分数出错!");
		}
		logBefore(logger, "获取游戏分数--scoreList--end:");
		return returnJson(pd, jsonObject);
	}

	/**
	 * 结束游戏(大屏先结束，再通知手机结束，分数不同步)
	 * 
	 * @throws BusinessException
	 */
	@RequestMapping(value = "endGame", method = RequestMethod.GET)
	public @ResponseBody Object endGame() {
		PageData pd = this.getPageData();
		LoggerUtil.getLogByLiveId(logger.getLog4jLogger(), pd.getString("liveId"));
		logBefore(logger, "结束游戏--endGame:" + pd);
		JSONObject jsonObject = new JSONObject();
		try {
			String liveId = pd.getString("liveId");
			RedisUtil.getInstance(1).hset(liveId,"status", "2");	//修改游戏状态为已完成
			String gameCode = RedisUtil.getInstance(1).hget(liveId, "gameCode");
			if (Tools.notEmpty(gameCode) && gameCode.equals("hdGame12")) {
				Set<Tuple> scoreList_red = RedisUtil.getInstance(2).zrevrangeWithScores(liveId + "_red", 0, -1);
				Set<Tuple> scoreList_blue = RedisUtil.getInstance(2).zrevrangeWithScores(liveId + "_blue", 0, -1);
				saveGameScore(liveId, gameCode, scoreList_red);
				saveGameScore(liveId, gameCode, scoreList_blue);
				RedisUtil.getInstance(2).del(liveId + "_red"); // 清除2号数据库中拔河游戏红方的排名记录
				RedisUtil.getInstance(2).del(liveId + "_blue"); // 清除2号数据库中拔河游戏蓝方的排名记录
				RedisUtil.getInstance(2).del(liveId + "_sum"); // 清除2号数据库中拔河游戏总分记录
				RedisUtil.getInstance(1).del(liveId); // 清除1号数据库中的游戏信息
				RedisUtil.getInstance(6).del(liveId + "_red");		//清除6号库中拔河比赛红队限定人数信息
				RedisUtil.getInstance(6).del(liveId + "_blue");		//清除6号库中拔河比赛蓝队限定人数信息
			} else {
				Set<Tuple> shakeList = RedisUtil.getInstance(2).zrevrangeWithScores(liveId, 0, -1);
				saveGameScore(liveId, gameCode, shakeList);
				RedisUtil.getInstance(2).del(liveId); // 清除2号数据库中摇一摇游戏的排名记录
				RedisUtil.getInstance(1).del(liveId); // 清除1号数据库中的游戏信息
			}
			jsonObject.put("message", "结束游戏成功");
			jsonObject.put("code", 200);
			logBefore(logger, "结束游戏--endGame:" + pd);
			// 发通知给用户 3 表示游戏结束
			sendGameChat(pd.getString("liveId"), pd.getString("gameCode"), "3", putToken(pd.getString("USER_ID")));
			logBefore(logger, "结束游戏--发送请求完成:" + pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			jsonObject.put("code", 500);
			jsonObject.put("message", "结束游戏出错");
		}
		return returnJson(pd, jsonObject);
	}
	
	/**
	 * 游戏结束流程调整(先发广播，在处理最后的分数)
	 * @return
	 */
	@RequestMapping(value = "endGameBefore", method = RequestMethod.GET)
	public @ResponseBody Object endGameBefore() {
		PageData pd = this.getPageData();
		LoggerUtil.getLogByLiveId(logger.getLog4jLogger(), pd.getString("liveId"));
		logBefore(logger, "结束游戏--endGameBefore:" + pd);
		JSONObject jsonObject = new JSONObject();
		try {
			String liveId = pd.getString("liveId");
			String gameCode = RedisUtil.getInstance(1).hget(liveId, "gameCode");
			RedisUtil.getInstance(1).hset(liveId,"status", "2");	//修改游戏状态为已完成
			
			jsonObject.put("message", "手机结束游戏成功");
			jsonObject.put("code", 200);
			logBefore(logger, "手机结束游戏--endGameBefore:" + pd);
			// 发通知给用户 3 表示游戏结束
			sendGameChat(pd.getString("liveId"), pd.getString("gameCode"), "3", putToken(pd.getString("USER_ID")));
			logBefore(logger, "手机结束游戏--发送请求完成:" + pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			jsonObject.put("code", 500);
			jsonObject.put("message", "手机结束游戏出错");
		}
		return returnJson(pd, jsonObject);
	}
	
	
	/**
	 * 大屏游戏结束，处理分数(保证手机与大屏分数一致)
	 * @return
	 */
	@RequestMapping(value = "endGameAfter", method = RequestMethod.GET)
	public @ResponseBody Object endGameAfter() {
		PageData pd = this.getPageData();
		LoggerUtil.getLogByLiveId(logger.getLog4jLogger(), pd.getString("liveId"));
		logBefore(logger, "大屏结束游戏--endGameAfter:" + pd);
		JSONObject jsonObject = new JSONObject();
		try {
			String liveId = pd.getString("liveId");
			String gameCode = RedisUtil.getInstance(1).hget(liveId, "gameCode");
			Set<Tuple> shakeList_Ten = RedisUtil.getInstance(2).zrevrangeWithScores(liveId, 0, 9);
			List returnList = setScoreList(shakeList_Ten);
			jsonObject.put("data", returnList);
			
			Set<Tuple> shakeList = RedisUtil.getInstance(2).zrevrangeWithScores(liveId, 0, -1);
			saveGameScore(liveId, gameCode, shakeList);
			RedisUtil.getInstance(2).del(liveId); // 清除2号数据库中游戏的排名记录
			RedisUtil.getInstance(1).del(liveId); // 清除1号数据库中的游戏信息
			
			jsonObject.put("message", "大屏结束游戏成功");
			jsonObject.put("code", 200);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			jsonObject.put("code", 500);
			jsonObject.put("message", "大屏结束游戏出错");
		}
		return returnJson(pd, jsonObject);
	}
	
	
	
	
	

	/**
	 * 游戏结束，保存游戏分数
	 * 
	 * @param liveId
	 * @param gameCode
	 * @param shakeList
	 * @throws Exception
	 */
	private void saveGameScore(String liveId, String gameCode, Set<Tuple> shakeList) throws Exception {
		logBefore(logger, "结束游戏--保存游戏分数 begin:");
		LoggerUtil.getLogByLiveId(logger.getLog4jLogger(), liveId);
		if (null != shakeList) {
			for (Tuple member : shakeList) {
				PageData gameResult = new PageData();
				gameResult.put("id", this.get32UUID());
				gameResult.put("gameCode", gameCode);
				gameResult.put("score", member.getScore());
				gameResult.put("liveId", liveId);
				gameResult.put("USER_ID", member.getElement());
				livegamescoreService.save(gameResult);
			}
		}
		logBefore(logger, "结束游戏--保存游戏分数 end:" );
	}

	/**
	 * 创建房间游戏二维码
	 * 
	 * @throws BusinessException
	 */
	@RequestMapping(value = "addCode", method = RequestMethod.GET)
	public @ResponseBody Object addCode() {
		JSONObject jsonObject = new JSONObject();
		PageData pd = this.getPageData();
		logBefore(logger, "addCode:" + pd);
		String liveCode = pd.getString("liveCode");
		if (StringUtils.isEmpty(liveCode)) {
			jsonObject.put("code", 500);
			jsonObject.put("message", "房间id为空");
		} else {
			// 生成二维码
			try {
				for (int i = 1; i <= 10; i++) {
					String gameCode = "hdGame" + i;
					/*
					 * jsonObject.put("liveCode", liveCode);
					 * jsonObject.put("gameCode", gameCode); String parms =
					 * java.net.URLEncoder.encode(jsonObject.toString(),"utf-8")
					 * ; String content = WxHttpUtils.OAUH_URL + "game?parms=" +
					 * parms + WxHttpUtils.NOT_OAUH_PARAMS;
					 */
					String h5url = dictionaryService.getValue(Const.H5URL);
					String content = h5url + "qrcode/getGameCode?id=" + liveCode + "&gameCode=" + gameCode;
					logMessage(logger, content);
					ZXingCodeUtil zp = new ZXingCodeUtil(); // 实例化二维码工具
					ZXingConfig zxingconfig = new ZXingConfig(); // 实例化二维码配置参数
					zxingconfig.setHints(zp.getDecodeHintType()); // 设置二维码的格式参数
					zxingconfig.setContent(content);// 设置二维码生成内容
					zxingconfig
							.setLogoPath(PathUtil.getClasspath() + Const.FILEPATHTWODIMENSIONCODE + gameCode + ".png"); // 设置Logo图片
					zxingconfig.setLogoConfig(new LogoConfig()); // Logo图片参数设置
					zxingconfig.setLogoFlg(true); // 设置生成Logo图片
					BufferedImage bim = zp.getQR_CODEBufferedImage(zxingconfig);// 生成二维码
					FileUploadQiNiu.uploadCompressFile(bim, "game/" + liveCode + "/" + gameCode);
					// Thread.sleep(500); // 缓冲
				}
				jsonObject.put("code", 200);
				jsonObject.put("message", "创建二维码成功");
			} catch (Exception e) {
				logger.error(e.toString(), e);
				jsonObject.put("code", 500);
				jsonObject.put("message", "创建二维码出错");
			}
		}
		return returnJson(pd, jsonObject);
	}

	/**
	 * 土豪排行榜
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public @ResponseBody Object list() {
		logBefore(logger, "土豪排行榜--list bgin");
		PageData pd = this.getPageData();
		JSONObject jsonObject = new JSONObject();
		try {
			List<PageData> list = allwaterService.listGiftSort(pd);
			jsonObject.put("data", list);
			jsonObject.put("code", 200);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			jsonObject.put("code", 500);
			jsonObject.put("message", "查询土豪排行榜出错");
		}
		logBefore(logger, "土豪排行榜--list end");
		return returnJson(pd, jsonObject);
	}

	/**
	 * 获取摇一摇分数排名结果
	 */
	@RequestMapping(value = "shakeResult", method = RequestMethod.GET)
	public @ResponseBody Object shakeReturn() {
		logBefore(logger, "获取摇一摇排行榜结果--shakeResult begin");
		PageData pd = this.getPageData();
		LoggerUtil.getLogByLiveId(logger.getLog4jLogger(), pd.getString("roomId"));
		JSONObject jsonObject = new JSONObject();
		String roomId = pd.getString("roomId");
		try {
			Set<Tuple> shakeList = RedisUtil.getInstance(2).zrevrangeWithScores(roomId, 0, 9);
			List returnList = setScoreList(shakeList);
			jsonObject.put("data", returnList);
			jsonObject.put("code", 200);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			jsonObject.put("code", 500);
			jsonObject.put("message", "获取摇一摇排行榜出错!");
		}
		logBefore(logger, "获取摇一摇排行榜结果--shakeResult end");
		return returnJson(pd, jsonObject);
	}
	
	
	
	/**
	 * 获取数钞票分数排名结果
	 */
	@RequestMapping(value = "countCashResult", method = RequestMethod.GET)
	public @ResponseBody Object countCashResult() {
		PageData pd = this.getPageData();
		logBefore(logger, pd.getString("roomId") + "获取数钞票排行榜结果--countCashResult begin");
		LoggerUtil.getLogByLiveId(logger.getLog4jLogger(), pd.getString("roomId"));
		JSONObject jsonObject = new JSONObject();
		String roomId = pd.getString("roomId");
		try {
			Set<Tuple> shakeList = RedisUtil.getInstance(2).zrevrangeWithScores(roomId, 0, 9);
			List returnList = setScoreList(shakeList);
			jsonObject.put("data", returnList);
			jsonObject.put("code", 200);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			jsonObject.put("code", 500);
			jsonObject.put("message", "获取数钞票排行榜出错!");
		}
		logBefore(logger, "获取数钞票排行榜结果--countCashResult end");
		return returnJson(pd, jsonObject);
	}
	

	/**
	 * 
	 * 获取当前游戏状态
	 * 
	 * @return
	 */
	@RequestMapping(value = "gameState/{roomId}", method = RequestMethod.GET)
	public @ResponseBody Object getGameState(@PathVariable("roomId") String roomId) {
		logBefore(logger, "获取当前游戏状态+房间Id/" + roomId + "  begin");
		LoggerUtil.getLogByLiveId(logger.getLog4jLogger(), roomId);
		PageData pd = this.getPageData();
		JSONObject jsonObject = new JSONObject();
		try {
			String status = RedisUtil.getInstance(1).hget(roomId, "status");
			jsonObject.put("data", status); // 0:游戏未开始 1:正在游戏中
			jsonObject.put("code", 200);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			jsonObject.put("code", 500);
			jsonObject.put("message", e);
		}
		logBefore(logger, "获取当前游戏状态+房间Id/" + roomId + "  end");
		return jsonObject;
	}

	/**
	 * 获取签到人数
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "report/{id}", method = RequestMethod.GET)
	public @ResponseBody Object get(@PathVariable("id") String id) throws Exception {
		logBefore(logger, "根据Report-ID查询" + id);
		LoggerUtil.getLogByLiveId(logger.getLog4jLogger(), id);
		JSONObject jsonObject = new JSONObject();
		PageData pd = this.getPageData();
		pd.put("liveid", id);
		int num = reportService.findReportNum(pd); // 根据ID读取
		pd.put("num", num);
		jsonObject.put("data", pd);
		jsonObject.put("code", 200);
		return returnJson(pd, jsonObject);
	}

	/**
	 * 获取拔河分数排名结果
	 */
	@RequestMapping(value = "tugOfWarResult", method = RequestMethod.GET)
	public @ResponseBody Object tugOfWarReturn() {
		logBefore(logger, "获取拔河分数排名结果--tugOfWarResult begin");
		PageData pd = this.getPageData();
		LoggerUtil.getLogByLiveId(logger.getLog4jLogger(), pd.getString("roomId"));
		JSONObject jsonObject = new JSONObject();
		String roomId = pd.getString("roomId");
		try {
			Set<Tuple> tugOfWarList_red = RedisUtil.getInstance(2).zrevrangeWithScores(roomId + "_red", 0, 4);
			Set<Tuple> tugOfWarList_blue = RedisUtil.getInstance(2).zrevrangeWithScores(roomId + "_blue", 0, 4);
			if (tugOfWarList_red.size() != 0 && tugOfWarList_blue.size() != 0) {
				List returnList_red = setScoreList(tugOfWarList_red);
				double totalScore_red = RedisUtil.getInstance(2).zscore(roomId + "_sum", "red");
				Long total_people_red = RedisUtil.getInstance(2).zcount(roomId + "_red", "0", "100000");
				if (total_people_red.equals(new Long(0))) {
					total_people_red = 1L;
				}
				double average_red = totalScore_red / total_people_red;

				List returnList_blue = setScoreList(tugOfWarList_blue);
				double totalScore_blue = RedisUtil.getInstance(2).zscore(roomId + "_sum", "blue");
				Long total_people_blue = RedisUtil.getInstance(2).zcount(roomId + "_blue", "0", "100000");
				if (total_people_blue.equals(new Long(0))) {
					total_people_blue = 1L;
				}
				double average_blue = totalScore_blue / total_people_blue;

				jsonObject.put("average_red", average_red);
				jsonObject.put("average_blue", average_blue);
				jsonObject.put("red_result", returnList_red);
				jsonObject.put("blue_result", returnList_blue);
				jsonObject.put("code", 200);
			} else if (tugOfWarList_red.size() == 0 && tugOfWarList_blue.size() != 0) {
				jsonObject.put("average_red", 0);
				jsonObject.put("red_result", new ArrayList());
				List returnList_blue = setScoreList(tugOfWarList_blue);
				double totalScore_blue = RedisUtil.getInstance(2).zscore(roomId + "_sum", "blue");
				Long total_people_blue = RedisUtil.getInstance(2).zcount(roomId + "_blue", "0", "100000");
				if (total_people_blue.equals(new Long(0))) {
					total_people_blue = 1L;
				}
				double average_blue = totalScore_blue / total_people_blue;
				jsonObject.put("average_blue", average_blue);
				jsonObject.put("blue_result", returnList_blue);

			} else if (tugOfWarList_blue.size() == 0 && tugOfWarList_red.size() != 0) {
				jsonObject.put("average_blue", 0);
				jsonObject.put("blue_result", new ArrayList());
				List returnList_red = setScoreList(tugOfWarList_red);
				double totalScore_red = RedisUtil.getInstance(2).zscore(roomId + "_sum", "red");
				Long total_people_red = RedisUtil.getInstance(2).zcount(roomId + "_red", "0", "100000");
				if (total_people_red.equals(new Long(0))) {
					total_people_red = 1L;
				}
				double average_red = totalScore_red / total_people_red;
				jsonObject.put("average_red", average_red);
				jsonObject.put("red_result", returnList_red);

			} else {
				jsonObject.put("average_red", 0);
				jsonObject.put("average_blue", 0);
				jsonObject.put("red_result", new ArrayList());
				jsonObject.put("blue_result", new ArrayList());
			}
			logBefore(logger, "获取拔河分数排名结果--tugOfWarResult end");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			jsonObject.put("code", 500);
			jsonObject.put("message", "获取拔河分数排行榜出错!");
		}

		return returnJson(pd, jsonObject);
	}

	/**
	 * 获取微信转发次数排名结果
	 */
	@RequestMapping(value = "wxShareResult", method = RequestMethod.GET)
	public @ResponseBody Object shareReturn() {
		logBefore(logger, "获取微信转发次数排名结果--wxShareResult");
		PageData pd = this.getPageData();
		JSONObject jsonObject = new JSONObject();
		String roomId = pd.getString("roomId");
		try {
			Set<Tuple> share_info = RedisUtil.getInstance(4).zrevrangeWithScores(roomId, 0, 19);
			List share_list = setScoreList(share_info);
			jsonObject.put("data", share_list);
			jsonObject.put("code", 200);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			jsonObject.put("code", 500);
			jsonObject.put("message", "获取微信转发次数排名结果出错!");
		}

		return returnJson(pd, jsonObject);
	}

	private List setScoreList(Set<Tuple> score_list) throws Exception {
		logBefore(logger, "获取游戏分数查询--setScoreList--begin:");
		List returnList = new ArrayList();
		if (null != score_list) {
			for (Tuple member : score_list) {
				Map returnData = new HashMap();
				PageData par = new PageData();
				par.put("USER_ID", member.getElement());
				PageData user = wechatInfoService.findHeadUrlByUserId(par);
				returnData.put("headimgurl", user.getString("headimgurl"));
				returnData.put("nickname", user.getString("nickname"));
				returnData.put("score", member.getScore());
				returnData.put("id", member.getElement());
				returnList.add(returnData);
			}
		}
		logBefore(logger, "获取游戏分数查询--setScoreList--end:");
		return returnList;
	}
	
	
	

	class SendMessage implements Runnable {
		private String liveId;
		private String gameCode;
		

		public SendMessage(String liveId, String gameCode) {
			super();
			this.liveId = liveId;
			this.gameCode = gameCode;
		}

		public void run() {
			logger.info("发送游戏用户微信通知-->liveId:" + liveId + "-->gameCode" + gameCode);
			String host = RedisUtil.getInstance(5).get(Const.SIYI_GONG);
			Map<String, Object> map = new HashMap<String, Object>();
			String result = "";
			map.put("live_id", liveId);
			map.put("gameCode", gameCode);
			try {
				String url = host + "gameMessage";
				result = HttpClientUtil.getHttp(url, map);
				
				if ("success".equals(result)) {
					logger.info("发送游戏用户微信通知成功");
				} else {
					logger.info("发送游戏用户微信通知失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("发送游戏用户微信通知失败{}", e);
			}

		}

	}
	
	/**
	 * 获取节目投票结果
	 */
	@RequestMapping(value = "VoteResult", method = RequestMethod.GET)
	@ResponseBody
	public  Object VoteResult() {
		PageData pd = this.getPageData();
		logBefore(logger, pd.getString("roomId") + "获取节目投票排行榜--VoteResult begin");
		LoggerUtil.getLogByLiveId(logger.getLog4jLogger(), pd.getString("roomId"));
		JSONObject jsonObject = new JSONObject();
		String roomId = pd.getString("roomId");
		try {
			Set<Tuple> vote_list = RedisUtil.getInstance(2).zrevrangeWithScores(roomId + "_vote", 0, -1);
			List returnList = setVoteList(vote_list);
			jsonObject.put("data", returnList);
			jsonObject.put("code", 200);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			jsonObject.put("code", 500);
			jsonObject.put("message", "获取节目投票排行榜出错!");
		}
		logBefore(logger, "获取节目投票排行榜--VoteResult end");
		return returnJson(pd, jsonObject);
	}
	
	private List setVoteList(Set<Tuple> score_list) throws Exception {
		logBefore(logger, "获取投票排名查询--setVoteList--begin:");
		List returnList = new ArrayList();
		if (null != score_list) {
			for (Tuple member : score_list) {
				Map returnData = new HashMap();
				returnData.put("score", member.getScore());
				returnData.put("vote_id", member.getElement());
				returnList.add(returnData);
			}
		}
		logBefore(logger, "获取投票排名查询--setVoteList--end:");
		return returnList;
	}
	
	
	/**
	 * 初始化投票
	 * 
	 * @throws BusinessException
	 */
	@RequestMapping(value = "initVote", method = RequestMethod.GET)
	public @ResponseBody Object initVote() throws BusinessException {
		PageData pd = this.getPageData();
		LoggerUtil.getLogByLiveId(logger.getLog4jLogger(), pd.getString("liveId"));
		JSONObject jsonObject = new JSONObject();

		String liveId = pd.getString("liveId");
		String gameCode = pd.getString("gameCode");
		if (null == liveId || "".equals(liveId)) {
			throw new BusinessException("300", "初始化投票失败,liveId不能为空!", pd);
		} else if (null == gameCode || "".equals(gameCode)) {
			throw new BusinessException("301", "初始化投票失败,gameCode不能为空!", pd);
		} else {
			RedisUtil.getInstance(1).hset(liveId, "gameCode", gameCode);
			RedisUtil.getInstance(1).hset(liveId, "status", "0"); // 0:表示游戏未开始
			try {
				pd.put("live_id", liveId);
				List<PageData> vote_list = voteService.vote_list(pd);
				jsonObject.put("vote_list", vote_list);
			} catch (Exception e) {
				logger.error("获取投票列表出错!");
				e.printStackTrace();
			}
			jsonObject.put("message", "初始化投票成功");
			jsonObject.put("code", 200);
			// 发广播通知用户
			boolean result = sendGameChat(liveId, gameCode, "1", putToken(pd.getString("USER_ID")));

			if (result) {
				jsonObject.put("voteChat", true);
			} else {
				jsonObject.put("voteChat", false);
			}

		}
		logMessage(logger, "主线程执行完");
		return returnJson(pd, jsonObject);
	}

	
	/**
	 * 结束投票(将投票成绩更新到数据库里)
	 * 
	 * @throws BusinessException
	 */
	@RequestMapping(value = "endVote", method = RequestMethod.GET)
	public @ResponseBody Object endVote() {
		PageData pd = this.getPageData();
		LoggerUtil.getLogByLiveId(logger.getLog4jLogger(), pd.getString("liveId"));
		logBefore(logger, "结束投票--endVote:" + pd);
		JSONObject jsonObject = new JSONObject();
		try {
			String liveId = pd.getString("liveId");
			RedisUtil.getInstance(1).hset(liveId,"status", "2");	//修改投票状态为已完成
			String gameCode = RedisUtil.getInstance(1).hget(liveId, "gameCode");
			Set<Tuple> voteList = RedisUtil.getInstance(2).zrevrangeWithScores(liveId + "_vote", 0, -1);
			saveVoteScore(liveId, voteList);
//			RedisUtil.getInstance(2).del(liveId + "_vote"); 	//投票排名不清除，以免有多轮投票
			RedisUtil.getInstance(1).del(liveId); // 清除1号数据库中的游戏信息
		
		
			jsonObject.put("message", "结束投票");
			jsonObject.put("code", 200);
			logBefore(logger, "结束投票--发送请求开始:" + pd);
			// 发通知给用户 3 表示游戏结束
			sendGameChat(pd.getString("liveId"), pd.getString("gameCode"), "3", putToken(pd.getString("USER_ID")));
			logBefore(logger, "结束投票--发送请求完成:" + pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			jsonObject.put("code", 500);
			jsonObject.put("message", "结束投票出错");
		}
		return returnJson(pd, jsonObject);
	}

	private void saveVoteScore(String liveId, Set<Tuple> voteList) throws Exception {
		logBefore(logger, "结束投票--保存投票结果 begin:");
		LoggerUtil.getLogByLiveId(logger.getLog4jLogger(), liveId);
		if (null != voteList) {
			for (Tuple member : voteList) {
				PageData voteResult = new PageData();
				voteResult.put("count", member.getScore());
				voteResult.put("live_id", liveId);
				voteResult.put("id", member.getElement());
				voteService.updateVoteCount(voteResult);
			}
		}
		logBefore(logger, "结束投票--保存投票结果 end:" );
		
	}
	

}
