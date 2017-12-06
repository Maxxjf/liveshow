package com.qcloud.liveshow.ui.main.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.ui.main.presenter.IGuidePresenter;
import com.qcloud.liveshow.ui.main.view.IGuideView;
import com.qcloud.qclib.base.BasePresenter;

/**
 * 类说明：启动页
 * Author: Kuzan
 * Date: 2017/11/23 9:41.
 */
public class GuidePresenterImpl extends BasePresenter<IGuideView> implements IGuidePresenter {

    public GuidePresenterImpl() {

    }

    @Override
    public void onBtnClick(int viewId) {
        switch (viewId) {
            case R.id.btn_go:
                mView.onGoClick();
                break;
        }
    }
}
