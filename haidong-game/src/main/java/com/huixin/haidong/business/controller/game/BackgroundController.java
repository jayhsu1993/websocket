package com.huixin.haidong.business.controller.game;

import com.huixin.framework.base.ResponseResult;
import com.huixin.framework.base.RestResultGenerator;
import com.huixin.framework.enums.ContentType;
import com.huixin.framework.utils.PageData;
import com.huixin.haidong.business.base.BaseController;
import com.huixin.haidong.service.system.service.BackgroundService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 *
 * @author Administrator
 * @date 2017/03/02
 */
@Controller
@RequestMapping(value = "/backgroundController")
public class BackgroundController extends BaseController {

    @Resource(name = "backgroundServiceImpl")
    private BackgroundService backgroundService;

    @RequestMapping(value = "/listBackgroundImg/{liveid}", method = RequestMethod.GET, produces = ContentType.APPLICATION_JSON_UTF8)
    @ResponseBody
    public ResponseResult<List<String>> listBackgroundImg(@PathVariable("liveid") String liveid){
        try {
            List<String> list = backgroundService.listBackgroundImgByLiveid(liveid);
            return RestResultGenerator.genResult(list, "获取轮播图信息成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("--->获取轮播图失败{}", e);
            return RestResultGenerator.genErrorResult("获取轮播图失败");
        }
    }

    @RequestMapping(value = "/listAwardImg/{liveid}", method = RequestMethod.GET, produces = ContentType.APPLICATION_JSON_UTF8)
    @ResponseBody
    public ResponseResult<List<String>> listAwardImg(@PathVariable("liveid") String liveid){
        try {
            List<String> list = backgroundService.listAwardImgByLiveid(liveid);
            return RestResultGenerator.genResult(list, "获取直播间奖品图片成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("--->获取直播间奖品图片失败{}", e);
            return RestResultGenerator.genErrorResult("获取直播间奖品图片失败");
        }
    }
}
