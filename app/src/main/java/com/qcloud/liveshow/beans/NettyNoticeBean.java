package com.qcloud.liveshow.beans;

/**
 * 类说明：通知
 * Author: Kuzan
 * Date: 2017/11/2 10:16.
 */
public class NettyNoticeBean {
    String room_number;     // 房间号
    MemberBean user;         // 用户id
    String user_id;           //用户id   这是主播退出，房间其他人收到的
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "NettyNoticeBean{" +
                "room_number='" + room_number + '\'' +
                ", user=" + user +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
