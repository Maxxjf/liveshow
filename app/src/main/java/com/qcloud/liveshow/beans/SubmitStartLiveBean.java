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

    @Override
    public String toString() {
        return "SubmitStartLiveBean{" +
                "cover='" + cover + '\'' +
                ", title='" + title + '\'' +
                ", notice='" + notice + '\'' +
                ", rates='" + rates + '\'' +
                '}';
    }
}
