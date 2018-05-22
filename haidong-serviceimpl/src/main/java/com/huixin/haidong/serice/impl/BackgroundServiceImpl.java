package com.huixin.haidong.serice.impl;

import com.huixin.framework.constant.PictureConstants;
import com.huixin.framework.redis.RedisUtil;
import com.huixin.framework.utils.*;
import com.huixin.framework.utils.qiniu.FileUploadQiNiu;
import com.huixin.haidong.service.system.business.background.BackgroundDao;
import com.huixin.haidong.service.system.business.live.LiveDao;
import com.huixin.haidong.service.system.service.BackgroundService;
import com.huixin.system.entity.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 *
 * @author Administrator
 * @date 2017/03/02
 */
@Service("backgroundServiceImpl")
public class BackgroundServiceImpl implements BackgroundService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BackgroundServiceImpl.class);

    @Autowired
    private BackgroundDao backgroundDao;

    @Autowired
    private LiveDao liveDao;


    /**
     * 获取轮播图地址
     * @param liveid
     * @return
     * @throws Exception
     */
    public List<String> listBackgroundImgByLiveid(String liveid) throws Exception {
        List<String> result = new ArrayList<String>();
        PageData data = new PageData();
        data.put("liveid",liveid);
        data.put("type",PictureConstants.PICTURE_BACKGROUND);
        List<PageData> list = backgroundDao.listBackgroundImgByLiveid(data);
        String qiniuPath = RedisUtil.getInstance(5).get(Const.QINIUHOSTPATH_NEW);
        for(PageData pd:list){
           result.add(qiniuPath + pd.getString("imgurl"));
        }
        return  result;
    }

    public List<String> listAwardImgByLiveid(String liveid) throws Exception {
        List<String> result = new ArrayList<String>();
        PageData data = new PageData();
        data.put("liveid",liveid);
        data.put("type",PictureConstants.PICTURE_AWARD);
        List<PageData> list = backgroundDao.listBackgroundImgByLiveid(data);
        String qiniuPath = RedisUtil.getInstance(5).get(Const.QINIUHOSTPATH_NEW);
        for(PageData pd:list){
            result.add(qiniuPath + pd.getString("imgurl"));
        }
        return  result;
    }

    /**
     * 上传背景轮播图
     * @param file
     * @param type
     */
    public PageData savePicture(MultipartFile file, String type, String liveid) throws Exception {
        PageData back = null;
        if (null != file && !file.isEmpty()) {
            PageData live = new PageData();
            live.put("id", liveid);
            live = liveDao.findById(live);
            back = new PageData();
            back.put("id", UuidUtil.get32UUID());
            back.put("liveid", liveid);
            //上传至七牛
            back.put("imgurl", FileUploadQiNiu.updateFile(file, liveid, UuidUtil.get32UUID()));
            back.put("userid", live.getString("userId"));
            back.put("livecode", live.getString("liveCode"));
            back.put("create_time", new Date());
            back.put("is_delete", 1);
            back.put("picture_name", getFileNameNoEx( file.getOriginalFilename()));
            if(PictureConstants.TYPE_BACKGROUND.equals(type)){
                back.put("type", PictureConstants.PICTURE_BACKGROUND);
                back.put("name", "轮播图");
            }else{
                back.put("type", PictureConstants.PICTURE_AWARD);
                back.put("name", "奖品图");
            }
            backgroundDao.save(back);
        }else{
            LOGGER.info("--->图片为空{}", file.getName());
        }
        return back;
    }

    public List<PageData> list(Page page) throws Exception {
        List<PageData> result = new ArrayList<PageData>();
        List<PageData> list = backgroundDao.list(page);
        for (PageData pd:list) {
            if(PictureConstants.PICTURE_BACKGROUND.equals(pd.getString("type"))){
                pd.put("type", PictureConstants.TYPE_BACKGROUND);
            }
            if(PictureConstants.PICTURE_AWARD.equals(pd.getString("type"))){
                pd.put("type", PictureConstants.TYPE_AWARD);
            }
            pd.put("imgurl", RedisUtil.getInstance(5).get(Const.QINIUHOSTPATH) + pd.getString("imgurl"));
            result.add(pd);
        }
        return result;
    }

    /*
     * Java文件操作 获取不带扩展名的文件名
     */
    private String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

}
