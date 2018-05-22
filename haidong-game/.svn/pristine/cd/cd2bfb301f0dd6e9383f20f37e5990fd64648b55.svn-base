package com.huixin.haidong.business.controller.game;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huixin.framework.utils.PageData;
import com.huixin.haidong.business.base.BaseController;
import com.huixin.haidong.service.system.business.game.GameGiftService;
import com.huixin.system.entity.Page;

import net.sf.json.JSONObject;

/** 
 * 类名称：GameGiftController
 * 创建人：system
 * 创建时间：2016-10-21
 */
@Controller
@RequestMapping(value="/gamegift")
public class GameGiftAPI extends BaseController {
	
	@Resource(name="gamegiftService")
	private GameGiftService gamegiftService;

	/**
	 * 新增
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Object add() {
		PageData pd = this.getPageData();
		logBefore(logger, "新增GameGift:" + pd);
		JSONObject jsonObject = new JSONObject();
		try {
			pd.put("id", this.get32UUID());	//主键
			gamegiftService.save(pd);
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
		logBefore(logger, "删除GameGift-id="+ id);
		PageData pd = new PageData();
		JSONObject jsonObject = new JSONObject();
		try {
			gamegiftService.delete(pd);
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
		logBefore(logger, "修改GameGift-id="+ id);
		PageData pd = this.getPageData();
		JSONObject jsonObject = new JSONObject();
		try {
			gamegiftService.edit(pd);
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
        logBefore(logger, "根据GameGift-ID查询" + id);
		JSONObject jsonObject = new JSONObject();
		PageData pd = this.getPageData();
		try {
			pd.put("id", id);
			pd = gamegiftService.findById(pd); // 根据ID读取
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
		logBefore(logger, "列表GameGift");
		PageData pd = new PageData();
		JSONObject jsonObject = new JSONObject();
		pd = this.getPageData();
		page.setPd(pd);
		try {
			List<PageData> list = gamegiftService.list(page);
			jsonObject.put("data", list);
			jsonObject.put("code", 200);
		} catch(Exception e){
			logger.error(e.toString(), e);
			jsonObject.put("message", e);
		}
		return jsonObject;
	}
	
	
}
