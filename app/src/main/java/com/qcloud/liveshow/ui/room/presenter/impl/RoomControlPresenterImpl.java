package com.qcloud.liveshow.ui.room.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.ui.room.presenter.IRoomControlPresenter;
import com.qcloud.liveshow.ui.room.view.IRoomControlView;
import com.qcloud.qclib.base.BasePresenter;

/**
 * 类说明：直播间
 * Author: Kuzan
 * Date: 2017/8/23 11:43.
 */
public class RoomControlPresenterImpl extends BasePresenter<IRoomControlView> implements IRoomControlPresenter {

    public RoomControlPresenterImpl() {

    }

    @Override
    public void onBtnClick(int viewId) {
        switch (viewId) {
            case R.id.btn_follow:
                mView.onFollowClick();
                break;
            case R.id.btn_notice:
                mView.onNoticeClick();
                break;
            case R.id.btn_send_message:
                mView.onSendMessageClick();
                break;
            case R.id.btn_buy_diamonds:
                mView.onBuyDiamondsClick();
                break;
            case R.id.btn_share:
                mView.onShareClick();
                break;
            case R.id.btn_receive_message:
                mView.onReceiveMessageClick();
                break;
            case R.id.btn_send_gift:
                mView.onSendGiftClick();
                break;
            case R.id.btn_exit:
                mView.onExitClick();
                break;
        }
    }
}
