package com.qcloud.liveshow.ui.main.widget;

import android.content.Context;
import android.content.Intent;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.ui.main.presenter.impl.LaunchFirstPresenterImpl;
import com.qcloud.liveshow.ui.main.view.ILaunchFirstView;
import com.qcloud.liveshow.widget.customview.LaunchLayout;

import butterknife.Bind;

/**
 * 类说明：启动页
 * Author: Kuzan
 * Date: 2017/11/23 9:41.
 */
public class LaunchFirstActivity extends BaseActivity<ILaunchFirstView, LaunchFirstPresenterImpl> implements ILaunchFirstView {
    @Bind(R.id.layout_launch)
    LaunchLayout mLayoutLaunch;
    @Override
    protected int initLayout() {
        return R.layout.activity_launch_first;
    }

    @Override
    protected LaunchFirstPresenterImpl initPresenter() {
        return new LaunchFirstPresenterImpl();
    }

    @Override
    protected boolean isFullscreen() {
        return true;
    }

    @Override
    protected void initViewAndData() {
        initLaunchLayout();
    }



    /**
     * 初始化引导布局
     * */
    private void initLaunchLayout() {
        mLayoutLaunch.initIndicator();
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, LaunchFirstActivity.class));
    }
}
