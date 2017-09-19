package com.qcloud.liveshow.ui.home.view;

import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.qclib.base.BaseView;

import java.util.List;

/**
 * 类说明：最新
 * Author: Kuzan
 * Date: 2017/8/11 11:26.
 */
public interface INewestView extends BaseView {
    /**替换数据*/
    void replaceList(List<RoomBean> beans, boolean isNext);

    /**添加数据*/
    void addListAtEnd(List<RoomBean> beans, boolean isNext);

    /**显示空布局*/
    void showEmptyView(String tip);

    /**隐藏空布局*/
    void hideEmptyView();
}
