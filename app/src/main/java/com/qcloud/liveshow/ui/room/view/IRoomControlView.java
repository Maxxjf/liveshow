package com.qcloud.liveshow.ui.room.view;

import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.NettyForbiddenBean;
import com.qcloud.liveshow.beans.NettyGiftBean;
import com.qcloud.liveshow.beans.NettyLiveNoticeBean;
import com.qcloud.liveshow.beans.NettyNoticeBean;
import com.qcloud.liveshow.beans.NettyPayVipRoomReveice;
import com.qcloud.liveshow.beans.NettyReceiveGroupBean;
import com.qcloud.liveshow.beans.NettyReceivePrivateBean;
import com.qcloud.liveshow.beans.NettyRoomMemberBean;
import com.qcloud.liveshow.beans.UserStatusBean;

import java.util.List;

/**
 * 类说明：直播间
 * Author: Kuzan
 * Date: 2017/8/23 11:42.
 */
public interface IRoomControlView {

    /**点击关注*/
    void onFollowClick();

    void showGift(NettyGiftBean gift);

    /**点击公告*/
    void onNoticeClick();

    /**点击发送消息*/
    void onSendMessageClick();

    /**点击购买钻石币*/
    void onBuyDiamondsClick();

    /**点击分享*/
    void onShareClick();

    /**点击接收消息*/
    void onReceiveMessageClick();

    /**点击送礼物*/
    void onSendGiftClick();

    /**点击退出*/
    void onExitClick();

    /**关注返回*/
    void onFollowRes(boolean isSuccess);

    /**获取会话列表*/
    void replaceChatList(List<MemberBean> beans);

    /**通知成员加入*/
    void addMember(NettyRoomMemberBean bean);

    /**群聊消息*/
    void addGroupChat(NettyReceiveGroupBean bean);

    /**私聊消息*/
    void addPrivateChat(NettyReceivePrivateBean bean);

    /**有用户退出群聊*/
    void userOutGroup(NettyNoticeBean bean);

    /**成功拉入黑名单*/
    void backListSuccess();

    /**成功设置守护*/
    void inOutGuardSuccess();

    /**成功设置守护*/
    void inOutGuardError(String msg);

    /**刷新群聊公告*/
    void refreshNotice(NettyLiveNoticeBean bean);

    /**添加消息列表*/
    void addMessage(MemberBean bean);

    /**添加消息列表*/
    void upDateApater(String charId,int charStatus);

    /**钻石币不够,要去充值*/
    void noMoney();

    /**更新信息发送状态*/
    void upDateGroupMessageStatus(int charPosition, int charStatus);

    /**收费房间收到消息*/
    void payVipRoom(NettyPayVipRoomReveice payVipRoomReveice);

    void getUserIdentity(UserStatusBean userStatusBean);

    void getUserIsAttention(UserStatusBean userStatusBean);
    /**更新禁言状态*/
    void refreshForbidden(NettyForbiddenBean obj);
}
