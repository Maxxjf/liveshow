package com.qcloud.liveshow.ui.anchor.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.FinishIncomeBean;
import com.qcloud.liveshow.model.IAnchorModel;
import com.qcloud.liveshow.model.IIMModel;
import com.qcloud.liveshow.model.impl.AnchorModelImpl;
import com.qcloud.liveshow.model.impl.IMModelImpl;
import com.qcloud.liveshow.ui.anchor.presenter.IAnchorPresenter;
import com.qcloud.liveshow.ui.anchor.view.IAnchorView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.beans.RxBusEvent;
import com.qcloud.qclib.callback.DataCallback;

import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * 类说明：直播间
 * Author: Kuzan
 * Date: 2017/9/1 17:19.
 */
public class AnchorPresenterImpl extends BasePresenter<IAnchorView> implements IAnchorPresenter {

    private IAnchorModel mModel;
    private IIMModel imModel;
    public AnchorPresenterImpl() {
        mModel = new AnchorModelImpl();
        imModel=new IMModelImpl();
        initRxBusEvent();

    }

    private void initRxBusEvent() {
        mEventBus.registerSubscriber(this, mEventBus.obtainSubscriber(RxBusEvent.class, new Consumer<RxBusEvent>() {
            @Override
            public void accept(RxBusEvent rxBusEvent) throws Exception {
                switch (rxBusEvent.getType()){
                    case R.id.netty_close_room:
                        mView.closeRoom();
                        break;
                }
            }
        }));
    }

    @Override
    public void finishLive(String roomId) {
        mModel.finishLive(roomId,new DataCallback<FinishIncomeBean>() {
            @Override
            public void onSuccess(FinishIncomeBean returnEmptyBean) {
                Timber.e("关闭直播成功");
            }

            @Override
            public void onError(int status, String errMsg) {
                Timber.e(errMsg);
            }
        });
    }
    /**
     * 退出群聊
     *
     * @time 2017/11/8 16:23
     */
    @Override
    public void outGroup(String roomNum) {
        imModel.outGroup(roomNum);
    }
}
