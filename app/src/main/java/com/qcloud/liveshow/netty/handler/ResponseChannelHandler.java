package com.qcloud.liveshow.netty.handler;

import android.text.TextUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.qcloud.liveshow.netty.callback.ResponseListener;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import timber.log.Timber;

/**
 * 类说明：响应消息处理
 * Author: Kuzan
 * Date: 2017/11/1 11:48.
 */
@ChannelHandler.Sharable
public class ResponseChannelHandler extends ChannelHandlerSuper {
    private ResponseListener mListener;

    protected ResponseChannelHandler() {
        mListener = new privateResponseListener();
    }

    public ResponseChannelHandler addListener(ResponseListener listener) {
        this.mListener = listener;
        return this;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        self:
        if (msg != null) {
            try {
                String response = (String) msg;
                if (TextUtils.isEmpty(response)) {
                    break self;
                }
                JsonElement element = new JsonParser().parse(response);
                if (ObservableNotify(ctx, element)) return;
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
        //super.channelRead(ctx, msg);
    }

    protected boolean ObservableNotify(ChannelHandlerContext ctx, JsonElement msg) throws Exception {
        Timber.i("notify msg: " + msg);
        return mListener.channelRead(ctx, msg);
    }

    private class privateResponseListener implements ResponseListener {
        @Override
        public boolean channelRead(ChannelHandlerContext ctx, JsonElement msg) throws Exception {
            return false;
        }
    }
}
