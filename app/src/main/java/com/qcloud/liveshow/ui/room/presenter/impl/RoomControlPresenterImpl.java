package com.qcloud.liveshow.ui.room.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.NettyContent2Bean;
import com.qcloud.liveshow.beans.NettyContentBean;
import com.qcloud.liveshow.beans.NettyGiftBean;
import com.qcloud.liveshow.beans.NettyLiveNoticeBean;
import com.qcloud.liveshow.beans.NettyNoticeBean;
import com.qcloud.liveshow.beans.NettyReceiveGroupBean;
import com.qcloud.liveshow.beans.NettyRoomMemberBean;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.liveshow.enums.CharStatusEnum;
import com.qcloud.liveshow.enums.StartFansEnum;
import com.qcloud.liveshow.model.IAnchorModel;
import com.qcloud.liveshow.model.IIMModel;
import com.qcloud.liveshow.model.IMineModel;
import com.qcloud.liveshow.model.impl.AnchorModelImpl;
import com.qcloud.liveshow.model.impl.IMModelImpl;
import com.qcloud.liveshow.model.impl.MineModelImpl;
import com.qcloud.liveshow.ui.room.presenter.IRoomControlPresenter;
import com.qcloud.liveshow.ui.room.view.IRoomControlView;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.beans.RxBusEvent;
import com.qcloud.qclib.callback.DataCallback;
import com.qcloud.qclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 类说明：直播间
 * Author: Kuzan
 * Date: 2017/8/23 11:43.
 */
public class RoomControlPresenterImpl extends BasePresenter<IRoomControlView> implements IRoomControlPresenter {
    private IAnchorModel anchorModel;
    private IMineModel mModel;
    private IIMModel mIMModel;

    private List<String> idList;//会话列表去重
    private List<String> longList;//粉丝去重
    private Disposable disposable;

    public RoomControlPresenterImpl() {
        mModel = new MineModelImpl();
        mIMModel = new IMModelImpl();
        anchorModel = new AnchorModelImpl();
        longList = new ArrayList<>();
        idList = new ArrayList<>();
        initRxBusEvent();
    }

