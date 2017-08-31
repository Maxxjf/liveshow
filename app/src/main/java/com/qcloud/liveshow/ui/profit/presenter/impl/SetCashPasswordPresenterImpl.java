package com.qcloud.liveshow.ui.profit.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.ui.profit.presenter.ISetCashPasswordPresenter;
import com.qcloud.liveshow.ui.profit.view.ISetCashPasswordView;
import com.qcloud.qclib.base.BasePresenter;

/**
 * 类说明：设置提现密码
 * Author: Kuzan
 * Date: 2017/8/31 14:12.
 */
public class SetCashPasswordPresenterImpl extends BasePresenter<ISetCashPasswordView> implements ISetCashPasswordPresenter {

    public SetCashPasswordPresenterImpl() {

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
}
