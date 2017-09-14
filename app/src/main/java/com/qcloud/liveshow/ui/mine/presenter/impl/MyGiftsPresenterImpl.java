package com.qcloud.liveshow.ui.mine.presenter.impl;

import com.qcloud.liveshow.beans.MyGiftsBean;
import com.qcloud.liveshow.model.IMineModel;
import com.qcloud.liveshow.model.impl.MineModelImpl;
import com.qcloud.liveshow.ui.mine.presenter.IMyGiftsPresenter;
import com.qcloud.liveshow.ui.mine.view.IMyGiftsView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：我的礼物列表
 * Author: Kuzan
 * Date: 2017/8/31 11:16.
 */
public class MyGiftsPresenterImpl extends BasePresenter<IMyGiftsView> implements IMyGiftsPresenter {

    private IMineModel mModel;

    public MyGiftsPresenterImpl() {
        mModel = new MineModelImpl();
    }

    @Override
    public void loadMyGifts(int pageNum, int pageSize) {
        mModel.getGiftPage(pageNum, pageSize, new DataCallback<MyGiftsBean>() {
            @Override
            public void onSuccess(MyGiftsBean bean) {
                if (mView == null) {
                    return;
                }
                if (bean != null) {
                    if (bean.getGiftList() != null) {
                        mView.replaceMyGiftList(bean.getGiftList());
                    }
                    if (bean.getGiveMemberList() != null) {
                        if (bean.getPageNum() == 1) {
                            mView.replaceList(bean.getGiveMemberList(), bean.isNext());
                        } else {
                            mView.addListAtEnd(bean.getGiveMemberList(), bean.isNext());
                        }
                    }
                    mView.refreshGiftProfit(bean.getSum());
                } else {
                    mView.loadErr(true, "暂无数据");
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
