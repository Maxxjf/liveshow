package com.qcloud.liveshow.enums;

import com.ksyun.media.streamer.framework.AVConst;

/**
 * 类说明：解码类型
 * Author: Kuzan
 * Date: 2017/9/26 9:44.
 */
public enum EncodeTypeEnum {
    /**H264*/
    H264(AVConst.CODEC_ID_AVC, "H264"),
    /**H265*/
    H265(AVConst.CODEC_ID_HEVC, "H265");

    private int key;
    private String value;

    EncodeTypeEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static EncodeTypeEnum valueOf(int key) {
        switch (key) {
            case AVConst.CODEC_ID_AVC:
                return H264;
            case AVConst.CODEC_ID_HEVC:
                return H265;
            default:
                return H264;
        }
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
