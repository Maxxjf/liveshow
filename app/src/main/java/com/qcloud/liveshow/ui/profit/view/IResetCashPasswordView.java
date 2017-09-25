package com.qcloud.liveshow.ui.profit.view;

import com.qcloud.qclib.base.BaseView;

/**
 * 类说明：重置提现密码
 * Author: Kuzan
 * Date: 2017/8/31 14:08.
 */
public interface IResetCashPasswordView extends BaseView {
    /**获取验证码*/
    void onGetCodeClick();

    /**点击设置提现密码*/
    void onConfirmClick();

    /**获取验证码成功*/
    void getCodeSuccess(String code);

    /**获取验证码失败*/
    void getCodeFailure(String errMsg);

    /**设置提现密码成功*/
    void resetPasswordSuccess();
}
