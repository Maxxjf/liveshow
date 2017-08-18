package com.qcloud.liveshow.ui.mine.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.ui.mine.presenter.ISettingPresenter;
import com.qcloud.liveshow.ui.mine.view.ISettingView;
import com.qcloud.qclib.base.BasePresenter;

/**
 * 类说明：设置
 * Author: Kuzan
 * Date: 2017/8/18 18:41.
 */
public class SettingPresenterImpl extends BasePresenter<ISettingView> implements ISettingPresenter {

    public SettingPresenterImpl() {

    }

    @Override
    public void onBtnClick(int viewId) {
        switch (viewId) {
            case R.id.layout_blacklist:
                mView.onBlacklistClick();
                break;
            case R.id.layout_about_us:
                mView.onAboutUsClick();
                break;
            case R.id.layout_clear_cache:
                mView.onClearCacheClick();
                break;
            case R.id.layout_problem:
                mView.onProblemClick();
                break;
            case R.id.btn_logout:
                mView.onLogoutClick();
                break;
        }
    }
}
