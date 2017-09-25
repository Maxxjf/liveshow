package com.qcloud.liveshow.ui.profit.presenter;

import com.qcloud.qclib.base.BtnClickPresenter;

/**
 * 类说明：我的收益
 * Author: Kuzan
 * Date: 2017/8/18 14:23.
 */
public interface IMyProfitPresenter extends BtnClickPresenter {
    /**获取我的收益*/
    void getMyProfit();

    /**是否设置提现密码*/
    void isSetPassword();
}
