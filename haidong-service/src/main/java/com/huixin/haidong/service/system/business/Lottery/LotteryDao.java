package com.huixin.haidong.service.system.business.Lottery;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.huixin.framework.utils.PageData;
import com.huixin.system.dao.DaoSupport;

@Component
public class LotteryDao {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("LotteryMapper.insertLotteryData", pd);
	}

	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("LotteryMapper.deleteLotteryData", pd);
	}

	/**
	 * 删除通过Type
	 */
	public void deleteByType(PageData pd)throws Exception{
		dao.delete("LotteryMapper.deleteByType", pd);
	}
	
	
	/**
	 * 获取没有中奖的签到人列表
	 * 
	 */
	public List<PageData> findReportNoAwardList(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("LotteryMapper.findReportNoAwardList", pd);
	}

	
	/**
	 * 根据获奖类型获取对应的人列表
	 */
	public List<PageData> findReportByType(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("LotteryMapper.findReportByType", pd);
	}

	/**
	 * 根据获奖类型获取对应的人列表(该列表只有name字段)
	 */
	public List<PageData> listNameByType(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("LotteryMapper.listNameByType", pd);
	}

	
	/**
	 * 获取已经中奖的人的列表(flag 为 2的情况)
	 */
	public List<PageData> listHasAward(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("LotteryMapper.listHasAward", pd);
	}

	/**
	 * 获取已经中奖的人的列表(flag 为 1的情况)
	 */
	public List<PageData> listNameHasAward(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("LotteryMapper.listNameHasAward", pd);
	}
	
	/**
	 * 删除全部抽奖人  
	 */
	public void deleteByAll(PageData pd)throws Exception{
		dao.delete("LotteryMapper.deleteByAll", pd);
	}

}
