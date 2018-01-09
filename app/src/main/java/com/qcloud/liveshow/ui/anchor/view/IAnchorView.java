package com.qcloud.liveshow.ui.anchor.view;

/**
 * 类说明：直播间
 * Author: Kuzan
 * Date: 2017/9/1 17:17.
 */
public interface IAnchorView {
    /**切换镜头*/
    void onSwitchCameraClick();

    /**打开闪光灯*/
    void onFlashClick();

    /**开始直播*/
    void onBeginAnchorClick();
    /**
     * 结束直播(强制退出)
     */
    void closeRoom();
}
