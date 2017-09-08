package com.qcloud.liveshow.ui.mine.presenter.impl;

import com.qcloud.liveshow.beans.LevelViewPageBean;
import com.qcloud.liveshow.enums.StartLevelEnum;
import com.qcloud.liveshow.ui.mine.presenter.IMyLevelPresenter;
import com.qcloud.liveshow.ui.mine.view.IMyLevelView;
import com.qcloud.qclib.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：我的等级
 * Author: Kuzan
 * Date: 2017/9/2 17:38.
 */
public class MyLevelPresenterImpl extends BasePresenter<IMyLevelView> implements IMyLevelPresenter {

    public MyLevelPresenterImpl() {

    }

    @Override
    public void createViewPager() {
        List<LevelViewPageBean> beans = new ArrayList<>();
        LevelViewPageBean bean = new LevelViewPageBean();
        bean.setEnum(StartLevelEnum.StartUser);
        bean.setBadgeNum(0);
        beans.add(bean);

        bean = new LevelViewPageBean();
        bean.setEnum(StartLevelEnum.StartAnchor);
        bean.setBadgeNum(0);
        beans.add(bean);

        mView.replaceList(beans);
    }
}
