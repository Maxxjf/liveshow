package com.qcloud.liveshow.beans;

/**
 * 类说明：收费直播请求
 * Author: iceberg
 * Date: 2018/1/11.
 */
public class NettyPayVipRoom {
    String roomId;
    String token;

    public String getRoom_id() {
        return roomId;
    }

    public void setRoom_id(String room_id) {
        this.roomId = room_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "NettyPayVipRoom{" +
                "room_id='" + roomId + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
