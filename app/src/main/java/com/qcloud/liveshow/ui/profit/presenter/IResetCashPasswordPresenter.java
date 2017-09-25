package com.qcloud.liveshow.ui.profit.presenter;

import com.qcloud.qclib.base.BtnClickPresenter;

/**
 * 类说明：重置提现密码
 * Author: Kuzan
 * Date: 2017/8/31 14:11.
 */
public interface IResetCashPasswordPresenter extends BtnClickPresenter {
    /**获取验证码*/
    void getCode(String phone);

    /**重置提现密码*/
    void resetWithdrawCashPassword(String phone, String code, String withdrawPassword);
}
