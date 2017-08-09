package com.qcloud.liveshow.ui.main.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.ui.main.presenter.IMainPresenter;
import com.qcloud.liveshow.ui.main.view.IMainView;
import com.qcloud.qclib.base.BasePresenter;

/**
 * 类说明：首页
 * Author: Kuzan
 * Date: 2017/8/1 19:17.
 */
public class MainPresenterImpl extends BasePresenter<IMainView> implements IMainPresenter {

    public MainPresenterImpl() {

    }

    @Override
    public void onBtnClick(int viewId) {
        switch (viewId) {
            case R.id.btn_home:
                mView.onHomeClick();
                break;
            case R.id.btn_live_show:
                mView.onLiveShowClick();
                break;
            case R.id.btn_mine:
                mView.onMineClick();
                break;
        }
    }
}
