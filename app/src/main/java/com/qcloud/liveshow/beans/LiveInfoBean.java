package com.qcloud.liveshow.beans;

/**
 * 类说明：获取直播前的信息
 * Author: iceberg
 * Date: 2017/10/27.
 */
public class LiveInfoBean {
    String cover;//	封面
    String coverUrl;//	封面Url
    String notice;//	公告
    int rates;//房间设置收费标准
    String ratesEndTime;//	收费结束时间
    String ratesStartTime;//收费开始时间
    String title;//标题
    int upperLimit;//直播间收费上限

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public int getRates() {
        return rates;
    }

    public void setRates(int rates) {
        this.rates = rates;
    }

    public String getRatesEndTime() {
        return ratesEndTime;
    }

    public void setRatesEndTime(String ratesEndTime) {
        this.ratesEndTime = ratesEndTime;
    }

    public String getRatesStartTime() {
        return ratesStartTime;
    }

    public void setRatesStartTime(String ratesStartTime) {
        this.ratesStartTime = ratesStartTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    @Override
    public String toString() {
        return "LiveInfoBean{" +
                "cover='" + cover + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", notice='" + notice + '\'' +
                ", rates=" + rates +
                ", ratesEndTime='" + ratesEndTime + '\'' +
                ", ratesStartTime='" + ratesStartTime + '\'' +
                ", title='" + title + '\'' +
                ", upperLimit='" + upperLimit + '\'' +
                '}';
    }
}
