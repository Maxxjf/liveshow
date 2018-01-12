package com.qcloud.liveshow.ui.home.widget;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.FollowAdapter;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.liveshow.constant.AppConstants;
import com.qcloud.liveshow.ui.home.presenter.impl.FollowPresenterImpl;
import com.qcloud.liveshow.ui.home.view.IFollowView;
import com.qcloud.liveshow.ui.room.widget.RoomActivity;
import com.qcloud.liveshow.widget.customview.EmptyView;
import com.qcloud.liveshow.widget.customview.NoFollowView;
import com.qcloud.qclib.beans.RxBusEvent;
import com.qcloud.qclib.rxbus.BusProvider;
import com.qcloud.qclib.swiperefresh.CustomSwipeRefreshLayout;
import com.qcloud.qclib.swiperefresh.SwipeRecyclerView;
import com.qcloud.qclib.swiperefresh.SwipeRefreshUtil;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.widget.layoutManager.RecycleViewDivider;

import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.ButterKnife;
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

    private NoFollowView mEmptyView;

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
        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mAdapter.getList() != null && mAdapter.getList().get(i).isLive()) {
                    RoomActivity.openActivity(getActivity(), i, mAdapter.getList());
                } else {
                    ToastUtils.ToastMessage(mContext, getResources().getString(R.string.tag_tip_anchor_is_no_live));
                }
            }
        });

        SwipeRefreshUtil.setLoadMore(mListFollow, true);
        SwipeRefreshUtil.setRefreshImage(getActivity(), mListFollow, R.drawable.icon_refresh_icon, CustomSwipeRefreshLayout.ANIM_IN_LEFT);
        mListFollow.setOnRefreshListener(new CustomSwipeRefreshLayout.OnRefreshListener() {
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

        mEmptyView = new NoFollowView(getActivity());
        mListFollow.setEmptyView(mEmptyView, Gravity.CENTER_HORIZONTAL);
        mEmptyView.setOnRefreshListener(new EmptyView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                BusProvider.getInstance().post(RxBusEvent.newBuilder(R.id.return_hot_fragment).build());
//                loadData();
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
                mListFollow.loadMoreFinish();
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
            if (mListFollow != null) {
                mListFollow.refreshFinish();
            }
            if (isShow) {
                ToastUtils.ToastMessage(mContext, errMsg);
            } else {
                Timber.e(errMsg);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
