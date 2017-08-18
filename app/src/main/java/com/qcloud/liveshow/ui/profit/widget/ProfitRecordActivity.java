package com.qcloud.liveshow.ui.profit.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.ProfitRecordAdapter;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.ui.profit.presenter.impl.ProfitRecordPresenterImpl;
import com.qcloud.liveshow.ui.profit.view.IProfitRecordView;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.pullrefresh.PullRefreshUtil;
import com.qcloud.qclib.pullrefresh.PullRefreshView;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.widget.layoutManager.FullyLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import timber.log.Timber;

/**
 * 类说明：收益明细
 * Author: Kuzan
 * Date: 2017/8/18 16:06.
 */
public class ProfitRecordActivity extends BaseActivity<IProfitRecordView, ProfitRecordPresenterImpl> implements IProfitRecordView {


    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.tv_profit_account)
    TextView mTvProfitAccount;
    @Bind(R.id.list_profit_record)
    RecyclerView mListProfitRecord;
    @Bind(R.id.refresh_view)
    PullRefreshView mRefreshView;

    private ProfitRecordAdapter mAdapter;

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


    }

    private void initRefreshLayout() {
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setSmoothScrollbarEnabled(true);
        mListProfitRecord.setLayoutManager(manager);
        mAdapter = new ProfitRecordAdapter(this);
        mListProfitRecord.setAdapter(mAdapter);

        List<String> list = new ArrayList<>();
        for (int i=0; i<20; i++) {
            list.add("收益" + i);
        }
        mAdapter.replaceList(list);


        PullRefreshUtil.setRefresh(mRefreshView, true, true);
        mRefreshView.setOnPullDownRefreshListener(new PullRefreshView.OnPullDownRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshView.refreshFinish();
            }
        });

        mRefreshView.setOnPullUpRefreshListener(new PullRefreshView.OnPullUpRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshView.refreshFinish();
            }
        });
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
        context.startActivity(new Intent(context, ProfitRecordActivity.class));
    }
}
