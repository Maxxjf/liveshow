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

    /**添加守护和取消守护*/
    void setGuard(long memberId, boolean isGuard);

    /**添加黑名单*/
    void addBlacklist(long id, boolean isBlacklist);

    /**发送群聊消息*/
    void sendGroupMessage(String roomNum, String content);
}
