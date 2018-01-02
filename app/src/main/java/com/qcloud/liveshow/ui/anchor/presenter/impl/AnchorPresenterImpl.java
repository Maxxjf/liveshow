package com.qcloud.liveshow.ui.anchor.presenter.impl;

import com.qcloud.liveshow.beans.FinishIncomeBean;
import com.qcloud.liveshow.model.IAnchorModel;
import com.qcloud.liveshow.model.impl.AnchorModelImpl;
import com.qcloud.liveshow.ui.anchor.presenter.IAnchorPresenter;
import com.qcloud.liveshow.ui.anchor.view.IAnchorView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.callback.DataCallback;

import timber.log.Timber;

/**
 * 类说明：直播间
 * Author: Kuzan
 * Date: 2017/9/1 17:19.
 */
public class AnchorPresenterImpl extends BasePresenter<IAnchorView> implements IAnchorPresenter {

    private IAnchorModel mModel;

    public AnchorPresenterImpl() {
        mModel = new AnchorModelImpl();
    }

    @Override
    public void finishLive() {
        mModel.finishLive(new DataCallback<FinishIncomeBean>() {
            @Override
            public void onSuccess(FinishIncomeBean returnEmptyBean) {
                Timber.e("关闭直播成功");
            }

            @Override
            public void onError(int status, String errMsg) {
                Timber.e(errMsg);
            }
        });
    }
}
