package com.qcloud.liveshow.ui.anchor.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.NettyRoomMemberBean;
import com.qcloud.liveshow.model.IAnchorModel;
import com.qcloud.liveshow.model.IIMModel;
import com.qcloud.liveshow.model.impl.AnchorModelImpl;
import com.qcloud.liveshow.model.impl.IMModelImpl;
import com.qcloud.liveshow.ui.anchor.presenter.IAnchorControlPresenter;
import com.qcloud.liveshow.ui.anchor.view.IAnchorControlView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.beans.ReturnDataBean;
import com.qcloud.qclib.beans.RxBusEvent;
import com.qcloud.qclib.callback.DataCallback;
import com.qcloud.qclib.rxbus.Bus;
import com.qcloud.qclib.rxbus.BusProvider;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * 类说明：直播间控制页面
 * Author: Kuzan
 * Date: 2017/9/2 15:49.
 */
public class AnchorControlPresenterImpl extends BasePresenter<IAnchorControlView> implements IAnchorControlPresenter {

    private IAnchorModel mModel;
    private IIMModel mIMModel;
    private Bus mEventBus = BusProvider.getInstance();

    public AnchorControlPresenterImpl() {
        mModel = new AnchorModelImpl();
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

    @Override
    public void getGuardList() {
        mModel.getGuardList(new DataCallback<ReturnDataBean<MemberBean>>() {
            @Override
            public void onSuccess(ReturnDataBean<MemberBean> bean) {
                if (mView == null) {
                    return;
                }
                if (bean != null) {
                    mView.replaceGuardList(bean.getList());
                }
            }

            @Override
            public void onError(int status, String errMsg) {
                if (mView != null) {
                    mView.loadErr(false, errMsg);
                }
            }
        });
    }

    @Override
    public void setGuard(long memberId, boolean isGuard) {

    }

    @Override
    public void addBlacklist(long id, boolean isBlacklist) {

    }

    /**
     * 发送群聊消息
     *
     * @time 2017/11/8 10:23
     */
    @Override
    public void sendGroupMessage(String roomNum, String content) {
        mIMModel.sendGroupChat(roomNum, content);
    }
}
