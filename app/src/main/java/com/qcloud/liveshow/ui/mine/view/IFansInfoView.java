package com.qcloud.liveshow.ui.mine.view;

import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.qclib.base.BaseView;

/**
 * 类说明：个人信息
 * Author: Kuzan
 * Date: 2017/8/15 9:24.
 */
public interface IFansInfoView extends BaseView {
    /**刷新用户信息*/
    void refreshUserInfo(MemberBean bean);
    /**点击发送消息*/
    void onClickSendMessage();
}
