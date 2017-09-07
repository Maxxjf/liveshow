package com.qcloud.liveshow.ui.mine.view;

import com.qcloud.liveshow.beans.UserBean;
import com.qcloud.qclib.base.BaseView;

/**
 * 类说明：个人信息
 * Author: Kuzan
 * Date: 2017/8/15 9:24.
 */
public interface IUserInfoView extends BaseView {
    /**编辑资料*/
    void onEditClick();

    /**我的关注*/
    void onFollowClick();

    /**我的粉丝*/
    void onFansClick();

    /**刷新用户信息*/
    void refreshUserInfo(UserBean bean);
}
