package com.qcloud.liveshow.beans;

/**
 * 类说明：接收私聊数据
 * Author: Kuzan
 * Date: 2017/11/2 9:47.
 */
public class NettyReceivePrivateBean {
    String from_user_id;        // 发送者
    String chat_id;
    NettyContentBean content;   // 发送文本消息内容

    public String getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(String from_user_id) {
        this.from_user_id = from_user_id;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public NettyContentBean getContent() {
        return content;
    }

    public void setContent(NettyContentBean content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NettyReceivePrivateBean{" +
                "from_user_id='" + from_user_id + '\'' +
                ", chat_id='" + chat_id + '\'' +
                ", content=" + content +
                '}';
    }
}
