package com.huixin.haidong.service.system.business.money;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huixin.framework.constant.PayConstants;
import com.huixin.framework.exception.BusinessException;
import com.huixin.framework.redis.RedisUtil;
import com.huixin.framework.utils.Const;
import com.huixin.framework.utils.DateUtil;
import com.huixin.framework.utils.GiftConst;
import com.huixin.framework.utils.Logger;
import com.huixin.framework.utils.PageData;
import com.huixin.framework.utils.RSATool;
import com.huixin.framework.utils.RandomUtil;
import com.huixin.framework.utils.UuidUtil;
import com.huixin.framework.wx.util.HttpUtils;
import com.huixin.haidong.service.system.business.dictionary.DictionaryService;
import com.huixin.system.dao.DaoSupport;
import com.huixin.system.entity.Page;

import net.sf.json.JSONObject;

/**
 * 类名称：moneyService 创建人：system 创建时间：2016-06-30
 */
@Service("moneyService")
public class MoneyService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Resource(name = "dictionaryService")
	private DictionaryService dictionaryService;

	/*
	 * 新增
	 */
	public void save(PageData pd) throws Exception {
		String defaultGold = dictionaryService.getValue(Const.DEFAULTGOLD);
		pd.put("gold", defaultGold);
		dao.save("MoneyMapper.save", pd);
	}

	/*
	 * 删除
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("MoneyMapper.delete", pd);
	}

	/*
	 * 修改
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("MoneyMapper.edit", pd);
	}

	/*
	 * 增加余额
	 */
	public void plusMoney(PageData pd) throws Exception {
		dao.update("MoneyMapper.plusMoney", pd);
	}

	/*
	 * 扣除余额
	 */
	public void minMoney(PageData pd) throws Exception {
		dao.update("MoneyMapper.minMoney", pd);
	}

	/*
	 * 增加RMB
	 */
	public void plusRMB(PageData pd) throws Exception {
		dao.update("MoneyMapper.plusRMB", pd);
	}

	/*
	 * 扣除RMB
	 */
	public void minRMB(PageData pd) throws Exception {
		dao.update("MoneyMapper.minRMB", pd);
	}

	/*
	 * 通过id获取嗨币余额
	 */
	public PageData getGold(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MoneyMapper.getGold", pd);
	}

	/*
	 * 列表
	 */
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("MoneyMapper.datalistPage", page);
	}

	/*
	 * 列表(全部)
	 */
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MoneyMapper.listAll", pd);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MoneyMapper.findById", pd);
	}

	/*
	 * 批量删除
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("MoneyMapper.deleteAll", ArrayDATA_IDS);
	}

	/*
	 * 充值记录查询
	 */
	public List<PageData> saveHb(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MoneyMapper.saveHb", pd);
	}

	// 查看个人的账户信息
	public PageData findByuserid(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MoneyMapper.findByuserid", pd);
	}

	// 获取个人嗨币余额
	public PageData getUserMoney(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MoneyMapper.getUserMoney", pd);
	}

	/**
	 * 保持数据一致性 修改订单状态 根据 out_trade_no 流水表中记录充值数据 根据充值金额增加个人嗨豆
	 * 
	 * @param pd
	 * @throws Exception
	 */

	/**
	 * 用户提现逻辑 前台传来:提现金额,用户id,提现账号 先检查前台传来的值是否为空 检查用户余额是否大于提现金额
	 * 在用户流水表增加数据tb_account 状态为审核中 在用户余额表中扣除提现金额 tb_money
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject updateWithdrawcash(PageData pd) throws Exception {
		JSONObject jsonObject = new JSONObject();
		if (pd.getString("totalfee") == null || "".equals(pd.getString("totalfee"))) {
			jsonObject.put("code", 100);
			return jsonObject;
		}
		try {
			if (Integer.valueOf(pd.getString("totalfee")) < 100) {
				jsonObject.put("code", 100);
				return jsonObject;
			}
		} catch (NumberFormatException e) {
			jsonObject.put("code", 100);
			throw new Exception("转换异常");
		}
		if (pd.getString("USER_ID") == null || "".equals(pd.getString("USER_ID"))) {
			jsonObject.put("code", 100);
			return jsonObject;
		}
		if (pd.getString("cashNumber") == null || "".equals(pd.getString("cashNumber"))) {
			jsonObject.put("code", 100);
			return jsonObject;
		}
		pd.put("userid", pd.getString("USER_ID"));
		PageData data = findByuserid(pd);
		// 余额大于提现金额
		try {
			if ((data.getBigDecimal("gold").compareTo(BigDecimal.valueOf(pd.getDouble("totalfee")))) == -1) {
				jsonObject.put("code", 100);
			} else {
				pd.put("type", "2");
				pd.put("id", UuidUtil.get32UUID());
				pd.put("userId", pd.getString("USER_ID"));
				dao.save("AccountMapper.save", pd);
				pd.put("gold", Integer.valueOf(pd.getString("totalfee")));
				dao.update("MoneyMapper.minMoney", pd);
				jsonObject.put("code", 200);
			}
		} catch (Exception e) {
			jsonObject.put("code", 100);
			throw new Exception("转换异常");
		}
		// 根据ID读取
		return jsonObject;
	}

	/**
	 * 获取商城订单ID 获取订单价格 获取用户ID 用户余额支付 error Unknown column 'shopOrderId' in
	 * 'where clause'
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject shoppay(PageData pd) throws Exception {
		JSONObject jsonObject = new JSONObject();
		if (pd.getString("totalfee") == null || "".equals(pd.getString("totalfee"))) {
			jsonObject.put("code", 100);
			return jsonObject;
		}
		if (pd.getString("orderId") == null || "".equals(pd.getString("orderId"))) {
			jsonObject.put("code", 100);
			return jsonObject;
		}
		if (pd.getString("userid") == null || "".equals(pd.getString("userid"))) {
			jsonObject.put("code", 100);
			return jsonObject;
		}
		pd.put("shopOrderId", pd.getString("orderId"));
		PageData dataOrder = findByShopOrderId(pd);
		if (dataOrder != null) {
			// 已支付
			jsonObject.put("code", 400);
			return jsonObject;
		}
		PageData data = findByuserid(pd);
		// 余额大于提现金额
		try {
			if ((data.getBigDecimal("gold").compareTo(BigDecimal.valueOf(pd.getDouble("totalfee")))) == -1) {
				// 余额不足
				jsonObject.put("code", 300);
			} else {
				pd.put("type", "3");
				pd.put("id", UuidUtil.get32UUID());
				pd.put("userId", pd.getString("userid"));
				pd.put("state", "1");
				dao.save("AccountMapper.save", pd);
				pd.put("gold", Integer.valueOf(pd.getString("totalfee")));
				pd.put("USER_ID", pd.getString("userid"));
				dao.update("MoneyMapper.minMoney", pd);
				jsonObject.put("code", 200);
			}
		} catch (Exception e) {
			throw new BusinessException(PayConstants.PAY_ERROR, "充值出错", pd);
		}
		return jsonObject;
	}

	private PageData findByShopOrderId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AccountMapper.findByShopOrderId", pd);
	}

	public void updateshopOrder(PageData pd) throws Exception {
		// 修改订单状态 根据 out_trade_no
		pd.put("state", "1");
		pd.put("timeend", DateUtil.getTime(DateUtil.fomatDate((String) pd.get("timeend"), "yyyyMMddHHmmss")));
		dao.update("AccountMapper.update", pd);

	}

	public void addbaitiao(PageData pd) throws Exception {
		dao.save("BaitiaoMapper.save", pd);
	}

	public PageData reloadbaitiao(PageData pd) throws Exception {
		return (PageData) dao.findForObject("BaitiaoMapper.reloadbaitiao", pd);
	}

	public PageData selectIap(PageData pd) throws Exception {
		return (PageData) dao.findForObject("IapMapper.selectIap", pd);
	}

	/**
	 * ios 内置充值 1现金表里增加充值记录 2流水表增加记录 3增加用户相应嗨豆
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void iapchongzhi(PageData pd) throws Exception {
		// 根据productid 查询
		PageData pageIap = selectIap(pd);
		if (pageIap != null) {
			PageData pdAccount = new PageData();
			pdAccount.put("id", UuidUtil.get32UUID());
			pdAccount.put("userId", pd.get("USER_ID"));
			pdAccount.put("outtradeno", "");
			pdAccount.put("totalfee", pageIap.get("gold"));
			// 1充值 2提现 3ios 充值
			pdAccount.put("type", "3");
			pdAccount.put("state", "1");
			dao.save("AccountMapper.save", pdAccount);
			PageData pdAllwater = new PageData();
			pdAllwater.put("id", UuidUtil.get32UUID()); // 主键
			pdAllwater.put("status", "2");
			pdAllwater.put("USER_ID", pd.get("USER_ID"));
			pdAllwater.put("waterid", "");// 充值嗨币流水号
			pdAllwater.put("gold", pageIap.get("gold"));
			dao.save("AllwaterMapper.save", pdAllwater);
			// 根据充值金额增加个人嗨豆
			dao.update("MoneyMapper.plusMoney", pdAllwater);
		}

	}

	/**
	 * 后台实时更新账户余额，及直播礼物收入，返回嗨币余额(事务控制)
	 * 
	 * @param giftId
	 * @return
	 * @throws Exception
	 */
	public synchronized JSONObject operateGold(String giftId, PageData pd) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String userId = pd.getString("USER_ID");
		String RoomId = RedisUtil.getInstance().hget(userId, "roomId"); // 从缓存中获取房间号
		// String RoomId = "2fdbcd1cec9542b9bf91564f406ae0b9";
		String giftMoney = GiftConst.getGiftMoney(giftId); // 根据礼物id获取礼物对应的金额
		PageData goldInfo = new PageData();
		goldInfo.put("gold", new BigDecimal(giftMoney));
		goldInfo.put("USER_ID", userId);
		PageData receiveGift = new PageData();
		receiveGift.put("id", RoomId);
		receiveGift.put("gift", new BigDecimal(giftMoney));
		PageData updateGold = pd;
		updateGold.put("USER_ID", userId);
		PageData oldGold = new PageData();

		// 封装礼物记录，实时刷新到数据库
		String waterId = RandomUtil.getTradeId(); // 生成流水号，当key存储
		// Map<String, String> giftInfo = new HashMap<String, String>();
		PageData giftInfo = new PageData();
		giftInfo.put("USER_ID", userId);
		giftInfo.put("giftid", giftId);
		giftInfo.put("liveid", RoomId); // 对应的直播房间号
		giftInfo.put("deal_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); // 交易时间
		giftInfo.put("status", "0"); // 0:礼物消费 1:礼物收入
		giftInfo.put("waterid", waterId); // 存入流水号
		giftInfo.put("id", UuidUtil.get32UUID());
		giftInfo.put("gold", new BigDecimal(giftMoney));
		// TODO 后面要加事务控制，保证消费与收入的数据一致性
		try {
			oldGold = getGold(updateGold); // 获取余额，判断是否大于礼物金额
			BigDecimal gold = oldGold.getBigDecimal("gold"); // 前端余额
			if (gold.compareTo(BigDecimal.valueOf(Double.parseDouble(giftMoney))) == -1) {
				jsonObject.put("data", "余额不足，请充值!");
				jsonObject.put("code", 100);
			} else {
				dao.update("MoneyMapper.minMoney", goldInfo); // 用户余额更新到数据库
				dao.update("LiveMapper.updateGift", receiveGift); // 房间礼物收入实时更新
				BigDecimal newGold = gold.subtract(BigDecimal.valueOf(Double.parseDouble(giftMoney)));
				oldGold.put("gold", newGold);
				dao.save("AllwaterMapper.saveGift", giftInfo); // 礼物记录实时更新到数据库
				jsonObject.put("data", oldGold);
				jsonObject.put("code", 200);
			}
		} catch (Exception e) {
			jsonObject.put("message", e);
			throw new BusinessException("300", "发礼物出错!", pd);
		}
		return jsonObject;
	}

	public void updateBackPcOrder(PageData pd) throws Exception {
		dao.update("MoneyMapper.plusRMB", pd);
	}

	public void updateXiaofeiPcOrder(PageData pd) throws Exception {
		dao.update("MoneyMapper.xiaofeiRMB", pd);
	}

}
