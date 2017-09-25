package com.qcloud.liveshow.beans;

/**
 * 类说明：收益
 * Author: Kuzan
 * Date: 2017/9/25 11:39.
 */
public class ProfitBean {
    double nowEarnings;         // 当前收益
    double sumEarnings;         // 累计收益
    double giftEarnings;        // 礼物收益
    double generalizeEarnings;  // 推广收益
    String gainSharing;         // 收益分成 (后台拼接"%")

    public double getNowEarnings() {
        return nowEarnings;
    }

    public String getNowEarningsStr() {
        return String.valueOf(nowEarnings);
    }

    public void setNowEarnings(double nowEarnings) {
        this.nowEarnings = nowEarnings;
    }

    public double getSumEarnings() {
        return sumEarnings;
    }

    public void setSumEarnings(double sumEarnings) {
        this.sumEarnings = sumEarnings;
    }

    public double getGiftEarnings() {
        return giftEarnings;
    }

    public void setGiftEarnings(double giftEarnings) {
        this.giftEarnings = giftEarnings;
    }

    public double getGeneralizeEarnings() {
        return generalizeEarnings;
    }

    public void setGeneralizeEarnings(double generalizeEarnings) {
        this.generalizeEarnings = generalizeEarnings;
    }

    public String getGainSharing() {
        return gainSharing;
    }

    public void setGainSharing(String gainSharing) {
        this.gainSharing = gainSharing;
    }

    @Override
    public String toString() {
        return "ProfitBean{" +
                "nowEarnings=" + nowEarnings +
                ", sumEarnings=" + sumEarnings +
                ", giftEarnings=" + giftEarnings +
                ", generalizeEarnings=" + generalizeEarnings +
                ", gainSharing='" + gainSharing + '\'' +
                '}';
    }
}
