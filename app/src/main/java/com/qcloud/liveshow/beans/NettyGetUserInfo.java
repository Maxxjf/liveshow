package com.qcloud.liveshow.beans;

/**
 * 类型：NettyGetUserInfo
 * Author: iceberg
 * Date: 2018/1/25.
 * TODO:
 */
public class NettyGetUserInfo {
    private String token;
    private String user_id;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "NettyGetUserInfo{" +
                "token='" + token + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
