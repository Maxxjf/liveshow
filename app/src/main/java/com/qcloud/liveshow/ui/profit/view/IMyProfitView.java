package com.qcloud.liveshow.ui.profit.view;

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

    /**买钻石币*/
    void onBuyDiamondsClick();

    /**用户提现协议*/
    void onCashAgreementClick();
}
