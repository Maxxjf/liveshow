package com.qcloud.liveshow.ui.home.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.FollowAdapter;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.liveshow.constant.AppConstants;
import com.qcloud.liveshow.ui.home.presenter.impl.FollowPresenterImpl;
import com.qcloud.liveshow.ui.home.view.IFollowView;
import com.qcloud.liveshow.widget.customview.EmptyView;
import com.qcloud.qclib.swiperefresh.CustomSwipeRefreshLayout;
import com.qcloud.qclib.swiperefresh.SwipeRecyclerView;
import com.qcloud.qclib.swiperefresh.SwipeRefreshUtil;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.widget.layoutManager.RecycleViewDivider;

import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindDimen;
import timber.log.Timber;

/**
 * 类说明：关注
 * Author: Kuzan
 * Date: 2017/8/11 11:35.
 */
public class FollowFragment extends BaseFragment<IFollowView, FollowPresenterImpl> implements IFollowView {

    @Bind(R.id.list_follow)
    SwipeRecyclerView mListFollow;

    @BindDimen(R.dimen.margin_2)
    int dividerHeight;
    @BindColor(R.color.colorBg)
    int dividerBg;

    private EmptyView mEmptyView;

    private FollowAdapter mAdapter;

    private int pageNum = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_follow;
    }

    @Override
    protected FollowPresenterImpl initPresenter() {
        return new FollowPresenterImpl();
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
        mPresenter.getFollowList(pageNum, AppConstants.PAGE_SIZE);
    }

    private void initRefreshView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mListFollow.setLayoutManager(manager);
        mListFollow.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, dividerHeight, dividerBg));

        mAdapter = new FollowAdapter(getActivity());
        mListFollow.setAdapter(mAdapter);

        SwipeRefreshUtil.setLoadMore(mListFollow, true);
        mListFollow.setOnRefreshListener(new CustomSwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                pageNum = 1;
                mListFollow.isMore(true);
                loadData();
            }
        });
        mListFollow.setOnLoadMoreListener(new CustomSwipeRefreshLayout.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                loadData();
            }
        });

        mEmptyView = new EmptyView(getActivity());
        mListFollow.setEmptyView(mEmptyView, Gravity.TOP);
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
            if (mListFollow != null) {
                mListFollow.refreshFinish();
            }
            if (beans != null && beans.size() > 0) {
                if (mAdapter != null) {
                    mAdapter.replaceList(beans);
                }
                if (mListFollow != null) {
                    mListFollow.isMore(isNext);
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
            if (mListFollow != null) {
                mListFollow.refreshFinish();
            }
            if (beans != null && beans.size() > 0) {
                if (mAdapter != null) {
                    mAdapter.addListAtEnd(beans);
                }
                if (mListFollow != null) {
                    mListFollow.isMore(isNext);
                }
            } else {
                ToastUtils.ToastMessage(getActivity(), R.string.toast_no_more_data);
                if (mListFollow != null) {
                    mListFollow.isMore(false);
                }
            }
        }
    }

    @Override
    public void showEmptyView(String tip) {
        if (mListFollow != null) {
            mListFollow.showEmptyView();
        }
    }

    @Override
    public void hideEmptyView() {
        if (mListFollow != null) {
            mListFollow.hideEmptyView();
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
