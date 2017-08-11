package com.qcloud.liveshow.enums;

/**
 * 类说明：启动首页
 * Author: Kuzan
 * Date: 2017/8/10 10:04.
 */
public enum StartHomeEnum {
    /**启动热门*/
    START_HOT(0, "热门"),
    /**启动最新*/
    START_NEWEST(1, "最新"),
    /**启动关注*/
    START_FOLLOW(2, "关注");

    private int key;
    private String value;

    StartHomeEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static StartHomeEnum valueOf(int key) {
        switch (key) {
            case 0:
                return START_HOT;
            case 1:
                return START_NEWEST;
            case 2:
                return START_FOLLOW;
            default:
                return START_HOT;
        }
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
