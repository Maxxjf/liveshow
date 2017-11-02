package com.qcloud.liveshow.netty.message;

import java.io.Serializable;

/**
 * 类说明：消息类型基类
 * Author: Kuzan
 * Date: 2017/11/1 11:44.
 */
public class MessageSuper implements Serializable {
    private int type = -1;
    private int code;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public byte[] read() {
        return NettyGson.newInstances().toJson(this).getBytes();
    }

    @Override
    public String toString() {
        return NettyGson.newInstances().toJson(this);
    }

    public MessageSuper write(byte[] bytes) {
        return NettyGson.newInstances().fromJson(new String(bytes), getClass());
    }
}
