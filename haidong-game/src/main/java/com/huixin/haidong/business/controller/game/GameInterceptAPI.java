package com.huixin.haidong.business.controller.game;

import javax.annotation.Resource;

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
import com.huixin.haidong.business.base.BaseController;
import com.huixin.haidong.service.system.business.dictionary.DictionaryService;
import com.huixin.haidong.service.system.business.wechatinfo.WechatInfoService;

import net.sf.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;

/**
 * 针对app用户游戏请求，带拦截
 * 
 * @author wuxiang date:2016.11.16
 *
 */

@Controller
@RequestMapping(value = "/gameIntercept")
public class GameInterceptAPI extends BaseController {
	@Resource(name = "wechatinfoService")
	public WechatInfoService wechatInfoService;
	@Resource(name = "dictionaryService")
	private DictionaryService dictionaryService;

	/**
	 * 跳转到摇一摇页面
	 * @return
	 */
	@RequestMapping(value = "initIntercept", method = RequestMethod.GET)
	public ModelAndView init() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("business/game/gameload");
		return mv;
	}

	/**
	 * 根据ID查询
	 * 
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/join/{id}", method = RequestMethod.GET)
	public @ResponseBody Object get(@PathVariable("id") String roomId) throws BusinessException {
		logBefore(logger, "根据RoomID查询当前游戏" + roomId);
		JSONObject jsonObject = new JSONObject();
		PageData pd = this.getPageData();
		try {
			String gameCode = RedisUtil.getInstance(1).hget(roomId, "gameCode");
			if (!StringUtils.isEmpty(gameCode)) {
				PageData user = wechatInfoService.findByUserIdUnionid(pd); // 根据ID读取
				if (null != user) {
					if ("hdGame11".equals(gameCode)) {
						jsonObject.put("data", null); // 摇一摇游戏
						jsonObject.put("code", 300); // 300代表是摇一摇游戏
						wechatInfoService.broadcastGame(pd.getString("USER_ID"));
					} else {
						String h5url = dictionaryService.getValue(Const.H5URL);
						jsonObject.put("data", h5url + "game" + "?userId=" + pd.getString("USER_ID") + "&gameCode="
								+ gameCode + "&liveId=" + roomId);
						jsonObject.put("code", 200);
						wechatInfoService.broadcastGame(pd.getString("USER_ID"));
					}
				} else {
					jsonObject.put("code", 202);
				}

			} else {
				jsonObject.put("code", 201);
			}
		} catch (Exception e) {
			throw new BusinessException("GAME_ERROR_002", "进入游戏出错啦~", pd);
		}
		return jsonObject;
	}

	/**
	 * 提交摇一摇游戏分数，进行排名
	 * 
	 * @throws BusinessException
	 */
	@RequestMapping(value = "shakeScore", method = RequestMethod.GET)
	public void shakeSort() throws BusinessException {
		logBefore(logger, "摇一摇排行榜");
		PageData pd = this.getPageData();
		String roomId = pd.getString("roomId");
		Double score = Double.parseDouble(pd.getString("score"));
		String userId = pd.getString("USER_ID");
		try {
			String gameCode = RedisUtil.getInstance(1).hget(roomId, "gameCode");
			String status = RedisUtil.getInstance(1).hget(roomId, "status");
			if (!StringUtils.isEmpty(gameCode) && "hdGame11".equals(gameCode) && "1".equals(status)) {
				RedisUtil.getInstance(2).zincrby(roomId, score, userId);
			}
		} catch (Exception e) {
			throw new BusinessException("GAME_ERROR_001", "摇一摇出错啦~", pd);
		}
	}

}
