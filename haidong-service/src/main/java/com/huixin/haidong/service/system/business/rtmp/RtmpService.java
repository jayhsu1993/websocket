package com.huixin.haidong.service.system.business.rtmp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huixin.framework.utils.PageData;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;

/**
 * 对推流地址表的操作
 * @author wuxiang
 * date:2016.8.31
 */
@Service("rtmpService")
public class RtmpService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("RtmpMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("RtmpMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("RtmpMapper.edit", pd);
	}
	
	/*
	* 修改状态
	*/
	public void editState(PageData pd)throws Exception{
		dao.updateState("RtmpMapper.editState", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("RtmpMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RtmpMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RtmpMapper.findById", pd);
	}
	
	/*
	* 通过liveId获取数据
	*/
	public PageData findByLiveId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RtmpMapper.findByLiveId", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("RtmpMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/*
	 * 获取推流信息
	 */
	public PageData getRtmpInfo(PageData pd) throws Exception{
		return (PageData)dao.findForObject("RtmpMapper.getRtmpInfo", pd);
	}
}
