package com.qcloud.liveshow.beans;

import java.util.List;

/**
 * 类说明：热门房间
 * Author: Kuzan
 * Date: 2017/9/7 15:19.
 */
public class HotRoomBean {
    List<BannerBean> imgList;   // 轮播图
    List<RoomBean> list;    // 直播间列表
    boolean next;       // 是否有一下页
    int pageNum;        // 页数
    int pageSize;       // 当页数量
    int total;          // 总数

    public List<BannerBean> getImgList() {
        return imgList;
    }

    public void setImgList(List<BannerBean> imgList) {
        this.imgList = imgList;
    }

    public List<RoomBean> getList() {
        return list;
    }

    public void setList(List<RoomBean> list) {
        this.list = list;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "HotRoomBean{" +
                "imgList=" + imgList +
                ", list=" + list +
                ", next=" + next +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", total=" + total +
                '}';
    }
}
