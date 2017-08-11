package com.qcloud.liveshow.beans;

import com.qcloud.liveshow.enums.StartHomeEnum;

/**
 * 类说明：首页ViewPage
 * Author: Kuzan
 * Date: 2017/8/11 11:37.
 */
public class HomeViewPageBean {
    StartHomeEnum mEnum;    // 启动首页枚举
    int badgeNum;   // 最新数量

    public StartHomeEnum getEnum() {
        return mEnum;
    }

    public void setEnum(StartHomeEnum anEnum) {
        mEnum = anEnum;
    }

    public int getBadgeNum() {
        return badgeNum;
    }

    public void setBadgeNum(int badgeNum) {
        this.badgeNum = badgeNum;
    }

    @Override
    public String toString() {
        return "HomeViewPageBean{" +
                "mEnum=" + mEnum +
                ", badgeNum=" + badgeNum +
                '}';
    }
}
