package com.huixin.haidong.service.system.business.hotelbespeak;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huixin.framework.utils.PageData;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;

/** 
 * 类名称：hotelbespeakService
 * 创建人：system
 * 创建时间：2017-04-07
 */
@Service("hotelbespeakService")
public class HotelBespeakService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("HotelBespeakMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("HotelBespeakMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("HotelBespeakMapper.edit", pd);
	}
	
	/*
	* 修改
	*/
	public void editByUserIdAndHotelId(PageData pd)throws Exception{
		dao.update("HotelBespeakMapper.editByUserIdAndHotelId", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("HotelBespeakMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("HotelBespeakMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("HotelBespeakMapper.findById", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findByUserIdAndHotelId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("HotelBespeakMapper.findByUserIdAndHotelId", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("HotelBespeakMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

