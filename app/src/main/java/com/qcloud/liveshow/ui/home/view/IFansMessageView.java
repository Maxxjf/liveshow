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
    void replaceList(List<NettyReceivePrivateBean> beans);

    void showEmptyView(String tip);

    void hideEmptyView();
}
