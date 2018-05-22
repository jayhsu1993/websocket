package com.huixin.haidong.service.system.business.ali;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huixin.framework.utils.PageData;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;

/**
 * 类名称：alicodeService 创建人：system 创建时间：2016-07-14 phone 手机 code 验证码 model 动码流水
 * status 状态 0:已发送,1:已验证 type 类型 1:注册, 2修改密码
 */
@Service("alicodeService")
public class AliCodeService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/*
	 * 新增
	 */
	public void save(PageData pd) throws Exception {
		dao.save("AliCodeMapper.save", pd);
	}

	/*
	 * 删除
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("AliCodeMapper.delete", pd);
	}

	/*
	 * 修改
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("AliCodeMapper.edit", pd);
	}

	/*
	 * 列表
	 */
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("AliCodeMapper.datalistPage", page);
	}

	/*
	 * 列表(全部)
	 */
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AliCodeMapper.listAll", pd);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AliCodeMapper.findById", pd);
	}

	/*
	 * 批量删除
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("AliCodeMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void updateByPhone(PageData pd) throws Exception {
		dao.update("AliCodeMapper.updateByPhone", pd);

	}

}
