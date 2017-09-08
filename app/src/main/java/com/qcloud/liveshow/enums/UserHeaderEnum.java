package com.qcloud.liveshow.enums;

/**
 * 类说明：用户头像大小
 * Author: Kuzan
 * Date: 2017/8/29 14:53.
 */
public enum UserHeaderEnum {
    /**默认*/
    Default(0, "默认"),
    /**大图*/
    Big(1, "大图"),
    /**小图*/
    Small(2, "小图");

    private int key;
    private String value;

    UserHeaderEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static UserHeaderEnum valueOf(int key) {
        switch (key) {
            case 0:
                return Default;
            case 1:
                return Big;
            case 2:
                return Small;
            default:
                return Default;
        }
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