    private void initRxBusEvent() {
        mEventBus.registerSubscriber(this, mEventBus.obtainSubscriber(RxBusEvent.class, new Consumer<RxBusEvent>() {
            @Override
            public void accept(@NonNull RxBusEvent rxBusEvent) throws Exception {
                if (mView != null) {
                    switch (rxBusEvent.getType()) {
                        case R.id.netty_get_chat_list_success:
                            MemberBean bean = (MemberBean) rxBusEvent.getObj();
                            if (bean != null && idList.contains(bean.getIdStr())) {
                                mView.addMessage(bean);
                                idList.add(bean.getIdStr());
                            }
                            break;
                        case R.id.netty_room_member_join:
                            // 成员加入
                            NettyRoomMemberBean member = (NettyRoomMemberBean) rxBusEvent.getObj();
                            if (!longList.contains(member.getUser().getIdStr())) {
                                longList.add(member.getUser().getIdStr());
                                mView.addMember(member);
                            }
                            break;
                        case R.id.netty_group_chat:
                             mView.addGroupChat((NettyReceiveGroupBean) rxBusEvent.getObj());
                            break;
                        case R.id.netty_private_chat:
                            // 私聊消息
//                            mView.addPrivateChat((NettyReceivePrivateBean) rxBusEvent.getObj());
                            break;
                        case R.id.netty_notice_out_group:
                            // 有人退出群聊
                            mView.userOutGroup((NettyNoticeBean) rxBusEvent.getObj());
                            break;
                        case R.id.netty_notice:
                            // 公告
                            mView.refreshNotice((NettyLiveNoticeBean) rxBusEvent.getObj());
                            break;
                        case R.id.netty_message_send_success:
                            //消息发送成功
                            String chatId = (String) rxBusEvent.getObj();
                            mView.upDateApater(chatId, CharStatusEnum.SUCCESS.getKey());
                            break;
                        case R.id.netty_group_message_send_success:
                            //群聊消息发送成功
                            String uuid=(String)rxBusEvent.getObj();
                            int chatPosition = Integer.valueOf(uuid);
                            mView.upDateGroupMessageStatus(chatPosition, CharStatusEnum.SUCCESS.getKey());
                            if (disposable!=null){
                                disposable.dispose();
                            }
                            break;
                        case R.id.netty_gift_show:
                            //收到礼物消息
                            NettyGiftBean gift=(NettyGiftBean)rxBusEvent.getObj();
                            if (gift.getGift()!=null&&gift.getUser()!=null){
                                gift.getGift().setGiftCount(1);
                                gift.getGift().setSendGiftTime(System.currentTimeMillis());
                                mView.showGift(gift);
                                NettyReceiveGroupBean groupBean=new NettyReceiveGroupBean();
                                groupBean.setUser(gift.getUser());
                                groupBean.setChatId(""+ UUID.randomUUID());
                                groupBean.setContent(new NettyContentBean("送出了"+gift.getGift().getName()));
                                // 群聊消息
                                mView.addGroupChat(groupBean);
                            }

                            break;
                        case R.id.netty_no_money:
                            NettyContent2Bean contentBean= (NettyContent2Bean) rxBusEvent.getObj();
                            String content=contentBean.getContent();
                            mView.noMoney(content);
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
    public void submitAttention(int type, long id, boolean isAttention) {
        if (type == StartFansEnum.Blacklist.getKey()) {
            mModel.submitAttention(StartFansEnum.Blacklist.getKey(), id, isAttention, new DataCallback<ReturnEmptyBean>() {
                @Override
                public void onSuccess(ReturnEmptyBean returnEmptyBean) {
                    if (mView != null) {
                        mView.backListSuccess();
                    }
                }

                @Override
                public void onError(int status, String errMsg) {

                }
            });
        } else {
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
    }

    /**
     * 获取会话列表
     *
     * @time 2017/11/8 16:15
     */
    @Override
    public void getChatList() {
        mIMModel.getChatList();
    }


    /**
     * 加入群聊
     */
    @Override
    public void joinGroup(String roomNumber) {
        mIMModel.joinGroup(roomNumber);
    }

    /**
     * 发送群聊消息
     *
     * @time 2017/11/8 10:21
     */
    @Override
    public void sendGroupMessage(String roomNum, String content,int position) {
        if (UserInfoUtil.mUser!=null&&StringUtils.isNotEmptyString(content)){
            MemberBean user=new MemberBean();
            user.setMemberGradeIcon(UserInfoUtil.mUser.getMemberGradeIconLong());
            user.setAnchorGradeIcon(UserInfoUtil.mUser.getAnchorGradeIconLong());
            user.setNickName(UserInfoUtil.mUser.getNickName());
            user.setAnchor(UserInfoUtil.mUser.isAnchor());

            NettyContentBean contentBean=new NettyContentBean();
            contentBean.setText(content);

            NettyReceiveGroupBean bean=new NettyReceiveGroupBean();
            bean.setChatId(String.valueOf(position));
            bean.setCharStatusEnum(CharStatusEnum.INPROGRESS.getKey());
            bean.setContent(contentBean);
            bean.setRoom_number(roomNum);
            bean.setUser(user);
            mView.addGroupChat(bean);
            mView.upDateGroupMessageStatus(position,CharStatusEnum.INPROGRESS.getKey());
            mIMModel.sendGroupChat(roomNum, content,String.valueOf(position));
            startTime(position);
        }
    }
    /**
     * 开始计时，5秒后发送失败
     *
     * @param
     */
    public void startTime(int position) {
        disposable = Observable.timer(5, TimeUnit.SECONDS).observeOn(Schedulers.io()).
                subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.upDateGroupMessageStatus(position, CharStatusEnum.FAIL.getKey());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.upDateGroupMessageStatus(position, CharStatusEnum.FAIL.getKey());
                    }
                });
    }
    /**
     * 设置/取消守护
     *
     * @param memberId
     * @param isGuard
     */
    @Override
    public void inOutGuard(long memberId, boolean isGuard) {
        anchorModel.inOutGuard(memberId, isGuard, new DataCallback<ReturnEmptyBean>() {
            @Override
            public void onSuccess(ReturnEmptyBean returnEmptyBean) {
                mView.inOutGuardSuccess();
            }

            @Override
            public void onError(int status, String errMsg) {
                mView.inOutGuardError(errMsg);
            }
        });
    }

    @Override
    public void shutUp(String roomNumber, String memberId, boolean isForbidden) {
        mIMModel.shutUp(roomNumber, memberId, isForbidden);
    }


}
