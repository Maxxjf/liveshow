package com.qcloud.liveshow.ui.profit.view;

import com.qcloud.liveshow.beans.ProfitBean;
import com.qcloud.qclib.base.BaseView;

/**
 * 类说明：我的收益
 * Author: Kuzan
 * Date: 2017/8/18 14:23.
 */
public interface IMyProfitView extends BaseView {
    /**提现规则*/
    void onCashRuleClick();

    /**确认提现*/
    void onConfirmCashClick();

    /**用户提现协议*/
    void onCashAgreementClick();

    /**获取我的收益成功*/
    void getMyProfitSuccess(ProfitBean bean);

    /**是否设置提现密码*/
    void isSetPassword(boolean isSet);
}
