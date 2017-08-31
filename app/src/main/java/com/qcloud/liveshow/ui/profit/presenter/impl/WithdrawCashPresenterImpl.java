package com.qcloud.liveshow.ui.profit.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.ui.profit.presenter.IWithdrawCashPresenter;
import com.qcloud.liveshow.ui.profit.view.IWithdrawCashView;
import com.qcloud.qclib.base.BasePresenter;

/**
 * 类说明：提现到银行卡
 * Author: Kuzan
 * Date: 2017/8/31 17:24.
 */
public class WithdrawCashPresenterImpl extends BasePresenter<IWithdrawCashView> implements IWithdrawCashPresenter {

    public WithdrawCashPresenterImpl() {

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
}
