package com.qcloud.liveshow.ui.home.view;

import com.qcloud.liveshow.beans.NettyReceivePrivateBean;
import com.qcloud.qclib.base.BaseView;

import java.util.List;

/**
 * 类说明：粉丝消息
 * Author: Kuzan
 * Date: 2017/9/11 12:01.
 */
public interface IFansMessageView extends BaseView {
    /**点击表情*/
    void onEmojiClick();

    /**点击发送消息*/
    void onSendClick();

    /**添加消息到列表*/
    void addMessage(NettyReceivePrivateBean bean);

    /**显示历史消息*/
    void replaceList(List<NettyReceivePrivateBean> beans);

    /**显示空布局*/
    void showEmptyView(String tip);

    /**隐藏空布局*/
    void hideEmptyView();


    /**更新列表*/
    void upDateApater(String chatId,int isSuccess);
}
