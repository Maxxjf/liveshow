package com.qcloud.liveshow.ui.home.presenter;

import com.qcloud.qclib.base.BtnClickPresenter;

/**
 * 类说明：粉丝消息
 * Author: Kuzan
 * Date: 2017/9/11 12:02.
 */
public interface IFansMessagePresenter extends BtnClickPresenter {
    /** 从Realm中获取聊天  */
    void getChars(String fromUserId);

    /**发送消息*/
    void sendMessage(String userId, String content);
}
