package com.qcloud.liveshow.beans;

/**
 * 类说明：通知
 * Author: Kuzan
 * Date: 2017/11/2 10:16.
 */
public class NettyNoticeBean {
    String room_number;     // 房间号
    MemberBean user;         // 用户id

    public String getRoom_number() {
        return room_number;
    }

    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }

    public MemberBean getUser() {
        return user;
    }

    public void setUser(MemberBean user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "NettyNoticeBean{" +
                "room_number='" + room_number + '\'' +
                ", user=" + user +
                '}';
    }
}
