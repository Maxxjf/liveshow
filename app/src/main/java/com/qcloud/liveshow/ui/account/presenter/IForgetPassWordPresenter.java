package com.qcloud.liveshow.ui.account.presenter;

import com.qcloud.qclib.base.BtnClickPresenter;

/**
 * 类说明：忘记/重置密码
 * Author: iceberg
 * Date: 2017/12/5.
 */
public interface IForgetPassWordPresenter extends BtnClickPresenter {
    /**忘记密码发送验证码*/
    void forgetPasswordCode(String loginAccount);

    /**忘记密码*/
    void forgetPassword(String loginAccount,String code,String newPassword);
}
