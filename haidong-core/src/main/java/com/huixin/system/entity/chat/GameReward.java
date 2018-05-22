package com.huixin.system.entity.chat;

import java.util.List;

import com.huixin.framework.utils.PageData;

public class GameReward extends Data {
	
	private String gameCode;
	private List<PageData> reward_user_info;
	
	public String getGameCode() {
		return gameCode;
	}
	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}
	public List<PageData> getReward_user_info() {
		return reward_user_info;
	}
	public void setReward_user_info(List<PageData> reward_user_info) {
		this.reward_user_info = reward_user_info;
	}
	
	
}
