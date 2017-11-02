package com.qcloud.liveshow.netty.callback;

import com.qcloud.liveshow.netty.message.MessageResponse;

import io.netty.channel.ChannelHandlerContext;

/**
 * 类说明：消息响应处理
 * Author: Kuzan
 * Date: 2017/11/1 11:42.
 */
public interface ResponseListener<T> {
    /**
     * 消息接收类型处理
     *
     * @param ctx
     * @param msg {@link MessageResponse}
     * @throws Exception
     */
    boolean channelRead(final ChannelHandlerContext ctx, T msg) throws Exception;
}
