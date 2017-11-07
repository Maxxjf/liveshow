package com.qcloud.liveshow.netty.handler;

import android.text.TextUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.qcloud.liveshow.netty.callback.ResponseListener;

import io.netty.channel.ChannelHandlerContext;
import timber.log.Timber;

/**
 * 类说明：响应消息处理
 * Author: Kuzan
 * Date: 2017/11/1 11:48.
 */
public class ResponseChannelHandler extends ChannelHandlerSuper {
    private ResponseListener mListener;

    private StringBuilder result = new StringBuilder();
    private int bracket = 0;    // 左括号为1，右括号为-1，当为0时，json结束
    private String indexString; // 单个字符串，用于判断左右括号的
    private StringBuilder jsonStr = new StringBuilder();
    private boolean isGetPacketFromData = false;    // 正在解包

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
                //result.append(msg);
                //splitJson(ctx);
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

    /**
     * 拆开返回的，解析出Json字符串，用来解决粘包问题
     */
    private void splitJson(ChannelHandlerContext ctx) throws Exception {
        if (isGetPacketFromData) {
            return;
        }
        isGetPacketFromData = true;
        // 用来接收一条完整的json的
        while (result.length() != 0) {
            indexString = result.substring(0, 1);
            result.delete(0, 1);
            jsonStr.append(indexString);

            if (indexString.equals("{")) {
                bracket += 1;
            } else if (indexString.equals("}")) {
                bracket -= 1;
            }

            if (bracket == 0) {
                // jsonStr就是完整字符串
                JsonElement element = new JsonParser().parse(String.valueOf(jsonStr));
                Timber.e("jsonStr：" + jsonStr);
                ObservableNotify(ctx, element);
                jsonStr.delete(0, jsonStr.length());
            }
        }

        if (jsonStr.length() != 0){
            result.append(jsonStr);
        }
        isGetPacketFromData = false;
    }
}
