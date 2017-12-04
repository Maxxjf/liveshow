package com.qcloud.liveshow.ui.profit.view;

import com.qcloud.liveshow.beans.ReturnWithdrawSuccessBean;
import com.qcloud.qclib.base.BaseView;

/**
 * 类说明：提现到银行卡
 * Author: Kuzan
 * Date: 2017/8/31 17:22.
 */
public interface IWithdrawCashView extends BaseView {
    /**点击选择银行*/
    void onSelectBankClick();

    /**点击确认*/
    void onConfirmClick();

    /**提现成功*/
    void withdraw2cardSuccess(ReturnWithdrawSuccessBean returnWithdrawSuccessBean);

    /**提现失败*/
    void withdraw2cardFails(String errMsg);
}
