package com.qcloud.liveshow.ui.mine.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.LevelViewPageBean;
import com.qcloud.liveshow.enums.StartLevelEnum;
import com.qcloud.liveshow.ui.mine.presenter.IMyLevelPresenter;
import com.qcloud.liveshow.ui.mine.view.IMyLevelView;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.beans.RxBusEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * 类说明：我的等级
 * Author: Kuzan
 * Date: 2017/9/2 17:38.
 */
public class MyLevelPresenterImpl extends BasePresenter<IMyLevelView> implements IMyLevelPresenter {

    public MyLevelPresenterImpl() {
        initEventBus();
    }

    private void initEventBus() {
        mEventBus.registerSubscriber(this, mEventBus.obtainSubscriber(RxBusEvent.class, new Consumer<RxBusEvent>() {
            @Override
            public void accept(RxBusEvent rxBusEvent) throws Exception {
                switch (rxBusEvent.getType()) {
                    case R.id.is_not_anchor:
                        if (mView != null) {
                            mView.isNoAnchor();
                        }
                        break;
                }
            }
        }));
    }

    @Override
    public void createViewPager() {
        List<LevelViewPageBean> beans = new ArrayList<>();
        LevelViewPageBean bean = new LevelViewPageBean();
        bean.setEnum(StartLevelEnum.StartUser);
        bean.setBadgeNum(0);
        beans.add(bean);
        if (UserInfoUtil.mUser.isAnchor()) {
            bean = new LevelViewPageBean();
            bean.setEnum(StartLevelEnum.StartAnchor);
            bean.setBadgeNum(0);
            beans.add(bean);
        }
        mView.replaceList(beans);
    }
}
