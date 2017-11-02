package com.qcloud.liveshow.netty.handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.qcloud.liveshow.netty.NettyConstants;
import com.qcloud.liveshow.netty.callback.PushCallBack;
import com.qcloud.liveshow.netty.message.MessageHeart;
import com.qcloud.liveshow.netty.message.MessageLogin;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import timber.log.Timber;

/**
 * 类说明：登录监听
 * Author: Kuzan
 * Date: 2017/11/1 12:06.
 */
@ChannelHandler.Sharable
public class LoginHandler extends ResponseChannelHandler {
    private ConcurrentLinkedQueue<Long> concurrentLinkedQueue;
    private final String tag;
    private PushCallBack<Boolean> callback;
    private int count;
    private boolean isHeartStart;

    public LoginHandler(String tag) {
        this.tag = tag;
        concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        callback = new PushCallBack<Boolean>() {
            @Override
            public void Response(Boolean aBoolean) {
                Timber.d("SOCKET_STATE = " + aBoolean);
            }
        };
    }

    public LoginHandler(String tag, PushCallBack<Boolean> callBack) {
        this(tag);
        this.callback = callBack;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        if (callback != null) {
            callback.Response(ClientImpl.newInstances().getConnectState());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(MessageLogin.create(tag));
    }

    @Override
    protected boolean ObservableNotify(final ChannelHandlerContext ctx, JsonElement msg) throws Exception {
        if (!isHeartStart && msg.isJsonObject()) {
            JsonObject object = (JsonObject) msg;
            int type = object.get(NettyConstants.PARAMS.RESPONSE.TYPE).getAsInt();
            if (type == NettyConstants.TYPE.REQUEST.LOGIN) {
                int code = object.get(NettyConstants.PARAMS.RESPONSE.CODE).getAsInt();
                if (code == NettyConstants.CODE.RESPONSE.SUCCESS) {
                    isHeartStart = true;
                    clear();
                    ctx.executor().scheduleAtFixedRate(new Runnable() {
                        @Override
                        public void run() {
                            ctx.writeAndFlush(MessageHeart.create("android", tag));
                        }
                    }, 2, 4, TimeUnit.SECONDS);
                } else {
                    isHeartStart = false;
                    // FIXME: 16/10/13 登录重试
                    ctx.executor().schedule(new Runnable() {
                        @Override
                        public void run() {
                            if (count > 3) {// FIXME: 16/10/14 不用size(),耗时
                                clear();
                                ctx.close();
                                return;
                            }
                            ++count;
                            ctx.writeAndFlush(MessageLogin.create(tag));
                            concurrentLinkedQueue.add(System.currentTimeMillis());
                        }
                    }, 500, TimeUnit.MILLISECONDS);
                }
                return true;
            }
        }
        return super.ObservableNotify(ctx, msg);
    }

    public void clear() {
        concurrentLinkedQueue.clear();
        count = 0;
    }
}
