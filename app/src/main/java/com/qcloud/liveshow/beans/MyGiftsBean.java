package com.qcloud.liveshow.beans;

import java.util.List;

/**
 * 类说明：我的礼物列表
 * Author: Kuzan
 * Date: 2017/9/12 15:11.
 */
public class MyGiftsBean {
    List<GiftBean> giftList;    // 礼物列表
    List<MemberBean> giveMemberList;      // 榜单列表
    boolean next;   // 是否有下一页
    int pageNum;    // 页数
    int pageSize;   // 一页数量
    double sum;     // 该主播累计礼物收益
    int total;      // 总数

    public List<GiftBean> getGiftList() {
        return giftList;
    }

    public void setGiftList(List<GiftBean> giftList) {
        this.giftList = giftList;
    }

    public List<MemberBean> getGiveMemberList() {
        return giveMemberList;
    }

    public void setGiveMemberList(List<MemberBean> giveMemberList) {
        this.giveMemberList = giveMemberList;
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

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "MyGiftsBean{" +
                "giftList=" + giftList +
                ", giveMemberList=" + giveMemberList +
                ", next=" + next +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", sum=" + sum +
                ", total=" + total +
                '}';
    }
}
