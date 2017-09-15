package com.qcloud.liveshow.ui.mine.presenter.impl;

import com.qcloud.liveshow.beans.AnchorGradeBean;
import com.qcloud.liveshow.model.IMineModel;
import com.qcloud.liveshow.model.impl.MineModelImpl;
import com.qcloud.liveshow.ui.mine.presenter.IAnchorLevelPresenter;
import com.qcloud.liveshow.ui.mine.view.IAnchorLevelView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：主播等级
 * Author: Kuzan
 * Date: 2017/9/2 17:58.
 */
public class AnchorLevelPresenterImpl extends BasePresenter<IAnchorLevelView> implements IAnchorLevelPresenter {

    private IMineModel mModel;

    public AnchorLevelPresenterImpl() {
        mModel = new MineModelImpl();
    }

    @Override
    public void getAnchorGrade() {
        mModel.getAnchorGrade(new DataCallback<AnchorGradeBean>() {
            @Override
            public void onSuccess(AnchorGradeBean bean) {
                if (mView == null) {
                    return;
                }
                if (bean != null) {
                    mView.refreshData(bean);

                    if (bean.getAnchorGradeList() != null) {
                        mView.replaceLevel(bean.getAnchorGradeList());
                    } else {
                        mView.loadErr(false, "暂无等级列表");
                    }
                } else {
                    mView.loadErr(true, "加载主播等级出错");
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
