package com.huixin.system.entity.chat;

/**
 * 遥控器实体类
 * @author wuxiang
 *
 * 2018年3月28日
 */
public class Telecontroller extends Data{
	private String action_code;
	private String action_state;
	private String action_param;
	
	public String getAction_code() {
		return action_code;
	}
	public void setAction_code(String action_code) {
		this.action_code = action_code;
	}
	public String getAction_state() {
		return action_state;
	}
	public void setAction_state(String action_state) {
		this.action_state = action_state;
	}
	public String getAction_param() {
		return action_param;
	}
	public void setAction_param(String action_param) {
		this.action_param = action_param;
	}
	
	
}
