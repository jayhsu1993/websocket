package com.huixin.haidong.service.system.business.budget;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huixin.framework.utils.PageData;
import com.huixin.system.dao.DaoSupport;

/**
 * 类名称：ballService 创建人：system 创建时间：2016-06-21
 */
@Service("budgetService")
public class BudgetService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/*
	 * 新增
	 */
	public void save(PageData pd) throws Exception {
		dao.save("WeddingbudgetMapper.save", pd);
	}

	/*
	 * 修改
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("WeddingbudgetMapper.edit", pd);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("WeddingbudgetMapper.findById", pd);
	}
	
	/*
	 * 通过手机号码获取数据
	 */
	public PageData findByPhone(PageData pd) throws Exception {
		return (PageData) dao.findForObject("WeddingbudgetMapper.findByPhone", pd);
	}

}
