package com.qcloud.liveshow.ui.home.presenter.impl;

import android.util.Log;

import com.qcloud.liveshow.beans.RetBean;
import com.qcloud.liveshow.model.ITestModel;
import com.qcloud.liveshow.model.impl.TestModelImpl;
import com.qcloud.liveshow.ui.home.presenter.IHotPresenter;
import com.qcloud.liveshow.ui.home.view.IHotView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：热门
 * Author: Kuzan
 * Date: 2017/8/11 11:29.
 */
public class HotPresenterImpl extends BasePresenter<IHotView> implements IHotPresenter {

    private ITestModel mModel;

    public HotPresenterImpl() {
        mModel = new TestModelImpl();
    }

    @Override
    public void loadData() {
        mModel.loadData(new DataCallback<RetBean>() {
            @Override
            public void onSuccess(RetBean retBean) {
                if (mView == null) {
                    return;
                }
                if (retBean != null) {
                    Log.e("TAG", retBean.toString());
                    mView.replaceList(retBean.getLives());
                }
            }

            @Override
            public void onError(int status, String errMsg) {

            }
        });
    }
}
