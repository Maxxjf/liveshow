package com.qcloud.liveshow.beans;

/**
 * 类说明：群聊
 * Author: Kuzan
 * Date: 2017/11/2 10:14.
 */
public class NettyGroupBean {
    String room_number;     // 房间号
    String token;
    NettyContentBean content;   // 发送群聊内容

    public String getRoom_number() {
        return room_number;
    }

    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public NettyContentBean getContent() {
        return content;
    }

    public void setContent(NettyContentBean content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NettyGroupBean{" +
                "room_number='" + room_number + '\'' +
                ", token='" + token + '\'' +
                ", content=" + content +
                '}';
    }
}
