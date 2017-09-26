package com.qcloud.liveshow.enums;

import com.ksyun.media.streamer.kit.StreamerConstants;

/**
 * 类说明：视频分辨率
 * Author: Kuzan
 * Date: 2017/9/26 9:17.
 */
public enum VideoResolutionEnum {
    /**360P*/
    VIDEO_RESOLUTION_360P(StreamerConstants.VIDEO_RESOLUTION_360P, "360P"),
    /**480P*/
    VIDEO_RESOLUTION_480P(StreamerConstants.VIDEO_RESOLUTION_480P, "480P"),
    /**540P*/
    VIDEO_RESOLUTION_540(StreamerConstants.VIDEO_RESOLUTION_540P, "540P"),
    /**720P*/
    VIDEO_RESOLUTION_720P(StreamerConstants.VIDEO_RESOLUTION_720P, "720P"),
    /**1080P*/
    VIDEO_RESOLUTION_1080P(StreamerConstants.VIDEO_RESOLUTION_1080P, "1080P");

    private int key;
    private String value;

    VideoResolutionEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static VideoResolutionEnum valueOf(int key) {
        switch (key) {
            case StreamerConstants.VIDEO_RESOLUTION_360P:
                return VIDEO_RESOLUTION_360P;
            case StreamerConstants.VIDEO_RESOLUTION_480P:
                return VIDEO_RESOLUTION_480P;
            case StreamerConstants.VIDEO_RESOLUTION_540P:
                return VIDEO_RESOLUTION_540;
            case StreamerConstants.VIDEO_RESOLUTION_720P:
                return VIDEO_RESOLUTION_720P;
            case StreamerConstants.VIDEO_RESOLUTION_1080P:
                return VIDEO_RESOLUTION_1080P;
            default:
                return VIDEO_RESOLUTION_360P;
        }
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
