package com.huixin.haidong.service.system.business.hllc;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huixin.framework.utils.PageData;
import com.huixin.framework.utils.UuidUtil;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;

@Service("hllcService")
public class HllcService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/*
	 * 查询婚礼流程列表
	 */
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("HllcMapper.list", page);
	}

	/**
	 * 查询婚礼子流程列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listInfo(Page page) throws Exception {
		return (List<PageData>) dao.findForList("HllcInfoMapper.listInfo", page);
	}

	/**
	 * 增加婚礼流程
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void add(PageData pd) throws Exception {
		dao.save("HllcMapper.save", pd);
	}

	/**
	 * 修改婚礼流程
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("HllcMapper.edit", pd);

	}

	/**
	 * 根据id 查询婚礼流程
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData gethllc(PageData pd) throws Exception {
		return (PageData) dao.findForObject("HllcMapper.findById", pd);
	}

	/**
	 * 根据id 查询婚礼子流程
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData gethllcInfo(PageData pd) throws Exception {
		return (PageData) dao.findForObject("HllcInfoMapper.gethllcInfo", pd);
	}

	/**
	 * 修改子流程
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void editHllcInfo(PageData pd) throws Exception {
		dao.update("HllcInfoMapper.edit", pd);

	}

	/**
	 * 增加子流程
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void addHllcInfo(PageData pd) throws Exception {
		dao.save("HllcInfoMapper.save", pd);

	}

	/**
	 * 删除婚礼流程 先删除该婚礼下所有的子流程 再删除婚礼主流程
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void deleteHllc(PageData pd) throws Exception {
		dao.delete("HllcInfoMapper.deleteHllcInfoByHllcId", pd);
		dao.delete("HllcMapper.deleteHllc", pd);

	}

	/**
	 * 删除婚礼子流程
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void deleteHllcInfo(PageData pd) throws Exception {
		dao.delete("HllcInfoMapper.deleteHllcInfo", pd);
	}

	/**
	 * 插入默认数据
	 * @param listhllc //主流程列表
	 * @param listhllcList //子流程列表
	 * @throws Exception 
	 */
	public void savedefault(String userid ,List<PageData> listhllc, List<PageData> listhllcList) throws Exception {
		for (int i=0;i<listhllc.size();i++ ){
			String id=UuidUtil.get32UUID();
			PageData pd	= new PageData();
			pd.put("id",id);
			pd.put("state","0");
			pd.put("userid",userid);
			pd.put("lcname", listhllc.get(i).getString("lcname"));
			pd.put("orderNumber", listhllc.get(i).getInt("orderNumber"));
			add(pd);
			for (int j=0;j<listhllcList.size();j++ ){
				if (listhllc.get(i).get("id").equals(listhllcList.get(j).get("hllcid"))) {
					PageData info=new PageData();
					info.put("id",UuidUtil.get32UUID());
					info.put("state","0");
					info.put("hllcid",id);
					info.put("info",listhllcList.get(j).get("info"));
					info.put("person",listhllcList.get(j).get("person"));
					info.put("startTime",listhllcList.get(j).get("startTime"));
					addHllcInfo(info);
				}
					
			}
		}	
	}
}
