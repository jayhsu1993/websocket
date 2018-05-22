package com.huixin.haidong.service.system.service;

import com.huixin.framework.utils.PageData;
import com.huixin.system.entity.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 *
 * @author Administrator
 * @date 2017/03/02
 */
public interface BackgroundService {
    List<String> listBackgroundImgByLiveid(String liveid) throws Exception;
    List<String> listAwardImgByLiveid(String liveid) throws Exception;
    PageData savePicture(MultipartFile file, String type, String liveid) throws Exception;
    List<PageData> list(Page page) throws Exception;
}
