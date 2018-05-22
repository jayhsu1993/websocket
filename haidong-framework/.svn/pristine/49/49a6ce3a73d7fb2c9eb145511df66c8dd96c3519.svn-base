package com.huixin.framework.wx.hongbao;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * HongbaoBeanNew
 *
 * @author Lance
 * @date 2017/04/06
 */
public class HongbaoBeanNew implements Comparable<HongbaoBeanNew>, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private BigDecimal money;// 抢到的金额
    private boolean flag;// 是否为手气最佳
    private String text;	//消息内容
    private int status; //状态
    private String massage;
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public int compareTo(HongbaoBeanNew hb) {
        // 先按age排序
        if (this.money.compareTo(hb.getMoney()) == 1) {
            return 1;
        }
        if (this.money.compareTo(hb.getMoney()) == -1) {
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

