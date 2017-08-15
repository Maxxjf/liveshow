package com.qcloud.liveshow.ui.mine.widget;

import android.content.Context;
import android.content.Intent;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.ui.mine.presenter.impl.UserInfoPresenterImpl;
import com.qcloud.liveshow.ui.mine.view.IUserInfoView;
import com.qcloud.qclib.toast.ToastUtils;

import timber.log.Timber;

/**
 * 类说明：个人信息
 * Author: Kuzan
 * Date: 2017/8/15 9:29.
 */
public class UserInfoActivity extends BaseActivity<IUserInfoView, UserInfoPresenterImpl> implements IUserInfoView {

    @Override
    protected int initLayout() {
        return R.layout.activity_user_info;
    }

    @Override
    protected UserInfoPresenterImpl initPresenter() {
        return new UserInfoPresenterImpl();
    }

    @Override
    protected boolean translucentStatusBar() {
        return true;
    }

    @Override
    protected void initViewAndData() {

    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {
        if (isShow) {
            ToastUtils.ToastMessage(this, errMsg);
        } else {
            Timber.e(errMsg);
        }
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, UserInfoActivity.class));
    }
}
