package com.qcloud.liveshow.widget.customview;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.LaunchViewPagerAdapter;
import com.qcloud.qclib.base.BaseLinearLayout;
import com.qcloud.qclib.widget.indicator.FixedIndicatorView;
import com.qcloud.qclib.widget.indicator.IndicatorViewPager;
import com.qcloud.qclib.widget.indicator.transition.OnTransitionImageListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 类说明：启动轮播图
 * Author: iceberg
 * Date: 2017/11/16 11:20.
 */
public class LaunchLayout extends BaseLinearLayout {

    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.view_page_indicator)
    FixedIndicatorView mIndicator;


    private IndicatorViewPager mIndicatorViewPager;
    private LaunchViewPagerAdapter mAdapter;

    public LaunchLayout(Context context) {
        this(context, null);
    }

    public LaunchLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LaunchLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_launch;
    }

    @Override
    protected void initViewAndData() {

    }

    public void initIndicator() {
        int selectColor = ContextCompat.getColor(mContext, R.color.colorTitle);
        int unSelectColor = ContextCompat.getColor(mContext, R.color.colorGray);
        mIndicator.setOnTransitionListener(new OnTransitionImageListener()
                .setColor(selectColor, unSelectColor));

        // 设置viewpager保留界面不重新加载的页面数量
        mViewPager.setOffscreenPageLimit(6);

        mIndicatorViewPager = new IndicatorViewPager(mIndicator, mViewPager);
        mAdapter = new LaunchViewPagerAdapter(mContext);
        mIndicatorViewPager.setAdapter(mAdapter);

        mAdapter.replaceList(initData());
    }


    private List<Integer> initData() {
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.launch1);
//        list.add(R.mipmap.launch2);
//        list.add(R.mipmap.launch3);
        list.add(R.mipmap.launch4);
        list.add(R.mipmap.launch6);
//        list.add(R.mipmap.launch7);
//        list.add(R.mipmap.launch8);
        list.add(R.mipmap.launch5);
        return list;
    }

    public void onDestroy() {

    }

}
