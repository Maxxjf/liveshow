package com.qcloud.liveshow.beans;

import com.qcloud.liveshow.enums.CharStatusEnum;

/**
 * 类说明：接收群聊消息
 * Author: Kuzan
 * Date: 2017/11/8 10:53.
 */
public class NettyReceiveGroupBean {
    String chatId;               //聊天唯一标识符
    String room_number;         // 房间号
    MemberBean user;            // 成员
    NettyContentBean content;   // 群聊内容
    int charStatusEnum= CharStatusEnum.SUCCESS.getKey();//发送状态

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public int getCharStatusEnum() {
        return charStatusEnum;
    }

    public void setCharStatusEnum(int charStatusEnum) {
        this.charStatusEnum = charStatusEnum;
    }

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
                "chatId='" + chatId + '\'' +
                ", room_number='" + room_number + '\'' +
                ", user=" + user +
                ", content=" + content +
                ", charStatusEnum=" + charStatusEnum +
                '}';
    }
}
