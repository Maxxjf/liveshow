package com.qcloud.liveshow.ui.home.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.NettyContentBean;
import com.qcloud.liveshow.beans.NettyReceivePrivateBean;
import com.qcloud.liveshow.model.IIMModel;
import com.qcloud.liveshow.model.impl.IMModelImpl;
import com.qcloud.liveshow.realm.RealmHelper;
import com.qcloud.liveshow.ui.home.presenter.IFansMessagePresenter;
import com.qcloud.liveshow.ui.home.view.IFansMessageView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.beans.RxBusEvent;
import com.qcloud.qclib.rxbus.Bus;
import com.qcloud.qclib.rxbus.BusProvider;

import java.util.List;
import java.util.UUID;

/**
 * 类说明：粉丝消息
 * Author: Kuzan
 * Date: 2017/9/11 12:04.
 */
public class FansMessagePresenterImpl extends BasePresenter<IFansMessageView> implements IFansMessagePresenter {
    private IIMModel mModel;
    private Bus mEventBus = BusProvider.getInstance();

    public FansMessagePresenterImpl() {
        mModel = new IMModelImpl();
        initRxBusEvent();
    }

    private void initRxBusEvent() {
        mEventBus.registerSubscriber(this, mEventBus.obtainSubscriber(RxBusEvent.class, rxBusEvent -> {
            if (mView != null) {
                switch (rxBusEvent.getType()) {
                    case R.id.netty_get_chat_list_success:
                        break;
                    case R.id.netty_room_member_join:
                        break;
                    case R.id.netty_group_chat:
                        // 群聊消息
                        break;
                    case R.id.netty_private_chat:
                        // 私聊消息
                        mView.addMessage((NettyReceivePrivateBean) rxBusEvent.getObj());
                        break;
                    case R.id.netty_notice_out_group:
                        // 通知
                        break;
                }
            }
        }));
    }

    @Override
    public void getChars(String fromUserId) {
        List<NettyReceivePrivateBean> charList = RealmHelper.getInstance().queryListByValue(
                NettyReceivePrivateBean.class, "from_user_id", fromUserId,"date_time");
        if (charList != null) {
            mView.replaceList(charList);
        }
    }

    @Override
    public void sendMessage(String userId, String content) {
        NettyContentBean contentBean = new NettyContentBean();
        contentBean.setText(content);
        NettyReceivePrivateBean nettyReceivePrivateBean = new NettyReceivePrivateBean();
        nettyReceivePrivateBean.setDate_time(String.valueOf(System.currentTimeMillis()));
        nettyReceivePrivateBean.setChat_id("" + UUID.randomUUID());
        nettyReceivePrivateBean.setFrom_user_id(userId);
        nettyReceivePrivateBean.setSend(true);
        nettyReceivePrivateBean.setContent(contentBean);
        mView.addMessage(nettyReceivePrivateBean);
        RealmHelper.getInstance().addOrUpdateBean(nettyReceivePrivateBean);

        mModel.sendPrivateChat(userId, content);
    }

    @Override
    public void onBtnClick(int viewId) {
        switch (viewId) {
            case R.id.btn_emoticon:
                mView.onEmojiClick();
                break;
        }
    }

    public void onDestroy() {
        if (mEventBus != null) {
            mEventBus.unregister(this);
            mEventBus = null;
        }
    }
}
