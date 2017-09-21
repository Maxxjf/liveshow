package com.qcloud.liveshow.ui.room.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.liveshow.enums.StartFansEnum;
import com.qcloud.liveshow.model.IMineModel;
import com.qcloud.liveshow.model.impl.MineModelImpl;
import com.qcloud.liveshow.ui.room.presenter.IRoomControlPresenter;
import com.qcloud.liveshow.ui.room.view.IRoomControlView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：直播间
 * Author: Kuzan
 * Date: 2017/8/23 11:43.
 */
public class RoomControlPresenterImpl extends BasePresenter<IRoomControlView> implements IRoomControlPresenter {

    private IMineModel mModel;

    public RoomControlPresenterImpl() {
        mModel = new MineModelImpl();
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

    @Override
    public void submitAttention(long id, boolean isAttention) {
        mModel.submitAttention(StartFansEnum.MyFans.getKey(), id, isAttention, new DataCallback<ReturnEmptyBean>() {
            @Override
            public void onSuccess(ReturnEmptyBean returnEmptyBean) {
                if (mView != null) {
                    mView.onFollowRes(true);
                }
            }

            @Override
            public void onError(int status, String errMsg) {
                if (mView != null) {
                    mView.onFollowRes(false);
                }
            }
        });
    }
}
