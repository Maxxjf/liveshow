package com.qcloud.liveshow.ui.home.presenter.impl;

import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.liveshow.model.IRoomModel;
import com.qcloud.liveshow.model.impl.RoomModelImpl;
import com.qcloud.liveshow.ui.home.presenter.ISearchAnchorPresenter;
import com.qcloud.liveshow.ui.home.view.ISearchAnchorView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.beans.ReturnDataBean;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：搜索主播
 * Author: Kuzan
 * Date: 2017/8/30 10:31.
 */
public class SearchAnchorPresenterImpl extends BasePresenter<ISearchAnchorView> implements ISearchAnchorPresenter {

    private IRoomModel mModel;

    public SearchAnchorPresenterImpl() {
        mModel = new RoomModelImpl();
    }

    @Override
    public void getSearchList(String keyword, int pageNum, int pageSize) {
        mModel.getSearchRoom(keyword, pageNum, pageSize, new DataCallback<ReturnDataBean<RoomBean>>() {
            @Override
            public void onSuccess(ReturnDataBean<RoomBean> bean) {
                if (mView == null) {
                    return;
                }
                if (bean != null) {
                    if (bean.getPageNum() == 1) {
                        mView.replaceList(bean.getList(), bean.isNext());
                    } else {
                        mView.addListAtEnd(bean.getList(), bean.isNext());
                    }
                } else {
                    mView.loadErr(true, "暂无数据");
                }
            }

            @Override
            public void onError(int status, String errMsg) {
                if (mView != null) {
                    mView.showEmptyView();
                    mView.loadErr(true, errMsg);
                }
            }
        });
    }
}
