package com.qcloud.liveshow.model;

/**
 * 类说明：IM通讯有关
 * Author: Kuzan
 * Date: 2017/11/2 9:34.
 */
public interface IIMModel {
    /**鉴权*/
    void auth();

    /**获取会话列表*/
    void getChatList();

    /**发送私聊消息*/
    void sendSingleChat(String userId, String content);

    /**发送群聊消息*/
    void sendGroupChat();

    /**发送群聊公告*/
    void sendGroupNotice();

    /**加入群聊*/
    void joinGroup(String roomNum);

    /**退出群聊*/
    void outGroup(String roomNum, String userId);

    /**拉黑某用户*/

    /**踢出某用户*/
}
