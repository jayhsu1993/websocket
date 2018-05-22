package com.huixin.haidong.business.controller.game;

import java.util.List;

import javax.annotation.Resource;

import com.huixin.framework.base.ResponseResult;
import com.huixin.framework.base.RestResultGenerator;
import com.huixin.haidong.service.system.business.game.LiveGameScoreService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huixin.framework.utils.PageData;
import com.huixin.haidong.business.base.BaseController;
import com.huixin.haidong.service.system.business.game.LiveGameService;
import com.huixin.system.entity.Page;

import net.minidev.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;

/** 
 * 类名称：LiveGameController
 * 创建人：system
 * 创建时间：2017-02-15
 */
@Controller
@RequestMapping(value="/livegame")
public class LiveGameAPI extends BaseController {
	
	@Resource(name="gameService")
	private LiveGameService livegameService;
	@Resource(name="livegamescoreService")
	private LiveGameScoreService liveGameScoreService;

	/**
	 * 跳转直播页面
	 * @throws Exception
	 */
	@RequestMapping(value = "/initLiveGame", method = RequestMethod.GET)
	public ModelAndView init() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("business/game/live");
		return mv;
	}

	/**
	 * 跳转直播页面
	 * @throws Exception
	 */
	@RequestMapping(value = "/initPrize", method = RequestMethod.GET)
	public ModelAndView initPrize() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("business/game/prize");
		return mv;
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Object add() {
		PageData pd = this.getPageData();
		logBefore(logger, "新增LiveGame:" + pd);
		JSONObject jsonObject = new JSONObject();
		try {
			pd.put("id", this.get32UUID());	//主键
			livegameService.save(pd);
			jsonObject.put("message", "新增成功");
			jsonObject.put("code", 200);
		} catch (Exception e) {
			jsonObject.put("message", e.getMessage());
			logger.error(e.toString(), e);
		} 
		return jsonObject;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public @ResponseBody Object delete(@PathVariable("id") String id) {
		logBefore(logger, "删除LiveGame-id="+ id);
		PageData pd = new PageData();
		JSONObject jsonObject = new JSONObject();
		try {
			livegameService.delete(pd);
			jsonObject.put("message", "删除信息成功");
			jsonObject.put("code", 200);
		} catch (Exception e) {
			jsonObject.put("message", e.getMessage());
			logger.error(e.toString(), e);
		} 
		return jsonObject;
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public @ResponseBody Object edit(@PathVariable("id") String id) {
		logBefore(logger, "修改LiveGame-id="+ id);
		PageData pd = this.getPageData();
		JSONObject jsonObject = new JSONObject();
		try {
			livegameService.edit(pd);
			jsonObject.put("message", "更新信息成功");
			jsonObject.put("code", 200);
		} catch (Exception e) {
			jsonObject.put("message", e.getMessage());
			logger.error(e.toString(), e);
		} 
		return jsonObject;
	}
	
	/**
	 * 根据ID查询
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public @ResponseBody Object get(@PathVariable("id") String id) {
        logBefore(logger, "根据LiveGame-ID查询" + id);
		JSONObject jsonObject = new JSONObject();
		PageData pd = this.getPageData();
		try {
			pd.put("id", id);
			pd = livegameService.findById(pd); // 根据ID读取
			jsonObject.put("data", pd);
			jsonObject.put("code", 200);
		} catch(Exception e){
			logger.error(e.toString(), e);
			jsonObject.put("message", e);
		}
		return jsonObject;
	}
	
	/**
	 * 查询列表
	 */
	@RequestMapping(method = RequestMethod.PATCH)
	public @ResponseBody Object list(Page page) {
		logBefore(logger, "列表LiveGame");
		PageData pd = new PageData();
		JSONObject jsonObject = new JSONObject();
		pd = this.getPageData();
		page.setPd(pd);
		try {
			List<PageData> list = livegameService.list(page);
			jsonObject.put("data", list);
			jsonObject.put("code", 200);
		} catch(Exception e){
			logger.error(e.toString(), e);
			jsonObject.put("message", e);
		}
		return jsonObject;
	}

	/**
	 * 获取游戏最高分
	 * @param gameCode
	 * @return
	 */
	@RequestMapping(value = "/getGameHightScore/{gameCode}")
	@ResponseBody
	public ResponseResult<Integer> getGameHightScore(@PathVariable("gameCode") String gameCode){
		try {
			int gameHeightScore = liveGameScoreService.getGameHeightScore(gameCode);
			return RestResultGenerator.genResult(gameHeightScore, "获取最高分成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取最高分出错", e);
			return RestResultGenerator.genErrorResult("获取最高分出错");
		}
	}

	
	
}
