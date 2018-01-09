package com.qcloud.liveshow.beans;

/**
 * 类说明：钻石币记录
 * Author: iceberg
 * Date: 2018/1/8.
 */
public class DiamondsRecordBean {
   int balance;//	钻石币余额	number
   String time;	//时间	string
   String typeName;//	类型名称	string
   int virtualCoin;//	该记录钻石币数	number

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getVirtualCoin() {
        return String.valueOf(virtualCoin);
    }

    public void setVirtualCoin(int virtualCoin) {
        this.virtualCoin = virtualCoin;
    }

    @Override
    public String toString() {
        return "DiamondsRecordBean{" +
                "balance=" + balance +
                ", time='" + time + '\'' +
                ", typeName='" + typeName + '\'' +
                ", virtualCoin=" + virtualCoin +
                '}';
    }
}
