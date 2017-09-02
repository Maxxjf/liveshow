package com.qcloud.liveshow.ui.anchor.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.ui.anchor.presenter.IAnchorControlPresenter;
import com.qcloud.liveshow.ui.anchor.view.IAnchorControlView;
import com.qcloud.qclib.base.BasePresenter;

/**
 * 类说明：直播间控制页面
 * Author: Kuzan
 * Date: 2017/9/2 15:49.
 */
public class AnchorControlPresenterImpl extends BasePresenter<IAnchorControlView> implements IAnchorControlPresenter {

    public AnchorControlPresenterImpl() {

    }

    @Override
    public void onBtnClick(int viewId) {
        switch (viewId) {
            case R.id.btn_notice:
                mView.onNoticeClick();
                break;
            case R.id.btn_send_message:
                mView.onSendMessageClick();
                break;
            case R.id.btn_receive_message:
                mView.onReceiveMessageClick();
                break;
            case R.id.btn_flash:
                mView.onFlashClick();
                break;
            case R.id.btn_switch_camera:
                mView.onSwitchCameraClick();
                break;
            case R.id.btn_share:
                mView.onShareClick();
                break;
            case R.id.btn_exit:
                mView.onExitClick();
                break;
        }
    }
}
