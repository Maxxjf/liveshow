package com.qcloud.liveshow.ui.home.view;

import com.qcloud.liveshow.beans.NettyMemberBean;
import com.qcloud.qclib.base.BaseView;

import java.util.List;

/**
 * 类说明：消息列表
 * Author: Kuzan
 * Date: 2017/8/30 11:20.
 */
public interface IMessageListView extends BaseView {
    /**鉴权成功*/
    void authSuccess(String msg);

    /**获取会话列表成功*/
    void replaceList(List<NettyMemberBean> beans);
}
