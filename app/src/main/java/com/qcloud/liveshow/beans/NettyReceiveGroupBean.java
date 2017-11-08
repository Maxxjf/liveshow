package com.qcloud.liveshow.beans;

/**
 * 类说明：接收群聊消息
 * Author: Kuzan
 * Date: 2017/11/8 10:53.
 */
public class NettyReceiveGroupBean {
    String room_number;         // 房间号
    MemberBean user;            // 成员
    NettyContentBean content;   // 群聊内容

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

    public NettyContentBean getContent() {
        return content;
    }

    public void setContent(NettyContentBean content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NettyReceiveGroupBean{" +
                "room_number='" + room_number + '\'' +
                ", user=" + user +
                ", content=" + content +
                '}';
    }
}
