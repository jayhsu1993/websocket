package com.huixin.system.entity.chat;

public class EmceeTip extends Data {
	private String photoUrl;	// 头像地址
	private String userName;	// 用户名
	private String gender; 		// 用户性别 1:男 2:女
	private String totelfee;       //打赏金额
	private String userId;		//用户
	
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getTotelfee() {
		return totelfee;
	}
	public void setTotelfee(String totelfee) {
		this.totelfee = totelfee;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
}
