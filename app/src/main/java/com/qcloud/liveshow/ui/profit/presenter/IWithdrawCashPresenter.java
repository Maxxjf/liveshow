package com.qcloud.liveshow.ui.profit.presenter;

import com.qcloud.qclib.base.BtnClickPresenter;

/**
 * 类说明：提现到银行卡
 * Author: Kuzan
 * Date: 2017/8/31 17:23.
 */
public interface IWithdrawCashPresenter extends BtnClickPresenter {
    /**
     * 提现
     * @param cash  金额
     * @param name  姓名
     * @param cardNumber  银行卡号
     * @param bankCode   银行开户行
     * @param password   密码
     */
    void withdraw2card(String cash, String name, String cardNumber, Integer bankCode, String password);
}
