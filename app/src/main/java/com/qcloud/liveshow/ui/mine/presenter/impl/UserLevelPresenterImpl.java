package com.qcloud.liveshow.ui.mine.presenter.impl;

import com.qcloud.liveshow.beans.MemberGradeBean;
import com.qcloud.liveshow.model.IMineModel;
import com.qcloud.liveshow.model.impl.MineModelImpl;
import com.qcloud.liveshow.ui.mine.presenter.IUserLevelPresenter;
import com.qcloud.liveshow.ui.mine.view.IUserLevelView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：用户等级
 * Author: Kuzan
 * Date: 2017/9/2 17:55.
 */
public class UserLevelPresenterImpl extends BasePresenter<IUserLevelView> implements IUserLevelPresenter {

    private IMineModel mModel;

    public UserLevelPresenterImpl() {
        mModel = new MineModelImpl();
    }

    @Override
    public void getMemberGrade() {
        mModel.getMemberGrade(new DataCallback<MemberGradeBean>() {
            @Override
            public void onSuccess(MemberGradeBean bean) {
                if (mView == null) {
                    return;
                }
                if (bean != null) {
                    mView.refreshData(bean);
                } else {
                    mView.loadErr(true, "获取会员等级失败");
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
