package com.qcloud.liveshow.ui.mine.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.GiftMemberAdapter;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.beans.GiftBean;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.constant.AppConstants;
import com.qcloud.liveshow.ui.mine.presenter.impl.MyGiftsPresenterImpl;
import com.qcloud.liveshow.ui.mine.view.IMyGiftsView;
import com.qcloud.liveshow.widget.customview.MyGiftPagerLayout;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.pullrefresh.PullRefreshUtil;
import com.qcloud.qclib.pullrefresh.PullRefreshView;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.widget.layoutManager.FullyLinearLayoutManager;

import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import timber.log.Timber;

/**
 * 类说明：我的礼物列表
 * Author: Kuzan
 * Date: 2017/8/31 11:17.
 */
public class MyGiftsActivity extends SwipeBaseActivity<IMyGiftsView, MyGiftsPresenterImpl> implements IMyGiftsView {

    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.page_gift)
    MyGiftPagerLayout mPageGift;
    @Bind(R.id.img_gift_profit_title)
    ImageView mImgGiftProfitTitle;
    @Bind(R.id.tv_gift_profit)
    TextView mTvGiftProfit;
    @Bind(R.id.list_gift)
    RecyclerView mListGift;
    @Bind(R.id.refresh_view)
    PullRefreshView mRefreshView;

    @BindString(R.string.money_str)
    String moneyStr;

    private GiftMemberAdapter mAdapter;

    private int pageNum = 1;

    @Override
    protected int initLayout() {
        return R.layout.activity_my_gifts;
    }

    @Override
    protected MyGiftsPresenterImpl initPresenter() {
        return new MyGiftsPresenterImpl();
    }

    @Override
    protected int setStatusBarColor() {
        return ContextCompat.getColor(this, R.color.black);
    }

    @Override
    protected void initViewAndData() {
        initGiftPager();
        initRefreshLayout();

        startLoadingDialog();
        loadData();
    }

    private void loadData() {
        mPresenter.loadMyGifts(pageNum, AppConstants.PAGE_SIZE);
    }

    private void initGiftPager() {
        mPageGift.setCountNum(4, 2);
    }

    private void initRefreshLayout() {
        PullRefreshUtil.setRefresh(mRefreshView, true, true);
        mRefreshView.setOnPullDownRefreshListener(new PullRefreshView.OnPullDownRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                mRefreshView.isMore(true);
                loadData();
            }
        });
        mRefreshView.setOnPullUpRefreshListener(new PullRefreshView.OnPullUpRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum++;
                loadData();
            }
        });
        mListGift.setLayoutManager(new FullyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mListGift.setNestedScrollingEnabled(false);

        mAdapter = new GiftMemberAdapter(this);
        mListGift.setAdapter(mAdapter);
    }

    @Override
    public void replaceMyGiftList(List<GiftBean> beans) {
        if (isRunning) {
            if (beans != null && beans.size() > 0) {
                mPageGift.setData(beans);
            }
        }
    }

    @Override
    public void replaceList(List<MemberBean> beans, boolean isNext) {
        if (isRunning) {
            stopLoadingDialog();
            if (beans != null && mAdapter != null) {
                mAdapter.replaceList(beans);
            }
            if (mRefreshView != null) {
                mRefreshView.refreshFinish();
                mRefreshView.isMore(isNext);
            }
        }
    }

    @Override
    public void addListAtEnd(List<MemberBean> beans, boolean isNext) {
        if (isRunning) {
            if (beans != null && mAdapter != null) {
                mAdapter.addListAtEnd(beans);
            }
            if (mRefreshView != null) {
                mRefreshView.refreshFinish();
                mRefreshView.isMore(isNext);
            }
        }
    }

    @Override
    public void refreshGiftProfit(double sum) {
        if (isRunning) {
            if (mTvGiftProfit != null) {
                mTvGiftProfit.setText(String.format(moneyStr, sum));
            }
        }
    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {
        if (isRunning) {
            if (mRefreshView != null) {
                mRefreshView.refreshFinish();
            }
            if (isShow) {
                ToastUtils.ToastMessage(this, errMsg);
            } else {
                Timber.e(errMsg);
            }
        }
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, MyGiftsActivity.class));
    }
}
