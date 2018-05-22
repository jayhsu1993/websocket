package com.huixin.haidong.service.system.business.conference;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huixin.framework.utils.PageData;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;

@Service("conferenceService")
public class ConferenceService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/*
	 * 新增
	 */
	public void save(PageData pd) throws Exception {
		dao.save("ConferenceMapper.save", pd);
	}

	/*
	 * 列表
	 */
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ConferenceMapper.datalistPage", page);
	}
	
	/*
	 * 通过手机号获取数据
	 */
	public PageData findByPhone(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ConferenceMapper.findByPhone", pd);
	}
}