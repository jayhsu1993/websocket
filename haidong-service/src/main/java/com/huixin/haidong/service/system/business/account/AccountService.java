package com.huixin.haidong.service.system.business.account;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huixin.framework.utils.PageData;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;

/** 
 * 类名称：accountService
 * 创建人：system
 * 创建时间：2017-02-09
 */
@Service("accountService")
public class AccountService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("AccountMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("AccountMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void update(PageData pd)throws Exception{
		dao.update("AccountMapper.update", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AccountMapper.datalistPage", page);
	}
	
	
	
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AccountMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AccountMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("AccountMapper.deleteAll", ArrayDATA_IDS);
	}
	
	

	public PageData findByoutradeno(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AccountMapper.findByoutradeno", pd);
	}

	public void updateOrder(PageData pd) throws Exception {
		dao.update("AccountMapper.updateOrder", pd);
	}
	
}

