package com.qcloud.liveshow.ui.home.presenter.impl;

import com.qcloud.liveshow.beans.HotRoomBean;
import com.qcloud.liveshow.beans.RetBean;
import com.qcloud.liveshow.model.IRoomModel;
import com.qcloud.liveshow.model.impl.RoomModelImpl;
import com.qcloud.liveshow.model.impl.TestModelImpl;
import com.qcloud.liveshow.ui.home.presenter.IHotPresenter;
import com.qcloud.liveshow.ui.home.view.IHotView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：热门直播间
 * Author: Kuzan
 * Date: 2017/8/11 11:29.
 */
public class HotPresenterImpl extends BasePresenter<IHotView> implements IHotPresenter {

    private IRoomModel mModel;

    public HotPresenterImpl() {
        mModel = new RoomModelImpl();
    }

    @Override
    public void loadData() {
        mModel.getHotRoom(new DataCallback<HotRoomBean>() {
            @Override
            public void onSuccess(HotRoomBean hotRoomBean) {
                if (mView == null) {
                    return;
                }
                if (hotRoomBean != null) {
                    mView.replaceBanner(hotRoomBean.getImgList());
                    //mView.replaceList(hotRoomBean.getRoomList());
                }
            }

            @Override
            public void onError(int status, String errMsg) {
                if (mView != null) {
                    mView.loadErr(true, errMsg);
                }
            }
        });
    }

    @Override
    public void loadTest() {
        new TestModelImpl().loadData(new DataCallback<RetBean>() {
            @Override
            public void onSuccess(RetBean retBean) {
                if (mView == null) {
                    return;
                }
                if (retBean != null) {
                    mView.replaceList(retBean.getLives());
                }
            }

            @Override
            public void onError(int status, String errMsg) {

            }
        });
    }
}
