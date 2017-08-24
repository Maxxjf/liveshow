package com.qcloud.liveshow.ui.player.view;

/**
 * 类说明：直播间
 * Author: Kuzan
 * Date: 2017/8/23 11:42.
 */
public interface IRoomControlView {
    /**点击关注*/
    void onFollowClick();

    /**点击公告*/
    void onNoticeClick();

    /**点击发送消息*/
    void onSendMessageClick();

    /**点击购买钻石币*/
    void onBuyDiamondsClick();

    /**点击分享*/
    void onShareClick();

    /**点击接收消息*/
    void onReceiveMessageClick();

    /**点击送礼物*/
    void onSendGiftClick();

    /**点击退出*/
    void onExitClick();

}
