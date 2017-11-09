package com.qcloud.liveshow.beans;

/**
 * 类说明：判断该用户身份(主播,守护,观众) 的请求
 * Author: iceberg
 * Date: 2017/11/9.
 */
public class GetUserStatusRequest {

    String memberId;//	被查看人id	number
    String roomId;//	房间id	number

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "GetUserStatusRequest{" +
                "memberId='" + memberId + '\'' +
                ", roomId='" + roomId + '\'' +
                '}';
    }
}
