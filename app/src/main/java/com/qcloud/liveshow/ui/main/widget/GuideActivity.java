package com.qcloud.liveshow.ui.main.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.GuideAdapter;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.constant.AppConstants;
import com.qcloud.liveshow.ui.account.widget.LoginActivity;
import com.qcloud.liveshow.ui.main.presenter.impl.GuidePresenterImpl;
import com.qcloud.liveshow.ui.main.view.IGuideView;
import com.qcloud.qclib.utils.ConstantUtil;
import com.qcloud.qclib.utils.SystemBarUtil;
import com.qcloud.qclib.widget.indicator.FixedIndicatorView;
import com.qcloud.qclib.widget.indicator.IndicatorViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 类说明：启动页
 * Author: Kuzan
 * Date: 2017/11/23 9:41.
 */
public class GuideActivity extends BaseActivity<IGuideView, GuidePresenterImpl> implements IGuideView {

    @Bind(R.id.guide_viewPager)
    ViewPager mGuideViewPager;
    @Bind(R.id.guide_indicator)
    FixedIndicatorView mGuideIndicator;
    @Bind(R.id.btn_go)
    TextView mBtnGo;

    @Override
    protected int initLayout() {
        return R.layout.activity_guide;
    }

    @Override
    protected GuidePresenterImpl initPresenter() {
        return new GuidePresenterImpl();
    }

    @Override
    protected void initViewAndData() {
        SystemBarUtil.hideStatusBar(this);
        initGuide();
    }

    private void initGuide() {
        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(mGuideIndicator, mGuideViewPager);

        final List<Integer> list = new ArrayList<>();
        list.add(R.drawable.bg_guide_1);
        list.add(R.drawable.bg_guide_2);
        list.add(R.drawable.bg_guide_3);
        list.add(R.drawable.bg_guide_4);
        GuideAdapter adapter = new GuideAdapter(this, list);

        indicatorViewPager.setAdapter(adapter);
        indicatorViewPager.setOnIndicatorPageChangeListener((preItem, currentItem) -> {
            if (currentItem == list.size() - 1) {
                mBtnGo.setVisibility(View.VISIBLE);
            } else {
                mBtnGo.setVisibility(View.GONE);
            }
        });
    }

    @OnClick({R.id.btn_go})
    public void onViewClicked(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onGoClick() {
        toLogin();
    }

    private void toLogin() {
        ConstantUtil.writeBoolean(AppConstants.IS_NO_FIRST_START, true);
        LoginActivity.openActivity(this);
        finish();
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, GuideActivity.class));
    }
}
