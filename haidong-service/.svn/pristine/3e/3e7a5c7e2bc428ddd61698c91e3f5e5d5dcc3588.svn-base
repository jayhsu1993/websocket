package com.huixin.haidong.service.system.business.dictionary;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.huixin.framework.redis.RedisUtil;
import com.huixin.framework.utils.PageData;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;

/**
 * 类名称：dictionaryService 创建人：system 创建时间：2016-11-28
 */
@Service("dictionaryService")
public class DictionaryService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/*
	 * 新增
	 */
	public void save(PageData pd) throws Exception {
		dao.save("DictionaryMapper.save", pd);
	}

	/*
	 * 删除
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("DictionaryMapper.delete", pd);
	}

	/*
	 * 修改
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("DictionaryMapper.edit", pd);
	}

	/*
	 * 列表
	 */
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("DictionaryMapper.datalistPage", page);
	}

	/*
	 * 列表(全部)
	 */
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("DictionaryMapper.listAll", pd);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("DictionaryMapper.findById", pd);
	}

	/*
	 * 通过name获取数据
	 */
	public PageData findByName(PageData pd) throws Exception {
		return (PageData) dao.findForObject("DictionaryMapper.findByName", pd);
	}

	/*
	 * 通过id获取数据
	 */
	public List<PageData> findByIds(String[] ArrayDATA_ID) throws Exception {
		return (List<PageData>) dao.findForList("DictionaryMapper.findByIds", ArrayDATA_ID);
	}

	/*
	 * 批量删除
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("DictionaryMapper.deleteAll", ArrayDATA_IDS);
	}

	/*
	 * 获取value值
	 */
	public String getValue(String name) throws Exception {
		String value = RedisUtil.getInstance(5).get(name);
		if (StringUtils.isEmpty(value)) {
			PageData pd = new PageData();
			pd.put("name", name);
			PageData data = findByName(pd);
			if (data != null && !data.equals("")) {
				value = data.getString("value");
				RedisUtil.getInstance(5).set(name, value);
			}
		}
		return value;
	}

}
