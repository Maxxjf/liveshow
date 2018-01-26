package com.qcloud.liveshow.ui.anchor.view;

import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.NettyForbiddenBean;
import com.qcloud.liveshow.beans.NettyGiftBean;
import com.qcloud.liveshow.beans.NettyLiveNoticeBean;
import com.qcloud.liveshow.beans.NettyNoticeBean;
import com.qcloud.liveshow.beans.NettyReceiveGroupBean;
import com.qcloud.liveshow.beans.NettyRoomMemberBean;
import com.qcloud.liveshow.beans.UserStatusBean;
import com.qcloud.qclib.base.BaseView;

import java.util.List;

/**
 * 类说明：直播间控制页面
 * Author: Kuzan
 * Date: 2017/9/2 15:37.
 */
public interface IAnchorControlView extends BaseView {


    /**群聊消息*/
    void addGroupChat(NettyReceiveGroupBean bean);

    /**有人退出房间*/
    void userOutGroup(NettyNoticeBean bean);

    /**更新私聊消息的状态*/
    void upDateMessageSendStatus(String chatId, int charStatus);

    void getUserIsAttention(UserStatusBean userStatusBean);

    /**更新群聊消息的状态*/
    void upDateGroupMessageStatus(int chatPosition, int charStatus);

    /**点击公告*/
    void onNoticeClick();

    /**点击发送消息*/
    void onSendMessageClick();

    /**点击接收消息*/
    void onReceiveMessageClick();

    /**点击闪光灯*/
    void onFlashClick();

    /**点击切换镜头*/
    void onSwitchCameraClick();

    /**点击分享*/
    void onShareClick();

    /**点击退出*/
    void onExitClick();

    /**刷新守护列表*/
    void replaceGuardList(List<MemberBean> been);

    /**通知成员加入*/
    void addMember(NettyRoomMemberBean bean);

    /**加入黑名单成功*/
    void backListSuccess();

    /**设为守护成功*/
    void inOutGuardSuccess();

    void refreshForbidden(NettyForbiddenBean obj);

    /**关注是否成功*/
    void onFollowRes(boolean isSuccess);

    void refreshNotice(NettyLiveNoticeBean bean);

    void loadError(String errorMsg);

    /**添加消息列表*/
    void addMessage(MemberBean bean);
    /**展示礼物*/
    void showGift(NettyGiftBean gift);
}
