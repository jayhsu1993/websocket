package com.huixin.haidong.serice.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huixin.framework.utils.PageData;
import com.huixin.haidong.service.system.business.vote.VoteDao;
import com.huixin.haidong.service.system.business.vote.VoteService;

@Service("voteService")
public class VoteServiceImpl implements VoteService {
	private static final Logger LOGGER = LoggerFactory.getLogger(VoteServiceImpl.class);
	
	@Autowired
	private VoteDao voteDao;
	
	public List<PageData> vote_list(PageData pd) throws Exception {
		return voteDao.listAllByLiveId(pd);
	}

	public void updateVoteCount(PageData pd) throws Exception {
		voteDao.updateCount(pd);
	}

}