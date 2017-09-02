package com.qcloud.liveshow.ui.anchor.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.ui.anchor.presenter.IAnchorFinishPresenter;
import com.qcloud.liveshow.ui.anchor.view.IAnchorFinishView;
import com.qcloud.qclib.base.BasePresenter;

/**
 * 类说明：直播完成页面
 * Author: Kuzan
 * Date: 2017/9/2 16:46.
 */
public class AnchorFinishPresenterImpl extends BasePresenter<IAnchorFinishView> implements IAnchorFinishPresenter {

    public AnchorFinishPresenterImpl() {

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
