package com.huixin.haidong.service.system.business.invitations;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huixin.framework.utils.PageData;
import com.huixin.framework.utils.UuidUtil;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;

/**
 * 类名称：invitationsService 创建人：system 创建时间：2016-06-22
 */
@Service("invitationsService")
public class InvitationsService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/*
	 * 新增
	 */
	public void save(PageData pd) throws Exception {
		dao.save("InvitationsMapper.save", pd);
	}

	
	/*
	 * 新增das
	 */
	public void saveBasic(PageData pd) throws Exception {
		dao.save("InvitationsMapper.saveBasic", pd);
	}
	
	/*
	 * 删除
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("InvitationsMapper.delete", pd);
	}

	/*
	 * 修改
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("InvitationsMapper.edit", pd);
	}
	
	/*
	 * 修改两段文字
	 */
	public void edittwo(PageData pd) throws Exception {
		dao.update("InvitationsMapper.edittwo", pd);
	}

	/*
	 * 列表
	 */
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("InvitationsMapper.datalistPage", page);
	}

	/*
	 * 列表(全部)
	 */
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("InvitationsMapper.listAll", pd);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("InvitationsMapper.findById", pd);
	}
	
	/*
	 * 通过create_user获取数据id
	 */
	public List<PageData> judgeByCreateUser(PageData pd) throws Exception {
		return  (List<PageData>) dao.findForList("InvitationsMapper.judgeByCreateUser", pd);
	}

	/*
	 * 批量删除
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("InvitationsMapper.deleteAll", ArrayDATA_IDS);
	}

	public PageData findByUserId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("InvitationsMapper.findByUserId", pd);
	}

	/***
	 * 微喜帖反馈人数增加
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void addInvitionsItem(PageData pd) throws Exception {
		if (pd != null) {
			/*
			 * PageData pagedata=findBytelNumber(pd); //修改 if (pagedata!=null){
			 * editInvitationsInfo(pd); } //添加 else {
			 */
			pd.put("id", UuidUtil.get32UUID());
			saveInvitationsInfo(pd);
			/* } */
		}

	}

	/**
	 * 根据创建者id 手机号码查询 参加人数是否已经存在
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findBytelNumber(PageData pd) throws Exception {
		return (PageData) dao.findForObject("InvitationsInfoMapper.findBytelNumber", pd);
	}
	
	/**
	 * 根据微喜帖关联ID 查询创建微喜帖用户的groupId
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findInviteGroupId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("InvitationsInfoMapper.findInviteGroupId", pd);
	}
	

	/*
	 * 新增
	 */
	public void saveInvitationsInfo(PageData pd) throws Exception {
		dao.save("InvitationsInfoMapper.saveInvitationsInfo", pd);
	}

	public void editInvitationsInfo(PageData pd) throws Exception {
		dao.update("InvitationsInfoMapper.editInvitationsInfo", pd);
	}

	public List<PageData> selectInvitionsItemByUserId(Page page ) throws Exception {
		return (List<PageData>) dao.findForList("InvitationsInfoMapper.datalistPage", page);
	}

	public PageData selectInvitionsItemTotalPerson(PageData pd) throws Exception {
		return (PageData) dao.findForObject("InvitationsInfoMapper.selectInvitionsItemTotalPerson", pd);
	}
	
	public PageData selectInvitionsItemByUnionid(PageData pd) throws Exception {
		return (PageData) dao.findForObject("InvitationsInfoMapper.selectInvitionsItemByUnionid", pd);
	}

	public void deleteInvitations(PageData pd) throws Exception {
		pd.put("invitations_id", pd.get("id"));
		dao.delete("InvitationsInfoMapper.deleteInvitationsInfo", pd);
		dao.delete("InvitationsMapper.deleteInvitations", pd);
	}
}
