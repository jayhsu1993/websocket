/**
 * 
 */
package com.huixin.framework.utils.ali;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.huixin.framework.constant.UserConstants;
import com.huixin.framework.exception.BusinessException;
import com.huixin.framework.utils.Logger;
import com.huixin.framework.utils.PageData;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * @author jacky
 *
 */
public class AliDaYu {

	private static final String App_Key = "23487877";

	private static final String URL = "http://gw.api.taobao.com/router/rest";

	private static final String SECRET = "b843770cfdc9cbe13960f41f233976fb";

	protected static Logger logger = Logger.getLogger("com.huixin.framework.utils.ali.AliDaYu");
	
	/**用户注册验证码*/
	public static final String SMS_11635137 = "SMS_20105023";
	
	/**修改密码验证码*/
	public static final String SMS_11635135 = "SMS_20105021";
	
	/**身份验证验证码*/
	public static final String SMS_11635141 = "SMS_20105027";
	
	public static void main(String[] args) throws Exception {
		// String phone = "15800366987";
		// String phone = "18621269363";
		PageData json = sendCode("15800366987", getRandomCode(2,6), SMS_11635137);
		System.out.println(json);
		System.out.println("====================");
		// 正确
//		String json = "{\"alibaba_aliqin_fc_sms_num_send_response\":{\"result\":{\"err_code\":\"0\",\"model\":\"102195959856^1102842291130\",\"success\":true},\"request_id\":\"zt99ijbc4e8g\"}}";
		// 错误
		// String json = "{\"error_response\":{\"code\":15,\"msg\":\"Remote
		// service
		// error\",\"sub_code\":\"isv.BUSINESS_LIMIT_CONTROL\",\"sub_msg\":\"触发业务流控\",\"request_id\":\"zt9s80z3qm1r\"}}";

		// System.out.println(sendCode(json, new PageData()));
		// JsonNode productMap = getMap(json);//转成map
		// JsonNode alibaba =
		// productMap.get("alibaba_aliqin_fc_sms_num_send_response");
		// if(null != alibaba){
		// productMap = alibaba.get("result");
		// if(null != productMap){
		// System.out.println(productMap.get("err_code").toString().replaceAll("\"",
		// ""));;
		// }
		// } else {
		// alibaba = productMap.get("error_response");
		// System.out.println(alibaba.get("code").asText());
		// }
	}

	private static JsonNode getMap(String json) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode productMap = mapper.readTree(json);// 转成map
		return productMap;
	}

	@SuppressWarnings("finally")
	public static PageData sendCode(String phone, String code, String sms) throws BusinessException {
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		AlibabaAliqinFcSmsNumSendResponse rsp = null;
		try {
			TaobaoClient client = new DefaultTaobaoClient(URL, App_Key, SECRET);

			// req.setExtend("123456");//
			// 公共回传参数，在“消息返回”中会透传回该参数；举例：用户可以传入自己下级的会员ID，在消息返回时，该会员ID会包含在内，用户可以根据该会员ID识别是哪位会员使用了你的应用
			// req.setSmsType("normal");// 短信类型，传入值请填写normal
			// req.setSmsFreeSignName("大鱼测试");//
			// 短信签名，传入的短信签名必须是在阿里大鱼“管理中心-短信签名管理”中的可用签名。如“阿里大鱼”已在短信签名管理中通过审核，则可传入”阿里大鱼“（传参时去掉引号）作为短信签名。短信效果示例：【阿里大鱼】欢迎使用阿里大鱼服务。
			// req.setSmsParamString("{\"code\":\"1234\",\"product\":\"alidayu\"}");
			// req.setRecNum("13000000000");//
			// 短信接收号码。支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。群发短信需传入多个号码，以英文逗号分隔，一次调用最多传入200个号码。示例：18600000000,13911111111,13322222222
			// req.setSmsTemplateCode("SMS_11635137");//
			// 短信模板ID，传入的模板必须是在阿里大鱼“管理中心-短信模板管理”中的可用模板。示例：SMS_585014
//			req.setExtend("");
			req.setSmsType("normal");
			req.setSmsFreeSignName("嗨动直播");
			req.setSmsParamString("{code:'" + code + "', product:'嗨动直播'}");
			req.setRecNum(phone);
			req.setSmsTemplateCode(sms);
			rsp = client.execute(req);
			logger.info("发送验证码:" + rsp.getBody());
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw new BusinessException(UserConstants.SEND_CODE_ERROR, "发送验证码出错", phone);
		}  
		return getJson(rsp.getBody());
	}

	@SuppressWarnings("finally")
	public static PageData getJson(String json) throws BusinessException {
		PageData pd = new PageData();
		try {
			JsonNode productMap = getMap(json);// 转成map
			JsonNode alibaba = productMap.get("alibaba_aliqin_fc_sms_num_send_response");
			if (null != alibaba) {
				productMap = alibaba.get("result");
				if (null != productMap) {
					pd.put("model", productMap.get("model").asText());
					// System.out.println(productMap.get("err_code").toString().replaceAll("\"",
					// ""));;
				}
			} else {
				alibaba = productMap.get("error_response");
				if (null != alibaba) {
					pd.put("msg", alibaba.get("sub_code").asText());
					pd.put("code", alibaba.get("code").asText());
				}
				// System.out.println(alibaba.get("code"));
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw new BusinessException(UserConstants.SEND_CODE_ERROR, "发送验证码出错", json);
		} 
		return pd;
	}

	public static String getRandomCode(int beg, int end) {
		String[] beforeShuffle = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		List list = Arrays.asList(beforeShuffle);
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		String afterShuffle = sb.toString();
		return afterShuffle.substring(beg, end);
	}

}
