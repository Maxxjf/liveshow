package com.qcloud.liveshow.ui.home.view;

import com.qcloud.liveshow.beans.BannerBean;
import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.qclib.base.BaseView;

import java.util.List;

/**
 * 类说明：热门
 * Author: Kuzan
 * Date: 2017/8/11 11:25.
 */
public interface IHotView extends BaseView {
    /**轮播图*/
    void replaceBanner(List<BannerBean> list);

    /**热门直播间*/
    void replaceList(List<RoomBean> beans, boolean isNext);

    /**热门直播间*/
    void addListAtEnd(List<RoomBean> beans, boolean isNext);

    /**显示空布局*/
    void showEmptyView();

    /**隐藏空布局*/
    void hideEmptyView();
}
