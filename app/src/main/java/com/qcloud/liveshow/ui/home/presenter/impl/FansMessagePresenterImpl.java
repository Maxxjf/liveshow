package com.qcloud.liveshow.ui.home.presenter.impl;

import com.qcloud.liveshow.beans.NettyReceivePrivateBean;
import com.qcloud.liveshow.realm.RealmHelper;
import com.qcloud.liveshow.ui.home.presenter.IFansMessagePresenter;
import com.qcloud.liveshow.ui.home.view.IFansMessageView;
import com.qcloud.qclib.base.BasePresenter;

import java.util.List;

import timber.log.Timber;

/**
 * 类说明：粉丝消息
 * Author: Kuzan
 * Date: 2017/9/11 12:04.
 */
public class FansMessagePresenterImpl extends BasePresenter<IFansMessageView> implements IFansMessagePresenter {
    RealmHelper realmHelper;
    public FansMessagePresenterImpl() {
        realmHelper=new RealmHelper();
    }

    @Override
    public void getChars(String fromUserId) {
        List<NettyReceivePrivateBean> charList = realmHelper.queryNettyReceivePrivateBeanById(fromUserId);
        Timber.e("charList:"+charList);
        mView.replaceList(charList);
    }
}
