package com.qcloud.liveshow.ui.home.view;

import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.qclib.base.BaseView;

import java.util.List;

/**
 * 类说明：搜索主播
 * Author: Kuzan
 * Date: 2017/8/30 10:29.
 */
public interface ISearchAnchorView extends BaseView {
    /**刷新列表*/
    void replaceList(List<RoomBean> beans, boolean isNext);

    /**添加数据到列表*/
    void addListAtEnd(List<RoomBean> beans, boolean isNext);

    /**显示空布局*/
    void showEmptyView();

    /**隐藏空布局*/
    void hideEmptyView();
}
