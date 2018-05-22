package com.huixin.framework.wx.hongbao;

import java.io.Serializable;

public class HongBaoBean implements Comparable<HongBaoBean>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private float money;// 抢到的金额
	private boolean flag;// 是否为手气最佳
	private String text;	//消息内容
	private int status; //状态
	private String massage;
	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	@Override
	public int compareTo(HongBaoBean hb) {
		 // 先按age排序
        if (this.money > hb.getMoney()) {
            return 1;
        }
        if (this.money < hb.getMoney()) {
            return -1;
        }
        return 0;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMassage() {
		return massage;
	}

	public void setMassage(String massage) {
		this.massage = massage;
	}
	
}
