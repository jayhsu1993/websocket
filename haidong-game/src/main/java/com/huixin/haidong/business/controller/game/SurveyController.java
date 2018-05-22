package com.huixin.haidong.business.controller.game;

import com.huixin.framework.base.ResponseResult;
import com.huixin.framework.base.RestResultGenerator;
import com.huixin.framework.utils.*;
import com.huixin.haidong.business.base.BaseController;
import com.huixin.haidong.service.system.business.draw.DrawService;
import com.huixin.haidong.service.system.business.prize.PrizeService;
import com.huixin.haidong.service.system.business.survey.SurveyService;
import com.huixin.system.entity.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * 类名称：SurveyController
 * 创建人：system
 * 创建时间：2017-03-16
 */
@RestController
@RequestMapping(value="/survey")
public class SurveyController extends BaseController {
	
	@Resource(name="surveyService")
	private SurveyService surveyService;
	
	@Resource(name="prizeService")
	private PrizeService prizeService;
	
	@Resource(name="drawService")
	private DrawService drawService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ResponseResult<List<PageData>> list(Page page){
		logBefore(logger, "列表Survey");
		PageData pd = new PageData();
		List<PageData>	varList = new ArrayList<PageData>();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			varList = surveyService.list(page);
		} catch(Exception e){
			logger.error(e.toString(), e);
			RestResultGenerator.genErrorResult("获取问题信息失败");
		}
		return RestResultGenerator.genResult(varList, "获取问题信息成功");
	}
	
	
	/*
	 * 列表显示获奖内定名单
	 */

	@RequestMapping(value="/list_prize")
	public ResponseResult<List<PageData>> list_prize(){
		logBefore(logger, "列表Survey");
		PageData pd = new PageData();
		List<PageData>	varList = new ArrayList<PageData>();
		try{
			pd = this.getPageData();
			varList  = prizeService.list(pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
			return RestResultGenerator.genErrorResult("获取内定奖项失败");
		}
		return RestResultGenerator.genResult(varList, "获取内定奖项成功");
	}
	
	/*
	 * 获取全场抽奖名单名单
	 * 前台传live_id
	 */

	@RequestMapping(value="/list_draw")
	public ResponseResult<String[]> list_draw(){
		logBefore(logger, "获取全场抽奖名单名单");
		PageData pd = this.getPageData();
		try{
			PageData people = drawService.findById(pd);
			
			if(null != people){
				String people_name = people.getString("people_name");
				String[] names = people_name.split(",");
				return RestResultGenerator.genResult(names, "获取全场抽奖名单成功");
			}else{
				return RestResultGenerator.genErrorResult("未上传全场抽奖名单");
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
			return RestResultGenerator.genErrorResult("获取全场抽奖名单失败");
		}
	}
}
