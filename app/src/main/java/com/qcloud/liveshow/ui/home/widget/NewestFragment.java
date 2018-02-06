package com.qcloud.liveshow.ui.home.widget;

import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.NewestAdapter;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.liveshow.constant.AppConstants;
import com.qcloud.liveshow.ui.home.presenter.impl.NewestPresenterImpl;
import com.qcloud.liveshow.ui.home.view.INewestView;
import com.qcloud.liveshow.ui.room.widget.RoomActivity;
import com.qcloud.liveshow.widget.customview.EmptyView;
import com.qcloud.liveshow.widget.customview.NoDataView;
import com.qcloud.qclib.swiperefresh.CustomSwipeRefreshLayout;
import com.qcloud.qclib.swiperefresh.SwipeRecyclerView;
import com.qcloud.qclib.swiperefresh.SwipeRefreshUtil;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.NetUtils;
import com.qcloud.qclib.widget.layoutManager.RecycleViewDivider;

import java.util.List;

import butterknife.Bind;
import timber.log.Timber;

/**
 * 类说明：最新
 * Author: Kuzan
 * Date: 2017/8/11 11:34.
 */
public class NewestFragment extends BaseFragment<INewestView, NewestPresenterImpl> implements INewestView {

    @Bind(R.id.list_newest)
    SwipeRecyclerView mListNewest;

    private NoDataView mEmptyView;

    private NewestAdapter mAdapter;

    private int pageNum = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_newest;
    }

    @Override
    protected NewestPresenterImpl initPresenter() {
        return new NewestPresenterImpl();
    }

    @Override
    protected void initViewAndData() {
        initRefreshView();
    }

    @Override
    protected void beginLoad() {
        loadData();
    }

    private void loadData() {
        mPresenter.getNewestList(pageNum, AppConstants.PAGE_SIZE);
    }

    private void initRefreshView() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        mListNewest.setLayoutManager(manager);
        mListNewest.addItemDecoration(new RecycleViewDivider(2, (int) getResources().getDimension(R.dimen.line_height), true));

        mAdapter = new NewestAdapter(getActivity());
        mListNewest.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RoomActivity.openActivity(getActivity(), i, mAdapter.getList());
            }
        });

        SwipeRefreshUtil.setLoadMore(mListNewest, true);
        SwipeRefreshUtil.setRefreshImage(getActivity(), mListNewest, R.drawable.icon_refresh_icon, CustomSwipeRefreshLayout.ANIM_IN_LEFT);
        mListNewest.setOnRefreshListener(new CustomSwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                pageNum = 1;
                mListNewest.isMore(true);
                loadData();
            }
        });
        mListNewest.setOnLoadMoreListener(new CustomSwipeRefreshLayout.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                loadData();
            }
        });

        mEmptyView = new NoDataView(getActivity());
        mListNewest.setEmptyView(mEmptyView, Gravity.CENTER_HORIZONTAL);
        mEmptyView.setOnRefreshListener(new EmptyView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    @Override
    public void replaceList(List<RoomBean> beans, boolean isNext) {
        if (isInFragment) {
            if (mListNewest != null) {
                mListNewest.refreshFinish();
            }
            if (beans != null && beans.size() > 0) {
                if (mAdapter != null) {
                    mAdapter.replaceList(beans);
                }
                if (mListNewest != null) {
                    mListNewest.isMore(isNext);
                }
                hideEmptyView();
            } else {
                showEmptyView(getResources().getString(R.string.tip_no_data));
            }
        }
    }

    @Override
    public void addListAtEnd(List<RoomBean> beans, boolean isNext) {
        if (isInFragment) {
            if (mListNewest != null) {
                mListNewest.loadMoreFinish();
            }
            if (beans != null && beans.size() > 0) {
                if (mAdapter != null) {
                    mAdapter.addListAtEnd(beans);
                }
                if (mListNewest != null) {
                    mListNewest.isMore(isNext);
                }
            } else {
                ToastUtils.ToastMessage(getActivity(), R.string.toast_no_more_data);
                if (mListNewest != null) {
                    mListNewest.isMore(false);
                }
            }
        }
    }

    @Override
    public void showEmptyView(String tip) {
        if (mListNewest != null&&mEmptyView!=null) {
            if (!NetUtils.isConnected(getActivity())){
                mEmptyView.noNetWork();
            }
            mListNewest.showEmptyView();
        }
    }

    @Override
    public void hideEmptyView() {
        if (mListNewest != null) {
            mListNewest.hideEmptyView();
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
        }
    }
}
