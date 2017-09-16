package com.qcloud.liveshow.ui.mine.presenter.impl;

import com.qcloud.liveshow.beans.ProblemBean;
import com.qcloud.liveshow.model.IUserModel;
import com.qcloud.liveshow.model.impl.UserModelImpl;
import com.qcloud.liveshow.ui.mine.presenter.IProblemPresenter;
import com.qcloud.liveshow.ui.mine.view.IProblemView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.beans.ReturnDataBean;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：常见问题
 * Author: Kuzan
 * Date: 2017/9/9 17:58.
 */
public class ProblemPresenterImpl extends BasePresenter<IProblemView> implements IProblemPresenter {

    private IUserModel mModel;

    public ProblemPresenterImpl() {
        mModel = new UserModelImpl();
    }

    @Override
    public void loadData(int pageNum, int pageSize) {
        mModel.getProblemList(pageNum, pageSize, new DataCallback<ReturnDataBean<ProblemBean>>() {
            @Override
            public void onSuccess(ReturnDataBean<ProblemBean> bean) {
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
                    mView.loadErr(true, errMsg);
                }
            }
        });
    }
}
