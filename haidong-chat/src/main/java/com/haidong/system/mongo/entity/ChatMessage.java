package com.haidong.system.mongo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by mhq on 16/10/28.
 */
@Document
public class ChatMessage {

    public ChatMessage(){}


    public ChatMessage(String roomId, String userId, String money, String text, String userName,
			String messageType, String gender, String photoUrl) {
		this.roomId = roomId;
		this.userId = userId;
		this.money = money;
		this.text = text;
		this.userName = userName;
		this.messageType = messageType;
		this.gender = gender;
		this.photoUrl = photoUrl;
	}


	@Id
    private String id;

    @Indexed
    private String roomId;
    private String userId;

	private String text;		//消息内容
	private String userName;	//用户名
	private String money;		//红包金额
	private String messageType;	//消息类型   1:普通消息  2:祝福消息
	private String gender;		//性别 1:男 2:女
	private String photoUrl;	//头像地址

    @Indexed(direction = IndexDirection.DESCENDING)
    private Date createTime;
    
    //private LocalDateTime createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

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

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}




	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	@Override
	public String toString() {
		return "ChatMessage [id=" + id + ", roomId=" + roomId + ", userId=" + userId + ", text=" + text + ", userName="
				+ userName + ", money=" + money + ", messageType=" + messageType + ", gender=" + gender + ", photoUrl="
				+ photoUrl + ", createTime=" + createTime + "]";
	}
	
}
