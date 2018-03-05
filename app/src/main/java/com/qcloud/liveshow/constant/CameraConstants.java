package com.qcloud.liveshow.constant;

import com.qcloud.liveshow.enums.AudioEncodeProfileEnum;
import com.qcloud.liveshow.enums.BeautyUiEnum;
import com.qcloud.liveshow.enums.EncodeMethodEnum;
import com.qcloud.liveshow.enums.EncodeProfileEnum;
import com.qcloud.liveshow.enums.EncodeSceneEnum;
import com.qcloud.liveshow.enums.EncodeTypeEnum;
import com.qcloud.liveshow.enums.ScreenOrientationEnum;
import com.qcloud.liveshow.enums.VideoResolutionEnum;

/**
 * 类说明：视频流常量
 * Author: Kuzan
 * Date: 2017/9/26 9:04.
 */
public interface CameraConstants {
    /**采集帧率*/
    int FRAME_RATE = 15;
    /**视频码率(Max)*/
    int VIDEO_BITRATE = 1000;
    /**音频码率(Max)*/
    int AUDIO_BITRATE = 60;
    /**采集分辨率(720P)*/
    int CAPTURE_RESOLUTION = VideoResolutionEnum.VIDEO_RESOLUTION_1080P.getKey();
    /**预览分辨率(720P)*/
    int PREVIEW_RESOLUTION = VideoResolutionEnum.VIDEO_RESOLUTION_720P.getKey();
    /**推流分辨率(720P)（不应大于预览分辨率，否则推流会有画质损失）*/
    int VIDEO_RESOLUTION = VideoResolutionEnum.VIDEO_RESOLUTION_480P.getKey();
    /**屏幕状态(竖屏)*/
    int SCREEN_ORIENTATION = ScreenOrientationEnum.PORTRAIT.getKey();
    /**解码类型(H264)*/
    int ENCODE_TYPE = EncodeTypeEnum.H264.getKey();
    /**解码方法(软编)*/
    int ENCODE_METHOD = EncodeMethodEnum.SOFTWARE.getKey();
    /**编码模式(确编方法下设为0，即不可用)*/
    int ENCODE_SCENE = EncodeSceneEnum.SHOWSELF.getKey();
    /**编码性能*/
    int ENCODE_PROFILE = EncodeProfileEnum.BALANCE.getKey();
    /**单双声道推流 1单声道 2双声道*/
    int STEREO_STREAM = 1;
    /**音频编码*/
    int AUDIO_ENCODE_PROFILE = AudioEncodeProfileEnum.AAC_LC.getKey();
    /**美容效果*/
    int BEAUTY_UI = BeautyUiEnum.PRO.getKey();
    /**磨皮效果*/
    int GRIND_SIZE = 10;
    /**美白效果*/
    int WHITEN_SIZE = 20;
    /**红润效果*/
    int RUDDY_SIZE = 30;
}
