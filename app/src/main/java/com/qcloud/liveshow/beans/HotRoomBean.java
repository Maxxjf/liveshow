package com.qcloud.liveshow.beans;

import java.util.List;

/**
 * 类说明：热门房间
 * Author: Kuzan
 * Date: 2017/9/7 15:19.
 */
public class HotRoomBean {
    List<BannerBean> imgList;   // 轮播图
    List<RoomBean> roomList;    // 直播间列表

    public List<BannerBean> getImgList() {
        return imgList;
    }

    public void setImgList(List<BannerBean> imgList) {
        this.imgList = imgList;
    }

    public List<RoomBean> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<RoomBean> roomList) {
        this.roomList = roomList;
    }

    @Override
    public String toString() {
        return "HotRoomBean{" +
                "imgList=" + imgList +
                ", roomList=" + roomList +
                '}';
    }
}
