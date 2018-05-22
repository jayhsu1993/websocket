package com.huixin.haidong.service.system.admin.appuser;

import java.util.List;

import javax.annotation.Resource;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import com.huixin.framework.grouproom.chat.EasemobChatGroups;
import com.huixin.framework.grouproom.chat.EasemobIMUsers;
import com.huixin.framework.utils.HttpClientUtil;
import com.huixin.framework.utils.PageData;
import com.huixin.framework.utils.UuidUtil;
import com.huixin.framework.wx.util.PropertiesUtil;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;

/***
 * origin 1:微信注册,2手机注册
 * 
 * @author X.Y.CHEN
 *
 *         2016年11月1日
 */
@Service("appuserService")
public class AppuserService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	// ======================================================================================

	/*
	 * 通过id获取数据
	 */
	public PageData findByUiId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AppuserMapper.findByUiId", pd);
	}

	/*
	 * 通过loginname获取数据
	 */
	public PageData findByUId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AppuserMapper.findByUId", pd);
	}

	/*
	 * 通过邮箱获取数据
	 */
	public PageData findByUE(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AppuserMapper.findByUE", pd);
	}

	/*
	 * 通过编号获取数据
	 */
	public PageData findByUN(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AppuserMapper.findByUN", pd);
	}

	/*
	 * 保存用户
	 */
	public void saveU(PageData pd) throws Exception {
		dao.save("AppuserMapper.saveU", pd);
	}

	/*
	 * 修改用户
	 */
	public void editU(PageData pd) throws Exception {
		dao.update("AppuserMapper.editU", pd);
	}

	/*
	 * 删除用户
	 */
	public void deleteU(PageData pd) throws Exception {
		dao.delete("AppuserMapper.deleteU", pd);
	}

	/*
	 * 批量删除用户
	 */
	public void deleteAllU(String[] USER_IDS) throws Exception {
		dao.delete("AppuserMapper.deleteAllU", USER_IDS);
	}

	/*
	 * 用户列表(全部)
	 */
	public List<PageData> listAllUser(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AppuserMapper.listAllUser", pd);
	}

	/*
	 * 用户列表(用户组)
	 */
	public List<PageData> listPdPageUser(Page page) throws Exception {
		return (List<PageData>) dao.findForList("AppuserMapper.userlistPage", page);
	}

	/*
	 * 保存用户IP
	 */
	public void saveIP(PageData pd) throws Exception {
		dao.update("AppuserMapper.saveIP", pd);
	}

	/*
	 * 登录判断
	 */
	public PageData getUserByNameAndPwd(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AppuserMapper.getUserInfo", pd);
	}

	/*
	 * 跟新登录时间
	 */
	public void updateLastLogin(PageData pd) throws Exception {
		dao.update("AppuserMapper.updateLastLogin", pd);
	}
	// ======================================================================================

	/***
	 * 添加商城.net用户
	 * 
	 * @param uuid
	 * @return
	 * @throws Exception
	 */
	public PageData saveNetUser(String uuid) throws Exception {
		PageData result = new PageData();
		String netUrl = PropertiesUtil.getValue("config.properties", "net_url");
		String str = HttpClientUtil.reqHttp(netUrl + "users.ashx?op=createUser",
				new NameValuePair[] { new BasicNameValuePair("UUID", uuid), new BasicNameValuePair("nickName", ""),
						new BasicNameValuePair("headImg", "") });
		if (null == str) {
			result.put("Code", "500");
			result.put("message", "连接出错");
		} else {
			result = PageData.toHashMap(str);
		}
		return result;
	}

}
