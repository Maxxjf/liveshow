package com.qcloud.liveshow.enums;

import com.ksyun.media.streamer.kit.StreamerConstants;

/**
 * 类说明：解码方式
 * Author: Kuzan
 * Date: 2017/9/26 9:48.
 */
public enum EncodeMethodEnum {
    /**硬编*/
    HARDWARE(StreamerConstants.ENCODE_METHOD_HARDWARE, "硬编"),
    /**软编*/
    SOFTWARE(StreamerConstants.ENCODE_METHOD_SOFTWARE, "软编"),
    /**软件兼容*/
    SOFTWARE_COMPAT(StreamerConstants.ENCODE_METHOD_SOFTWARE_COMPAT, "软件兼容");

    private int key;
    private String value;

    EncodeMethodEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static EncodeMethodEnum valueOf(int key) {
        switch (key) {
            case StreamerConstants.ENCODE_METHOD_HARDWARE:
                return HARDWARE;
            case StreamerConstants.ENCODE_METHOD_SOFTWARE:
                return SOFTWARE;
            case StreamerConstants.ENCODE_METHOD_SOFTWARE_COMPAT:
                return SOFTWARE_COMPAT;
            default:
                return SOFTWARE_COMPAT;
        }
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
