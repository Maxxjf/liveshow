package com.qcloud.liveshow.ui.profit.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.GetCodeResBean;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.liveshow.model.IProfitModel;
import com.qcloud.liveshow.model.impl.ProfitModelImpl;
import com.qcloud.liveshow.ui.profit.presenter.ISetCashPasswordPresenter;
import com.qcloud.liveshow.ui.profit.view.IResetCashPasswordView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：重置提现密码
 * Author: Kuzan
 * Date: 2017/8/31 14:12.
 */
public class ResetCashPasswordPresenterImpl extends BasePresenter<IResetCashPasswordView> implements ISetCashPasswordPresenter {

    private IProfitModel mModel;

    public ResetCashPasswordPresenterImpl() {
        mModel = new ProfitModelImpl();
    }

    @Override
    public void onBtnClick(int viewId) {
        switch (viewId) {
            case R.id.btn_get_code:
                mView.onGetCodeClick();
                break;
            case R.id.btn_confirm:
                mView.onConfirmClick();
                break;
        }
    }

    @Override
    public void getCode(String phone) {
        mModel.getCodeBySetPassword(phone, new DataCallback<GetCodeResBean>() {
            @Override
            public void onSuccess(GetCodeResBean bean) {
                if (mView != null) {
                    if (bean != null) {
                        mView.getCodeSuccess(bean.getCode());
                    }
                }
            }

            @Override
            public void onError(int status, String errMsg) {
                if (mView != null) {
                    mView.getCodeFailure(errMsg);
                }
            }
        });
    }

    @Override
    public void setWithdrawCashPassword(String phone, String code, String withdrawPassword) {
        mModel.setWithdrawCashPassword(phone, code, withdrawPassword, new DataCallback<ReturnEmptyBean>() {
            @Override
            public void onSuccess(ReturnEmptyBean returnEmptyBean) {
                if (mView != null) {
                    mView.resetPasswordSuccess();
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
}
