package com.qcloud.liveshow.enums;

/**
 * 类说明：启动我的关注和我的粉丝和黑名单列表
 * Author: Kuzan
 * Date: 2017/8/18 14:06.
 */
public enum StartFansEnum {
    /**我的关注*/
    MyFollow(1, "我的关注"),
    /**我的粉丝*/
    MyFans(2, "我的粉丝"),
    /**黑名单*/
    Blacklist(3, "黑名单");

    private int key;
    private String value;

    StartFansEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static StartFansEnum valueOf(int key) {
        switch (key) {
            case 1:
                return MyFollow;
            case 2:
                return MyFans;
            case 3:
                return Blacklist;
            default:
                return MyFollow;
        }
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
