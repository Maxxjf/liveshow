package com.qcloud.liveshow.enums;

import static com.qcloud.liveshow.enums.StartMainEnum.START_HOME;
import static com.qcloud.liveshow.enums.StartMainEnum.START_LIVE_SHOW;
import static com.qcloud.liveshow.enums.StartMainEnum.START_MINE;

/**
 * 类说明：用户头像大小
 * Author: Kuzan
 * Date: 2017/8/29 14:53.
 */
public enum UserHeaderEnum {
    /**默认*/
    DEFAULT(0, "默认"),
    /**大图*/
    BIG(1, "大图"),
    /**小图*/
    SMALL(2, "小图");

    private int key;
    private String value;

    UserHeaderEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static UserHeaderEnum valueOf(int key) {
        switch (key) {
            case 0:
                return DEFAULT;
            case 1:
                return BIG;
            case 2:
                return SMALL;
            default:
                return DEFAULT;
        }
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
