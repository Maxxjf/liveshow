package com.qcloud.liveshow.ui.profit.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.ProfitBean;
import com.qcloud.liveshow.beans.ReturnSuccessBean;
import com.qcloud.liveshow.model.IProfitModel;
import com.qcloud.liveshow.model.impl.ProfitModelImpl;
import com.qcloud.liveshow.ui.profit.presenter.IMyProfitPresenter;
import com.qcloud.liveshow.ui.profit.view.IMyProfitView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：我的收益
 * Author: Kuzan
 * Date: 2017/8/18 14:24.
 */
public class MyProfitPresenterImpl extends BasePresenter<IMyProfitView> implements IMyProfitPresenter {

    private IProfitModel mModel;

    public MyProfitPresenterImpl() {
        mModel = new ProfitModelImpl();
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
            case R.id.btn_cash_agreement:
                mView.onCashAgreementClick();
                break;
        }
    }

    @Override
    public void getMyProfit() {
        mModel.getMyProfit(new DataCallback<ProfitBean>() {
            @Override
            public void onSuccess(ProfitBean bean) {
                if (mView == null) {
                    return;
                }
                if (bean != null) {
                    mView.getMyProfitSuccess(bean);
                } else {
                    mView.loadErr(true, "获取我的收益失败");
                }
            }

            @Override
            public void onError(int status, String errMsg) {
                if (mView != null) {
                    mView.loadErr(true, errMsg);
                }
            }
        });
    }

    @Override
    public void isSetPassword() {
        mModel.isSetPassword(new DataCallback<ReturnSuccessBean>() {
            @Override
            public void onSuccess(ReturnSuccessBean bean) {
                if (mView == null) {
                    return;
                }
                if (bean != null) {
                    mView.isSetPassword(bean.isSet());
                } else {
                    mView.isSetPassword(false);
                }
            }

            @Override
            public void onError(int status, String errMsg) {
                if (mView != null) {
                    mView.loadErr(false, errMsg);
                    mView.isSetPassword(false);
                }
            }
        });
    }
}
