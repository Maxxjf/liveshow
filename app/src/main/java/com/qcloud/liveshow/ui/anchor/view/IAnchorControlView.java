package com.qcloud.liveshow.ui.anchor.view;

import com.qcloud.qclib.base.BaseView;

/**
 * 类说明：直播间控制页面
 * Author: Kuzan
 * Date: 2017/9/2 15:37.
 */
public interface IAnchorControlView extends BaseView {
    /**点击公告*/
    void onNoticeClick();

    /**点击发送消息*/
    void onSendMessageClick();

    /**点击接收消息*/
    void onReceiveMessageClick();

    /**点击闪光灯*/
    void onFlashClick();

    /**点击切换镜头*/
    void onSwitchCameraClick();

    /**点击分享*/
    void onShareClick();

    /**点击退出*/
    void onExitClick();
}
