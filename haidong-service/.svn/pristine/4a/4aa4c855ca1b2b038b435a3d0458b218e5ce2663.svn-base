package com.huixin.haidong.service.system.business.redpacket;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huixin.framework.utils.PageData;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;

/** 
 * 类名称：redpacketService
 * 创建人：system
 * 创建时间：2016-07-06
 */
@Service("redpacketService")
public class RedpacketService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("RedpacketMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("RedpacketMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("RedpacketMapper.edit", pd);
	}
	
	/*
	* 收红包
	*/
	public void getRp(PageData pd)throws Exception{
		dao.update("RedpacketMapper.addRp", pd);
	}
	
	/*
	* 发红包
	*/
	public void sendRp(PageData pd)throws Exception{
		dao.update("RedpacketMapper.sendRp", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("RedpacketMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RedpacketMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RedpacketMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("RedpacketMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

