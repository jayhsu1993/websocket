package com.huixin.haidong.service.system.business.game;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huixin.framework.utils.PageData;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;

/** 
 * 类名称：livegamescoreService
 * 创建人：system
 * 创建时间：2016-08-31
 */
@Service("livegamescoreService")
public class LiveGameScoreService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("LiveGameScoreMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("LiveGameScoreMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("LiveGameScoreMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("LiveGameScoreMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("LiveGameScoreMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("LiveGameScoreMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("LiveGameScoreMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 获取游戏历史最高分
	 * @param gameCode
	 * @return
	 */
	public int getGameHeightScore(String gameCode) throws Exception {
		PageData pd = new PageData();
		pd.put("gameCode", gameCode);
		pd = (PageData) dao.findForObject("LiveGameScoreMapper.getGameHeightScore", pd);
		if(pd != null){
			return pd.getInt("score");
		}
		return 0;
	}

}

