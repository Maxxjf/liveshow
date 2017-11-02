package com.qcloud.liveshow.netty.message;

import com.qcloud.liveshow.netty.NettyConstants;

/**
 * 类说明：登录信息
 * Author: Kuzan
 * Date: 2017/11/1 12:07.
 */
public class MessageLogin extends MessageSuper {
    private final String alias;
    private String title;
    private String action; //用户打开app后的动作

    public MessageLogin(String tag) {
        this.alias = tag;
        setType(NettyConstants.TYPE.REQUEST.LOGIN);
    }

    public static MessageLogin create(String tag) {
        return new MessageLogin(tag);
    }
}
