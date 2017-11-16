package com.qcloud.liveshow.beans;

/**
 * 类说明：直播公告
 * Author: iceberg
 * Date: 2017/11/16.
 */
public class NettyLiveNoticeBean {
    NettyContentBean content;
    String room_number;
//    NettyMemberBean user;

    public NettyContentBean getContent() {
        return content;
    }

    public void setContent(NettyContentBean content) {
        this.content = content;
    }

    public String getRoom_number() {
        return room_number;
    }

    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }

//    public NettyMemberBean getUser() {
//        return user;
//    }
//
//    public void setUser(NettyMemberBean user) {
//        this.user = user;
//    }

    @Override
    public String toString() {
        return "NettyLiveNoticeBean{" +
                "content=" + content +
                ", room_number='" + room_number + '\'' +
//                ", user=" + user +
                '}';
    }
}
