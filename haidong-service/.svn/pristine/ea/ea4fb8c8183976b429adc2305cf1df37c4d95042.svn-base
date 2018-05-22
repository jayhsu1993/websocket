package com.huixin.haidong.service.system.business.userAuths;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huixin.framework.utils.PageData;
import com.huixin.system.dao.DaoSupport;

@Service("userAuthsService")
public class UserAuthsService {
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

	
	public PageData findByUserid(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserAuthsMapper.findByuserid", pd);
	}


	/*
	 * 列表(全部)
	 */
	public List<PageData> findALL(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserAuthsMapper.findALL", pd);
	}

	public void updateCredential(PageData pd) throws Exception {
		dao.update("UserAuthsMapper.updateCredential", pd);
	}
}
