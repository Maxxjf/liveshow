package com.qcloud.liveshow.netty.message;

import java.util.HashMap;

/**
 * 类说明：消息类型
 * Author: Kuzan
 * Date: 2017/11/1 11:46.
 */
public class MessageResponse extends MessageSuper {
    private HashMap<String, Object> extras;
    private String title;
    private String action; //用户打开app后的动作
    private byte reply;

    public HashMap<String, Object> getExtras() {
        return extras;
    }

    public void setExtras(HashMap<String, Object> extras) {
        this.extras = extras;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public byte getReplay() {
        return reply;
    }

    public void setReplay(byte reply) {
        this.reply = reply;
    }


    @Override
    public MessageResponse write(byte[] bytes) {
        return NettyGson.newInstances().fromJson(new String(bytes), getClass());
    }
}
