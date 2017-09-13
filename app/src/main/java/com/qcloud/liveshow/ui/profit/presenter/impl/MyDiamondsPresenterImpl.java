package com.qcloud.liveshow.ui.profit.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.DiamondsBean;
import com.qcloud.liveshow.model.IProfitModel;
import com.qcloud.liveshow.model.impl.ProfitModelImpl;
import com.qcloud.liveshow.ui.profit.presenter.IMyDiamondsPresenter;
import com.qcloud.liveshow.ui.profit.view.IMyDiamondsView;
import com.qcloud.liveshow.utils.BasicsUtil;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.beans.ReturnDataBean;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：我的钻石币
 * Author: Kuzan
 * Date: 2017/9/1 15:30.
 */
public class MyDiamondsPresenterImpl extends BasePresenter<IMyDiamondsView> implements IMyDiamondsPresenter {

    private IProfitModel mModel;

    public MyDiamondsPresenterImpl() {
        mModel = new ProfitModelImpl();
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

    /**
     * 获取钻石币充值套餐
     * */
    @Override
    public void getDiamondsList() {
        if (BasicsUtil.mDiamondsList != null && BasicsUtil.mDiamondsList.size() > 0) {
            if (mView != null) {
                mView.replaceDiamondsList(BasicsUtil.mDiamondsList);
            }
        } else {
            mModel.getDiamondsList(new DataCallback<ReturnDataBean<DiamondsBean>>() {
                @Override
                public void onSuccess(ReturnDataBean<DiamondsBean> bean) {
                    if (mView == null) {
                        return;
                    }
                    if (bean != null) {
                        BasicsUtil.mDiamondsList = bean.getList();
                        mView.replaceDiamondsList(bean.getList());
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
}
