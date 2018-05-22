package com.huixin.haidong.service.system.business.hongbao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huixin.framework.utils.PageData;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;

/**
 * 类名称：hongbaotouserService 创建人：system 创建时间：2016-10-31
 */
@Service("hongbaotouserService")
public class HongBaoToUserService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/*
	 * 新增
	 */
	public void save(PageData pd) throws Exception {
		dao.save("HongBaoToUserMapper.save", pd);
	}

	/*
	 * 删除
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("HongBaoToUserMapper.delete", pd);
	}

	/*
	 * 修改
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("HongBaoToUserMapper.edit", pd);
	}

	/*
	 * 列表
	 */
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("HongBaoToUserMapper.datalistPage", page);
	}
	
	/*
	 * 根据红包id获取红包记录列表
	 */
	public List<PageData> recordList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("HongBaoToUserMapper.recordlistPage", page);
	}
	
	/*
	 * 根据用户user_id获取收红包记录列表
	 */
	public List<PageData> receiveRecordList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("HongBaoToUserMapper.receiveRecordlistPage", page);
	}
	
	/*
	 * 根据用户user_id获取发红包记录列表
	 */
	public List<PageData> sendRecordList(Page page) throws Exception {
		return (List<PageData>) dao.findForList("HongBaoToUserMapper.sendRecordlistPage", page);
	}
	

	/*
	 * 列表(全部)
	 */
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("HongBaoToUserMapper.listAll", pd);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("HongBaoToUserMapper.findById", pd);
	}

	/*
	 * 批量删除
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("HongBaoToUserMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/*
	 * 通过user_id获取发送红包的总金额数据
	 */
	public PageData sendAllMoneyByUserId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("HongBaoToUserMapper.sendAllMoneyByUserId", pd);
	}
	
	/*
	 * 通过user_id获取收红包的总金额数据
	 */
	public PageData receiveAllMoneyByUserId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("HongBaoToUserMapper.receiveAllMoneyByUserId", pd);
	}
	
	/*
	 * 通过user_id和红包id判断用户是否已抢过该红包
	 */
	public PageData judgeWhetherGet(PageData pd) throws Exception {
		return (PageData) dao.findForObject("HongBaoToUserMapper.judgeWhetherGet", pd);
	}

}
