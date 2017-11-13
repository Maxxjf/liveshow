package com.qcloud.liveshow.netty;

import android.content.Context;

import com.qcloud.liveshow.netty.handler.ClientImpl;
import com.qcloud.liveshow.netty.handler.ClientNetWorkIml;
import com.qcloud.liveshow.netty.handler.NettyDispose;
import com.qcloud.liveshow.netty.handler.ResponseHandler;

/**
 * 类说明：Netty工具
 * Author: Kuzan
 * Date: 2017/11/1 12:05.
 */
public class NettyClientBus {
    // 标志是否在运行
    public static boolean isRun = false;

    /**
     * 初始化
     * */
    public static void Initialization(Context context, String host, int port) {
        isRun = true;
        ClientImpl.newInstances()
                .bind(host, port)
                .addResponseListener(new ResponseHandler())     // 响应数据监听
                .Initialization();

        ClientNetWorkIml.newInstance().startListener(context);
    }

    /**
     * 请求数据
     *
     * @param messageSuper 发送的内容
     * */
    public static void request(String messageSuper) {
        ClientImpl.newInstances().request(messageSuper);
    }

    /**
     * 回收数据
     * */
    public static void recycle() {
        ClientNetWorkIml.newInstance().onDestroy();
        ClientImpl.newInstances().onDestroy();
        NettyDispose.onDestroy();
        isRun = false;
    }
}
