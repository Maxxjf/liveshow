package com.qcloud.liveshow.ui.home.presenter;

/**
 * 类说明：粉丝消息
 * Author: Kuzan
 * Date: 2017/9/11 12:02.
 */
public interface IFansMessagePresenter {
    /**
     * 从Realm中获取聊天
     */
    void getChars(String fromUserId);

}
