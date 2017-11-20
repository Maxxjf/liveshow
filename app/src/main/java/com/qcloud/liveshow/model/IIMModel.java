package com.qcloud.liveshow.model;

/**
 * 类说明：IM通讯有关
 * Author: Kuzan
 * Date: 2017/11/2 9:34.
 */
public interface IIMModel {
    /**
     * 鉴权
     */
    void auth();

    /**
     * 获取会话列表
     */
    void getChatList();

    /**
     * 发送私聊消息
     */
    void sendPrivateChat(String userId, String content);

    /**
     * 发送群聊消息
     */
    void sendGroupChat(String roomNum, String content);

    /**
     * 发送群聊公告
     */
    void sendGroupNotice(String roomNum, String content);

    /**
     * 加入群聊
     */
    void joinGroup(String roomNum);

    /**
     * 退出群聊
     */
    void outGroup(String roomNum);

    /**
     * 踢出某用户
     */

    void shutUp(String roomNum, String memberId, boolean isForbidden);

    /**
     * 删除私聊列表
     */
    void deleteMessage(String to_user_id);
    /**拉黑某用户*/

    /**踢出某用户*/


}
