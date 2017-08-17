package com.qcloud.liveshow.base;

import android.os.Bundle;
import android.view.View;

import com.qcloud.liveshow.utils.SwipeBackUtils;
import com.qcloud.liveshow.widget.swipeback.SwipeBackActivityBase;
import com.qcloud.liveshow.widget.swipeback.SwipeBackActivityHelper;
import com.qcloud.liveshow.widget.swipeback.SwipeBackLayout;
import com.qcloud.qclib.base.BasePresenter;

/**
 * 类说明：侧滑返回实现
 * Author: Kuzan
 * Date: 2017/8/17 14:37.
 */
public abstract class SwipeBaseActivity<V, T extends BasePresenter<V>> extends BaseActivity<V, T> implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    @Override
    protected int initLayout() {
        return initLayoutBySwipe();
    }

    @Override
    protected T initPresenter() {
        return initPresenterBySwipe();
    }

    @Override
    protected void initViewAndData() {
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();

        initLayoutBySwipe();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        SwipeBackUtils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    /**
     * 模版方法，通过该方法获取该Activity的view的layoutId
     */
    protected abstract int initLayoutBySwipe();

    /**
     * 实例化presenter
     *
     * @return presenter
     */
    protected abstract T initPresenterBySwipe();

    /**
     * 初始化界面和数据
     */
    protected abstract void initViewAndDataBySwipe();
}
