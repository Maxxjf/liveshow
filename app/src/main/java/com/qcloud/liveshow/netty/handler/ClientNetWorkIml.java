package com.qcloud.liveshow.netty.handler;

import android.content.Context;

/**
 * 类说明：网络监听
 * Author: Kuzan
 * Date: 2017/11/1 11:58.
 */
public abstract class ClientNetWorkIml {
    /**
     * recycle
     */
    public abstract void onDestroy();

    /**
     * start listener netWork
     *
     * @param context
     */
    public abstract void startListener(Context context);

    public static ClientNetWorkIml newInstance() {
        return ClientNetWork.newInstance();
    }
}
