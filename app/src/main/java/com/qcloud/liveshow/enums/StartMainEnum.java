package com.qcloud.liveshow.enums;

/**
 * 类说明：启动主界面
 * Author: Kuzan
 * Date: 2017/8/9 21:04.
 */
public enum StartMainEnum {
    /**启动首页*/
    StartHome(0, "首页"),
    /**启动直播*/
    StartLiveShow(1, "直播"),
    /**启动我的*/
    StartMine(2, "我的");

    private int key;
    private String value;

    StartMainEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static StartMainEnum valueOf(int key) {
        switch (key) {
            case 0:
                return StartHome;
            case 1:
                return StartLiveShow;
            case 2:
                return StartMine;
            default:
                return StartHome;
        }
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
