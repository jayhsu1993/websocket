package com.huixin.haidong.service.system.business.game;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huixin.framework.utils.PageData;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;

/** 
 * 类名称：gamegiftService
 * 创建人：system
 * 创建时间：2016-10-21
 */
@Service("gamegiftService")
public class GameGiftService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("GameGiftMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("GameGiftMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("GameGiftMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("GameGiftMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("GameGiftMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("GameGiftMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("GameGiftMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/*
	 * 更新礼物详情关联信息
	 */
	public void updateDetails(PageData pd)throws Exception{
		dao.update("GameGiftMapper.updateDetails", pd);
	}
	
}

