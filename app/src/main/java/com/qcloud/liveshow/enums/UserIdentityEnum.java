package com.qcloud.liveshow.enums;

/**
 * 类说明：用户身份
 * Author: iceberg
 * Date: 2018/1/23 10:26.
 */
public enum UserIdentityEnum {

    /**观众*/
    Audience(0, "观众"),
    /**守护*/
    Guard(1, "守护"),
    /**主播*/
    Anchor(2, "主播");

    private int key;
    private String value;

    UserIdentityEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static UserIdentityEnum valueOf(int key) {
        switch (key) {
            case 0:
                return Audience;
            case 1:
                return Guard;
            case 2:
                return Anchor;
            default:
                return Audience;
        }
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
