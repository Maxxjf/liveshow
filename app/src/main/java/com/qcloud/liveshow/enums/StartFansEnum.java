package com.qcloud.liveshow.enums;

import static com.qcloud.liveshow.enums.StartHomeEnum.START_FOLLOW;
import static com.qcloud.liveshow.enums.StartHomeEnum.START_HOT;
import static com.qcloud.liveshow.enums.StartHomeEnum.START_NEWEST;

/**
 * 类说明：启动我的关注和我的粉丝和黑名单列表
 * Author: Kuzan
 * Date: 2017/8/18 14:06.
 */
public enum StartFansEnum {
    /**我的关注*/
    MY_FOLLOW(1, "我的关注"),
    /**我的粉丝*/
    MY_FANS(2, "我的粉丝"),
    /**黑名单*/
    BLACKLIST(3, "黑名单");

    private int key;
    private String value;

    StartFansEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static StartFansEnum valueOf(int key) {
        switch (key) {
            case 1:
                return MY_FOLLOW;
            case 2:
                return MY_FANS;
            case 3:
                return BLACKLIST;
            default:
                return MY_FOLLOW;
        }
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
