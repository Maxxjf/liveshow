package com.qcloud.liveshow.ui.mine.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.ui.mine.presenter.IUserInfoPresenter;
import com.qcloud.liveshow.ui.mine.view.IUserInfoView;
import com.qcloud.qclib.base.BasePresenter;

/**
 * 类说明：个人信息
 * Author: Kuzan
 * Date: 2017/8/15 9:26.
 */
public class UserInfoPresenterImpl extends BasePresenter<IUserInfoView> implements IUserInfoPresenter {

    public UserInfoPresenterImpl() {

    }

    @Override
    public void onBtnClick(int viewId) {
        switch (viewId) {
            case R.id.btn_edit_info:
                mView.onEditClick();
                break;
            case R.id.layout_follow:
                mView.onFollowClick();
                break;
            case R.id.layout_fans:
                mView.onFansClick();
                break;
        }
    }
}
