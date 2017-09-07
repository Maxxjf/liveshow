package com.qcloud.liveshow.enums;

/**
 * 类说明：第三方登录
 * Author: Kuzan
 * Date: 2017/9/7 11:20.
 */
public enum ThirdLoginEnum {
    /**微信登录*/
    WEXIN(0, "微信登录"),
    /**Facebook登录*/
    FACEBOOK(1, "Facebook登录");

    private int key;
    private String value;

    ThirdLoginEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static ThirdLoginEnum valueOf(int key) {
        switch (key) {
            case 0:
                return WEXIN;
            case 1:
                return FACEBOOK;
            default:
                return WEXIN;
        }
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
