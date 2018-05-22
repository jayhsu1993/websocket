package com.haidong.system.mongo.dao;

import java.util.List;

import org.springframework.data.domain.Page;

import com.haidong.system.mongo.entity.ChatMessage;

public interface MessageDao {
	//增
	public String save(ChatMessage message);
	//查
	public List<ChatMessage> getByRoomId(String roomId);
	//删
	public void removeByRoomId(String roomId);
	//分页
	public Page<ChatMessage> paginationQuery(String roomId,Integer pageNum, Integer pageSize);
}
