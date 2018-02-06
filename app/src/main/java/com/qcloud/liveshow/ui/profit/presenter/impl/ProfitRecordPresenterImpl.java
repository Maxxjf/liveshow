package com.qcloud.liveshow.ui.profit.presenter.impl;

import com.qcloud.liveshow.beans.ProfitRecordBean;
import com.qcloud.liveshow.model.IProfitModel;
import com.qcloud.liveshow.model.impl.ProfitModelImpl;
import com.qcloud.liveshow.ui.profit.presenter.IProfitRecordPresenter;
import com.qcloud.liveshow.ui.profit.view.IProfitRecordView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.beans.ReturnDataBean;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：收益明细
 * Author: Kuzan
 * Date: 2017/8/18 16:05.
 */
public class ProfitRecordPresenterImpl extends BasePresenter<IProfitRecordView> implements IProfitRecordPresenter {

    private IProfitModel mModel;

    public ProfitRecordPresenterImpl() {
        mModel = new ProfitModelImpl();
    }

    @Override
    public void getProfitRecord(int pageNum, int pageSize) {
        mModel.getProfitRecord(pageNum, pageSize, new DataCallback<ReturnDataBean<ProfitRecordBean>>() {
            @Override
            public void onSuccess(ReturnDataBean<ProfitRecordBean> bean) {
                if (mView == null) {
                    return;
                }
                if (bean != null) {
                    if (bean.getPageNum() == 1) {
                        mView.replaceList(bean.getList(), bean.isNext());
                    } else {
                        mView.addListAtEnd(bean.getList(), bean.isNext());
                    }
                } else {
                    mView.loadErr(true, "暂无数据");
                }
            }

            @Override
            public void onError(int status, String errMsg) {
                if (mView != null) {
                    mView.showEmptyView("哎呀！网络出错");
                    mView.loadErr(true, errMsg);
                }
            }
        });
    }
}
