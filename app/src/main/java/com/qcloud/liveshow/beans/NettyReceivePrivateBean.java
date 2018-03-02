package com.qcloud.liveshow.beans;

import com.qcloud.liveshow.enums.CharStatusEnum;
import com.qcloud.qclib.utils.DateUtils;

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
    /**这接收过来本来是时间戳，但Realm排列不接受Long类型，只能String*/
    String date_time;           //接收时间
    boolean isSend = false;     // false为未读的，true为自己发的
    int sendStatus= CharStatusEnum.INPROGRESS.getKey();       //聊天的发送状态  1为成功，2为失败，3是发送中
    boolean isBlack=false;    //这条消息是否被拉黑


    public boolean isBlack() {
        return isBlack;
    }

    public void setBlack(boolean black) {
        isBlack = black;
    }

    public int getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(int sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getFrom_user_id() {
        return from_user_id;
    }

    public String getDate_time_str() {
            return DateUtils.longToString(Long.parseLong(date_time), "yy-MM-dd HH:mm:ss");
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

    public String getDate_time() {
        return date_time;
    }
    public long getDate_timeLong() {
        return Long.parseLong(date_time);
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    @Override
    public String toString() {
        return "NettyReceivePrivateBean{" +
                "chat_id='" + chat_id + '\'' +
                ", from_user_id='" + from_user_id + '\'' +
                ", content=" + content +
                ", date_time='" + date_time + '\'' +
                ", isSend=" + isSend +
                ", sendStatus=" + sendStatus +
                ", isBlack=" + isBlack +
                '}';
    }
}
