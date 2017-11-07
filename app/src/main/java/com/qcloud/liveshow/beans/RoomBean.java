package com.qcloud.liveshow.beans;

import com.qcloud.qclib.utils.StringUtils;

import java.io.Serializable;

/**
 * 类说明：直播间
 * Author: Kuzan
 * Date: 2017/9/7 15:21.
 */
public class RoomBean implements Serializable {
    long roomId;            // 房间id
    String cover;           // 封面url
    String title;           // 房间标题
    String type;            // 房间类型
    int watchNum;           // 观看人数
    int rates;              // 直播间收费标准
    boolean isAttention;    // 是否关注了该主播员
    AnchorBean member;      // 主播员信息

    public long getRoomId() {
        return roomId;
    }

    public String getRoomIdStr() {
        return String.valueOf(roomId);
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return StringUtils.isEmptyString(title) ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return StringUtils.isEmptyString(type) ? "普通房" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWatchNum() {
        return watchNum;
    }

    public String getWatchNumStr() {
        return String.valueOf(watchNum);
    }

    public void setWatchNum(int watchNum) {
        this.watchNum = watchNum;
    }

    public int getRates() {
        return rates;
    }

    public String getRatesStr() {
        return String.valueOf(rates);
    }

    public void setRates(int rates) {
        this.rates = rates;
    }

    public boolean isAttention() {
        return isAttention;
    }

    public void setAttention(boolean attention) {
        isAttention = attention;
    }

    public AnchorBean getMember() {
        return member;
    }

    public void setMember(AnchorBean member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "RoomBean{" +
                "roomId=" + roomId +
                ", cover='" + cover + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", watchNum=" + watchNum +
                ", rates=" + rates +
                ", isAttention=" + isAttention +
                ", member=" + member +
                '}';
    }
}
