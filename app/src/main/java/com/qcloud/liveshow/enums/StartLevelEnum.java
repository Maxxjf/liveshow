package com.qcloud.liveshow.enums;

/**
 * 类说明：启动我的等级枚举
 * Author: Kuzan
 * Date: 2017/9/2 17:50.
 */
public enum StartLevelEnum {
    /**启动用户等级*/
    START_USER(0, "用户等级"),
    /**启动主播等级*/
    START_ANCHOR(1, "主播等级");

    private int key;
    private String value;

    StartLevelEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static StartLevelEnum valueOf(int key) {
        switch (key) {
            case 0:
                return START_USER;
            case 1:
                return START_ANCHOR;
            default:
                return START_USER;
        }
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
