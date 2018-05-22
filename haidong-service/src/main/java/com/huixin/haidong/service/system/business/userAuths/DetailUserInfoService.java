package com.huixin.haidong.service.system.business.userAuths;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huixin.framework.utils.PageData;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;



/** 
 * 类名称：detailuserinfoService
 * 创建人：system
 * 创建时间：2017-06-19
 */
@Service("detailuserinfoService")
public class DetailUserInfoService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("DetailUserInfoMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("DetailUserInfoMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("DetailUserInfoMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("DetailUserInfoMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("DetailUserInfoMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DetailUserInfoMapper.findById", pd);
	}
	public PageData findByUserId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DetailUserInfoMapper.findByUserId", pd);
	}
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("DetailUserInfoMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

