package com.huixin.haidong.serice.impl;

import com.alibaba.druid.util.StringUtils;
import com.huixin.framework.redis.RedisUtil;
import com.huixin.framework.utils.Const;
import com.huixin.framework.utils.PageData;
import com.huixin.haidong.service.system.business.dictionary.DictionaryService;
import com.huixin.haidong.service.system.business.live.LiveDao;
import com.huixin.haidong.service.system.business.signwish.SignWishDao;
import com.huixin.haidong.service.system.business.staffstyle.StaffStyleDao;
import com.huixin.haidong.service.system.business.turntable.TurnTableDao;
import com.huixin.haidong.service.system.business.vote.VoteDao;
import com.huixin.haidong.service.system.service.LiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/9.
 *
 * @author Administrator
 * @date 2017/03/09
 */
@Service("liveServiceImpl")
public class LiveServiceImpl implements LiveService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LiveServiceImpl.class);

    @Autowired
    private LiveDao liveDao;
    
    @Autowired
	private SignWishDao signWishDao;
    
    @Autowired
	private VoteDao voteDao;
    
    @Autowired
	private StaffStyleDao staffStyleDao;
    
    @Autowired
	private TurnTableDao turnTableDao;

    @Resource
    private DictionaryService dictionaryService;

    public PageData queryLive(String id){
        try {
            PageData live = liveDao.queryLive(id);
            PageData liveResource = liveDao.queryLiveResource(id);
            //七牛地址
            String qiniuPath = dictionaryService.getValue(Const.QINIUHOSTPATH);
            if(liveResource != null && live != null){
                live.put("back_path", liveResource.getString("back_path"));
                live.put("live_path", liveResource.getString("live_path"));
                live.put("headimgurl", liveResource.getString("headimgurl"));
                live.put("font_pic", qiniuPath + liveResource.getString("font_pic"));
                live.put("share_pic", qiniuPath + liveResource.getString("share_pic"));
                // 根据直播号，redis0号数据库查找缓存的当前房间的观看人数
                String currentNum = RedisUtil.getInstance().get(id);
                if(StringUtils.isEmpty(currentNum)){
                    currentNum = "0";
                }
                live.put("currentNum", currentNum);
            }
            return live;
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error(id + "---->查询直播间信息出错" + e.getMessage());
            return null;
        }
    }

    public PageData queryLiveResource(String id) throws Exception {
        return liveDao.queryLiveResource(id);
    }

    public List<PageData> listLive() throws Exception {
        PageData pd = new PageData();
        return liveDao.listLive(pd);
    }

	public Map<String, List<PageData>> getDesignConfig(PageData pd) throws Exception {
		Map result = new HashMap();
		List<PageData> vote_list = voteDao.listAllByLiveId(pd);
		List<PageData> staff_style = staffStyleDao.listAllByLiveId(pd);
		List<PageData> turn_table = turnTableDao.listAllByLiveId(pd);
		result.put("vote_list", vote_list);
		result.put("staff_style", staff_style);
		result.put("turn_table", turn_table);
		return result;
	}


}
