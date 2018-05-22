package com.huixin.haidong.serice.impl;

import com.alibaba.druid.util.StringUtils;
import com.huixin.framework.utils.PageData;
import com.huixin.haidong.service.system.business.userAuths.UserAuthsDao;
import com.huixin.haidong.service.system.business.wechatinfo.WechatInfoDao;
import com.huixin.haidong.service.system.service.UserAuthsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/3/13.
 *
 * @author Administrator
 * @date 2017/03/13
 */
@Service("userAuthsServiceImpl")
public class UserAuthsServiceImpl implements UserAuthsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthsServiceImpl.class);

    @Autowired
    private UserAuthsDao userAuthsDao;

    @Autowired
    private WechatInfoDao wechatInfoDao;

    /**
     * 绑定手机
     * @param pd
     * @return
     */
    public String bindingPhone(PageData pd) throws Exception {
        String result = "success";
        String phone = pd.getString("phone");
        String liveid = pd.getString("liveid");
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(liveid)){
            return "liveid或者phone不能为空";
        }
        PageData wechatinfo = wechatInfoDao.findByUnionId(pd);
        if(wechatinfo == null){
            return "微信用户不存在";
        }
        //发送手机验证码

        return null;
    }
}
