package com.qcloud.liveshow.ui.profit.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.DiamondsRecordAdapter;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.beans.DiamondsRecordBean;
import com.qcloud.liveshow.constant.AppConstants;
import com.qcloud.liveshow.ui.profit.presenter.impl.DiamondsRecordPresenterImpl;
import com.qcloud.liveshow.ui.profit.view.IDiamondsRecordView;
import com.qcloud.liveshow.widget.customview.NoRecordView;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.pullrefresh.PullRefreshRecyclerView;
import com.qcloud.qclib.pullrefresh.PullRefreshUtil;
import com.qcloud.qclib.pullrefresh.PullRefreshView;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.NetUtils;

import java.util.List;

import butterknife.Bind;
import timber.log.Timber;

/**
 * 类说明：钻石币交易记录
 * Author: Kuzan
 * Date: 2017/9/1 16:28.
 */
public class DiamondsRecordActivity extends SwipeBaseActivity<IDiamondsRecordView, DiamondsRecordPresenterImpl> implements IDiamondsRecordView {

    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.list_record)
    PullRefreshRecyclerView mListRecord;
    @Bind(R.id.no_record)
    NoRecordView mEmptyView;
    private DiamondsRecordAdapter mAdapter;
    private int pageNum = 1;

    @Override
    protected int initLayout() {
        return R.layout.activity_diamonds_record;
    }

    @Override
    protected DiamondsRecordPresenterImpl initPresenter() {
        return new DiamondsRecordPresenterImpl();
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
        initListView();
        mPresenter.loadData(pageNum, AppConstants.PAGE_SIZE);
    }


    private void initListView() {
        PullRefreshUtil.setRefresh(mListRecord, true, true);
        mListRecord.setOnPullDownRefreshListener(new PullRefreshView.OnPullDownRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                mListRecord.isMore(true);
                loadData();
            }
        });
        mListRecord.setOnPullUpRefreshListener(new PullRefreshView.OnPullUpRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum++;
                loadData();
            }
        });

        mListRecord.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DiamondsRecordAdapter(this);
        mListRecord.setAdapter(mAdapter);
    }

    private void loadData() {
        mPresenter.loadData(pageNum, AppConstants.PAGE_SIZE);
    }

    @Override
    public void replaceList(List<DiamondsRecordBean> beans, boolean isNext) {
        if (isRunning && mListRecord != null && mAdapter != null) {
            if (beans != null && beans.size() > 0) {
                mAdapter.replaceList(beans);
                mListRecord.isMore(isNext);
                hideEmptyView();
            } else {
                showEmptyView();
            }
            mListRecord.refreshFinish();
        }
    }

    @Override
    public void addListAtEnd(List<DiamondsRecordBean> beans, boolean isNext) {
        if (isRunning && mListRecord != null && mAdapter != null) {
            if (beans != null && beans.size() > 0) {
                mAdapter.addListAtEnd(beans);
                mListRecord.isMore(isNext);
            } else {
                ToastUtils.ToastMessage(this, R.string.toast_no_more_data);
                mListRecord.isMore(false);
            }
            mListRecord.refreshFinish();
        }
    }

    @Override
    public void showEmptyView() {
        if (mListRecord != null) {
            mListRecord.setVisibility(View.GONE);
        }
        if (mEmptyView != null) {
            if (!NetUtils.isConnected(this)){
                mEmptyView.noNetWork();
            }
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideEmptyView() {
        if (mListRecord != null) {
            mListRecord.setVisibility(View.VISIBLE);
        }
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.GONE);
        }
    }
    @Override
    public void loadErr(boolean isShow, String errMsg) {
        if (isRunning) {
            if (isShow) {
                ToastUtils.ToastMessage(this, errMsg);
            } else {
                Timber.e(errMsg);
            }
        }
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, DiamondsRecordActivity.class));
    }
}
