package com.qcloud.liveshow.beans;

/**
 * 类说明：直播结束的收益
 * Author: iceberg
 * Date: 2018/1/2.
 */
public class FinishIncomeBean {
    String earnings;//	当前收益
    String giftEarnings;//礼物收益
    String totalEarnings;//本场收益
    String watchTotal;//观看人数

    public String getEarnings() {
        return earnings;
    }

    public void setEarnings(String earnings) {
        this.earnings = earnings;
    }

    public String getGiftEarnings() {
        return giftEarnings;
    }

    public void setGiftEarnings(String giftEarnings) {
        this.giftEarnings = giftEarnings;
    }

    public String getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(String totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public String getWatchTotal() {
        return watchTotal;
    }

    public void setWatchTotal(String watchTotal) {
        this.watchTotal = watchTotal;
    }

    @Override
    public String toString() {
        return "FinishIncomeBean{" +
                "earnings='" + earnings + '\'' +
                ", giftEarnings='" + giftEarnings + '\'' +
                ", totalEarnings='" + totalEarnings + '\'' +
                ", watchTotal='" + watchTotal + '\'' +
                '}';
    }
}
