package com.qcloud.liveshow.enums;

import android.content.pm.ActivityInfo;

/**
 * 类说明：屏幕状态
 * Author: Kuzan
 * Date: 2017/9/26 9:40.
 */
public enum ScreenOrientationEnum {
    /**横屏*/
    LANDSCAPE(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE, "横屏"),
    /**竖屏*/
    PORTRAIT(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT, "竖屏"),
    /**动态*/
    FULL_SENSOR(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR, "动态");

    private int key;
    private String value;

    ScreenOrientationEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static ScreenOrientationEnum valueOf(int key) {
        switch (key) {
            case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
                return LANDSCAPE;
            case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
                return PORTRAIT;
            case ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR:
                return FULL_SENSOR;
            default:
                return PORTRAIT;
        }
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
