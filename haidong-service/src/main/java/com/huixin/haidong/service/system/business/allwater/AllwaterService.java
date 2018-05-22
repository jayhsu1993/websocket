package com.huixin.haidong.service.system.business.allwater;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huixin.framework.utils.PageData;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;

/**
 * 类名称：allwaterService 创建人：system 创建时间：2016-07-06
 */
@Service("allwaterService")
public class AllwaterService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/*
	 * 新增
	 */
	public void save(PageData pd) throws Exception {
		dao.save("AllwaterMapper.save", pd);
	}

	/*
	 * 新增(以Map形式作为参数)
	 */
	public void saveGift(Map pd) throws Exception {
		dao.save("AllwaterMapper.saveGift", pd);
	}

	/*
	 * 删除
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("AllwaterMapper.delete", pd);
	}

	/*
	 * 修改
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("AllwaterMapper.edit", pd);
	}

	// 列表
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("AllwaterMapper.datalistPage", page);
	}

	/*
	 * 收礼物列表
	 */
	public List<PageData> datalistShouPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("AllwaterMapper.shoudatalistPage", page);
	}

	/*
	 * 发礼物列表
	 */
	public List<PageData> datalistSendPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("AllwaterMapper.senddatalistPage", page);
	}

	/*
	 * 列表(全部)
	 */
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AllwaterMapper.listAll", pd);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AllwaterMapper.findById", pd);
	}

	/*
	 * 批量删除
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("AllwaterMapper.deleteAll", ArrayDATA_IDS);
	}

	// 收礼物
	public List<PageData> listByLiveid(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AllwaterMapper.listByLiveid", pd);
	}

	// 发礼物
	public List<PageData> listByuserid(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AllwaterMapper.listByuserid", pd);
	}

	// 礼物土豪榜大屏使用
	public List<PageData> listGiftSort(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AllwaterMapper.listGiftSort", pd);
	}
	
	// 礼物土豪榜直播间使用
	public List<PageData> listGiftSortTop50(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AllwaterMapper.listGiftSortTop50", pd);
	}

	//个人礼物总额
	public PageData shouGifttotal(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AllwaterMapper.shouGifttotal", pd);
	}

}
