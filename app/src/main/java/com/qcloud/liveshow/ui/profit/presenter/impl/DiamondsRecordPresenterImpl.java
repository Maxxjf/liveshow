package com.qcloud.liveshow.ui.profit.presenter.impl;

import com.qcloud.liveshow.beans.DiamondsRecordBean;
import com.qcloud.liveshow.model.IMineModel;
import com.qcloud.liveshow.model.impl.MineModelImpl;
import com.qcloud.liveshow.ui.profit.presenter.IDiamondsRecordPresenter;
import com.qcloud.liveshow.ui.profit.view.IDiamondsRecordView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.beans.ReturnDataBean;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：钻石币交易记录
 * Author: Kuzan
 * Date: 2017/9/1 16:27.
 */
public class DiamondsRecordPresenterImpl extends BasePresenter<IDiamondsRecordView> implements IDiamondsRecordPresenter {
    private IMineModel mModel;
    public DiamondsRecordPresenterImpl() {
        mModel=new MineModelImpl();
    }
    @Override
    public void loadData(int pageNum, int pageSize) {
        mModel.diamondsRecord(pageNum, pageSize, new DataCallback<ReturnDataBean<DiamondsRecordBean>>() {
            @Override
            public void onSuccess(ReturnDataBean<DiamondsRecordBean> bean) {
                if (mView == null) {
                    return;
                }
                if (bean != null) {
                    // 列表
                    if (bean.getPageNum() == 1) {
                        mView.replaceList(bean.getList(), bean.isNext());
                    } else {
                        mView.addListAtEnd(bean.getList(), bean.isNext());
                    }
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
