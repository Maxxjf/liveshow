package com.qcloud.liveshow.ui.room.presenter;

import com.qcloud.qclib.base.BtnClickPresenter;

/**
 * 类说明：直播间
 * Author: Kuzan
 * Date: 2017/8/23 11:43.
 */
public interface IRoomControlPresenter extends BtnClickPresenter {

    /**关注该主播员*/
    void submitAttention(int Type,long id, boolean isAttention);

    /**获取会话列表*/
    void getChatList();

    /**加入群聊*/
    void joinGroup(String roomNumber);

    /**发送群聊消息*/
    void sendGroupMessage(String roomNum, String content,int position);

    /**设置守护/取消守护*/
    void  inOutGuard(long memberId, boolean isGuard);

    void getUserIdentity(String memberId, String roomId);

    void getUserIsAttention(String idStr, String roomIdStr);

    void  shutUp(String roomNumber, String memberId, boolean isForbidden);


    void payVip(String roomId);

    void outGroup(String roomNum);

    void watchCalculate(String roomNum);
}
