package com.qcloud.liveshow.ui.home.widget;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.FollowAdapter;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.ui.home.presenter.impl.FollowPresenterImpl;
import com.qcloud.liveshow.ui.home.view.IFollowView;
import com.qcloud.liveshow.widget.customview.EmptyView;
import com.qcloud.qclib.swiperefresh.CustomSwipeRefreshLayout;
import com.qcloud.qclib.swiperefresh.SwipeRecyclerView;
import com.qcloud.qclib.swiperefresh.SwipeRefreshUtil;
import com.qcloud.qclib.widget.layoutManager.RecycleViewDivider;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindDimen;

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
    @BindColor(R.color.colorGrayBg)
    int dividerBg;

    private EmptyView mEmptyView;

    private FollowAdapter mAdapter;

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
                final Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        mListFollow.refreshFinish();

                    }
                };
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(0);
                    }
                }).start();
            }
        });
        mListFollow.setOnLoadMoreListener(new CustomSwipeRefreshLayout.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                final Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        mListFollow.loadMoreFinish();

                    }
                };
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(0);
                    }
                }).start();
            }
        });

        mEmptyView = new EmptyView(getActivity());
        mListFollow.setEmptyView(mEmptyView, Gravity.CENTER);
        mEmptyView.setOnRefreshListener(new EmptyView.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {

    }
}
