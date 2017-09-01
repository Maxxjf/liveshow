package com.qcloud.liveshow.ui.profit.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.ui.profit.presenter.IMyDiamondsPresenter;
import com.qcloud.liveshow.ui.profit.view.IMyDiamondsView;
import com.qcloud.qclib.base.BasePresenter;

/**
 * 类说明：我的钻石币
 * Author: Kuzan
 * Date: 2017/9/1 15:30.
 */
public class MyDiamondsPresenterImpl extends BasePresenter<IMyDiamondsView> implements IMyDiamondsPresenter {

    public MyDiamondsPresenterImpl() {

    }

    @Override
    public void onBtnClick(int viewId) {
        switch (viewId) {
            case R.id.btn_recharge_agreement:
                mView.onRechargeClick();
                break;
            case R.id.tv_customer_service:
                mView.onCustomerServiceClick();
                break;
        }
    }
}
