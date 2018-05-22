package com.huixin.haidong.serice.impl;

import com.alibaba.druid.util.StringUtils;
import com.huixin.data.entity.Report;
import com.huixin.framework.base.ResponseResult;
import com.huixin.framework.base.RestResultGenerator;
import com.huixin.framework.enums.LiveStatus;
import com.huixin.framework.redis.RedisUtil;
import com.huixin.framework.utils.Const;
import com.huixin.framework.utils.PageData;
import com.huixin.framework.utils.PoolingHttpClient;
import com.huixin.framework.utils.UuidUtil;
import com.huixin.framework.utils.qiniu.FileUploadQiNiu;
import com.huixin.haidong.service.system.business.dictionary.DictionaryService;
import com.huixin.haidong.service.system.business.live.LiveService;
import com.huixin.haidong.service.system.business.report.ReportDao;
import com.huixin.haidong.service.system.service.WechatInfoService;
import com.huixin.system.entity.Page;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huixin.haidong.service.system.business.wechatinfo.WechatInfoDao;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * WechatInfoServiceImpl类
 *
 * @author Lance
 * @data 2017/3/1
*/
@Service("wechatinfoServiceimpl")
public class WechatInfoServiceImpl implements WechatInfoService {
	private static final Logger LOGGER = LoggerFactory.getLogger(WechatInfoServiceImpl.class);

	private static final String GAME_SHAKE = "hdGame11";
	private static final String GAME_SIGN = "sign";
	private static final String GAME_VOTE = "vote";

	@Autowired
	private WechatInfoDao wechatInfodao;

	@Autowired
	private ReportDao reportDao;

	@Resource(name = "dictionaryService")
	private DictionaryService dictionaryService;

	@Resource(name = "liveService")
	private LiveService liveService;

	public ResponseResult<Map<String, Object>> userSignIn(String liveid, String userId) throws Exception {
		Map<String, Object> map = new HashedMap();
		map.put("liveId", liveid);
		//获取直播间状态---
		String gameCode = RedisUtil.getInstance(1).hget(liveid, "gameCode");
		String status = RedisUtil.getInstance(1).hget(liveid, "status");
		map.put("status", status);
		//缓存状态为空,直播未开始或者已结束
		if(StringUtils.isEmpty(gameCode)){
			map.put("liveStatus", LiveStatus.LIVE_END.getCode());
			return RestResultGenerator.genResult(map,"直播未开始或者已结束");
		}else if(GAME_SIGN.equals(gameCode)){
			map.put("liveStatus", LiveStatus.LIVE_START.getCode());
			return RestResultGenerator.genResult(map, "直播间已开启签到，前往填写签到信息页");
		}else if(GAME_SHAKE.equals(gameCode)){
			map.put("gameCode", gameCode);
			//摇一摇
			map.put("gameType", "300");
			map.put("liveStatus", LiveStatus.LIVE_GAME.getCode());
			broadcastGame(userId,liveid);
			return RestResultGenerator.genResult(map, "直播间正在摇一摇游戏互动");
		}else if(GAME_VOTE.equals(gameCode)){
			map.put("gameCode", gameCode);
			//投票互动
			map.put("gameType", "vote");
			map.put("liveStatus", LiveStatus.LIVE_GAME.getCode());
			return RestResultGenerator.genResult(map, "直播间正在进行投票互动");
		}else {
			PageData pd = new PageData();
			pd.put("name","H5URL");
			map.put("gameCode", gameCode);
			//H5页面游戏
			map.put("liveStatus", LiveStatus.LIVE_GAME.getCode());
			map.put("gameType", "200");
			map.put("gameUrl", RedisUtil.getInstance(5).get(Const.H5URL) + "game?liveId=" + liveid + "&userId=" + userId + "&gameCode=" + gameCode);
			broadcastGame(userId, liveid);
			return RestResultGenerator.genResult(map, "直播间正在游戏互动，前往游戏页");
		}
	}

	public String registerReport(Report report){
		try {
			String result = "success";
			PageData newReport = new PageData();
			newReport.put("liveid",report.getLiveid());
			newReport.put("userId", report.getUserId());
			List<PageData> list = reportDao.listReportByUserIdAndLiveid(newReport);
			//判断重复签到
			if(list.size() > 0){
				return "checked";
			}
			PageData wechatinfo = new PageData();
			wechatinfo.put("USER_ID", report.getUserId());
			wechatinfo = wechatInfodao.findByUserId(wechatinfo);

			newReport.put("reheadimgurl", getHeadImgUrl(report.getReheadimgurl(), wechatinfo, report.getUserId()));
			newReport.put("unionid",UuidUtil.get32UUID());
			newReport.put("create_time",new Date());
			newReport.put("wish", report.getWish());
			newReport.put("state", 1);
			newReport.put("renickname", report.getRenickname());
			reportDao.save(newReport);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(report.toString() + "--->保存签到信息出错--->" + e.getMessage());
			return "error";
		}
	}


	/*
	 * 列表
	 */
	public List<PageData> list(Page page) throws Exception {
		return wechatInfodao.list(page);
	}

	private String uploadHeadImg(String headimgurl, String userid){
		try {
			//上传头像
			String imgResult = FileUploadQiNiu.updateAppFile(headimgurl, userid,
					UuidUtil.get32UUID());
//			String qiniu = dictionaryService.getValue(Const.QINIUHOSTPATH);
			return imgResult;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.debug(headimgurl + " " + userid + "--->上传头像出错" + e.getMessage());
			return "";
		}
	}

	/**
	 * 请求chat项目，发广播
	 * @throws Exception
	 */
	private String broadcastGame(String userId, String liveId) throws Exception{
		String chaturl = dictionaryService.getValue(Const.CHATURL);
		String url = chaturl + "gamePeople/" + userId +"/" + liveId;
		return PoolingHttpClient.httpGet(url);
	}

	/**
	 * get存储头像地址
	 * @param headimgurl
	 * @param wechatinfo
	 * @param userid
	 * @return
	 */
	private String getHeadImgUrl(String headimgurl, PageData wechatinfo, String userid){
		String result = "";
		//头像空,使用用户默认头像
		if(StringUtils.isEmpty(headimgurl)){
			//wechatinfo是七牛图片地址
			if(wechatinfo.getString("headimgurl").contains("http://ocau360tj.bkt.clouddn.com/")){
				result = wechatinfo.getString("headimgurl").replace("http://ocau360tj.bkt.clouddn.com/", "");
			}
		}else {
			if(headimgurl.contains("data:image/jpeg;base64,") || headimgurl.contains("data:image/png;base64,") ||
					headimgurl.contains("data:image/jpg;base64,")){
				result = uploadHeadImg(headimgurl, userid);
			}else {
				result = headimgurl.replace("http://ocau360tj.bkt.clouddn.com/", "");
			}
		}
		return result;
	}
}
