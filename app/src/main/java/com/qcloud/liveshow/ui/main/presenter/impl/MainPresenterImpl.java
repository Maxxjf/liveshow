package com.qcloud.liveshow.ui.main.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.ApplyStatusBean;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.liveshow.enums.ApplyStatusEnum;
import com.qcloud.liveshow.enums.StartMainEnum;
import com.qcloud.liveshow.model.IAnchorModel;
import com.qcloud.liveshow.model.impl.AnchorModelImpl;
import com.qcloud.liveshow.model.impl.ProfitModelImpl;
import com.qcloud.liveshow.ui.main.presenter.IMainPresenter;
import com.qcloud.liveshow.ui.main.view.IMainView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.beans.RxBusEvent;
import com.qcloud.qclib.callback.DataCallback;

import io.reactivex.functions.Consumer;

/**
 * 类说明：首页
 * Author: Kuzan
 * Date: 2017/8/1 19:17.
 */
public class MainPresenterImpl extends BasePresenter<IMainView> implements IMainPresenter {

    private IAnchorModel mAnchorModel;

    public MainPresenterImpl() {
        mAnchorModel = new AnchorModelImpl();
        initEventBus();
    }

    private void initEventBus() {
        mEventBus.registerSubscriber(this,mEventBus.obtainSubscriber(RxBusEvent.class, new Consumer<RxBusEvent>() {
            @Override
            public void accept(RxBusEvent rxBusEvent) throws Exception {
                switch (rxBusEvent.getType()){
                    case R.id.return_hot_fragment:
                        if (mView!=null){
                            mView.switchFragment(StartMainEnum.StartHome.getKey());
                        }
                        break;
                }
            }
        }));
    }

    @Override
    public void onBtnClick(int viewId) {
        switch (viewId) {
            case R.id.btn_live_show:
                mView.onLiveShowClick();
                break;
        }
    }

    /**
     * 获取主播申请状态
     * */
    @Override
    public void getApplyStatus() {
        mAnchorModel.getApplyStatus(new DataCallback<ApplyStatusBean>() {
            @Override
            public void onSuccess(ApplyStatusBean bean) {
                if (mView == null) {
                    return;
                }
                if (bean != null) {
                    ApplyStatusEnum statusEnum = ApplyStatusEnum.valueOf(bean.getState());
                    switch (statusEnum) {
                        case Pending:
                            mView.showPending();
                            break;
                        case Agree:
                            mView.showAgree();
                            break;
                        case Disagree:
                            mView.showDisagree();
                            break;
                        case NotApply:
                            mView.showNotApply();
                            break;
                        case Disable:
                            mView.showDisable();
                            break;
                    }
                } else {
                    mView.loadErr(true, "获取主播申请状态失败，请重试");
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

    /**
     * 绑定分佣关系
     * */
    @Override
    public void submitBinding(String code) {
        new ProfitModelImpl().submitBinding(code, new DataCallback<ReturnEmptyBean>() {
            @Override
            public void onSuccess(ReturnEmptyBean returnEmptyBean) {
                if (mView != null) {
                    mView.bindingSuccess();
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
