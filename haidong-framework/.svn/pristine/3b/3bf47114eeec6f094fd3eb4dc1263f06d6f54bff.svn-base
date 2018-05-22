/**
 * 
 */
package com.huixin.framework.wx.hongbao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.huixin.framework.constant.HongBaoConstants;
import com.huixin.framework.exception.BusinessException;
import com.huixin.framework.redis.RedisUtil;

/**
 * @author Administrator
 *
 */
public class HongBaoUtils {

	public static void main(String[] args) throws BusinessException {
//		byte[] by = RedisUtil.getInstance(3).getByte("MapTest1");
//		List<HongBaoBean> hbList = (List<HongBaoBean>) SerializeUtil.unserialize(by);
//		for (int j = 0; j < hbList.size(); j++) {
//			HongBaoBean hb = hbList.get(j);
//			System.out.println(hb.getMoney() + " :" + hb.isFlag());
//		}
//		System.out.println("==============================");
		for (int i = 0; i < 5; i++) {
			HongBaoBean hb = getHongBao("MapTest1");
			System.out.println(hb.getMoney() + " :" + hb.isFlag());
		}
	}

	public static synchronized HongBaoBean getHongBao(String id) throws BusinessException {
		byte[] by = RedisUtil.getInstance(3).getByte(id);
		HongBaoBean result = new HongBaoBean();
		if (null == by) {
			setNullHongBao(result);
			return result;
			//throw new BusinessException(HongBaoConstants.LIST_NULL, "红包已抢完！", null);
		}
		List<HongBaoBean> hbList = (List<HongBaoBean>) SerializeUtil.unserialize(by);
		if (0 == hbList.size()) {
			setNullHongBao(result);
			return result;
			//throw new BusinessException(HongBaoConstants.LIST_NULL, "红包已抢完！", null);
		}
		Random r = new Random();
		int hit = 0;
		if (hbList.size() > 0) {
			hit = r.nextInt(hbList.size());
		}
		result = hbList.get(hit);
		result.setMassage("已存入钱包.");
		result.setText("嗨币");
		result.setStatus(1);
		hbList.remove(hit);
		setHongBaoToRedis(id, hbList);
		return result;
	}

	public static synchronized HongbaoBeanNew getHongBaoNew(String id) throws BusinessException {
		byte[] by = RedisUtil.getInstance(3).getByte(id);
		HongbaoBeanNew result = new HongbaoBeanNew();
		if (null == by) {
			setNullHongBaoNew(result);
			return result;
		}
		List<HongbaoBeanNew> hbList = (List<HongbaoBeanNew>) SerializeUtil.unserialize(by);
		if (0 == hbList.size()) {
			setNullHongBaoNew(result);
			return result;
		}
		Random r = new Random();
		int hit = 0;
		if (hbList.size() > 0) {
			hit = r.nextInt(hbList.size());
		}
		result = hbList.get(hit);
		result.setMassage("已存入钱包.");
		result.setText("嗨币");
		result.setStatus(1);
		hbList.remove(hit);
		setHongBaoToRedisNew(id, hbList);
		return result;
	}
	
	public static HongBaoBean getRedPacketStatus(String id) throws BusinessException {
		byte[] by = RedisUtil.getInstance(3).getByte(id);
		HongBaoBean result = new HongBaoBean();
		if (null == by) {
			setNullHongBao(result);
			return result;
			//throw new BusinessException(HongBaoConstants.LIST_NULL, "红包已抢完！", null);
		}
		List<HongBaoBean> hbList = (List<HongBaoBean>) SerializeUtil.unserialize(by);
		if (0 == hbList.size()) {
			setNullHongBao(result);
			return result;
			//throw new BusinessException(HongBaoConstants.LIST_NULL, "红包已抢完！", null);
		}
		Random r = new Random();
		int hit = 0;
		if (hbList.size() > 0) {
			result.setStatus(1);
		}
		return result;
	}

	private static void setNullHongBao(HongBaoBean result) {
		result.setText("红包已抢完.");
		result.setMassage("下次早点来噢.");
		result.setStatus(0);
	}
	private static void setNullHongBaoNew(HongbaoBeanNew result) {
		result.setText("红包已抢完.");
		result.setMassage("下次早点来噢.");
		result.setStatus(0);
	}

	/***
	 * 设置红包
	 * 
	 * @param id
	 *            key
	 * @param total
	 *            总金额
	 * @param number
	 *            分数
	 * @throws BusinessException 
	 */
	public static void setHongBao(String id, float total, int number) throws BusinessException {
		List<HongBaoBean> hbList = getHongBaoList(total, number);
		setHongBaoToRedis(id, hbList);
	}
	public static void setHongBaoNew(String id, double total, int number) throws BusinessException {
		List<HongbaoBeanNew> hbList = getHongBaoListNew(total, number);
		setHongBaoToRedisNew(id, hbList);
	}

