package com.huixin.system.entity.chat;

public class MessageReturn {
	public String code;	//返回状态码
	public Data data;	//返回数据
	public String message;	//操作结果信息
	public String type;	//对应的操作类型
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
