package com.qcloud.liveshow.ui.home.widget;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.HomeViewPagerAdapter;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.beans.HomeViewPageBean;
import com.qcloud.liveshow.ui.home.presenter.impl.HomePresenterImpl;
import com.qcloud.liveshow.ui.home.view.IHomeView;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.DensityUtils;
import com.qcloud.qclib.widget.indicator.FixedIndicatorView;
import com.qcloud.qclib.widget.indicator.IndicatorViewPager;
import com.qcloud.qclib.widget.indicator.slidebar.ColorBar;
import com.qcloud.qclib.widget.indicator.transition.OnTransitionTextListener;

import java.util.List;

/**
 * 类说明：首页
 * Author: Kuzan
 * Date: 2017/8/10 9:55.
 */
public class HomeFragment extends BaseFragment<IHomeView, HomePresenterImpl> implements IHomeView {

    private TitleBar mTitleBar;
    private FixedIndicatorView mIndicator;
    private ViewPager mViewPager;

    private IndicatorViewPager mIndicatorViewPager;
    private HomeViewPagerAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected HomePresenterImpl initPresenter() {
        return new HomePresenterImpl();
    }

    @Override
    protected void initViewAndData() {
        mTitleBar = (TitleBar) mView.findViewById(R.id.title_bar);
        mIndicator = (FixedIndicatorView) mView.findViewById(R.id.view_page_indicator);
        mViewPager = (ViewPager) mView.findViewById(R.id.view_pager);

        initTitleBar();
        initIndicator();
    }

    @Override
    protected void beginLoad() {

    }

    private void initTitleBar() {
        mTitleBar.setOnBtnListener(new TitleBar.OnBtnListener() {
            @Override
            public void onBtnClick(View view) {
                switch (view.getId()) {
                    case R.id.ib_left:
                        ToastUtils.ToastMessage(getActivity(), "搜索");
                        break;
                    case R.id.ib_right:
                        ToastUtils.ToastMessage(getActivity(), "消息");
                        break;
                }
            }
        });
    }

    /**
     * 初始化指示器
     * */
    private void initIndicator() {
        ColorBar bar = new ColorBar(getActivity(), ContextCompat.getColor(getContext(), R.color.colorTitle), 5);
        bar.setWidth(DensityUtils.dp2px(getActivity(), 70));
        mIndicator.setScrollBar(bar);

        float unSelectSize = 16;
        float selectSize = unSelectSize * 1.2f;

        int selectColor = ContextCompat.getColor(getContext(), R.color.colorTitle);
        int unSelectColor = ContextCompat.getColor(getContext(), R.color.colorSubTitle);
        mIndicator.setOnTransitionListener(new OnTransitionTextListener()
                .setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));

        // 设置viewpager保留界面不重新加载的页面数量
        mViewPager.setOffscreenPageLimit(3);

        mIndicatorViewPager = new IndicatorViewPager(mIndicator, mViewPager);
        mAdapter = new HomeViewPagerAdapter(getActivity(), getChildFragmentManager());
        mIndicatorViewPager.setAdapter(mAdapter);

        mPresenter.createViewPager();
    }

    @Override
    public void replaceList(List<HomeViewPageBean> beans) {
        if (isInFragment && beans != null) {
            mAdapter.replaceList(beans);
        }
    }

    public static HomeFragment newInstance(int type) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("TYPE", type);
        fragment.setArguments(bundle);

        return fragment;
    }
}
