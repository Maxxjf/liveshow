package com.qcloud.liveshow.beans;

import com.qcloud.qclib.utils.StringUtils;

/**
 * 类说明：聊天内容
 * Author: Kuzan
 * Date: 2017/11/2 9:43.
 */
public class NettyContentBean {
    String text;

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
