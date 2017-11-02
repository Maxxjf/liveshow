package com.qcloud.liveshow.beans;

/**
 * 类说明：鉴权
 * Author: Kuzan
 * Date: 2017/11/2 9:39.
 */
public class NettyAuthBean {
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "NettyAuthBean{" +
                "token='" + token + '\'' +
                '}';
    }
}
