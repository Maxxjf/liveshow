package com.qcloud.liveshow.beans;

/**
 * 类说明：发送私聊
 * Author: Kuzan
 * Date: 2017/11/2 9:42.
 */
public class NettySendPrivateBean {
    String to_user_id;          // 接收者
    String token;
    NettyContentBean content;   // 发送文本消息内容

    public String getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(String to_user_id) {
        this.to_user_id = to_user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public NettyContentBean getContent() {
        return content;
    }

    public void setContent(NettyContentBean content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NettySendSingleBean{" +
                "to_user_id='" + to_user_id + '\'' +
                ", token='" + token + '\'' +
                ", content=" + content +
                '}';
    }
}
