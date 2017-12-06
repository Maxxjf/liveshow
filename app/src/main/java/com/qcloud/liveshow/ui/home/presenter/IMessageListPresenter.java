package com.qcloud.liveshow.ui.home.presenter;

import com.qcloud.liveshow.beans.MemberBean;

/**
 * 类说明：消息列表
 * Author: Kuzan
 * Date: 2017/8/30 11:21.
 */
public interface IMessageListPresenter {
    void getAllList();
    /**标为已读*/
    void flagIsRead(MemberBean userBean);

    void deleteMessage(MemberBean userBean);
}
