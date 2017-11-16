package com.qcloud.liveshow.beans;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 类说明：接收私聊数据
 * Author: Kuzan
 * Date: 2017/11/2 9:47.
 */
public class NettyReceivePrivateBean extends RealmObject {
    @PrimaryKey
    String chat_id;             //聊天id唯一码
    String from_user_id;        // 发送者id
    NettyContentBean content;   // 发送文本消息内容
    boolean isSend=false;             // false为读的，true为自己发的

    public String getFrom_user_id() {
        return from_user_id;
    }
    public String getFrom_user_idStr() {
        return String.valueOf(from_user_id);
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

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    @Override
    public String toString() {
        return "NettyReceivePrivateBean{" +
                "chat_id='" + chat_id + '\'' +
                ", from_user_id='" + from_user_id + '\'' +
                ", content=" + content +
                ", isSend=" + isSend +
                '}';
    }
}
