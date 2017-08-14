package com.qcloud.liveshow.ui.home.widget;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.NewestAdapter;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.ui.home.presenter.impl.NewestPresenterImpl;
import com.qcloud.liveshow.ui.home.view.INewestView;
import com.qcloud.liveshow.widget.customview.EmptyView;
import com.qcloud.qclib.swiperefresh.CustomSwipeRefreshLayout;
import com.qcloud.qclib.swiperefresh.SwipeRecyclerView;
import com.qcloud.qclib.swiperefresh.SwipeRefreshUtil;
import com.qcloud.qclib.widget.layoutManager.RecycleViewDivider;

import butterknife.Bind;

/**
 * 类说明：最新
 * Author: Kuzan
 * Date: 2017/8/11 11:34.
 */
public class NewestFragment extends BaseFragment<INewestView, NewestPresenterImpl> implements INewestView {

    @Bind(R.id.list_newest)
    SwipeRecyclerView mListNewest;

    private EmptyView mEmptyView;

    private NewestAdapter mAdapter;

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

    }

    private void initRefreshView() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        mListNewest.setLayoutManager(manager);
        mListNewest.addItemDecoration(new RecycleViewDivider(2, (int) getResources().getDimension(R.dimen.line_height), true));

        mAdapter = new NewestAdapter(getActivity());
        mListNewest.setAdapter(mAdapter);

        SwipeRefreshUtil.setLoadMore(mListNewest, true);
        mListNewest.setOnRefreshListener(new CustomSwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                final Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        mListNewest.refreshFinish();

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
        mListNewest.setOnLoadMoreListener(new CustomSwipeRefreshLayout.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                final Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        mListNewest.loadMoreFinish();

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
        mListNewest.setEmptyView(mEmptyView, Gravity.CENTER);
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
