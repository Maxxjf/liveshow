package com.qcloud.liveshow.beans;

import java.util.List;

/**
 * 类说明：私聊会话列表
 * Author: Kuzan
 * Date: 2017/11/2 10:00.
 */
public class NettyChatListBean {
    List<MemberBean> list;

    public List<MemberBean> getList() {
        return list;
    }

    public void setList(List<MemberBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "NettySingleListBean{" +
                "list=" + list +
                '}';
    }
}
