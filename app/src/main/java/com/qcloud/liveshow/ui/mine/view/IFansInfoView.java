package com.qcloud.liveshow.ui.mine.view;

import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.qclib.base.BaseView;

/**
 * 类说明：粉丝信息
 * Author: iceberg
 * Date: 2017/11/9 9:26.
 */
public interface IFansInfoView extends BaseView {
    /**刷新用户信息*/
    void refreshUserInfo(MemberBean bean);
    /**点击发送消息*/
    void onClickSendMessage();
}
