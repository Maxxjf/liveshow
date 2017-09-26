package com.qcloud.liveshow.enums;

import com.ksyun.media.streamer.encoder.VideoEncodeFormat;

/**
 * 类说明：编码模式
 * Author: Kuzan
 * Date: 2017/9/26 9:57.
 */
public enum EncodeSceneEnum {
    /**通用模式*/
    DEFAULT(VideoEncodeFormat.ENCODE_SCENE_DEFAULT, "通用模式"),
    /**秀场模式*/
    SHOWSELF(VideoEncodeFormat.ENCODE_SCENE_SHOWSELF, "秀场模式"),
    /**游戏模式*/
    GAME(VideoEncodeFormat.ENCODE_SCENE_GAME, "游戏模式");

    private int key;
    private String value;

    EncodeSceneEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static EncodeSceneEnum valueOf(int key) {
        switch (key) {
            case VideoEncodeFormat.ENCODE_SCENE_DEFAULT:
                return DEFAULT;
            case VideoEncodeFormat.ENCODE_SCENE_SHOWSELF:
                return SHOWSELF;
            case VideoEncodeFormat.ENCODE_SCENE_GAME:
                return GAME;
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
