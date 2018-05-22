package com.huixin.haidong.service.system.business.vote;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huixin.framework.utils.PageData;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;



/** 
 * 类名称：voteService
 * 创建人：system
 * 创建时间：2018-01-08
 */
@Service("voteDao")
public class VoteDao {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("VoteMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("VoteMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("VoteMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("VoteMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAllByLiveId(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("VoteMapper.listAllByLiveId", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("VoteMapper.findById", pd);
	}
	
	
	/*
	* 通过id获取数据
	*/
	public void updateCount(PageData pd)throws Exception{
		dao.update("VoteMapper.updateCount", pd);
	}
	
}

