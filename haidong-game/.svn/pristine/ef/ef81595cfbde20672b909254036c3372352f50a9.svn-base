package com.huixin.haidong.business.base;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.huixin.framework.redis.RedisUtil;
import com.huixin.framework.utils.Const;
import com.huixin.framework.utils.Logger;
import com.huixin.framework.utils.PageData;
import com.huixin.framework.utils.Tools;
import com.huixin.framework.utils.UuidUtil;
import com.huixin.framework.utils.token.Jwt;
import com.huixin.haidong.service.system.business.log.LogService;
import com.huixin.system.entity.Page;

import net.sf.json.JSONObject;


public class BaseController {

	protected Logger logger = Logger.getLogger(this.getClass());

	// 成功
	protected String code = "00";

	private static final long serialVersionUID = 6357869213649815390L;

	@Resource(name="logService")
	private LogService logService;
	
	/**
	 * 
	 * @param action 操作
	 * @param user_id
	 * @param ip Ip
	 * @param excetion 异常信息
	 * @param type 1.微信登录,2.手机登录,3:退出,4:pc注册登录商城
	 * @throws Exception 
	 */
	protected void saveLog(String action, String user_id, String ip, String excetion, int type,String systype,String cid,String vendor) throws Exception {
		PageData pd = new PageData();
		pd.put("id", this.get32UUID());
		pd.put("action", action);
		pd.put("user_id", user_id);
		pd.put("ip", ip);
		pd.put("excetion", excetion);
		pd.put("type", type);
		pd.put("SYS_TYPE", systype);
		pd.put("CID", cid);
		pd.put("vendor", vendor);
		logService.save(pd);
	}
	
	/**
	 * 得到PageData
	 */
	public PageData getPageData() {
		return new PageData(this.getRequest());
	}

	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView() {
		return new ModelAndView();
	}

	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		return request;
	}

	/**
	 * 得到32位的uuid
	 * 
	 * @return
	 */
	public String get32UUID() {

		return UuidUtil.get32UUID();
	}

	/**
	 * 得到分页列表的信息
	 */
	public Page getPage() {
		return new Page();
	}
	
	public static void logMessage(Logger logger, String message) {
		logger.info(message);
	}

	public static void logBefore(Logger logger, String interfaceName) {
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}

	public static void logAfter(Logger logger) {
		logger.info("end");
		logger.info("");
	}
	
	public String getUserPicker(List<PageData> list, String id, String content,String path) {
		String result = "";
		for (int i = 0; i < list.size(); i++) {
			if(i == 0){
				result +="[";
			} 
			result += "{value: '" + list.get(i).getString(id) +"',text:'" +list.get(i).getString(content)+"',path:'" +list.get(i).getString(path)+ "'}";
			
			if(i + 1 == list.size()){
				result +="]";
			} else {
				result +=",";
			}
		}
		return result;
	}
	public String getUserPicker(List<PageData> list, String id, String content) {
		String result = "";
		for (int i = 0; i < list.size(); i++) {
			if(i == 0){
				result +="[";
			} 
			result += "{value: '" + list.get(i).getString(id) +"',text:'" +list.get(i).getString(content)+"'}";
			
			if(i + 1 == list.size()){
				result +="]";
			} else {
				result +=",";
			}
		}
		return result;
	}
	
	/***
	 * 获取图片路径
	 * @return
	 */
	public String getImgHostPath() {
		return Tools.readTxtFile(Const.IMGHOSTPATH);
	}
	
	public String putToken(String value) {
		Map<String, Object> payload = new HashMap<String, Object>();
		Date date = new Date();
		payload.put("uid", value);//用户id
		payload.put("iat", date.getTime());// 生成时间
		payload.put("ext", date.getTime() + 1000 * 60 * 60 * 24 * 7);// 过期时间7天
		return Jwt.createToken(payload);
	}
	
	public Object returnJson(PageData pd, JSONObject jsonObject){
		String callback = pd.getString("callback");
		if(StringUtils.isEmpty(callback)) {
			return jsonObject;
		}
		return callback+ "(" + jsonObject + ")";
	}
	
	/**
	 * 登录时获取
	 * @return
	 */
	public Map<String, String> getConfig() {
		Map<String, String> config = new HashMap<String, String>();
		config.put("GIFT_HIDE", RedisUtil.getInstance(5).get("GIFT_HIDE"));
		config.put("VERSION", RedisUtil.getInstance(5).get("VERSION"));
		config.put("SERVICE_TEL", RedisUtil.getInstance(5).get("SERVICE_TEL"));
		config.put("LOGIN_TYPE2", RedisUtil.getInstance(5).get("LOGIN_TYPE"));
		return config;
	}

}
