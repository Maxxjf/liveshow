package com.qcloud.liveshow.ui.mine.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.ui.mine.presenter.IFansInfoPresenter;
import com.qcloud.liveshow.ui.mine.view.IFansInfoView;
import com.qcloud.qclib.base.BasePresenter;

/**
 * 类说明：个人信息
 * Author: Kuzan
 * Date: 2017/8/15 9:26.
 */
public class FansInfoPresenterImpl extends BasePresenter<IFansInfoView> implements IFansInfoPresenter {

    public FansInfoPresenterImpl() {

    }

    @Override
    public void onBtnClick(int viewId) {
        switch (viewId) {
            case R.id.btn_send_message:
                mView.onClickSendMessage();
                break;
            case R.id.layout_follow:

                break;
            case R.id.layout_fans:

                break;
        }
    }
}
