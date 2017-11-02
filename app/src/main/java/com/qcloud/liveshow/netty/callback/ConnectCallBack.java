package com.qcloud.liveshow.netty.callback;

/**
 * 类说明：连接回调
 * Author: Kuzan
 * Date: 2017/11/1 11:40.
 */
public interface ConnectCallBack {
    /**连接状态*/
    void onConnectChange(boolean isConnect);
}
