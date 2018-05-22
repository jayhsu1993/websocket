package com.huixin.haidong.service.system.business.seat;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huixin.framework.utils.PageData;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;

/**
 * 婚礼座位排列
 * 
 * @author Administrator
 *
 */
@Service("seatService")
public class SeatService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/*
	 * 新增
	 */
	public void save(PageData pd) throws Exception {
		dao.save("SeatMapper.save", pd);
	}

	/*
	 * 修改
	 */
	public void update(PageData pd) throws Exception {
		dao.update("SeatMapper.update", pd);

	}

	/*
	 * 删除
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("SeatMapper.delete", pd);

	}	/*
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("SeatMapper.findById", pd);
	}
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SeatMapper.list", page);
	}
}
