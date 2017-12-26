package com.qcloud.liveshow.ui.anchor.presenter;

import com.qcloud.qclib.base.BtnClickPresenter;

/**
 * 类说明：直播间控制页面
 * Author: Kuzan
 * Date: 2017/9/2 15:49.
 */
public interface IAnchorControlPresenter extends BtnClickPresenter {
    /**获取守护列表*/
    void getGuardList();

    /**拉入/解除黑名单*/
    void submitAttention(int type, long id, boolean isAttention);
    /**得到成员列表*/
    void getChatList();
    /**加入群聊*/
    void joinGroup(String roomNumber);

    /**发送群聊消息*/
    void sendGroupMessage(String roomNum, String content,int position);

    void sendGroupNotice(String roomNum, String content);

    /**设置/取消守护*/
    void inOutGuard(long memberId, boolean isGuard);
    /**禁言/解除禁言*/
    void shutUp(String roomNumber, String memberId, boolean isForbidden);
}
