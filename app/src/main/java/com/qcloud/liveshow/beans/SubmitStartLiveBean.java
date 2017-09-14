package com.qcloud.liveshow.beans;

/**
 * 类说明：提交创建直播间
 * Author: Kuzan
 * Date: 2017/9/14 16:11.
 */
public class SubmitStartLiveBean {
    String cover;       // 封面
    String title;       // 标题
    String notice;      // 公告
    String rates;       // 收费标准
    String feeEndTime;  // 收费结束时间
    String feeStartTime;// 收费开始时间

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getRates() {
        return rates;
    }

    public void setRates(String rates) {
        this.rates = rates;
    }

    public String getFeeEndTime() {
        return feeEndTime;
    }

    public void setFeeEndTime(String feeEndTime) {
        this.feeEndTime = feeEndTime;
    }

    public String getFeeStartTime() {
        return feeStartTime;
    }

    public void setFeeStartTime(String feeStartTime) {
        this.feeStartTime = feeStartTime;
    }

    @Override
    public String toString() {
        return "SubmitStartLiveBean{" +
                "cover='" + cover + '\'' +
                ", title='" + title + '\'' +
                ", notice='" + notice + '\'' +
                ", rates='" + rates + '\'' +
                ", feeEndTime='" + feeEndTime + '\'' +
                ", feeStartTime='" + feeStartTime + '\'' +
                '}';
    }
}
