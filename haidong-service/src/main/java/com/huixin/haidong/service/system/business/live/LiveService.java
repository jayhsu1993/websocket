package com.huixin.haidong.service.system.business.live;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huixin.framework.utils.PageData;
import com.huixin.framework.utils.Tools;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;

/**
 * 类名称：liveService 创建人：system 创建时间：2016-07-01
 */
@Service("liveService")
public class LiveService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/*
	 * 新增直播信息
	 */
	public void saveLiveInfo(PageData pd) throws Exception {
		dao.save("LiveMapper.saveLiveInfo", pd);
	}

	/*
	 * 新增直播资源路径
	 */
	public void saveResource(PageData pd) throws Exception {
		dao.save("LiveMapper.saveResource", pd);
	}

	/*
	 * 删除
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("LiveMapper.delete", pd);
	}

	/*
	 * 修改
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("LiveMapper.edit", pd);
	}

	/*
	 * 列表
	 */
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("LiveMapper.datalistPage", page);
	}

	/*
	 * 列表(全部)
	 */
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("LiveMapper.listAll", pd);
	}

	/*
	 * 列表(获取当前正在直播的列表)
	 */
	public List<PageData> listRecommend(Page page) throws Exception {
		return (List<PageData>) dao.findForList("LiveMapper.datalistPage", page);
	}
	
	/*
	 * 列表(获取尚未开播的直播列表)
	 */
	public List<PageData> listWaitLive(Page page) throws Exception {
		return (List<PageData>) dao.findForList("LiveMapper.listPageWaitLive", page);
	}
	
	/*
	 * 列表(获取当前正在直播的列表)
	 */
	public List<PageData> listLiving(Page page) throws Exception {
		return (List<PageData>) dao.findForList("LiveMapper.listPageLiving", page);
	}
	/*
	 * 列表(获取回放直播的列表)
	 */
	public List<PageData> listBack(Page page) throws Exception {
		return (List<PageData>) dao.findForList("LiveMapper.listPageBack", page);
	}

	/*
	 * 列表(根据关键字搜索直播)
	 */
	public List<PageData> searchLive(Page page) throws Exception {
		return (List<PageData>) dao.findForList("LiveMapper.listPageSearchLive", page);
	}
	
	/*
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("LiveMapper.findById", pd);
	}

	/*
	 * 批量删除
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("LiveMapper.deleteAll", ArrayDATA_IDS);
	}

	/*
	 * 操作直播状态
	 */
	public void operateLive(PageData pd) throws Exception {
		dao.update("LiveMapper.operateLive", pd);
	}

	/*
	 * 更新礼物收入
	 */
	public void updateGift(PageData pd) throws Exception {
		dao.update("LiveMapper.updateGift", pd);
	}

	/*
	 * 进入直播
	 */
	public PageData findLiveById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("LiveMapper.findLiveById", pd);
	}

	/**
	 * 进入个人用户直播
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getUserLive(PageData pd) throws Exception {

		return (PageData) dao.findForObject("LiveMapper.userLiveList", pd);
	}

	/**
	 * 保存live直播间的二维码地址
	 * @param QRCodeUrl
	 * @throws Exception
	 */
	public void updateLiveQRCodeUrl(String QRCodeUrl, String id) throws Exception{
		PageData pd = new PageData();
		pd.put("id", id);
		pd.put("QRCodeUrl", QRCodeUrl);
		dao.update("LiveMapper.updateLiveQRCodeUrl", pd);
	}

	/**
	 * 获取live直播间的二维码地址
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String queryLiveQRCodeUrl(String id) throws Exception{
		PageData pd = new PageData();
		pd.put("id", id);
		pd = (PageData) dao.findForObject("LiveMapper.queryLiveQRCodeUrl", pd);
		return pd.getString("QRCodeUrl");
	}
	
	/**
	 * 切换直播流地址
	 */
	public void changeLivePath(PageData pd)throws Exception{
		dao.update("LiveMapper.changeLivePath", pd);
	}

	
	/**
	 * 切换时，获取当前直播地址
	 */
	public PageData getCurrentLive(PageData pd)throws Exception{
		return (PageData) dao.findForObject("LiveMapper.getCurrentLive", pd);
	}
	
	/**
	 * 获取直播签到人数
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getLiveByReportNum(PageData pd)throws Exception{
		return (PageData) dao.findForObject("LiveMapper.getLiveByReportNum", pd);
	}
	

}
