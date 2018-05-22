package com.huixin.system.entity.chat;

/**
 * 
 * @author wuxiang date:2016.8.1 房间登陆返回信息
 */
public class LoginInfo extends Data {

	private String countNum;	// 房间总人数
	private String photoUrl;	// 头像地址
	private String userName;	// 用户名
	private String gender; 		// 用户性别 1:男 2:女
	private String type;		// 客户端类别 1:普通用户 2:大屏
	private String wish;		// 祝福语
	private int state;			// 用户登录状态
	private String money;       //用户份子钱
	private String userId;		//用户

	public String getCountNum() {
		return countNum;
	}

	public void setCountNum(String countNum) {
		this.countNum = countNum;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWish() {
		return wish;
	}

	public void setWish(String wish) {
		this.wish = wish;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}


	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	

}
