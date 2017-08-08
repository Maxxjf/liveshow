package com.qcloud.liveshow.ui.main.widget;

import android.content.Context;
import android.content.Intent;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.ui.main.presenter.impl.MainPresenterImpl;
import com.qcloud.liveshow.ui.main.view.IMainView;

public class MainActivity extends BaseActivity<IMainView, MainPresenterImpl> implements IMainView {

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenterImpl initPresenter() {
        return new MainPresenterImpl();
    }

    @Override
    protected void initViewAndData() {

    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }
}
