package com.qcloud.liveshow.netty.callback;

import com.google.gson.JsonElement;

import io.netty.channel.ChannelHandlerContext;

/**
 * 类说明：消息响应处理
 * Author: Kuzan
 * Date: 2017/11/1 11:42.
 */
public interface ResponseListener {
    /**
     * 消息接收类型处理
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    boolean channelRead(final ChannelHandlerContext ctx, JsonElement msg) throws Exception;
}
