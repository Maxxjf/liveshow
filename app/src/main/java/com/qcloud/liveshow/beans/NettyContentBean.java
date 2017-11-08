package com.qcloud.liveshow.beans;

import com.qcloud.qclib.utils.DateUtils;
import com.qcloud.qclib.utils.StringUtils;

/**
 * 类说明：聊天内容
 * Author: Kuzan
 * Date: 2017/11/2 9:43.
 */
public class NettyContentBean {
    String text;
    long date_time;

    public NettyContentBean() {

    }

    public NettyContentBean(String text) {
        this.text = text;
    }

    public String getText() {
        return StringUtils.isEmptyString(text)?"":text;
    }

    public void setText(String text) {
        this.text = text;
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

    @Override
    public String toString() {
        return "NettyContentBean{" +
                "text='" + text + '\'' +
                ", date_time=" + date_time +
                '}';
    }
}