	/***
	 * 设置到redis
	 * 
	 * @param id
	 * @param hbList
	 */
	private static void setHongBaoToRedis(String id, List<HongBaoBean> hbList) {
		if (hbList.size() > 0) {
			RedisUtil.getInstance(3).setBytes(id, SerializeUtil.serialize(hbList));
		} else {
			RedisUtil.getInstance(3).del(id);
		}
	}

	private static void setHongBaoToRedisNew(String id, List<HongbaoBeanNew> hbList) {
		if (hbList.size() > 0) {
			RedisUtil.getInstance(3).setBytes(id, SerializeUtil.serialize(hbList));
		} else {
			RedisUtil.getInstance(3).del(id);
		}
	}

	/***
	 * 获取红包列表
	 * 
	 * @param total
	 *            总金额
	 * @param number
	 *            分数
	 * @return 分配好的红包集合
	 * @throws BusinessException 
	 */
	private static List<HongbaoBeanNew> getHongBaoListNew(double total, int number) throws BusinessException {
		List<HongbaoBeanNew> result = new ArrayList<>();
		//抢到的总金额
		double money;
		double min = 0.01;
		double max;
		int i = 1;
		//用户发红包个数为1个
		if(i == number){
			HongbaoBeanNew hb = new HongbaoBeanNew();
			hb.setMoney(BigDecimal.valueOf(total));
			hb.setFlag(true);
			result.add(hb);
			return result;
		}
		//多个红包
		if(number > i){
			while (i < number){
				max = total - min * (number - i);
				int k = (int) ((number - i) / 2);
				if (number - i <= 2) {
					k = number - i;
				}
				max = max / k;
				money = (min * 100 + Math.random() * (max * 100 - min * 100 + 1));
				HongbaoBeanNew hb = new HongbaoBeanNew();
				money = getFormatNew((float) money / 100);
				total = getFormatNew(total - money);
				hb.setMoney(BigDecimal.valueOf(money));
				result.add(hb);
				i++;
				if (i == number) {
					hb = new HongbaoBeanNew();
					hb.setMoney(BigDecimal.valueOf(total));
					result.add(hb);
				}
			}
			// 最佳手气
			int index = (result.indexOf(Collections.max(result)) + 1);
			HongbaoBeanNew hb = result.get(index - 1);
			hb.setFlag(true);
			result.set(index - 1, hb);
			return result;
		}
		return result;
	}

	private static List<HongBaoBean> getHongBaoList(float total, int number) throws BusinessException {
		// 抢到的总金额
		float money;
		double min = 0.01;
		double max;
		int i = 1;
		//		float sum = 0;
		List<HongBaoBean> hbList = new ArrayList<HongBaoBean>();
			if (i == number) {
			HongBaoBean hb = new HongBaoBean();
			hb.setMoney(total);
			hbList.add(hb);
		} else  if (number > i){
			while (i < number) {
				max = total - min * (number - i);
				int k = (int) ((number - i) / 2);
				if (number - i <= 2) {
					k = number - i;
				}
				max = max / k;
				money = (int) (min * 100 + Math.random() * (max * 100 - min * 100 + 1));
				HongBaoBean hb = new HongBaoBean();
				money = getFormat((float) money / 100);
				total = getFormat(total - money);
				hb.setMoney(money);
				hbList.add(hb);
	//				sum += money;
	//				 System.out.println("1第" + i + "个人拿到" + money + "剩下" + total);
				i++;
				if (i == number) {
					hb = new HongBaoBean();
					hb.setMoney(total);
					hbList.add(hb);
	//					sum += total;
	//					 System.out.println("2第" + i + "个人拿到" + total + "剩下0");
				}
			}
		} else {
			throw new BusinessException(HongBaoConstants.PAY_ERROR, "发红包出错！", null);
		}
		// 最佳手气
		int index = (hbList.indexOf(Collections.max(hbList)) + 1);
		HongBaoBean hb = hbList.get(index - 1);
			hb.setFlag(true);
			hbList.set(index - 1, hb);
	//		System.out.println("本轮发红包中第" + index + "个人手气最佳");
	//		System.out.println(getFormat(sum));
			return hbList;
	}

	private static float getFormat(float param) {
		BigDecimal b = new BigDecimal(param);
		double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return (float) f1;
	}

	private static double getFormatNew(double param) {
		BigDecimal b = new BigDecimal(param);
		double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return f1;
	}
}
