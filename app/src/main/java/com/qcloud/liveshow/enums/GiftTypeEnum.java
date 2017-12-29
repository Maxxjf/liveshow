package com.qcloud.liveshow.enums;

/**
 * 类说明：启动我的关注和我的粉丝和黑名单列表
 * Author: Kuzan
 * Date: 2017/8/18 14:06.
 */
public enum GiftTypeEnum {
    /**大礼物*/
    BigGift(1, "大礼物"),
    /**小礼物*/
    SmallGift(0, "小礼物");

    private int key;
    private String value;

    GiftTypeEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static GiftTypeEnum valueOf(int key) {
        switch (key) {
            case 0:
                return SmallGift;
            case 1:
                return BigGift;
            default:
                return SmallGift;
        }
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
