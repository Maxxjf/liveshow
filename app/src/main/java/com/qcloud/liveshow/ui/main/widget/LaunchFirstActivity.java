package com.qcloud.liveshow.ui.main.widget;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.ui.main.presenter.impl.LaunchFirstPresenterImpl;
import com.qcloud.liveshow.ui.main.view.ILaunchFirstView;
import com.qcloud.liveshow.widget.customview.LaunchLayout;
import com.qcloud.qclib.utils.ConstantUtil;

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
        checkFirstLaunch();
        initEmojiLayout();
    }

    /**
     * 第一次启动APP就加载引导图
     */
    private void checkFirstLaunch() {
        if (ConstantUtil.getBoolean("LaunchBefore")){//检查是否启动过，在引导图最后一张图启动setTrue
            LaunchActivity.openActivity(mContext);
        }
    }

    /**
     * 初始化表情布局
     * */
    private void initEmojiLayout() {
        mLayoutLaunch.initIndicator();
    }
}
