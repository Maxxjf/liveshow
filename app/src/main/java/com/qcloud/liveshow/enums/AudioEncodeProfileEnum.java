package com.qcloud.liveshow.enums;

import com.ksyun.media.streamer.framework.AVConst;

/**
 * 类说明：音频编码性能
 * Author: Kuzan
 * Date: 2017/9/26 10:01.
 */
public enum AudioEncodeProfileEnum {
    /**aac_lc*/
    AAC_LC(AVConst.PROFILE_AAC_LOW, "aac_lc"),
    /**aac_he*/
    AAC_HE(AVConst.PROFILE_AAC_HE, "aac_he"),
    /**aac_hev2*/
    AAC_HE_V2(AVConst.PROFILE_AAC_HE_V2, "aac_hev2");

    private int key;
    private String value;

    AudioEncodeProfileEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static AudioEncodeProfileEnum valueOf(int key) {
        switch (key) {
            case AVConst.PROFILE_AAC_HE:
                return AAC_HE;
            case AVConst.PROFILE_AAC_HE_V2:
                return AAC_HE_V2;
            default:
                return AAC_LC;
        }
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
