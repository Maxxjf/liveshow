package com.qcloud.liveshow.ui.home.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.SearchAnchorAdapter;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.liveshow.constant.AppConstants;
import com.qcloud.liveshow.ui.home.presenter.impl.SearchAnchorPresenterImpl;
import com.qcloud.liveshow.ui.home.view.ISearchAnchorView;
import com.qcloud.liveshow.ui.room.widget.RoomActivity;
import com.qcloud.liveshow.widget.customview.NoSearchResView;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.pullrefresh.PullRefreshRecyclerView;
import com.qcloud.qclib.pullrefresh.PullRefreshUtil;
import com.qcloud.qclib.pullrefresh.PullRefreshView;
import com.qcloud.qclib.toast.ToastUtils;

import java.util.List;

import butterknife.Bind;
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

    private NoSearchResView mEmptyView;

    private SearchAnchorAdapter mAdapter;

    private String keyword = "";
    private int pageNum = 1;

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

    private void loadData() {
        mPresenter.getSearchList(keyword, pageNum, AppConstants.PAGE_SIZE);
    }

    private void initTitleBar() {
        mTitleBar.setSearchHint(R.string.input_search_anchor_hint);
        mTitleBar.setOnBtnListener(new TitleBar.OnBtnListener() {
            @Override
            public void onBtnClick(View view) {
                if (view.getId() == R.id.btn_search) {
                    keyword = mTitleBar.getSearchValue();
                    loadData();
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
                pageNum = 1;
                mListSearchRes.isMore(true);
                loadData();
            }
        });
        mListSearchRes.setOnPullUpRefreshListener(new PullRefreshView.OnPullUpRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum++;
                loadData();
            }
        });

        mAdapter = new SearchAnchorAdapter(this);
        mListSearchRes.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RoomActivity.openActivity(mContext, i, mAdapter.getList());
            }
        });

        mEmptyView = new NoSearchResView(this);
        mListSearchRes.setEmptyView(mEmptyView, Gravity.CENTER_HORIZONTAL);
        showEmptyView();
    }

    @Override
    public void replaceList(List<RoomBean> beans, boolean isNext) {
        if (isRunning) {
            if (mListSearchRes != null) {
                mListSearchRes.refreshFinish();
            }
            if (beans != null && !beans.isEmpty()) {
                hideEmptyView();
                if (mAdapter != null) {
                    mAdapter.replaceList(beans);
                }
                if (mListSearchRes != null) {
                    mListSearchRes.isMore(isNext);
                }
            } else {
                showEmptyView();
            }
        }
    }

    @Override
    public void addListAtEnd(List<RoomBean> beans, boolean isNext) {
        if (isRunning) {
            if (mListSearchRes != null) {
                mListSearchRes.refreshFinish();
            }
            if (beans != null) {
                if (mAdapter != null) {
                    mAdapter.addListAtEnd(beans);
                }
                if (mListSearchRes != null) {
                    mListSearchRes.isMore(isNext);
                }
            } else {
                ToastUtils.ToastMessage(this, R.string.toast_no_more_data);
                if (mListSearchRes != null) {
                    mListSearchRes.isMore(false);
                }
            }
        }
    }

    @Override
    public void showEmptyView() {
        if (mListSearchRes != null) {
            mListSearchRes.showEmptyView();
        }
    }

    @Override
    public void hideEmptyView() {
        if (mListSearchRes != null) {
            mListSearchRes.hideEmptyView();
        }
    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {
        if (isRunning) {
            if (mListSearchRes != null) {
                mListSearchRes.refreshFinish();
            }
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
}
