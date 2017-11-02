package com.qcloud.liveshow.beans;

import java.util.List;

/**
 * 类说明：私聊会话列表
 * Author: Kuzan
 * Date: 2017/11/2 10:00.
 */
public class NettyChatListBean {
    List<NettyMemberBean> list;

    public List<NettyMemberBean> getList() {
        return list;
    }

    public void setList(List<NettyMemberBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "NettySingleListBean{" +
                "list=" + list +
                '}';
    }
}
