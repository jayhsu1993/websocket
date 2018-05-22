package com.huixin.framework.wx.share;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.huixin.framework.wx.util.PropertiesUtil;
import com.huixin.framework.wx.util.WxHttpUtils;

import net.sf.json.JSONObject;

public class WXShare {

	//token
	private  static String JSAPI_TOKEN = "";
	
	//jsapi_ticket
	private  static String JSAPI_TICKET = "";
	
	//Calendar specialDate = Calendar.getInstance();
	private  static Calendar refreshDate  = Calendar.getInstance();
	
	private static Map getOpenMap(String openParam) {
		String openJsonStr = WxHttpUtils.sendGET(WxHttpUtils.JSAPI_TOKEN_URL, openParam);
		return JSONObject.fromObject(openJsonStr);
	}
	
	private  static String GetTicket(){
		try {
			Calendar now = Calendar.getInstance();
			
			if( now.after(refreshDate) ||JSAPI_TOKEN.isEmpty() ||JSAPI_TICKET.isEmpty()){
				// 将appid+secret+code+grant_type拼接起来（根据wechat.properties配置文件获取相应的值）
				String openParam = "grant_type=client_credential&appid=" + PropertiesUtil.getValue("wechat.properties", "mchappid") + "&secret="
						+ PropertiesUtil.getValue("wechat.properties", "appsecret") ;
				// 请求链接。前面是获取access_token的微信官方链接可以获取access_token，openid。。。其他没啥用
				Map openMap = getOpenMap(openParam);
				Integer errcode = (Integer) openMap.get("errcode");
				if (errcode!= null && errcode > 0) {
					throw new Exception( (String) openMap.get("errmsg"));
				}
				// 将access_token拿出来
				JSAPI_TOKEN = (String) openMap.get("access_token");
				Integer expires_in = (Integer) openMap.get("expires_in");
				refreshDate = Calendar.getInstance();
				
				// 获取ticket
				String userinfo = WxHttpUtils.sendGET(WxHttpUtils.JSAPI_TICKET_URL,
						"access_token=" + JSAPI_TOKEN + "&type=jsapi");
				JSONObject ticketInfo = JSONObject.fromObject(userinfo);
				errcode = (Integer) ticketInfo.get("errcode");
				if (errcode!= null && errcode > 0) {
					throw new Exception( (String) ticketInfo.get("errmsg"));
				}
				JSAPI_TICKET = (String) ticketInfo.get("ticket");
				Integer expires_in2 = (Integer) ticketInfo.get("expires_in");
				if(expires_in2 < expires_in){
					refreshDate.add(Calendar.SECOND, expires_in2-500);
				} else {
					refreshDate.add(Calendar.SECOND, expires_in-500);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSAPI_TICKET;
	}
	

    public static Map<String, String> Sign(String url) {
    	String jsapi_ticket = GetTicket();
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        //ret.put("url", url);
        //ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("appId" , PropertiesUtil.getValue("wechat.properties", "mchappid"));
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
