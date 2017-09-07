package com.qcloud.liveshow.ui.mine.view;

import com.qcloud.liveshow.beans.UserBean;
import com.qcloud.qclib.base.BaseView;

/**
 * 类说明：编辑用户信息
 * Author: Kuzan
 * Date: 2017/8/17 18:59.
 */
public interface IEditUserView extends BaseView {
    /**点击用户头像*/
    void onUserHeadClick();

    /**刷新用户信息*/
    void refreshUserInfo(UserBean bean);
}
