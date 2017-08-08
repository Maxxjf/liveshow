package com.qcloud.liveshow.ui.account.presenter;

import com.qcloud.qclib.base.BtnClickPresenter;

/**
 * 类说明：登录页面数据处理
 * Author: Kuzan
 * Date: 2017/8/8 15:42.
 */
public interface ILoginPresenter extends BtnClickPresenter {
    /**获取验证码*/
    void getCode(String mobile);

    /**点击登录*/
    void login(String mobile, String code);
}
