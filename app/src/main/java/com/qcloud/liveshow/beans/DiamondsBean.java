package com.qcloud.liveshow.beans;

import com.qcloud.qclib.utils.StringUtils;

/**
 * 类说明：钻石币
 * Author: Kuzan
 * Date: 2017/9/13 10:35.
 */
public class DiamondsBean {
    long id;            // id
    String name;        // 套餐名称
    String appleID;     // 苹果套餐ID
    int virtualCoin;    // 虚拟币
    double money;       // 金额
    int ordinal;        // 顺序
    boolean isSelect;   // 是否选中

    public long getId() {
        return id;
    }
    public String getIdStr() {
        return String.valueOf(id);
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return StringUtils.isEmptyString(name) ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppleID() {
        return appleID;
    }

    public void setAppleID(String appleID) {
        this.appleID = appleID;
    }

    public int getVirtualCoin() {
        return virtualCoin;
    }

    public void setVirtualCoin(int virtualCoin) {
        this.virtualCoin = virtualCoin;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public void refreshSelect() {
        isSelect = !isSelect;
    }

    @Override
    public String toString() {
        return "DiamondsBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", appleID='" + appleID + '\'' +
                ", virtualCoin=" + virtualCoin +
                ", money=" + money +
                ", ordinal=" + ordinal +
                ", isSelect=" + isSelect +
                '}';
    }
}
