package com.qcloud.liveshow.ui.room.presenter.impl;

import com.qcloud.liveshow.model.IIMModel;
import com.qcloud.liveshow.model.impl.IMModelImpl;
import com.qcloud.liveshow.ui.room.presenter.IRoomPresenter;
import com.qcloud.liveshow.ui.room.view.IRoomView;
import com.qcloud.qclib.base.BasePresenter;

/**
 * 类说明：直播间
 * Author: Kuzan
 * Date: 2017/8/22 10:54.
 */
public class RoomPresenterImpl extends BasePresenter<IRoomView> implements IRoomPresenter {
    private IIMModel mIModel;

    public RoomPresenterImpl() {
        mIModel=new IMModelImpl();
    }

    @Override
    public void onBtnClick(int viewId) {

    }

    /**
     * 退出群聊
     *
     * @time 2017/11/8 16:23
     */
    @Override
    public void outGroup(String roomNum) {
        mIModel.outGroup(roomNum);
    }
}
