package com.qcloud.liveshow.ui.home.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.SearchAnchorAdapter;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.ui.home.presenter.impl.SearchAnchorPresenterImpl;
import com.qcloud.liveshow.ui.home.view.ISearchAnchorView;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.pullrefresh.PullRefreshRecyclerView;
import com.qcloud.qclib.pullrefresh.PullRefreshUtil;
import com.qcloud.qclib.pullrefresh.PullRefreshView;
import com.qcloud.qclib.toast.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * 类说明：搜索主播
 * Author: Kuzan
 * Date: 2017/8/30 10:45.
 */
public class SearchAnchorActivity extends SwipeBaseActivity<ISearchAnchorView, SearchAnchorPresenterImpl> implements ISearchAnchorView {

    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.list_search_res)
    PullRefreshRecyclerView mListSearchRes;

    private SearchAnchorAdapter mAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_search_anchor;
    }

    @Override
    protected SearchAnchorPresenterImpl initPresenter() {
        return new SearchAnchorPresenterImpl();
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
        initTitleBar();

        initListView();
    }

    private void initTitleBar() {
        mTitleBar.setSearchHint(R.string.input_search_anchor_hint);
        mTitleBar.setOnBtnListener(new TitleBar.OnBtnListener() {
            @Override
            public void onBtnClick(View view) {
                if (view.getId() == R.id.btn_search) {
                    String keyWord = mTitleBar.getSearchValue().trim();
                    ToastUtils.ToastMessage(SearchAnchorActivity.this, keyWord+"");
                } else {
                    finish();
                }
            }
        });
    }

    private void initListView() {
        PullRefreshUtil.setRefresh(mListSearchRes, true, true);

        mListSearchRes.setLayoutManager(new LinearLayoutManager(this));
        mListSearchRes.setOnPullDownRefreshListener(new PullRefreshView.OnPullDownRefreshListener() {
            @Override
            public void onRefresh() {
                mListSearchRes.refreshFinish();
            }
        });
        mListSearchRes.setOnPullUpRefreshListener(new PullRefreshView.OnPullUpRefreshListener() {
            @Override
            public void onRefresh() {
                mListSearchRes.refreshFinish();
            }
        });

        mAdapter = new SearchAnchorAdapter(this);
        mListSearchRes.setAdapter(mAdapter);

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
        context.startActivity(new Intent(context, SearchAnchorActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
