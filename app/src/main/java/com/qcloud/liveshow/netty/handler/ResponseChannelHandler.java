package com.qcloud.liveshow.netty.handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.qcloud.liveshow.netty.NettyClientBus;
import com.qcloud.liveshow.netty.callback.ResponseListener;
import com.qcloud.liveshow.utils.JsonUtil;
import com.qcloud.qclib.rxutil.RxScheduler;
import com.qcloud.qclib.rxutil.task.IOTask;
import com.qcloud.qclib.utils.StringUtils;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

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
    /**添加数据队列*/
    private BlockingDeque<String> mMessageDeque = new LinkedBlockingDeque<>();
    private StringBuffer receiveMsg = new StringBuffer();
    private ChannelHandlerContext mContext;

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
            String response = (String) msg;
            if (StringUtils.isEmptyString(response)) {
                break self;
            }
            Timber.i("response :" + response);
            if (mContext == null) {
                mContext = ctx;
            }

            mMessageDeque.put(response);
        }
        super.channelRead(ctx, msg);
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
     * 处理队列数据
     * */
    public void disposeJson() {
        RxScheduler.doOnIOThread((IOTask<Void>) () -> {
            while (NettyClientBus.isRun) {
                try {
                    String response = mMessageDeque.take();
                    Timber.e("response"+response);
                    Timber.e("receiveMsg"+receiveMsg);
                    if (JsonUtil.isGoodJson(response)) {
                        // 取到一个完整的json，则之前取到的不完整json先解析
                        String receiveMsgStr = String.valueOf(receiveMsg);
                        if (receiveMsgStr.length() > 0) {
                            splitJson(receiveMsgStr);
                            receiveMsg = new StringBuffer();
                        }
                        JsonElement element = new JsonParser().parse(response);
                        ObservableNotify(mContext, element);
                    } else if (mMessageDeque.isEmpty()) {
                        // 最后一条
                        if (JsonUtil.isGoodJson(response)) {
                            JsonElement element = new JsonParser().parse(response);
                            ObservableNotify(mContext, element);
                        } else {
                            receiveMsg.append(response);

                            String receiveMsgStr = String.valueOf(receiveMsg);
                            if (receiveMsgStr.length() > 0) {
                                splitJson(receiveMsgStr);
                                receiveMsg = new StringBuffer();
                            }
                        }
                    } else {
                        // 粘包/拆包数据
                        receiveMsg.append(response);
                    }
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        });
    }

    /**
     * 解析粘包，解析出Json字符串
     */
    private void splitJson(String response) throws Exception {
        if (StringUtils.isEmptyString(response)){
            return;
        }
        Timber.e("解析粘包/拆包");
        synchronized (this) {
            int bracket = 0;
            String indexStr; //单个字符串
            StringBuilder jsonObjStr = new StringBuilder();
            for (int i = 0; i < response.length(); i++) {
                indexStr = response.substring(i, i+1);
                jsonObjStr.append(indexStr);
                if (indexStr.equals("{")) {
                    bracket += 1;
                } else if (indexStr.equals("}")) {
                    bracket -= 1;
                }
                if (bracket == 0) {
                    // 当满足条件时，是一个完整的json字符串
                    String jsonStr = String.valueOf(jsonObjStr);
                    Timber.e("jsonStr：" + jsonStr);
                    if (JsonUtil.isGoodJson(jsonStr)) {
                        JsonElement element = new JsonParser().parse(jsonStr);
                        ObservableNotify(mContext, element);
                    }
                    jsonObjStr = new StringBuilder();
                }
            }
        }
    }
}
