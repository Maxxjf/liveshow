package com.qcloud.liveshow.ui.profit.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.DiamondsRecordAdapter;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.ui.profit.presenter.impl.DiamondsRecordPresenterImpl;
import com.qcloud.liveshow.ui.profit.view.IDiamondsRecordView;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.pullrefresh.PullRefreshRecyclerView;
import com.qcloud.qclib.pullrefresh.PullRefreshUtil;
import com.qcloud.qclib.pullrefresh.PullRefreshView;
import com.qcloud.qclib.toast.ToastUtils;

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

    private DiamondsRecordAdapter mAdapter;

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
    }

    private void initListView() {
        PullRefreshUtil.setRefresh(mListRecord, true, true);
        mListRecord.setOnPullDownRefreshListener(new PullRefreshView.OnPullDownRefreshListener() {
            @Override
            public void onRefresh() {
                mListRecord.refreshFinish();
            }
        });
        mListRecord.setOnPullUpRefreshListener(new PullRefreshView.OnPullUpRefreshListener() {
            @Override
            public void onRefresh() {
                mListRecord.refreshFinish();
            }
        });

        mListRecord.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DiamondsRecordAdapter(this);
        mListRecord.setAdapter(mAdapter);
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
