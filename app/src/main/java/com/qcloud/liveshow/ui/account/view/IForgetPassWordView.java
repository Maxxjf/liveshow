package com.qcloud.liveshow.ui.account.view;

import com.qcloud.qclib.base.BaseView;

/**
 * 类说明：忘记/重置密码
 * Author: iceberg
 * Date: 2017/12/5.
 */
public interface IForgetPassWordView extends BaseView {
    /**得到验证码*/
    void getCodeSuccess(String code);
    /**得到验证码*/
    void getCodeFail(String errMsg);

    /**成功修改密码*/
    void updatePasswordSuccess();

    /**点击验证码按钮*/
    void onClickGetCode();

    /**点击提交按钮*/
    void onClickConfirm();
}
