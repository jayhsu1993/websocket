/**
 * 
 */
package com.huixin.framework.wx.lottery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.huixin.framework.redis.RedisUtil;

/**
 * @author Administrator
 *
 */
public class LotteryUtil {

	public static LotteryGift getLottery() {
		List<LotteryGift> gifts = new ArrayList<LotteryGift>();
		// 序号==物品Id==物品名称==概率
		gifts.add(new LotteryGift(1, "gift", "20", 0, 0.06d));
//		gifts.add(new LotteryGift(2, "sendLottery", "30", 0, 0.006d));
//		gifts.add(new LotteryGift(3, "redPacket", "100", 0, 0.001d));
	

		List<Double> orignalRates = new ArrayList<Double>(gifts.size());
		for (LotteryGift gift : gifts) {
			double probability = gift.getProbability();
			if (probability < 0) {
				probability = 0;
			}
			orignalRates.add(probability);
		}

		// statistics
		Map<Integer, Integer> count = new HashMap<Integer, Integer>();
		double num = 10;
		int orignalIndex = LotteryUtil.lottery(orignalRates);

		Integer value = count.get(orignalIndex);
		count.put(orignalIndex, value == null ? 1 : value + 1);
		double sum = 0;
		return gifts.get(orignalIndex);
	}

	/**
	 * 抽奖
	 *
	 * @param orignalRates
	 *            原始的概率列表，保证顺序和实际物品对应
	 * @return 物品的索引
	 */
	public static int lottery(List<Double> orignalRates) {
		if (orignalRates == null || orignalRates.isEmpty()) {
			return -1;
		}

		int size = orignalRates.size();

		// 计算总概率，这样可以保证不一定总概率是1
		double sumRate = 0d;
		for (double rate : orignalRates) {
			sumRate += rate;
		}

		// 计算每个物品在总概率的基础下的概率情况
		List<Double> sortOrignalRates = new ArrayList<Double>(size);
		Double tempSumRate = 0d;
		for (double rate : orignalRates) {
			tempSumRate += rate;
			sortOrignalRates.add(tempSumRate / sumRate);
		}

		// 根据区块值来获取抽取到的物品索引
		double nextDouble = Math.random();
		sortOrignalRates.add(nextDouble);
		Collections.sort(sortOrignalRates);

		return sortOrignalRates.indexOf(nextDouble);
	}
}
