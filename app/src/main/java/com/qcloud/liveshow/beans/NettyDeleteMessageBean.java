package com.qcloud.liveshow.beans;

/**
 * 类说明：删除消息请求实体
 * Author: iceberg
 * Date: 2017/11/20.
 */
public class NettyDeleteMessageBean {
    String to_user_id;
    String token;

    public String getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(String to_user_id) {
        this.to_user_id = to_user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "NettyDeleteMessageBean{" +
                "to_user_id='" + to_user_id + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
