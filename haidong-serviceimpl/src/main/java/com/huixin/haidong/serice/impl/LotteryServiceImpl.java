package com.huixin.haidong.serice.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huixin.framework.utils.PageData;
import com.huixin.haidong.service.system.business.Lottery.LotteryDao;
import com.huixin.haidong.service.system.business.Lottery.LotteryService;


@Service("lotteryServiceImpl")
public class LotteryServiceImpl implements LotteryService{
	
    @Autowired
    private LotteryDao lotteryDao;


	@Override
	public void saveLotteryData(PageData data) throws Exception {
		lotteryDao.save(data);
	}


	@Override
	public void deleteLotteryData(PageData data) throws Exception {
		lotteryDao.delete(data);
	}


	@Override
	public void deleteByType(PageData data) throws Exception {
		lotteryDao.deleteByType(data);
	}


	@Override
	public List<PageData> listReport(PageData data) throws Exception {
        List<PageData> list = lotteryDao.findReportNoAwardList(data);
        return list;
	}


	@Override
	public List<PageData> listByType(PageData data) throws Exception {
		List<PageData> list = lotteryDao.findReportByType(data);
		return list;
	}


	@Override
	public List<PageData> listNameByType(PageData data) throws Exception {
		List<PageData> list = lotteryDao.listNameByType(data);
		return list;
	}


	@Override
	public List<PageData> listHasAward(PageData data) throws Exception {
		List<PageData> list = lotteryDao.listHasAward(data);
		return list;
	}


	@Override
	public List<PageData> listNameHasAward(PageData data) throws Exception {
		List<PageData> list = lotteryDao.listNameHasAward(data);
		return list;
	}


	@Override
	public void deleteByAll(PageData data) throws Exception {
		lotteryDao.deleteByAll(data);
	}



}
