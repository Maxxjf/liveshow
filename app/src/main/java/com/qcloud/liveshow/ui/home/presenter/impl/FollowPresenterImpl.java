package com.qcloud.liveshow.ui.home.presenter.impl;

import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.liveshow.model.IRoomModel;
import com.qcloud.liveshow.model.impl.RoomModelImpl;
import com.qcloud.liveshow.ui.home.presenter.IFollowPresenter;
import com.qcloud.liveshow.ui.home.view.IFollowView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.beans.ReturnDataBean;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：关注
 * Author: Kuzan
 * Date: 2017/8/11 11:30.
 */
public class FollowPresenterImpl extends BasePresenter<IFollowView> implements IFollowPresenter {

    private IRoomModel mModel;

    public FollowPresenterImpl() {
        mModel = new RoomModelImpl();
    }

    @Override
    public void getFollowList(int pageNum, int pageSize) {
        mModel.getNewestRoom(pageNum, pageSize, new DataCallback<ReturnDataBean<RoomBean>>() {
            @Override
            public void onSuccess(ReturnDataBean<RoomBean> bean) {
                if (mView == null) {
                    return;
                }
                if (bean != null && bean.getList() != null) {
                    if (bean.getPageNum() <= 1) {
                        mView.replaceList(bean.getList(), bean.isNext());
                    } else {
                        mView.addListAtEnd(bean.getList(), bean.isNext());
                    }
                } else {
                    mView.showEmptyView("暂无数据");
                }
            }

            @Override
            public void onError(int status, String errMsg) {
                if (mView != null) {
                    mView.showEmptyView(errMsg);
                }
            }
        });
    }
}
