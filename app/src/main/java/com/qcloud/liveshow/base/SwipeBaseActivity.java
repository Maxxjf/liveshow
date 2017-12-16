package com.qcloud.liveshow.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.utils.SwipeBackUtils;
import com.qcloud.qclib.widget.swipeback.ISwipeBack;
import com.qcloud.qclib.widget.swipeback.SwipeBackHelper;
import com.qcloud.qclib.widget.swipeback.SwipeBackLayout;

/**
 * 类说明：侧滑返回实现
 * Author: Kuzan
 * Date: 2017/8/17 14:37.
 */
public abstract class SwipeBaseActivity<V, T extends BasePresenter<V>> extends BaseActivity<V, T> implements ISwipeBack {

    private SwipeBackHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackHelper(this);
        mHelper.onActivityCreate();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHelper = null;
    }
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
    }
}
