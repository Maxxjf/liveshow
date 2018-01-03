package com.qcloud.liveshow.ui.home.presenter.impl;

import com.qcloud.liveshow.beans.HomeViewPageBean;
import com.qcloud.liveshow.enums.StartHomeEnum;
import com.qcloud.liveshow.ui.home.presenter.IHomePresenter;
import com.qcloud.liveshow.ui.home.view.IHomeView;
import com.qcloud.qclib.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：首页有关数据处理
 * Author: Kuzan
 * Date: 2017/8/10 9:54.
 */
public class HomePresenterImpl extends BasePresenter<IHomeView> implements IHomePresenter {
    public HomePresenterImpl() {
    }

    @Override
    public void createViewPager() {
        List<HomeViewPageBean> beans = new ArrayList<>();
        HomeViewPageBean bean = new HomeViewPageBean();
        bean.setEnum(StartHomeEnum.StartHot);
        bean.setBadgeNum(0);
        beans.add(bean);

        bean = new HomeViewPageBean();
        bean.setEnum(StartHomeEnum.StartNewest);
        bean.setBadgeNum(0);
        beans.add(bean);

        bean = new HomeViewPageBean();
        bean.setEnum(StartHomeEnum.StartFollow);
        bean.setBadgeNum(0);
        beans.add(bean);

        mView.replaceList(beans);
    }
}
