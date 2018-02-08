package com.qcloud.liveshow.ui.profit.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.ProfitRecordAdapter;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.beans.ProfitRecordBean;
import com.qcloud.liveshow.constant.AppConstants;
import com.qcloud.liveshow.ui.profit.presenter.impl.ProfitRecordPresenterImpl;
import com.qcloud.liveshow.ui.profit.view.IProfitRecordView;
import com.qcloud.liveshow.widget.customview.EmptyView;
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
 * 类说明：收益明细
 * Author: Kuzan
 * Date: 2017/8/18 16:06.
 */
public class ProfitRecordActivity extends SwipeBaseActivity<IProfitRecordView, ProfitRecordPresenterImpl> implements IProfitRecordView {

    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.list_profit_record)
    PullRefreshRecyclerView mListProfitRecord;

    private ProfitRecordAdapter mAdapter;

    private int pageNum = 1;
    private NoRecordView mEmptyView;

    @Override
    protected int initLayout() {
        return R.layout.activity_profit_record;
    }

    @Override
    protected ProfitRecordPresenterImpl initPresenter() {
        return new ProfitRecordPresenterImpl();
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
        initRefreshLayout();

        loadData();
        startLoadingDialog();
    }

    private void loadData() {
        mPresenter.getProfitRecord(pageNum, AppConstants.PAGE_SIZE);
    }

    private void initRefreshLayout() {
        PullRefreshUtil.setRefresh(mListProfitRecord, true, true);
        mListProfitRecord.setOnPullDownRefreshListener(new PullRefreshView.OnPullDownRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                mListProfitRecord.isMore(true);
                loadData();
            }
        });

        mListProfitRecord.setOnPullUpRefreshListener(new PullRefreshView.OnPullUpRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum++;
                loadData();
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mListProfitRecord.setLayoutManager(manager);
        mAdapter = new ProfitRecordAdapter(this);
        mListProfitRecord.setAdapter(mAdapter);

        mEmptyView = new NoRecordView(this);
        mListProfitRecord.setEmptyView(mEmptyView, Gravity.CENTER_HORIZONTAL);
        mEmptyView.setOnRefreshListener(new EmptyView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    @Override
    public void replaceList(List<ProfitRecordBean> beans, boolean isNext) {
        if (isRunning) {
            stopLoadingDialog();
            if (mListProfitRecord != null) {
                mListProfitRecord.refreshFinish();
            }
            if (beans != null && beans.size() > 0) {
                if (mAdapter != null) {
                    mAdapter.replaceList(beans);
                }
                if (mListProfitRecord != null) {
                    mListProfitRecord.isMore(isNext);
                }
                hideEmptyView();
            } else {
                showEmptyView("暂无数据");
            }
        }
    }

    @Override
    public void addListAtEnd(List<ProfitRecordBean> beans, boolean isNext) {
        if (isRunning) {
            if (mListProfitRecord != null) {
                mListProfitRecord.refreshFinish();
            }
            if (beans != null && beans.size() > 0) {
                if (mAdapter != null) {
                    mAdapter.addListAtEnd(beans);
                }
                if (mListProfitRecord != null) {
                    mListProfitRecord.isMore(isNext);
                }
            } else {
                ToastUtils.ToastMessage(this, R.string.toast_no_more_data);
                if (mListProfitRecord != null) {
                    mListProfitRecord.isMore(false);
                }
            }
        }
    }

    @Override
    public void showEmptyView(String tip) {
        if (mListProfitRecord != null) {
            if (!NetUtils.isConnected(this)){
                mEmptyView.noNetWork();
            }else {
                mEmptyView.hasNetWork();
            }
            mListProfitRecord.showEmptyView();
        }
    }

    @Override
    public void hideEmptyView() {
        if (mListProfitRecord != null) {
            mListProfitRecord.hideEmptyView();
        }
    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {
        if (isRunning) {
            stopLoadingDialog();
            if (mListProfitRecord != null) {
                mListProfitRecord.refreshFinish();
            }
            if (isShow) {
                ToastUtils.ToastMessage(this, errMsg);
            } else {
                Timber.e(errMsg);
            }
        }
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, ProfitRecordActivity.class));
    }
}
