package com.huixin.haidong.service.system.business.prize;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.huixin.framework.utils.PageData;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;

@Component
public class PrizeDao {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PrizeMapper.datalistPage", page);
	}
}
