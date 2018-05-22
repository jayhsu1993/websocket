/**
 * 
 */
package com.huixin.system.entity.chat;

/**
 * @author X.Y.CHEN
 * 红包返回数据
 * 2016年11月1日
 */
public class RedPacket extends Data {
	private String text;	//消息内容
	private String userId;	//用户Id
	private String userName;	//用户名
	private String redpacketId;//红包id
	private String photoUrl;	//头像地址
	private String total;//红包金额
	private String couple;//区分新人红包
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRedpacketId() {
		return redpacketId;
	}
	public void setRedpacketId(String redpacketId) {
		this.redpacketId = redpacketId;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}

	public String getCouple() {
		return couple;
	}

	public void setCouple(String couple) {
		this.couple = couple;
	}
}
