package com.huixin.haidong.service.system.business.groupuser;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huixin.framework.utils.PageData;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;
/** 
 * 类名称：grouptouserService
 * 创建人：system
 * 创建时间：2016-07-13
 */
@Service("grouptouserService")
public class GroupToUserService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("GroupToUserMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("GroupToUserMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("GroupToUserMapper.edit", pd);
	}
	
	/*
	* 修改
	*/
	public void updateGroup(PageData pd)throws Exception{
		dao.update("GroupToUserMapper.updateGroup", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("GroupToUserMapper.datalistPage", page);
	}
	
	/*
	*群成员列表
	*/
	public List<PageData> groupuserlistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("GroupToUserMapper.groupuserlistPage", page);
	}
	
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("GroupToUserMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("GroupToUserMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("GroupToUserMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/*
	*用户所加群组列表(全部)
	*/
	public List<PageData> listAllGroup(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("GroupToUserMapper.listAllGroup", pd);
	}
	
	/*
	*群组中所有用户列表(全部)
	*/
	public List<PageData> listAllUser(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("GroupToUserMapper.listAllUser", pd);
	}
	
	
}

