package com.qcloud.liveshow.netty.callback;

/**
 * 类说明：消息推送
 * Author: Kuzan
 * Date: 2017/11/1 11:43.
 */
public interface PushCallBack<T> {
    void Response(T t);
}
