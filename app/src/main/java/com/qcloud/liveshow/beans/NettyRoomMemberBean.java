package com.qcloud.liveshow.beans;

import com.qcloud.qclib.utils.StringUtils;

/**
 * 类说明：看直播成员
 * Author: Kuzan
 * Date: 2017/11/8 9:36.
 */
public class NettyRoomMemberBean {
    String room_number;
    MemberBean user;

    public String getRoom_number() {
        return StringUtils.isEmptyString(room_number)? "" : room_number;
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
        return "NettyRoomMemberBean{" +
                "room_number='" + room_number + '\'' +
                ", user=" + user +
                '}';
    }
}
