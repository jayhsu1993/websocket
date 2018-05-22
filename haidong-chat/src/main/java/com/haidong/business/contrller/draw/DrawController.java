package com.haidong.business.contrller.draw;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.haidong.business.contrller.base.BaseController;
import com.huixin.framework.utils.AppUtil;
import com.huixin.framework.utils.Const;
import com.huixin.framework.utils.PageData;
import com.huixin.haidong.service.system.business.wechatinfo.WechatInfoService;
import com.huixin.system.entity.Page;

import net.sf.json.JSONObject;

/**
 * 
 * @author wuxiang
 * date:2016.8.4
 * 抽奖界面返回数据(来宾的姓名和头像)
 */
@Controller
public class DrawController extends BaseController{
	
	@Resource(name="wechatinfoService")
	private WechatInfoService wechatinfoService;
	
	@RequestMapping(value="/draw",method=RequestMethod.GET)
	@ResponseBody
	public Object draw(String liveId){
		logBefore(logger, "列表WechatInfo");
		JSONObject jsonObject = new JSONObject();
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		String callback = pd.getString("callback");
		try{
			List<PageData>	varList = wechatinfoService.listPart(pd);	//列出WechatInfo列表
			jsonObject.put("num", String.valueOf(varList.size()));
			jsonObject.put("data", varList);
			jsonObject.put("code", 200);
		} catch(Exception e){
			logger.error(e.toString(), e);
			map.put("message", e);
		}
		return callback+ "(" + jsonObject + ")";
	}
	
}
