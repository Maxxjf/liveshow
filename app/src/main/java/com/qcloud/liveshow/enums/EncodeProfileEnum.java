package com.qcloud.liveshow.enums;

import com.ksyun.media.streamer.encoder.VideoEncodeFormat;

/**
 * 类说明：编码性能
 * Author: Kuzan
 * Date: 2017/9/26 10:01.
 */
public enum EncodeProfileEnum {
    /**低功耗*/
    LOW_POWER(VideoEncodeFormat.ENCODE_PROFILE_LOW_POWER, "低功耗"),
    /**平衡*/
    BALANCE(VideoEncodeFormat.ENCODE_PROFILE_BALANCE, "平衡"),
    /**高性能*/
    HIGH_PERFORMANCE(VideoEncodeFormat.ENCODE_PROFILE_HIGH_PERFORMANCE, "高性能");

    private int key;
    private String value;

    EncodeProfileEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static EncodeProfileEnum valueOf(int key) {
        switch (key) {
            case VideoEncodeFormat.ENCODE_PROFILE_LOW_POWER:
                return LOW_POWER;
            case VideoEncodeFormat.ENCODE_PROFILE_BALANCE:
                return BALANCE;
            case VideoEncodeFormat.ENCODE_PROFILE_HIGH_PERFORMANCE:
                return HIGH_PERFORMANCE;
            default:
                return BALANCE;
        }
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
