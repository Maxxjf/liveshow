package com.qcloud.liveshow.ui.room.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.ui.room.presenter.IRoomFinishPresenter;
import com.qcloud.liveshow.ui.room.view.IRoomFinishView;
import com.qcloud.qclib.base.BasePresenter;

/**
 * 类说明：直播完成
 * Author: Kuzan
 * Date: 2017/9/11 15:39.
 */
public class RoomFinishPresenterImpl extends BasePresenter<IRoomFinishView> implements IRoomFinishPresenter {

    public RoomFinishPresenterImpl() {

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
