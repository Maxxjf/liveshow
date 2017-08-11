package com.qcloud.liveshow.enums;

/**
 * 类说明：启动主界面
 * Author: Kuzan
 * Date: 2017/8/9 21:04.
 */
public enum StartMainEnum {
    /**启动首页*/
    START_HOME(0, "首页"),
    /**启动直播*/
    START_LIVE_SHOW(1, "直播"),
    /**启动我的*/
    START_MINE(2, "我的");

    private int key;
    private String value;

    StartMainEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static StartMainEnum valueOf(int key) {
        switch (key) {
            case 0:
                return START_HOME;
            case 1:
                return START_LIVE_SHOW;
            case 2:
                return START_MINE;
            default:
                return START_HOME;
        }
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
