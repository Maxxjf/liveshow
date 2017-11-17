package com.qcloud.liveshow.ui.home.view;

import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.qclib.base.BaseView;

import java.util.List;

/**
 * 类说明：消息列表
 * Author: Kuzan
 * Date: 2017/8/30 11:20.
 */
public interface IMessageListView extends BaseView {

    /**获取会话列表成功*/
    void replaceList(List<MemberBean> beans);
    /**添加信息列表*/
    void addMessage(MemberBean bean);

    /**显示空布局*/
    void showEmptyView(String tip);

    /**隐藏空布局*/
    void hideEmptyView();
}
