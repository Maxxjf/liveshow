package com.qcloud.liveshow.enums;

/**
 * 类说明：启动首页
 * Author: Kuzan
 * Date: 2017/8/10 10:04.
 */
public enum StartHomeEnum {
    /**启动热门*/
    StartHot(0, "热门"),
    /**启动最新*/
    StartNewest(1, "最新"),
    /**启动关注*/
    StartFollow(2, "关注");

    private int key;
    private String value;

    StartHomeEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static StartHomeEnum valueOf(int key) {
        switch (key) {
            case 0:
                return StartHot;
            case 1:
                return StartNewest;
            case 2:
                return StartFollow;
            default:
                return StartHot;
        }
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
