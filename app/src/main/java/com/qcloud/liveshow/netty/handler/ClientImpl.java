package com.qcloud.liveshow.netty.handler;

import com.qcloud.liveshow.netty.callback.ConnectCallBack;
import com.qcloud.liveshow.netty.callback.ResponseListener;

/**
 * 类说明：Netty服务器接口
 * Author: Kuzan
 * Date: 2017/11/1 11:41.
 */
public abstract class ClientImpl implements ConnectCallBack {

    /**
     * 初始化
     * */
    public abstract void Initialization();

    /**
     * 添加要发送的数据
     *
     * @param message
     */
    public abstract void request(String message);

    /**
     * 是否Socket在连接中
     */
    public abstract boolean getConnectState();

    /**
     * 重启
     *
     * @param restart true 重启 false 不重启.
     */
    protected abstract void restart(boolean restart);

    /**
     * 关闭
     * */
    protected abstract void close();

    /**
     * 清掉所有对象数据
     */
    public abstract void onDestroy();

    /**
     * 添加响应事件监听，在Initialization前，ResponseChannelHandler后添加
     *
     * @param response
     * @return ClientImpl
     */
    public abstract ClientImpl addResponseListener(ResponseListener response);

    /**
     * 添加响应事件监听，在Initialization前添加
     *
     * @param response
     * @return ClientImpl
     */
    public abstract ClientImpl addResponseListener(ResponseChannelHandler response);

    /**
     * 绑定服务器
     *
     * @param host ip
     * @param port 端口
     */
    public abstract ClientImpl bind(String host, int port);

    /**
     * @return NettyClient对象
     */
    public static ClientImpl newInstances() {
        return NettyClient.newInstances();
    }
}
