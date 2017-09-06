package com.qcloud.liveshow.ui.account.view;

import com.qcloud.liveshow.beans.LoginBean;
import com.qcloud.liveshow.beans.WeChatUserBean;
import com.qcloud.qclib.base.BaseView;

/**
 * 类说明：登录页面
 * Author: Kuzan
 * Date: 2017/8/8 15:32.
 */
public interface ILoginView extends BaseView {
    /**点击登录*/
    void onLoginClick();

    /**点击获取验证码*/
    void onGetCodeClick();

    /**点击微信登录*/
    void onWeChatClick();

    /**点击facebook登录*/
    void onFacebookClick();

    /**点击条款*/
    void onClauseClick();

    /**登录成功*/
    void loginSuccess(LoginBean bean);

    /**获取验证码成功*/
    void getCodeSuccess();

    /**获取验证码失败*/
    void getCodeFailure(String errMsg);

    /**微信用户信息*/
    void weChatUserInfo(WeChatUserBean bean);
}
