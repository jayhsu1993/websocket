package com.huixin.haidong.service.system.business.prize;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huixin.framework.utils.PageData;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;

@Service("prizeService")
public class PrizeService {
	
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	
	/**
	 * 
	 * 显示内定中奖名单
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> list(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PrizeMapper.datalist", pd);
	}
	
}
