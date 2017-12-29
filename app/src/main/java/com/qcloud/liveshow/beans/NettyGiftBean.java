package com.qcloud.liveshow.beans;

/**
 * 类说明：收到礼物展示
 * Author: iceberg
 * Date: 2017/12.28
 */
public class NettyGiftBean {
    GiftBean gift;
    String token;
    MemberBean user;

    public GiftBean getGift() {
        return gift;
    }

    public void setGift(GiftBean gift) {
        this.gift = gift;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public MemberBean getUser() {
        return user;
    }

    public void setUser(MemberBean user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "NettyGiftBean{" +
                "gift=" + gift +
                ", token='" + token + '\'' +
                ", user=" + user +
                '}';
    }
}
