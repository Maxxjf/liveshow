package com.qcloud.liveshow.ui.home.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.HotAdapter;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.beans.BannerBean;
import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.liveshow.constant.AppConstants;
import com.qcloud.liveshow.ui.home.presenter.impl.HotPresenterImpl;
import com.qcloud.liveshow.ui.home.view.IHotView;
import com.qcloud.liveshow.ui.main.widget.WebActivity;
import com.qcloud.liveshow.ui.room.widget.RoomActivity;
import com.qcloud.liveshow.widget.customview.NoDataView;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.pullrefresh.PullRefreshUtil;
import com.qcloud.qclib.pullrefresh.PullRefreshView;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.NetUtils;
import com.qcloud.qclib.utils.ScreenUtils;
import com.qcloud.qclib.widget.customview.banner.CustomBanner;
import com.qcloud.qclib.widget.layoutManager.FullyLinearLayoutManager;

import java.util.List;

import butterknife.Bind;
import timber.log.Timber;

/**
 * 类说明：热门
 * Author: Kuzan
 * Date: 2017/8/11 11:33.
 */
public class HotFragment extends BaseFragment<IHotView, HotPresenterImpl> implements IHotView {

    @Bind(R.id.banner)
    CustomBanner mBanner;
    @Bind(R.id.list_hot)
    RecyclerView mListHot;
    @Bind(R.id.empty_view)
    NoDataView mEmptyView;
    @Bind(R.id.refresh_layout)
    PullRefreshView mRefreshLayout;

    private HotAdapter mAdapter;
    private int pageNum = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot;
    }

    @Override
    protected HotPresenterImpl initPresenter() {
        return new HotPresenterImpl();
    }

    @Override
    protected void initViewAndData() {
        initBanner();

        initRefreshLayout();
    }

    @Override
    protected void beginLoad() {
        loadData();
    }

    private void loadData() {
        mPresenter.loadData(pageNum, AppConstants.PAGE_SIZE);
    }

    private void initBanner() {
        int width = ScreenUtils.getScreenWidth(getActivity());
        ViewGroup.LayoutParams lp = mBanner.getLayoutParams();
        lp.height = width * 280 / 690;
        mBanner.setLayoutParams(lp);

        mBanner.setPages(new CustomBanner.ViewCreator<BannerBean>() {
            @Override
            public View createView(Context context, int position) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(0, 0, 0, 0);
                return imageView;
            }

            @Override
            public void UpdateUI(Context context, View view, int position, BannerBean bean) {
                ImageView imageView = (ImageView) view;
                imageView.setImageBitmap(null);
                if (bean != null) {
                    GlideUtil.loadImage(context, imageView, bean.getImg(), R.drawable.bitmap_banner, true, false);
                }
            }
        })
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setIndicatorRes(R.drawable.banner_point_select, R.drawable.banner_point_unselect)
                //设置指示器的方向
                .setIndicatorGravity(CustomBanner.IndicatorGravity.CENTER_HORIZONTAL)
                //设置翻页的效果，不需要翻页效果可用不设
                .startTurning(5000);

        mBanner.setOnPageClickListener((position, o) -> {
            BannerBean bean = (BannerBean) o;
            WebActivity.openActivity(mContext, "热门活动", bean.getHandleUrl());
        });
    }

    private void initRefreshLayout() {
        FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        mListHot.setLayoutManager(layoutManager);
        mListHot.setNestedScrollingEnabled(false);

        PullRefreshUtil.setRefresh(mRefreshLayout, true, true);
        mRefreshLayout.setOnPullDownRefreshListener(() -> {
            pageNum = 1;
            mRefreshLayout.isMore(true);
            loadData();
        });
        mRefreshLayout.setOnPullUpRefreshListener(() -> {
            pageNum++;
            loadData();
        });

        mAdapter = new HotAdapter(getActivity());
        mListHot.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapterView, view, i, l) ->
                RoomActivity.openActivity(getActivity(), i, mAdapter.getList()));
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadData(pageNum, AppConstants.PAGE_SIZE);
    }

    @Override
    public void replaceBanner(List<BannerBean> list) {
        if (isInFragment) {
            if (list != null && !list.isEmpty()) {
                mBanner.setVisibility(View.VISIBLE);
                mBanner.replaceData(list);
            } else {
                mBanner.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void replaceList(List<RoomBean> beans, boolean isNext) {
        if (isInFragment) {
            if (beans != null && beans.size() > 0) {
                if (mAdapter != null) {
                    mAdapter.replaceList(beans);
                }
                if (mRefreshLayout != null) {
                    mRefreshLayout.isMore(isNext);
                }
                hideEmptyView();
            } else {
                showEmptyView();
            }
            if (mRefreshLayout != null) {
                mRefreshLayout.refreshFinish();
            }
        }
    }

    @Override
    public void addListAtEnd(List<RoomBean> beans, boolean isNext) {
        if (isInFragment) {
            if (beans != null && beans.size() > 0) {
                if (mAdapter != null) {
                    mAdapter.addListAtEnd(beans);
                }
                if (mRefreshLayout != null) {
                    mRefreshLayout.isMore(isNext);
                }
            } else {
                ToastUtils.ToastMessage(getActivity(), R.string.toast_no_more_data);
                if (mRefreshLayout != null) {
                    mRefreshLayout.isMore(false);
                }
            }
            if (mRefreshLayout != null) {
                mRefreshLayout.refreshFinish();
            }
        }
    }

    @Override
    public void showEmptyView() {

        if (mListHot != null) {
            mListHot.setVisibility(View.GONE);
        }
        if (mEmptyView != null) {
            if (!NetUtils.isConnected(getActivity())){
                mEmptyView.noNetWork();
            }else {
                mEmptyView.hasNetWork();
            }
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideEmptyView() {
        if (mListHot != null) {
            mListHot.setVisibility(View.VISIBLE);
        }
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {
        if (isInFragment) {
            if (isShow) {
                ToastUtils.ToastMessage(mContext, errMsg);
            } else {
                Timber.e(errMsg);
            }
            if (mRefreshLayout != null) {
                mRefreshLayout.refreshFinish();
            }
        }
    }
}
