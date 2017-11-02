package com.qcloud.liveshow.netty;

import android.content.Context;

import com.qcloud.liveshow.netty.handler.ClientImpl;
import com.qcloud.liveshow.netty.handler.ClientNetWorkIml;
import com.qcloud.liveshow.netty.handler.LoginHandler;
import com.qcloud.liveshow.netty.handler.ResponseHandler;
import com.qcloud.qclib.callback.DataCallback;

import java.lang.reflect.Type;

/**
 * 类说明：Netty工具
 * Author: Kuzan
 * Date: 2017/11/1 12:05.
 */
public class NettyClientBus {
    /**
     * 初始化
     * */
    public static void Initialization(Context context, final String unique, String host, int port) {
        ClientImpl.newInstances()
                .bind(host, port)
                .addResponseListener(new LoginHandler(unique))
                //.addResponseListener(new ResponseHandler())
                .Initialization();

        ClientNetWorkIml.newInstance().startListener(context);
    }

    /**
     * 请求数据
     *
     * @param messageSuper 发送的内容
     * @param callback 回调
     * @param type 响应数据的类型，注意是接收的数据，并不是发送给服务器的数据
     * */
    public static <T> void request(String messageSuper, DataCallback<T> callback, Type type) {
        ClientImpl.newInstances().addResponseListener(new ResponseHandler<T>(callback, type));
        ClientImpl.newInstances().request(messageSuper);
    }

    /**
     * 回收数据
     * */
    public static void Recycle() {
        ClientNetWorkIml.newInstance().onDestroy();
        ClientImpl.newInstances().onDestroy();
        ResponseHandler.onDestroy();
    }
}
