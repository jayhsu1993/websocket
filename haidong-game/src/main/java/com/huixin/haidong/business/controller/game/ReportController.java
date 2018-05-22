package com.huixin.haidong.business.controller.game;

import com.huixin.framework.base.RestResultGenerator;
import com.huixin.framework.enums.ContentType;
import com.huixin.framework.utils.PageData;
import com.huixin.haidong.business.base.BaseController;
import com.huixin.haidong.service.system.service.ReportService;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */
@RestController
@RequestMapping(value = "/reportController")
public class ReportController extends BaseController {

    @Resource(name = "reportServiceImpl")
    private ReportService reportService;

    /**
     * 跳转到签到页面
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public ModelAndView init() {
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("business/game/index");
        return mv;
    }

    /**
     * 获取签到用户信息列表
     */
    @RequestMapping(value = "/report/{liveid}", method = RequestMethod.GET, produces = ContentType.APPLICATION_JSON_UTF8)
    @ResponseBody
    public Object listReport(@PathVariable("liveid") String liveid){
        logBefore(logger, "根据live-ID查询" + liveid);
        try {
            List<PageData> list = reportService.listReport(liveid);
            return RestResultGenerator.genResult(list,"查询信息成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("-->查询签到直播间初始化信息失败{}",e);
            return RestResultGenerator.genErrorResult("查询出错");
        }
    }

}
