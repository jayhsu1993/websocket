package com.huixin.haidong.serice.impl;

import com.alibaba.druid.util.StringUtils;
import com.huixin.framework.redis.RedisUtil;
import com.huixin.framework.utils.DateUtil;
import com.huixin.framework.utils.PageData;
import com.huixin.framework.utils.Tools;
import com.huixin.framework.utils.token.Jwt;
import com.huixin.haidong.service.system.business.live.LiveDao;
import com.huixin.haidong.service.system.business.report.ReportDao;
import com.huixin.haidong.service.system.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/2.
 *
 * @author Administrator
 * @date 2017/03/02
 */
@Service("reportServiceImpl")
public class ReportServiceImpl implements ReportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private LiveDao liveDao;
    
    public String signIn(PageData data ,HttpSession session, int joinStatus) {
        String result = "success";
        //房间id
        String liveCode = data.getString("liveCode");
        //密码
        String pwd = data.getString("password");
        if(StringUtils.isEmpty(liveCode) || StringUtils.isEmpty(pwd)){
            result = "用户名或密码为空";
            return result;
        }
        PageData live = null;
        PageData user = new PageData();
        try {
            live = liveDao.findById(data);
            if(null != live){
//            	user = liveDao.getCreateUserInfo(data);
            	//更新最后登录时间
            	updateLastDate(live, joinStatus);
                if(Tools.isEmpty(live.getString("scene_type"))){
                	 session.setAttribute("scene_type", "0");
                	 //获取互动场景类型(商务还是婚礼)默认0:为婚礼模式  1：商务模式
                }else{
                	session.setAttribute("scene_type", live.getString("scene_type"));
                }
                session.setAttribute("QRCodeUrl", live.getString("QRCodeUrl"));
                // 查询是否公众号授权且获取一键吸粉状态功能
            	PageData auth_info = liveDao.getAuthInfo(live);
            	if(null != auth_info){
            		String status = auth_info.getString("open_powder");
            		if("1".equals(status)){	//已开启
            			String WXQRCodeUrl = live.getString("WXQRCodeUrl");
            			if(Tools.notEmpty(WXQRCodeUrl)){
            				session.setAttribute("QRCodeUrl", WXQRCodeUrl);
            			}
            		}
            	}
            	String game_logo_pic = live.getString("game_logo_pic");
            	String game_sign_content = live.getString("game_sign_content");
                session.setAttribute("roomId", live.getString("id"));
                session.setAttribute("token", putToken(live.getString("id")));
                session.setAttribute("host", RedisUtil.getInstance(5).get("QINIUHOSTPATH"));
                session.setAttribute("chatUrl", RedisUtil.getInstance(5).get("CHAT_URL_NEW"));
                session.setAttribute("signType", live.getInt("sign_type"));
                session.setAttribute("game_logo_pic", Tools.notEmpty(game_logo_pic)? game_logo_pic : "");
                session.setAttribute("game_sign_content", Tools.notEmpty(game_sign_content)? game_sign_content : "");
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.debug("--->查询live房间出错{}",e);
            result = "房间id错误";
            return result;
        }
        if(live == null){
            result = "房间不存在";
            return result;
        }
        if(!(Tools.notEmpty(live.getString("wedding_time")) && DateUtil.compareDate(live.getString("wedding_time"), DateUtil.getTime()))){
        	result = "账号已过期";
            return result;
        }
        if(!pwd.equals(live.getString("password"))){
            result = "密码错误";
            return result;
        }
        //成功
        return result;
    }

    private void updateLastDate(PageData live, int joinStatus) {
		live.put("joinStatus", joinStatus);
		try {
			liveDao.updateLastDate(live);
		} catch (Exception e) {
			e.printStackTrace();
			   LOGGER.debug("--->更新登录时间出错{}",e);
		}
	}

	public List<PageData> listReport(String liveid) throws Exception {
        PageData pd = new PageData();
        pd.put("liveid",liveid);
        List<PageData> list = reportDao.findReportList(pd);
        return list;
    }

    private String putToken(String value) {
        Map<String, Object> payload = new HashMap<String, Object>();
        Date date = new Date();
        //用户id
        payload.put("uid", value);
        // 生成时间
        payload.put("iat", date.getTime());
        // 过期时间7天
        payload.put("ext", date.getTime() + 1000 * 60 * 60 * 24 * 7);
        return Jwt.createToken(payload);
    }
    
}
