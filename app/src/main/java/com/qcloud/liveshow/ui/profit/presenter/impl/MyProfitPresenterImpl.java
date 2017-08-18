package com.qcloud.liveshow.ui.profit.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.ui.profit.presenter.IMyProfitPresenter;
import com.qcloud.liveshow.ui.profit.view.IMyProfitView;
import com.qcloud.qclib.base.BasePresenter;

/**
 * 类说明：我的收益
 * Author: Kuzan
 * Date: 2017/8/18 14:24.
 */
public class MyProfitPresenterImpl extends BasePresenter<IMyProfitView> implements IMyProfitPresenter {

    public MyProfitPresenterImpl() {

    }

    @Override
    public void onBtnClick(int viewId) {
        switch (viewId) {
            case R.id.btn_rule:
                mView.onCashRuleClick();
                break;
            case R.id.btn_confirm_cash:
                mView.onConfirmCashClick();
                break;
            case R.id.btn_buy_diamonds:
                mView.onBuyDiamondsClick();
                break;
            case R.id.btn_cash_agreement:
                mView.onCashAgreementClick();
                break;
        }
    }
}
