package com.qcloud.liveshow.ui.room.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.NettyRoomMemberBean;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.liveshow.enums.StartFansEnum;
import com.qcloud.liveshow.model.IIMModel;
import com.qcloud.liveshow.model.IMineModel;
import com.qcloud.liveshow.model.impl.IMModelImpl;
import com.qcloud.liveshow.model.impl.MineModelImpl;
import com.qcloud.liveshow.ui.room.presenter.IRoomControlPresenter;
import com.qcloud.liveshow.ui.room.view.IRoomControlView;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.beans.RxBusEvent;
import com.qcloud.qclib.callback.DataCallback;
import com.qcloud.qclib.rxbus.Bus;
import com.qcloud.qclib.rxbus.BusProvider;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * 类说明：直播间
 * Author: Kuzan
 * Date: 2017/8/23 11:43.
 */
public class RoomControlPresenterImpl extends BasePresenter<IRoomControlView> implements IRoomControlPresenter {

    private IMineModel mModel;
    private IIMModel mIMModel;
    private Bus mEventBus = BusProvider.getInstance();

    public RoomControlPresenterImpl() {
        mModel = new MineModelImpl();
        mIMModel = new IMModelImpl();
        mEventBus.register(this);
        initRxBusEvent();
    }

    private void initRxBusEvent() {
        mEventBus.registerSubscriber(this, mEventBus.obtainSubscriber(RxBusEvent.class, new Consumer<RxBusEvent>() {
            @Override
            public void accept(@NonNull RxBusEvent rxBusEvent) throws Exception {
                if (mView != null) {
                    switch (rxBusEvent.getType()) {
                        case R.id.netty_room_member_join:
                            NettyRoomMemberBean bean = (NettyRoomMemberBean) rxBusEvent.getObj();
                            mView.addMember(bean);
                            break;
                    }
                }
            }
        }));
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
                UserInfoUtil.loadUserInfo();
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

    /**
     * 发送群聊消息
     *
     * @time 2017/11/8 10:21
     */
    @Override
    public void sendGroupMessage(String roomNum, String content) {
        mIMModel.sendGroupChat(roomNum, content);
    }

    public void onDestroy() {
        if (mEventBus != null) {
            mEventBus.unregister(this);
            mEventBus = null;
        }
    }
}
