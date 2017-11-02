package com.qcloud.liveshow.beans;

import com.qcloud.qclib.utils.DateUtils;

/**
 * 类说明：接收私聊数据
 * Author: Kuzan
 * Date: 2017/11/2 9:47.
 */
public class NettyReceiveSingleBean {
    String from_user_id;        // 发送者
    long date_time;
    NettyContentBean content;   // 发送文本消息内容

    public String getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(String from_user_id) {
        this.from_user_id = from_user_id;
    }

    public long getDate_time() {
        return date_time;
    }

    public String getDate_time_str() {
        if (date_time > 0) {
            return DateUtils.getCurrTime("yy-MM-dd HH:mm:ss");
        } else {
            return DateUtils.longToString(date_time, "yy-MM-dd HH:mm:ss");
        }
    }

    public void setDate_time(long date_time) {
        this.date_time = date_time;
    }

    public NettyContentBean getContent() {
        return content;
    }

    public void setContent(NettyContentBean content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NettyReceiveSingleBean{" +
                "from_user_id='" + from_user_id + '\'' +
                ", date_time=" + date_time +
                ", content=" + content +
                '}';
    }
}
