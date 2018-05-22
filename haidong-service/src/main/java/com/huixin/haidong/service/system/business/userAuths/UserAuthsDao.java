package com.huixin.haidong.service.system.business.userAuths;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.huixin.framework.utils.PageData;
import com.huixin.system.dao.DaoSupport;

@Component
public class UserAuthsDao {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /*
     * 新增
     */
    public void save(PageData pd) throws Exception {
        dao.save("UserAuthsMapper.save", pd);
    }

    /*
     * 通过page获取数据
     */
    public PageData findByPage(PageData pd) throws Exception {
        return (PageData) dao.findForObject("UserAuthsMapper.findByPage", pd);
    }

    public void updateCredential(PageData pd) throws Exception {
        dao.update("UserAuthsMapper.updateCredential", pd);
    }


}
