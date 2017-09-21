package com.qcloud.liveshow.ui.home.presenter;

/**
 * 类说明：搜索主播
 * Author: Kuzan
 * Date: 2017/8/30 10:31.
 */
public interface ISearchAnchorPresenter {
    /**搜索*/
    void getSearchList(String keyword, int pageNum, int pageSize);
}
