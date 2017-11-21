package com.qcloud.liveshow.ui.home.widget;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.HomeViewPagerAdapter;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.beans.HomeViewPageBean;
import com.qcloud.liveshow.ui.home.presenter.impl.HomePresenterImpl;
import com.qcloud.liveshow.ui.home.view.IHomeView;
import com.qcloud.liveshow.utils.MessageUtil;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.beans.RxBusEvent;
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
@SuppressLint("WrongViewCast")
public class HomeFragment extends BaseFragment<IHomeView, HomePresenterImpl> implements IHomeView {

    private TitleBar mTitleBar;
    private FixedIndicatorView mIndicator;
    private ViewPager mViewPager;
    private RelativeLayout mLayoutTitle;

    private IndicatorViewPager mIndicatorViewPager;
    private HomeViewPagerAdapter mAdapter;

    /**显示与隐藏TitleBar动画*/
    private ObjectAnimator mAnimator;

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
        mLayoutTitle = (RelativeLayout) mView.findViewById(R.id.layout_title);
        initTitleBar();
        initIndicator();
        checkMessageIsRead();
    }

    @Override
    protected void beginLoad() {
        initRxBusEvent();
    }

    private void initRxBusEvent() {
        mEventBus.registerSubscriber(this, mEventBus.obtainSubscriber(RxBusEvent.class, rxBusEvent -> {
            if (rxBusEvent.getType() == R.id.show_hide_title_bar) {
                boolean isShow = (boolean) rxBusEvent.getObj();
                showOrHideTitle(isShow);
            }else if (rxBusEvent.getType()==R.id.netty_private_chat){
                checkMessageIsRead();
            }else if (rxBusEvent.getType()==R.id.check_no_read_chat){
                checkMessageIsRead();
            }
        }));
    }

    private void checkMessageIsRead(){
        int noReadNumber= MessageUtil.getInstance().getNoReadNumber();//未读消息数量
        if (noReadNumber!=0){
            mTitleBar.setIsRead(false);
        }
    }
    private void initTitleBar() {
        mTitleBar.setOnBtnListener(view -> {
            switch (view.getId()) {
                case R.id.ib_left:
                    SearchAnchorActivity.openActivity(getActivity());
                    break;
                case R.id.ib_right:
                    MessageListActivity.openActivity(getActivity());
                    break;
            }
        });
    }


    /**
     * 初始化指示器
     * */
    private void initIndicator() {
        ColorBar bar = new ColorBar(getActivity(), ContextCompat.getColor(getContext(), R.color.colorTitle), 5);
        bar.setWidth(DensityUtils.dp2px(getActivity(), 60));
        mIndicator.setScrollBar(bar);

        float unSelectSize = 14;
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

    /**
     * 显示或隐藏标题栏
     * */
    private void showOrHideTitle(boolean isShow) {
//        if (mLayoutTitle != null) {
//            if (isShow) {
//                restartTitleAnimator(mLayoutTitle, 0f);
//                if (mLayoutTitle.getVisibility() == View.GONE) {
//                    mLayoutTitle.setVisibility(View.VISIBLE);
//                }
//            } else {
//                restartTitleAnimator(mLayoutTitle, -mLayoutTitle.getHeight());
//                if (mLayoutTitle.getVisibility() == View.VISIBLE) {
//                    mLayoutTitle.setVisibility(View.GONE);
//                }
//            }
//        }
    }

    private void restartTitleAnimator(View target, float value) {
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }

        mAnimator = ObjectAnimator
                .ofFloat(target, View.TRANSLATION_Y, value)
                .setDuration(250);
        mAnimator.start();
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
