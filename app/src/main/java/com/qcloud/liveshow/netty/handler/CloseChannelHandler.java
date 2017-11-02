package com.qcloud.liveshow.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

/**
 * 类说明：关闭处理
 * Author: Kuzan
 * Date: 2017/11/1 11:58.
 */
public class CloseChannelHandler extends ChannelHandlerSuper {
    private final ClientImpl client;

    public CloseChannelHandler(ClientImpl client) {
        this.client = client;
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        this.client.restart(true);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        this.client.restart(true);
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        super.close(ctx, promise);
        this.client.close();
    }
}
