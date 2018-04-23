package com.qcloud.liveshow.ui.anchor.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.FinishIncomeBean;
import com.qcloud.liveshow.model.IAnchorModel;
import com.qcloud.liveshow.model.impl.AnchorModelImpl;
import com.qcloud.liveshow.ui.anchor.presenter.IAnchorFinishPresenter;
import com.qcloud.liveshow.ui.anchor.view.IAnchorFinishView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.callback.DataCallback;

import timber.log.Timber;

/**
 * 类说明：直播完成页面
 * Author: Kuzan
 * Date: 2017/9/2 16:46.
 */
public class AnchorFinishPresenterImpl extends BasePresenter<IAnchorFinishView> implements IAnchorFinishPresenter {
    private IAnchorModel mModel;
    public AnchorFinishPresenterImpl() {
        mModel=new AnchorModelImpl();
    }

    /**
     * 结束直播的收益
     */
    @Override
    public void finishLive(String roomId) {
        mModel.finishLive(roomId,new DataCallback<FinishIncomeBean>() {
            @Override
            public void onSuccess(FinishIncomeBean bean) {
                if (mView!=null){
                    Timber.e("FinishIncomeBean:"+bean);
                    mView.loadData(bean);
                    Timber.e("关闭直播成功");
                }
            }

            @Override
            public void onError(int status, String errMsg) {
                if (mView!=null){
                    Timber.e("errMsg:");
                    mView.loadErr(true,errMsg);
                }
            }
        });
    }
    @Override
    public void onBtnClick(int viewId) {
        switch (viewId) {
            case R.id.btn_go_home:
                mView.onGoHomeClick();
                break;
        }
    }
}
