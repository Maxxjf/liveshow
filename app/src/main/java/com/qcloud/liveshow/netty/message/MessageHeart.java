package com.qcloud.liveshow.netty.message;

import com.qcloud.liveshow.netty.NettyConstants;

/**
 * 类说明：心跳
 * Author: Kuzan
 * Date: 2017/11/1 12:11.
 */
public class MessageHeart extends MessageSuper {
    private final String tag;
    private final String ua;

    public MessageHeart(String tag, String ua) {
        this.tag = tag;
        this.ua = ua;
        setType(NettyConstants.TYPE.REQUEST.PING);
    }

    public static MessageHeart create(String tag, String ua) {
        return new MessageHeart(tag, ua);
    }
}
