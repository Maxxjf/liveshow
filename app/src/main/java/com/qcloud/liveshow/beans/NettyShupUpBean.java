package com.qcloud.liveshow.beans;

/**
 * 类说明：IM禁言请求
 * Author: iceberg
 * Date: 2017/11/9 10:14.
 */
public class NettyShupUpBean {
    String room_number;     // 房间号
    String member_id;
    boolean is_forbidden;   // 发送群聊内容

    public String getRoom_number() {
        return room_number;
    }

    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public boolean isIs_forbidden() {
        return is_forbidden;
    }

    public void setIs_forbidden(boolean is_forbidden) {
        this.is_forbidden = is_forbidden;
    }

    @Override
    public String toString() {
        return "NettyShupUpBean{" +
                "room_number='" + room_number + '\'' +
                ", member_id='" + member_id + '\'' +
                ", is_forbidden=" + is_forbidden +
                '}';
    }
}
