package com.qcloud.liveshow.beans;

import com.qcloud.liveshow.enums.StartLevelEnum;

/**
 * 类说明：等级ViewPage
 * Author: Kuzan
 * Date: 2017/9/2 17:50.
 */
public class LevelViewPageBean {
    StartLevelEnum mEnum;    // 启动首页枚举
    int badgeNum;   // 最新数量

    public StartLevelEnum getEnum() {
        return mEnum;
    }

    public void setEnum(StartLevelEnum anEnum) {
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
        return "LevelViewPageBean{" +
                "mEnum=" + mEnum +
                ", badgeNum=" + badgeNum +
                '}';
    }
}
