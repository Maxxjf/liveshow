package com.qcloud.liveshow.ui.mine.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.LevelViewPagerAdapter;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.beans.LevelViewPageBean;
import com.qcloud.liveshow.ui.mine.presenter.impl.MyLevelPresenterImpl;
import com.qcloud.liveshow.ui.mine.view.IMyLevelView;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.utils.DensityUtils;
import com.qcloud.qclib.widget.indicator.FixedIndicatorView;
import com.qcloud.qclib.widget.indicator.IndicatorViewPager;
import com.qcloud.qclib.widget.indicator.slidebar.ColorBar;
import com.qcloud.qclib.widget.indicator.transition.OnTransitionTextListener;

import java.util.List;

import butterknife.Bind;

/**
 * 类说明：我的等级
 * Author: Kuzan
 * Date: 2017/9/2 17:39.
 */
public class MyLevelActivity extends SwipeBaseActivity<IMyLevelView, MyLevelPresenterImpl> implements IMyLevelView {
    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.view_page_indicator)
    FixedIndicatorView mIndicator;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    private IndicatorViewPager mIndicatorViewPager;
    private LevelViewPagerAdapter mAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_my_level;
    }

    @Override
    protected MyLevelPresenterImpl initPresenter() {
        return new MyLevelPresenterImpl();
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
    protected void initViewAndData() {
        initIndicator();
    }

    /**
     * 初始化指示器
     * */
    private void initIndicator() {
        ColorBar bar = new ColorBar(this, ContextCompat.getColor(this, R.color.colorTitle), 5);
        bar.setWidth(DensityUtils.dp2px(this, 60));
        mIndicator.setScrollBar(bar);

        float unSelectSize = 14;
        float selectSize = unSelectSize * 1.2f;

        int selectColor = ContextCompat.getColor(this, R.color.colorTitle);
        int unSelectColor = ContextCompat.getColor(this, R.color.colorSubTitle);
        mIndicator.setOnTransitionListener(new OnTransitionTextListener()
                .setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));

        // 设置viewpager保留界面不重新加载的页面数量
        mViewPager.setOffscreenPageLimit(3);

        mIndicatorViewPager = new IndicatorViewPager(mIndicator, mViewPager);
        mAdapter = new LevelViewPagerAdapter(this, getSupportFragmentManager());
        mIndicatorViewPager.setAdapter(mAdapter);

        mPresenter.createViewPager();
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, MyLevelActivity.class));
    }

    @Override
    public void replaceList(List<LevelViewPageBean> beans) {
        if (isRunning && beans != null) {
            mAdapter.replaceList(beans);
        }
    }
}
