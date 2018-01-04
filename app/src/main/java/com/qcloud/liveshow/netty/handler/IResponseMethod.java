package com.qcloud.liveshow.netty.handler;

import com.google.gson.JsonElement;

import io.reactivex.annotations.NonNull;

/**
 * 类说明：数据响应处理方法
 * Author: Kuzan
 * Date: 2017/11/6 13:51.
 */
public interface IResponseMethod {
    /**鉴权*/
    void disposeAuth(@NonNull JsonElement msgConfig);

    /**群聊*/
    void disposeGroup(@NonNull JsonElement msgConfig);

    /**私聊*/
    void disposePrivate(@NonNull JsonElement msgConfig);

    void disposeGift(JsonElement msgConfig);

    /**私聊会话列表*/
    void disposeChatList(@NonNull JsonElement msgConfig);

    /**有人退出群*/
    void disposeUserOutGroup(@NonNull JsonElement msgConfig);
    /**通知*/
    void disposeNotice(JsonElement msgConfig);
    /**钻石币不足*/
    void disposeNoMoney(JsonElement msgConfig);

    /**看直播成员*/
    void disposeGroupMember(@NonNull JsonElement msgConfig);
}
