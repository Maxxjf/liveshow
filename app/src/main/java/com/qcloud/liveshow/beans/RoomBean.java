package com.qcloud.liveshow.beans;

import com.qcloud.qclib.utils.StringUtils;

/**
 * 类说明：直播间
 * Author: Kuzan
 * Date: 2017/9/7 15:21.
 */
public class RoomBean {
    long roomId;    // 房间id
    String cover;   // 封面url
    String headImg; // 主播头像url
    String icon;    // 等级图标url
    String nickName;// 主播昵称
    int sex;        // 性别 0:男 1:女
    String title;   // 房间标题
    int type;       // 房间类型
    long watchNum;  // 观看人数

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNickName() {
        return StringUtils.isEmptyString(nickName)? "" : nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public String getSexStr() {
        return sex == 0? "男" : "女";
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getWatchNum() {
        return watchNum;
    }

    public String getWatchNumStr() {
        return String.valueOf(watchNum);
    }

    public void setWatchNum(long watchNum) {
        this.watchNum = watchNum;
    }

    @Override
    public String toString() {
        return "RoomBean{" +
                "roomId=" + roomId +
                ", cover='" + cover + '\'' +
                ", headImg='" + headImg + '\'' +
                ", icon='" + icon + '\'' +
                ", nickName='" + nickName + '\'' +
                ", sex=" + sex +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", watchNum=" + watchNum +
                '}';
    }
}
