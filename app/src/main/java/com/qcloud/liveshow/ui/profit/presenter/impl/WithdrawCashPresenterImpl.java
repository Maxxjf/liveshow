package com.qcloud.liveshow.ui.profit.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.ReturnWithdrawSuccessBean;
import com.qcloud.liveshow.model.IProfitModel;
import com.qcloud.liveshow.model.impl.ProfitModelImpl;
import com.qcloud.liveshow.ui.profit.presenter.IWithdrawCashPresenter;
import com.qcloud.liveshow.ui.profit.view.IWithdrawCashView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：提现到银行卡
 * Author: Kuzan
 * Date: 2017/8/31 17:24.
 */
public class WithdrawCashPresenterImpl extends BasePresenter<IWithdrawCashView> implements IWithdrawCashPresenter {
    IProfitModel mModel;

    public WithdrawCashPresenterImpl() {
        mModel = new ProfitModelImpl();
    }

    @Override
    public void onBtnClick(int viewId) {
        switch (viewId) {
            case R.id.tv_bank:
                mView.onSelectBankClick();
                break;
            case R.id.btn_confirm:
                mView.onConfirmClick();
                break;
        }
    }

    /**
     * 提现
     *
     * @param cash       金额
     * @param name       姓名
     * @param cardNumber 银行卡号
     * @param bankCode   银行开户行
     * @param password   密码
     */

    @Override
    public void withdraw2card(String cash, String name, String cardNumber, Integer bankCode, String password) {
        mModel.withdraw2Card(cash, name, cardNumber, bankCode, password, new DataCallback<ReturnWithdrawSuccessBean>() {
            @Override
            public void onSuccess(ReturnWithdrawSuccessBean returnWithdrawSuccessBean) {
                if (mView != null) {
                    if (returnWithdrawSuccessBean != null) {
                        mView.withdraw2cardSuccess(returnWithdrawSuccessBean);
                    }
                }
            }

            @Override
            public void onError(int status, String errMsg) {
                mView.withdraw2cardFails(errMsg);
            }
        });
    }
}
