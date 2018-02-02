package com.qcloud.liveshow.ui.home.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.NettyContentBean;
import com.qcloud.liveshow.beans.NettyReceivePrivateBean;
import com.qcloud.liveshow.enums.CharStatusEnum;
import com.qcloud.liveshow.model.IIMModel;
import com.qcloud.liveshow.model.impl.IMModelImpl;
import com.qcloud.liveshow.realm.RealmHelper;
import com.qcloud.liveshow.ui.home.presenter.IFansMessagePresenter;
import com.qcloud.liveshow.ui.home.view.IFansMessageView;
import com.qcloud.liveshow.utils.MessageUtil;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.beans.RxBusEvent;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 类说明：粉丝消息
 * Author: Kuzan
 * Date: 2017/9/11 12:04.
 */
public class FansMessagePresenterImpl extends BasePresenter<IFansMessageView> implements IFansMessagePresenter {
    private IIMModel mModel;
    private Disposable disposable;

    public FansMessagePresenterImpl() {
        mModel = new IMModelImpl();
        initRxBusEvent();
    }

    private void initRxBusEvent() {
        mEventBus.registerSubscriber(this, mEventBus.obtainSubscriber(RxBusEvent.class, rxBusEvent -> {
            if (mView != null) {
                switch (rxBusEvent.getType()) {
//                    case R.id.netty_get_chat_list_success:
//                        break;
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
                    case R.id.netty_message_send_success:
                        //消息发送成功
                        String chatId = (String) rxBusEvent.getObj();
                        mView.upDateApater(chatId, CharStatusEnum.SUCCESS.getKey());
                        if (disposable != null && !disposable.isDisposed()) {
                            disposable.dispose();
                        }
                        break;

                }
            }
        }));
    }

    @Override
    public void getChars(String fromUserId) {
        List<NettyReceivePrivateBean> charList = RealmHelper.getInstance().queryListByValue(
                NettyReceivePrivateBean.class, "from_user_id", fromUserId, "date_time");
        if (charList != null) {
            mView.replaceList(charList);
        }
    }

    @Override
    public void sendMessage(MemberBean memberBean, String content) {
        if (!MessageUtil.getInstance().isInList(memberBean.getIdStr())){//如果Member不在列表里
            RealmHelper.getInstance().addOrUpdateBean(memberBean);//添加到本地数据
        }
        NettyContentBean contentBean = new NettyContentBean();
        contentBean.setText(content);
        NettyReceivePrivateBean nettyReceivePrivateBean = new NettyReceivePrivateBean();
        nettyReceivePrivateBean.setDate_time(String.valueOf(System.currentTimeMillis()));
        nettyReceivePrivateBean.setChat_id("" + UUID.randomUUID());
        nettyReceivePrivateBean.setFrom_user_id(memberBean.getIdStr());
        nettyReceivePrivateBean.setSend(true);
        nettyReceivePrivateBean.setContent(contentBean);
        nettyReceivePrivateBean.setSendStatus(CharStatusEnum.INPROGRESS.getKey());
        mView.addMessage(nettyReceivePrivateBean);
        RealmHelper.getInstance().addOrUpdateBean(nettyReceivePrivateBean);
        mModel.sendPrivateChat(memberBean.getIdStr(), content, nettyReceivePrivateBean.getChat_id());
        startTime(nettyReceivePrivateBean);

    }

    public void startTime(NettyReceivePrivateBean bean) {
        disposable = Observable.timer(5, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        RealmHelper.getInstance().updateMessageStatus(bean.getChat_id(), CharStatusEnum.FAIL.getKey());
                        if (mView!=null){
                            mView.upDateApater(bean.getChat_id(), CharStatusEnum.FAIL.getKey());
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        RealmHelper.getInstance().updateMessageStatus(bean.getChat_id(), CharStatusEnum.FAIL.getKey());
                        if (mView!=null){
                            mView.upDateApater(bean.getChat_id(), CharStatusEnum.FAIL.getKey());
                        }
                    }
                });
    }

    @Override
    public void onBtnClick(int viewId) {
        switch (viewId) {
            case R.id.btn_emoticon:
                mView.onEmojiClick();
                break;
        }
    }

}
