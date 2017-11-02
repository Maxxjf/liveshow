package com.qcloud.liveshow.ui.room.presenter.impl;

import com.qcloud.liveshow.beans.NettyGroupBean;
import com.qcloud.liveshow.model.IIMModel;
import com.qcloud.liveshow.model.impl.IMModelImpl;
import com.qcloud.liveshow.ui.room.presenter.IRoomPresenter;
import com.qcloud.liveshow.ui.room.view.IRoomView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：直播间
 * Author: Kuzan
 * Date: 2017/8/22 10:54.
 */
public class RoomPresenterImpl extends BasePresenter<IRoomView> implements IRoomPresenter {
    IIMModel imModel;
    public RoomPresenterImpl() {
        imModel=new IMModelImpl();
    }

    @Override
    public void onBtnClick(int viewId) {

    }

    @Override
    public void joinGroup(String roomNumber) {
        imModel.joinGroup(roomNumber, new DataCallback<NettyGroupBean>() {
            @Override
            public void onSuccess(NettyGroupBean nettyGroupBean) {

            }

            @Override
            public void onError(int status, String errMsg) {

            }
        });
    }
}
