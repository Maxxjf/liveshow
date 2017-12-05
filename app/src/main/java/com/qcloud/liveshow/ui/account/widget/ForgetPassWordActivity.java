package com.qcloud.liveshow.ui.account.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.ui.account.presenter.impl.ForgetPassWordPresenterImpl;
import com.qcloud.liveshow.ui.account.view.IForgetPassWordView;

/**
 * 类说明：忘记/重置密码
 * Author: iceberg
 * Date: 2017/12/5.
 */
public class ForgetPassWordActivity extends SwipeBaseActivity<IForgetPassWordView,ForgetPassWordPresenterImpl> implements IForgetPassWordView {
    @Override
    public void loadErr(boolean isShow, String errMsg) {

    }
    @Override
    protected int setStatusBarColor() {
        return ContextCompat.getColor(this, R.color.white);
    }

    @Override
    protected boolean isStatusBarTextDark() {
        return true;
    }
    @Override
    protected int initLayout() {
        return R.layout.activity_forget_passwork;
    }

    @Override
    protected ForgetPassWordPresenterImpl initPresenter() {
        return new ForgetPassWordPresenterImpl();
    }

    @Override
    protected void initViewAndData() {

    }
    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, ForgetPassWordActivity.class));
    }

}
