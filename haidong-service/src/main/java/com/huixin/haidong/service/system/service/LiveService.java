package com.huixin.haidong.service.system.service;

import com.huixin.framework.utils.PageData;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/9.
 * ILiveService类
 *
 * @author Administrator
 * @date 2017/03/09
 */
public interface LiveService {
    PageData queryLive(String id);
    PageData queryLiveResource(String id) throws Exception;
    List<PageData> listLive() throws Exception;
    
    Map<String, List<PageData>> getDesignConfig(PageData pd) throws Exception;
}
