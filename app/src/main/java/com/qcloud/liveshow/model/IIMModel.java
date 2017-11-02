package com.qcloud.liveshow.model;

import com.qcloud.liveshow.beans.NettyAuthBean;
import com.qcloud.liveshow.beans.NettyChatListBean;
import com.qcloud.liveshow.beans.NettyGroupBean;
import com.qcloud.liveshow.beans.NettyNoticeBean;
import com.qcloud.liveshow.beans.NettyReceiveSingleBean;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：IM通讯有关
 * Author: Kuzan
 * Date: 2017/11/2 9:34.
 */
public interface IIMModel {
    /**鉴权*/
    void auth(DataCallback<NettyAuthBean> callback);

    /**获取会话列表*/
    void getChatList(DataCallback<NettyChatListBean> callback);

    /**发送私聊消息*/
    void sendSingleChat(String userId, String content, DataCallback<NettyReceiveSingleBean> callback);

    /**发送群聊消息*/
    void sendGroupChat(DataCallback<NettyGroupBean> callback);

    /**发送群聊公告*/
    void sendGroupNotice(DataCallback<NettyNoticeBean> callback);

    /**加入群聊*/
    void joinGroup(String roomNum, String userId, DataCallback<NettyGroupBean> callback);

    /**退出群聊*/
    void outGroup(String roomNum, String userId, DataCallback<NettyNoticeBean> callback);

    /**拉黑某用户*/

    /**踢出某用户*/
}
