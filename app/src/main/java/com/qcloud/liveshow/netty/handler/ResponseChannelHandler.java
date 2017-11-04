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
                Timber.i("socket " + response);
                if (TextUtils.isEmpty(response))
                    break self;
                result.append(msg);
                splitJson(ctx);
                //JsonElement element = new JsonParser().parse(response);
//                if (ObservableNotify(ctx, element)) return;
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
        super.channelRead(ctx, msg);
        Timber.i("socket 消息向下传递:");
    }

    StringBuilder result=new StringBuilder();
    int  bracket=0;//左括号为1，右括号为-1，当为0时，json结束
    String indexString;//单个字符串，用于判断左右括号的
    StringBuilder jsonStr=new StringBuilder();
    boolean isGetPacketFromData=false;//正在解包
    /**
     * 拆开返回的，解析出Json字符串
     */
    private void splitJson(ChannelHandlerContext ctx) throws Exception {
        if (isGetPacketFromData){
            return;
        }
        isGetPacketFromData=true;
        StringBuilder stringBuilderReader=new StringBuilder(result);//解析的长度
        //用来接收一条完整的json的
        for (int i=0;i<stringBuilderReader.length();i++){
            indexString=stringBuilderReader.substring(i,i+1);
            result.delete(0,1);
            jsonStr.append(indexString);
            if (indexString.equals("{")){
                bracket+=1;
            }else if (indexString.equals("}")){
                bracket-=1;
            }
            if (bracket==0){
//               //TODO 开始干大事  jsonStr就是完整字符串
                JsonElement element = new JsonParser().parse(String.valueOf(jsonStr));
                Timber.e("jsonStr："+jsonStr);
                ObservableNotify(ctx, element);
                jsonStr.delete(0,jsonStr.length());
            }
        }
        if (jsonStr.length()!=0){
            result.append(jsonStr);
        }
        isGetPacketFromData=false;
    }

    protected boolean ObservableNotify(ChannelHandlerContext ctx, JsonElement msg) throws Exception {
        Timber.i("notify msg:" + msg);
        return mListener.channelRead(ctx, msg);
    }

    private class privateResponseListener implements ResponseListener {
        @Override
        public boolean channelRead(ChannelHandlerContext ctx, JsonElement msg) throws Exception {
            return false;
        }
    }
}
