package com.huixin.haidong.service.system.business.signwish;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huixin.framework.utils.PageData;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;


/** 
 * 类名称：signwishService
 * 创建人：system
 * 创建时间：2017-11-27
 */
@Service("signWishDao")
public class SignWishDao {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("SignWishMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("SignWishMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("SignWishMapper.edit", pd);
	}
	
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAllByLiveId(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SignWishMapper.listAllByLiveId", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SignWishMapper.findById", pd);
	}
	
	/*
	* 通过type获取数据
	*/
	public List<PageData> findByType(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SignWishMapper.findByType", pd);
	}
	
	/*
	* 通过liveId获取数据
	*/
	public List<PageData> findByLiveId(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SignWishMapper.findByLiveId", pd);
	}
	
	
	/*
	* 通过liveId获取数据
	*/
	public List<PageData> getWishListByLiveId(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SignWishMapper.getlistPageByLiveId", page);
	}
}

