package com.huixin.haidong.business.controller.game;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.huixin.framework.base.ResponseResult;
import com.huixin.framework.base.RestResultGenerator;
import com.huixin.framework.redis.RedisUtil;
import com.huixin.haidong.service.system.service.LiveService;
import com.huixin.haidong.service.system.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.huixin.framework.utils.PageData;
import com.huixin.framework.utils.Tools;
import com.huixin.haidong.business.base.BaseController;

@Controller
@RequestMapping(value = "/box")
public class BoxLoginAPI extends BaseController {

	@Resource(name = "liveServiceImpl")
	private LiveService liveService;

	@Resource(name = "reportServiceImpl")
	private ReportService reportService;

	/**
	 * 登录盒子
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = this.getModelAndView();
		HttpSession session = request.getSession();
		String roomId = (String) session.getAttribute("roomId");
		String mode_type = (String) session.getAttribute("mode_type");
		try {
			PageData live_config = liveService.queryLiveResource(roomId);
			if(null != live_config){
				mv.addObject("live_config", live_config);
			}
		} catch (Exception e) {
			logger.error("获取直播间定制参数出错");
			e.printStackTrace();
		}
		if(Tools.notEmpty(mode_type)){
			if("0".equals(mode_type)){
				mv.setViewName("business/game/indexnew");	//0跳转默认婚礼版首页
			}else if("1".equals(mode_type)){
				mv.setViewName("business/game/business");	//1跳转商务版首页
			}
		}else{
			logger.error("用户模式类型信息缺失");
		}
		
		return mv;
	}

	/**
	 * 登录盒子
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult<String> boxLogin(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		PageData pd = this.getPageData();
		logBefore(logger, "登录大屏互动:" + pd);
		String result = reportService.signIn(pd, session);
		if ("success".equals(result)) {
			result = "验证通过,登录成功";
			return RestResultGenerator.genResult(result, "登录成功");
		} else {//登录失败
			return RestResultGenerator.genErrorResult(result);
		}
	}
	
	
	/**
	 * 直接登录大屏
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/directLogin", method = RequestMethod.GET)
	public ModelAndView directLogin(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		logBefore(logger, "直接登录大屏互动:" + pd);
		String result = reportService.signIn(pd, session);
		if ("success".equals(result)) {
			mv.setViewName("redirect:/box/init");
		}else{
			mv.addObject("error_info", result);
			mv.setViewName("business/game/login");
		}
		return mv;
	}
	
	/**
	 * 清除缓存的gameCode和状态
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/clearRedis", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult<String> clearRedis(HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		PageData pd = this.getPageData();
		String roomId = pd.getString("roomId");
		logBefore(logger, "清除大屏当前状态:" + pd);
		try {
			RedisUtil.getInstance(1).del(roomId);
			result = "状态清除成功";
			return RestResultGenerator.genResult(result, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return RestResultGenerator.genErrorResult(result);
		}

	}
	
	
	/**
	 * 加载企业互动定制功能
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/getConfig", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult<Map> getConfig(HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		PageData pd = this.getPageData();
		logBefore(logger, "加载企业互动定制功能:" + pd);
		try {
			
			Map config_result = liveService.getDesignConfig(pd);
			
			return RestResultGenerator.genResult(config_result, "加载企业互动定制功能成功");
		} catch (Exception e) {
			e.printStackTrace();
			return RestResultGenerator.genErrorResult(result);
		}

	}
	

	/**
	 * 	去企业版登录页面
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/to_login", method = RequestMethod.GET)
	public ModelAndView to_login(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("business/game/businesslogin");	//企业版登录页面
		return mv;
	}
	
	
}
