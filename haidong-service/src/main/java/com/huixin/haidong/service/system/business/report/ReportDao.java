package com.huixin.haidong.service.system.business.report;

import com.huixin.framework.utils.PageData;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/** 
 * 类名称：reportService
 * 创建人：system
 * 创建时间：2016-07-08
 */
@Component
public class ReportDao {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("ReportMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("ReportMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("ReportMapper.edit", pd);
	}
	
	/*
	* 修改状态
	*/
	public void editState(PageData pd)throws Exception{
		dao.updateState("ReportMapper.editState", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ReportMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ReportMapper.listAll", pd);
	}
	
	/*
	* 通过openid获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ReportMapper.findById", pd);
	}
	
	/*
	* 通过unionid获取数据
	*/
	public PageData findByUnionId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ReportMapper.findByUnionId", pd);
	}

	public PageData findByUserIdAndLiveid(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ReportMapper.findByUserIdAndLiveid", pd);
	}

	/**
	 * 根据liveid获取所有签到用户信息列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findReportList(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("ReportMapper.findReportList", pd);
	}
	
	/***
	 * 获取签到人数
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Integer findReportNum(PageData pd)throws Exception{
		return (Integer)dao.findForObject("ReportMapper.findReportNum", pd);
	}

	public List<PageData> listReportByUserIdAndLiveid(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ReportMapper.listReportByUserIdAndLiveid", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ReportMapper.deleteAll", ArrayDATA_IDS);
	}

	public void editOuttradeno(PageData pd) throws Exception {
		dao.update("ReportMapper.editOuttradeno", pd);
		
	}

	public void editPayState(PageData pd) throws Exception {
		dao.update("ReportMapper.editPayState",pd);
		
	}

	
}

