package com.qcloud.liveshow.beans;

/**
 * 类型：NettyForbiddenBean
 * Author: iceberg
 * Date: 2018/1/23.
 * 禁言
 */
public class NettyForbiddenBean {
    private String room_number;
    private String member_id;
    private boolean is_forbidden;

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
        return "NettyForbiddenBean{" +
                "room_number='" + room_number + '\'' +
                ", member_id='" + member_id + '\'' +
                ", is_forbidden=" + is_forbidden +
                '}';
    }
}
