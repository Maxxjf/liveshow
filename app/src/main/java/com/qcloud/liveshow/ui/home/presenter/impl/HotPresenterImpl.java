package com.qcloud.liveshow.ui.home.presenter.impl;

import com.qcloud.liveshow.beans.HotRoomBean;
import com.qcloud.liveshow.model.IRoomModel;
import com.qcloud.liveshow.model.impl.RoomModelImpl;
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
    public void loadData(int pageNum, int pageSize) {
        mModel.getHotRoom(pageNum, pageSize, new DataCallback<HotRoomBean>() {
            @Override
            public void onSuccess(HotRoomBean bean) {
                if (mView == null) {
                    return;
                }
                if (bean != null) {
                    mView.replaceBanner(bean.getImgList());
                    mView.replaceList(bean.getRoomList(), false);
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
}
