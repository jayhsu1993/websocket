package com.huixin.haidong.service.system.business.hongbao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huixin.framework.utils.PageData;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;

/** 
 * 类名称：hongbaoService
 * 创建人：system
 * 创建时间：2016-10-31
 */
@Service("hongbaoService")
public class HongBaoService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("HongBaoMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("HongBaoMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("HongBaoMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("HongBaoMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("HongBaoMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("HongBaoMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("HongBaoMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/*
	*列表(过期红包)
	*/
	public List<PageData> listOverdue(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("HongBaoMapper.listOverdue", pd);
	}
	
	/*
	* 新增红包日志
	*/
	public void saveLog(PageData pd)throws Exception{
		dao.save("HongBaoMapper.saveLog", pd);
	}
}

