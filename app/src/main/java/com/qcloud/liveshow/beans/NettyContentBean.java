package com.qcloud.liveshow.beans;

import com.qcloud.qclib.utils.StringUtils;

import io.realm.RealmObject;

/**
 * 类说明：聊天内容
 * Author: Kuzan
 * Date: 2017/11/2 9:43.
 */
public class NettyContentBean  extends RealmObject {
    String text;

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

    @Override
    public String toString() {
        return "NettyContentBean{" +
                "text='" + text + '\'' +
                '}';
    }
}
